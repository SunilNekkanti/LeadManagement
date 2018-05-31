  
    
<div class="generic-container" >
   <div class="panel panel-success" ng-if="!ctrl.display">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="prvdr">List of Providers </span> 
               <button type="button"   ng-click="ctrl.addProvider();$event.preventDefault();" ng-hide="ctrl.displayEditButton" class="btn btn-success custom-width floatRight"> Add </button>   
               <button type="button" ng-click="ctrl.editProvider(ctrl.prvdrId);$event.preventDefault();" ng-show="ctrl.displayEditButton" class="btn btn-primary custom-width floatRight">Edit</button>  
              <button type="button" ng-click="ctrl.removeProvider(ctrl.prvdrId);$event.preventDefault();"  ng-show="ctrl.displayEditButton" class="btn btn-danger custom-width floatRight">Remove</button>  
        </div>
        <div class="table-responsive">
			<div class="panel-body">
			
	        	<table datatable="" id="content"   dt-options="ctrl.dtOptions"  dt-columns="ctrl.dtColumns" dt-instance="ctrl.dtInstance" class="table-responsive table  bordered table-striped table-condensed datatable "></table>
          </div>
		</div>
    </div>
     
    
    <div class="panel panel-success" ng-if="ctrl.display">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">Provider </span></div>
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
	                            <input type="text" ng-model="ctrl.prvdr.name" name="name" class="username form-control input-sm" placeholder="Enter your name" required ng-minlength="5"/>
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
	                        <label class="col-md-2 control-lable" for="ageseen">Age Seen</label>
	                        <div class="col-md-7">
	                            <input type="text" ng-model="ctrl.prvdr.ageSeen" name="ageSeen" class="form-control input-sm" placeholder="Enter Age Seen." required ng-minlength="6"/>
	                            <div class="has-error" ng-show="myForm.$dirty">
	                                  <span ng-show="myForm.ageSeen.$error.required">This is a required field</span>
                                      <span ng-show="myForm.ageSeen.$error.minlength">Minimum length required is 5</span>
                                      <span ng-show="myForm.ageSeen.$invalid">This field is invalid </span>
                                 </div>
	                        </div>
	                    </div>
	                </div>
	
	               <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="address">Address</label>
	                        <div class="col-md-7">
	                            <input type="text" ng-model="ctrl.prvdr.address" name="address" class="form-control input-sm" placeholder="Enter address" required ng-minlength="6"/>
	                            <div class="has-error" ng-show="myForm.$dirty">
	                                  <span ng-show="myForm.address.$error.required">This is a required field</span>
                                      <span ng-show="myForm.address.$error.minlength">Minimum length required is 5</span>
                                      <span ng-show="myForm.address.$invalid">This field is invalid </span>
                                 </div>
	                        </div>
	                    </div>
	                </div>
	                
	                 <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="hoursOfOperation">Hours of Operation</label>
	                        <div class="col-md-7">
	                            <input type="text" ng-model="ctrl.prvdr.hoursOfOperation" name="hoursOfOperation" class="form-control input-sm" placeholder="Enter Hours Of Operation" required ng-minlength="10"/>
	                            <div class="has-error" ng-show="myForm.$dirty">
	                                  <span ng-show="myForm.hoursOfOperation.$error.required">This is a required field</span>
                                      <span ng-show="myForm.hoursOfOperation.$error.minlength">Minimum length required is 5</span>
                                      <span ng-show="myForm.hoursOfOperation.$invalid">This field is invalid </span>
                                 </div>
	                        </div>
	                    </div>
	                </div>
	                
	                 <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="phone">Office Number</label>
	                        <div class="col-md-7">
	                            <input type="text" ng-model="ctrl.prvdr.phone" name="phone" class="form-control input-sm" placeholder="Enter Office Phone Number" required phone-input  ng-minlength="10" ng-maxlength="14"/>
	                            <div class="has-error" ng-show="myForm.$dirty">
	                                  <span ng-show="myForm.phone.$error.required">This is a required field</span>
                                      <span ng-show="myForm.phone.$error.minlength">Minimum length required is 5</span>
                                      <span ng-show="myForm.phone.$invalid">This field is invalid </span>
                                 </div>
	                        </div>
	                    </div>
	                </div>
	                
	                 <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="uname">Languages</label>
	                        <div class="col-md-7">
	                        <select ng-model="ctrl.prvdr.languages" name="languages" ng-options="language.description for language in ctrl.languages track by language.description" required multiple> </select>
	                           <div class="has-error" ng-show="myForm.$dirty">
	                                      <span ng-show="myForm.languages.$error.required">This is a required field</span>
	                            </div>
	                        </div>
	                    </div>
	                </div>

	                
	                  <div class="row">
	                    <div class="form-group col-md-12">
	                    </div>
	                </div>
	               
	                <div class="row">
	                    <div class="form-actions floatRight">
	                        <input type="submit"  value="{{!ctrl.prvdr.id ? 'Add' : 'Update'}}" ng-mousedown="$event.preventDefault();" class="btn btn-primary btn-xs" ng-disabled="myForm.$invalid || myForm.$pristine">
	                        <button type="button" ng-click="ctrl.reset();$event.preventDefault();" class="btn btn-warning btn-xs" ng-disabled="myForm.$pristine">Reset Form</button>
	                    </div>
	                </div>
	            </form>
    	    </div>
		</div>	
    </div>
    
    
</div>