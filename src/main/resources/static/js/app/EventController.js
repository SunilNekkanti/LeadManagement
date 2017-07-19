'use strict';

app
		.controller(
				'EventController',
				[
						'EventService',
						'BrokerageService',
						'FacilityTypeService',
						'UserService',
						'StateService',
						'EventTemplateService',
						'ActivityTypeService',
						'$scope',
						'$compile',
						'$filter',
						'DTOptionsBuilder',
						'DTColumnBuilder',
						function(EventService, BrokerageService,  FacilityTypeService, UserService, StateService, EventTemplateService, ActivityTypeService, $scope, $compile, $filter,
								DTOptionsBuilder, DTColumnBuilder) {

							var self = this;
							self.event = {};
							self.events = [];
							self.brokerages = [];
							self.facilityTypes = [];
							self.activityTypes = [];
							self.eventTemplates = [];
							self.users = [];
							self.display = false;
							self.states = [];
							self.displayEditButton = false;
							self.submit = submit;
							self.addEvent = addEvent;
							self.getAllEvents = getAllEvents;
							self.createEvent = createEvent;
							self.updateEvent = updateEvent;
							self.removeEvent = removeEvent;
							self.editEvent = editEvent;
							self.getAllAgents = getAllAgents;
							self.getAllStates = getAllStates;
							self.getAllBrokerages = getAllBrokerages;
							self.getAllFacilityTypes = getAllFacilityTypes;
							self.getAllActivityTypes = getAllActivityTypes;
							self.getAllEventTemplates = getAllEventTemplates;
							self.eventRepEmails = getEventRepEmails;
							self.dtInstance = {};
							self.eventId = null;
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
														return '<input type="checkbox" ng-model="ctrl.event['
																+ data
																+ '].checkbox_status" ng-checked="ctrl.event['
																+ data
																+ '].selected" ng-true-value="true" ng-false-value="false" ng-change="ctrl.checkBoxChange(ctrl.event['
																+ data
																+ '].checkbox_status,'
																+ data
																+ ')" />';
													}).withClass("text-center"),
									DTColumnBuilder.newColumn('eventName')
											.withTitle('EVENT NAME')
											.withOption('defaultContent', ''),
									DTColumnBuilder.newColumn('eventDateStartTime')
											.withTitle('EVENT STARTTIME').withOption(
													'defaultContent', ''),
									DTColumnBuilder.newColumn('eventDateEndTime')
													.withTitle('EVENT ENDTIME').withOption(
															'defaultContent', ''),
									DTColumnBuilder.newColumn(
											'brokerage.description').withTitle(
											'BROKERAGE').withOption('defaultContent', ''),
									DTColumnBuilder.newColumn('facilityType.description').withTitle(
											'FACILITY TYPE').withOption('defaultContent', ''),
									DTColumnBuilder.newColumn('activityType.description').withTitle(
											'ACTIVITY TYPE').withOption('defaultContent', ''),
									DTColumnBuilder.newColumn(
											'representatives[].username').withTitle(
											'REPRESENTATIVES').withOption('defaultContent', ''),
									DTColumnBuilder.newColumn('eventTemplate.address1')
											.withTitle('ADDRESS1').withOption(
												'defaultContent', ''),
									DTColumnBuilder.newColumn('eventTemplate.address2')
													.withTitle('ADDRESS2').withOption(
													'defaultContent', ''),
									DTColumnBuilder.newColumn('eventTemplate.city')
												.withTitle('CITY').withOption(
													'defaultContent', ''),
									DTColumnBuilder.newColumn('eventTemplate.state.shortName')
														.withTitle('STATE').withOption(
														'defaultContent', ''),
									DTColumnBuilder.newColumn('eventTemplate.zipCode.code')
														.withTitle('ZIPCODE').withOption(
														'defaultContent', ''),
									DTColumnBuilder.newColumn('eventTemplate.contactPerson')
															.withTitle('CONTACT PERSON').withOption(
															'defaultContent', ''),
									DTColumnBuilder.newColumn('eventTemplate.contactPhone')
																.withTitle('CONTACT PHONE').withOption(
																'defaultContent', '')];

							self.dtOptions = DTOptionsBuilder
									.newOptions()
									.withOption(
											'ajax',
											{
												url : 'http://localhost:8080/EventManagement/api/event/',
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

							function checkBoxChange(checkStatus, eventId) {
								self.displayEditButton = checkStatus;
								self.eventId = eventId

							}

							function serverData(sSource, aoData, fnCallback) {
								// All the parameters you need is in the aoData
								// variable

								var draw = parseInt("1");
								var order = aoData[2].value;
								var page = aoData[3].value / aoData[4].value;
								var length = aoData[4].value;
								var search = aoData[5].value;

								console.log(JSON.stringify(aoData));
								// Then just call your service to get the
								// records from server side
								EventService
										.loadEvents(page, length, search, order)
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
								if (self.event.id === undefined
										|| self.event.id === null) {
									console.log('Saving New Event', self.event);
									createEvent(self.event);
								} else {
									updateEvent(self.event, self.event.id);
									console.log('Event updated with id ',
											self.event.id);
								}
								self.displayEditButton = false;
							}

							function createEvent(event) {
								console.log('About to create event');
								console.log('event'  + JSON.stringify(event));
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
													$scope.myForm
															.$setPristine();
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
													$scope.myForm
															.$setPristine();
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
								self.brokerages = getAllBrokerages();
								self.facilityTypes = getAllFacilityTypes();
								self.activityTypes = getAllActivityTypes();
								self.eventTemplates = getAllEventTemplates();
									EventService
										.getEvent(id)
										.then(
												function(event) {
													self.event = event;
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
								self.display = true;
								self.users = getAllAgents();
								self.states = getAllStates();
								self.brokerages = getAllBrokerages();
								self.facilityTypes = getAllFacilityTypes();
								self.activityTypes = getAllActivityTypes();
								self.eventTemplates = getAllEventTemplates();
								
								console.log('self.eventTemplates'+self.eventTemplates);
							}

							function reset() {
								self.successMessage = '';
								self.errorMessage = '';
								self.event = {};
								$scope.myForm.$setPristine(); // reset Form
							}

							function getEventRepEmails(){
								alert('getEventRepEmails');
								 var obj = myForm.eventRepresentatives,
							        options = obj.options, 
							        selected = [], i, str;

							    for (i = 0; i < options.length; i++) {
							        options[i].selected && selected.push(obj[i].value);
							    }

							    str = selected.join();
							    alert('str'+str);
							    console.log('str'+str);

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
							
							function getAllBrokerages() {
								return BrokerageService
										.getAllBrokerages();
							}

							function getAllFacilityTypes() {
								return FacilityTypeService
										.getAllFacilityTypes();
							}

							function getAllActivityTypes() {
								return ActivityTypeService
										.getAllActivityTypes();
							}

							
							function getAllEventTemplates() {
								return EventTemplateService
										.getAllEventTemplates();
							}

							function today() {
								    self.event.eventDateTime = new Date();
							}
								  

							function clear() {
								self.event.eventDateTime  = null;
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
							  self.event.eventDateTime = new Date(year, month, day);
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
								  self.eventss = [
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

								      for (var i = 0; i < self.eventss.length; i++) {
								        var currentDay = new Date(self.eventss[i].date).setHours(0,0,0,0);

								        if (dayToCheck === currentDay) {
								          return self.eventss[i].status;
								        }
								      }
								    }

								    return '';
								  }
	} 
]);