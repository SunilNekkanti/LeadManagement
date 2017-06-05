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
    LANGUAGE_SERVICE_API : 'http://localhost:8080/LeadManagement/api/leadLanguage/',
    PLANTYPE_SERVICE_API : 'http://localhost:8080/LeadManagement/api/planType/',
    INSURANCE_SERVICE_API : 'http://localhost:8080/LeadManagement/api/insurance/'
});

app.controller('NavbarController', ['$scope', '$state', function($scope, $state){
  $scope.isCollapsed = true;
  
  $scope.callMe = function(url){
	  $state.go(url);
  }
}]);


 


app.config(['$stateProvider', '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {
	
	
    // Now set up the states
    $stateProvider
       .state('user', {
          url: '/user',
          templateUrl: 'partials/list',
          controller:'UserController',
          controllerAs:'ctrl',
          resolve: {
              users: function ($q, UserService, RoleService) {
                  console.log('Load all users');
                  var deferred = $q.defer();
                  RoleService.loadAllRoles().then(deferred.resolve, deferred.resolve);
                  UserService.loadAllUsers().then(deferred.resolve, deferred.resolve);
                  
                  console.log('deferred.promise'+deferred.promise);
                  return deferred.promise;
              }
          }
      })
      .state('lead', {
          url: '/',
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
		      languages: function ($q,  LeadLanguageService) {
		          console.log('Load all leadLanguages');
		          var deferred = $q.defer();
		          LeadLanguageService.loadAllLeadLanguages().then(deferred.resolve, deferred.resolve);
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
        $urlRouterProvider.otherwise('/');
    }]);

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


