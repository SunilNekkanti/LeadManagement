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
	                        <label class="col-sm-2  control-label" for="gender">Brokerage</label>
	                        <div class="col-sm-3">
	                        <select ng-model="ctrl.event.brokerage" ng-options="brokerage.description for brokerage in ctrl.brokerages track by brokerage.description" required></select>
	                        </div>
	                    </div>
	                </div>
	               
	                
	                 <div class="row">
	                    <div class="form-group col-sm-12">
	                        <label class="col-sm-2  control-label" for="language">Facility Type</label>
	                        <div class="col-sm-3">
	                        <select ng-model="ctrl.event.facilityType" ng-options="facilityType.description for facilityType in ctrl.facilityTypes track by facilityType.description" required></select>
	                        </div>
	                    </div>
	                </div>
	               
	                 <div class="row">
	                    <div class="form-group col-sm-12">
	                        <label class="col-sm-2  control-label" for="homePhone">Representative</label>
	                        <div class="col-sm-3">
	                         <select ng-model="ctrl.event.agent"  ng-options="agent.username for agent in ctrl.users | filter :{roles:{role:'ROLE_AGENT'}}  track by agent.username"  required></select>
	                        </div>
	                    </div>
	                </div>
	              
	                	 <div class="row">
	                    <div class="form-group col-sm-12">
	                        <div  require>  <label class="col-sm-2  control-label" for="venue">Venue Name</label></div>
	                        <div class="col-sm-3">
	                            <input type="text" ng-model="ctrl.event.venue" id="venue" name="venue" class="username  form-control input-sm" placeholder="Enter your name" required ng-minlength="5"/>
	                  			<div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.venue.$error.required">This is a required field</span>
                                      <span ng-show="myForm.venue.$error.minlength">Minimum length required is 5</span>
                                      <span ng-show="myForm.venue.$invalid">This field is invalid </span>
                                  </div>      
	                        </div>
	                    </div>
	                </div>
	                
	             
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