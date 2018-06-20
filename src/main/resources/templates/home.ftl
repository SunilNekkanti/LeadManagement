<!DOCTYPE html>
<html ng-app="my-app">

<head>
  <link rel="stylesheet" href="css/app.css"  />     
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <link rel="stylesheet" href="css/ui-navbar.css"  />
  <link rel="stylesheet" href="css/bootstrap-multiselect.css"  />

  <link rel="stylesheet" href="//cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css" />
  <link rel="stylesheet" href="https://cdn.datatables.net/colreorder/1.3.2/css/colReorder.dataTables.min.css"/>
  <link rel="stylesheet" href="https://cdn.datatables.net/fixedheader/3.1.3/css/fixedHeader.dataTables.min.css"/>
  <link rel="stylesheet" href="//cdn.datatables.net/1.10.12/css/dataTables.bootstrap.min.css" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker.min.css" />
  <link rel="stylesheet" href="https://cdn.datatables.net/fixedcolumns/3.2.4/css/fixedColumns.dataTables.min.css" />
  <link rel="stylesheet" href="//cdn.datatables.net/buttons/1.3.1/css/buttons.dataTables.min.css" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-select/0.20.0/select.min.css" />
  <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/selectize.js/0.8.5/css/selectize.default.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/angular-datatables/0.6.2/css/angular-datatables.min.css">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" />
  
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
  <script   src="//cdn.datatables.net/1.10.16/js/dataTables.bootstrap.min.js"></script> 
  
  <script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.1/angular.js"></script>
  <script src=" https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.1/angular-resource.min.js"></script> 
  <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-datatables/0.6.2/plugins/bootstrap/angular-datatables.bootstrap.min.js"></script>

  <script src="http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.9.0/moment-with-locales.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>      
  <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>

  <script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.1/angular-animate.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.1/angular-sanitize.min.js"></script>
  <script src="//angular-ui.github.io/bootstrap/ui-bootstrap-tpls-0.12.0.js"></script>
  <script src="js/lib/angular-datatables.min.js"></script>
  <script data-require="ngStorage@0.3.0" data-semver="0.3.0" src="http://rawgit.com/gsklee/ngStorage/0.3.0/ngStorage.min.js"></script>
  <script src="js/lib/angular-ui-router.min.js"></script>
  <script src="js/lib/angular-bootstrap-multiselect.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-select/0.20.0/select.min.js"></script>
  <script src="js/lib/Chart.min.js"></script>
  <script src="js/lib/angular-chart.min.js"></script>
  <script src="js/lib/ocLazyLoad.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.1/angular-cookies.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.1/angular-cookies.min.js.map"></script>
  
  <script src="https://cdn.datatables.net/fixedcolumns/3.2.4/js/dataTables.fixedColumns.min.js"></script>
  <script src="https://cdn.datatables.net/buttons/1.2.2/js/dataTables.buttons.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
  <script src="http://l-lin.github.io/angular-datatables/archives/vendor/datatables-light-columnfilter/dist/dataTables.lightColumnFilter.min.js"></script> 
  
  <script src="https://cdn.datatables.net/colreorder/1.3.2/js/dataTables.colReorder.min.js"></script>
  <script src="https://cdn.datatables.net/fixedheader/3.1.3/js/dataTables.fixedHeader.min.js"></script>
  
  <script src="https://cdn.datatables.net/buttons/1.2.2/js/buttons.colVis.min.js"></script>
  <script src="https://cdn.datatables.net/buttons/1.2.2/js/buttons.flash.min.js"></script>
  <script src="https://cdn.datatables.net/buttons/1.2.2/js/buttons.html5.min.js"></script>
  <script src="https://cdn.datatables.net/buttons/1.2.2/js/buttons.print.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.32/pdfmake.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.32/vfs_fonts.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-select/0.20.0/select.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-datatables/0.6.2/plugins/buttons/angular-datatables.buttons.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-datatables/0.6.2/plugins/fixedcolumns/angular-datatables.fixedcolumns.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-datatables/0.6.2/plugins/fixedheader/angular-datatables.fixedheader.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-datatables/0.6.2/plugins/tabletools/angular-datatables.tabletools.min.js"></script>
  <script src="http://l-lin.github.io/angular-datatables/archives/dist/plugins/light-columnfilter/angular-datatables.light-columnfilter.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-datatables/0.6.2/plugins/colreorder/angular-datatables.colreorder.min.js"> </script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-datatables/0.6.2/plugins/fixedheader/angular-datatables.fixedheader.min.js"> </script>

  <script src="js/app/app.js"></script>
  <script src="js/app/UserService.js"></script>
  <script src="js/app/datetimepicker.js"></script>
  <script src="js/app/datetimepicker.templates.js"></script>
  
  <style>
     .dataTable > thead > tr > th[class*="sort"]::after{display: none}
</style>
</head>

<body class="ng-cloak" ng-controller="NavbarController">
  <nav class="navbar   navbar-inverse"  ng-if="displayNavbar" role="navigation">
    <div class="container-fluid" >
    
      <div class="navbar-collapse collapse in" collapse="isCollapsed" aria-expanded="true" >
        <ul class="nav navbar-nav">
        
        <li class="dropdown" dropdown ng-if="loginUser.roleName !== 'AGENT'">
            <a href="#" class="dropdown-toggle" dropdown-toggle role="button" aria-expanded="false">Events <span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
              <li><a  ui-sref="main.event" ui-sref-active="active">Event List</a></li>
              <li ng-if="(loginUser.roleName === 'ADMIN' || loginUser.roleName === 'MANAGER' || loginUser.roleName === 'EVENT_COORDINATOR')"><a   ui-sref="main.eventAssignment" ui-sref-active="active" >Event Assignments</a> </li>
            </ul>
          </li>
        <li><a  ui-sref="main.lead" ui-sref-active="active">Leads</a></li>
          <li class="dropdown" dropdown ng-if="(loginUser.roleName === 'ADMIN' || loginUser.roleName === 'MANAGER' || loginUser.roleName === 'EVENT_COORDINATOR')">
            <a href="#" class="dropdown-toggle" dropdown-toggle role="button" aria-expanded="false">Admin <span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
              <li><a ui-sref="main.user" ui-sref-active="active"  >User Accounts</a> </li>
              <li class="divider"></li>
             		<li class="dropdown-submenu" dropdown>
            		<a href="#" class="dropdown-toggle" dropdown-toggle role="button" aria-expanded="false">Settings</a>
            			<ul class="dropdown-menu" role="submenu">
              				<li><a  ui-sref="main.facilityType" ui-sref-active="active" >Facility Types</a></li>
            				<li><a  ui-sref="main.leadStatus" ui-sref-active="active" >Lead Statuses</a></li>
            				<li><a  ui-sref="main.leadStatusDetail" ui-sref-active="active"  >Lead Status Details</a></li>
              				<li><a  ui-sref="main.role" ui-sref-active="active"  >Roles</a> </li>
           				 </ul>
          			</li>
             
             
            </ul>
          </li>
          <li class="dropdown" dropdown ng-if="loginUser.roleName !== 'AGENT'">
            <a href="#" class="dropdown-toggle" dropdown-toggle role="button" aria-expanded="false">Reports <span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
              <li ng-if="(loginUser.roleName === 'ADMIN' || loginUser.roleName === 'MANAGER' || loginUser.roleName === 'EVENT_COORDINATOR')"><a   ui-sref="main.statusReport" ui-sref-active="active" >Status Report</a> </li>
            </ul>
          </li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
        	<li> <a  href="#"> <span class="glyphicon glyphicon-user"></span> ${username}</a></li>
        	<li> <a ng-click="callMe('logout')" ><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
        </ul>
      </div>
    </div>
  </nav>
   <script id="__bs_script__">//<![CDATA[
    document.write("<script async src='http://HOST:PORT/browser-sync/browser-sync-client.js?v=2.24.4'><\/script>".replace(/HOST/g, location.hostname).replace(/PORT/g, 3000));
//]]></script>
 <ui-view> </ui-view>

</body>

</html>
