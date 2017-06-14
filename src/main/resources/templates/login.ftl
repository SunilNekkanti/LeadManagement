<!DOCTYPE html>
<html >

<head>
  <link href="css/app.css" rel="stylesheet" />
  <link href="css/bootstrap.css" rel="stylesheet" />
  <link href="css/ui-navbar.css" rel="stylesheet" />
  <link data-require="bootstrap-css@3.3.1" data-semver="3.3.1" rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css" />
  <link rel="stylesheet" href="//cdn.datatables.net/1.10.1/css/jquery.dataTables.css" />
  <link href="http://cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.css" />
  <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker.min.css" />
  
   <script   src="http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.9.0/moment-with-locales.js"></script>
   <script data-require="jquery@1.10.1" data-semver="1.10.1" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>      
   <script   src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.js"></script>
   <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>
   <script src="//cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>   
   <script src="http://cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.js"></script> 
   <script data-require="angular.js@1.3.13" data-semver="1.3.13" src="https://code.angularjs.org/1.3.13/angular.js"></script>
   <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.6.1/angular-animate.js"></script>
   <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.6.1/angular-sanitize.js"></script>
   <script src="//angular-ui.github.io/bootstrap/ui-bootstrap-tpls-2.5.0.js"></script>
   <script data-require="angular-datatables@*" data-semver="0.6.0" src="//cdnjs.cloudflare.com/ajax/libs/angular-datatables/0.6.0/angular-datatables.min.js"></script>
   <script data-require="ngStorage@0.3.0" data-semver="0.3.0" src="http://rawgit.com/gsklee/ngStorage/0.3.0/ngStorage.min.js"></script>
   <script data-require="ui-router@1.0.0-beta.2" data-semver="1.0.0-beta.2" src="//cdnjs.cloudflare.com/ajax/libs/angular-ui-router/1.0.0-beta.2/angular-ui-router.js"></script>
   <script src="//angular-ui.github.io/bootstrap/ui-bootstrap-tpls-0.12.0.js"></script>
   
     
  <script src="js/app/LoginController.js"></script>
</head>



<body>
<div class="container">

 <nav class="navbar navbar-default myNavbar" >
    <div class="container-fluid">
    
     </div>
  </nav>  
  
	<div  class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3" >
		<form    action="loginform.do" method="post" name="myForm" class="form-horizontal">
				<h2>Please Sign In</h2>

				<hr class="colorgraph">
				<div class="form-group">
				 <input type="text"  ng-model="ctrl.login.username" name="username" class="username form-control input-sm" placeholder="Enter user name" required ng-minlength="5"/>
				</div>
				<div class="form-group">
					 <input type="password"  ng-model="ctrl.login.password"  name="password" class="password form-control input-sm" placeholder="Enter password" required ng-minlength="5"/>
				</div>
				<span class="button-checkbox">
					<button type="button" class="btn" data-color="info">Remember
						Me</button> <input type="checkbox" name="remember_me" id="remember_me"
					checked="checked" class="hidden"> <a href=""
					class="btn btn-link pull-right">Forgot Password?</a>
				</span>
				<hr class="colorgraph">
				<div class="row">
					<div class="col-xs-6 col-sm-6 col-md-6">
						<input type="submit" class="btn btn-lg btn-success btn-block"
							value="Sign In"  ng-disabled="myForm.$invalid || myForm.$pristine">
					</div>
					<div class="col-xs-6 col-sm-6 col-md-6">
						<a href="" class="btn btn-lg btn-primary btn-block">Register</a>
					</div>
				</div>
		</form>
	</div>
</div>
</body>

</html>
