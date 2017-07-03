//Code goes here
'use strict';
var app = angular.module('my-app', ['ui.bootstrap','ui.router','ngStorage','datatables','ngAnimate', 'ngSanitize']);


app.constant('urls', {
    BASE: 'http://localhost:8080/LeadManagement',
    USER_SERVICE_API : 'http://localhost:8080/LeadManagement/api/user/',
    LEAD_SERVICE_API : 'http://localhost:8080/LeadManagement/api/lead/',
    GENDER_SERVICE_API : 'http://localhost:8080/LeadManagement/api/gender/',
    ROLE_SERVICE_API : 'http://localhost:8080/LeadManagement/api/role/',
    STATE_SERVICE_API : 'http://localhost:8080/LeadManagement/api/state/',
    STATUS_SERVICE_API : 'http://localhost:8080/LeadManagement/api/leadStatus/',
    LANGUAGE_SERVICE_API : 'http://localhost:8080/LeadManagement/api/language/',
    PLANTYPE_SERVICE_API : 'http://localhost:8080/LeadManagement/api/planType/',
    INSURANCE_SERVICE_API : 'http://localhost:8080/LeadManagement/api/insurance/',
    PROVIDER_SERVICE_API : 'http://localhost:8080/LeadManagement/api/provider/',
    FACILITYTYPE_SERVICE_API : 'http://localhost:8080/LeadManagement/api/facilityType/',
    BROKERAGE_SERVICE_API : 'http://localhost:8080/LeadManagement/api/brokerage/',
    EVENT_SERVICE_API : 'http://localhost:8080/LeadManagement/api/event/',
    LOGIN_USER : 'http://localhost:8080/LeadManagement/getloginInfo',
    FILE_UPLOADER : 'http://localhost:8080/LeadManagement/api/fileUpload/fileProcessing.do' ,
    COUNTY_SERVICE_API : 'http://localhost:8080/LeadManagement/api/county/' 
});

app.controller('NavbarController',  ['$scope', '$state', function($scope, $state){
  $scope.isCollapsed = true;
  $scope.displayNavbar = true;
  $scope.callMe = function(url){
	  if(url == 'logout'){
		  $scope.displayNavbar = false;
	  }
	  $state.go(url);
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
          url: '/lead',
          templateUrl: 'partials/lead_list',
          controller:'LeadController',
          controllerAs:'ctrl',
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
              users: function ($q,  UserService) {
		          console.log('Load all users');
		          var deferred = $q.defer();
		          UserService.loadAllUsers().then(deferred.resolve, deferred.resolve);
		          console.log('deferred.promise'+deferred.promise);
		          return deferred.promise;
		      },
      		  states: function ($q,  StateService) {
		          console.log('Load all leads');
		          var deferred = $q.defer();
		          StateService.loadAllStates().then(deferred.resolve, deferred.resolve);
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


/*
A directive to enable two way binding of file field
*/
app.directive('fileModel', function ($parse) {
   return {
       restrict: 'A', //the directive can be used as an attribute only

       /*
        link is a function that defines functionality of directive
        scope: scope associated with the element
        element: element on which this directive used
        attrs: key value pair of element attributes
        */
       link: function (scope, element, attrs, ngModel) {
           var model = $parse(attrs.fileModel),
               modelSetter = model.assign; //define a setter for demoFileModel
           
           //Bind change event on the element
           element.bind('change', function (e) {
        	   
        	   alert(JSON.stringify(e));
               //Call apply on scope, it checks for value changes and reflect them on UI
               scope.$apply(function () {
                   //set the model value
                   modelSetter(scope, element[0].files[0]);
               });
               
           });
       }
   };
});