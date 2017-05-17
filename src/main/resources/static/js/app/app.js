//Code goes here
'use strict';
var app = angular.module('my-app', ['ui.bootstrap','ui.router','ngStorage']);



app
.controller('NavbarController', ['$scope', '$state', function($scope, $state){
  $scope.isCollapsed = true;
  
  $scope.callMe = function(url){
	  alert(url);
	  $state.go(url);
  }
}]);


app.controller('TreeController', ['$scope',TreeController]) ;

function TreeController($scope) {
		
  $scope.callMe = function() {
	  console.log('test');
	  alert('test linkUrl:');
   // $state.go('home');
  }
  
  $scope.tree = [{
    name: "Bob",
    link: "#",
    subtree: [{
      name: "Ann",
      link: "#"
    }]
  }, {
    name: "John",
    link: "#",
    subtree: [{
      name: "Mary",
      link: "#"
    }]
  }, {
    name: "divider",
    link: "#"
  }, {
    name: "User Accounts",
    link: "home"
  }, {
    name: "divider",
    link: "#"
  },{
    name: "Again another person",
    link: "#"
  }];
  
}

app.directive('tree', function() {
  return {
    restrict: "E",
    replace: true,
    scope: {
      tree: '='
    },
    templateUrl: 'partials/template-ul'
  }
});

app.directive('leaf', function($compile) {
  return {
    restrict: "E",
    replace: true,
    scope: {
      leaf: "=",
      someCtrlFn : '&callMe'
    },
    templateUrl: 'partials/template-li',
    link: function(scope, element, attrs) {
      if (angular.isArray(scope.leaf.subtree)) {
        element.append("<tree tree='leaf.subtree'></tree>");
        element.addClass('dropdown-submenu');
        $compile(element.contents())(scope);
      } else {
        element.bind('click', function(e) {
        	alert('before '+scope.leaf.link);
        	alert('after');
        	console.log('test');
             });
      }
    }
  }
});



app.constant('urls', {
    BASE: 'http://localhost:8080/LeadManagement',
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
                  console.log('deferred.promise'+deferred.promise);
                  return deferred.promise;
              }
          }
      })

        $urlRouterProvider.otherwise('/');
    }]);

