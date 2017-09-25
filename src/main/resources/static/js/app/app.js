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
    EVENT_SERVICE_API : '/LeadManagement/api/event/',
    EVENT_ASSIGNMENT_SERVICE_API : '/LeadManagement/api/eventAssignment/',
    LOGIN_USER : '/LeadManagement/getloginInfo',
    FILE_UPLOADER : '/LeadManagement/api/fileUpload/fileProcessing.do' ,
    COUNTY_SERVICE_API : '/LeadManagement/api/county/',
    EVENT_FREQUENCY_SERVICE_API : '/LeadManagement/api/eventFrequency/',
    EVENT_MONTH_SERVICE_API : '/LeadManagement/api/eventMonth/',
    EVENT_WEEKDAY_SERVICE_API : '/LeadManagement/api/eventWeekDay/',
    FILE_UPLOADED_SERVICE_API : '/LeadManagement/api/fileUploaded/',
    EVENT_WEEKNUMBER_SERVICE_API : '/LeadManagement/api/eventWeekNumber/',
    BESTTIMETOCALL_SERVICE_API : '/LeadManagement/api/bestTimeToCall/'
});

app.controller('NavbarController',  ['$rootScope', '$scope', '$state', '$stateParams', 'LeadService', '$localStorage', function( $rootScope, $scope, $state, $stateParams, LeadService, $localStorage){

	$rootScope.displayNavbar =   false;
	  loginUser();
  
  function loginUser() {
	  if($localStorage.loginUser === undefined  ){
		     
			console.log('About to fetch loginUser');
			LeadService
					.loginUser()
					.then(
							function(loginUser) {
								console
										.log('fetched loginUser details successfully');
								$rootScope.displayNavbar =true;
							},
							function(errResponse) {
								 callMe('logout')
								 $rootScope.displayNavbar =false;
								console
										.error('Error while fetching loginUser');
								
							});
	  }else{
			 $rootScope.loginUser = $localStorage.loginUser;
			 $rootScope.displayNavbar =   true;
		}
	}
  $scope.callMe = function(url,params){
	  if(url === 'logout'){
		  $rootScope.displayNavbar = false;
		 
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
       .state('user.add', {
          url: '/user',
          templateUrl: 'partials/list',
          controller:'UserController',
          controllerAs:'ctrl',
          resolve: {
        	  roles: function ($stateParams, $q, RoleService) {
        			  console.log('Load all roles');
                      var deferred = $q.defer();
                      RoleService.loadAllRoles().then(deferred.resolve, deferred.resolve);
                      console.log('deferred.promise'+deferred.promise);
                      return deferred.promise;
                
              },
		      languages: function ($stateParams,$q,  LanguageService) {
		    		  console.log('Load all languages');
			          var deferred = $q.defer();
			          LanguageService.loadAllLanguages().then(deferred.resolve, deferred.resolve);
			          console.log('deferred.promise'+deferred.promise);
			          return deferred.promise; 
		        
		      },
		      insurances: function ($stateParams,$q,  InsuranceService) {
		    		    console.log('Load all insurances');
				          var deferred = $q.defer();
				          InsuranceService.loadAllInsurances().then(deferred.resolve, deferred.resolve);
				          console.log('deferred.promise'+deferred.promise);
				          return deferred.promise;	  
		      
		      },
		      states: function ($stateParams,$q,  StateService) {
		    		  console.log('Load all states');
			          var deferred = $q.defer();
			          StateService.loadAllStates().then(deferred.resolve, deferred.resolve);
			          console.log('deferred.promise'+deferred.promise);
			          return deferred.promise; 
		      }
          }
      })
      .state('user', {
          url: '/user/:id',
          templateUrl: 'partials/list',
          controller:'UserController',
          controllerAs:'ctrl',
          params: {
      	    'id': '', 
      	    'userDisplay': false, 
      	  },
          resolve: {
        	  roles: function ($stateParams, $q, RoleService) {
        		  if($stateParams.id !== ''){
        			  console.log('Load all users');
                      var deferred = $q.defer();
                      RoleService.loadAllRoles().then(deferred.resolve, deferred.resolve);
                      console.log('deferred.promise'+deferred.promise);
                      return deferred.promise;
        		  }
                
              },
		      languages: function ($stateParams,$q,  LanguageService) {
		    	  if($stateParams.id !== ''){
		    		  console.log('Load all languages');
			          var deferred = $q.defer();
			          LanguageService.loadAllLanguages().then(deferred.resolve, deferred.resolve);
			          console.log('deferred.promise'+deferred.promise);
			          return deferred.promise; 
		    	  }
		        
		      },
		      insurances: function ($stateParams,$q,  InsuranceService) {
		    	  if($stateParams.id !== ''){
		    		    console.log('Load all insurances');
				          var deferred = $q.defer();
				          InsuranceService.loadAllInsurances().then(deferred.resolve, deferred.resolve);
				          console.log('deferred.promise'+deferred.promise);
				          return deferred.promise;	  
		    	  }
		      
		      },
		      states: function ($stateParams,$q,  StateService) {
		    	  if($stateParams.id !== ''){
		    		  console.log('Load all states');
			          var deferred = $q.defer();
			          StateService.loadAllStates().then(deferred.resolve, deferred.resolve);
			          console.log('deferred.promise'+deferred.promise);
			          return deferred.promise; 
		    	  }
		         
		      }
          }
      })
       .state('lead', {
          url: '/lead' ,
          templateUrl: 'partials/lead_list',
          controller:'LeadController',
          controllerAs:'ctrl',
          resolve: {
		     
          }
      })
      .state('lead.edit', {
          url: '/' ,
          templateUrl: 'partials/lead_list',
          controller:'LeadController',
          controllerAs:'ctrl',
          params: {
        	    'id': '',  
        	    'eventId': '',   
        	    'leadDisplay': false
        	  },
          resolve: {
		      events: function ($stateParams, $q,  EventService) {
		    		  console.log('Load all events');
			          var deferred = $q.defer();
			          EventService.loadAllEvents().then(deferred.resolve, deferred.resolve);
			          return deferred.promise;
		      },
		      users: function ($stateParams, $q,  UserService) {
		    		  console.log('Load all users');
			          var deferred = $q.defer();
			          UserService.loadAllUsers().then(deferred.resolve, deferred.resolve);
			          return deferred.promise;
		      },
		      providers: function ($stateParams, $q,  ProviderService) {
		    		  console.log('Load all users');
			          var deferred = $q.defer();
			          ProviderService.loadAllProviders().then(deferred.resolve, deferred.resolve);
			          return deferred.promise;
		      },
      		  states: function ($stateParams, $q,  StateService) {
      				console.log('Load all leads');
      				var deferred = $q.defer();
      				StateService.loadAllStates().then(deferred.resolve, deferred.resolve);
		      },
              genders: function ($stateParams, $q,  GenderService) {
            		  console.log('Load all leads');
    		          var deferred = $q.defer();
    		          GenderService.loadAllGenders().then(deferred.resolve, deferred.resolve);
    		          return deferred.promise;
		      },
		      statuses: function ($stateParams, $q,  LeadStatusService) {
		    		  console.log('Load all leadStatuses');
			          var deferred = $q.defer();
			          LeadStatusService.loadAllLeadStatuses().then(deferred.resolve, deferred.resolve);
			          return deferred.promise;
		      },
		      languages: function ($stateParams, $q,  LanguageService) {
		    		  console.log('Load all languages');
			          var deferred = $q.defer();
			          LanguageService.loadAllLanguages().then(deferred.resolve, deferred.resolve);
			          return deferred.promise;
		      },
		      insurances: function ($stateParams, $q,  InsuranceService) {
		    		  console.log('Load all leadInsurances');
			          var deferred = $q.defer();
			          InsuranceService.loadAllInsurances().then(deferred.resolve, deferred.resolve);
			          return deferred.promise;
		      },
		      planTypes: function ($stateParams, $q,  PlanTypeService) {
		    		  console.log('Load all users');
			          var deferred = $q.defer();
			          PlanTypeService.loadAllPlanTypes().then(deferred.resolve, deferred.resolve);
			          return deferred.promise;
		      },
		      bestTimeToCalls: function ($stateParams, $q,  BestTimeToCallService) {
		    		  console.log('Load all bestTimeToCalls');
			          var deferred = $q.defer();
			          BestTimeToCallService.loadAllBestTimeToCalls().then(deferred.resolve, deferred.resolve);
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
             
          }
      })
      .state('facilityType', {
          url: '/facilityType',
          templateUrl: 'partials/facilityType_list',
          controller:'FacilityTypeController',
          controllerAs:'ctrl',
          resolve: {
        	  facilityTypes: function ($q,FacilityTypeService) {
                  console.log('Load all facilityTypes');
                  var deferred = $q.defer();
                  FacilityTypeService.loadFacilityTypes(0,10,'',null).then(deferred.resolve, deferred.resolve);
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
                  LeadStatusService.loadLeadStatuses(0,10,'',null).then(deferred.resolve, deferred.resolve);
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
          params: {
        	    'id': '', 
        	    'eventDisplay': false
        	  },
          resolve: {
        	  facilityTypes: function ($q,FacilityTypeService) {
                  console.log('Load all facilityTypes');
                  var deferred = $q.defer();
                  FacilityTypeService.loadAllFacilityTypes().then(deferred.resolve, deferred.resolve);
                  console.log('deferred.promise'+deferred.promise);
                  return deferred.promise;
              }
          }
      })
       .state('event.edit', {
          url: '/',
          templateUrl: 'partials/event_list',
          controller:'EventController',
          controllerAs:'ctrl',
          resolve: {
              facilityTypes: function ($q,FacilityTypeService) {
                  console.log('Load all facilityTypes');
                  var deferred = $q.defer();
                  FacilityTypeService.loadAllFacilityTypes().then(deferred.resolve, deferred.resolve);
                  console.log('deferred.promise'+deferred.promise);
                  return deferred.promise;
              }
          }
      })
       .state('eventAssignment', {
          url: '/eventAssignment' ,
          templateUrl: 'partials/eventAssignment_list',
          controller:'EventAssignmentController',
          controllerAs:'ctrl',
          params: {
        	    'id': '', 
        	    'eventAssignmentDisplay': false
        	  }
      })
      .state('eventAssignment.edit', {
          url: '/' ,
          templateUrl: 'partials/eventAssignment_list',
          controller:'EventAssignmentController',
          controllerAs:'ctrl',
          params: {
        	    'eventId': '', 
        	    'eventAssignmentDisplay': false, 
        	  },
          resolve: {
        	  events: function ($q,  EventService) {
		          console.log('Load all events');
		          var deferred = $q.defer();
		          EventService.loadAllEvents().then(deferred.resolve, deferred.resolve);
		          console.log('deferred.promise'+deferred.promise);
		          return deferred.promise;
		      },
		      users: function ($q,  UserService) {
		          console.log('Load all users');
		          var deferred = $q.defer();
		          UserService.loadAllUsers().then(deferred.resolve, deferred.resolve);
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
              languages: function ($q,LanguageService) {
                  console.log('Load all languages');
                  var deferred = $q.defer();
                  LanguageService.loadLanguages().then(deferred.resolve, deferred.resolve);
                  
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
      .state('insurance', {
          url: '/insurance',
          templateUrl: 'partials/insurance_list',
          controller:'InsuranceController',
          controllerAs:'ctrl',
          resolve: {
        	  insurances: function ($q,InsuranceService) {
                  console.log('Load all insurances');
                  var deferred = $q.defer();
                  InsuranceService.loadInsurances(0,10,'',null).then(deferred.resolve, deferred.resolve);
                  
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
          }
      })
      .state('logout', {
          url: '/logout',
          templateUrl: 'logout'
      })
       .state('login', {
          url: '/login',
          templateUrl: 'login'
      })
        $urlRouterProvider.otherwise('login');
    
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
                minDate:   new Date(new Date().setDate(new Date().getDate()-7)),
                maxDate: new Date(new Date().setFullYear(new Date().getFullYear() + 1))
            });

            $(element).on("dp.change", function (e) {
                ngModel.$viewValue = moment(e.date).format('MM/DD/YYYY HH:mm')  ;
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
                ngModel.$viewValue = moment(e.date).format('MM/DD/YYYY');
                ngModel.$commitViewValue();
            });
        }
    };
});



app.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        require: "ngModel",
        link: function (scope, element, attrs, ngModel) {
        	
            var model = $parse(attrs.fileModel);
            var isMultiple = attrs.multiple;
            var modelSetter = model.assign;
            var dirty = false;
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
                    scope.myForm.$setDirty();  
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

app.filter('nonAdminUsersFilter', function () {
  return function (input, arrayOfString) {
	  var out =[];
      input.filter( function( user ) {
    		    if( arrayOfString.indexOf( user.role.role ) >-1){
    		    	
    		    	out.push(user);
    		    }
    });
      return out;
  }
});

app.filter('filterFromArray', function() {
	  return function(input, arrayToMatch) {
		    input = input || [];
		    arrayToMatch= arrayToMatch|| [];
		    var l = input.length,
		        k = arrayToMatch.length,
		        out = [], i = 0,
		        map = {};

		    for (; i < k; i++) {
		      map[arrayToMatch[k]] = true;
		    }

		    for (i = 0; i < l; i++) {
		      if(map[input[i].name]){
		          out.push(input[i]);
		      }
		    }

		    return out;
		  };
		});

app.filter('tel', function () {
    return function (tel) {
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

