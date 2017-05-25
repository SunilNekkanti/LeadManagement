<!DOCTYPE html>
<html ng-app="my-app">

<head>
  <link href="css/app.css" rel="stylesheet" />
  <link href="css/bootstrap.css" rel="stylesheet" />
  <link href="css/ui-navbar.css" rel="stylesheet" />
  <link data-require="bootstrap-css@3.3.1" data-semver="3.3.1" rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css" />
        
  <script data-require="angular.js@1.3.13" data-semver="1.3.13" src="https://code.angularjs.org/1.3.13/angular.js"></script>
  <script data-require="ui-router@1.0.0-beta.2" data-semver="1.0.0-beta.2" src="//cdnjs.cloudflare.com/ajax/libs/angular-ui-router/1.0.0-beta.2/angular-ui-router.js"></script>
  <script src="//angular-ui.github.io/bootstrap/ui-bootstrap-tpls-0.12.0.js"></script>
  <script data-require="ngStorage@0.3.0" data-semver="0.3.0" src="http://rawgit.com/gsklee/ngStorage/0.3.0/ngStorage.min.js"></script>


    <script data-require="jquery@1.10.1" data-semver="1.10.1" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
    <script src="//cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>  
    <script src="https://rawgithub.com/vivendi/angular-datatables/master/src/angular-datatables.js"></script>
	<script src="https://rawgithub.com/vivendi/angular-datatables/master/src/angular-datatables.directive.js"></script>
	<script src="https://rawgithub.com/vivendi/angular-datatables/master/src/angular-datatables.factory.js"></script>
	<script src="https://rawgithub.com/vivendi/angular-datatables/master/src/angular-datatables.bootstrap.js"></script>
    
    
  <script src="js/app/app.js"></script>
  <script src="js/app/UserService.js"></script>
  <script src="js/app/UserController.js"></script>
  <script src="js/app/GenderService.js"></script>
  <script src="js/app/LeadService.js"></script>
  <script src="js/app/LeadController.js"></script>
    <script src="js/app/RoleService.js"></script>
  <script src="js/app/RoleController.js"></script>


</head>

<body>
  <nav class="navbar navbar-default myNavbar" ng-controller="NavbarController">
    <div class="container-fluid">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle" ng-click="isCollapsed = !isCollapsed" aria-expanded="true">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="#">Brand</a>
      </div>

      <div class="navbar-collapse collapse in" collapse="isCollapsed" aria-expanded="true">
        <ul class="nav navbar-nav">
          <li class="dropdown" dropdown>
            <a href="#" class="dropdown-toggle" dropdown-toggle role="button" aria-expanded="false">Admin <span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
              <li><a href="#" ng-click="callMe('lead')">Leads</a>
              </li>
               <li class="divider"></li>
              <li><a href="#" ng-click="callMe('role')">Roles</a>
              </li>
              <li><a href="#" ng-click="callMe('home')">User Accounts</a>
              </li>
              <li class="divider"></li>
              <li><a href="#">Separated link</a>
              </li>
              <li class="divider"></li>
              <li><a href="#">One more separated link</a>
              </li>
            </ul>
          </li>
        </ul>
        <form class="navbar-form navbar-left" role="search">
          <div class="form-group">
            <input type="text" class="form-control" placeholder="Search">
          </div>
          <button type="submit" class="btn btn-default">Submit</button>
        </form>
        <ul class="nav navbar-nav navbar-right">
          <li><a href="#">Link</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>

  
     <!-- Hook here the partials -->
    <div ui-view></div>
</body>

</html>
