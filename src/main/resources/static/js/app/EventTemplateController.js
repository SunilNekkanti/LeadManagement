'use strict';

app
		.controller(
				'EventTemplateController',
				[
						'EventTemplateService',
						'StateService',
						'$scope',
						'$compile',
						'$filter',
						'DTOptionsBuilder',
						'DTColumnBuilder',
						function(EventTemplateService, StateService, $scope, $compile, $filter,
								DTOptionsBuilder, DTColumnBuilder) {

							var self = this;
							self.eventTemplate = {};
							self.eventTemplates = [];
							self.display = false;
							self.states = [];
							self.displayEditButton = false;
							self.submit = submit;
							self.addEventTemplate = addEventTemplate;
							self.getAllEventTemplates = getAllEventTemplates;
							self.createEventTemplate = createEventTemplate;
							self.updateEventTemplate = updateEventTemplate;
							self.removeEventTemplate = removeEventTemplate;
							self.editEventTemplate = editEventTemplate;
							self.getAllStates = getAllStates;
							self.dtInstance = {};
							self.eventTemplateId = null;
							self.reset = reset;
							self.today = today;
							self.toggleMin = toggleMin;
							self.successMessage = '';
							self.errorMessage = '';
							self.done = false;

							self.onlyIntegers = /^\d+$/;
							self.onlyNumbers = /^\d+([,.]\d+)?$/;
							self.checkBoxChange = checkBoxChange;
							self.dtColumns = [
									DTColumnBuilder
											.newColumn('id')
											.withTitle('ACTION')
											.renderWith(
													function(data, type, full,
															meta) {
														return '<input type="checkbox" ng-model="ctrl.eventTemplate['
																+ data
																+ '].checkbox_status" ng-checked="ctrl.eventTemplate['
																+ data
																+ '].selected" ng-true-value="true" ng-false-value="false" ng-change="ctrl.checkBoxChange(ctrl.eventTemplate['
																+ data
																+ '].checkbox_status,'
																+ data
																+ ')" />';
													}).withClass("text-center"),
									DTColumnBuilder.newColumn('name')
											.withTitle('EVENT NAME')
											.withOption('defaultContent', ''),
									DTColumnBuilder.newColumn('address1')
											.withTitle('ADDRESS1').withOption(
												'defaultContent', ''),
									DTColumnBuilder.newColumn('address2')
													.withTitle('ADDRESS2').withOption(
													'defaultContent', ''),
									DTColumnBuilder.newColumn('city')
												.withTitle('CITY').withOption(
													'defaultContent', ''),
									DTColumnBuilder.newColumn('state.shortName')
														.withTitle('STATE').withOption(
														'defaultContent', ''),
									DTColumnBuilder.newColumn('zipCode.code')
														.withTitle('ZIPCODE').withOption(
														'defaultContent', ''),
									DTColumnBuilder.newColumn('contactPerson')
															.withTitle('CONTACT PERSON').withOption(
															'defaultContent', ''),
									DTColumnBuilder.newColumn('contactPhone')
																.withTitle('CONTACT PHONE').withOption(
																'defaultContent', ''),
									DTColumnBuilder.newColumn('contactEmail')
																.withTitle('CONTACT EMAIL').withOption(
																'defaultContent', '')];

							self.dtOptions = DTOptionsBuilder
									.newOptions()
									.withOption(
											'ajax',
											{
												url : 'http://localhost:8080/EventTemplateManagement/api/eventTemplate/',
												type : 'GET'
											}).withDataProp('data').withOption(
											'serverSide', true).withOption(
											"bLengthChange", false).withOption(
											"bPaginate", true).withOption(
											'processing', true).withOption(
											'saveState', true)
									.withDisplayLength(10).withOption(
											'columnDefs', [ {
												orderable : false,
												className : 'select-checkbox',
												targets : 0,
												sortable : false,
												aTargets : [ 0, 1 ]
											} ]).withOption('select', {
										style : 'os',
										selector : 'td:first-child'
									}).withOption('createdRow', createdRow)
									.withPaginationType('full_numbers')
									.withFnServerData(serverData);

							self.reloadData = reloadData;

							function createdRow(row, data, dataIndex) {
								// Recompiling so we can bind Angular directive
								// to the DT
								$compile(angular.element(row).contents())(
										$scope);
								console.log("test");
							}

							function checkBoxChange(checkStatus, eventTemplateId) {
								self.displayEditButton = checkStatus;
								self.eventTemplateId = eventTemplateId

							}

							function serverData(sSource, aoData, fnCallback) {
								// All the parameters you need is in the aoData
								// variable

								var draw = parseInt("1");
								var order = aoData[2].value;
								var page = aoData[3].value / aoData[4].value;
								var length = aoData[4].value;
								var search = aoData[5].value;

								// Then just call your service to get the
								// records from server side
								EventTemplateService
										.loadEventTemplates(page, length, search, order)
										.then(
												function(result) {
													var records = {
														'draw' : (parseInt(result.data.number) + 1),
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
								if (self.eventTemplate.id === undefined
										|| self.eventTemplate.id === null) {
									console.log('Saving New EventTemplate', self.eventTemplate);
									createEventTemplate(self.eventTemplate);
								} else {
									updateEventTemplate(self.eventTemplate, self.eventTemplate.id);
									console.log('EventTemplate updated with id ',
											self.eventTemplate.id);
								}
								self.displayEditButton = false;
							}

							function createEventTemplate(eventTemplate) {
								console.log('About to create eventTemplate');
								console.log('eventTemplate'  + JSON.stringify(eventTemplate));
								EventTemplateService
										.createEventTemplate(eventTemplate)
										.then(
												function(response) {
													console
															.log('EventTemplate created successfully');
													self.successMessage = 'EventTemplate created successfully';
													self.errorMessage = '';
													self.done = true;
													self.display = false;
													self.eventTemplate = {};
													$scope.myForm
															.$setPristine();
												},
												function(errResponse) {
													console
															.error('Error while creating EventTemplate');
													self.errorMessage = 'Error while creating EventTemplate: '
															+ errResponse.data.errorMessage;
													self.successMessage = '';
												});
							}

							function updateEventTemplate(eventTemplate, id) {
								console.log('About to update eventTemplate');
								EventTemplateService
										.updateEventTemplate(eventTemplate, id)
										.then(
												function(response) {
													console
															.log('EventTemplate updated successfully');
													self.successMessage = 'EventTemplate updated successfully';
													self.errorMessage = '';
													self.done = true;
													self.display = false;
													$scope.myForm
															.$setPristine();
												},
												function(errResponse) {
													console
															.error('Error while updating EventTemplate');
													self.errorMessage = 'Error while updating EventTemplate '
															+ errResponse.data;
													self.successMessage = '';
												});
							}

							function removeEventTemplate(id) {
								console.log('About to remove EventTemplate with id '
										+ id);
								EventTemplateService
										.removeEventTemplate(id)
										.then(
												function() {
													console
															.log('EventTemplate '
																	+ id
																	+ ' removed successfully');
												},
												function(errResponse) {
													console
															.error('Error while removing eventTemplate '
																	+ id
																	+ ', Error :'
																	+ errResponse.data);
												});
							}
							function editEventTemplate(id) {
								self.successMessage = '';
								self.errorMessage = '';
								self.states = getAllStates();
									EventTemplateService
										.getEventTemplate(id)
										.then(
												function(eventTemplate) {
													self.eventTemplate = eventTemplate;
													self.display = true;
												},
												function(errResponse) {
													console
															.error('Error while removing eventTemplate '
																	+ id
																	+ ', Error :'
																	+ errResponse.data);
												});
							}

							function addEventTemplate() {
								self.successMessage = '';
								self.errorMessage = '';
								self.display = true;
								self.states = getAllStates();
							}

							function reset() {
								self.successMessage = '';
								self.errorMessage = '';
								self.eventTemplate = {};
								$scope.myForm.$setPristine(); // reset Form
							}

							
							function addAgentEventTemplateAppointment() {
								self.eventTemplate.agentEventTemplateAppointments.push(self.selectedAgentEventTemplateAppointment);
							}
							function getAllEventTemplates() {
								return EventTemplateService.getEventTemplates();
							}

							
							function getAllStates() {
								return StateService.getAllStates();
							}
							
							function today() {
								    self.eventTemplate.eventTemplateDateTime = new Date();
							}
								  

							function clear() {
								self.eventTemplate.eventTemplateDateTime  = null;
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
							  self.eventTemplate.eventTemplateDateTime = new Date(year, month, day);
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
								  self.eventTemplatess = [
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

								      for (var i = 0; i < self.eventTemplatess.length; i++) {
								        var currentDay = new Date(self.eventTemplatess[i].date).setHours(0,0,0,0);

								        if (dayToCheck === currentDay) {
								          return self.eventTemplatess[i].status;
								        }
								      }
								    }

								    return '';
								  }
	} 
]);