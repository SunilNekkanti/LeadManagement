<div class="generic-container" >
    <div class="panel panel-default" ng-show="ctrl.display">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="facilityType">Facility Type </span></div>
		<div class="panel-body">
	        <div class="formcontainer">
	            <div class="alert alert-success" facilityType="alert" ng-if="ctrl.successMessage">{{ctrl.successMessage}}</div>
	            <div class="alert alert-danger" facilityType="alert" ng-if="ctrl.errorMessage">{{ctrl.errorMessage}}</div>
	            <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
	                <input type="hidden" ng-model="ctrl.facilityType.id" />
	                <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 col-md-offset-4 control-lable" for="uname">Facility Type</label>
	                        <div class="col-md-3">
	                            <input type="text" ng-model="ctrl.facilityType.description" id="uname" class="username form-control input-sm" placeholder="Enter Facility Type" required ng-minlength="4"/>
	                        </div>
	                    </div>
	                </div>

	                <div class="row">
	                    <div class="form-actions floatCenter col-md-offset-8">
	                        <input type="submit"  value="{{!ctrl.facilityType.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid || myForm.$pristine">
	                        <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset Form</button>
	                    </div>
	                </div>
	            </form>
    	    </div>
		</div>	
    </div>
    <div class="panel panel-default" ng-hide="ctrl.display">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="facilityType">List of Facility Types </span>  <button type="button"   ng-click="ctrl.addFacilityType()" class="btn btn-success  custom-width floatRight"> Add </button></div> 
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
		            <tr ng-repeat="r in ctrl.getAllFacilityTypes()">
		                <td>{{r.id}}</td>
		                <td>{{r.description}}</td>
		                <td><button type="button" ng-click="ctrl.editFacilityType(r.id)" class="btn btn-primary custom-width">Edit</button></td>
		                <td><button type="button" ng-click="ctrl.removeFacilityType(r.id)" class="btn btn-danger custom-width">Remove</button></td>
		            </tr>
		            </tbody>
		        </table>		
			</div>
		</div>
    </div>
</div>