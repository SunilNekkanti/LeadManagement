<div class="generic-container">

  <div class="panel panel-default" ng-hide="ctrl.display">
    <!-- Default panel contents -->
    <div class="panel-heading"><span class="user">List of Events </span>
      <button type="button" ng-click="ctrl.addEvent()" ng-hide="ctrl.displayEditButton" class="btn btn-success custom-width floatRight"> Add </button>
      <button type="button" ng-click="ctrl.editEvent(ctrl.eventId)" ng-show="ctrl.displayEditButton" class="btn btn-primary custom-width floatRight">Edit</button>
      <button type="button" ng-click="ctrl.removeEvent(ctrl.eventId)" ng-show="ctrl.displayEditButton" class="btn btn-danger custom-width floatRight">Remove</button>
    </div>
    <div class="panel-body">
      <div class="table-responsive">
        <table datatable="" id="content" dt-options="ctrl.dtOptions" dt-columns="ctrl.dtColumns" dt-instance="ctrl.dtInstance" class="table table-hover table-responsive  bordered table-striped table-condensed datatable "></table>

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
        
        
           <div class="panel-heading">
            <div class="form-actions floatCenter col-lg-offset-4">
              <input type="submit" value="{{!ctrl.event.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid || myForm.$pristine">
              <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset Form</button>
              <button type="button" ng-click="ctrl.repeat()" class="btn  btn-primary btn-sm" >Repeat</button>
            </div>
          </div>
          
          <input type="hidden" ng-model="ctrl.event.id" />
          
        <div class="form-group col-lg-6">
           
          <div class="row">
            <div class="form-group col-lg-12">
              <label class="col-lg-2  control-label"  for="eventName" require>Name </label>
              <div class="col-lg-10">
                <input type="text" ng-model="ctrl.event.eventName" id="eventName" name="eventName" class="username form-control input-sm" placeholder="Enter your name" required ng-minlength="5" />
                <div class="has-error" ng-show="myForm.$dirty">
                  <span ng-show="myForm.eventName.$error.required">This is a required field</span>
                  <span ng-show="myForm.eventName.$error.minlength">Minimum length required is 5</span>
                  <span ng-show="myForm.eventName.$invalid">This field is invalid </span>
                </div>
              </div>
            </div>
          </div>

          <div class="row">
            <div class="form-group col-lg-12">
              <label class="col-lg-2  control-label" for="eventTemplate">Location</label>
              <div class="col-lg-10">
                <select ng-model="ctrl.event.eventTemplate" name="eventTemplate" ng-options="eventTemplate.name for eventTemplate in ctrl.eventTemplates track by eventTemplate.name" required></select>
                <div class="has-error" ng-show="myForm.$dirty">
                  <span ng-show="myForm.eventTemplate.$error.required">This is a required field</span>
                </div>
              </div>
            </div>
          </div>

          <div class="row">
            <div class="form-group col-lg-12">
              <label class="col-lg-2  control-label" for="eventDateTime">StartTime</label>
              <div class="col-lg-4">
              	<div class="input-group date" id="eventDateStartTime" ng-model="ctrl.event.eventDateStartTime" date-picker>
                	<input type="text" class="form-control netto-input" ng-model="ctrl.event.eventDateStartTime" date-picker-input required>
                	<span class="input-group-addon">
           								<span class="glyphicon glyphicon-calendar"></span>
               		 </span>
                 </div>
              </div>
        
               <label class="col-lg-2  control-label" for="eventDateEndTime">EndTime</label>
               <div class="col-lg-4">
              		<div class="input-group date" id="eventDateEndTime" ng-model="ctrl.event.eventDateEndTime" date-picker>
                	<input type="text" class="form-control netto-input" ng-model="ctrl.event.eventDateEndTime" date-picker-input required>
                		<span class="input-group-addon">
           								<span class="glyphicon glyphicon-calendar"></span>
                		</span>
              		</div>
              	</div>
            </div>
          </div>

          <div class="row">
            <div class="form-group col-lg-12">
              <label class="col-lg-2  control-label" for="brokerage">Brokerage</label>
              <div class="col-lg-2">
              		<select class="col-lg-12" ng-model="ctrl.event.brokerage" name="brokerage" ng-options="brokerage.description for brokerage in ctrl.brokerages track by brokerage.description" required></select>
              		<div class="has-error" ng-show="myForm.$dirty">
                		<span ng-show="myForm.brokerage.$error.required">This is a required field</span>
              		</div>
              </div>
       
              <label class="col-lg-2  control-label" for="facilityType">Facility</label>
              <div class="col-lg-2">
              		<select class="col-lg-12" ng-model="ctrl.event.facilityType" name="facilityType" ng-options="facilityType.description for facilityType in ctrl.facilityTypes track by facilityType.description" required></select>
              		<div class="has-error" ng-show="myForm.$dirty">
                		<span ng-show="myForm.facilityType.$error.required">This is a required field</span>
             		 </div>
              </div>
      
              <label class="col-lg-2  control-label" for="activityType">Activity</label>
              <div class="col-lg-2">
                <select ng-model="ctrl.event.activityType" name="activityType" ng-options="activityType.description for activityType in ctrl.activityTypes track by activityType.description" required></select>
                <div class="has-error" ng-show="myForm.$dirty">
                  <span ng-show="myForm.activityType.$error.required">This is a required field</span>
                </div>
              </div>
            </div>
          </div>

          <div class="row">
            <div class="form-group col-lg-12">
              <label class="col-lg-2  control-label" for="agent">Representatives</label>
              <div class="col-lg-10">
                <select ng-model="ctrl.event.representatives" name="eventRepresentatives" ng-options=" agent.username for agent in ctrl.users   track by agent.username"  multiple required>
                </select>
              </div>
              {{ctrl.getEventRepEmails()}}
            </div>
          </div>
           

          <div class="row">
            <div class="form-group col-lg-12">
              <div require>
                <label class="col-lg-2 control-label" for="notes">Notes</label>
              </div>
              <div class="col-lg-10" >
                <textarea name="notes"  style="height:320px" class="form-control" id="notes" ng-model="ctrl.event.notes"></textarea>
              </div>
            </div>
          </div>
          
          <div class="row fileUploadSection">
	                    <div class="form-group col-lg-12 fileUploadRow">
	                        <div  require>  <label class="col-lg-2  control-label" for="contactEmail">FileUpload</label></div>
	                        <div class="col-lg-3">
	                            <input class="form-control input-sm"      type="file" name="files"  file-model="ctrl.myFiles" ng-model="ctrl.myFiles" multiple />
	                        
	                  		</div>
	                    </div>
	                </div>

       
          
          </div>
          
            <div class="form-group col-lg-6" ng-show="ctrl.repeatDisplay">
            
	              <div class="row">
		                <div class="form-group col-lg-12">
		             			 <label class="col-lg-2  control-label" for="frequency">Repeat</label>
							              <div class="col-lg-3"  ng-init="ctrl.event.frequency= ctrl.event.frequency||'DAILY'">
							              		<select class="col-sm-12"  ng-model="ctrl.event.frequency" name="frequency" ng-options="frequency as frequency for frequency in ctrl.eventFrequencies " ></select>
							              </div>


		             			 <label class="col-lg-1    control-label" for="interval">every</label>   
							              <div class="col-lg-2">
							              		<select class="col-sm-12"   ng-model="ctrl.event.interval" name="interval" ng-options="i as i for i  in  ctrl.eventIntervals" >
							              		</select>  </div>
							               <div class="col-lg-2">
							               <span ng-show="ctrl.event.frequency.id ==1"> day(s) </span>
							               <span ng-show="ctrl.event.frequency.id ==2"> week(s) on</span> 
							               	<span ng-show="ctrl.event.frequency.id ==3"> month(s) </span>
							               	<span ng-show="ctrl.event.frequency.id ==4"> year(s) </span>
							               </div>
			          </div>
            	</div>
            
            	 <div class="row" ng-show="ctrl.event.frequency =='WEEKLY'">
            	 
            	
            	  	<div class="form-group ">
		        		 <label ng-repeat="item in ctrl.eventOnWeekDays" class="item" id="{{item.shortName}}"> 
		        		 <input type="checkbox" ng-change="ctrl.sync(bool, item)" ng-model="bool" ng-checked="ctrl.isChecked(item.id)"> {{item.shortName}} 
		            	 </label>
		  			 </div>
            	 </div>
            	 
            	  
            	 <div ng-init="ctrl.onDayorThe= ctrl.onDayorThe||true"  ng-show="ctrl.event.frequency =='MONTHLY' || ctrl.event.frequency == 'YEARLY'">
            		 <div class="row">
	            	  	<div class="form-group col-lg-12">
			         	     <label class="col-lg-2 col-lg-offset-1">  
	    					   <input    type="radio" ng-model="ctrl.onDayorThe" ng-value="true">&nbspon<span ng-show="ctrl.event.frequency =='MONTHLY'">&nbspday</span>
	 					 	</label>
	 					 	 <div   class="form-group col-lg-9">
	 				  					<select ng-show="ctrl.event.frequency =='YEARLY'"  ng-disabled="ctrl.onDayorThe==false"   ng-model="ctrl.event.month" name="onMonth" ng-options="eventMonth.description for eventMonth  in  ctrl.eventMonths track by eventMonth.id" ></select>
 				  			           <select    ng-disabled="ctrl.onDayorThe==false"   ng-model="ctrl.event.onDay" name="ondays" ng-options="i as i for i  in  ctrl.eventOnDays" ></select>  
 						    </div>
 						 
			  			 </div>
			  		  </div>
			  		
            		<div class="row"  >
            	  		<div class="form-group col-lg-12">
		         		     <label class="col-lg-2 col-lg-offset-1">  
	    					  <input   type="radio" ng-model="ctrl.onDayorThe" ng-value="false">&nbspon the 
	 					 	</label>
 				 			<div class="col-lg-2" > 
 				  				<select    ng-disabled="ctrl.onDayorThe==true"  class="col-lg-12" ng-model="ctrl.eventOnWeek" name="onWeek" ng-options="i as i for i  in  ctrl.eventOnWeeks" ></select>  
 							 </div>
				        	<div class="col-lg-3" > 
 				  				<select     ng-disabled="ctrl.onDayorThe==true" class="col-lg-9" ng-model="ctrl.eventOnWeekDay" name="weekdays" ng-options="weekDay.id as weekDay.description for weekDay  in  ctrl.eventOnWeekDays track by weekDay.id" ></select>  
 						 	</div>
		  			 	</div>
            	 	</div>
            	</div>
            	
            	 <div class="row">
		                <div class="form-group col-lg-12">
		             			 <label class="col-lg-2  control-label" for="end">End</label>
							              <div class="col-lg-3">
							              		<select class="col-sm-8" ng-model="ctrl.eventEndOption" name="end" ng-options="i as i for i  in  ctrl.eventEndOptions" ></select>
							              </div>
							               <div ng-show="ctrl.eventEndOption=='After'" class="col-lg-1">
               									 <input type="text" ng-model="ctrl.eventEndCount" id="count" name="eventCount" class="username form-control input-sm" placeholder="Enter  count" required ng-minlength="1" />
               									 <div class="has-error" ng-show="myForm.$dirty">
                 									 <span ng-show="myForm.eventEndCount.$error.required">This is a required field</span>
                  									 <span ng-show="myForm.eventEndCount.$error.minlength">Minimum length required is 5</span>
                  									 <span ng-show="myForm.eventEndCount.$invalid">This field is invalid </span>
                								  </div>
							              </div>
							              
							            
	                   			
		  			 	</div>
            	 </div>
            	
            	 <div class="row"> rrule = {{ctrl.eventrrule()}} </div>
            </div>
            
          
        </form>
      </div>
    </div>
  </div>
</div>
 