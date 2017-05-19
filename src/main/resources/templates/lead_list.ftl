<div class="generic-container" >
    <div class="panel panel-default" ng-show="ctrl.display">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">Lead </span></div>
		<div class="panel-body">
	        <div class="formcontainer">
	            <div class="alert alert-success" role="alert" ng-if="ctrl.successMessage">{{ctrl.successMessage}}</div>
	            <div class="alert alert-danger" role="alert" ng-if="ctrl.errorMessage">{{ctrl.errorMessage}}</div>
	            <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
	                <input type="hidden" ng-model="ctrl.lead.id" />
	                <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-1 col-md-offset-4 control-lable" for="uname">First Name</label>
	                        <div class="col-md-3">
	                            <input type="text" ng-model="ctrl.lead.firstName" id="uname" class="username form-control input-sm" placeholder="Enter your name" required ng-minlength="5"/>
	                        </div>
	                    </div>
	                </div>
    				 <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-1 col-md-offset-4 control-lable" for="uname">Last Name</label>
	                        <div class="col-md-3">
	                            <input type="text" ng-model="ctrl.lead.lastName" id="uname" class="username form-control input-sm" placeholder="Enter your name" required ng-minlength="5"/>
	                        </div>
	                    </div>
	                </div>
	                 <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-1 col-md-offset-4 control-lable" for="uname">Date Of Birth</label>
	                        <div class="col-md-3">
	                            <input type="text" ng-model="ctrl.lead.dob" id="uname" class="username form-control input-sm" placeholder="Enter your name" required ng-minlength="10" ng-maxlength="10"/>
	                        </div>
	                    </div>
	                </div>
	                <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-1 col-md-offset-4 control-lable" for="uname">Gender</label>
	                        <div class="col-md-3">
	                        <select ng-model="ctrl.lead.genderId" ng-options="gender.description for gender in ctrl.genders track by gender.description"></select>
	                        </div>
	                    </div>
	                </div>
	               
	                <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-1 col-md-offset-4 control-lable" for="uname">Medicaid</label>
	                        <div class="col-md-1">
	                        <input type="checkbox"  ng-model="ctrl.lead.hasMedicaid"   name="hasMedicaid"     ng-true-value="'Y'"     ng-false-value="'N'" >
	                        </div>
	                    
	                        <label class="col-md-1  control-lable" for="uname">Medicare</label>
	                        <div class="col-md-1">
	                            <input type="checkbox"  ng-model="ctrl.lead.hasMedicare"   name="hasMedicare"     ng-true-value="'Y'"     ng-false-value="'N'" >
	                        </div>
	                        
	                          <label class="col-md-1  control-lable" for="uname">Disable</label>
	                        <div class="col-md-1">
	                            <input type="checkbox"  ng-model="ctrl.lead.hasDisability"   name="hasMedicare"     ng-true-value="'Y'"     ng-false-value="'N'" >
	                        </div>
	                    </div>
	                </div>
	

	                <div class="row">
	                    <div class="form-actions floatCenter col-md-offset-8">
	                        <input type="submit"  value="{{!ctrl.lead.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid || myForm.$pristine">
	                        <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset Form</button>
	                    </div>
	                </div>
	            </form>
    	    </div>
		</div>	
    </div>
    <div class="panel panel-default" ng-hide="ctrl.display">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">List of Leads </span>  <button type="button"   ng-click="ctrl.addLead()" class="btn btn-success  custom-width floatRight"> Add </button></div> 
		<div class="panel-body">
			<div class="table-responsive">
		        <table class="table table-hover table-responsive">
		            <thead>
		            <tr>
		                <th>ID</th>
		                <th>FIRSTNAME</th>
		                <th>LASTNAME</th>
		                <th width="100">GENDER</th>
		                <th width="100">DOB</th>
		                <th width="100">MEDICAID</th>
		                <th width="100">MEDICARE</th>
		                <th width="100">DISABILITY</th>
		            </tr>
		            </thead>
		            <tbody>
		            <tr ng-repeat="l in ctrl.getAllLeads()">
		                <td>{{l.id}}</td>
		                <td>{{l.firstName}}</td>
		                <td>{{l.lastName}}</td>
		                <td>{{l.genderId.description}}</td>
		                <td>{{l.dob}}</td>
		                <td>{{l.hasMedicaid}}</td>
		                <td>{{l.hasMedicare}}</td>
		                <td>{{l.hasDisability}}</td>
		                <td><button type="button" ng-click="ctrl.editLead(l.id)" class="btn btn-primary custom-width">Edit</button></td>
		                <td><button type="button" ng-click="ctrl.removeLead(l.id)" class="btn btn-danger custom-width">Remove</button></td>
		            </tr>
		            </tbody>
		        </table>		
			</div>
		</div>
    </div>
</div>