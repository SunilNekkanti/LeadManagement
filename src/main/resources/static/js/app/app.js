//Code goes here
'use strict';
var app = angular.module('my-app', ['ui.bootstrap','ui.router','ngStorage','datatables']);


app.constant('urls', {
    BASE: 'http://localhost:8080/LeadManagement',
    USER_SERVICE_API : 'http://localhost:8080/LeadManagement/api/user/',
    LEAD_SERVICE_API : 'http://localhost:8080/LeadManagement/api/lead/',
    GENDER_SERVICE_API : 'http://localhost:8080/LeadManagement/api/gender/',
    ROLE_SERVICE_API : 'http://localhost:8080/LeadManagement/api/role/',
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
       .state('home', {
          url: '/',
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
          url: '/lead',
          templateUrl: 'partials/lead_list',
          controller:'LeadController',
          controllerAs:'ctrl',
          resolve: {
              users: function ($q, LeadService, GenderService) {
                  console.log('Load all leads');
                  var deferred = $q.defer();
                  LeadService.loadAllLeads().then(deferred.resolve, deferred.resolve);
                  GenderService.loadAllGenders().then(deferred.resolve, deferred.resolve);
                  
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

