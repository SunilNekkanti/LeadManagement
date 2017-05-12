<!DOCTYPE html>
<html ng-app="my-app">

<head>
  <link href="style.css" rel="stylesheet" />
  <script data-require="angular.js@1.3.13" data-semver="1.3.13" src="https://code.angularjs.org/1.3.13/angular.js"></script>
  <script src="//angular-ui.github.io/bootstrap/ui-bootstrap-tpls-0.12.0.js"></script>
  <script src="js/app/script.js"></script>
  <link data-require="bootstrap-css@3.3.1" data-semver="3.3.1" rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css" />
</head>

<body>
  <h1>Hello Navbar</h1>
  <div ng-app="my-app">
    <div ng-controller="treeController">
      <nav class="navbar navbar-default" role="navigation">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" ng-init="navCollapsed = true" ng-click="navCollapsed = !navCollapsed">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
          <a class="navbar-brand" href="#">Brand</a>
        </div>
        
        <!-- Collect the nav links, forms, and other content for toggling -->
      <div class="collapse navbar-collapse" ng-class="!navCollapsed && 'in'">
        
        <ul class="nav navbar-nav">
          <li dropdown>
            <a href="#" dropdown-toggle>
              Dropdown
              <b class='caret'></b>
            </a>
            <tree tree='tree'></tree>
          </li>
          <li dropdown>
            <a href="#" dropdown-toggle>
              Just a clone
              <span class='caret'></span>
            </a>
            <tree tree='tree'></tree>
          </li>
        </ul>
        </div><!-- /.navbar-collapse -->
      </nav>
    </div>
  </div>
</body>

</html>




