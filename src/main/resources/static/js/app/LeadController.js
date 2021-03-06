(function(){
'use strict';
var app = angular.module('my-app');

app.controller(
				'LeadController',
				[
						'LeadService',
						'GenderService',
						'StateService',
						'LeadStatusService',
						'LeadStatusDetailService',
						'LanguageService',
						'InsuranceService',
						'PlanTypeService',
						'ProviderService',
						'UserService',
						'BestTimeToCallService',
						'EventService',
						'FileUploadService',
						'$sce',
						'$scope',
						'$rootScope',
						'$location',
						'$stateParams',
						'$compile',
						'$state',
						'$filter',
						'$localStorage',
						'DTOptionsBuilder',
						'DTColumnBuilder',
						'$timeout',
						function(LeadService, GenderService, StateService,
								LeadStatusService,LeadStatusDetailService, LanguageService,
								InsuranceService, PlanTypeService, ProviderService, UserService, BestTimeToCallService, EventService, FileUploadService, $sce, $scope,$rootScope, $location, $stateParams, $compile, $state,$filter,
								$localStorage, DTOptionsBuilder, DTColumnBuilder,$timeout) {

							var self = this;
							self.myFile;
							self.invalid =true;
							self.pristine = true;
							self.serverResponse = {};
							self.lead = {};
							self.user = {};
							self.loggedUserInsId = $localStorage.loginUser.insuranceId||'0';
							self.loginUserRole = $localStorage.loginUser.roleName;
							self.notes ='';
							self.leads = [];
							self.leadEventId = $stateParams.eventId;
							self.genders = [];
							self.bestTimeToCalls = [];
							self.states = [];
							//$location.url('/');
							self.languages = [];
							self.statuses = [];
							self.statusDetails = [];
							self.insurances = [];
							self.providers = [];
							self.planTypes = [];
							self.selectedAgentLeadAppointment = {};
							self.users = [];
							self.events = [];
							self.content = '';
							self.display = $stateParams.leadDisplay||false;
							self.displayEditButton = false;
							self.submit = submit;
							self.addLead = addLead;
							self.getAllLeads = getAllLeads;
							self.createLead = createLead;
							self.updateLead = updateLead;
							self.removeLead = removeLead;
							self.editLead = editLead;
							self.resetAssignment = resetAssignment;
							self.dtInstance = {};
							self.leadId = null;
							self.getAllGenders = getAllGenders;
							self.getAllStates = getAllStates;
							self.getAllLeadStatuses = getAllLeadStatuses;
							self.getAllLeadStatusDetails = getAllLeadStatusDetails;
							self.getAllLanguages = getAllLanguages;
							self.getAllInsurances = getAllInsurances;
							self.getAllBestTimeToCalls = getAllBestTimeToCalls;
							self.getAllAgents = getAllAgents;
							self.getAllPlanTypes = getAllPlanTypes;
							self.getAllInsuranceTypes = getAllInsuranceTypes;
							self.getAllProviders = getAllProviders;
							self.getAllEvents = getAllEvents;
							self.consentFormSigned = 'N';
							self.uploadFile = uploadFile;
							self.readUploadedFile = readUploadedFile;
							self.addAgentLeadAppointment = addAgentLeadAppointment;
							self.showAgentAssignment = showAgentAssignment;
							self.showEventSource = showEventSource;
							self.showStatusNotes = showStatusNotes;
							self.showStatusChangeDetails =showStatusChangeDetails;
							self.showEventStatus =showEventStatus;
							self.showLeadAdditionalDetails = showLeadAdditionalDetails;
							self.configLeadEvent = configLeadEvent;
							self.showAddorUpdateButton = showAddorUpdateButton;
							self.leadEdit = leadEdit;
							self.cancelEdit = cancelEdit;
							 configLeadEvent();
							self.reset = reset;
							self.successMessage = '';
							self.errorMessage = '';
							self.done = false;
							self.onlyIntegers = /^\d+$/;
							self.onlyNumbers = /^\d+([,.]\d+)?$/;
							self.checkBoxChange = checkBoxChange;
							$localStorage.pageNumber ;
							self.dtInstanceCallback = dtInstanceCallback;
						    function dtInstanceCallback(dtInstance) {
						        self.dtInstance = dtInstance;
						    }
						    self.genders = getAllGenders();
						    self.filterGenderList =[];
							    self.genders.forEach(function(gender){
							       self.filterGenderList.push( {value:gender.id, label:gender.description});
							    } );


							self.languages = getAllLanguages();
							self.filterLanguageList =[];
							    self.languages.forEach(function(language){
							       self.filterLanguageList.push( {value:language.id, label:language.description});
							    } );

							self.statuses = getAllLeadStatuses();
							self.filterStatusList =[];
							    self.statuses.forEach(function(status){
							       self.filterStatusList.push( {value:status.id, label:status.description});
							    } );

							self.statusDetails = getAllLeadStatusDetails();
							self.filterStatusDetailList =[];
							    self.statusDetails.forEach(function(statusDetail){
							       self.filterStatusDetailList.push( {value:statusDetail.id, label:statusDetail.description});
							    } );

							self.dtColumns = [
									DTColumnBuilder.newColumn('firstName','FIRSTNAME').renderWith(
													function(data, type, full,
															meta) {
														 return '<a href="javascript:void(0)" class="'+full.id+'" ng-click="ctrl.leadEdit('+full.id+')">'+data+'</a>';
													}).withClass("text-left"),
									DTColumnBuilder.newColumn('lastName','LASTNAME').withOption(),
									DTColumnBuilder.newColumn(
											'dob','DOB').withOption('defaultContent', ''),
									DTColumnBuilder.newColumn(
											'gender.description','GENDER'),
									DTColumnBuilder.newColumn(
											'contact.homePhone','PHONE').renderWith(
											               function(data, type, full, meta) {
											                 var s2 = (""+data).replace(/\D/g, '');
  															 var m = s2.match(/^(\d{3})(\d{3})(\d{4})$/);
  															return (!m) ? null : "(" + m[1] + ") " + m[2] + "-" + m[3];
												          }).withClass("text-left").withOption('defaultContent', ''),
									DTColumnBuilder.newColumn(
											'language.description','LANGUAGE'),
									DTColumnBuilder.newColumn(
											'status.description','STATUS'),
									DTColumnBuilder.newColumn(
													'statusDetail.description','STATUS_DETAIL').withOption('defaultContent', ''),
									DTColumnBuilder.newColumn(
													'lastNotes','NOTES').withClass("text-left").withOption('defaultContent', '')];

							self.dtOptions = DTOptionsBuilder.newOptions().withBootstrap()
									 .withDisplayLength(20)
									.withOption('bServerSide', true)
											.withOption('responsive', true)
											.withOption("bLengthChange", false)
											.withOption("bPaginate", true)
											.withOption('bProcessing', true)
											.withOption('stateSave', true)
											.withOption('searchDelay', 2000)
										    .withOption('createdRow', createdRow)
									        .withPaginationType('full_numbers')
									        .withOption('ordering', true)
											.withOption('order', [[0,'ASC'],[1,'ASC']])
											.withOption('bDeferRender', true)
										    .withOption('scrollX', '716')
										    .withLightColumnFilter({
										        '0': {
										          html: 'input',
										          type: 'text',
										          time:500
										          },
										        '1': {
										          html: 'input',
										          type: 'text',
										          time: 500
										        },
										        '2': {
										          html: 'input',
										          type: 'text',
										          time: 500
										        },
										        '3': {
										          html: 'input',
										          type: 'select',
										          values: self.filterGenderList,
										          time: 500
										        },
										        '4': {
										          html: 'input',
										          type: 'text',
										          time: 500
										        },
										        '5': {
										          html: 'input',
										          type: 'select',
										          values: self.filterLanguageList,
										          time: 500
										        },
										        '6': {
										          html: 'input',
										          type: 'select',
										          values: self.filterStatusList,
										          time: 500
										        },
										        '7': {
										          html: 'input',
										          type: 'select',
										          values: self.filterStatusDetailList,
										          time:500
										        }
										      })
										      .withOption('language', {
										          'processing': '<div class="table-logo-wrapper"><div class="vloader"></div></div>'
										         // 'url': $cookies.get('JSESSION')
										        })
									        .withFnServerData(serverData);
							if(self.display){
								 addLead();
						    }

							function createdRow(row, data, dataIndex) {
								// Recompiling so we can bind Angular directive
								// to the DT
								 $compile(angular.element(row).contents())(
										$scope);
							}

							function checkBoxChange(checkStatus, leadId) {
								self.displayEditButton = checkStatus;
								self.leadId = leadId

							}

							function serverData(sSource, aoData, fnCallback) {


								// All the parameters you need is in the aoData
								// variable
								var order = aoData[2].value;
								var page = 	aoData[3].value / aoData[4].value;
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

								 var firstName      	= aoData[1].value[0]['search'].value || '';
      							 var lastName       	= aoData[1].value[1]['search'].value || '';
      							 var dob			 	= aoData[1].value[2]['search'].value || '';
      							 var selectedGender 	= aoData[1].value[3]['search'].value || '';
      							 var phoneNo        	= aoData[1].value[4]['search'].value || '';
							     var selectedLang   	= aoData[1].value[5]['search'].value || '';
							     var selectedStatus   	= aoData[1].value[6]['search'].value || '';
							     var selectedStDetails  = aoData[1].value[7]['search'].value || '';

								// Then just call your service to get the  records from server side
								LeadService
										.loadLeads(page, length, search.value, sortCol+','+sortDir
										 ,firstName,lastName,dob, selectedGender, phoneNo,selectedLang,selectedStatus, selectedStDetails)
										.then(
												function(result) {
													var records = {
														'recordsTotal' : result.data.totalElements||0,
														'recordsFiltered' : result.data.totalElements||0,
														'data' : result.data.content||{}
													};
													fnCallback(records);
												});
							}

							function callback(json) {
								console.log(json);
							}

							function submit() {
								console.log('Submitting');
								  
								if(self.lead.agentLeadAppointmentList){
									self.lead.agentLeadAppointmentList = self.lead.agentLeadAppointmentList.filter(function( obj ) {
										  return obj.id != self.selectedAgentLeadAppointment.id;
										});
									if(Object.keys(self.selectedAgentLeadAppointment).length > 0){
										self.lead.agentLeadAppointmentList.push(self.selectedAgentLeadAppointment);
									}

                                    console.log('Submitting agentLeadAppointmentList');
								}
								if(self.lead.agentLeadAppointmentList === undefined && self.selectedAgentLeadAppointment != null && Object.keys(self.selectedAgentLeadAppointment).length > 0 ){
								self.lead.agentLeadAppointmentList.push(self.selectedAgentLeadAppointment);
								}

								 if(self.notes && self.notes != ''){
				            		 self.lead.leadNotes = [];
				            		 self.lead.leadNotes.push({notes:self.notes, user:{id:$localStorage.loginUser.userId}});
				            	 }
								 uploadFile();

							}


							function readUploadedFile(){
								console.log('About to read consignment form');
								FileUploadService.getFileUpload(self.lead.fileUpload.id).then(
										function(response) {
											self.errorMessage = '';
											var file = new Blob([response], {type: self.lead.fileUpload.contentType});
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
							function createLead(lead) {
								console.log('About to create lead');
								LeadService
										.createLead(lead)
										.then(
												function(response) {
													console
															.log('Lead created successfully');
													self.successMessage = 'Lead created successfully';
													self.errorMessage = '';
													self.done = true;
													self.lead = {};
													self.lead.agentLeadAppointmentList = [];
													self.selectedAgentLeadAppointment = {};
													self.notes ='';
													clearFiles();

													self.dtInstance.reloadData();
							                       // self.dtInstance.rerender();
							                        $state.go('main.lead',{},{reload:'main.lead'});
							                        self.display = false;
												},
												function(errResponse) {
													console
															.error('Error while creating Lead');
													self.errorMessage = 'Error while creating Lead: '
															+ errResponse.data.errorMessage;
													self.successMessage = '';
												});
							}

							function updateLead(lead, id) {
								console.log('About to update lead',lead.id);
								LeadService
										.updateLead(lead, id)
										.then(
												function(response) {
													console
															.log('Lead updated successfully');
													self.successMessage = 'Lead updated successfully';
													self.errorMessage = '';
													self.done = true;
													self.notes ='';
													clearFiles();
													self.dtInstance.reloadData();
							                      //  self.dtInstance.rerender();
													self.selectedAgentLeadAppointment = {};
							                        $state.go('main.lead',{},{reload:'main.lead'});
							                        self.display = false;
												},
												function(errResponse) {
													console
															.error('Error while updating Lead');
													self.errorMessage = 'Error while updating Lead: '
															+ errResponse.data.errorMessage;
													self.successMessage = '';
												});
							}

							function removeLead(id) {
								console.log('About to remove Lead with id '
										+ id);
								LeadService
										.removeLead(id)
										.then(
												function() {
													console
															.log('Lead '
																	+ id
																	+ ' removed successfully');
													self.dtInstance.reloadData();
							                      //  self.dtInstance.rerender();
												},
												function(errResponse) {
													console
															.error('Error while removing lead '
																	+ id
																	+ ', Error :'
																	+ errResponse.data.error);
												});
							}
							function editLead(id) {

								self.successMessage = '';
								self.errorMessage = '';
									LeadService
										.getLead(id)
										.then(
												function(lead) {
													self.lead = lead;

														if(self.lead.agentLeadAppointmentList === undefined){
															self.selectedAgentLeadAppointment= {} ;
														}
														else{
															if(self.lead.agentLeadAppointmentList.length>0){
															self.selectedAgentLeadAppointmentList  = self.lead.agentLeadAppointmentList.filter(function( obj ) {
																                                    console.log('obj.user.id',obj.user.id );
										   															console.log('$localStorage.loginUser.userId',$localStorage.loginUser.userId );

										   															return obj.user.id == $localStorage.loginUser.userId;
										   															 });

																if(self.selectedAgentLeadAppointmentList !== undefined && self.selectedAgentLeadAppointmentList.length > 0)	{
																    self.selectedAgentLeadAppointment =  self.selectedAgentLeadAppointmentList[0];
																} else {
																	self.selectedAgentLeadAppointment =  self.lead.agentLeadAppointmentList[0];
																}

																console.log('self.selectedAgentLeadAppointment  after filter' );
															}
														}
													self.events = getAllEvents();
													self.genders = getAllGenders();
													self.bestTimeToCalls = getAllBestTimeToCalls();
													self.states = getAllStates();
													self.languages = getAllLanguages();
													self.statuses = getAllLeadStatuses();
													self.statusDetails = getAllLeadStatusDetails();
													self.insurances = getAllInsurances();
													self.planTypes = getAllPlanTypes();
													self.providers = getAllProviders();
													self.users = getAllAgents();
													self.display = true;
												},
												function(errResponse) {
													console
															.error('Error while removing lead '
																	+ id
																	+ ', Error :'
																	+ errResponse.data);
												});
							}

							function addLead() {
								self.errorMessage = '';
								self.successMessage = '';
								self.lead.agentLeadAppointmentList = [];
								if(self.display){
									self.providers = getAllProviders();
									self.users = getAllAgents();
									self.events = getAllEvents();
									self.genders = getAllGenders();
									self.bestTimeToCalls = getAllBestTimeToCalls();
									self.states = getAllStates();
									self.languages = getAllLanguages();
									self.statuses = getAllLeadStatuses();
									self.statusDetails = getAllLeadStatusDetails();
									self.insurances = getAllInsurances();
									self.planTypes = getAllPlanTypes();
									self.display = true;
								}else{
								  $state.go('main.lead.edit',{},{reload:false}).transition;
									self.providers = getAllProviders();
											self.users = getAllAgents();
											self.events = getAllEvents();
											self.genders = getAllGenders();
											self.bestTimeToCalls = getAllBestTimeToCalls();
											self.states = getAllStates();
											self.languages = getAllLanguages();
											self.statuses = getAllLeadStatuses();
											self.statusDetails = getAllLeadStatusDetails();
											self.insurances = getAllInsurances();
											self.planTypes = getAllPlanTypes();
											self.display = true;
								}

							}

							function reset() {
								self.successMessage = '';
								self.errorMessage = '';
								self.lead = {};
								self.selectedAgentLeadAppointment = {};
								$scope.myForm.$setPristine(); // reset Form
							}

							function cancelEdit(){
					            self.successMessage='';
					            self.errorMessage='';
					            self.lead={};
					            self.selectedAgentLeadAppointment = {};
					            self.display = false;
							      	$state.go('main.lead', {}, {reload: 'main.lead'});
					        }

							function uploadFile() {
								if(self.myFile){
									var  promise = FileUploadService.uploadFileToUrl(self.myFile);

						            promise.then(function (response) {
						            	if(!self.lead.fileUpload){
						            		self.lead.fileUpload = {};
						            	}
						            	 var fileuploads = response;
						            	 if(fileuploads.length >0 )
						                self.lead.fileUpload= fileuploads[0];
						            	 if (self.lead.id === undefined || self.lead.id === null) {
												console.log('Saving New Lead');
												createLead(self.lead);

											} else {

												updateLead(self.lead, self.lead.id);
												console.log('Lead updated with id ',
														self.lead.id);
											}
											self.displayEditButton = false;


						            }, function () {
						                self.serverResponse = 'An error has occurred';
						            });
								}else{
									if (self.lead.id === undefined || self.lead.id === null) {
										console.log('Saving New Lead');
										createLead(self.lead);

									} else {
										 console.log('before upload lead',self.lead.agentLeadAppointmentList);
										updateLead(self.lead, self.lead.id);
										console.log('Lead updated with id ',
												self.lead.id);
									}
									self.displayEditButton = false;
								}


						        }

						        function configLeadEvent(){
						        	self.successMessage = '';
									self.errorMessage = '';

									if(self.leadEventId){
										EventService
										.getEvent(self.leadEventId).then(
												function(event) {
													self.lead.event = event;
													self.events = getAllEvents();
												},
												function(errResponse) {
													console
															.error('Error while fetching event '
																	+ self.leadEventId
																	+ ', Error :'
																	+ errResponse.data);
												});;
									}
						        }

						    function showAgentAssignment(){
						    
						    	if(   (self.lead.status &&
						    	 (self.lead.status.description === 'Agent' || self.lead.status.description === 'Converted' || self.lead.status.description === 'Disenrolled')
						    	 || self.lead.agentLeadAppointmentList.length > 0
						    	 ) ){
						    		return true;
						    	}else{
						    		return false;
						    	}
						    }
						    function showAddorUpdateButton(){
						    			return (self.consentFormSigned === 'Y' && !self.myFile) || self.invalid  || self.pristine ;
						    }


						    function showCurrentPlan(){
						    	if($localStorage.loginUser.roleName == 'EVENT_COORDINATOR'){
						    		return true;
						    	}else{
						    		return  false;
						    	}
						    }

						    function showEventStatus(){
						    	if($localStorage.loginUser.roleName != 'EVENT_COORDINATOR'){
						    		return true;
						    	}else{
						    		return false;
						    	}
						    }

						    function showStatusNotes(){
						    	if($localStorage.loginUser.roleName == 'AGENT'){
						    		return true;
						    	}else{
						    		return false;
						    	}
						    }

						    function showEventSource(){
						    	if($localStorage.loginUser.roleName != 'AGENT'){
						    		return true;
						    	}else{
						    		return false;
						    	}
						    }

						    function showEventStatus(){
						    	if($localStorage.loginUser.roleName != 'EVENT_COORDINATOR'){
						    		return true;
						    	}else{
						    		return false;
						    	}
						    }

						    function showStatusChangeDetails(){
						    	if( self.lead.status && (self.lead.status.description == 'Converted' || self.lead.status.description == 'Disenrolled')){
						    		return true;
						    	}else{
						    		return false;
						    	}
						    }

						    function showLeadAdditionalDetails(){
						    	if($localStorage.loginUser.roleName == 'MANAGER'  || $localStorage.loginUser.roleName == 'CARE_COORDINATOR' || $localStorage.loginUser.roleName == 'EVENT_COORDINATOR' || $localStorage.loginUser.roleName == 'ADMIN'){
						    		return true;
						    	}else{
						    		return false;
						    	}
						    }




							function addAgentLeadAppointment() {
								self.lead.agentLeadAppointments.push(self.selectedAgentLeadAppointment);
							}
							function getAllLeads() {
								return LeadService.getAllLeads();
							}

							function getAllGenders() {
								return GenderService.getAllGenders();
							}

							function getAllBestTimeToCalls() {
								return BestTimeToCallService.getAllBestTimeToCalls();
							}

							function getAllStates() {
								return StateService.getAllStates();
							}

							function getAllLeadStatuses() {
								return LeadStatusService.getAllLeadStatuses();
							}

							function getAllLeadStatusDetails() {
								return LeadStatusDetailService.getAllLeadStatusDetails();
							}

							function getAllLanguages() {
								return LanguageService
										.getAllLanguages();
							}

							function getAllInsurances() {
								return InsuranceService
										.getAllInsurances();
							}
							function getAllInsuranceTypes() {
								return InsuranceTypeService
										.getAllInsuranceTypes();
							}

							function getAllAgents() {
								return UserService
										.getAllUsers();
							}

							function getAllPlanTypes() {
								return PlanTypeService
										.getAllPlanTypes();
							}

							function getAllProviders() {
								return ProviderService
										.getAllProviders();
							}
							function getAllEvents() {
								return EventService
										.getAllEvents();
							}

							function leadEdit(id){
								var params = {'leadDisplay':true};
								var trans =  $state.go('main.lead.edit',{},{reload:false}).transition;
								
								if(trans !== undefined){
							        trans.onSuccess({}, function() {
							          editLead(id);
							        }, {
							          priority: -1
							        });
								}else{
									editLead(id);
								}
							}

							function clearFiles(){
								angular.forEach(
									    angular.element("input[type='file']"),
									    function(inputElem) {
									      angular.element(inputElem).val(null);
									    });
							}

							function resetAssignment(status){
								if(self.lead.agentLeadAppointmentList === undefined || self.lead.agentLeadAppointmentList.length ===0){

									if(status === 'Converted'){
										self.lead.agentLeadAppointmentList =[];
										self.selectedAgentLeadAppointment = {appointmentTime:new Date(), user:{id:$localStorage.loginUser.userId}, prvdr:self.selectedAgentLeadAppointment.prvdr,insurance:self.selectedAgentLeadAppointment.insurance,effectiveFrom:self.selectedAgentLeadAppointment.effectiveFrom};
										console.log('Converted agent record',self.selectedAgentLeadAppointment );

									}else{
										self.selectedAgentLeadAppointment= {} ;
									}
								}
								else{
									self.selectedAgentLeadAppointments = self.lead.agentLeadAppointmentList;
									//self.selectedAgentLeadAppointments = $filter("filter")(self.lead.agentLeadAppointmentList, {activeInd :'Y'});
									if(self.selectedAgentLeadAppointments.length>0){

										self.selectedAgentLeadAppointment = self.selectedAgentLeadAppointments[0];
									}
							   }
							}
							
							 self.refreshEvents = function (searchFilter){
          												EventService.loadEvents(0, 20, searchFilter, null)
															.then( function(result)   {
													      		self.events = result.data.content ;
													  		});
      						}

	}
]);
})();
