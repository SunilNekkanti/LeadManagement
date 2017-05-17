<!DOCTYPE html>
<html lang="en" ng-app="App">

  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Navbar Demo</title>
    <!-- Css -->
    
    <!-- JavaScript -->
    <script data-require="angular.js@1.3.15" data-semver="1.3.15" src="https://code.angularjs.org/1.3.15/angular.js"></script>
    <script data-require="angular-ui-bootstrap@0.12.0" data-semver="0.12.0" src="http://angular-ui.github.io/bootstrap/ui-bootstrap-tpls-0.12.0.min.js"></script>
    <script data-require="ui-router@0.2.13" data-semver="0.2.13" src="//rawgit.com/angular-ui/ui-router/0.2.13/release/angular-ui-router.js"></script>
    <script src="script.js"></script>
    <script src="ui-navbar.js"></script>
    <link href="css/bootstrap.css" rel="stylesheet"/>
    <link href="css/app.css" rel="stylesheet"/>
    <link href="css/ui-navbar.css" rel="stylesheet"/>
     <link data-require="bootstrap-css@3.3.1" data-semver="3.3.1" rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css" />
    <link rel="stylesheet" href="css/ui-navbar.css" />
   
  </head>

  <body>
    <div ng-controller="NavigationController">
      <nav class="navbar navbar-default" role="navigation">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" ng-init="navCollapsed = true" ng-click="navCollapsed = !navCollapsed">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" ui-sref="home">Brand</a>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" ng-class="!navCollapsed && 'in'">
          <ul class="nav navbar-nav">
            <li dropdown="">
              <a href="#" dropdown-toggle="">
                        Dropdown
                                                                                                        <b class="caret"></b>
              </a>
              <tree tree="tree"></tree>
            </li>
            <li dropdown="">
              <a href="#" dropdown-toggle="">
                        Just a clone
                                                                                                        <span class="caret"></span>
              </a>
              <tree tree="tree"></tree>
            </li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li dropdown="">
              <a href="#" dropdown-toggle="">
                        Dropdown right
                                                                                                        <b class="caret"></b>
              </a>
              <tree tree="tree"></tree>
            </li>
            <li dropdown="">
              <a href="#" dropdown-toggle="">
                        Just a clone right
                                                                                                        <span class="caret"></span>
              </a>
              <tree tree="tree"></tree>
            </li>
          </ul>
        </div>
        <!-- /.navbar-collapse -->
      </nav>
    </div>
    
    <!-- Hook here the partials -->
    <div ui-view=""></div>
    
    <div ng-controller="AppCtrl" ng-cloak="" class="navBardemoBasicUsage" >
  <md-content class="md-padding">
    <md-nav-bar md-selected-nav-item="currentNavItem" nav-bar-aria-label="navigation links">
      <md-nav-item md-nav-click="goto('page1')" name="page1">Page One</md-nav-item>
      <md-nav-item md-nav-click="goto('page2')" name="page2">Page Two</md-nav-item>
      <md-nav-item md-nav-click="goto('page3')" name="page3">Page Three</md-nav-item>
      <!-- these require actual routing with ui-router or ng-route, so they won't work in the demo
      <md-nav-item md-nav-sref="app.page4" name="page4">Page Four</md-nav-item>
      <md-nav-item md-nav-href="#page5" name="page5">Page Five</md-nav-item>
      -->
    </md-nav-bar>
    <div class="ext-content">
      External content for `<span>{{currentNavItem}}</span>`
    </div>
  </md-content></div>
 
 
 <div ui-view></div>
        <script src="js/lib/angular.min.js" ></script>
        <script src="js/lib/angular-ui-router.min.js" ></script>
        <script src="js/lib/localforage.min.js" ></script>
        <script src="js/lib/ngStorage.min.js"></script>
        <script src="js/app/app.js"></script>
        <script src="js/app/UserService.js"></script>
        <script src="js/app/UserController.js"></script>
  </body>

</html>

