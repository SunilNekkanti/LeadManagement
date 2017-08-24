<!DOCTYPE html>
<html ng-app="my-app">

<head>
  <link href="css/app.css" rel="stylesheet" />
  <link href="css/bootstrap.css" rel="stylesheet" />
  <link href="css/ui-navbar.css" rel="stylesheet" />
  
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
  <link rel="stylesheet" href="//cdn.datatables.net/1.10.1/css/jquery.dataTables.css" />
  <link rel="stylesheet" href="https://cdn.datatables.net/1.10.15/css/dataTables.bootstrap.min.css" />
   <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker.min.css" />
   
   
   <script data-require="jquery@1.10.1" data-semver="1.10.1" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
  <script src="//cdn.datatables.net/1.10.1/js/jquery.dataTables.js"></script>
  <script src="http://code.angularjs.org/1.3.5/angular.js"></script>
  <script src="https://code.angularjs.org/1.3.5/angular-resource.js"></script>
  <script type="text/javascript" src="https://rawgit.com/vivendi/angular-datatables/master/src/angular-datatables.bootstrap.js"></script>
  
   <script   src="http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.9.0/moment-with-locales.js"></script>
   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>      
   <script   src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.js"></script>
   <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>
   <script src="http://cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.js"></script> 
   <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.6.1/angular-animate.js"></script>
   <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.6.1/angular-sanitize.js"></script>
   <script src="//angular-ui.github.io/bootstrap/ui-bootstrap-tpls-2.5.0.js"></script>
   <script data-require="angular-datatables@*" data-semver="0.6.0" src="//cdnjs.cloudflare.com/ajax/libs/angular-datatables/0.6.0/angular-datatables.min.js"></script>
   <script data-require="ngStorage@0.3.0" data-semver="0.3.0" src="http://rawgit.com/gsklee/ngStorage/0.3.0/ngStorage.min.js"></script>
   <script data-require="ui-router@1.0.0-beta.2" data-semver="1.0.0-beta.2" src="//cdnjs.cloudflare.com/ajax/libs/angular-ui-router/1.0.0-beta.2/angular-ui-router.js"></script>
    <script data-require="ui-bootstrap@*" data-semver="0.12.0" src="http://angular-ui.github.io/bootstrap/ui-bootstrap-tpls-0.12.0.min.js"></script>
   <script src="//angular-ui.github.io/bootstrap/ui-bootstrap-tpls-0.12.0.js"></script>
 
  <script src="js/app/app.js"></script>
  <script src="js/app/UserService.js"></script>
  <script src="js/app/UserController.js"></script>
  <script src="js/app/GenderService.js"></script>
  <script src="js/app/StateService.js"></script>
  <script src="js/app/LeadStatusService.js"></script>
  <script src="js/app/LanguageService.js"></script>
  <script src="js/app/InsuranceService.js"></script>
  <script src="js/app/PlanTypeService.js"></script>
  <script src="js/app/ProviderService.js"></script>
  <script src="js/app/LeadService.js"></script>
  <script src="js/app/LeadController.js"></script>
  <script src="js/app/RoleService.js"></script>
  <script src="js/app/RoleController.js"></script>
  <script src="js/app/FacilityTypeController.js"></script>
  <script src="js/app/FacilityTypeService.js"></script>
  <script src="js/app/EventController.js"></script>
  <script src="js/app/EventService.js"></script>
  <script src="js/app/FileUploadService.js"></script>
  <script src="js/app/LanguageController.js"></script>
  <script src="js/app/LanguageService.js"></script>
  <script src="js/app/CountyService.js"></script>
  <script src="js/app/CountyController.js"></script>
  <script src="js/app/ProviderService.js"></script>
  <script src="js/app/ProviderController.js"></script>
  <script src="js/app/LeadStatusController.js"></script>
  <script src="js/app/InsuranceController.js"></script>
  <script src="js/app/EventFrequencyController.js"></script>
  <script src="js/app/EventFrequencyService.js"></script>
  <script src="js/app/EventMonthController.js"></script>
  <script src="js/app/EventMonthService.js"></script>
  <script src="js/app/EventWeekDayController.js"></script>
  <script src="js/app/EventWeekDayService.js"></script>
  <script src="js/app/EventWeekNumberService.js"></script>
  <script src="js/app/EventAssignmentService.js"></script>
  <script src="js/app/EventAssignmentController.js"></script>
</head>

<body class="ng-cloak" ng-controller="NavbarController">
  <nav class="navbar navbar-default myNavbar"  ng-if="displayNavbar" role="navigation">
    <div class="container-fluid" >
    
      <div class="navbar-collapse collapse in" collapse="isCollapsed" aria-expanded="true" >
        <ul class="nav navbar-nav">
        
        <li class="dropdown" dropdown>
            <a href="#" class="dropdown-toggle" dropdown-toggle role="button" aria-expanded="false">Events <span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
              <li><a  ng-click="callMe('event','')">Event List</a></li>
              <li><a   ng-click="callMe('eventAssignment','')">Event Assignments</a> </li>
            </ul>
          </li>
          
        <li><a  href="javascript:void(0)" ng-click="callMe('lead','')">Leads</a></li>
          <li class="dropdown" dropdown>
            <a href="#" class="dropdown-toggle" dropdown-toggle role="button" aria-expanded="false">Admin <span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
              <li><a ng-click="callMe('user','')">User Accounts</a> </li>
              <li class="divider"></li>
             		<li class="dropdown-submenu" dropdown>
            		<a href="#" class="dropdown-toggle" dropdown-toggle role="button" aria-expanded="false">Settings</a>
            			<ul class="dropdown-menu" role="submenu">
              				<li><a  ng-click="callMe('facilityType','')">Facility Types</a></li>
            				<li><a   ng-click="callMe('leadStatus','')">Lead Statuses</a></li>
              				<li><a   ng-click="callMe('role','')">Roles</a> </li>
           				 </ul>
          			</li>
             
             
            </ul>
          </li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
        	<li> <a href="#"> <span class="glyphicon glyphicon-user"></span> ${username}</a></li>
        	<li> <a ng-click="callMe('logout')"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
        </ul>
      </div>
    </div>
  </nav>

  
     <!-- Hook here the partials -->
    <div ui-view></div>
</body>

</html>
