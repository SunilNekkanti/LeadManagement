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
						'EventService',
						'FileUploadService',
						'$scope',
						'$compile',
						'$filter',
						'DTOptionsBuilder',
						'DTColumnBuilder',
						function(LeadService, GenderService, StateService,
								LeadStatusService, LanguageService,
								InsuranceService, PlanTypeService, ProviderService, UserService, EventService, FileUploadService, $scope, $compile, $filter,
								DTOptionsBuilder, DTColumnBuilder) {

							var self = this;
							self.myFile;
							self.serverResponse = {};
							self.loginUser = {};
							self.lead = {};
							//self.lead.bestTimeToCall = "1988-04-21T18:25:43-05:00";
							self.leads = [];
							self.genders = [];
							self.states = [];
							self.languages = [];
							self.statuses = [];
							self.insurances = [];
							self.providers = [];
							self.planTypes = [];
							self.selectedAgentLeadAppointment = {};
							self.users = [];
							self.events = [];
							self.display = false;
							self.displayEditButton = false;
							self.submit = submit;
							self.addLead = addLead;
							self.getAllLeads = getAllLeads;
							self.createLead = createLead;
							self.updateLead = updateLead;
							self.removeLead = removeLead;
							self.editLead = editLead;
							self.dtInstance = {};
							self.leadId = null;
							self.getAllGenders = getAllGenders;
							self.getAllStates = getAllStates;
							self.getAllLeadStatuses = getAllLeadStatuses;
							self.getAllLanguages = getAllLanguages;
							self.getAllInsurances = getAllInsurances;
							self.getAllAgents = getAllAgents;
							self.getAllPlanTypes = getAllPlanTypes;
							self.getAllInsuranceTypes = getAllInsuranceTypes;
							self.getAllProviders = getAllProviders;
							self.getAllEvents = getAllEvents;
							self.uploadFile = uploadFile;
							self.addAgentLeadAppointment = addAgentLeadAppointment;
							self.reset = reset;
							loginUser : loginUser;
							self.today = today;
							self.toggleMin = toggleMin;
							self.successMessage = '';
							self.errorMessage = '';
							self.done = false;
							loginUser();
							self.onlyIntegers = /^\d+$/;
							self.onlyNumbers = /^\d+([,.]\d+)?$/;
							self.checkBoxChange = checkBoxChange;
							self.reloadData = reloadData;
							self.dtColumns = [
									DTColumnBuilder
											.newColumn('id')
											.withTitle('ACTION')
											.renderWith(
													function(data, type, full,
															meta) {
														return '<input type="checkbox" ng-model="ctrl.lead['
																+ data
																+ '].checkbox_status" ng-checked="ctrl.lead['
																+ data
																+ '].selected" ng-true-value="true" ng-false-value="false" ng-change="ctrl.checkBoxChange(ctrl.lead['
																+ data
																+ '].checkbox_status,'
																+ data
																+ ')" />';
													}).withClass("text-center"),
									DTColumnBuilder.newColumn('firstName')
											.withTitle('FIRSTNAME')
											.withOption('defaultContent', ''),
									DTColumnBuilder.newColumn('lastName')
											.withTitle('LASTNAME').withOption(
													'defaultContent', ''),
									DTColumnBuilder.newColumn(
											'genderId.description').withTitle(
											'GENDER').withOption(
													'defaultContent', ''),
									DTColumnBuilder.newColumn('dob').withTitle(
											'BIRTHDAY').withOption(
													'defaultContent', ''),
									DTColumnBuilder.newColumn('planType.description')
											.withTitle('MEDICAID/MEDICARE').withOption(
													'defaultContent', ''),
									DTColumnBuilder.newColumn(
											'language.description').withTitle(
											'LANGUAGE').withOption(
													'defaultContent', ''),
									DTColumnBuilder.newColumn(
											'status.description').withTitle(
											'STATUS').withOption(
													'defaultContent', ''),
									DTColumnBuilder.newColumn('homePhone')
											.withTitle('HOME_PHONE').withOption(
													'defaultContent', '')
											.withOption('defaultContent', ''),
									DTColumnBuilder.newColumn('mobilePhone')
											.withTitle('MOBILE_PHONE').withOption(
													'defaultContent', '')
											.withOption('defaultContent', ''),
									DTColumnBuilder.newColumn('email')
											.withTitle('EMAIL').withOption(
													'defaultContent', ''),
									DTColumnBuilder.newColumn('address1')
											.withTitle('ADDRESS1').withOption(
													'defaultContent', ''),
									DTColumnBuilder.newColumn('address2')
											.withTitle('ADDRESS2').withOption(
													'defaultContent', ''),
									DTColumnBuilder.newColumn('city')
											.withTitle('CITY').withOption(
													'defaultContent', ''),
									DTColumnBuilder.newColumn(
											'stateCode.description').withTitle(
											'STATE').withOption(
											'defaultContent', ''),
									DTColumnBuilder.newColumn('zipCode.code')
											.withTitle('ZIPCODE').withOption(
													'defaultContent', ''),
									DTColumnBuilder.newColumn('event.eventName')
											.withTitle('SOURCE').withOption(
															'width', '10%')];

							self.dtOptions = DTOptionsBuilder
									.newOptions()
									.withOption(
											'ajax',
											{
												url : 'http://localhost:8080/LeadManagement/api/lead/',
												type : 'GET'
											}).withDataProp('data').withOption('bServerSide', true)
											.withOption("bLengthChange", false)
											.withOption("bPaginate", true)
											.withOption('bProcessing', true)
											.withOption('bSaveState', true)
									        .withDisplayLength(10).withOption( 'columnDefs', [ {
												                                orderable : false,
																				className : 'select-checkbox',
																				targets : 0,
																				sortable : false,
																				aTargets : [ 0, 1 ] } ])
											.withOption('select', {
																	style : 'os',
																	selector : 'td:first-child' })
										    .withOption('createdRow', createdRow)
									        .withPaginationType('full_numbers')
									        
									        .withFnServerData(serverData);


							function createdRow(row, data, dataIndex) {
								// Recompiling so we can bind Angular directive
								// to the DT
								$compile(angular.element(row).contents())(
										$scope);
								console.log("test");
							}

							function checkBoxChange(checkStatus, leadId) {
								self.displayEditButton = checkStatus;
								self.leadId = leadId

							}

							function serverData(sSource, aoData, fnCallback) {
								
								
								// All the parameters you need is in the aoData
								// variable
								var order = aoData[2].value;
								var page = aoData[3].value / aoData[4].value;
								var length = aoData[4].value;
								var search = aoData[5].value;

								// Then just call your service to get the
								// records from server side
								LeadService
										.loadLeads(page, length, search.value, order)
										.then(
												function(result) {
													var records = {
														'recordsTotal' : result.data.totalElements,
														'recordsFiltered' : result.data.totalElements,
														'data' : result.data.content
													};
													fnCallback(records);
												});
							}

							 function reloadData() {
								var resetPaging = false;
								self.dtInstance.reloadData(callback,
										resetPaging);
							} 

							function callback(json) {
								console.log(json);
							}

							function submit() {
								console.log('Submitting');
								if(self.lead.agentLeadAppointmentList){
									self.lead.agentLeadAppointmentList = self.lead.agentLeadAppointmentList.filter(function( obj ) {
										  return obj.id !== self.selectedAgentLeadAppointment.id;
										});
									
									self.lead.agentLeadAppointmentList.push(self.selectedAgentLeadAppointment);
									console.log('self.lead.agentLeadAppointmentList '+JSON.stringify(self.lead.agentLeadAppointmentList));
								}
								
								if (self.lead.id === undefined
										|| self.lead.id === null) {
									console.log('Saving New Lead', self.lead);
									
									uploadFile();
									
									
								} else {
									updateLead(self.lead, self.lead.id);
									console.log('Lead updated with id ',
											self.lead.id);
								}
								self.displayEditButton = false;
							}
							
							
							function loginUser() {
								console.log('About to fetch loginUser');
								LeadService
										.loginUser()
										.then(
												function(loginUser) {
													console
															.log('fetched loginUser details successfully');
													
													self.loginUser = loginUser;
												},
												function(errResponse) {
													console
															.error('Error while fetching loginUser');
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
													$scope.myForm
															.$setPristine();
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
													self.display = false;
													$scope.myForm
															.$setPristine();
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
								self.genders = getAllGenders();
								self.states = getAllStates();
								self.languages = getAllLanguages();
								self.statuses = getAllLeadStatuses();
								self.insurances = getAllInsurances();
								self.planTypes = getAllPlanTypes();
								self.providers = getAllProviders();
								self.users = getAllAgents();
								self.events = getAllEvents();
									LeadService
										.getLead(id)
										.then(
												function(lead) {
													self.lead = lead;
													self.display = true;
														if(self.lead.agentLeadAppointmentList === undefined){
															self.selectedAgentLeadAppointment= {} ;
														} 			
														else{
															self.selectedAgentLeadAppointments = $filter("filter")(self.lead.agentLeadAppointmentList, {activeInd :'Y'});	
															if(self.selectedAgentLeadAppointments.length>0){
																self.selectedAgentLeadAppointment = self.selectedAgentLeadAppointments[0];
															}
														}
												
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
								self.successMessage = '';
								self.errorMessage = '';
								self.display = true;
								self.genders = getAllGenders();
								self.states = getAllStates();
								self.languages = getAllLanguages();
								self.statuses = getAllLeadStatuses();
								self.insurances = getAllInsurances();
								self.planTypes = getAllPlanTypes();
								self.providers = getAllProviders();
								self.users = getAllAgents();
								self.events = getAllEvents();
							}

							function reset() {
								self.successMessage = '';
								self.errorMessage = '';
								self.lead = {};
								$scope.myForm.$setPristine(); // reset Form
							}

							function uploadFile() {
						           var file = self.myFile;
						           var  promise = FileUploadService.uploadFileToUrl(file, self.lead);

						            promise.then(function (response) {
						            	
						            	console.log('serverResponse ============='+JSON.stringify(response));
						            	if(!self.lead.fileUpload){
						            		self.lead.fileUpload = {};
						            	}
						                self.lead.fileUpload.id = response;
						                console.log('self.serverResponse ============='+self.lead.fileUpload.id);
						                createLead(self.lead);
						            }, function () {
						                self.serverResponse = 'An error has occurred';
						            })
						        };
							
							function addAgentLeadAppointment() {
								self.lead.agentLeadAppointments.push(self.selectedAgentLeadAppointment);
							}
							function getAllLeads() {
								return LeadService.getAllLeads();
							}

							function getAllGenders() {
								return GenderService.getAllGenders();
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
							
							function today() {
								    self.bestTimeToCall = new Date();
							}
								  

							function clear() {
								self.bestTimeToCall  = null;
							}


							self.inlineOptions = {
								    customClass: getDayClass,
								    minDate: new Date(),
								    showWeeks: true
								  } ;

							self.dateOptions = {
								    dateDisabled: disabled,
								    formatYear: 'yy',
								    maxDate: new Date(2020, 5, 22),
								    minDate: new Date(),
								    startingDay: 1
								  };

								  // Disable weekend selection
							function disabled(data) {
								    var date = data.date,
								      mode = data.mode;
								    return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
								  }

						    function toggleMin() {
								    self.inlineOptions.minDate = self.inlineOptions.minDate ? null : new Date();
								    self.dateOptions.minDate = self.inlineOptions.minDate;
								  };


						   function open1() {
								    self.popup1.opened = true;
								  };

						   function   open2() {
								    self.popup2.opened = true;
								  };

						  function setDate(year, month, day) {
							  self.bestTimeToCall = new Date(year, month, day);
								  };

							self.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
							self.format = self.formats[1];
							self.altInputFormats = ['M!/d!/yyyy'];

							self.popup1 = {
								    opened: false
								  };

							self.popup2 = {
								    opened: false
								  };

								  var tomorrow = new Date();
								  tomorrow.setDate(tomorrow.getDate() + 1);
								  var afterTomorrow = new Date();
								  afterTomorrow.setDate(tomorrow.getDate() + 1);
								  self.events = [
								    {
								      date: tomorrow,
								      status: 'full'
								    },
								    {
								      date: afterTomorrow,
								      status: 'partially'
								    }
								  ];

								  function getDayClass(data) {
								    var date = data.date,
								      mode = data.mode;
								    if (mode === 'day') {
								      var dayToCheck = new Date(date).setHours(0,0,0,0);

								      for (var i = 0; i < self.events.length; i++) {
								        var currentDay = new Date(self.events[i].date).setHours(0,0,0,0);

								        if (dayToCheck === currentDay) {
								          return self.events[i].status;
								        }
								      }
								    }

								    return '';
								  }
	} 
]);