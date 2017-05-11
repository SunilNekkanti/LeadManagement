var app = angular.module('crudApp',['ui.router','ngStorage','ngMaterial', 'ngMessages', 'material.svgAssetsCache','ui.bootstrap', 'ui.router', 'ui.navbar']);

app.constant('urls', {
    BASE: 'http://localhost:8080/SpringBootCRUDApp',
    USER_SERVICE_API : 'http://localhost:8080/LeadManagement/api/user/'
});

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
              users: function ($q, UserService) {
                  console.log('Load all users');
                  var deferred = $q.defer();
                  UserService.loadAllUsers().then(deferred.resolve, deferred.resolve);
                  return deferred.promise;
              }
          }
      })
      .state('state1', {
        url: "/state1",
        templateUrl: "state1.html"
      })
      .state('state2', {
        url: "/state2",
        templateUrl: "state2.html"
      });
    

        $stateProvider
            .state('home', {
                url: '/',
                templateUrl: 'partials/list',
                controller:'UserController',
                controllerAs:'ctrl',
                resolve: {
                    users: function ($q, UserService) {
                        console.log('Load all users');
                        var deferred = $q.defer();
                        UserService.loadAllUsers().then(deferred.resolve, deferred.resolve);
                        return deferred.promise;
                    }
                }
            });
        $urlRouterProvider.otherwise('/');
    }]);



 

app.controller('AppCtrl', AppCtrl);

	  function AppCtrl($scope) {
	    $scope.currentNavItem = 'page1';
	  }
	 
	  
	  'use strict';

	 
	  app.controller('NavigationController', function($scope) {

	    $scope.tree = [{
	      name: "States",
	      link: "#",
	      subtree: [{
	        name: "state 1",
	        link: "state1",
	        subtree: [{name: "state 1",
	        link: "state1"}]
	      }, {
	        name: "state 2",
	        link: "state2"
	      }]
	    }, {
	      name: "No states",
	      link: "#",
	      subtree: [{
	        name: "no state connected",
	        link: "#"
	      }]
	    }, {
	      name: "divider",
	      link: "#"

	    }, {
	      name: "State has not been set up",
	      link: "#"
	    }, {
	      name: "divider",
	      link: "#"
	    }, {
	      name: "Here again no state set up",
	      link: "#"
	    }];
	  });