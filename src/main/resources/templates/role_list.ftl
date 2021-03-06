
<div class="generic-container">
   <div class="panel panel-success" ng-if="!ctrl.display">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="role">List of Roles </span> 
               <button type="button"  ng-click="ctrl.addRole();$event.preventDefault();" ng-hide="ctrl.displayEditButton" class="btn btn-success btn-xs custom-width floatRight"> Add </button>   
               <button type="button" ng-click="ctrl.editRole(ctrl.facilityTypeId);$event.preventDefault();" ng-show="ctrl.displayEditButton" class="btn btn-primary btn-xs  custom-width floatRight">Edit</button>  
              <button type="button" ng-click="ctrl.removeRole(ctrl.facilityTypeId);$event.preventDefault();"  ng-show="ctrl.displayEditButton" class="btn btn-danger  btn-xs custom-width floatRight">Remove</button>  
        </div>
        <div class="table-responsive">
			<div class="panel-body">
	        	<table datatable="" id="content"   dt-options="ctrl.dtOptions"  dt-columns="ctrl.dtColumns" dt-instance="ctrl.dtInstance" class="table-responsive table  bordered table-striped table-condensed datatable "  cellspacing="0" width="100%" ></table>
            </div>
		</div>
    </div>
    
    <div class="panel panel-success" ng-if="ctrl.display">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="role">Role </span></div>
		<div class="panel-body">
	        <div class="formcontainer">
	            <div class="alert alert-success" role="alert" ng-if="ctrl.successMessage">{{ctrl.successMessage}}</div>
	            <div class="alert alert-danger" role="alert" ng-if="ctrl.errorMessage">{{ctrl.errorMessage}}</div>
	            <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
	                <input type="hidden" ng-model="ctrl.role.id" />
	                <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-1 col-md-offset-4 control-lable" for="uname">Role</label>
	                        <div class="col-md-3">
	                            <input type="text" ng-model="ctrl.role.role" id="uname" class="username form-control input-sm" placeholder="Enter Role name" required ng-minlength="4"/>
	                        </div>
	                    </div>
	                </div>

	                <div class="row">
	                    <div class="form-actions floatCenter col-md-offset-8">
	                        <input type="submit"  value="{{!ctrl.role.id ? 'Add' : 'Update'}}" ng-mousedown="$event.preventDefault();" class="btn btn-primary btn-xs" ng-disabled="myForm.$invalid || myForm.$pristine">
	                        <button type="button" ng-click="ctrl.reset();$event.preventDefault();" class="btn btn-warning btn-xs" ng-show="!ctrl.role.id" ng-disabled="myForm.$pristine">Reset Form</button>
	                        <button type="button" ng-click="ctrl.cancelEdit();$event.preventDefault();" class="btn btn-warning btn-xs"  >Cancel</button>
	                    </div>
	                </div>
	            </form>
    	    </div>
		</div>	
    </div>
   
   
</div>