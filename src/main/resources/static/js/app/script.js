var app = angular.module('my-app', ['ui.bootstrap']);

app.controller('treeController', function($scope) {
  $scope.callMe = function() {
    alert("test");
  };
  
  $scope.tree = [{
    name: "Bob",
    link: "#",
    subtree: [{
      name: "Ann",
      link: "#"
    }]
  }, {
    name: "Jon",
    link: "#",
    subtree: [{
      name: "Mary",
      link: "#"
    }]
  }, {
    name: "divider",
    link: "#"
  }, {
    name: "Another person",
    link: "#"
  }, {
    name: "divider",
    link: "#"
  },{
    name: "Again another person",
    link: "#"
  }];
  
});

app.directive('tree', function() {
  return {
    restrict: "E",
    replace: true,
    scope: {
      tree: '='
    },
    templateUrl: 'partials/template-ul'
  };
});

app.directive('leaf', function($compile) {
  return {
    restrict: "E",
    replace: true,
    scope: {
      leaf: "="
    },
    templateUrl: 'partials/template-li',
    link: function(scope, element, attrs) {
      if (angular.isArray(scope.leaf.subtree)) {
        element.append("<tree tree='leaf.subtree'></tree>");
        element.addClass('dropdown-submenu');
        $compile(element.contents())(scope);
      } else {
        element.bind('click', function() {
          alert("You have clicked on " + scope.leaf.name);
        });

      }
    }
  };
});