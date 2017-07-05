  
    
<div class="generic-container" >
   <div class="panel panel-default" ng-hide="ctrl.display">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="prvdr">List of Providers </span> 
               <button type="button"   ng-click="ctrl.addProvider()" ng-hide="ctrl.displayEditButton" class="btn btn-success custom-width floatRight"> Add </button>   
               <button type="button" ng-click="ctrl.editProvider(ctrl.prvdrId)" ng-show="ctrl.displayEditButton" class="btn btn-primary custom-width floatRight">Edit</button>  
              <button type="button" ng-click="ctrl.removeProvider(ctrl.prvdrId)"  ng-show="ctrl.displayEditButton" class="btn btn-danger custom-width floatRight">Remove</button>  
        </div>
        <div class="table-responsive">
			<div class="panel-body">
			
	        	<table datatable="" id="content"   dt-options="ctrl.dtOptions"  dt-columns="ctrl.dtColumns" dt-instance="ctrl.dtInstance" class="table-responsive table  bordered table-striped table-condensed datatable "></table>
          </div>
		</div>
    </div>
     
    
    <div class="panel panel-default" ng-show="ctrl.display">
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
	                            <input type="text" ng-model="ctrl.prvdr.name" id="uname" class="username form-control input-sm" placeholder="Enter your name" required ng-minlength="5"/>
	                        </div>
	                    </div>
	                </div>

	                <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="ageseen">Age Seen</label>
	                        <div class="col-md-7">
	                            <input type="text" ng-model="ctrl.prvdr.ageSeen" id="ageSeen" class="form-control input-sm" placeholder="Enter Age Seen." required ng-minlength="6"/>
	                        </div>
	                    </div>
	                </div>
	
	               <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="address">Address</label>
	                        <div class="col-md-7">
	                            <input type="text" ng-model="ctrl.prvdr.address" id="address" class="form-control input-sm" placeholder="Enter address" required ng-minlength="6"/>
	                        </div>
	                    </div>
	                </div>
	                
	                 <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="hoursOfOperation">Hours of Operation</label>
	                        <div class="col-md-7">
	                            <input type="text" ng-model="ctrl.prvdr.hoursOfOperation" id="hoursOfOperation" class="form-control input-sm" placeholder="Enter Hours Of Operation" required ng-minlength="6"/>
	                        </div>
	                    </div>
	                </div>
	                
	                 <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="phone">Office Number</label>
	                        <div class="col-md-7">
	                            <input type="text" ng-model="ctrl.prvdr.phone" id="phone" class="form-control input-sm" placeholder="Enter Office Phone Number" required ng-minlength="6"/>
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
	                        <label class="col-md-2 control-lable" for="brokerage">Brokerage</label>
	                        <div class="col-md-7">
	                        <select ng-model="ctrl.prvdr.brokerages" ng-options="brokerage.description for brokerage in ctrl.brokerages track by brokerage.description" required multiple></select>
	                           <div class="has-error" ng-show="myForm.$dirty">
	                                      <span ng-show="myForm.brokerage.$error.required">This is a required field</span>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	               
	                <div class="row">
	                    <div class="form-actions floatRight">
	                        <input type="submit"  value="{{!ctrl.prvdr.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid || myForm.$pristine">
	                        <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset Form</button>
	                    </div>
	                </div>
	            </form>
    	    </div>
		</div>	
    </div>
    
    
</div>