<div class="generic-container">

	<div class="panel panel-success" ng-hide="ctrl.display">
		<!-- Default panel contents -->
		<div class="panel-heading">
			<span class="user">List of EventAssignments </span>
			<button type="button" ng-if="ctrl.adminOrManager()"
				ng-click="ctrl.addEventAssignment();$event.preventDefault();"
				ng-hide="ctrl.displayEditButton"
				class="btn btn-success btn-xs custom-width floatRight">Add
			</button>
			<button type="button" ng-if="ctrl.adminOrManager()"
				ng-click="ctrl.editEventAssignment(ctrl.eventAssignmentId);$event.preventDefault();"
				ng-show="ctrl.displayEditButton"
				class="btn btn-primary btn-xs custom-width floatRight">Edit</button>
			<button type="button" ng-if="ctrl.adminOrManager()"
				ng-click="ctrl.removeEventAssignment(ctrl.eventAssignmentId);$event.preventDefault();"
				ng-show="ctrl.displayEditButton"
				class="btn btn-danger btn-xs custom-width floatRight">Remove</button>
		</div>
		<div class="panel-body">
			<div class="table-responsive">
				<table datatable="" id="content" dt-options="ctrl.dtOptions"
					dt-columns="ctrl.dtColumns" dt-instance="ctrl.dtInstance"
					class="table table-hover table-responsive  bordered table-striped table-condensed datatable"
					cellspacing="0" width="100%"></table>

			</div>
		</div>
	</div>


	<div class="panel panel-success" ng-show="ctrl.display">
		<!-- Default panel contents -->
		<div class="panel-heading">
			<span class="eventAssignment">EventAssignment </span>
			<button type="button"
					ng-click="ctrl.repeat();$event.preventDefault();"
					class="btn  btn-primary btn-xs  floatRight">Repeat</button>
			<button type="button"
				ng-click="ctrl.addLead();$event.preventDefault();"
				ng-show="ctrl.eventAssignment.id"
				class="btn btn-success btn-xs  floatRight">Add Lead</button>
			<button type="button"
				ng-click="ctrl.cancelEdit();$event.preventDefault();"
				class="btn btn-warning btn-xs floatRight">Cancel</button>
			<button type="button"
				ng-click="ctrl.reset();$event.preventDefault();"
				class="btn btn-warning btn-xs floatRight"
				ng-disabled="myForm.$pristine"
				ng-if="ctrl.adminOrManager() && !ctrl.event.id">Reset Form</button>
			<input type="button"
				value="{{!ctrl.eventAssignment.id ? 'Add' : 'Update'}}"
				ng-click="ctrl.submit();$event.preventDefault();"
				class="btn btn-primary btn-xs floatRight"
				ng-disabled="myForm.$invalid || myForm.$pristine"
				ng-show="ctrl.adminOrManager()" />{{!ctrl.eventAssignment.id ? 'Add'
			: 'Update'}}

		</div>
		<div class="panel-body">
			<div class="formcontainer">
				<div class="alert alert-success" role="alert"
					ng-if="ctrl.successMessage">{{ctrl.successMessage}}</div>
				<div class="alert alert-danger" role="alert"
					ng-if="ctrl.errorMessage">{{ctrl.errorMessage}}</div>
				<form ng-submit="ctrl.submit()" name="myForm"
					class="form-horizontal">


					<div class="panel-heading">
						<div class="form-actions floatCenter col-md-offset-4"></div>
					</div>

					<input type="hidden" ng-model="ctrl.eventAssignment.id" />

					<div class="form-group col-md-6">

						<div class="row">
							<div class="form-group col-sm-12">
								<label class="col-md-3  control-label" for="eventAssignmentName"
									require>Name </label>
								<div class="col-sm-9">
								
										
									 <ui-select
										ng-model="ctrl.eventAssignment.event"
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

					  <div class="row">
							<div class="form-group col-md-12">
							
							 <label class="col-md-3  control-label" for="startDate">StartDate</label>
			                <div class="col-md-4">
			                  <div class="input-group"  moment-picker="ctrl.eventAssignment.startDate"  max-view="minute" max-date="ctrl.event.endTime"  ng-model="ctrl.eventAssignment.eventDateStartTime" locale="en" format="L">
							    <span class="input-group-addon">
							        <i class="octicon octicon-clock  glyphicon glyphicon-calendar"></i>
							    </span>
							    <input class="form-control"  name="startDate" placeholder="Select a time"  ng-model="ctrl.eventAssignment.startDate"  ng-model-options="{ updateOn: 'blur' }" required>
			                 </div>
			                   <div class="has-error" ng-show="myForm.$dirty">
			                        <span ng-show="myForm.startDate.$invalid">This field is invalid </span>
			                         <span ng-show="myForm.startDate.$error.required">This is a required field</span>
			                   </div>
			                </div>
                
							
							</div>
						</div>
						
					 <div class="row">
							<div class="form-group col-md-12">
								<label class="col-md-3  control-label" for="agent">Representatives</label>
								<div class="col-md-9">
								    <multiselect name="eventAssignmentRepresentatives" ng-model="ctrl.eventAssignment.representatives"   placeholder="Choose representatives"   options="ctrl.users" id-prop="id" display-prop="name" show-search="true" show-select-all="true" show-unselect-all="true" search-limit="10" ng-required="true"></multiselect>
									<div class="has-error" ng-show="myForm.$dirty">
										<span
											ng-show="myForm.eventAssignmentRepresentatives.$error.required">This
											is a required field</span>
									</div>
								</div>
							</div>
						</div>
 
					</div>
					<div class="form-group col-md-6" ng-show="ctrl.repeatDisplay">
						<div class="row">
							<div class="form-group col-lg-12">
								<label class="col-md-2 control-label" for="frequency">Repeat</label>
								<div class="col-md-3">
									<select class="col-md-12 form-control"
										ng-init=" ctrl.eventAssignment.frequency=ctrl.eventAssignment.frequency|| 'DAILY'"
										ng-model="ctrl.eventAssignment.frequency" name="frequency"
										ng-options="frequency as frequency for frequency in ctrl.eventAssignmentFrequencies"></select>
								</div>
								<span class="repeat-interval-panel"> <label
									class="control-label col-md-1 repeat-interval-pretext">every</label>
									<span class="input-group spinner  col-md-2"> <select
										class="col-md-12 form-control"
										ng-model="ctrl.eventAssignment.interval" name="interval"
										ng-options="interval for interval in ctrl.eventAssignmentIntervals">
									</select>
								</span> <span ng-show="ctrl.eventAssignment.frequency=='DAILY'"><label
										class="control-label">&nbsp; day(s) </label></span> <span
									ng-show="ctrl.eventAssignment.frequency=='WEEKLY'"><label
										class="control-label">&nbsp; week(s) on </label></span> <span
									ng-show="ctrl.eventAssignment.frequency=='MONTHLY'"><label
										class="control-label">&nbsp; month(s) </label> </span> <span
									ng-show="ctrl.eventAssignment.frequency=='YEARLY'"><label
										class="control-label">&nbsp; year(s) </label></span>
								</span>

							</div>
						</div>

						<div class="row">
							<div class="form-group col-md-12"
								ng-show="ctrl.eventAssignment.frequency=='WEEKLY'">
								<div class="col-md-offset-2" >
									<div class="btn-group" ng-repeat="weekDay in ctrl.eventAssignmentOnWeekDays">
										 <label class="btn btn-default"  ng-model="ctrl.checkEventAssignmentOnWeekDays[weekDay.shortName]" uib-btn-checkbox >{{weekDay.shortName}}</label>
									</div>
								</div>	
							</div>
						</div>

						<div ng-init="ctrl.onDayorThe=ctrl.onDayorThe||true"
							ng-show="ctrl.eventAssignment.frequency=='MONTHLY' || ctrl.eventAssignment.frequency=='YEARLY'">
							<div class="row">

								<div class="form-group col-md-12">

									<div class="col-md-2">
										<label class="col-md-12 control-label text-center"> <input
											type="radio" ng-model="ctrl.onDayorThe" ng-value="true">&nbsp;on<span
											ng-show="ctrl.eventAssignment.frequency=='MONTHLY'">&nbsp;day</span>
											<span ng-show="ctrl.eventAssignment.frequency=='YEARLY'">&nbsp;&nbsp;&nbsp;&nbsp;</span>
										</label>
									</div>
									<div class="col-md-3"
										ng-show="ctrl.eventAssignment.frequency=='YEARLY'">
										<select class="form-control col-md-7"
											ng-disabled="ctrl.onDayorThe==false"
											ng-model="ctrl.eventAssignmentMonthOnDay" name="onMonth"
											ng-options="eventAssignmentMonth.description for eventAssignmentMonth in ctrl.eventAssignmentMonths track by eventAssignmentMonth.id"></select>
									</div>
									<div class="col-md-3">
										<select class="form-control col-md-4"
											ng-disabled="ctrl.onDayorThe==false"
											ng-model="ctrl.eventAssignment.onDay" name="ondays"
											ng-options="i for i in ctrl.eventAssignmentOnDays"></select>
									</div>
									<div class="col-md-3"></div>
								</div>
							</div>

							<div class="row">
								<div class="form-group col-md-12">

									<div class="col-md-2">
										<label class="col-md-12 control-label text-center"> <input
											type="radio" ng-model="ctrl.onDayorThe" ng-value="false" />&nbsp;on
											the
										</label>
									</div>
									<div class="col-md-3">
										<select class="form-control col-md-12"
											ng-disabled="ctrl.onDayorThe==true"
											ng-model="ctrl.eventAssignmentOnWeek" name="onWeek"
											ng-options="weekNo.description for weekNo in ctrl.eventAssignmentOnWeeks track by weekNo.id"></select>
									</div>
									<div class="col-md-3">
										<select class="form-control col-md-12"
											ng-disabled="ctrl.onDayorThe==true"
											ng-model="ctrl.eventAssignmentOnWeekDay" name="weekday"
											ng-options="weekDay.description for weekDay in ctrl.eventAssignmentOnWeekDays track by weekDay.id"></select>
									</div>
									<div class="col-md-1"
										ng-show="ctrl.eventAssignment.frequency=='YEARLY'">
										<label class="col-md-12 control-label text-center"> of
										</label>
									</div>
									<div class="col-md-3"
										ng-show="ctrl.eventAssignment.frequency=='YEARLY'">
										<select class="form-control col-md-7"
											ng-disabled="ctrl.onDayorThe==true"
											ng-model="ctrl.eventAssignmentMonthOnThe" name="onMonth"
											ng-options="eventAssignmentMonth.description for eventAssignmentMonth in ctrl.eventAssignmentMonths track by eventAssignmentMonth.id"></select>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group col-md-12">
								<label class="col-md-2 control-label" for="end">End</label>
								<div class="col-md-3" >
									<select class="col-md-8 form-control"
										ng-model="ctrl.eventAssignmentEndOption" name="end"
										ng-options="i as i for i in ctrl.eventAssignmentEndOptions"></select>
								</div>
								<div ng-show="ctrl.eventAssignmentEndOption==='After'"
									class="col-md-1">
									<input type="text" ng-model="ctrl.eventAssignmentEndCount"
										id="count" name="eventAssignmentCount"
										class="username form-control input-md"
										placeholder="Enter count" required ng-minlength="1" />
									<div class="has-error" ng-show="myForm.$dirty">
										<span ng-show="myForm.eventAssignmentEndCount.$error.required">This
											is a required field</span> <span
											ng-show="myForm.eventAssignmentEndCount.$error.minlength">Minimum
											length required is 5</span> <span
											ng-show="myForm.eventAssignmentEndCount.$invalid">This
											field is invalid </span>
									</div>
								</div>
								<div ng-show="ctrl.eventAssignmentEndOption==='On date'"
									class="col-md-4">
									<div class="col-md-9 input-group date" id="appointment"
										ng-model="ctrl.eventAssignmentUntil" date1-picker>
										<input type="text" class="form-control netto-input col-md-12"
											ng-model="ctrl.eventAssignmentUntil" date-picker-input /> <span
											class="input-group-addon"> <span
											class="glyphicon glyphicon-calendar"></span>
										</span>
									</div>
								</div>
							</div>
						</div>

					</div>



				</form>
			</div>
		</div>
	</div>
</div>