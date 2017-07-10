  
    
<div class="generic-container" >
   <div class="panel panel-default" ng-hide="ctrl.display">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="user">List of Users </span> 
               <button type="button"   ng-click="ctrl.addUser()" ng-hide="ctrl.displayEditButton" class="btn btn-success custom-width floatRight"> Add </button>   
               <button type="button" ng-click="ctrl.editUser(ctrl.userId)" ng-show="ctrl.displayEditButton" class="btn btn-primary custom-width floatRight">Edit</button>  
              <button type="button" ng-click="ctrl.removeUser(ctrl.userId)"  ng-show="ctrl.displayEditButton" class="btn btn-danger custom-width floatRight">Remove</button>  
        </div>
        <div class="table-responsive">
			<div class="panel-body">
			
	        	<table datatable="" id="content"   dt-options="ctrl.dtOptions"  dt-columns="ctrl.dtColumns" dt-instance="ctrl.dtInstance" class="table-responsive table  bordered table-striped table-condensed datatable "></table>
          </div>
		</div>
    </div>
     
    
    <div class="panel panel-default" ng-show="ctrl.display">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">User </span></div>
		<div class="panel-body">
	        <div class="formcontainer">
	            <div class="alert alert-success" role="alert" ng-if="ctrl.successMessage">{{ctrl.successMessage}}</div>
	            <div class="alert alert-danger" role="alert" ng-if="ctrl.errorMessage">{{ctrl.errorMessage}}</div>
	            <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
	                <input type="hidden" ng-model="ctrl.user.id" />
	                <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="uname">Name</label>
	                        <div class="col-md-7">
	                            <input type="text" ng-model="ctrl.user.username" name="username" class="username form-control input-sm" placeholder="Enter your name" ng-required="true" ng-minlength="5"/>
	                            <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.username.$error.required">This is a required field</span>
                                      <span ng-show="myForm.username.$error.minlength">Minimum length required is 5</span>
                                      <span ng-show="myForm.username.$invalid">This field is invalid </span>
                                  </div>
	                        </div>
	                    </div>
	                </div>

	                <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="age">Password</label>
	                        <div class="col-md-7">
	                            <input type="text" ng-model="ctrl.user.password" id="age" class="form-control input-sm" placeholder="Enter your Password." ng-required="true" ng-minlength="6"/>
	                        </div>
	                    </div>
	                </div>
	
 				  <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="uname">Role</label>
	                        <div class="col-md-7">
	                        <select ng-model="ctrl.user.roles" name="role" ng-options="role.role for role in ctrl.roles track by role.role" multiple ng-required="true"></select>
	                        <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.role.$error.required">This is a required field</span>
                                  </div>
	                        </div>
	                    </div>
	                </div>
	                
	                 <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="uname">Language</label>
	                        <div class="col-md-7">
	                        <select ng-model="ctrl.user.languages" name="language" ng-options="language.description for language in ctrl.languages | orderBy:'description' track by language.description" ng-required="(ctrl.user.roles|filter:{role:'ROLE_AGENT'}).length > 0" multiple></select>
	                           <div class="has-error" ng-show="myForm.$dirty">
	                                      <span ng-show="myForm.language.$error.required">This is a required field</span>
	                            </div>
	                        </div>
	                    </div>
	                </div>

	                 <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="uname">County</label>
	                        <div class="col-md-7">
	                        	<select ng-model="ctrl.user.counties" name="county" ng-options="county.description for county in ctrl.counties | orderBy:'description' track by county.description"   ng-required="(ctrl.user.roles|filter:{role:'ROLE_AGENT'}).length > 0" multiple=true ></select>
	                        		<div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="(ctrl.user.roles|filter:{role:'ROLE_AGENT'}).length > 0 && myForm.county.$error.required">This is a required field</span>
                                  </div>
	                        </div>
	                    </div>
	                </div>
	               
	                  <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="email" require>email</label>
	                        <div class="col-md-7">
	                            <input type="email" ng-model="ctrl.user.email" id="email" name="email" class="form-control input-sm" placeholder="Enter  email." ng-required="true" ng-minlength="6"/>
	                             <div class="has-error" ng-show="myForm.$dirty">
	                                 <span ng-show="myForm.username.$error.required">This is a required field</span>
                                     <span ng-show="myForm.email.$error.minlength">Minimum length required is 5</span>
                                     <span ng-show="myForm.email.$invalid">This field is invalid </span>
                                  </div>
	                        </div>
	                    </div>
	                </div>
	                
	                  <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="phone">Phone</label>
	                        <div class="col-md-7">
	                            <input type="text" ng-model="ctrl.user.phone" name="phone" class="form-control input-sm" placeholder="Enter phone number." phone-input ng-required="true" ng-minlength="10"/>
	                            <div class="has-error" ng-show="myForm.$dirty">
	                                 <span ng-show="myForm.phone.$error.required">This is a required field</span>
                                     <span ng-show="myForm.phone.$error.minlength">Minimum length required is 10</span>
                                     <span ng-show="myForm.phone.$invalid">This field is invalid </span>
                                  </div>
	                        </div>
	                    </div>
	                </div>
	                
	                
	                  <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="brokerage">Brokerage</label>
	                        <div class="col-md-7">
	                        <select ng-model="ctrl.user.brokerages" name="brokerages" ng-options="brokerage.description for brokerage in ctrl.brokerages track by brokerage.description" ng-required="(ctrl.user.roles|filter:{role:'ROLE_AGENT'}).length > 0" multiple > </select>
	                           <div class="has-error" ng-show="myForm.$dirty">
	                                      <span ng-show="(ctrl.user.roles|filter:{role:'ROLE_AGENT'}).length > 0 && myForm.brokerages.$error.required">This is a required field</span>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	               
	               <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="licenseNo">License No</label>
	                        <div class="col-md-7">
	                            <input type="text" ng-model="ctrl.user.licenseNo" name="licenseNo" class="form-control input-sm" placeholder="Enter agent LicenseNo." ng-required="(ctrl.user.roles|filter:{role:'ROLE_AGENT'}).length > 0" ng-minlength="6"/>
	                            <div class="has-error" ng-show="myForm.$dirty">
	                                 <span ng-show="(ctrl.user.roles|filter:{role:'ROLE_AGENT'}).length > 0 &&  myForm.licenseNo.$error.required">This is a required field</span>
                                  </div>
	                        </div>
	                    </div>
	                </div>
	               
	                <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="uname">Position</label>
	                        <div class="col-md-7">
	                            <input type="text" ng-model="ctrl.user.position" name="position" class="username form-control input-sm" placeholder="Enter Position in Organization" ng-required="true" ng-minlength="5"/>
	                            <div class="has-error" ng-show="myForm.$dirty">
	                                 <span ng-show="myForm.position.$error.required">This is a required field</span>
                                     <span ng-show="myForm.position.$error.minlength">Minimum length required is 5</span>
                                     <span ng-show="myForm.position.$invalid">This field is invalid </span>
                                  </div>
	                        </div>
	                    </div>
	                </div>
	                
	                <div class="row">
	                    <div class="form-actions floatRight">
	                        <input type="submit"  value="{{!ctrl.user.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid || myForm.$pristine">
	                        <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset Form</button>
	                    </div>
	                </div>
	            </form>
    	    </div>
		</div>	
    </div>
    
    
</div>