(function(){
'use strict';
var app = angular.module('my-app');

app.controller(
				'EventController',
				[
						'EventService',
						'FacilityTypeService',
						'UserService',
						'StateService',
						'FileUploadService',
						'$scope',
						'$localStorage',
						'$state',
						'$stateParams',
						'$compile',
						'$sce',
						'$filter',
						'DTOptionsBuilder',
						'DTColumnBuilder',
						function(EventService,  FacilityTypeService, UserService, StateService,   FileUploadService,  $scope,$localStorage,$state,$stateParams, $compile, $sce, $filter,
								DTOptionsBuilder, DTColumnBuilder) {

							var self = this;
							self.event = {};
							self.events = [];
							self.myFiles = [];
							self.bool = false;
							self.facilityTypes = [];
							self.users = [];
							self.display = $stateParams.eventDisplay||false;;
							self.states = [];
							self.displayEditButton = false;
							self.submit = submit;
							self.addEvent = addEvent;
							self.event.interval=self.event.interval||1;
							self.getAllEvents = getAllEvents;
							self.createEvent = createEvent;
							self.updateEvent = updateEvent;
							self.removeEvent = removeEvent;
							self.editEvent = editEvent;
							self.getAllAgents = getAllAgents;
							self.getAllStates = getAllStates;
							self.getAllFacilityTypes = getAllFacilityTypes;
							self.getAllActivityTypes = getAllActivityTypes;
							self.getEventRepEmails = getEventRepEmails;
							self.readUploadedFile = readUploadedFile;
							self.cancelEdit = cancelEdit;
							self.adminOrManager = adminOrManager;
							self.uploadFile = uploadFile;
							self.convertToInt =convertToInt;
							//Lead from event
							self.addLead = addLead;
							self.dtInstance = {};
							self.eventId = null;
							self.reset = reset;
							self.eventEdit = eventEdit;
							self.successMessage = '';
							self.errorMessage = '';
							self.done = false;
							self.validEventDate = validEventDate;
							self.onlyIntegers = /^\d+$/;
							self.onlyNumbers = /^\d+([,.]\d+)?$/;
							self.checkBoxChange = checkBoxChange;
							self.errMessage = '';
							self.dtColumns = [
									DTColumnBuilder.newColumn('eventName')
											.withTitle('EVENT NAME')
											.withOption('defaultContent', '').renderWith(
													function(data, type, full,
															meta) {
														 return '<a href="javascript:void(0)" class="'+full.id+'" ng-click="ctrl.eventEdit('+full.id+')">'+data+'</a>';
													}).withClass("text-left"),
									DTColumnBuilder.newColumn('startTime')
													.withTitle('STARTTIME').withOption(
															'defaultContent', ''),
									DTColumnBuilder.newColumn('endTime')
																	.withTitle('ENDTIME').withOption(
																			'defaultContent', ''),
									DTColumnBuilder.newColumn('facilityType.description').withTitle(
											'FACILITY TYPE').withOption('defaultContent', ''),
									DTColumnBuilder.newColumn('contact.contactPerson')
															.withTitle('CONTACT PERSON').withOption(
															'defaultContent', ''),
									DTColumnBuilder.newColumn('contact.mobilePhone')
																.withTitle('CONTACT PHONE').renderWith( 
											               function(data, type, full, meta) {
											                 var s2 = (""+data).replace(/\D/g, '');
  															 var m = s2.match(/^(\d{3})(\d{3})(\d{4})$/);
  															return (!m) ? null : "(" + m[1] + ") " + m[2] + "-" + m[3];
												          }).withClass("text-left").withOption('defaultContent', '') ];

							
							self.dtOptions = DTOptionsBuilder.newOptions()
							.withDisplayLength(20)
						    .withOption('bServerSide', true)
									.withOption("bLengthChange", false)
									.withOption("bPaginate", true)
									.withOption('bProcessing', true)
									.withOption('bStateSave', true)
									.withOption('searchDelay', 3000)
								    .withOption('createdRow', createdRow)
							        .withPaginationType('full_numbers')
							        
							        .withFnServerData(serverData);


							function createdRow(row, data, dataIndex) {
								// Recompiling so we can bind Angular directive
								// to the DT
								$compile(angular.element(row).contents())(
										$scope);
							}

							function checkBoxChange(checkStatus, eventId) {
								self.displayEditButton = checkStatus;
								self.eventId = eventId

							}

							function serverData(sSource, aoData, fnCallback) {
								// All the parameters you need is in the aoData
								// variable

								var order = aoData[2].value;
								var page = aoData[3].value / aoData[4].value;
								var length = aoData[4].value;
								var search = aoData[5].value;

								var paramMap = {};
								for ( var i = 0; i < aoData.length; i++) {
								  paramMap[aoData[i].name] = aoData[i].value;
								}
								
								var sortCol ='';
								var sortDir ='';
								// extract sort information
								 if(paramMap['columns'] !== undefined && paramMap['columns'] !== null && paramMap['order'] !== undefined && paramMap['order'] !== null ){
									 sortCol = paramMap['columns'][paramMap['order'][0]['column']].data;
									  sortDir = paramMap['order'][0]['dir'];
								 }
								 
								// Then just call your service to get the
								// records from server side
								EventService
										.loadEvents(page, length, search.value, sortCol+','+sortDir)
										.then(
												function(result) {
													var records = {
															'recordsTotal' : result.data.totalElements ||0,
															'recordsFiltered' : result.data.totalElements||0,
															'data' : result.data.content||{}
													};
													fnCallback(records);
												});
							}


							function submit() {
								console.log('Submitting');
								uploadFile();
								
								self.displayEditButton = false;
							}

							function readUploadedFile(self_event_attachment_id,self_event_attachment_contentType ){
								console.log('About to read uploaded documents');
								
								FileUploadService.getFileUpload(self_event_attachment_id).then(
										function(response) {
											self.errorMessage = '';
											var file = new Blob([response], {type: self_event_attachment_contentType});
											 var fileURL = URL.createObjectURL(file);
										    self.content = $sce.trustAsResourceUrl(fileURL); 
										    
										},
										function(errResponse) {
											console
													.error('Error while reading consignment form');
											self.errorMessage = 'Error while reading consignment form: '
													+ errResponse.data.errorMessage;
											self.successMessage = '';
										}); 
							}
							
							function createEvent(event) {
								console.log('About to create event');
								EventService
										.createEvent(event)
										.then(
												function(response) {
													console
															.log('Event created successfully');
													self.successMessage = 'Event created successfully';
													self.errorMessage = '';
													self.done = true;
													self.display = false;
													self.event = {};
													clearFiles();
													$scope.myForm
															.$setPristine();
							                        self.dtInstance.rerender();
							                        $state.go('main.event');
												},
												function(errResponse) {
													console
															.error('Error while creating Event');
													self.errorMessage = 'Error while creating Event: '
															+ errResponse.data.errorMessage;
													self.successMessage = '';
												});
							}

							function updateEvent(event, id) {
								console.log('About to update event');
								EventService
										.updateEvent(event, id)
										.then(
												function(response) {
													console
															.log('Event updated successfully');
													self.successMessage = 'Event updated successfully';
													self.errorMessage = '';
													self.done = true;
													self.display = false;
													self.event = {};
													clearFiles();
							                        self.dtInstance.rerender();
							                        $state.go('main.event');
												},
												function(errResponse) {
													console
															.error('Error while updating Event');
													self.errorMessage = 'Error while updating Event '
															+ errResponse.data;
													self.successMessage = '';
												});
							}

							function removeEvent(id) {
								console.log('About to remove Event with id '
										+ id);
								EventService
										.removeEvent(id)
										.then(
												function() {
													console
															.log('Event '
																	+ id
																	+ ' removed successfully');
							                        self.dtInstance.rerender();
												},
												function(errResponse) {
													console
															.error('Error while removing event '
																	+ id
																	+ ', Error :'
																	+ errResponse.data);
												});
							}
							function editEvent(id) {
								
								self.successMessage = '';
								self.errorMessage = '';
								self.users = getAllAgents();
								self.states = getAllStates();
								self.facilityTypes = getAllFacilityTypes();
									EventService
										.getEvent(id)
										.then(
												function(event) {
													self.event = event;
													if(self.event.repeatRule==='' ){
														self.repeatDisplay = false;
									                }
													self.display = true;
												},
												function(errResponse) {
													console
															.error('Error while removing event '
																	+ id
																	+ ', Error :'
																	+ errResponse.data);
												});
							}

							function addEvent() {
								self.successMessage = '';
								self.errorMessage = '';
								var trans =  $state.go('main.event.edit').transition;
								trans.onSuccess({}, function() {
									self.users = getAllAgents();
									self.states = getAllStates();
									self.facilityTypes = getAllFacilityTypes();
									self.display = true;
								});
							}

							function reset() {
								self.successMessage = '';
								self.errorMessage = '';
								self.event = {};
								$scope.myForm.$setPristine(); // reset Form
							}
							
							function cancelEdit(){
					            self.successMessage='';
					            self.errorMessage='';
					            self.event={};
					            $scope.myForm.$setPristine(); //reset Form
					            self.display = false;
					            $state.go('main.event', {}, {reload: false});
					        }
							
                            function convertToInt(id){
                                return parseInt(id, 10);
                            }
                            
							function uploadFile() {
						           var  promise = FileUploadService.uploadFileToUrl(self.myFiles);

						            promise.then(function (response) {
						            	if(!self.event.attachments){
						            		self.event.attachments = [];
						            	}
						                self.event.attachments = response;
						                if(self.repeatDisplay === false){
						                	self.event.repeatRule='';
						                }
						                if (self.event.id === undefined
												|| self.event.id === null) {
											
											createEvent(self.event);
										} else {
											updateEvent(self.event, self.event.id);
											console.log('Event updated with id ',
													self.event.id);
										}
						               // createEvent(self.event);
						            }, function () {
						                self.serverResponse = 'An error has occurred';
						            })
						        };
						        
						    
							function getEventRepEmails(){
								
								if(self.event.representatives){
									var repEmails = self.event.representatives.map(function(agent) {return agent.email;});
									var str = repEmails.join();
									return str;
								}
								
								return "";

							}
							
							function  adminOrManager(){
						    	if($localStorage.loginUser.roleName === 'ADMIN' ||  $localStorage.loginUser.roleName === 'MANAGER' ||  $localStorage.loginUser.roleName === 'EVENT_COORDINATOR'){
						    		return true;
						    	}else{
						    		return false;
						    	}
						    }

							
							function addAgentEventAppointment() {
								self.event.agentEventAppointments.push(self.selectedAgentEventAppointment);
							}
							

							function getAllEvents() {
								return EventService.getAllEvents();
							}

							function getAllAgents() {
								return UserService
										.getAllUsers();
							}
							
							function getAllStates() {
								return StateService.getAllStates();
							}
							

							function getAllFacilityTypes() {
								return FacilityTypeService
										.getAllFacilityTypes();
							}

							function getAllActivityTypes() {
								return ActivityTypeService
										.getAllActivityTypes();
							}

							
							function eventEdit(id){
							//	var params = {"id":id,"eventDisplay":true};
								var trans =  $state.go('main.event.edit').transition;
								trans.onSuccess({}, function() {
								
								editEvent(id);
								});
							}
							
							function addLead( ){
								var params = {"eventId":self.event.id,"leadDisplay":true};
								$state.go('main.lead.edit', params );
							}

							function clearFiles(){
								angular.forEach(
									    angular.element("input[type='file']"),
									    function(inputElem) {
									      angular.element(inputElem).val(null);
									    });
							}
							
							 function validEventDate(startDate,endDate) {
							    if( new Date(startDate).getTime()  >=  new Date(endDate).getTime() ){
							      self.errMessage = 'End Date should be greater than start date';
							      $scope.myForm.eventDateEndTime.$setValidity("improper", false);
							      
							    }else{
							    self.errMessage='';
							    $scope.myForm.eventDateEndTime.$setValidity("improper", true);
							    }
							}

							function clear() {
								self.event.eventDateTime  = null;
							}
								  
	} 
 ]);

})();