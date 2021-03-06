<div class="generic-container">
  <div class="panel panel-success" ng-if="!ctrl.display">
    <!-- Default panel contents -->
    <div class="panel-heading"><span class="user">List of Leads </span>
      <button type="button" ng-click="ctrl.addLead()" ng-hide="ctrl.displayEditButton || ctrl.loginUserRole === 'AGENT'" class="btn btn-success  btn-xs  custom-width floatRight"> Add </button>
      <button type="button" ng-click="ctrl.editLead(ctrl.leadId)" ng-show="ctrl.displayEditButton" class="btn btn-primary btn-xs custom-width floatRight">Edit</button>
      <button type="button" ng-click="ctrl.removeLead(ctrl.leadId)" ng-show="ctrl.displayEditButton" class="btn btn-danger btn-xs  custom-width floatRight">Remove</button>
    </div>


    <div class="panel-body">
      <div class="table-responsive">
        <table dataTable="" id="content" dt-options="ctrl.dtOptions" dt-columns="ctrl.dtColumns" dt-instance="ctrl.dtInstanceCallback" dt-disable-deep-watchers="true"   class="table table-hover table-responsive  bordered table-striped table-condensed datatable dt-responsive   dataTable row-border hover " cellspacing="0" width="100%" ></table>
      </div>
    </div>
  </div>
  
  <div class="panel panel-success" ng-if="ctrl.display">
    <div class="panel-heading">List Details </div>
    <div class="panel-body">
     <div class="formcontainer">
     <div class="alert alert-success" role="alert" ng-if="ctrl.successMessage">{{ctrl.successMessage}}</div>
     <div class="alert alert-danger" role="alert" ng-if="ctrl.errorMessage">{{ctrl.errorMessage}}</div>
      <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
        <input type="hidden" ng-model="ctrl.lead.id" />

        <div class="col-sm-12 sourceInfo" ng-if="ctrl.showEventSource()">
          <div class="panel panel-success">
            <div class="panel-heading">Source</div>
            <div class="panel-body">
              <div class="row-fluid">
                <div class="col-sm-6">
                  <div class="form-group col-sm-12">
                    <label for="agentEvent">Event </label>
                	 <ui-select ng-model="ctrl.lead.event" name="eventName"
									theme="bootstrap" ng-disabled="disabled" style="width: 350px;" required>
						<ui-select-match
							placeholder="Select or search an event in the list...">{{$select.selected.eventName}}</ui-select-match>
						<ui-select-choices
							repeat="event in ctrl.events | filter: $select.search"
							refresh="ctrl.refreshEvents($select.search)"  refresh-delay="0" >
						<span
							ng-bind-html="event.eventName | highlight: $select.search"></span>
						</ui-select-choices> </ui-select>
							
						<div class="has-error" ng-show="myForm.$dirty">
							<span ng-show="myForm.eventName.$error.required">This
								is a required field</span>
						</div>
									
                  </div>
                </div>

             
              </div>
            </div>
          </div>
        </div>

        <div class="col-sm-6 ptInfo">
          <div class="panel panel-success">
            <div class="panel-heading">Lead Info</div>
            <div class="panel-body">
              <div class="row-fluid">
                <div class="col-sm-6">
                  <div class="form-group col-sm-12">
                    <label for="firstName"> First Name </label>
                    <input type="text" ng-model="ctrl.lead.firstName" id="firstName" name="firstName" class="username form-control input-sm" placeholder="Enter firstname" required ng-minlength="2" />
                    <div class="has-error" ng-show="myForm.$dirty">
                      <span ng-show="myForm.firstName.$error.required">This is a required field</span>
                      <span ng-show="myForm.firstName.$error.minlength">Minimum length required is 2</span>
                      <span ng-show="myForm.firstName.$invalid">This field is invalid </span>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6">
                  <div class="form-group col-sm-12">
                    <label for="lastName">Last Name</label>
                    <input type="text" ng-model="ctrl.lead.lastName" id="lastName" name="lastName" class="username  form-control input-sm" placeholder="Enter last name" required ng-minlength="2" />
                    <div class="has-error" ng-show="myForm.$dirty">
                      <span ng-show="myForm.lastName.$error.required">This is a required field</span>
                      <span ng-show="myForm.lastName.$error.minlength">Minimum length required is 2</span>
                      <span ng-show="myForm.lastName.$invalid">This field is invalid </span>
                    </div>
                  </div>
                </div>
              </div>

              <div class="row-fluid">
                <div class="col-sm-6">
                  <div class="form-group col-sm-12">
                    <label for="gender">Gender</label>
                    <select ng-model="ctrl.lead.gender" name="gender" class="form-control" ng-options="gender.description for gender in ctrl.genders | orderBy:'description' track by gender.description" required>
                      <option></option>
                    </select>
                    <div class="has-error" ng-show="myForm.$dirty">
                      <span ng-show="myForm.gender.$error.required">This is a required field</span>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6">
                  <div class="form-group col-sm-12">
                    <label for="dob">Date Of Birth</label>
                    <div class="input-group date" id="dob" ng-model="ctrl.lead.dob" date1-picker>
                      <input type="text" class="form-control netto-input" ng-model="ctrl.lead.dob" date-picker-input>
                      <span class="input-group-addon">
			           							<span class="glyphicon glyphicon-calendar"></span>
                      </span>
                    </div>
                  </div>
                </div>
              </div>

              <div class="row-fluid">
                <div class="col-sm-6">
                  <div class="form-group col-sm-12">
                    <label for="planType">Plan Type</label>
                    <select ng-model="ctrl.lead.planType" class="form-control" name="planType" ng-options="planType.description for planType in ctrl.planTypes | orderBy:'description' track by planType.description" required>
                      <option></option>
                    </select>
                    <div class="has-error" ng-show="myForm.$dirty">
                      <span ng-show="myForm.planType.$error.required">This is a required field</span>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6" >
                  <div class="form-group col-sm-12">
                    <label for="currentPlan">Current Plan</label>
                    <input type="text" ng-model="ctrl.lead.initialInsurance"   name="initialInsurance" class="username  form-control input-sm" placeholder="Enter Present Insurance"  ng-minlength="3" />
                    <div class="has-error" ng-show="myForm.$dirty">
                   	 <span ng-show="myForm.initialInsurance.$error.minlength">Minimum length required is 3</span>
                    </div>
                  </div>
                </div>
              </div>

              <div class="row-fluid">
                <div class="col-sm-6">
                  <div class="form-group col-sm-12">
                    <label for="language">Language</label>
                    <select ng-model="ctrl.lead.language" name="language" class="form-control"   ng-options="language.description for language in ctrl.languages | orderBy:'description' track by language.description" required>
                      <option></option>
                    </select>
                    <div class="has-error" ng-show="myForm.$dirty">
                      <span ng-show="myForm.language.$error.required">This is a required field</span>
                    </div>
                  </div>
                </div>
                
              
                <div class="col-sm-6" ng-if="ctrl.lead.id">
                  <div class="form-group col-sm-12">
                    <label for="language">File</label>
                    	<a  ng-click="ctrl.readUploadedFile()" style="display:block;">
          					<span class="glyphicon glyphicon-file">{{ctrl.lead.fileUpload.fileName}}</span>
        				</a>
                  </div>
                </div>
                
              </div>
            </div>
          </div>
        </div>



        <div class="col-sm-6 cntInfo">
          <div class="panel panel-success">
            <div class="panel-heading">Contact Info</div>
            <div class="panel-body">

              <div class="row-fluid">
                <div class="col-sm-12">
                  <div class="form-group col-sm-12">
                    <label for="address1">Address 1</label>
                    <input type="text" ng-model="ctrl.lead.contact.address1" id="address1" name="address1" class="username form-control input-sm" placeholder="Enter Address" ng-required="!ctrl.lead.contact.homePhone" ng-minlength="6" ng-maxlength="100" />
                    <div class="has-error" ng-show="myForm.$dirty">
                      <span ng-show="myForm.address1.$error.required">This is a required field</span>
                      <span ng-show="myForm.address1.$error.minlength">Minimum length required is 5</span>
                      <span ng-show="myForm.address1.$invalid">This field is invalid </span>
                    </div>
                  </div>
                </div>
              </div>

              <div class="row-fluid">
                <div class="col-sm-12">
                  <div class="form-group col-sm-12">
                    <label for="	Last Name "> City / State / Zip</label>
                     <div class="has-error" ng-show="myForm.$dirty">
                      	<span ng-show="myForm.city.$error.required">City is a required field</span>
                      	<span ng-show="myForm.city.$error.minlength">City Minimum length required is 4</span>
                      	<span ng-show="myForm.city.$invalid">City field is invalid </span>
                       </div>
                       
                        <div class="has-error" ng-show="myForm.$dirty">
                      	<span ng-show="myForm.state.$error.required">State is a required field</span>
                       </div>
                       
                        <div class="has-error" ng-show="myForm.$dirty">
                      	<span ng-show="myForm.zipcode.$error.required">Zipcode is a required field</span>
                      	<span ng-show="myForm.zipcode.$error.minlength">Zipcode Minimum length required is 5</span>
                       </div>
                       
                    <div class="input-group">
                      <input type="text" ng-model="ctrl.lead.contact.city" id="city" name="city" class="username form-control" placeholder="Enter City" ng-required="!ctrl.lead.contact.homePhone" ng-minlength="4" ng-maxlength="100" />
                      <span class="input-group-addon">-</span>
                      <select ng-model="ctrl.lead.contact.stateCode" class="form-control" name="state" ng-options="state.description for state in ctrl.states | orderBy:'description' track by state.description" ng-required="!ctrl.lead.contact.homePhone">
                        <option></option>
                      </select>
                      <span class="input-group-addon">-</span>
                      <input type="text" ng-model="ctrl.lead.contact.zipCode" name="zipcode" id="zipcode" class="username form-control input-sm" placeholder="Enter zipcode" zip-code  ng-required="!ctrl.lead.contact.homePhone" ng-minlength="5" />
                      
                    </div>
                  </div>
                </div>

              </div>

		 
              <div class="row-fluid">
                <div class="col-sm-6">
                  <div class="form-group col-sm-12">
                    <label for="homePhone">Home Phone</label>
                    <input type="text" ng-model="ctrl.lead.contact.homePhone" id="homePhone" name="homePhone" class="username form-control input-sm" placeholder="Enter Home phone" ng-required="!ctrl.lead.contact.address1" phone-input ng-minlength="10" />
                    <div class="has-error" ng-show="myForm.$dirty">
                      <span ng-show="myForm.homePhone.$error.required">This is a required field</span>
                    </div>
                  </div>
                </div>

                <div class="col-sm-6">
                  <div class="form-group col-sm-12">
                    <label for="mobilePhone">Emergency Contact #</label>
                    <input type="text" ng-model="ctrl.lead.contact.mobilePhone" id="mobilePhone" class="username form-control input-sm" placeholder="Enter Mobile phone" phone-input ng-minlength="10" />
                  </div>
                </div>

              </div>

              <div class="row-fluid">
                <div class="col-sm-6">
                  <div class="form-group col-sm-12">
                    <label for="bestTimeToCall">Best Time to Call</label>
                     <select ng-model="ctrl.lead.bestTimeToCall" class="form-control" name="bestTimeToCall" ng-options="bestTimeToCall.description for bestTimeToCall in ctrl.bestTimeToCalls track by bestTimeToCall.description" required>
                       <option></option>
                     </select>
                      <div class="has-error" ng-show="myForm.$dirty">
                      <span ng-show="myForm.bestTimeToCall.$error.required">This is a required field</span>
                    </div>
                  </div>
                </div>

                <div class="col-sm-6">
                  <div class="form-group col-sm-12">
                    <label for="email" require>Email </label>
                      <input type="email" ng-model="ctrl.lead.email" id="email" name="email" class="username form-control input-sm" placeholder="Enter email" ng-minlength="5" />
                      <div class="has-error" ng-show="myForm.$dirty">
                        <span ng-show="myForm.email.$error.minlength">Minimum length required is 8</span>
                        <span ng-show="myForm.email.$invalid">This field is invalid </span>
                      </div>
                  </div>
                </div>

              </div>
            </div>
          </div>
        </div>

        <div class="col-sm-12 additionalInfo"  >
          <div class="panel panel-success">
            <div class="panel-heading">Additional Details</div>
            <div class="panel-body">
              <div class="row-fluid col-md-6">
                <div class="col-sm-12">
                  <div class="form-group col-sm-12">
                    <label for="agree consent form">Agree Consent Form </label>
                    <input type="checkbox" class="form-control bigCheckBox" ng-model="ctrl.consentFormSigned" name="consentFormSigned" ng-true-value="'Y'" ng-false-value="'N'" >
                  </div>
                </div>

                <div class="col-sm-12" >
                  <div class="form-group col-sm-12">
                    <label for="file">File </label>
                    <input class="col-sm-12  control-label form-control" name="fileUpload" type="file" file-model="ctrl.myFile"  ng-model="ctrl.myFile" id="myFileField"  ng-required="ctrl.consentFormSigned==='Y'"/>
                      <div class="has-error" ng-show="ctrl.consentFormSigned==='Y' && myForm.$dirty && !ctrl.myFile">
                        <span ng-show="myForm.fileUpload.$error.required">This is a required field</span>
                      </div>
                  </div>
                </div>
              </div>
              
              
              <div class="row-fluid col-md-6" ng-if="(ctrl.loginUserRole == 'EVENT_COORDINATOR'  || !((ctrl.lead.status && ctrl.lead.status.description == 'Agent') ||ctrl.lead.agentLeadAppointmentList.length > 0))">
                <div class="col-sm-12">
                  <div class="form-group col-sm-12">
                    <label for="leadcreation">Notes </label>
                     <div class="has-error" ng-show="myForm.$dirty">
                      <span ng-show="myForm.leadcreationnotes.$error.required">This is a required field</span>
                    </div>       
                    <textarea name="leadcreationnotes" class="form-control" id="notes" ng-model="ctrl.notes" required></textarea>
                    <textarea name="leadcreationnoteshistory" disabled class="form-control" id="notes" ng-model="ctrl.lead.notesHistory"></textarea>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        
 
    <div class="col-sm-12 agentInfo"  ng-if="ctrl.showAgentAssignment()">
          <div class="panel panel-success">
            <div class="panel-heading">Agent Assignment</div>
            <div class="panel-body">
            
              <div class="row-fluid col-md-6">
                 <div class="col-sm-12">
                  <div class="form-group col-sm-12">
                    <label for="agentAssignment">Agent Name</label>
                     <select class="form-control" name="agentAssignment" ng-model="ctrl.selectedAgentLeadAppointment.user" ng-options="(agent.name + '('+agent.role.role+ ')') for agent in ctrl.users  | orderBy:'name'  track by agent.id"  required="(ctrl.lead.status.description=='Agent' || ctrl.lead.status.description=='Converted' || ctrl.lead.status.description=='Disenrolled')">
                     </select>
                      <div class="has-error" ng-show="myForm.$dirty">
                      <span ng-show="myForm.agentAssignment.$error.required">This is a required field</span>
                    </div>   
                  </div>
                  </div> 

                  <div class="col-sm-12">
                  <div class="form-group col-sm-12">
                    <label for="appointmentnotes">Appointment </label>
                     <div class="input-group date"  id="appointment"   ng-model="ctrl.selectedAgentLeadAppointment.appointmentTime" date-picker >
                      <input type="datetime" class="form-control netto-input" ng-model="ctrl.selectedAgentLeadAppointment.appointmentTime" date-picker-input  ng-required="ctrl.showAgentAssignment()" name="appointmentTime">
                      <span class="input-group-addon">
		           								<span class="glyphicon glyphicon-calendar"></span>
                      </span>
                     </div>
                     <div class="has-error" ng-show="myForm.$dirty">
                        <span ng-show="myForm.appointmentTime.$invalid">This field is invalid </span>
                         <span ng-show="myForm.appointmentTime.$error.required">This is a required field</span>
                      </div>
                  </div>
              </div>
              </div>

              <div class="row-fluid col-md-6" ng-if="ctrl.loginUserRole != 'AGENT'">
                <div class="col-sm-12">
                  <div class="form-group col-sm-12">
                    <label for="	Last Name ">Notes </label>
                     <div class="has-error" ng-show="myForm.$dirty">
                      <span ng-show="myForm.notes.$error.required">This is a required field</span>
                    </div>       
                    <textarea name="notes" class="form-control" id="notess" ng-model="ctrl.notes"  required ></textarea>
                    <textarea name="notes1" disabled class="form-control" id="notess1" ng-model="ctrl.lead.notesHistory"></textarea>
                    
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="col-sm-12 statusInfo" >
          <div class="panel panel-success">
            <div class="panel-heading">Status</div>
            <div class="panel-body">
            	<div class="row-fluid">
                	<div class="col-sm-6">
                  		<div class="form-group col-sm-12">
                  			<label class="control-label" for="selectbasic">Status</label>
              				<select ng-model="ctrl.lead.status" class="form-control" ng-change="ctrl.resetAssignment(ctrl.lead.status.description)"  ng-options="status.description for status in ctrl.statuses | orderBy:'description' track by status.description" ng-required="ctrl.lead.id">
              				  <option></option>
              				</select>
                 	 	</div>
               		</div>  
               		 
               		<div class="col-sm-6">
                  		<div class="form-group col-sm-12">
                  			<label class="control-label" for="selectbasic">Status Detail</label>
              				<select ng-model="ctrl.lead.statusDetail" class="form-control" name="statusDetail"  ng-options="status.description for status in ctrl.statusDetails | filter:{leadStatus:{id:ctrl.lead.status.id}} | orderBy:'description' track by status.description" ng-required="((ctrl.statusDetails | filter:{leadStatus:{id:ctrl.lead.status.id}}).length >0 && ctrl.lead.id)">
              				  <option></option>
              				</select>
              				<div class="has-error" ng-show="myForm.$dirty">
                               <span ng-show="myForm.statusDetail.$error.required">This is a required field</span>
                           </div>
                 	 	</div>
               		</div>  
               		
               		<div class="col-sm-6" ng-if="ctrl.showStatusNotes()">
                  		<div class="form-group col-sm-12">
                  		 	<div class="has-error" ng-show="myForm.$dirty">
                      			<span ng-show="myForm.notes.$error.required">This is a required field</span>
                   			 </div>       
                  			 <label class="control-label" for="statusNotes">Notes</label>	
                  			 <textarea name="notes" class="form-control" id="notes" ng-model="ctrl.notes" required></textarea>
                    		 <textarea name="notes1" disabled class="form-control" id="notes1" ng-model="ctrl.lead.notesHistory"></textarea>	
                  		</div>
               		</div> 
               		
               	</div>	
            
              
            </div>
          </div>
        </div>


        <div class="col-sm-12 pcpInfo" ng-if="ctrl.showStatusChangeDetails()">
          <div class="panel panel-success">
            <div class="panel-heading">PCP Details</div>
            <div class="panel-body">

              <div class="row-fluid">
                <div class="col-sm-3">
                  <div class="form-group col-sm-12">
                    <label for="PCP">PCP </label>
                    <select class="form-control" name="pcp" ng-model="ctrl.selectedAgentLeadAppointment.prvdr" ng-options="prvdr.name for prvdr in ctrl.providers | orderBy:'name' track by prvdr.name" required="ctrl.lead.status.id && ctrl.lead.status.id == 2"></select>
                      <div class="has-error" ng-show="myForm.$dirty">
                      <span ng-show="myForm.pcp.$error.required">This is a required field</span>
                    </div>                    
                  </div>
                </div>

                <div class="col-sm-3">
                  <div class="form-group col-sm-12">

                    <label for="effectiveDate">Effective From</label>
                    <div class="input-group date" ng-model="ctrl.selectedAgentLeadAppointment.effectiveFrom" date1-picker>
                      <input type="text" name="effectiveFrom" class="form-control netto-input" ng-model="ctrl.selectedAgentLeadAppointment.effectiveFrom" date-picker-input  ng-required="ctrl.showStatusChangeDetails()">
                      <span class="input-group-addon">
			           							<span class="glyphicon glyphicon-calendar"></span>
                      </span>
                    </div>
                    <div class="has-error" ng-show="myForm.$dirty">
                      <span ng-show="myForm.effectiveFrom.$error.required">This is a required field</span>
                    </div> 
                  </div>
                </div>
                
                <div class="col-sm-3" ng-if="ctrl.lead.status.description == 'Disenrolled'">
                  <div class="form-group col-sm-12">

                    <label for="effectiveDate">Effective To</label>
                    <div class="input-group date" ng-model="ctrl.selectedAgentLeadAppointment.effectiveTo" date1-picker>
                      <input type="text" name="effectiveTo" class="form-control netto-input" ng-model="ctrl.selectedAgentLeadAppointment.effectiveTo" date-picker-input  ng-required="ctrl.showStatusChangeDetails() &&   ctrl.lead.status.description == 'Disenrolled'">
                      <span class="input-group-addon">
			           							<span class="glyphicon glyphicon-calendar"></span>
                      </span>
                    </div>
                    <div class="has-error" ng-show="myForm.$dirty">
                      <span ng-show="myForm.effectiveTo.$error.required">This is a required field</span>
                    </div> 
                  </div>
                </div>
                
                <div class="col-sm-3">
                  <div class="form-group col-sm-12">
                    <label for="plan">Plan</label>
                      <div ng-if="(ctrl.loggedUserInsId == 0)">
                      <select name="plan"  class="form-control" ng-model="ctrl.selectedAgentLeadAppointment.insurance"  ng-options="insurance.name for insurance in ctrl.insurances | orderBy:'description' track by insurance.name"  required="ctrl.loggedUserInsId == 0" > 
                       </select>
                      </div>
                      <div  ng-if="(ctrl.loggedUserInsId != 0)">
                      <select  name="plan" class="form-control" ng-model="ctrl.selectedAgentLeadAppointment.insurance" ng-init="ctrl.selectedAgentLeadAppointment.insurance = ctrl.selectedAgentLeadAppointment.insurance||(ctrl.insurances | orderBy:'description' | filter :{id:ctrl.loggedUserInsId})[0]" 
                       ng-options="insurance.name for insurance in ctrl.insurances  | filter :{id:ctrl.loggedUserInsId} | orderBy:'description' track by insurance.name"  required="tctrl.loggedUserInsId != 0">
                       </select>
                      </div>
                      
                       <div class="has-error" ng-show="myForm.$dirty">
                      <span ng-show="myForm.plan.$error.required">This is a required field</span>
                    </div> 
                    
                  </div>
                </div>
                
                
              </div>

            </div>

          </div>
        </div>
       
         
            <span style="display:hidden" ng-show="flase">{{ ctrl.invalid = myForm.$inValid}}</span>
            <span style="display:hidden" ng-show="flase">{{ ctrl.pristine = myForm.$pristine}}</span>
          <div class="row-fluid col-sm-12" style="padding-bottom:20px;">
        
            <div class="form-actions floatCenter col-sm-offset-9">
              <input type="submit" ng-dblclick="return" value="{{!ctrl.lead.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-xs" ng-disabled="(ctrl.showAddorUpdateButton() || myForm.$pristine || myForm.$invalid) "/>
              <button type="button" ng-click="ctrl.cancelEdit(); $event.preventDefault();" class="btn btn-warning btn-xs"    >Cancel</button>
              <button type="button" ng-click="ctrl.reset(); $event.preventDefault();" class="btn btn-warning btn-xs"  ng-if="!ctrl.lead.id"  ng-disabled="myForm.$pristine">Reset Form</button>
            </div>
          </div>

      </form>
      </div>
    </div>
   </div>

 
 
</div>

  
  <style>
  	.bigCheckBox{
  		width:30px;
  		height:12px;
  	}
  </style>