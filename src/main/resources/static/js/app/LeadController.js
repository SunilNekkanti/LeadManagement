'use strict';

app
		.controller(
				'LeadController',
				[
						'LeadService',
						'GenderService',
						'StateService',
						'LeadStatusService',
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
								LeadStatusService, LanguageService,
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
							console.log('leadcontroller.js$localStorage.loginUser.roleName'+$localStorage.loginUser.roleName);
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
						    
							self.dtColumns = [
									DTColumnBuilder.newColumn('firstName','FIRSTNAME').renderWith(
													function(data, type, full,
															meta) {
														 return '<a href="javascript:void(0)" class="'+full.id+'" ng-click="ctrl.leadEdit(' +full.id+')">'+data+'</a>';
													}).withClass("text-left"),
									DTColumnBuilder.newColumn('lastName','LASTNAME').withOption(),
									DTColumnBuilder.newColumn(
											'gender.description','GENDER'),
									DTColumnBuilder.newColumn(
											'language.description','LANGUAGE'),
									DTColumnBuilder.newColumn(
											'status.description','STATUS')];

							self.dtOptions = DTOptionsBuilder.newOptions()
									 .withDisplayLength(20)
									.withOption('bServerSide', true)
											.withOption('responsive', true)
											.withOption("bLengthChange", false)
											.withOption("bPaginate", true)
											.withOption('bProcessing', true)
											.withOption('bSaveState', true)
											.withOption('searchDelay', 1000)
										    .withOption('createdRow', createdRow)
									        .withPaginationType('full_numbers')
									        .withOption('ordering', true)
											.withOption('order', [[0,'ASC'],[1,'ASC']])
											.withOption('aLengthMenu', [[15, 20, -1],[ 15, 20, "All"]])
											.withOption('bDeferRender', true)
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
								 
								// Then just call your service to get the
								// records from server side
								LeadService
										.loadLeads(page, length, search.value, sortCol+','+sortDir )
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
									
									self.lead.agentLeadAppointmentList.push(self.selectedAgentLeadAppointment);
									
									console.log('Submitting agentLeadAppointmentList'+JSON.stringify(self.lead.agentLeadAppointmentList));
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
													self.display = false;
													self.lead = {};
													self.selectedAgentLeadAppointment = {};
													self.notes ='';
													clearFiles();
													self.dtInstance.reloadData();
							                       // self.dtInstance.rerender();
							                        $state.go('lead');
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
								console.log('About to update lead');
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
							                        $state.go('lead');
							                        self.display = false;
												},
												function(errResponse) {
													console
															.error('Error while updating Lead');
													self.errorMessage = 'Error while updating Lead '
															+ errResponse.data;
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
																	+ errResponse.data);
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
															self.selectedAgentLeadAppointments = self.lead.agentLeadAppointmentList;
															//self.selectedAgentLeadAppointments = $filter("filter")(self.lead.agentLeadAppointmentList, {activeInd :'Y'});	
															if(self.selectedAgentLeadAppointments.length>0){
																self.selectedAgentLeadAppointment = self.selectedAgentLeadAppointments[0];
															}
														}
													self.events = getAllEvents();
													self.genders = getAllGenders();
													self.bestTimeToCalls = getAllBestTimeToCalls();
													self.states = getAllStates();
													self.languages = getAllLanguages();
													self.statuses = getAllLeadStatuses();
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
								if(self.display){
									self.providers = getAllProviders();
									self.users = getAllAgents();
									self.events = getAllEvents();
									self.genders = getAllGenders();
									self.bestTimeToCalls = getAllBestTimeToCalls(); 
									self.states = getAllStates();
									self.languages = getAllLanguages();
									self.statuses = getAllLeadStatuses();
									self.insurances = getAllInsurances();
									self.planTypes = getAllPlanTypes();
									self.display = true;
								}else{
									var trans =  $state.go('lead.edit').transition;
									 trans.onSuccess({}, function() {   
										 self.providers = getAllProviders();
											self.users = getAllAgents();
											self.events = getAllEvents();
											self.genders = getAllGenders();
											self.bestTimeToCalls = getAllBestTimeToCalls(); 
											self.states = getAllStates();
											self.languages = getAllLanguages();
											self.statuses = getAllLeadStatuses();
											self.insurances = getAllInsurances();
											self.planTypes = getAllPlanTypes();
											self.display = true;
									 }, { priority: -1 });
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
								$state.go('lead', {}, {reload: true});
					            
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
						    	if(   $localStorage.loginUser.roleName != 'EVENT_COORDINATOR'){
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
						    	if($localStorage.loginUser.roleName != 'EVENT_COORDINATOR' && self.lead.status && self.lead.status.description == 'Converted'){
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
								var trans =  $state.go('lead.edit',params).transition;
								trans.onSuccess({}, function() { editLead(id); }, { priority: -1 });
								 
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
										console.log('Converted agent record'+JSON.stringify(self.selectedAgentLeadAppointment) );
										
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

	} 
]);