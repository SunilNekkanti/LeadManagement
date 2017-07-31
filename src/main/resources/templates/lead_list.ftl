<div class="generic-container">
	<div class="panel panel-default" ng-hide="ctrl.display">
    	<!-- Default panel contents -->
    	<div class="panel-heading"><span class="user">List of Leads </span>
      		<button type="button" ng-click="ctrl.addLead()" ng-hide="ctrl.displayEditButton" ng-show="ctrl.loginUser.roleName != 'ROLE_AGENT'" class="btn btn-success  btn-xs  custom-width floatRight"> Add </button>
      		<button type="button" ng-click="ctrl.editLead(ctrl.leadId)" ng-show="ctrl.displayEditButton" class="btn btn-primary btn-xs custom-width floatRight">Edit</button>
      		<button type="button" ng-click="ctrl.removeLead(ctrl.leadId)" ng-show="ctrl.displayEditButton" class="btn btn-danger btn-xs  custom-width floatRight">Remove</button>
    	</div>
    
    	<div class="panel-body">
      		<div class="table-responsive">
        		<table datatable="" id="content" dt-options="ctrl.dtOptions" dt-columns="ctrl.dtColumns" dt-instance="ctrl.dtInstance" class="table table-hover table-responsive  bordered table-striped table-condensed datatable "></table>
	      	</div>
    	</div>
  	</div>
	<div class="panel panel-default" ng-show="ctrl.display">
		<div class="panel-heading">List Details</div>
    	<div class="panel-body">
    		<form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
    			<input type="hidden" ng-model="ctrl.lead.id" />
    			
    			<div class="col-sm-6">
				 	<div class="form-group">
				    	<label class="control-label col-sm-4" for="firstName">	First Name </label>
				      	<div class="col-sm-8">
				        	<input type="text" ng-model="ctrl.lead.firstName" id="firstName" name="firstName" class="username form-control input-sm" placeholder="Enter firstname" required ng-minlength="5" />
                			<div class="has-error" ng-show="myForm.$dirty">
                  				<span ng-show="myForm.firstName.$error.required">This is a required field</span>
                  				<span ng-show="myForm.firstName.$error.minlength">Minimum length required is 5</span>
                  				<span ng-show="myForm.firstName.$invalid">This field is invalid </span>
                			</div>
				      	</div>
				    </div>
	    
				   <div class="form-group">
				    	<label class="control-label col-sm-4" for="lastName">Last Name</label>
				      	<div class="col-sm-8">
				        	<input type="text" ng-model="ctrl.lead.lastName" id="lastName" name="lastName" class="username  form-control input-sm" placeholder="Enter last name" required ng-minlength="5" />
                			<div class="has-error" ng-show="myForm.$dirty">
                  				<span ng-show="myForm.lastName.$error.required">This is a required field</span>
                  				<span ng-show="myForm.lastName.$error.minlength">Minimum length required is 5</span>
                  				<span ng-show="myForm.lastName.$invalid">This field is invalid </span>
                			</div>
				      	</div>
				    </div>
	    
				    <div class="form-group">
				    	<label class="control-label col-sm-4" for="dob">Date Of Birth</label>
				      	<div class="col-sm-8">
				        	<div class="input-group date" id="dob" ng-model="ctrl.lead.dob" date1-picker>
           						<input type="text" class="form-control netto-input" ng-model="ctrl.lead.dob" date-picker-input>
					            <span class="input-group-addon">
           							<span class="glyphicon glyphicon-calendar"></span>
                  				</span>
                			</div>
				      	</div>
				    </div>
	    
				    <div class="form-group">
				    	<label class="control-label col-sm-4" for="gender">Gender</label>
				      	<div class="col-sm-8">
				        	<select ng-model="ctrl.lead.genderId" name="gender" class="form-control" ng-options="gender.description for gender in ctrl.genders | orderBy:'description' track by gender.description" required></select>
                			<div class="has-error" ng-show="myForm.$dirty">
                  				<span ng-show="myForm.gender.$error.required">This is a required field</span>
                			</div>
				      	</div>
				    </div>
				    
				    <div class="form-group">
				    	<label class="control-label col-sm-4" for="language">Language</label>
				      	<div class="col-sm-8">
				        	<select ng-model="ctrl.lead.language" name="language" class="form-control" ng-options="language.description for language in ctrl.languages | orderBy:'description' track by language.description" required></select>
                			<div class="has-error" ng-show="myForm.$dirty">
                  				<span ng-show="myForm.language.$error.required">This is a required field</span>
                			</div>
				      	</div>
				    </div>
				    
				    
				    <div class="form-group" ng-show="ctrl.loginUser.roleName != 'ROLE_AGENT'">
				    	<label class="control-label col-sm-4" for="language">Source</label>
				      	<div class="col-sm-8">
				        	<div class="input-group">
    							<input type="text" class="form-control" placeholder="Agent Event"/>
    								<span class="input-group-addon">-</span>
    								<input type="text" class="form-control" placeholder="Agent Name"/>
							</div>
				      	</div>
				    </div>
				    
				    <div class="form-group">
				  		<label class="col-md-4 control-label" for="selectbasic">Status</label>
				   		<div class="col-md-8">
				    		<select ng-model="ctrl.lead.status" class="form-control" ng-options="status.description for status in ctrl.statuses | orderBy:'description' track by status.description" required></select>
				  		</div>
					</div>
						
						
				</div>	
				
				
				<div class="col-sm-6">
				 	<div class="form-group">
				    	<label class="control-label col-sm-4" for="planType">Plan Type</label>
				      	<div class="col-sm-8">
				        	<select ng-model="ctrl.lead.planType" class="form-control"  ng-options="planType.description for planType in ctrl.planTypes | orderBy:'description' track by planType.description" required></select>
                			<div class="has-error" ng-show="myForm.$dirty">
                  				<span ng-show="myForm.language.$error.required">This is a required field</span>
                			</div>
				      	</div>
				    </div>
	    
				   <div class="form-group">
				    	<label class="control-label col-sm-4" for="currentPlan">Current Plan</label>
				      	<div class="col-sm-8">
				      	<select ng-model="ctrl.lead.insurance" class="form-control"  ng-options="insurance.name for insurance in ctrl.insurances  | orderBy:'name' | filter:{planType:{id:ctrl.lead.planType.id}} track by insurance.name" required></select>
                			
				      	</div>
				    </div>
	    
				    <div class="form-group">
				    	<label class="control-label col-sm-4" for="consentFormSigned">Agree Consent Form </label>
				      	<div class="col-sm-8">
                			<input type="checkbox" ng-model="ctrl.lead.consentFormSigned" name="consentFormSigned" ng-true-value="'Y'" ng-false-value="'N'" required>
                			<div ng-if="!ctrl.lead.id">
                  				<div class="has-error" ng-show="myForm.$dirty">
                    				<span ng-show="myForm.consentFormSigned.$error.required">This is a required field</span>
                  				</div>
                			</div>
              			</div>
				    </div>
				    
				    <div class="form-group">
				    	<label class="control-label col-sm-4" for="gender">File</label>
				      	<div class="col-sm-8">
				        	<input class="col-sm-12  control-label form-control" name="fileUpload" type="file" file-model="ctrl.myFiles"  ng-model="ctrl.myfiles" id="myFileField"  required/>
                			<div ng-if="!ctrl.lead.id ">
                  				<div class="has-error" ng-show="myForm.$dirty">
                    				<span ng-show="myForm.fileUpload.$error.required">This is a required field</span>
                  				</div>
                			</div>
				      	</div>
				    </div>
				    
				    <div class="form-group">
				    	<label class="control-label col-sm-4" for="agentAssignment">Agent Assignment</label>
				      	<div class="col-sm-8">
				        	<input type="checkbox" ng-model="agentCheck" name="agentAssignment">
				      	</div>
				    </div>
				    
				    <div class="form-group">
						<label class="col-md-3 control-label" for="notes">Notes</label>
				  		<div class="col-md-9">                     
				    		<textarea name="notes" class="form-control" id="notes" ng-model="ctrl.selectedAgentLeadAppointment.notes"></textarea>
				  		</div>
				  		<label class="col-md-3 control-label" for="history">History</label>
				  		<div class="col-md-9">                     
				    		<textarea name="notes" disabled class="form-control" id="notes" ng-model="ctrl.selectedAgentLeadAppointment.notes"></textarea>
				  		</div>
					</div>
				    
				</div>
				
				<fieldset class="col-sm-12" ng-show="ctrl.lead.status.id == 2">
					<legend>PCP Details</legend>
						<!-- Select Basic -->
					<div class="col-sm-12">
						<div class="col-md-6">
							<div class="form-group">
								<label class="col-md-4 control-label" for="uname">PCP </label>
						  		<div class="col-md-8">
						  			<select class="form-control" ng-model="ctrl.selectedAgentLeadAppointment.prvdr" ng-options="prvdr.name for prvdr in ctrl.providers | orderBy:'name' track by prvdr.name" required="ctrl.lead.status.id == 2"></select>
						  		</div>
						  	</div>
						 </div> 	
					
						 <div class="col-md-6">
						 	<div class="form-group">	
						  		<label class="col-md-4 control-label" for="effectiveDate">Effective From</label>
						  		<div class="input-group date col-md-8" id="besttime" ng-model="ctrl.selectedAgentLeadAppointment.effectiveFrom" date1-picker >
	                      			<input type="text" class="form-control netto-input" ng-model="ctrl.selectedAgentLeadAppointment.effectiveFrom" date-picker-input required="ctrl.lead.status.id == 2">
	                   				<span class="input-group-addon">
	           							<span class="glyphicon glyphicon-calendar"></span>
	                   				</span>
	                   			</div>
							</div>
						</div>
					</div>
					<div class="col-sm-12">	
						<div class="col-md-6">		
							<div class="form-group" >
								<label class="col-md-4 control-label" for="planType">Plan Type</label>
						  		<div class="col-md-8">
						  			<select class=" form-control" ng-model="ctrl.selectedAgentLeadAppointment.planType" ng-options="planType.description for planType in ctrl.planTypes | orderBy:'description' track by planType.description" required="ctrl.lead.status.id == 2"></select>
						  		</div>
						  	</div>
						</div>
					
						<div class="col-md-6">
							<div class="form-group" >	
						  		<label class="col-md-4 control-label" for="plan">Plan</label>
						  		<div class="col-md-8">
						  			<select class=" form-control" ng-model="ctrl.selectedAgentLeadAppointment.leadMbrInsuranceList" class="form-control" ng-options="insurance.name for insurance in ctrl.insurances | orderBy:'description' | filter :{planType:{id:ctrl.selectedAgentLeadAppointment.planType.id}} track by insurance.name"></select>
						  		</div>
						  	</div>
						</div>
					</div>
					<div class="col-sm-12">	
						<div class="col-md-6">
							<div class="form-group" >
								<label class="col-md-4 control-label" for="drappointment">Dr Appointment </label>
						  		<div  class="input-group date col-md-8" name="drappointment" ng-model="ctrl.selectedAgentLeadAppointment.drappointment" date-picker>
						  			<input type="text" class="form-control netto-input" ng-model="ctrl.selectedAgentLeadAppointment.drappointment" date-picker-input required="ctrl.lead.status.id == 2">
	                      			<span class="input-group-addon">
	           							<span class="glyphicon glyphicon-calendar"></span>
	                   				</span>
						  		</div>
						  	</div>		
					    </div>
					    
					    <div class="col-md-6">	  		
					    	<div class="form-group" >
						  		<label class="col-md-4 control-label" for="transportation">Transportation</label>
						  		<div class="col-md-8">
						  			<input type="checkbox" ng-model="ctrl.selectedAgentLeadAppointment.transportation" name="transportation" ng-true-value="'Y'" ng-false-value="'N'" />
						  		</div>
							</div>
						</div>
					</div>	
				</fieldset>
				
				<fieldset class="col-sm-12">
					<legend>Contact</legend>
					<div class="col-sm-6">
				 		<div class="form-group">
				    		<label class="col-sm-4  control-label" for="homePhone">Home Phone</label>
				      		<div class="col-sm-8">
				        		<input type="text" ng-model="ctrl.lead.homePhone" id="homePhone" class="username form-control input-sm" placeholder="Enter Home phone" required phone-input ng-minlength="10" />
				      		</div>
				    	</div>
				    	
				    	<div class="form-group">
				    		<label class="col-sm-4  control-label" for="mobilePhone">Mobile Phone</label>
				      		<div class="col-sm-8">
				        		<input type="text" ng-model="ctrl.lead.mobilePhone" id="mobilePhone" class="username form-control input-sm" placeholder="Enter Mobile phone" phone-input ng-minlength="10" />
				      		</div>
				    	</div>
				    	
				    	<div class="form-group">
				    		<label class="col-sm-4  control-label" for="bestTimeToCall">Best Time to Call</label>
				      		<div class="col-sm-8">
				        		<input type="text" class="form-control netto-input" ng-model="ctrl.lead.bestTimeToCall" date-picker-input>
				      		</div>
				    	</div>
				    	
				    	<div class="form-group">
				    		<label class="col-sm-4  control-label"   for="email" require>Email </label>
				      		<div class="col-sm-8">
				        		<input type="email" ng-model="ctrl.lead.email" id="email" name="email" class="username form-control input-sm" placeholder="Enter email" ng-minlength="5" />
	                  			<div class="has-error" ng-show="myForm.$dirty">
	                   				<span ng-show="myForm.email.$error.minlength">Minimum length required is 8</span>
	                   				<span ng-show="myForm.email.$invalid">This field is invalid </span>
	               				</div>
				      		</div>
				    	</div>
				    </div>
				    
				    <div class="col-sm-6">
				 		<div class="form-group">
				    		<label class="col-sm-4  control-label" for="address1">Address 1</label>
				      		<div class="col-sm-8">
				        		<input type="text" ng-model="ctrl.lead.address1" id="address1" class="username form-control input-sm" placeholder="Enter Address" required ng-minlength="6" ng-maxlength="100" />
				      		</div>
				    	</div>
				    	
				    	<div class="form-group">
				    		<label class="col-sm-4  control-label" for="addres2">Address 2</label>
				      		<div class="col-sm-8">
				        		<input type="text" ng-model="ctrl.lead.address2" id="addres2" class="username form-control input-sm" placeholder="Enter Address" ng-maxlength="10" />
				      		</div>
				    	</div>
				    	
				    	<div class="form-group">
				    		<label class="col-sm-4  control-label" for="city">City</label>
				      		<div class="col-sm-8">
				        		<input type="text" ng-model="ctrl.lead.city" id="city" class="username form-control input-sm" placeholder="Enter City" required ng-minlength="4" ng-maxlength="100" />
				      		</div>
				    	</div>
				    	
				    	<div class="form-group">
				    		<label class="col-sm-4  control-label" for="state">State</label>
				      		<div class="col-sm-8">
				        		<select ng-model="ctrl.lead.stateCode" class="form-control" ng-options="state.description for state in ctrl.states | orderBy:'description' track by state.description" required></select>
				      		</div>
				    	</div>
				    	
				    	<div class="form-group">
				    		<label class="col-sm-4  control-label" for="zipCode">Zipcode</label>
				      		<div class="col-sm-8">
				        		<select ng-model="ctrl.lead.zipCode" class="form-control" ng-options="zipCode.code for zipCode in ctrl.lead.stateCode.zipCodes | orderBy:'code'  track by zipCode.code" required></select>
				      		</div>
				    	</div>
				    </div>
				</fieldset>	
				
				<fieldset class="col-sm-12" ng-show="ctrl.display && agentCheck && ctrl.loginUser.roleName != 'ROLE_AGENT'">
					<legend>Agent </legend>
					<div class="col-sm-6">
						<div class="form-group">
					 		<label class="col-sm-4  control-label" for="agent">Agent</label>
					      	<div class="col-sm-8">
					        	<select ng-model="ctrl.selectedAgentLeadAppointment.user" class="form-control" ng-options="agent.username for agent in ctrl.users  | orderBy:'username' | filter:{roles:[{role:'ROLE_AGENT'}]} track by agent.username" required></select>
					      	</div>
					     </div> 	
					     
					     <div class="form-group">
					 		<label class="col-sm-4  control-label" for="appointment">Appointment Time</label>
					      	<div class="col-sm-8">
					        	<div class="input-group date" id="appointment" ng-model="ctrl.selectedAgentLeadAppointment.appointmentTime" date-picker>
                    				<input type="text" class="form-control netto-input" ng-model="ctrl.selectedAgentLeadAppointment.appointmentTime" date-picker-input>
									<span class="input-group-addon">
           								<span class="glyphicon glyphicon-calendar"></span>
                    				</span>
					      		</div>
					     	</div> 
				    </div>
				</fieldset>	
				
				<div class="row col-sm-12" style="padding-bottom:20px;">
    				<div class="form-actions floatCenter col-sm-offset-9">
        	 			<input type="submit" value="{{!ctrl.lead.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" ng-disabled="!ctrl.myFile || ctrl.lead.consentFormSigned == 'N' || myForm.$invalid || myForm.$pristine">
             			<button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset Form</button>
          			</div>
          		</div>		
				
		    </form>
    	</div>
    	
  	</div>
</div>