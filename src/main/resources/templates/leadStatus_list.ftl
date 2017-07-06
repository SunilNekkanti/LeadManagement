<div class="generic-container" >
    <div class="panel panel-default" ng-show="ctrl.display">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="leadStatus">Lead Status </span></div>
		<div class="panel-body">
	        <div class="formcontainer">
	            <div class="alert alert-success" leadStatus="alert" ng-if="ctrl.successMessage">{{ctrl.successMessage}}</div>
	            <div class="alert alert-danger" leadStatus="alert" ng-if="ctrl.errorMessage">{{ctrl.errorMessage}}</div>
	            <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
	                <input type="hidden" ng-model="ctrl.leadStatus.id" />
	                <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 col-md-offset-4 control-lable" for="uname">Lead Status</label>
	                        <div class="col-md-3">
	                            <input type="text" ng-model="ctrl.leadStatus.description" name="description" class="username form-control input-sm" placeholder="Enter Lead Status" required ng-minlength="5"/>
	                            <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.description.$error.required">This is a required field</span>
                                      <span ng-show="myForm.description.$error.minlength">Minimum length required is 5</span>
                                      <span ng-show="myForm.description.$invalid">This field is invalid </span>
                                  </div>
	                        </div>
	                    </div>
	                </div>

	                <div class="row">
	                    <div class="form-actions floatCenter col-md-offset-8">
	                        <input type="submit"  value="{{!ctrl.leadStatus.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid || myForm.$pristine">
	                        <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset Form</button>
	                    </div>
	                </div>
	            </form>
    	    </div>
		</div>	
    </div>
    <div class="panel panel-default" ng-hide="ctrl.display">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="leadStatus">List of Lead Statuss </span>  <button type="button"   ng-click="ctrl.addLeadStatus()" class="btn btn-success  custom-width floatRight"> Add </button></div> 
		<div class="panel-body">
			<div class="table-responsive">
		        <table class="table table-hover">
		            <thead>
		            <tr>
		                <th>ID</th>
		                <th>FACILITY TYPE</th>
		            </tr>
		            </thead>
		            <tbody>
		            <tr ng-repeat="ls in ctrl.getAllLeadStatuses()">
		                <td>{{ls.id}}</td>
		                <td>{{ls.description}}</td>
		                <td><button type="button" ng-click="ctrl.editLeadStatus(ls.id)" class="btn btn-primary custom-width">Edit</button></td>
		                <td><button type="button" ng-click="ctrl.removeLeadStatus(ls.id)" class="btn btn-danger custom-width">Remove</button></td>
		            </tr>
		            </tbody>
		        </table>		
			</div>
		</div>
    </div>
</div>