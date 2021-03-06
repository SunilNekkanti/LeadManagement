(function() {
  'use strict';
  var app = angular.module('my-app');

  app
    .controller(
      'EventAssignmentController', [
        'EventAssignmentService',
        'UserService',
        'EventService',
        'EventFrequencyService',
        'EventMonthService',
        'EventWeekDayService',
        'EventWeekNumberService',
        '$scope',
        '$localStorage',
        '$state',
        '$stateParams',
        '$compile',
        '$filter',
        'DTOptionsBuilder',
        'DTColumnBuilder',
        function(EventAssignmentService, UserService, EventService, EventFrequencyService, EventMonthService, EventWeekDayService, EventWeekNumberService, $scope, $localStorage, $state, $stateParams, $compile, $filter,
          DTOptionsBuilder, DTColumnBuilder) {

          var self = this;
       //   $scope.checkEventAssignmentOnWeekDays  = [];
          self.eventAssignment = {};
          self.eventAssignment.event ={};
          self.eventAssignments = [];
          self.events = [];
          self.bool = false;
          self.nonAdminRoles = ['AGENT', 'EVENT_COORDINATOR', 'CARE_COORDINATOR', 'MANAGER']
          self.eventAssignmentFrequencies = ['DAILY', 'WEEKLY', 'MONTHLY', 'YEARLY'];
          self.eventAssignmentIntervals = ['1', '2', '3', '4', '5'];
          self.eventAssignmentOnDays = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31'];
          self.eventAssignmentOnWeeks = [];
          self.eventAssignmentEndOptions = ['Never', 'After', 'On date'];
          self.eventAssignmentEndOption = 'Never';
          self.eventAssignment.frequency = 'DAILY';
          self.onDayorThe = true;
          self.selectedWeekDays = [];
          self.eventAssignmentEndCount = 1;
          self.eventAssignmentUntil = new Date();
          self.eventAssignmentMonth = {};
          self.users = [];
          self.display = $stateParams.eventAssignmentDisplay || false;
          self.displayEditButton = false;
          self.submit = submit;
          self.addEventAssignment = addEventAssignment;
          self.getAllEventAssignments = getAllEventAssignments;
          self.createEventAssignment = createEventAssignment;
          self.updateEventAssignment = updateEventAssignment;
          self.removeEventAssignment = removeEventAssignment;
          self.editEventAssignment = editEventAssignment;
          self.updateEventTimes = updateEventTimes;
          self.getAllNonAgents = getAllNonAgents;
          self.getAllEvents = getAllEvents;
          self.getAllEventWeekNumbers = getAllEventWeekNumbers;
          self.getAllEventMonths = getAllEventMonths;
          self.getAllEventWeekDays = getAllEventWeekDays;
          self.getEventAssignmentRepEmails = getEventAssignmentRepEmails;
          self.parseEventAssignmentRule = parseEventAssignmentRule;
          self.findWeekDaysByShortNames = findWeekDaysByShortNames;
          self.findWeekDayByShortNames = findWeekDayByShortNames;
          self.eventAssignmentrrule = eventAssignmentrrule;
          self.convertToInt = convertToInt;
          self.isOnDayorThe = isOnDayorThe;
          self.addLead = addLead;
          self.adminOrManager = adminOrManager;
          self.eventAssignmentEdit = eventAssignmentEdit;
          self.dtInstance = {};
          self.eventAssignmentId = null;
          self.reset = reset;
          self.repeatDisplay = false;
          self.repeat = repeat;
          self.successMessage = '';
          self.errorMessage = '';
          self.done = false;
          self.eventAssignmentOnWeekDays = getAllEventWeekDays() || [];
          self.checkEventAssignmentOnWeekDays = {'MO':false, 'TU':false,'WE':false, 'TH':false,'FR':false,'SA':false,'SU':false};
          self.eventAssignmentMonths = getAllEventMonths() || [];
          self.validEventDate = validEventDate;
          self.cancelEdit = cancelEdit;
          self.onlyIntegers = /^\d+$/;
          self.onlyNumbers = /^\d+([,.]\d+)?$/;
          self.checkBoxChange = checkBoxChange;
          self.dtColumns = [
            DTColumnBuilder.newColumn('event.eventName')
            .withTitle('EVENT NAME').renderWith(
              function(data, type, full,
                meta) {
                return '<a href="javascript:void(0)" class="' + full.id + '" ng-click="ctrl.eventAssignmentEdit(' + full.id + ')">' + data + '</a>';
              }).withClass("text-left"),
            DTColumnBuilder.newColumn('startDate')
            .withTitle('STARTDATE').withOption(
              'defaultContent', ''),
            DTColumnBuilder.newColumn('event.startTime')
            .withTitle('STARTTIME').withOption(
              'defaultContent', ''),
            DTColumnBuilder.newColumn('event.endTime')
            .withTitle('ENDTIME').withOption(
              'defaultContent', ''),
            DTColumnBuilder.newColumn('representatives[,].name').withTitle(
              'REPRESENTATIVES').withOption('defaultContent', ''),
            DTColumnBuilder.newColumn('repeatRule').withTitle('Rule').withOption('defaultContent', '')
          ];

          self.dtOptions = DTOptionsBuilder.newOptions().withBootstrap()
            .withDisplayLength(20)
            .withOption('bServerSide', true)
            .withOption("bLengthChange", false)
            .withOption("bPaginate", true)
            .withOption('bProcessing', true)
            .withOption('stateSave', true)
            .withOption('searchDelay', 3000)
            .withOption('createdRow', createdRow)
            .withPaginationType('full_numbers')

            .withFnServerData(serverData);

	    $scope.$watchCollection('ctrl.checkEventAssignmentOnWeekDays', function (newv ,oldv) {
				 self.selectedWeekDays= [];
				    angular.forEach(self.eventAssignmentOnWeekDays, function (weekDay) {
				      if (self.checkEventAssignmentOnWeekDays !== undefined && self.checkEventAssignmentOnWeekDays[weekDay.shortName]) {
				       self.selectedWeekDays.push(weekDay);
				      }
				    });
			  });
	  
	  

          function createdRow(row, data, dataIndex) {
            // Recompiling so we can bind Angular directive
            // to the DT
            $compile(angular.element(row).contents())(
              $scope);
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

            var paramMap = {};
            for (var i = 0; i < aoData.length; i++) {
              paramMap[aoData[i].name] = aoData[i].value;
            }

            var sortCol = '';
            var sortDir = '';
            // extract sort information
            if (paramMap['columns'] !== undefined && paramMap['columns'] !== null && paramMap['order'] !== undefined && paramMap['order'] !== null) {
              sortCol = paramMap['columns'][paramMap['order'][0]['column']].data;
              sortDir = paramMap['order'][0]['dir'];
            }

            // Then just call your service to get the
            // records from server side
            EventAssignmentService
              .loadEventAssignments(page, length, search.value, sortCol + ',' + sortDir)
              .then(
                function(result) {
                  var records = {
                    'recordsTotal': result.data.totalElements || 0,
                    'recordsFiltered': result.data.totalElements || 0,
                    'data': result.data.content || {}
                  };
                  fnCallback(records);
                });
          }
					
					function validEventDate(startDate, endDate) {

            var startdateTime = new Date(startDate).getTime();
            var enddateTime = new Date(endDate).getTime();
            if (startdateTime >= enddateTime) {
              self.errMessage = 'End Date should be greater than start date';
              $scope.myForm.eventDateEndTime.$setValidity("improper", false);
            } else {
              self.errMessage = '';
              $scope.myForm.eventDateEndTime.$setValidity("improper", true);
            }
            console.log('self.errMessage', self.errMessage);
          }


          function submit() {
            console.log('Submitting');
            self.eventAssignment.repeatRule = eventAssignmentrrule();
            if (!self.repeatDisplay) {
              self.eventAssignment.repeatRule = '';
            }
            if (self.eventAssignment.id === undefined ||
              self.eventAssignment.id === null) {
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
                  // $scope.myForm.$setPristine();
                  self.eventAssignment = {};
                  self.dtInstance.reloadData();
                  self.dtInstance.rerender();
                  $state.go('main.eventAssignment');
                },
                function(errResponse) {
                  console
                    .error('Error while creating EventAssignment');
                  self.errorMessage = 'Error while creating EventAssignment: ' +
                    errResponse.data.errorMessage;
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
                  $state.go('main.eventAssignment');
                },
                function(errResponse) {
                  console
                    .error('Error while updating EventAssignment');
                  self.errorMessage = 'Error while updating EventAssignment ' +
                    errResponse.data;
                  self.successMessage = '';
                });
          }

          function removeEventAssignment(id) {
            console.log('About to remove EventAssignment with id ' +
              id);
            EventAssignmentService
              .removeEventAssignment(id)
              .then(
                function() {
                  console
                    .log('EventAssignment ' +
                      id +
                      ' removed successfully');
                  self.dtInstance.reloadData();
                  self.dtInstance.rerender();
                },
                function(errResponse) {
                  console
                    .error('Error while removing eventAssignment ' +
                      id +
                      ', Error :' +
                      errResponse.data);
                });
          }

          function editEventAssignment(id) {

            self.successMessage = '';
            self.errorMessage = '';
            self.users = getAllNonAgents();
            self.events = getAllEvents();
            self.eventAssignmentMonths = getAllEventMonths();
            self.eventAssignmentOnWeekDays = getAllEventWeekDays();
            self.eventAssignmentOnWeeks = getAllEventWeekNumbers();
            EventAssignmentService
              .getEventAssignment(id)
              .then(
                function(eventAssignment) {
                  self.eventAssignment = eventAssignment;
                  if (self.eventAssignment.repeatRule === '') {
                    self.repeatDisplay = false;
                  }
                  parseEventAssignmentRule();
                  self.display = true;
                },
                function(errResponse) {
                  console
                    .error('Error while removing eventAssignment ' +
                      id +
                      ', Error :' +
                      errResponse.data);
                });
          }

          function addEventAssignment() {

            $state.go('main.eventAssignment.edit', {}, {reload:false});
            self.successMessage = '';
            self.errorMessage = '';
            self.users = getAllNonAgents();
            self.events = getAllEvents();
            self.eventAssignmentMonths = getAllEventMonths();
            self.eventAssignmentMonth = self.eventAssignmentMonths[0];
            self.eventAssignmentOnWeekDays = getAllEventWeekDays();
            // self.eventAssignmentOnWeekDaysss =
            // self.eventAssignmentOnWeekDays[0];
            self.eventAssignmentOnWeeks = getAllEventWeekNumbers();
            self.eventAssignmentOnWeek = self.eventAssignmentOnWeeks[0];
            self.display = true;

          }

          function updateEventTimes() {
            self.eventAssignment.eventDateStartTime = self.eventAssignment.event.eventDateStartTime;
            self.eventAssignment.eventDateEndTime = self.eventAssignment.event.eventDateEndTime;
          }

          function reset() {
            self.successMessage = '';
            self.errorMessage = '';
            self.eventAssignment = {};
            $scope.myForm.$setPristine(); // reset Form
          }

          function cancelEdit() {

            self.successMessage = '';
            self.errorMessage = '';
            self.eventAssignment = {};
            self.display = false;
            reset();
            $state.go('main.eventAssignment', {}, {
              reload: false,inherit:true,notify:false
            });
            
          }


          function convertToInt(id) {
            return parseInt(id, 10);
          }


          function eventAssignmentrrule() {
            var rrule = [];

            var freq = 'FREQ=' + self.eventAssignment.frequency;
            rrule.push(freq);
            var byDay;

            if (self.eventAssignment.frequency == 'WEEKLY' && self.selectedWeekDays.length > 0) {
              self.selectedWeekDays = $filter('orderBy')(self.selectedWeekDays, 'id')
              byDay = ';BYDAY=' + self.selectedWeekDays.map(o => o.shortName).join();
              if (byDay) rrule.push(byDay);
            }

            if (self.eventAssignment.frequency == 'MONTHLY') {
              if (self.onDayorThe && self.onDayorThe == true) {
                var byMonthDay = ';BYMONTHDAY=' + self.eventAssignment.onDay;
                rrule.push(byMonthDay);
              } else {
                var byMonthWeekAndDay = ';BYSETPOS=' + self.eventAssignmentOnWeek.id + ';BYDAY=' + self.eventAssignmentOnWeekDay.shortName;
                rrule.push(byMonthWeekAndDay);
              }

            }

            if (self.eventAssignment.frequency == 'YEARLY') {
              if (self.onDayorThe && self.onDayorThe == true) {
                var byMonthAndDay = ';BYMONTH=' + self.eventAssignmentMonthOnDay.id + ';BYMONTHDAY=' + self.eventAssignment.onDay;
                rrule.push(byMonthAndDay);

              } else {
                var byDayWeekNoAndMonth = ';BYDAY=' + self.eventAssignmentOnWeekDay.shortName + ';BYSETPOS=' + self.eventAssignmentOnWeek.id + ';BYMONTH=' + self.eventAssignmentMonthOnThe.id;
                rrule.push(byDayWeekNoAndMonth);

              }

            }

            var interval = ';INTERVAL=' + self.eventAssignment.interval
            if (self.eventAssignment.interval) rrule.push(interval);

            if (self.eventAssignmentEndOption == 'After') {
              var count = ';COUNT=' + self.eventAssignmentEndCount;
              rrule.push(count);
            } else if (self.eventAssignmentEndOption == 'On date') {
              
              var momentObj = moment(self.eventAssignmentUntil);
              momentObj.add(1, 'days').calendar(); 
              var momentString = momentObj.format('YYYYMMDDTHHmmss'); 
              var count = ';UNTIL=' + momentString+'Z';
              rrule.push(count);
            }

            return rrule.join('');

          }

          function parseEventAssignmentRule() {

            var res = self.eventAssignment.repeatRule.split(";");
            if (res.length > 1) {
              self.repeatDisplay = true;
              for (var i = 0; i < res.length; i++) {
                var ruleType = res[i].split("=");
                if (ruleType.length > 1) {
                  switch (ruleType[0]) {
                    case "COUNT":
                      self.eventAssignmentEndCount = ruleType[1];
                      self.eventAssignmentEndOption = 'After';
                      break;
                    case "UNTIL":
                      var momentObj = moment(ruleType[1],'YYYYMMDDTHHmmss');
              		  momentObj.subtract(1, 'days').calendar(); 
                      self.eventAssignmentUntil = momentObj.format('MM/DD/YYYY');
                      self.eventAssignmentEndOption = 'On date';
                      break;
                    case 'INTERVAL':
                      self.eventAssignmentInterval = ruleType[1];
                      self.eventAssignment.interval = ruleType[1];
                      break;
                    case 'BYDAY':
                      findWeekDayByShortNames(ruleType[1]);
                      findWeekDaysByShortNames(ruleType[1]);
                      break;
                    case 'BYSETPOS':
                      var  eventAssignmentOnWeeks = $filter('filter')(self.eventAssignmentOnWeeks, {
                        id:  ruleType[1]
                      });
                      self.eventAssignmentOnWeek = (eventAssignmentOnWeeks !== undefined)? eventAssignmentOnWeeks[0] :'';
                      self.onDayorThe = false;
                      break;
                    case 'BYMONTH':
                      self.eventAssignmentMonthOnDay = $filter('filter')(self.eventAssignmentMonths, {
                        id: parseInt(ruleType[1])
                      }, true)[0];
                      self.onDayorThe = false;
                      break;
                    case 'BYMONTHDAY':
                      self.eventAssignment.onDay = ruleType[1];
                      self.onDayorThe = true;
                      break;
                    case 'FREQ':
                      self.eventAssignment.frequency = ruleType[1];
                      break;

                    default:
                      self.eventAssignmentEndOption == 'Never';
                  }
                }


              }
            }
          }

          function getEventAssignmentRepEmails() {

            if (self.eventAssignment.representatives) {
              var repEmails = self.eventAssignment.representatives.map(function(agent) {
                return agent.email;
              });
              var str = repEmails.join();
              return str;
            }

            return "";

          }

          function repeat() {
            self.repeatDisplay = !self.repeatDisplay;
            if (self.repeatDisplay == true) {
              self.eventAssignment.interval = '1';
              self.eventAssignment.frequency = 'DAILY';
              self.eventAssignment.onWeekDay = {
                "id": 1
              };
              self.eventAssignment.onWeek = 'First';
              self.eventAssignment.onDay = 1;
              self.eventAssignment.month = {
                "id": 1
              };
              self.onDayorThe = true;
            } else {
              self.eventAssignment.interval = 0;
              self.eventAssignment.onWeekDay = {};
              self.eventAssignment.onWeek = '';
              self.eventAssignment.onDay = 0;
              // self.eventAssignment.frequency = {};
              self.eventAssignment.month = {};
              self.eventAssignment.repeatRule = '';
            }
          }

          function addAgentEventAssignmentAppointment() {
            self.eventAssignment.agentEventAssignmentAppointments.push(self.selectedAgentEventAssignmentAppointment);
          }

          function getAllEventWeekDays() {
            return EventWeekDayService.getAllEventWeekDays();
          }

          function getAllEventMonths() {
            return EventMonthService.getAllEventMonths();
          }

          function getAllEventWeekNumbers() {
            return EventWeekNumberService.getAllEventWeekNumbers();
          }


          function getAllEventAssignments() {
            return EventAssignmentService.getAllEventAssignments();
          }

          function getAllNonAgents() {
            return UserService
              .getAllUsers().filter(function(user){ return user.role.role != 'AGENT' });
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
            self.eventAssignment.eventAssignmentDateTime = null;
          }

          function isOnDayorThe() {
            if (self.onDayorThe) {
              self.eventAssignment.month = self.eventAssignment.month || {};
              self.eventAssignment.month.id = self.eventAssignment.month.id || 1;
            } else {
              self.eventAssignment.month = self.eventAssignment.month || {};
              self.eventAssignment.month.id = self.eventAssignment.month.id || 1;
              self.eventAssignmentOnWeek = self.eventAssignmentOnWeek || {};
              self.eventAssignmentOnWeek.id = self.eventAssignmentOnWeek.id || 1;
            }
          }

	

          function addLead() {
            var params = {
              "eventId": self.eventAssignment.event.id,
              "leadDisplay": true
            };
            $state.go('main.lead.edit', params);

          }

          function findWeekDaysByShortNames(shortNames) {
            self.selectedWeekDays = self.eventAssignmentOnWeekDays.filter(function(weekday) {
              return shortNames.indexOf(weekday.shortName) > -1;
            });
            self.selectedWeekDays.forEach(function(weekday){
            var index = self.eventAssignmentOnWeekDays.map(a => a.shortName).indexOf(weekday.shortName);
	            if( index > -1){
	           		 self.checkEventAssignmentOnWeekDays[weekday.shortName] = true;
	            }
            });
          }

          function findWeekDayByShortNames(shortName) {
            self.eventAssignmentOnWeekDay = self.eventAssignmentOnWeekDays.filter(function(weekday) {
              return shortName.indexOf(weekday.shortName) > -1;
            })[0];
            
            var index = self.eventAssignmentOnWeekDays.map( a => a.shortName).indexOf(self.eventAssignmentOnWeekDay.shortName);
            if( index > -1){
		        self.checkEventAssignmentOnWeekDays[self.eventAssignmentOnWeekDay.shortName] = true;
            }
          }

          function adminOrManager() {
            if ($localStorage.loginUser.roleName === 'ADMIN' || $localStorage.loginUser.roleName === 'MANAGER'  ||  $localStorage.loginUser.roleName === 'EVENT_COORDINATOR') {
              return true;
            } else {
              return false;
            }
          }

          function eventAssignmentEdit(id) {
           var  trans =  $state.go('main.eventAssignment.edit',{}, {reload:'main.eventAssignment.edit'}).transition;
		     editEventAssignment(id);
		    
		    
          }

          function setDate(year, month, day) {
            self.eventAssignment.eventAssignmentDateTime = new Date(year, month, day);
          };

		 self.refreshEvents = function (searchFilter){
          EventService.loadEvents(0, 20, searchFilter, null)
											.then(
											    function(result)   {
													      self.events = result.data.content ;
													  });
      }
      

        }

      ]);
})();
