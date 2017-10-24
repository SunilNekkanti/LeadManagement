<div class="generic-container" >

   <div class="panel panel-default" ng-if="!ctrl.display">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="facilityType">List of Lead Status Details </span> 
               <button type="button"  ng-click="ctrl.addLeadStatusDetail()" ng-hide="ctrl.displayEditButton" class="btn btn-success btn-xs  custom-width floatRight"> Add </button>   
               <button type="button" ng-click="ctrl.editLeadStatusDetail(ctrl.leadStatusDetailId)" ng-show="ctrl.displayEditButton" class="btn btn-primary btn-xs  custom-width floatRight">Edit</button>  
              <button type="button" ng-click="ctrl.removeLeadStatusDetaile(ctrl.leadStatusDetailId)"  ng-show="ctrl.displayEditButton" class="btn btn-danger btn-xs  custom-width floatRight">Remove</button>  
        </div>
        <div class="table-responsive">
			<div class="panel-body">
	        	<table datatable="" id="content"   dt-options="ctrl.dtOptions"  dt-columns="ctrl.dtColumns" dt-instance="ctrl.dtInstance" class="table-responsive table  bordered table-striped table-condensed datatable "  cellspacing="0" width="100%" ></table>
            </div>
		</div>
    </div>
    
    <div class="panel panel-default" ng-if="ctrl.display">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="leadStatusDetail">Lead Status Detail</span></div>
		<div class="panel-body">
	        <div class="formcontainer">
	            <div class="alert alert-success" leadStatusDetail="alert" ng-if="ctrl.successMessage">{{ctrl.successMessage}}</div>
	            <div class="alert alert-danger" leadStatusDetail="alert" ng-if="ctrl.errorMessage">{{ctrl.errorMessage}}</div>
	            <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
	                <input type="hidden" ng-model="ctrl.leadStatusDetail.id" />
	                <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 col-md-offset-4 control-lable" for="uname">Lead Status Detail</label>
	                        <div class="col-md-3">
	                            <input type="text" ng-model="ctrl.leadStatusDetail.description" name="description" class="username form-control input-sm" placeholder="Enter Lead Status" required ng-minlength="5"/>
	                            <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.description.$error.required">This is a required field</span>
                                      <span ng-show="myForm.description.$error.minlength">Minimum length required is 5</span>
                                      <span ng-show="myForm.description.$invalid">This field is invalid </span>
                                  </div>
	                        </div>
	                    </div>
	                </div>
	                
	                
	              <div class="row">
                     <div class="form-group col-sm-12">
                      <div class="col-md-2 col-md-offset-4 ">
                        <label  class=""col-md-2  control-lable" for="agentEvent">Lead Status </label>
                        </div>
                          <div class="col-md-3">
                             <select ng-model="ctrl.leadStatusDetail.leadStatus" class="form-control input-sm" name="leadStatus" ng-options="leadStatus.description for leadStatus in ctrl.leadStatuses  | orderBy:'description'  track by leadStatus.description" required>
                            <option></option>
                          </select>
                           <div class="has-error" ng-show="myForm.$dirty">
                              <span ng-show="myForm.leadStatus.$error.required">This is a required field</span>
                           </div>
                          </div> 
                      </div>
                 </div>
                

	                <div class="row">
	                    <div class="form-actions floatCenter col-md-offset-8">
	                        <input type="submit"  value="{{!ctrl.leadStatusDetail.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-xs" ng-disabled="myForm.$invalid || myForm.$pristine">
	                        <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-xs" ng-show="!ctrl.leadStatusDetail.id" ng-disabled="myForm.$pristine">Reset Form</button>
	                        <button type="button" ng-click="ctrl.cancelEdit()" class="btn btn-warning btn-xs" ng-show="ctrl.leadStatusDetail.id" >Cancel</button>
	                    </div>
	                </div>
	            </form>
    	    </div>
		</div>	
    </div>
    
</div>