  
    
<div class="generic-container" >
   <div class="panel panel-success" ng-if="!ctrl.display">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="prvdr">List of Insurances </span> 
               <button type="button"  ng-click="ctrl.addInsurance();$event.preventDefault();" ng-hide="ctrl.displayEditButton" class="btn btn-success custom-width floatRight"> Add </button>   
               <button type="button" ng-click="ctrl.editInsurance(ctrl.insuranceId);$event.preventDefault();" ng-show="ctrl.displayEditButton" class="btn btn-primary custom-width floatRight">Edit</button>  
              <button type="button" ng-click="ctrl.removeInsurance(ctrl.insuranceId);$event.preventDefault();"  ng-show="ctrl.displayEditButton" class="btn btn-danger custom-width floatRight">Remove</button>  
        </div>
        <div class="table-responsive">
			<div class="panel-body">
			
	        	<table datatable="" id="content"   dt-options="ctrl.dtOptions"  dt-columns="ctrl.dtColumns" dt-instance="ctrl.dtInstance" class="table-responsive table  bordered table-striped table-condensed datatable "></table>
          </div>
		</div>
    </div>
     
    
    <div class="panel panel-success" ng-if="ctrl.display">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">Insurance </span></div>
		<div class="panel-body">
	        <div class="formcontainer">
	            <div class="alert alert-success" role="alert" ng-if="ctrl.successMessage">{{ctrl.successMessage}}</div>
	            <div class="alert alert-danger" role="alert" ng-if="ctrl.errorMessage">{{ctrl.errorMessage}}</div>
	            <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
	                <input type="hidden" ng-model="ctrl.prvdr.id" />
	                <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="uname">Name</label>
	                        <div class="col-md-7">
	                            <input type="text" ng-model="ctrl.insurance.name" name="name" class="username form-control input-sm" placeholder="Enter your name" required ng-minlength="5"/>
	                             <div class="has-error" ng-show="myForm.$dirty">
	                                  <span ng-show="myForm.name.$error.required">This is a required field</span>
                                      <span ng-show="myForm.name.$error.minlength">Minimum length required is 5</span>
                                      <span ng-show="myForm.name.$invalid">This field is invalid </span>
                                  </div>
	                        </div>
	                    </div>
	                </div>

	                
	                  <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="planType">Plan Type</label>
	                        <div class="col-md-7">
	                        <select ng-model="ctrl.insurance.planType" name="planType"  ng-options="planType.description for planType in ctrl.planTypes track by planType.description" required ></select>
	                           <div class="has-error" ng-show="myForm.$dirty">
	                                      <span ng-show="myForm.planType.$error.required">This is a required field</span>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	               
	                <div class="row">
	                    <div class="form-actions floatRight">
	                        <input type="submit"  value="{{!ctrl.insurance.id ? 'Add' : 'Update'}}" ng-mousedown="$event.preventDefault();" class="btn btn-primary btn-xs" ng-disabled="myForm.$invalid || myForm.$pristine">
	                        <button type="button" ng-click="ctrl.reset();$event.preventDefault();" class="btn btn-warning btn-xs" ng-disabled="myForm.$pristine">Reset Form</button>
	                    </div>
	                </div>
	            </form>
    	    </div>
		</div>	
    </div>
    
    
</div>