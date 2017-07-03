<div class="generic-container" >

   <div class="panel panel-default" ng-hide="ctrl.display">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="user">List of Leads </span> 
               <button type="button"   ng-click="ctrl.addLead()" ng-hide="ctrl.displayEditButton" ng-show="ctrl.loginUser.roleName != 'ROLE_AGENT'" class="btn btn-success custom-width floatRight"> Add </button>   
               <button type="button" ng-click="ctrl.editLead(ctrl.leadId)" ng-show="ctrl.displayEditButton" class="btn btn-primary custom-width floatRight">Edit</button>  
              <button type="button" ng-click="ctrl.removeLead(ctrl.leadId)"  ng-show="ctrl.displayEditButton" class="btn btn-danger custom-width floatRight">Remove</button>  
        </div>
		<div class="panel-body">
			<div class="table-responsive">
	        <table datatable="" id="content"   dt-options="ctrl.dtOptions"  dt-columns="ctrl.dtColumns" dt-instance="ctrl.dtInstance" class="table table-hover table-responsive  bordered table-striped table-condensed datatable "></table>
	        
          </div>
		</div>
    </div>
     
     
    <div class="panel panel-default" ng-show="ctrl.display">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">Lead Information</span></div>
		<div class="panel-body">
	        <div class="formcontainer">
	            <div class="alert alert-success" role="alert" ng-if="ctrl.successMessage">{{ctrl.successMessage}}</div>
	            <div class="alert alert-danger" role="alert" ng-if="ctrl.errorMessage">{{ctrl.errorMessage}}</div>
	            <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
	                <input type="hidden" ng-model="ctrl.lead.id" />
	                <div class="row">
	                    <div class="form-group col-sm-4">
	                          <label class="col-sm-5  control-label" require for="firstName" require>First Name </label>  
	                        <div class="col-sm-7">
	                            <input type="text" ng-model="ctrl.lead.firstName" id="firstName" name="firstName" class="username form-control input-sm" placeholder="Enter firstname" required ng-minlength="5"/>
	                             <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.firstName.$error.required">This is a required field</span>
                                      <span ng-show="myForm.firstName.$error.minlength">Minimum length required is 5</span>
                                      <span ng-show="myForm.firstName.$invalid">This field is invalid </span>
                                  </div>
	                        </div>
	                    </div>
	                
	                    <div class="form-group col-sm-4">
	                        <div  require>  <label class="col-sm-5  control-label" for="lastName">Last Name</label></div>
	                        <div class="col-sm-7">
	                            <input type="text" ng-model="ctrl.lead.lastName" id="lastName" name="lastName" class="username  form-control input-sm" placeholder="Enter last name" required ng-minlength="5"/>
	                  			<div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.lastName.$error.required">This is a required field</span>
                                      <span ng-show="myForm.lastName.$error.minlength">Minimum length required is 5</span>
                                      <span ng-show="myForm.lastName.$invalid">This field is invalid </span>
                                  </div>      
	                        </div>
	                    </div>
	                
	                    <div class="form-group col-sm-4">
	                        <label class="col-sm-5  control-label" for="dob">Date Of Birth</label>
	                        <div class="col-sm-7">
	                            <div class="input-group date" id="dob"   ng-model="ctrl.lead.dob" date1-picker>
         							<input type="text" class="form-control netto-input"  ng-model="ctrl.lead.dob" date-picker-input>

         							<span class="input-group-addon">
           								<span class="glyphicon glyphicon-calendar"></span>
         							</span>
       							</div>
	                            
	                        </div>
	                    </div>
	                </div>
	                
	                <div class="row">
	                    <div class="form-group col-sm-4">
	                        <label class="col-sm-5  control-label" for="gender">Gender</label>
	                        <div class="col-sm-7">
		                        <select ng-model="ctrl.lead.genderId" name="gender"  ng-options="gender.description for gender in ctrl.genders track by gender.description" required></select>
		                        <div class="has-error" ng-show="myForm.$dirty">
	                                      <span ng-show="myForm.gender.$error.required">This is a required field</span>
	                            </div>      
	                        </div>
	                        
	                    </div>
	               
	                    <div class="form-group col-sm-4">
	                        <label class="col-sm-5  control-label" for="language">Language</label>
	                        <div class="col-sm-7">
	                        	<select ng-model="ctrl.lead.language" name="language" ng-options="language.description for language in ctrl.languages track by language.description" required></select>
	                           <div class="has-error" ng-show="myForm.$dirty">
	                                      <span ng-show="myForm.language.$error.required">This is a required field</span>
	                            </div>
	                        </div>
	                    </div>
	                    
	                    <div class="form-group col-sm-4">
	                        <label class="col-sm-5  control-label" for="planType">Plan Type</label>
	                        <div class="col-sm-7">
	                          <select ng-model="ctrl.lead.planType" ng-options="planType.description for planType in ctrl.planTypes track by planType.description"  required></select>
	                          <div class="has-error" ng-show="myForm.$dirty">
	                                      <span ng-show="myForm.language.$error.required">This is a required field</span>
	                            </div>
	                        </div>
	                    </div>
	                    
	                    <div class="form-group col-sm-4">
	                        <label class="col-sm-5  control-label" for="insurance">Current Plan</label>
	                        <div class="col-sm-7">
	                        <select ng-model="ctrl.lead.leadMbrInsuranceList" ng-options="insurance.name for insurance in ctrl.insurances | filter :{planType:{id:ctrl.lead.planType.id}} track by insurance.name" ></select>
	                        </div>
	                    </div>
	                </div>
	               
	               <div class="row">
	                      
	                      <div class="form-group col-sm-4">
	                        <label class="col-sm-5  control-label" for="status">Status</label>
	                        <div class="col-sm-7">
	                        <select ng-model="ctrl.lead.status" ng-options="status.description for status in ctrl.statuses track by status.description" required></select>
	                        </div>
	                    </div>

	                </div>
	                
	                 
	                
	              <div class="row">
	                  
	                    
	                    <div class="form-group col-sm-4">
	                        <label class="col-sm-5  control-label" for="consentFormSigned">Agree Consent Form </label>
	                        <div class="col-sm-7">
	                        	<input type="checkbox"  ng-model="ctrl.lead.consentFormSigned"   name="consentFormSigned"     ng-true-value="'Y'"     ng-false-value="'N'" required>
	                        	<div class="has-error" ng-show="myForm.$dirty">
	                                      <span ng-show="myForm.consentFormSigned.$error.required">This is a required field</span>
	                            </div>
	                        </div>
	                    </div>    


	                 
	                   <div class="form-group col-sm-4">
					                <div class="form-group">
					                    <input class="col-sm-5  control-label" name="fileUpload" type="file" file-model="ctrl.myFile"  class="form-control" id ="myFileField" ng-model="ctrl.myFile" required/>
					                    
					                    <div class="has-error" ng-show="myForm.$dirty">
	                                      <span ng-show="myForm.fileUpload.$error.required">This is a required field</span>
	                                    </div>
					                </div>
					               <!-- <button ng-click="ctrl.uploadFile()" class = "btn btn-primary">Upload File</button> -->
					   </div>
					   
	             </div>
	             
	                 
	                
	             <div class="row col-sm-offset-1 col-sm-10" ng-show="ctrl.lead.status.id == 2">
	             	<div class="panel panel-default">
					    <div class="panel-heading">More Details for Converted</div>
					    <div class="panel-body">
					    		<div class="form-group col-sm-4">
					    			<label class="col-sm-5    control-label" for="uname">PCP Name</label>
	                        		<div class="col-sm-7">
	                           			<select ng-model="ctrl.selectedAgentLeadAppointment.prvdr" ng-options="prvdr.name for prvdr in ctrl.providers track by prvdr.name" required="ctrl.lead.status.id == 2"></select>
	                       			 </div>
					    	   </div>
					    	   
					    	   <div class="form-group col-sm-4">
					    			<label class="col-sm-5    control-label" for="Plan">Plan</label>
	                        		<div class="col-sm-7">
	                           			<select ng-model="ctrl.selectedAgentLeadAppointment.planType" ng-options="planType.description for planType in ctrl.planTypes track by planType.description" required="ctrl.lead.status.id == 2"></select>
	                       			 </div>
					    	   </div>
					    	   
					    	   <div class="form-group col-sm-4">
					    			<label class="col-sm-5    control-label" for="effectiveDate">Effective Date</label>
	                        		<div class="col-sm-7">
	                           			<div class="input-group date" id="besttime"  ng-model="ctrl.selectedAgentLeadAppointment.effectiveFrom" date-picker>
         								<input type="text" class="form-control netto-input"  ng-model="ctrl.selectedAgentLeadAppointment.effectiveFrom" date-picker-input required="ctrl.lead.status.id == 2">

         							<span class="input-group-addon">
           								<span class="glyphicon glyphicon-calendar"></span>
         							</span>
       							</div>
	                       			 </div>
					    	   </div>
					    	   
					    </div>
					</div>
	             
	             </div>
	            </div>
	      	</div>	 
	      </div>	         
	     <div class="panel panel-default" ng-show="ctrl.display">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">Contact Details </span></div>
		<div class="panel-body">            
	                <div class="row">
	                    <div class="form-group col-sm-4">
	                        <label class="col-sm-5  control-label" for="homePhone">Home Phone</label>
	                        <div class="col-sm-7">
	                        <input type="text" ng-model="ctrl.lead.homePhone" id="homePhone" class="username form-control input-sm" placeholder="Enter Home phone" required ng-minlength="10" ng-maxlength="15"/>
	                        </div>
	                        
	                    </div>
	                
	                    <div class="form-group col-sm-4">
	                        <label class="col-sm-5  control-label" for="mobilePhone">Mobile Phone</label>
	                        <div class="col-sm-7">
	                        <input type="text" ng-model="ctrl.lead.mobilePhone" id="mobilePhone" class="username form-control input-sm" placeholder="Enter Mobile phone"   ng-minlength="10" ng-maxlength="15"/>
	                        </div>
	                    </div>
	                    
	                     <div class="form-group col-sm-4">
		                    <label class="col-sm-5 control-label" for="homePhone">Best Time to Call</label>
		                        <div class="col-sm-7">
		                         	<div class="input-group date" id="besttime"  ng-model="ctrl.lead.bestTimeToCall" date-picker>
	         							<input type="text" class="form-control netto-input"  ng-model="ctrl.lead.bestTimeToCall" date-picker-input>
	
	         							<span class="input-group-addon">
	           								<span class="glyphicon glyphicon-calendar"></span>
	         							</span>
	       							</div>
		                        </div>
	               		</div>
	               	</div>	
	                
	               <div class="row">
	                    <div class="form-group col-sm-4">
	                        <label class="col-sm-5  control-label" for="email">Email</label>
	                        <div class="col-sm-7">
	                        <input type="text" ng-model="ctrl.lead.email" id="email" class="username form-control input-sm" placeholder="Enter Email"   ng-minlength="6" ng-maxlength="100"/>
	                        </div>
	                    </div>
	                
	                    <div class="form-group col-sm-4">
	                        <label class="col-sm-5  control-label" for="address1">Address 1</label>
	                        <div class="col-sm-7">
	                        <input type="text" ng-model="ctrl.lead.address1" id="address1" class="username form-control input-sm" placeholder="Enter Address" required ng-minlength="6" ng-maxlength="100"/>
	                        </div>
	                    </div>
	              
	                    <div class="form-group col-sm-4">
	                        <label class="col-sm-5  control-label" for="addres2">Address 2</label>
	                        <div class="col-sm-7">
	                        <input type="text" ng-model="ctrl.lead.address2" id="addres2" class="username form-control input-sm" placeholder="Enter Address"   ng-maxlength="10"/>
	                        </div>
	                    </div>
	                </div>
	                
	                  <div class="row">
	                    <div class="form-group col-sm-4">
	                        <label class="col-sm-5  control-label" for="city">City</label>
	                        <div class="col-sm-7">
	                        <input type="text" ng-model="ctrl.lead.city" id="city" class="username form-control input-sm" placeholder="Enter City"  required  ng-minlength="4" ng-maxlength="100"/>
	                        </div>
	                    </div>
	                
	                    <div class="form-group col-sm-4">
	                        <label class="col-sm-5  control-label" for="uname">State</label>
	                        <div class="col-sm-7">
	                        <select ng-model="ctrl.lead.stateCode" ng-options="state.description for state in ctrl.states track by state.description" required></select>
	                        </div>
	                    </div>
	                
	                    <div class="form-group col-sm-4">
	                        <label class="col-sm-5  control-label" for="zipCode">Zipcode</label>
	                        <div class="col-sm-7">
	                        <select ng-model="ctrl.lead.zipCode" ng-options="zipCode.code for zipCode in ctrl.lead.stateCode.zipCodes track by zipCode.code" required></select>
	                        </div>
	                    </div>
	                </div>
	             </div>
		      </div>	
	                
	                 <div class="panel panel-default" ng-show="ctrl.display">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">Agent Assignment </span></div>
		<div class="panel-body">
		
	                <div class="row" ng-show="ctrl.loginUser.roleName != 'ROLE_AGENT'">
	                    <div class="form-group col-sm-4">
	                        <label class="col-sm-5  control-label" for="eventName">Source</label>
	                        <div class="col-sm-7">
	                        <select ng-model="ctrl.lead.event" ng-options="event.eventName for event in ctrl.events track by event.eventName" required></select>
	                        </div>
	                    </div>
	                
	                    <div class="form-group col-sm-4">
	                        <label class="col-sm-5  control-label" for="homePhone">Agent</label>
	                        <div class="col-sm-7">
	                         	<select ng-model="ctrl.selectedAgentLeadAppointment.user"  ng-options="agent.username for agent in ctrl.users  | filter:{roles:[{role:'ROLE_AGENT'}]} track by agent.username"  required></select>
	                        </div>
	                     </div>
	                     <div class="form-group col-sm-4">
	                        <label class="col-sm-5 control-label" for="appointment">Appointment Time</label>
	                        <div class="col-sm-7">
	                        	<div class="input-group date" id="appointment"   ng-model="ctrl.selectedAgentLeadAppointment.appointmentTime" date-picker>
         							<input type="text" class="form-control netto-input"  ng-model="ctrl.selectedAgentLeadAppointment.appointmentTime" date-picker-input>

         							<span class="input-group-addon">
           								<span class="glyphicon glyphicon-calendar"></span>
         							</span>
       							</div>
	                        </div>
	                    </div>
	                </div>
	                
	                <div class="row">
	                    <div class="form-actions floatCenter col-sm-offset-8">
	                        <input type="submit"  value="{{!ctrl.lead.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" ng-disabled="!ctrl.myFile || ctrl.lead.consentFormSigned == 'N' || myForm.$invalid || myForm.$pristine">
	                        <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset Form</button>
	                    </div>
	                </div>
	            </form>
    	    </div>
		</div>	
    </div>

</div>