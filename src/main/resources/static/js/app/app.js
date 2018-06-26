(function() {
  'use strict';
  var app = angular.module('my-app', ['ngCookies','moment-picker','datatables','ui.bootstrap', 'datatables.light-columnfilter', 'datatables.fixedheader',  'datatables.bootstrap', 'datatables.colreorder', 'datatables.fixedheader', 'datatables.buttons', 'datatables.fixedcolumns', 'ui.router', 'ngStorage', 'ngAnimate', 'ngSanitize', 'btorfs.multiselect', 'oc.lazyLoad', 'ui.select', 'chart.js']);
  app.config(['ChartJsProvider', function(ChartJsProvider) {
    // Configure all charts
    ChartJsProvider.setOptions({
      chartColors: ['#46BFBD', '#00ADF9', '#DCDCDC', '#FDB45C', '#FF5252', '#949FB1', '#4D5360', '#FF8A80']
    });
    // Configure all line charts
    ChartJsProvider.setOptions('line', {
      showLines: true
    });
  }]);
  
  app.config(['momentPickerProvider', function (momentPickerProvider) {
        momentPickerProvider.options({
            /* Picker properties */
            locale:        'en',
            format:        'L LTS',
            minView:       'decade',
            maxView:       'minute',
            startView:     'year',
            autoclose:     true,
            today:         false,
            keyboard:      false,
            
            /* Extra: Views properties */
            leftArrow:     '&larr;',
            rightArrow:    '&rarr;',
            yearsFormat:   'YYYY',
            monthsFormat:  'MMM',
            daysFormat:    'D',
            hoursFormat:   'HH:[00]',
            minutesFormat: moment.localeData().longDateFormat('LT').replace(/[aA]/, ''),
            secondsFormat: 'ss',
            minutesStep:   30,
            secondsStep:   1
        });
    }]);

  app.constant('urls', {
    BASE: '/LeadManagement/',
    USER_SERVICE_API: '/LeadManagement/api/user/',
    LEAD_SERVICE_API: '/LeadManagement/api/lead/',
    GENDER_SERVICE_API: '/LeadManagement/api/gender/',
    ROLE_SERVICE_API: '/LeadManagement/api/role/',
    STATE_SERVICE_API: '/LeadManagement/api/state/',
    STATUS_SERVICE_API: '/LeadManagement/api/leadStatus/',
    STATUS_DETAIL_SERVICE_API: '/LeadManagement/api/leadStatusDetail/',
    LANGUAGE_SERVICE_API: '/LeadManagement/api/language/',
    PLANTYPE_SERVICE_API: '/LeadManagement/api/planType/',
    INSURANCE_SERVICE_API: '/LeadManagement/api/insurance/',
    PROVIDER_SERVICE_API: '/LeadManagement/api/provider/',
    FACILITYTYPE_SERVICE_API: '/LeadManagement/api/facilityType/',
    EVENT_SERVICE_API: '/LeadManagement/api/event/',
    EVENT_ASSIGNMENT_SERVICE_API: '/LeadManagement/api/eventAssignment/',
    LOGIN_USER: '/LeadManagement/api/getloginInfo',
    FILE_UPLOADER: '/LeadManagement/api/fileUpload/fileProcessing.do',
    COUNTY_SERVICE_API: '/LeadManagement/api/county/',
    EVENT_FREQUENCY_SERVICE_API: '/LeadManagement/api/eventFrequency/',
    EVENT_MONTH_SERVICE_API: '/LeadManagement/api/eventMonth/',
    EVENT_WEEKDAY_SERVICE_API: '/LeadManagement/api/eventWeekDay/',
    FILE_UPLOADED_SERVICE_API: '/LeadManagement/api/fileUploaded/',
    EVENT_WEEKNUMBER_SERVICE_API: '/LeadManagement/api/eventWeekNumber/',
    BESTTIMETOCALL_SERVICE_API: '/LeadManagement/api/bestTimeToCall/',
    STATUS_REPORT_SERVICE_API: '/LeadManagement/api/statusReport/'
  });

  app.run(function($rootScope, $state) {
    $rootScope.$state = $state;
    var lastDigestRun = new Date();

    setInterval(function() {
      var now = Date.now();
      if (now - lastDigestRun > 15 * 60 * 1000) {
        $state.go('logout');
        $rootScope.displayNavbar = false;
        $rootScope.loginUser = undefined;
        $localStorage.loginUser
        if (localStorage.getItem("loginUser")  === undefined || localStorage.getItem("loginUser") != null) {
          localStorage.removeItem("loginUser") ;
        }
      }
    }, 2 * 60 * 1000);

    $rootScope.$watch(function() {
      lastDigestRun = new Date();
    });
  });

  app.controller('NavbarController', ['$rootScope', '$scope', '$state', '$stateParams', 'UserService', '$localStorage', '$window', '$timeout', '$cookies', function($rootScope, $scope, $state, $stateParams, UserService, $localStorage, $window, $timeout,$cookies) {
    $rootScope.displayNavbar = false;
    loginUser();

    function loginUser() {
      if ($localStorage.loginUser === undefined) {

        console.log('About to fetch loginUser');
        UserService.loginUser()
          .then(
            function(loginUser) {
              console
                .log('fetched loginUser details successfully');
              $rootScope.displayNavbar = true;
              $rootScope.loginUser = $localStorage.loginUser;
              $cookies.put('myFavorite', 'oatmeal');
            },
            function(errResponse) {
              callMe('logout')
              $rootScope.displayNavbar = false;
              console
                .error('Error while fetching loginUser');

            });
      } else {
        $rootScope.loginUser = $localStorage.loginUser;
        $rootScope.displayNavbar = true;
      }
    }

    $scope.callMe = function(url, params) {

      if (url === 'logout') {
        $rootScope.displayNavbar = false;
        $rootScope.loginUser = undefined;
        $window.localStorage.clear();
      }
      if (params !== '') {
        $state.go(url, params, {
          reload: true, inherit: false,notify: true
        });

      } else {
        $state.go(url, {}, {
          reload: true, inherit: false,notify: true
        });
      }
    }
  }]);



  app.config(['$ocLazyLoadProvider', '$stateProvider', '$urlRouterProvider',
    function($ocLazyLoadProvider, $stateProvider, $urlRouterProvider) {

      $urlRouterProvider.otherwise('login');

      $ocLazyLoadProvider.config({
        'debug': false,
        'events': true,
        'modules': [{
          serie: true,
          name: 'main',
          files: ['js/app/RoleService.js', 'js/app/UserService.js', 'js/app/CountyService.js', 'js/app/GenderService.js', 'js/app/InsuranceService.js', 'js/app/StateService.js', 'js/app/LanguageService.js', 'js/app/PlanTypeService.js', 'js/app/BestTimeToCallService.js']
        }, {
          serie: true,
          name: 'main.user',
          files: ['js/app/UserController.js']
        }, {
          serie: true,
          name: 'main.lead',
          files: ['js/app/LeadService.js', 'js/app/LeadStatusService.js', 'js/app/LeadStatusDetailService.js', 'js/app/ProviderService.js', 'js/app/EventService.js', 'js/app/FileUploadService.js', 'js/app/LeadController.js']
        }, {
          serie: true,
          name: 'main.facilityType',
          files: ['js/app/FacilityTypeService.js', 'js/app/FacilityTypeController.js']
        }, {
          serie: true,
          name: 'main.leadStatus',
          files: ['js/app/LeadStatusService.js', 'js/app/LeadStatusController.js']
        }, {
          serie: true,
          name: 'main.leadStatusDetail',
          files: ['js/app/LeadStatusDetailService.js', 'js/app/LeadStatusService.js', 'js/app/LeadStatusDetailController.js']
        }, {
          serie: true,
          name: 'main.role',
          files: ['js/app/RoleService.js', 'js/app/RoleController.js']
        }, {
          name: 'main.event',
          serie: true,
          files: ['js/app/EventService.js', 'js/app/FacilityTypeService.js', 'js/app/FileUploadService.js', 'js/app/EventController.js']
        }, {
          name: 'main.eventAssignment',
          serie: true,
          files: ['js/app/EventAssignmentService.js', 'js/app/EventService.js', 'js/app/EventFrequencyService.js', 'js/app/EventMonthService.js', 'js/app/EventWeekDayService.js', 'js/app/EventWeekNumberService.js', 'js/app/EventWeekNumberService.js', 'js/app/EventAssignmentController.js']
        }, {
          name: 'main.statusReport',
          serie: true,
          files: ['js/app/EventService.js', 'js/app/StatusReportService.js', 'js/app/LeadStatusService.js', 'js/app/StatusReportController.js']
        }]

      });

      // Now set up the states
      $stateProvider
        .state('login', {
          url: '/',
          templateUrl: 'login'
        })
        .state('main', {
          template: '<ui-view> </ui-view>',
          // Make this state abstract so it can never be
          // loaded directly
          abstract: true,

          // Centralize the config resolution
          resolve: {
            loadMyService: ['$ocLazyLoad', function($ocLazyLoad) {
              return $ocLazyLoad.load('main');
            }],
            roles: ['loadMyService', '$q', '$injector', function(loadMyService, $q, $injector) {
              var RoleService = $injector.get("RoleService");
              console.log('Load all  roles');
              var deferred = $q.defer();
              RoleService.loadAllRoles().then(deferred.resolve, deferred.resolve);
              return deferred.promise;
            }],
            genders: ['loadMyService', '$q', '$injector', function(loadMyService, $q, $injector) {
              console.log('Load all genders');
              var GenderService = $injector.get("GenderService");
              var deferred = $q.defer();
              GenderService.loadAllGenders().then(deferred.resolve, deferred.resolve);
              return deferred.promise;
            }],
            states: ['loadMyService', '$q', '$injector', function(loadMyService, $q, $injector) {
              console.log('Load all states');
              var StateService = $injector.get("StateService");
              var deferred = $q.defer();
              StateService.loadAllStates().then(deferred.resolve, deferred.resolve);
              return deferred.promise;
            }],
            languages: ['loadMyService', '$q', '$injector', function(loadMyService, $q, $injector) {
              console.log('Load all languages');
              var LanguageService = $injector.get("LanguageService");
              var deferred = $q.defer();
              LanguageService.loadAllLanguages().then(deferred.resolve, deferred.resolve);
              return deferred.promise;
            }],
            users: ['loadMyService', '$q', '$injector', function(loadMyService, $q, $injector) {
              console.log('Load all users');
              var UserService = $injector.get("UserService");
              var deferred = $q.defer();
              UserService.loadAllUsers().then(deferred.resolve, deferred.resolve);
              return deferred.promise;
            }],
            planTypes: ['loadMyService', '$q', '$injector', function(loadMyService, $q, $injector) {
              console.log('Load all users');
              var PlanTypeService = $injector.get("PlanTypeService");
              var deferred = $q.defer();
              PlanTypeService.loadAllPlanTypes().then(deferred.resolve, deferred.resolve);
              return deferred.promise;
            }],
            languages: ['loadMyService', '$q', '$injector', function(loadMyService, $q, $injector) {
              console.log('Load all languages');
              var LanguageService = $injector.get("LanguageService");
              var deferred = $q.defer();
              LanguageService.loadAllLanguages().then(deferred.resolve, deferred.resolve);
              return deferred.promise;
            }],
            insurances: ['loadMyService', '$q', '$injector', function(loadMyService, $q, $injector) {
              console.log('Load all languages');
              var InsuranceService = $injector.get("InsuranceService");
              var deferred = $q.defer();
              InsuranceService.loadAllInsurances().then(deferred.resolve, deferred.resolve);
              return deferred.promise;
            }],
            bestTimeToCalls: ['loadMyService', '$q', '$injector', function(loadMyService, $q, $injector) {
              var BestTimeToCallService = $injector.get("BestTimeToCallService");
              console.log('Load all bestTimeToCalls');
              var deferred = $q.defer();
              BestTimeToCallService.loadAllBestTimeToCalls().then(deferred.resolve, deferred.resolve);
              return deferred.promise;
            }]
          }
        })
        .state('main.home', {
          url: '/home',
          cache: false,
          templateUrl: 'home'
        })
        .state('main.user', {
          url: '/user',
          cache: false,
          templateUrl: 'partials/list',
          controller: 'UserController',
          controllerAs: 'ctrl',
          resolve: {
            loadMyService: ['$ocLazyLoad', function($ocLazyLoad) {
              return $ocLazyLoad.load('main.user');
            }]
          }
        })
        .state('main.user.edit', {
          url: '/',
          cache: false,
          templateUrl: 'partials/list',
          controller: 'UserController',
          controllerAs: 'ctrl',
          params: {
            'id': '',
            'userDisplay': false,
          },
          resolve: {
          }
        })
        .state('main.lead', {
          url: '/lead',
          cache: false,
          templateUrl: 'partials/lead_list',
          controller: 'LeadController',
          controllerAs: 'ctrl',
          params: {
            'id': '',
            'leadDisplay': false
          },
          resolve: {
            loadMyService: ['$ocLazyLoad', function($ocLazyLoad) {
              return $ocLazyLoad.load('main.lead');
            }],
            statuses: ['loadMyService', '$q', '$injector', function(loadMyService, $q, $injector) {
              var LeadStatusService = $injector.get("LeadStatusService");
              console.log('Load all leadStatuses');
              var deferred = $q.defer();
              LeadStatusService.loadAllLeadStatuses().then(deferred.resolve, deferred.resolve);
              return deferred.promise;
            }],
            leadStatusDetails: ['loadMyService', '$q', '$injector', function(loadMyService, $q, $injector) {
              var LeadStatusDetailService = $injector.get("LeadStatusDetailService");
              console.log('Load all leadStatusDetails');
              var deferred = $q.defer();
              LeadStatusDetailService.loadAllLeadStatusDetails().then(deferred.resolve, deferred.resolve);
              console.log('deferred.promise' + deferred.promise);
              return deferred.promise;
            }]
          }
        })
        .state('main.lead.edit', {
          url: '/',
          cache: false,
          templateUrl: 'partials/lead_list',
          controller: 'LeadController',
          controllerAs: 'ctrl',
          params: {
            'eventId': '',
            'leadDisplay': true
          },
          resolve: {
              events: ['loadMyService', '$q', '$injector', function(loadMyService, $q, $injector) {
              var EventService = $injector.get("EventService");
              console.log('Load all  events');
              var deferred = $q.defer();
              EventService.loadAllEvents().then(deferred.resolve, deferred.resolve);
              return deferred.promise;
            }],
            providers: ['loadMyService', '$q', '$injector', function(loadMyService, $q, $injector) {
              var ProviderService = $injector.get("ProviderService");
              console.log('Load all providers');
              var deferred = $q.defer();
              ProviderService.loadAllProviders().then(deferred.resolve, deferred.resolve);
              return deferred.promise;
            }]
          }
        })
        .state('main.role', {
          url: '/role',
          cache: false,
          templateUrl: 'partials/role_list',
          controller: 'RoleController',
          controllerAs: 'ctrl',
          resolve: {
            loadMyService: ['$ocLazyLoad', function($ocLazyLoad) {
              return $ocLazyLoad.load('main.role');
            }]
          }
        })
        .state('main.facilityType', {
          url: '/facilityType',
          cache: false,
          templateUrl: 'partials/facilityType_list',
          controller: 'FacilityTypeController',
          controllerAs: 'ctrl',
          resolve: {
            loadMyService: ['$ocLazyLoad', function($ocLazyLoad) {
              return $ocLazyLoad.load('main.facilityType');
            }],
            facilityTypes: ['loadMyService', '$q', '$injector', function(loadMyService, $q, $injector) {
              var FacilityTypeService = $injector.get("FacilityTypeService");
              console.log('Load all  facilityTypes');
              var deferred = $q.defer();
              FacilityTypeService.loadAllFacilityTypes().then(deferred.resolve, deferred.resolve);
              return deferred.promise;
            }]

          }
        })
        .state('main.leadStatus', {
          url: '/leadStatus',
          cache: false,
          templateUrl: 'partials/leadStatus_list',
          controller: 'LeadStatusController',
          controllerAs: 'ctrl',
          resolve: {
            loadMyService: ['$ocLazyLoad', function($ocLazyLoad) {
              return $ocLazyLoad.load('main.leadStatus');
            }],
            leadStatuses: ['loadMyService', '$q', '$injector', function(loadMyService, $q, $injector) {
              var LeadStatusService = $injector.get("LeadStatusService");
              console.log('Load all  leadStatuses');
              var deferred = $q.defer();
              LeadStatusService.loadAllLeadStatuses().then(deferred.resolve, deferred.resolve);
              return deferred.promise;
            }]
          }
        })
        .state('main.leadStatusDetail', {
          url: '/leadStatusDetail',
          cache: false,
          templateUrl: 'partials/leadStatusDetail_list',
          controller: 'LeadStatusDetailController',
          controllerAs: 'ctrl',
          resolve: {
            loadMyService: ['$ocLazyLoad', function($ocLazyLoad) {
              return $ocLazyLoad.load('main.leadStatusDetail');
            }],
            leadStatuses: ['loadMyService', '$q', '$injector', function(loadMyService, $q, $injector) {
              var LeadStatusService = $injector.get("LeadStatusService");
              console.log('Load all  leadStatuses');
              var deferred = $q.defer();
              LeadStatusService.loadAllLeadStatuses().then(deferred.resolve, deferred.resolve);
              return deferred.promise;
            }],
            leadStatusDetails: ['loadMyService', '$q', '$injector', function(loadMyService, $q, $injector) {
              var LeadStatusDetailService = $injector.get("LeadStatusDetailService");
              console.log('Load all  leadStatusDetails');
              var deferred = $q.defer();
              LeadStatusDetailService.loadAllLeadStatusDetails().then(deferred.resolve, deferred.resolve);
              return deferred.promise;
            }]
          }
        })
        .state('main.event', {
          url: '/event',
          cache: false,
          templateUrl: 'partials/event_list',
          controller: 'EventController',
          controllerAs: 'ctrl',
          params: {
            'id': '',
            'eventDisplay': false
          },
          resolve: {
            loadMyService: ['$ocLazyLoad', function($ocLazyLoad) {
              return $ocLazyLoad.load('main.event');
            }],
            facilityTypes: ['loadMyService', '$q', '$injector', function(loadMyService, $q, $injector) {
              var FacilityTypeService = $injector.get("FacilityTypeService");
              console.log('Load all facilityTypes');
              var deferred = $q.defer();
              FacilityTypeService.loadAllFacilityTypes().then(deferred.resolve, deferred.resolve);
              console.log('deferred.promise' + deferred.promise);
              return deferred.promise;
            }]
          }
        })
        .state('main.event.edit', {
          url: '/',
          cache: false,
          templateUrl: 'partials/event_list',
          controller: 'EventController',
          controllerAs: 'ctrl',
          resolve: {}
        })
        .state('main.eventAssignment', {
          url: '/eventAssignment',
          cache: false,
          templateUrl: 'partials/eventAssignment_list',
          controller: 'EventAssignmentController',
          controllerAs: 'ctrl',
          params: {
            'id': '',
            'eventAssignmentDisplay': false
          },
          resolve: {
            loadMyService: ['$ocLazyLoad', function($ocLazyLoad) {
              return $ocLazyLoad.load('main.eventAssignment');
            }]
          }
        })
        .state('main.eventAssignment.edit', {
          url: '/',
          cache: false,
          templateUrl: 'partials/eventAssignment_list',
          controller: 'EventAssignmentController',
          controllerAs: 'ctrl',
          params: {
            'eventAssignmentDisplay': true,
          },
          resolve: {
            events: function($q, EventService) {
              console.log('Load all events');
              var deferred = $q.defer();
              EventService.loadAllEvents().then(deferred.resolve, deferred.resolve);
              console.log('deferred.promise' + deferred.promise);
              return deferred.promise;
            },
            users: function($q, UserService) {
              console.log('Load all users');
              var deferred = $q.defer();
              UserService.loadAllUsers().then(deferred.resolve, deferred.resolve);
              console.log('deferred.promise' + deferred.promise);
              return deferred.promise;
            },
            eventMonths: function($q, EventMonthService) {
              console.log('Load all eventMonths');
              var deferred = $q.defer();
              EventMonthService.loadAllEventMonths().then(deferred.resolve, deferred.resolve);
              console.log('deferred.promise' + deferred.promise);
              return deferred.promise;
            },
            eventWeekDays: function($q, EventWeekDayService) {
              console.log('Load all eventWeekDays');
              var deferred = $q.defer();
              EventWeekDayService.loadAllEventWeekDays().then(deferred.resolve, deferred.resolve);
              console.log('deferred.promise' + deferred.promise);
              return deferred.promise;
            },
            eventWeekNumbers: function($q, EventWeekNumberService) {
              console.log('Load all eventWeekNumbers');
              var deferred = $q.defer();
              EventWeekNumberService.loadAllEventWeekNumbers().then(deferred.resolve, deferred.resolve);
              console.log('deferred.promise' + deferred.promise);
              return deferred.promise;
            }
          }
        })
        .state('main.provider', {
          url: '/provider',
          cache: false,
          templateUrl: 'partials/provider_list',
          controller: 'ProviderController',
          controllerAs: 'ctrl',
          resolve: {
            languages: function($q, LanguageService) {
              console.log('Load all languages');
              var deferred = $q.defer();
              LanguageService.loadLanguages().then(deferred.resolve, deferred.resolve);

              console.log('deferred.promise' + deferred.promise);
              return deferred.promise;
            },
            states: function($q, StateService) {
              console.log('Load all states');
              var deferred = $q.defer();
              StateService.loadAllStates().then(deferred.resolve, deferred.resolve);
              console.log('deferred.promise' + deferred.promise);
              return deferred.promise;
            }

          }
        })
        .state('main.insurance', {
          url: '/insurance',
          cache: false,
          templateUrl: 'partials/insurance_list',
          controller: 'InsuranceController',
          controllerAs: 'ctrl',
          resolve: {
            insurances: function($q, InsuranceService) {
              console.log('Load all insurances');
              var deferred = $q.defer();
              InsuranceService.loadInsurances(0, 20, '', null).then(deferred.resolve, deferred.resolve);

              console.log('deferred.promise' + deferred.promise);
              return deferred.promise;
            },
            planTypes: function($q, PlanTypeService) {
              console.log('Load all planTypes');
              var deferred = $q.defer();
              PlanTypeService.loadAllPlanTypes().then(deferred.resolve, deferred.resolve);

              console.log('deferred.promise' + deferred.promise);
              return deferred.promise;
            }
          }
        })
        .state('main.statusReport', {
          url: '/statusReport',
          cache: false,
          templateUrl: 'partials/statusReport_list',
          controller: 'StatusReportController',
          controllerAs: 'ctrl',
          resolve: {
            loadMyService: ['$ocLazyLoad', function($ocLazyLoad) {
              return $ocLazyLoad.load('main.statusReport');
            }],
            events: ['loadMyService', '$q', '$injector', function(loadMyService, $q, $injector) {
              var EventService = $injector.get("EventService");
              console.log('Load all  events');
              var deferred = $q.defer();
              EventService.loadAllEvents().then(deferred.resolve, deferred.resolve);
              return deferred.promise;
            }]
          }
        })
        .state('main.language', {
          url: '/language',
          cache: false,
          templateUrl: 'partials/language_list',
          controller: 'LanguageController',
          controllerAs: 'ctrl',
          resolve: {}
        })
        .state('logout', {
          url: '/logout',
          cache: false,
          templateUrl: 'logout'
        })
        .state('accessDenied', {
          url: '/accessDenied',
          templateUrl: 'accessDenied',
          resolve: {
            insurances: function($q) {
              console.log('Load all insurances');
            }
          }
        })

      function run() {
        FastClick.attach(document.body);
      }
    }

  ]);

  // DatePicker -> NgModel
  app.directive('datePicker', function() {
    return {
      require: 'ngModel',
      link: function(scope, element, attr, ngModel) {
        $(element).datetimepicker({
          locale: 'en-us',
          format: 'MM/DD/YYYY hh:mm a',
          parseInputDate: function(data) {
            if (data instanceof Date) {
              return moment(data);
            } else {
              return moment(new Date(data));
            }
          },
          minDate: new Date(new Date().setFullYear(new Date().getFullYear() - 1)),
          maxDate: new Date(new Date().setFullYear(new Date().getFullYear() + 1))
        });

        $(element).on("dp.change", function(e) {
          ngModel.$viewValue = moment(e.date).format('MM/DD/YYYY hh:mm a');
          ngModel.$commitViewValue();

        });
      }
    };
  });

  //DatePicker -> NgModel
  app.directive('date1Picker', function() {
    return {
      require: 'ngModel',
      link: function(scope, element, attr, ngModel) {
        $(element).datetimepicker({
          locale: 'en-us',
          format: 'MM/DD/YYYY',
          parseInputDate: function(data) {
            if (data instanceof Date) {
              return moment(data);
            } else {
              return moment(new Date(data));
            }
          },
          maxDate: new Date(new Date().setFullYear(new Date().getFullYear() + 1))
        });

        $(element).on("dp.change", function(e) {
          ngModel.$viewValue = moment(e.date).format('MM/DD/YYYY');
          ngModel.$commitViewValue();
        });
      }
    };
  });

//DatePicker -> NgModel
  app.directive('timePicker', function() {
    return {
      require: 'ngModel',
      link: function(scope, element, attr, ngModel) {
        $(element).datetimepicker({
          locale: 'en-us',
          format: 'MM/DD/YYYY',
          parseInputDate: function(data) {
            if (data instanceof Date) {
              return moment(data);
            } else {
              return moment(new Date(data));
            }
          },
          maxDate: new Date(new Date().setFullYear(new Date().getFullYear() + 1))
        });

        $(element).on("dp.change", function(e) {
          ngModel.$viewValue = moment(e.date).format('MM/DD/YYYY');
          ngModel.$commitViewValue();
        });
      }
    };
  });


  app.directive('fileModel', ['$parse', function($parse) {
    return {
      restrict: 'A',
      require: "ngModel",
      link: function(scope, element, attrs, ngModel) {

        var model = $parse(attrs.fileModel);
        var isMultiple = attrs.multiple;
        var modelSetter = model.assign;
        var dirty = false;
        element.bind('change', function() {
          var values = [];
          angular.forEach(element[0].files, function(item) {
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
          scope.$apply(function() {
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

  app.directive('zipCode', function() {
    return {
      restrict: 'A',
      require: 'ngModel',
      link: function(scope, element, attrs, ngModelController) {
        // format on keyup
        var formatZip = function(value) {
          if (value == undefined) return '';
          value = value.toString();
          value = value.replace(/[^0-9]/g, '');
          if (value.length > 5) {
            value = value.slice(0, 5);
          }
          if (value.length > 5) {
            value = value.slice(0, 5);
          }

          return value;
        };
        var applyFormatting = function() {
          var value = element.val();
          var original = value;
          if (!value || value.length == 0) {
            return
          }
          value = formatZip(value);
          if (value != original) {
            element.val(value);
            element.triggerHandler('input')
          }
        };
        element.bind('keyup', function(e) {
          var keycode = e.keyCode;
          var isTextInputKey =
            (keycode > 47 && keycode < 58) || // number keys
            keycode == 32 || keycode == 8 || // spacebar or backspace
            (keycode > 64 && keycode < 91) || // letter keys
            (keycode > 95 && keycode < 112) || // numpad keys
            (keycode > 185 && keycode < 193) || // ;=,-./` (in order)
            (keycode > 218 && keycode < 223); // [\]' (in order)
          if (isTextInputKey) {
            applyFormatting();
          }
        });

        ngModelController.$formatters.push(function(value) {
          if (!value || value.length == 0) {
            return value;
          }
          value = formatZip(value);
          return value;
        });
      }
    };
  });


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
          return viewValue.replace(/[^0-9]/g, '').slice(0, 10);
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
          if (key == 91 || (15 < key && key < 19) || (37 <= key && key <= 40)) {
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

  app.filter('nonAdminUsersFilter', function() {
    return function(input, arrayOfString) {
      var out = [];
      input.filter(function(user) {
        if (arrayOfString.indexOf(user.role.role) > -1) {

          out.push(user);
        }
      });
      return out;
    }
  });

  app.filter('filterFromArray', function() {
    return function(input, arrayToMatch) {
      input = input || [];
      arrayToMatch = arrayToMatch || [];
      var l = input.length,
        k = arrayToMatch.length,
        out = [],
        i = 0,
        map = {};

      for (; i < k; i++) {
        map[arrayToMatch[k]] = true;
      }

      for (i = 0; i < l; i++) {
        if (map[input[i].name]) {
          out.push(input[i]);
        }
      }

      return out;
    };
  });

  app.filter('tel', function() {
    return function(tel) {
      if (!tel) {
        return '';
      }

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

      if (number) {
        if (number.length > 3) {
          number = number.slice(0, 3) + '-' + number.slice(3, 7);
        } else {
          number = number;
        }

        return ("(" + city + ") " + number).trim();
      } else {
        return "(" + city;
      }

    };
  });

})();
