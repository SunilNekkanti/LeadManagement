<div class="generic-container" >

   <div class="panel panel-default" ng-hide="ctrl.display">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="user">List of Events </span> 
               <button type="button"   ng-click="ctrl.addEvent()" ng-hide="ctrl.displayEditButton" class="btn btn-success custom-width floatRight"> Add </button>   
               <button type="button" ng-click="ctrl.editEvent(ctrl.eventId)" ng-show="ctrl.displayEditButton" class="btn btn-primary custom-width floatRight">Edit</button>  
              <button type="button" ng-click="ctrl.removeEvent(ctrl.eventId)"  ng-show="ctrl.displayEditButton" class="btn btn-danger custom-width floatRight">Remove</button>  
        </div>
		<div class="panel-body">
			<div class="table-responsive">
	        <table datatable="" id="content"   dt-options="ctrl.dtOptions"  dt-columns="ctrl.dtColumns" dt-instance="ctrl.dtInstance" class="table table-hover table-responsive  bordered table-striped table-condensed datatable "></table>
	        
          </div>
		</div>
    </div>
     
     
    <div class="panel panel-default" ng-show="ctrl.display">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="event">Event </span></div>
		<div class="panel-body">
	        <div class="formcontainer">
	            <div class="alert alert-success" role="alert" ng-if="ctrl.successMessage">{{ctrl.successMessage}}</div>
	            <div class="alert alert-danger" role="alert" ng-if="ctrl.errorMessage">{{ctrl.errorMessage}}</div>
	            <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
	                <input type="hidden" ng-model="ctrl.event.id" />
	                <div class="row">
	                    <div class="form-group col-sm-12">
	                          <label class="col-sm-2  control-label" require for="eventName" require>Event Name </label>  
	                        <div class="col-sm-3">
	                            <input type="text" ng-model="ctrl.event.eventName" id="eventName" name="eventName" class="username form-control input-sm" placeholder="Enter your name" required ng-minlength="5"/>
	                             <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.eventName.$error.required">This is a required field</span>
                                      <span ng-show="myForm.eventName.$error.minlength">Minimum length required is 5</span>
                                      <span ng-show="myForm.eventName.$invalid">This field is invalid </span>
                                  </div>
	                        </div>
	                    </div>
	                </div>
	                
	                 <div class="row">
	                    <div class="form-group col-sm-12">
	                        <label class="col-sm-2  control-label" for="eventDateTime">Event DateTime</label>
	                        <div class="col-sm-3">
	                           <div class="input-group date" id="eventDateTime"  ng-model="ctrl.event.eventDateTime" date-picker>
         							<input type="text" class="form-control netto-input"  ng-model="ctrl.event.eventDateTime" date-picker-input required>

         							<span class="input-group-addon">
           								<span class="glyphicon glyphicon-calendar"></span>
         							</span>
	                           </div>
	                        </div>
	                    </div>
	                </div>
	                
	                <div class="row">
	                    <div class="form-group col-sm-12">
	                        <label class="col-sm-2  control-label" for="brokerage">Brokerage</label>
	                        <div class="col-sm-3">
	                        <select ng-model="ctrl.event.brokerage" ng-options="brokerage.description for brokerage in ctrl.brokerages track by brokerage.description" required></select>
	                        </div>
	                    </div>
	                </div>
	               
	                
	                 <div class="row">
	                    <div class="form-group col-sm-12">
	                        <label class="col-sm-2  control-label" for="facilityType">Facility Type</label>
	                        <div class="col-sm-3">
	                        <select ng-model="ctrl.event.facilityType" ng-options="facilityType.description for facilityType in ctrl.facilityTypes track by facilityType.description" required></select>
	                        </div>
	                    </div>
	                </div>
	               
	                 <div class="row">
	                    <div class="form-group col-sm-12">
	                        <label class="col-sm-2  control-label" for="agent">Representative</label>
	                        <div class="col-sm-3">
	                         <select ng-model="ctrl.event.agent"  ng-options="agent.username for agent in ctrl.users | filter:{roles:[{role:'ROLE_AGENT'}]}   track by agent.username"  required></select>
	                        </div>
	                    </div>
	                </div>
	              
	                <div class="row">
	                    <div class="form-group col-sm-12">
	                        <div  require>  <label class="col-sm-2  control-label" for="address1">Venue Address1</label></div>
	                        <div class="col-sm-3">
	                            <input type="text" ng-model="ctrl.event.address1" id="address1" name="address1" class="username  form-control input-sm" placeholder="Enter venue address1" required ng-minlength="5"/>
	                  			<div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.address1.$error.required">This is a required field</span>
                                      <span ng-show="myForm.address1.$error.minlength">Minimum length required is 5</span>
                                      <span ng-show="myForm.address1.$invalid">This field is invalid </span>
                                  </div>      
	                        </div>
	                    </div>
	                </div>
	                
	                <div class="row">
	                    <div class="form-group col-sm-12">
	                        <div  require>  <label class="col-sm-2  control-label" for="address2">Venue Address2</label></div>
	                        <div class="col-sm-3">
	                            <input type="text" ng-model="ctrl.event.address2" id="address2" name="address2" class="username  form-control input-sm" placeholder="Enter venue address2" required ng-minlength="5"/>
	                  			<div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.address2.$error.required">This is a required field</span>
                                      <span ng-show="myForm.address2.$error.minlength">Minimum length required is 5</span>
                                      <span ng-show="myForm.address2.$invalid">This field is invalid </span>
                                  </div>      
	                        </div>
	                    </div>
	                </div>
	                
	                <div class="row">
	                    <div class="form-group col-sm-12">
	                        <div  require>  <label class="col-sm-2  control-label" for="city">Venue City</label></div>
	                        <div class="col-sm-3">
	                            <input type="text" ng-model="ctrl.event.city" id="city" name="city" class="username  form-control input-sm" placeholder="Enter city" required ng-minlength="5"/>
	                  			<div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.city.$error.required">This is a required field</span>
                                      <span ng-show="myForm.city.$error.minlength">Minimum length required is 5</span>
                                      <span ng-show="myForm.city.$invalid">This field is invalid </span>
                                  </div>      
	                        </div>
	                    </div>
	                </div>
	             
	             <div class="row">
	                   <div class="form-group col-sm-4">
	                        <label class="col-sm-5  control-label" for="uname">Venue State</label>
	                        <div class="col-sm-7">
	                        <select ng-model="ctrl.event.state" ng-options="state.description for state in ctrl.states track by state.description" required></select>
	                        </div>
	                    </div>
	                </div>
	                
	                <div class="row">
	                   <div class="form-group col-sm-4">
	                        <label class="col-sm-5  control-label" for="uname">Zipcode</label>
	                        <div class="col-sm-7">
	                        <select ng-model="ctrl.event.zipCode" ng-options="zipCode.code for zipCode in ctrl.event.state.zipCodes track by zipCode.code" required></select>
	                        </div>
	                    </div>
	                </div>
	                
	                <div class="row">
	                    <div class="form-group col-sm-12">
	                        <div  require>  <label class="col-sm-2  control-label" for="contactPerson">Contact Person</label></div>
	                        <div class="col-sm-3">
	                            <input type="text" ng-model="ctrl.event.contactPerson" id="contactPerson" name="contactPerson" class="username  form-control input-sm" placeholder="Enter venue contactPerson" required ng-minlength="5"/>
	                  			<div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.contactPerson.$error.required">This is a required field</span>
                                      <span ng-show="myForm.contactPerson.$error.minlength">Minimum length required is 5</span>
                                      <span ng-show="myForm.contactPerson.$invalid">This field is invalid </span>
                                  </div>      
	                        </div>
	                    </div>
	                </div>
	                
	                <div class="row">
	                    <div class="form-group col-sm-12">
	                        <div  require>  <label class="col-sm-2  control-label" for="contactPhone">Contact Phone</label></div>
	                        <div class="col-sm-3">
	                            <input type="text" ng-model="ctrl.event.contactPhone" id="contactPhone" name="contactPhone" class="username  form-control input-sm" placeholder="Enter venue contactPhone" required ng-minlength="5"/>
	                  			<div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.contactPhone.$error.required">This is a required field</span>
                                      <span ng-show="myForm.contactPhone.$error.minlength">Minimum length required is 5</span>
                                      <span ng-show="myForm.contactPhone.$invalid">This field is invalid </span>
                                  </div>      
	                        </div>
	                    </div>
	                </div>
	                
	                {{myForm.$invalid  }} {{ myForm.$pristine}}
	                <div class="row">
	                    <div class="form-actions floatCenter col-sm-offset-8">
	                        <input type="submit"  value="{{!ctrl.event.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid || myForm.$pristine">
	                        <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset Form</button>
	                    </div>
	                </div>
	            </form>
    	    </div>
		</div>	
    </div>

</div>