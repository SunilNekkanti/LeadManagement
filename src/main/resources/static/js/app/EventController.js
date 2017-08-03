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
						'ActivityTypeService',
						'FileUploadService',
						'EventFrequencyService',
						'EventMonthService',
						'EventWeekDayService',
						'$scope',
						'$state',
						'$compile',
						'$filter',
						'DTOptionsBuilder',
						'DTColumnBuilder',
						function(EventService, BrokerageService,  FacilityTypeService, UserService, StateService,  ActivityTypeService, FileUploadService, EventFrequencyService,EventMonthService, EventWeekDayService, $scope,$state, $compile, $filter,
								DTOptionsBuilder, DTColumnBuilder) {

							var self = this;
							self.event = {};
							self.events = [];
							self.myFiles = [];
							self.bool = false;
							self.brokerages = [];
							self.facilityTypes = [];
							self.activityTypes = [];
							self.eventFrequencies = ['DAILY','WEEKLY','MONTHLY','YEARLY'];
							self.eventIntervals = [1,2,3,4,5];
							self.eventOnDays = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31];
							self.eventOnWeeks = [{"description":"First","id":1} ,{"description":"Second","id":2},{"description":"Third","id":3},{"description":"Fourth","id":4},{"description":"Last","id":-1}];
							self.eventEndOptions = ['Never','After','On date'];
							self.eventEndOption = 'Never';
							self.event.frequency='DAILY';
							self.eventOnWeekDays = [];
							self.eventOnWeekDay = {};
							self.eventOnWeek ='First';
							self.onDayorThe = true;
							self.selectedWeekDays = [];
							self.eventEndCount = 1;
							self.eventUntil = new Date();
							self.eventMonths = [];
							self.eventMonth = {};
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
							//self.getAllEventFrequencies = getAllEventFrequencies;
							self.getAllEventMonths = getAllEventMonths;
							self.getAllEventWeekDays = getAllEventWeekDays;
							self.getEventRepEmails = getEventRepEmails;
							self.parseEventRule = parseEventRule;
							self.findWeekDaysByShortNames = findWeekDaysByShortNames;
							self.uploadFile = uploadFile;
							self.eventrrule = eventrrule;
							
							//Lead from event
							self.addLead = addLead;
							self.isOnDayorThe = isOnDayorThe;
							self.sync =sync;
							self.isChecked = isChecked;
							self.dtInstance = {};
							self.eventId = null;
							self.reset = reset;
							self.today = today;
							self.repeatDisplay = false;
							self.repeat = repeat;
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
									self.event.repeatRule = eventrrule();
									uploadFile();
									//createEvent(self.event);
								} else {
									updateEvent(self.event, self.event.id);
									console.log('Event updated with id ',
											self.event.id);
								}
								self.displayEditButton = false;
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
								//self.eventFrequencies = getAllEventFrequencies();
								self.eventMonths =getAllEventMonths();
								self.eventOnWeekDays =getAllEventWeekDays();
									EventService
										.getEvent(id)
										.then(
												function(event) {
													self.event = event;
													parseEventRule();
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
								//self.eventFrequencies = getAllEventFrequencies();
								self.eventMonths =getAllEventMonths();
								self.eventMonth = self.eventMonths[0];
								self.eventOnWeekDays =getAllEventWeekDays();
								self.eventOnWeekDay = self.eventOnWeekDays[0];
								self.eventOnWeek = self.eventOnWeeks[0];
								
							}

							function reset() {
								self.successMessage = '';
								self.errorMessage = '';
								self.event = {};
								$scope.myForm.$setPristine(); // reset Form
							}

							function uploadFile() {
						           var  promise = FileUploadService.uploadFileToUrl(self.myFiles);

						            promise.then(function (response) {
						            	if(!self.event.attachments){
						            		self.event.attachments = [];
						            	}
						                self.event.attachments = response;
						                createEvent(self.event);
						            }, function () {
						                self.serverResponse = 'An error has occurred';
						            })
						        };
						        
						    function eventrrule(){
						    	var rrule = [];
						    	
						    	var freq = 'FREQ='+self.event.frequency;
						    	 rrule.push(freq);
						    	var byDay ;

						    	if (self.event.frequency== 'WEEKLY' && self.selectedWeekDays.length >0) {
						    		self.selectedWeekDays = $filter('orderBy')(self.selectedWeekDays, 'id')
						    			byDay = ';BYDAY='+ self.selectedWeekDays.map(o => o.shortName).join();
						    		if(byDay)rrule.push(byDay);
						    	}
						    	
						    	if(self.event.frequency== 'MONTHLY' ){
						    		if(self.onDayorThe && self.onDayorThe==true){
						    			var byMonthDay =';BYMONTHDAY='+self.event.onDay;
							    		rrule.push(byMonthDay)
						    		}else{
						    			var byMonthWeekAndDay = ';BYSETPOS='+self.eventOnWeek.id+';BYMONTHDAY='+self.eventOnWeekDay.shortName
						    			rrule.push(byMonthWeekAndDay)
						    		}
						    		
						    	}
						    	
						    	if(self.event.frequency== 'YEARLY' ){
						    		if(self.onDayorThe && self.onDayorThe==true){
						    			var byMonthAndDay = ';BYMONTH='+self.event.month.id+';BYMONTHDAY='+self.event.onDay;
						    			rrule.push(byMonthAndDay)
						    			
						    		}else{
						    			var byDayWeekNoAndMonth =';BYDAY='+self.eventOnWeekDay.shortName+';BYSETPOS='+self.eventOnWeek.id+';BYMONTH='+self.event.month.id;
							    		rrule.push(byDayWeekNoAndMonth)
							    	
						    		}
						    		
						    	}
						    	
						    	var interval = ';INTERVAL='+self.event.interval
						    	if(self.event.interval) rrule.push(interval);
						    	
						    	if(self.eventEndOption  =='After'){
						    		var count = ';COUNT='+self.eventEndCount;
						    		 rrule.push(count);
						    	} else if(self.eventEndOption =='On date'){
						    		var count = ';UNTIL='+self.eventUntil;
						    		 rrule.push(count);
						    	}
						    		
						    	
						    	return rrule.join('');
						    	
						    }
						    
						    function parseEventRule(){
						    	var res = self.event.repeatRule.split(";");
						    	if(res.length>0){
						    		self.repeatDisplay =true;
						    		for(var i=0;i<res.length;i++){
						    			var ruleType = res[i].split("=");
						    			  if(ruleType.length > 1){
						    				  switch(ruleType[0])
							    				{
							    				   case "COUNT": self.eventEndCount = ruleType[1]; self.eventEndOption  ='After' ;break;
							    				   case "UNTIL": self.eventUntil = ruleType[1];  self.eventEndOption  =='On date'; break;
							    				   case 'INTERVAL': self.eventInterval = ruleType[1];  break;
							    				   case 'BYDAY':  findWeekDaysByShortNames( ruleType[1]);  break;
							    				   case 'BYSETPOS': self.eventOnWeek.id = ruleType[1]; ctrl.onDayorThe =false; break;
							    				   case 'BYMONTH': self.event.month.id = ruleType[1];  break;
							    				   case 'BYMONTHDAY': self.event.onDay = ruleType[1];   break;
							    				   case 'FREQ': self.event.frequency = ruleType[1];  break;

							    				   default: 
							    					   self.eventEndOption  =='Never';
							    				}
						    			  }
						    				

						    		}
						    	}
							  }
						    
							function getEventRepEmails(){
								
								if(self.event.representatives){
									var repEmails = self.event.representatives.map(function(agent) {return agent.email;});
									var str = repEmails.join();
									return str;
								}
								
								return "";

							}
							
							function repeat() {
								self.repeatDisplay = !self.repeatDisplay;
								if(self.repeatDisplay == true){
									self.event.interval = 1;
									self.event.onWeekDay ={"id":1} ;
									self.event.onWeek='First';
									self.event.onDay=1;
									//self.event.frequency ={};
									//self.event.frequency.id =1;
									self.event.month = {"id":1};
									self.onDayorThe =true;
								}else{
									self.event.interval = 0;
									self.event.onWeekDay ={} ;
									self.event.onWeek='';
									self.event.onDay=0;
									//self.event.frequency = {};
									self.event.month = {};
								}
							}
							
							function addAgentEventAppointment() {
								self.event.agentEventAppointments.push(self.selectedAgentEventAppointment);
							}
							
							function getAllEventWeekDays(){
								return EventWeekDayService.getAllEventWeekDays();
							}
							
							function getAllEventMonths(){
								return EventMonthService.getAllEventMonths();
							}

							
							/*function getAllEventFrequencies(){
								return EventFrequencyService.getAllEventFrequencies();
							}*/

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

							
							 
							
							function addLead( ){
								$scope.$emit('selected-event', self.event);
								$state.go('lead', {eventId: self.event.id, leadDisplay:true});
								
							}

							function today() {
								    self.event.eventDateTime = new Date();
							}
								  

							function clear() {
								self.event.eventDateTime  = null;
							}

							function isOnDayorThe(){
								if(self.onDayorThe){
									self.event.month = ctrl.event.month||{};
									self.event.month.id = self.event.month.id || 1
								}else{
									self.event.month = ctrl.event.month||{};
									self.event.month.id = self.event.month.id || 1
									
									ctrl.eventOnWeek
								}
							}
							function isChecked(id){
							      var match = false;
							      for(var i=0 ; i < self.selectedWeekDays.length; i++) 
							        if(self.selectedWeekDays[i].id == id){
							           match = true;
							          break;
							        }
							      return match;
							  };
							  
							  
							 function sync(bool, item){
								 
								    if(bool){
								      // add item
								    	self.selectedWeekDays.push(item);
								    } else {
								      // remove item
								      for(var i=0 ; i < self.selectedWeekDays.length; i++) {
								        if(self.selectedWeekDays[i].id == item.id){
								        	self.selectedWeekDays.splice(i,1);
								        }
								      }      
								    }
								  };
								  
							  function findWeekDaysByShortNames(shortNames){
								    self.selectedWeekDays = self.eventOnWeekDays.filter(function(weekday){
									    return shortNames.indexOf(weekday.shortName)> -1 ;
									});
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