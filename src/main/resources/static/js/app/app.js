//Code goes here
'use strict';
var app = angular.module('my-app', ['ui.bootstrap','ui.router','ngStorage','datatables','ngAnimate', 'ngSanitize']);


app.constant('urls', {
    BASE: '/LeadManagement',
    USER_SERVICE_API : '/LeadManagement/api/user/',
    LEAD_SERVICE_API : '/LeadManagement/api/lead/',
    GENDER_SERVICE_API : '/LeadManagement/api/gender/',
    ROLE_SERVICE_API : '/LeadManagement/api/role/',
    STATE_SERVICE_API : '/LeadManagement/api/state/',
    STATUS_SERVICE_API : '/LeadManagement/api/leadStatus/',
    LANGUAGE_SERVICE_API : '/LeadManagement/api/language/',
    PLANTYPE_SERVICE_API : '/LeadManagement/api/planType/',
    INSURANCE_SERVICE_API : '/LeadManagement/api/insurance/',
    PROVIDER_SERVICE_API : '/LeadManagement/api/provider/',
    FACILITYTYPE_SERVICE_API : '/LeadManagement/api/facilityType/',
    ACTIVITYTYPE_SERVICE_API : '/LeadManagement/api/activityType/',
    BROKERAGE_SERVICE_API : '/LeadManagement/api/brokerage/',
    EVENT_SERVICE_API : '/LeadManagement/api/event/',
    LOGIN_USER : '/LeadManagement/getloginInfo',
    FILE_UPLOADER : '/LeadManagement/api/fileUpload/fileProcessing.do' ,
    COUNTY_SERVICE_API : '/LeadManagement/api/county/',
    EVENT_FREQUENCY_SERVICE_API : '/LeadManagement/api/eventFrequency/',
    EVENT_MONTH_SERVICE_API : '/LeadManagement/api/eventMonth/',
    EVENT_WEEKDAY_SERVICE_API : '/LeadManagement/api/eventWeekDay/',
    FILE_UPLOADED_SERVICE_API : '/LeadManagement/api/fileUploaded/',
    EVENT_WEEKNUMBER_SERVICE_API : '/LeadManagement/api/eventWeekNumber/'
});

app.controller('NavbarController',  ['$scope', '$state', '$stateParams', function($scope, $state, $stateParams){
	
  $scope.isCollapsed = true;
  $scope.displayNavbar = true;
  $scope.callMe = function(url,params){
	  if(url == 'logout'){
		  $scope.displayNavbar = false;
	  }
	  if( params !==''){
		  $state.go(url,params);
		  
	  }else{
		  $state.go(url);
	  }
  }
}]);



app.config(['$stateProvider', '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {
	
	
    // Now set up the states
    $stateProvider
      .state('home', {
        url: '/home',
        templateUrl: 'home'
       })
       .state('user', {
          url: '/user',
          templateUrl: 'partials/list',
          controller:'UserController',
          controllerAs:'ctrl',
          resolve: {
              users: function ($q, UserService) {
                  console.log('Load all users');
                  var deferred = $q.defer();
                  UserService.loadAllUsers().then(deferred.resolve, deferred.resolve);
                  console.log('deferred.promise'+deferred.promise);
                  return deferred.promise;
              },
              roles: function ($q, RoleService) {
                  console.log('Load all users');
                  var deferred = $q.defer();
                  RoleService.loadAllRoles().then(deferred.resolve, deferred.resolve);
                  console.log('deferred.promise'+deferred.promise);
                  return deferred.promise;
              },
		      languages: function ($q,  LanguageService) {
		          console.log('Load all languages');
		          var deferred = $q.defer();
		          LanguageService.loadAllLanguages().then(deferred.resolve, deferred.resolve);
		          console.log('deferred.promise'+deferred.promise);
		          return deferred.promise;
		      },
		      counties: function ($q,  CountyService) {
		          console.log('Load all languages');
		          var deferred = $q.defer();
		          CountyService.loadAllCounties().then(deferred.resolve, deferred.resolve);
		          console.log('deferred.promise'+deferred.promise);
		          return deferred.promise;
		      },
              brokerages: function ($q,BrokerageService) {
                  console.log('Load all brokerages');
                  var deferred = $q.defer();
                  BrokerageService.loadAllBrokerages().then(deferred.resolve, deferred.resolve);
                  
                  console.log('deferred.promise'+deferred.promise);
                  return deferred.promise;
              },
          }
      })
      .state('lead', {
          url: '/lead' ,
          templateUrl: 'partials/lead_list',
          controller:'LeadController',
          controllerAs:'ctrl',
          params: {
        	    'eventId': '0', 
        	    'leadDisplay': false, 
        	  },
          resolve: {
              leads: function ($q, LeadService) {
                  console.log('Load all leads');
                  var deferred = $q.defer();
                  LeadService.loadAllLeads().then(deferred.resolve, deferred.resolve);
                  console.log('deferred.promise'+deferred.promise);
                  return deferred.promise;
              },
      
      		  states: function ($q,  StateService) {
		          console.log('Load all leads');
		          var deferred = $q.defer();
		          StateService.loadAllStates().then(deferred.resolve, deferred.resolve);
		          console.log('deferred.promise'+deferred.promise);
		          return deferred.promise;
		      },
              
              genders: function ($q,  GenderService) {
		          console.log('Load all leads');
		          var deferred = $q.defer();
		          GenderService.loadAllGenders().then(deferred.resolve, deferred.resolve);
		          console.log('deferred.promise'+deferred.promise);
		          return deferred.promise;
		      },
		      statuses: function ($q,  LeadStatusService) {
		          console.log('Load all leadStatuses');
		          var deferred = $q.defer();
		          LeadStatusService.loadAllLeadStatuses().then(deferred.resolve, deferred.resolve);
		          console.log('deferred.promise'+deferred.promise);
		          return deferred.promise;
		      },
		      languages: function ($q,  LanguageService) {
		          console.log('Load all languages');
		          var deferred = $q.defer();
		          LanguageService.loadAllLanguages().then(deferred.resolve, deferred.resolve);
		          console.log('deferred.promise'+deferred.promise);
		          return deferred.promise;
		      },
		      insurances: function ($q,  InsuranceService) {
		          console.log('Load all leadInsurances');
		          var deferred = $q.defer();
		          InsuranceService.loadAllInsurances().then(deferred.resolve, deferred.resolve);
		          console.log('deferred.promise'+deferred.promise);
		          return deferred.promise;
		      },
		      users: function ($q,  UserService) {
		          console.log('Load all users');
		          var deferred = $q.defer();
		          UserService.loadAllUsers().then(deferred.resolve, deferred.resolve);
		          console.log('deferred.promise'+deferred.promise);
		          return deferred.promise;
		      },
		      planTypes: function ($q,  PlanTypeService) {
		          console.log('Load all users');
		          var deferred = $q.defer();
		          PlanTypeService.loadAllPlanTypes().then(deferred.resolve, deferred.resolve);
		          console.log('deferred.promise'+deferred.promise);
		          return deferred.promise;
		      },
		      providers: function ($q,  ProviderService) {
		          console.log('Load all users');
		          var deferred = $q.defer();
		          ProviderService.loadAllProviders().then(deferred.resolve, deferred.resolve);
		          console.log('deferred.promise'+deferred.promise);
		          return deferred.promise;
		      },
		      events: function ($q,  EventService) {
		          console.log('Load all events');
		          var deferred = $q.defer();
		          EventService.loadAllEvents().then(deferred.resolve, deferred.resolve);
		          console.log('deferred.promise'+deferred.promise);
		          return deferred.promise;
		      }
		      
          }
      })
      
      .state('role', {
          url: '/role',
          templateUrl: 'partials/role_list',
          controller:'RoleController',
          controllerAs:'ctrl',
          resolve: {
              users: function ($q,RoleService) {
                  console.log('Load all roles');
                  var deferred = $q.defer();
                  RoleService.loadAllRoles().then(deferred.resolve, deferred.resolve);
                  
                  console.log('deferred.promise'+deferred.promise);
                  return deferred.promise;
              }
          }
      })
      .state('facilityType', {
          url: '/facilityType',
          templateUrl: 'partials/facilityType_list',
          controller:'FacilityTypeController',
          controllerAs:'ctrl',
          resolve: {
              users: function ($q,FacilityTypeService) {
                  console.log('Load all facilityTypes');
                  var deferred = $q.defer();
                  FacilityTypeService.loadAllFacilityTypes().then(deferred.resolve, deferred.resolve);
                  
                  console.log('deferred.promise'+deferred.promise);
                  return deferred.promise;
              }
          }
      })
      .state('activityType', {
          url: '/activityType',
          templateUrl: 'partials/activityType_list',
          controller:'ActivityTypeController',
          controllerAs:'ctrl',
          resolve: {
              users: function ($q,ActivityTypeService) {
                  console.log('Load all facilityTypes');
                  var deferred = $q.defer();
                  ActivityTypeService.loadAllActivityTypes().then(deferred.resolve, deferred.resolve);
                  
                  console.log('deferred.promise'+deferred.promise);
                  return deferred.promise;
              }
          }
      })
      .state('leadStatus', {
          url: '/leadStatus',
          templateUrl: 'partials/leadStatus_list',
          controller:'LeadStatusController',
          controllerAs:'ctrl',
          resolve: {
              leadStatuses: function ($q,LeadStatusService) {
                  console.log('Load all leadStatuses');
                  var deferred = $q.defer();
                  LeadStatusService.loadAllLeadStatuses().then(deferred.resolve, deferred.resolve);
                  
                  console.log('deferred.promise'+deferred.promise);
                  return deferred.promise;
              }
          }
      })
      .state('brokerage', {
          url: '/brokerage',
          templateUrl: 'partials/brokerage_list',
          controller:'BrokerageController',
          controllerAs:'ctrl',
          resolve: {
              brokerages: function ($q,BrokerageService) {
                  console.log('Load all brokerages');
                  var deferred = $q.defer();
                  BrokerageService.loadAllBrokerages().then(deferred.resolve, deferred.resolve);
                  
                  console.log('deferred.promise'+deferred.promise);
                  return deferred.promise;
              }
          }
      })
      .state('event', {
          url: '/event',
          templateUrl: 'partials/event_list',
          controller:'EventController',
          controllerAs:'ctrl',
          resolve: {
              events: function ($q,EventService) {
                  console.log('Load all events');
                  var deferred = $q.defer();
                  EventService.loadAllEvents().then(deferred.resolve, deferred.resolve);
                  
                  console.log('deferred.promise'+deferred.promise);
                  return deferred.promise;
              },
              brokerages: function ($q,BrokerageService) {
                  console.log('Load all brokerages');
                  var deferred = $q.defer();
                  BrokerageService.loadAllBrokerages().then(deferred.resolve, deferred.resolve);
                  
                  console.log('deferred.promise'+deferred.promise);
                  return deferred.promise;
              },
              facilityTypes: function ($q,FacilityTypeService) {
                  console.log('Load all facilityTypes');
                  var deferred = $q.defer();
                  FacilityTypeService.loadAllFacilityTypes().then(deferred.resolve, deferred.resolve);
                  
                  console.log('deferred.promise'+deferred.promise);
                  return deferred.promise;
              },
              activityTypes: function ($q,ActivityTypeService) {
                  console.log('Load all facilityTypes');
                  var deferred = $q.defer();
                  ActivityTypeService.loadAllActivityTypes().then(deferred.resolve, deferred.resolve);
                  
                  console.log('deferred.promise'+deferred.promise);
                  return deferred.promise;
              },
              users: function ($q,  UserService) {
		          console.log('Load all users');
		          var deferred = $q.defer();
		          UserService.loadAllUsers().then(deferred.resolve, deferred.resolve);
		          console.log('deferred.promise'+deferred.promise);
		          return deferred.promise;
		      },
      		  states: function ($q,  StateService) {
		          console.log('Load all states');
		          var deferred = $q.defer();
		          StateService.loadAllStates().then(deferred.resolve, deferred.resolve);
		          console.log('deferred.promise'+deferred.promise);
		          return deferred.promise;
		      },
      		  eventMonths: function ($q,  EventMonthService) {
		          console.log('Load all eventMonths');
		          var deferred = $q.defer();
		          EventMonthService.loadAllEventMonths().then(deferred.resolve, deferred.resolve);
		          console.log('deferred.promise'+deferred.promise);
		          return deferred.promise;
		      },
      		  eventWeekDays: function ($q,  EventWeekDayService) {
		          console.log('Load all eventWeekDays');
		          var deferred = $q.defer();
		          EventWeekDayService.loadAllEventWeekDays().then(deferred.resolve, deferred.resolve);
		          console.log('deferred.promise'+deferred.promise);
		          return deferred.promise;
		      },
      		  eventWeekNumbers: function ($q,  EventWeekNumberService) {
		          console.log('Load all eventWeekNumbers');
		          var deferred = $q.defer();
		          EventWeekNumberService.loadAllEventWeekNumbers().then(deferred.resolve, deferred.resolve);
		          console.log('deferred.promise'+deferred.promise);
		          return deferred.promise;
		      }
          }
      })
      .state('eventTemplate', {
          url: '/eventTemplate',
          templateUrl: 'partials/eventTemplate_list',
          controller:'EventTemplateController',
          controllerAs:'ctrl',
          resolve: {
              events: function ($q,EventTemplateService) {
                  console.log('Load all eventTemplates');
                  var deferred = $q.defer();
                  EventTemplateService.loadAllEventTemplates().then(deferred.resolve, deferred.resolve);
                  
                  console.log('deferred.promise'+deferred.promise);
                  return deferred.promise;
              },
      		  states: function ($q,  StateService) {
		          console.log('Load all states');
		          var deferred = $q.defer();
		          StateService.loadAllStates().then(deferred.resolve, deferred.resolve);
		          console.log('deferred.promise'+deferred.promise);
		          return deferred.promise;
		      }
          }
      })
      .state('provider', {
          url: '/provider',
          templateUrl: 'partials/provider_list',
          controller:'ProviderController',
          controllerAs:'ctrl',
          resolve: {
        	  providers: function ($q,ProviderService) {
                  console.log('Load all providers');
                  var deferred = $q.defer();
                  ProviderService.loadAllProviders().then(deferred.resolve, deferred.resolve);
                  
                  console.log('deferred.promise'+deferred.promise);
                  return deferred.promise;
              },
               brokerages: function ($q,BrokerageService) {
                  console.log('Load all brokerages');
                  var deferred = $q.defer();
                  BrokerageService.loadAllBrokerages().then(deferred.resolve, deferred.resolve);
                  
                  console.log('deferred.promise'+deferred.promise);
                  return deferred.promise;
              },
              languages: function ($q,LanguageService) {
                  console.log('Load all languages');
                  var deferred = $q.defer();
                  LanguageService.loadLanguages().then(deferred.resolve, deferred.resolve);
                  
                  console.log('deferred.promise'+deferred.promise);
                  return deferred.promise;
              }
          }
      })
      .state('insurance', {
          url: '/insurance',
          templateUrl: 'partials/insurance_list',
          controller:'InsuranceController',
          controllerAs:'ctrl',
          resolve: {
        	  insurances: function ($q,InsuranceService) {
                  console.log('Load all insurances');
                  var deferred = $q.defer();
                  InsuranceService.loadAllInsurances().then(deferred.resolve, deferred.resolve);
                  
                  console.log('deferred.promise'+deferred.promise);
                  return deferred.promise;
              },
              planTypes: function ($q,PlanTypeService) {
                  console.log('Load all planTypes');
                  var deferred = $q.defer();
                  PlanTypeService.loadAllPlanTypes().then(deferred.resolve, deferred.resolve);
                  
                  console.log('deferred.promise'+deferred.promise);
                  return deferred.promise;
              }
          }
      })
      .state('language', {
          url: '/language',
          templateUrl: 'partials/language_list',
          controller:'LanguageController',
          controllerAs:'ctrl',
          resolve: {
              users: function ($q,LanguageService) {
                  console.log('Load all languages');
                  var deferred = $q.defer();
                  LanguageService.loadAllLanguages().then(deferred.resolve, deferred.resolve);
                  
                  console.log('deferred.promise'+deferred.promise);
                  return deferred.promise;
              }
          }
      })
      .state('logout', {
          url: '/logout',
          templateUrl: 'login'
      })
        $urlRouterProvider.otherwise('lead');
    
    function run() {
        FastClick.attach(document.body);
    }
    }

]);

// DatePicker -> NgModel
app.directive('datePicker', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, attr, ngModel) {
            $(element).datetimepicker({
                locale: 'en-us',
                format: 'MM/DD/YYYY HH:mm',
                parseInputDate: function (data) {
                    if (data instanceof Date) {
                        return moment(data);
                    } else {
                        return moment(new Date(data));
                    }
                },
                maxDate: new Date()
            });

            $(element).on("dp.change", function (e) {
                ngModel.$viewValue = e.date;
                ngModel.$commitViewValue();
            });
        }
    };
});

//DatePicker -> NgModel
app.directive('date1Picker', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, attr, ngModel) {
            $(element).datetimepicker({
                locale: 'en-us',
                format: 'MM/DD/YYYY',
                parseInputDate: function (data) {
                    if (data instanceof Date) {
                        return moment(data);
                    } else {
                        return moment(new Date(data));
                    }
                },
                maxDate: new Date()
            });

            $(element).on("dp.change", function (e) {
                ngModel.$viewValue = e.date;
                ngModel.$commitViewValue();
            });
        }
    };
});


// DatePicker Input NgModel->DatePicker
app.directive('datePickerInput', function() {
    return {
        require: 'ngModel',
        link: function (scope, element, attr, ngModel) {
            // Trigger the Input Change Event, so the Datepicker gets refreshed
            scope.$watch(attr.ngModel, function (value) {
                if (value) {
                    element.trigger("change");
                }
            });
        }
    };
});


app.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs, ngModel) {
            var model = $parse(attrs.fileModel);
            var isMultiple = attrs.multiple;
            var modelSetter = model.assign;
            element.bind('change', function () {
                var values = [];
                angular.forEach(element[0].files, function (item) {
                    var value = {
                       // File Name 
                        name: item.name,
                        //File Size 
                        size: item.size,
                        //File URL to view 
                        url: URL.createObjectURL(item),
                        // File Input Value 
                        _file: item
                    };
                    values.push(value);
                });
                scope.$apply(function () {
                    if (isMultiple) {
                        modelSetter(scope, element[0].files);
                    } else {
                        modelSetter(scope, element[0].files[0]);
                    }
                });
            });
        }
    };
}]);

/*
A directive to enable two way binding of file field
*/
/*app.directive('fileModel', function ($parse) {
   return {
       restrict: 'A', //the directive can be used as an attribute only

       
        link is a function that defines functionality of directive
        scope: scope associated with the element
        element: element on which this directive used
        attrs: key value pair of element attributes
        
       link: function (scope, element, attrs, ngModel) {
           var model = $parse(attrs.fileModel),
               modelSetter = model.assign; //define a setter for demoFileModel
           
           //Bind change event on the element
           element.bind('change', function (e) {
        	   
               //Call apply on scope, it checks for value changes and reflect them on UI
               scope.$apply(function () {
                   //set the model value
                   modelSetter(scope, element[0].files[0]);
               });
               
           });
       }
   };
});
*/




app.directive('phoneInput', function($filter, $browser) {
    return {
        require: 'ngModel',
        link: function($scope, $element, $attrs, ngModelCtrl) {
            var listener = function() {
                var value = $element.val().replace(/[^0-9]/g, '');
                $element.val($filter('tel')(value, false));
            };

            // This runs when we update the text field
            ngModelCtrl.$parsers.push(function(viewValue) {
                return viewValue.replace(/[^0-9]/g, '').slice(0,10);
            });

            // This runs when the model gets updated on the scope directly and keeps our view in sync
            ngModelCtrl.$render = function() {
                $element.val($filter('tel')(ngModelCtrl.$viewValue, false));
            };

            $element.bind('change', listener);
            $element.bind('keydown', function(event) {
                var key = event.keyCode;
                // If the keys include the CTRL, SHIFT, ALT, or META keys, or the arrow keys, do nothing.
                // This lets us support copy and paste too
                if (key == 91 || (15 < key && key < 19) || (37 <= key && key <= 40)){
                    return;
                }
                $browser.defer(listener); // Have to do this or changes don't get picked up properly
            });

            $element.bind('paste cut', function() {
                $browser.defer(listener);
            });
        }

    };
});
app.filter('tel', function () {
    return function (tel) {
        console.log(tel);
        if (!tel) { return ''; }

        var value = tel.toString().trim().replace(/^\+/, '');

        if (value.match(/[^0-9]/)) {
            return tel;
        }

        var country, city, number;

        switch (value.length) {
            case 1:
            case 2:
            case 3:
                city = value;
                break;

            default:
                city = value.slice(0, 3);
                number = value.slice(3);
        }

        if(number){
            if(number.length>3){
                number = number.slice(0, 3) + '-' + number.slice(3,7);
            }
            else{
                number = number;
            }

            return ("(" + city + ") " + number).trim();
        }
        else{
            return "(" + city;
        }

    };
});

