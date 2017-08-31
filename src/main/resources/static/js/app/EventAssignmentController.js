'use strict';

app
		.controller(
				'EventAssignmentController',
				[
						'EventAssignmentService',
						'UserService',
						'EventService',
						'EventFrequencyService',
						'EventMonthService',
						'EventWeekDayService',
						'EventWeekNumberService',
						'$scope',
						'$rootScope',
						'$state',
						'$compile',
						'$filter',
						'DTOptionsBuilder',
						'DTColumnBuilder',
						function(EventAssignmentService,  UserService,  EventService, EventFrequencyService,EventMonthService, EventWeekDayService,EventWeekNumberService, $scope,$rootScope,$state, $compile, $filter,
								DTOptionsBuilder, DTColumnBuilder) {

							var self = this;
							self.eventAssignment = {};
							self.eventAssignments = [];
							self.events = [];
							self.bool = false;
							self.nonAdminRoles = ['AGENT','EVENT_COORDINATOR','CARE_COORDINATOR','MANAGER']
							self.eventAssignmentFrequencies = ['DAILY','WEEKLY','MONTHLY','YEARLY'];
							self.eventAssignmentIntervals = ['1','2','3','4','5'];
							self.eventAssignmentOnDays = ['1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30','31'];
							self.eventAssignmentOnWeeks = [];
							self.eventAssignmentEndOptions = ['Never','After','On date'];
							self.eventAssignmentEndOption = 'Never';
							self.eventAssignment.frequency='DAILY';
							self.eventAssignmentOnWeekDays = [];
							self.onDayorThe = true;
							self.selectedWeekDays = [];
							self.eventAssignmentEndCount = 1;
							self.eventAssignmentUntil = new Date();
							self.eventAssignmentMonths = [];
							self.eventAssignmentMonth = {};
							self.users = [];
							self.display = false;
							self.displayEditButton = false;
							self.submit = submit;
							self.addEventAssignment = addEventAssignment;
							self.eventAssignment.interval=self.eventAssignment.interval||1;
							self.getAllEventAssignments = getAllEventAssignments;
							self.createEventAssignment = createEventAssignment;
							self.updateEventAssignment = updateEventAssignment;
							self.removeEventAssignment = removeEventAssignment;
							self.editEventAssignment = editEventAssignment;
							self.updateEventTimes = updateEventTimes;
							self.getAllAgents = getAllAgents;
							self.getAllEvents = getAllEvents;
							self.getAllEventWeekNumbers = getAllEventWeekNumbers;
							self.getAllEventMonths = getAllEventMonths;
							self.getAllEventWeekDays = getAllEventWeekDays;
							self.getEventAssignmentRepEmails = getEventAssignmentRepEmails;
							self.parseEventAssignmentRule = parseEventAssignmentRule;
							self.findWeekDaysByShortNames = findWeekDaysByShortNames;
							self.findWeekDayByShortNames = findWeekDayByShortNames;
							self.eventAssignmentrrule = eventAssignmentrrule;
							self.convertToInt =convertToInt;
							self.isOnDayorThe = isOnDayorThe;
							self.sync =sync;
							self.isChecked = isChecked;
							self.dtInstance = {};
							self.eventAssignmentId = null;
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
									DTColumnBuilder.newColumn('event.eventName')
											.withTitle('EVENT NAME').renderWith(
													function(data, type, full,
															meta) {
														 return '<a href="javascript:void(0)" class="'+full.id+'" ng-click="ctrl.editEventAssignment('+full.id+')">'+data+'</a>';
													}).withClass("text-center"),
									DTColumnBuilder.newColumn('event.eventDateStartTime')
											.withTitle('STARTDATE').renderWith(function(data, type) {
												return $filter('date')(data, 'MM/dd/yyyy'); //date filter
											}).withOption(
													'defaultContent', ''),
									DTColumnBuilder.newColumn('event.eventDateStartTime')
													.withTitle('STARTTIME').renderWith(function(data, type) {
														return $filter('date')(data, 'HH:mm'); //date filter
													}).withOption(
															'defaultContent', ''),				
									DTColumnBuilder.newColumn('event.eventDateEndTime')
													.withTitle('ENDDATE').renderWith(function(data, type) {
														return $filter('date')(data, 'MM/dd/yyyy'); //date filter
													}).withOption(
															'defaultContent', ''),
									DTColumnBuilder.newColumn('event.eventDateEndTime')
																	.withTitle('ENDTIME').renderWith(function(data, type) {
																		return $filter('date')(data, 'HH:mm'); //date filter
																	}).withOption(
																			'defaultContent', ''),
								    DTColumnBuilder.newColumn('representatives[].username').withTitle(
																			'REPRESENTATIVES').withOption('defaultContent', ''),
								    DTColumnBuilder.newColumn('repeatRule').withTitle('Rule').withOption('defaultContent', '')										];

							self.dtOptions = DTOptionsBuilder
									.newOptions()
									.withOption(
											'ajax',
											{
												url : '/LeadManagement/api/eventAssignment/',
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

							function checkBoxChange(checkStatus, eventAssignmentId) {
								self.displayEditButton = checkStatus;
								self.eventAssignmentId = eventAssignmentId

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
								EventAssignmentService
								.loadEventAssignments(page, length, search.value, order)
										.then(
												function(result) {
													var records = {
															'recordsTotal' : result.data.totalElements||0,
															'recordsFiltered' : result.data.numberOfElements||0,
															'data' : result.data.content||{}
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
								self.eventAssignment.repeatRule = eventAssignmentrrule();
								console.log('self.eventAssignment.repeatRule '+self.eventAssignment.repeatRule ); 
								if (self.eventAssignment.id === undefined
										|| self.eventAssignment.id === null) {
									createEventAssignment(self.eventAssignment);
								} else {
									updateEventAssignment(self.eventAssignment, self.eventAssignment.id);
									console.log('EventAssignment updated with id ',
											self.eventAssignment.id);
								}
								self.displayEditButton = false;
							}

							function createEventAssignment(eventAssignment) {
								console.log('About to create eventAssignment');
								EventAssignmentService
										.createEventAssignment(eventAssignment)
										.then(
												function(response) {
													console
															.log('EventAssignment created successfully');
													self.successMessage = 'EventAssignment created successfully';
													self.errorMessage = '';
													self.done = true;
													self.display = false;
													self.eventAssignment = {};
													$scope.myForm
															.$setPristine();
													self.dtInstance.reloadData();
							                        self.dtInstance.rerender();
												},
												function(errResponse) {
													console
															.error('Error while creating EventAssignment');
													self.errorMessage = 'Error while creating EventAssignment: '
															+ errResponse.data.errorMessage;
													self.successMessage = '';
												});
							}

							function updateEventAssignment(eventAssignment, id) {
								console.log('About to update eventAssignment');
								EventAssignmentService
										.updateEventAssignment(eventAssignment, id)
										.then(
												function(response) {
													console
															.log('EventAssignment updated successfully');
													self.successMessage = 'EventAssignment updated successfully';
													self.errorMessage = '';
													self.done = true;
													self.display = false;
													$scope.myForm
															.$setPristine();
													self.dtInstance.reloadData();
							                        self.dtInstance.rerender();
												},
												function(errResponse) {
													console
															.error('Error while updating EventAssignment');
													self.errorMessage = 'Error while updating EventAssignment '
															+ errResponse.data;
													self.successMessage = '';
												});
							}

							function removeEventAssignment(id) {
								console.log('About to remove EventAssignment with id '
										+ id);
								EventAssignmentService
										.removeEventAssignment(id)
										.then(
												function() {
													console
															.log('EventAssignment '
																	+ id
																	+ ' removed successfully');
													self.dtInstance.reloadData();
							                        self.dtInstance.rerender();
												},
												function(errResponse) {
													console
															.error('Error while removing eventAssignment '
																	+ id
																	+ ', Error :'
																	+ errResponse.data);
												});
							}
							function editEventAssignment(id) {
								
								self.successMessage = '';
								self.errorMessage = '';
								self.users = getAllAgents();
								self.events =getAllEvents();
								self.eventAssignmentMonths =getAllEventMonths();
								self.eventAssignmentOnWeekDays =getAllEventWeekDays();
								self.eventAssignmentOnWeeks =getAllEventWeekNumbers();
									EventAssignmentService
										.getEventAssignment(id)
										.then(
												function(eventAssignment) {
													self.eventAssignment = eventAssignment;
													if(self.eventAssignment.repeatRule==='' ){
														self.repeatDisplay = false;
									                }
													parseEventAssignmentRule();
													self.display = true;
												},
												function(errResponse) {
													console
															.error('Error while removing eventAssignment '
																	+ id
																	+ ', Error :'
																	+ errResponse.data);
												});
							}

							function addEventAssignment() {
								self.successMessage = '';
								self.errorMessage = '';
								self.display = true;
								self.users = getAllAgents();
								self.events =getAllEvents();
								self.eventAssignmentMonths =getAllEventMonths();
								self.eventAssignmentMonth = self.eventAssignmentMonths[0];
								self.eventAssignmentOnWeekDays =getAllEventWeekDays();
								//self.eventAssignmentOnWeekDaysss = self.eventAssignmentOnWeekDays[0];
								self.eventAssignmentOnWeeks =getAllEventWeekNumbers();
								self.eventAssignmentOnWeek = self.eventAssignmentOnWeeks[0];
								
							}
							
							function updateEventTimes(){
								self.eventAssignment.eventDateStartTime   = self.eventAssignment.event.eventDateStartTime;
								self.eventAssignment.eventDateEndTime   = self.eventAssignment.event.eventDateEndTime;
							}

							function reset() {
								self.successMessage = '';
								self.errorMessage = '';
								self.eventAssignment = {};
								$scope.myForm.$setPristine(); // reset Form
							}
							
                            function convertToInt(id){
                                return parseInt(id, 10);
                            }
                            
						        
						    function eventAssignmentrrule(){
						    	var rrule = [];
						    	
						    	var freq = 'FREQ='+self.eventAssignment.frequency;
						    	 rrule.push(freq);
						    	var byDay ;

						    	if (self.eventAssignment.frequency== 'WEEKLY' && self.selectedWeekDays.length >0) {
						    		self.selectedWeekDays = $filter('orderBy')(self.selectedWeekDays, 'id')
						    			byDay = ';BYDAY='+ self.selectedWeekDays.map(o => o.shortName).join();
						    		if(byDay)rrule.push(byDay);
						    	}
						    	
						    	if(self.eventAssignment.frequency== 'MONTHLY' ){
						    		if(self.onDayorThe && self.onDayorThe==true){
						    			var byMonthDay =';BYMONTHDAY='+self.eventAssignment.onDay;
							    		rrule.push(byMonthDay);
						    		}else{
						    		//	self.eventAssignmentOnWeek = self.eventAssignmentOnWeek||{};
						    		//	self.eventAssignmentOnWeekDayss = self.eventAssignmentOnWeekDayss||{};
						    			var byMonthWeekAndDay = ';BYSETPOS='+self.eventAssignmentOnWeek.id+';BYDAY='+self.eventAssignmentOnWeekDay.shortName;
						    			rrule.push(byMonthWeekAndDay);
						    		}
						    		
						    	}
						    	
						    	if(self.eventAssignment.frequency== 'YEARLY' ){
						    		if(self.onDayorThe && self.onDayorThe==true){
						    			var byMonthAndDay = ';BYMONTH='+self.eventAssignmentMonthOnDay.id+';BYMONTHDAY='+self.eventAssignment.onDay;
						    			rrule.push(byMonthAndDay);
						    			
						    		}else{
						    			var byDayWeekNoAndMonth =';BYDAY='+self.eventAssignmentOnWeekDay.shortName+';BYSETPOS='+self.eventAssignmentOnWeek.id+';BYMONTH='+self.eventAssignmentMonthOnThe.id;
							    		rrule.push(byDayWeekNoAndMonth);
							    	
						    		}
						    		
						    	}
						    	
						    	var interval = ';INTERVAL='+self.eventAssignment.interval
						    	if(self.eventAssignment.interval) rrule.push(interval);
						    	
						    	if(self.eventAssignmentEndOption  =='After'){
						    		var count = ';COUNT='+self.eventAssignmentEndCount;
						    		 rrule.push(count);
						    	} else if(self.eventAssignmentEndOption =='On date'){
						    		var count = ';UNTIL='+self.eventAssignmentUntil;
						    		 rrule.push(count);
						    	}
						    		
						    	return rrule.join('');
						    	
						    }
						    
						    function parseEventAssignmentRule(){
						    	
						    	var res = self.eventAssignment.repeatRule.split(";");
						    	if(res.length>1){
						    		self.repeatDisplay =true;
						    		for(var i=0;i<res.length;i++){
						    			var ruleType = res[i].split("=");
						    			  if(ruleType.length > 1){
						    				  switch(ruleType[0])
							    				{
							    				   case "COUNT": self.eventAssignmentEndCount = ruleType[1]; self.eventAssignmentEndOption  ='After' ;break;
							    				   case "UNTIL": self.eventAssignmentUntil = ruleType[1];  self.eventAssignmentEndOption  =='On date'; break;
							    				   case 'INTERVAL': self.eventAssignmentInterval =  ruleType[1]; self.eventAssignment.interval = ruleType[1]; break;
							    				   case 'BYDAY': findWeekDayByShortNames(ruleType[1]); findWeekDaysByShortNames(ruleType[1]);   break;
							    				   case 'BYSETPOS':   self.eventAssignmentOnWeek = $filter('filter')(self.eventAssignmentOnWeeks, { id: parseInt(ruleType[1]) }, true)[0];   self.onDayorThe =false; break;
							    				   case 'BYMONTH': self.eventAssignmentMonthOnDay= $filter('filter')(self.eventAssignmentMonths, { id: parseInt(ruleType[1]) }, true)[0];  self.onDayorThe=false;  break;
							    				   case 'BYMONTHDAY':   self.eventAssignment.onDay = ruleType[1]; self.onDayorThe =true;   break;
							    				   case 'FREQ': self.eventAssignment.frequency = ruleType[1];  break;

							    				   default: 
							    					   self.eventAssignmentEndOption  =='Never';
							    				}
						    			  }
						    				

						    		}
						    	}
							  }
						    
							function getEventAssignmentRepEmails(){
								
								if(self.eventAssignment.representatives){
									var repEmails = self.eventAssignment.representatives.map(function(agent) {return agent.email;});
									var str = repEmails.join();
									return str;
								}
								
								return "";

							}
							
							function repeat() {
								self.repeatDisplay = !self.repeatDisplay;
								if(self.repeatDisplay == true){
									self.eventAssignment.interval = 1;
									self.eventAssignment.frequency = 'DAILY';
									self.eventAssignment.onWeekDay ={"id":1} ;
									self.eventAssignment.onWeek='First';
									self.eventAssignment.onDay=1;
									self.eventAssignment.month = {"id":1};
									self.onDayorThe =true;
								}else{
									self.eventAssignment.interval = 0;
									self.eventAssignment.onWeekDay ={} ;
									self.eventAssignment.onWeek='';
									self.eventAssignment.onDay=0;
									//self.eventAssignment.frequency = {};
									self.eventAssignment.month = {};
									self.eventAssignment.repeatRule = '';
								}
							}
							
							function addAgentEventAssignmentAppointment() {
								self.eventAssignment.agentEventAssignmentAppointments.push(self.selectedAgentEventAssignmentAppointment);
							}
							
							function getAllEventWeekDays(){
								return EventWeekDayService.getAllEventWeekDays();
							}
							
							function getAllEventMonths(){
								return EventMonthService.getAllEventMonths();
							}
							
							function getAllEventWeekNumbers(){
								return EventWeekNumberService.getAllEventWeekNumbers();
							}

							
							/*function getAllEventAssignmentFrequencies(){
								return EventAssignmentFrequencyService.getAllEventAssignmentFrequencies();
							}*/

							function getAllEventAssignments() {
								return EventAssignmentService.getAllEventAssignments();
							}

							function getAllAgents() {
								return UserService
										.getAllUsers();
							}
							
							function getAllEvents() {
								
								var events = EventService
								.getAllEvents();
								return events
							}


							function today() {
								    self.eventAssignment.eventAssignmentDateTime = new Date();
							}
								  

							function clear() {
								self.eventAssignment.eventAssignmentDateTime  = null;
							}

							function isOnDayorThe(){
								if(self.onDayorThe){
									self.eventAssignment.month = self.eventAssignment.month||{};
									self.eventAssignment.month.id = self.eventAssignment.month.id || 1;
								}else{
									self.eventAssignment.month = self.eventAssignment.month||{};
									self.eventAssignment.month.id = self.eventAssignment.month.id || 1;
									self.eventAssignmentOnWeek = self.eventAssignmentOnWeek|| {};
									self.eventAssignmentOnWeek.id = self.eventAssignmentOnWeek.id||1;
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
								    self.selectedWeekDays = self.eventAssignmentOnWeekDays.filter(function(weekday){
									    return shortNames.indexOf(weekday.shortName)> -1 ;
									});
							  }
							  function findWeekDayByShortNames(shortName){
								    self.eventAssignmentOnWeekDay = self.eventAssignmentOnWeekDays.filter(function(weekday){
									    return shortName.indexOf(weekday.shortName)> -1 ;
									})[0];
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
							  self.eventAssignment.eventAssignmentDateTime = new Date(year, month, day);
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
								  self.eventAssignmentss = [
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

								      for (var i = 0; i < self.eventAssignmentss.length; i++) {
								        var currentDay = new Date(self.eventAssignmentss[i].date).setHours(0,0,0,0);

								        if (dayToCheck === currentDay) {
								          return self.eventAssignmentss[i].status;
								        }
								      }
								    }

								    return '';
								  }
								  
								  
	} 
]);