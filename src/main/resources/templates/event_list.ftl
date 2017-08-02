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
        <table datatable="" id="content" dt-options="ctrl.dtOptions" dt-columns="ctrl.dtColumns" dt-instance="ctrl.dtInstance" class="table table-hover table-responsive  bordered table-striped table-condensed datatable"></table>

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
            <div class="form-actions floatCenter col-md-offset-4">
              <input type="submit" value="{{!ctrl.event.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid || myForm.$pristine">
              <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset Form</button>
              <button type="button" ng-click="ctrl.repeat()" class="btn  btn-primary btn-sm">Repeat</button>
              <button type="button"  ng-click="ctrl.addLead()" ng-show="ctrl.event.id"   class="btn btn-success  btn-sm">Add Lead</button>
            </div>
          </div>

          <input type="hidden" ng-model="ctrl.event.id" />

          <div class="form-group col-md-6">

            <div class="row">
              <div class="form-group col-md-12">
                <label class="col-md-2  control-label" for="eventName" require>Name </label>
                <div class="col-md-10">
                  <input type="text" ng-model="ctrl.event.eventName" id="eventName" name="eventName" class="username form-control input-md" placeholder="Enter event name" required ng-minlength="5" />
                  <div class="has-error" ng-show="myForm.$dirty">
                    <span ng-show="myForm.eventName.$error.required">This is a required field</span>
                    <span ng-show="myForm.eventName.$error.minlength">Minimum length required is 5</span>
                    <span ng-show="myForm.eventName.$invalid">This field is invalid </span>
                  </div>
                </div>
              </div>
            </div>

            <div class="row">
              <div class="form-group col-md-12">
                <label class="col-md-2  control-label" for="eventDateTime">StartTime</label>
                <div class="col-md-4">
                  <div class="input-group date" id="eventDateStartTime" ng-model="ctrl.event.eventDateStartTime" date-picker>
                    <input type="text" class="form-control netto-input" ng-model="ctrl.event.eventDateStartTime" date-picker-input required>
                    <span class="input-group-addon">
           								<span class="glyphicon glyphicon-calendar"></span>
                    </span>
                  </div>
                </div>

                <label class="col-md-2  control-label" for="eventDateEndTime">EndTime</label>
                <div class="col-md-4">
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
              <div class="form-group col-md-12">
                <label class="col-md-2  control-label" for="address1">Address1</label>
                <div class="col-md-10">
                  <input type="text" ng-model="ctrl.event.address1" id="address1" name="address1" class="username  form-control input-md" placeholder="Enter event address1" required ng-minlength="5" />
                  <div class="has-error" ng-show="myForm.$dirty">
                    <span ng-show="myForm.address1.$error.required">This is a required field</span>
                    <span ng-show="myForm.address1.$error.minlength">Minimum length required is 5</span>
                    <span ng-show="myForm.address1.$invalid">This field is invalid </span>
                  </div>
                </div>
              </div>
            </div>

            <div class="row">
              <div class="form-group col-md-12">
                <label class="col-md-2 control-label" for="address2">Address2</label>
                <div class="col-md-10">
                  <input type="text" ng-model="ctrl.event.address2" id="address2" name="address2" class="username  form-control input-md" placeholder="Enter event address2" ng-minlength="5" />
                </div>
              </div>
            </div>

            <div class="row">
              <div class="form-group col-md-12">
                <label class="col-md-2  control-label" for="city">City</label>
                <div class="col-md-3">
                  <input type="text" ng-model="ctrl.event.city" id="city" name="city" class="username  form-control input-md" placeholder="Enter event city" required ng-minlength="5" />
                  <div class="has-error" ng-show="myForm.$dirty">
                    <span ng-show="myForm.city.$error.required">This is a required field</span>
                    <span ng-show="myForm.city.$error.minlength">Minimum length required is 5</span>
                    <span ng-show="myForm.city.$invalid">This field is invalid </span>
                  </div>
                </div>

                <label class="col-md-1  control-label" for="state">State</label>
                <div class="col-md-2">
                  <select ng-model="ctrl.event.state" ng-options="state.description for state in ctrl.states track by state.description" required></select>
                </div>

                <label class="col-md-1  control-label" for="zipcode">Zipcode</label>
                <div class="col-md-3">
                  <select ng-model="ctrl.event.zipCode" ng-options="zipCode.code for zipCode in ctrl.event.state.zipCodes track by zipCode.code" required></select>
                </div>
              </div>
            </div>

            <div class="row">
              <div class="form-group col-md-12">
                <div require>
                  <label class="col-md-2  control-label" for="contactPerson">Contact Person</label>
                </div>
                <div class="col-md-3">
                  <input type="text" ng-model="ctrl.event.contactPerson" id="contactPerson" name="contactPerson" class="username  form-control input-md" placeholder="Enter event contactPerson" required ng-minlength="5" />
                  <div class="has-error" ng-show="myForm.$dirty">
                    <span ng-show="myForm.contactPerson.$error.required">This is a required field</span>
                    <span ng-show="myForm.contactPerson.$error.minlength">Minimum length required is 5</span>
                    <span ng-show="myForm.contactPerson.$invalid">This field is invalid </span>
                  </div>
                </div>

                <div require>
                  <label class="col-md-2  control-label" for="contactPhone">Contact Phone</label>
                </div>
                <div class="col-md-3">
                  <input type="text" ng-model="ctrl.event.contactPhone" id="contactPhone" name="contactPhone" class="username  form-control input-md" phone-input placeholder="Enter event contactPhone" required ng-minlength="5" />
                  <div class="has-error" ng-show="myForm.$dirty">
                    <span ng-show="myForm.contactPhone.$error.required">This is a required field</span>
                    <span ng-show="myForm.contactPhone.$error.minlength">Minimum length required is 5</span>
                    <span ng-show="myForm.contactPhone.$invalid">This field is invalid </span>
                  </div>
                </div>
              </div>
            </div>

            <div class="row">
              <div class="form-group col-md-12">
                <div require>
                  <label class="col-md-2  control-label" for="contactEmail">Contact Email</label>
                </div>
                <div class="col-md-3">
                  <input type="email" ng-model="ctrl.event.contactEmail" id="contactEmail" name="contactEmail" class="username  form-control input-md" placeholder="Enter event contactEmail" required ng-minlength="5" />
                  <div class="has-error" ng-show="myForm.$dirty">
                    <span ng-show="myForm.contactEmail.$error.required">This is a required field</span>
                    <span ng-show="myForm.contactEmail.$error.minlength">Minimum length required is 5</span>
                    <span ng-show="myForm.contactEmail.$invalid">This field is invalid </span>
                  </div>
                </div>
              </div>
            </div>




            <div class="row">
              <div class="form-group col-md-12">
                <label class="col-md-2  control-label" for="brokerage">Brokerage</label>
                <div class="col-md-2">
                  <select class="col-md-12 form-control" ng-model="ctrl.event.brokerage" name="brokerage" ng-options="brokerage.description for brokerage in ctrl.brokerages track by brokerage.description" required></select>
                  <div class="has-error" ng-show="myForm.$dirty">
                    <span ng-show="myForm.brokerage.$error.required">This is a required field</span>
                  </div>
                </div>

                <label class="col-md-2  control-label" for="facilityType">Facility</label>
                <div class="col-md-2">
                  <select class="col-md-12 form-control" ng-model="ctrl.event.facilityType" name="facilityType" ng-options="facilityType.description for facilityType in ctrl.facilityTypes track by facilityType.description" required></select>
                  <div class="has-error" ng-show="myForm.$dirty">
                    <span ng-show="myForm.facilityType.$error.required">This is a required field</span>
                  </div>
                </div>

                <label class="col-md-2  control-label" for="activityType">Activity</label>
                <div class="col-md-2">
                  <select ng-model="ctrl.event.activityType" class="form-control" name="activityType" ng-options="activityType.description for activityType in ctrl.activityTypes track by activityType.description" required></select>
                  <div class="has-error" ng-show="myForm.$dirty">
                    <span ng-show="myForm.activityType.$error.required">This is a required field</span>
                  </div>
                </div>
              </div>
            </div>

            <div class="row">
              <div class="form-group col-md-12">
                <label class="col-md-2  control-label" for="agent">Representatives</label>
                <div class="col-md-10">
                  <select ng-model="ctrl.event.representatives" class="form-control" name="eventRepresentatives" ng-options="agent.username for agent in ctrl.users   track by agent.username" multiple required>
                  </select>
                </div>
               
              </div>
            </div>

            <div class="row">
              <div class="form-group col-md-12">
                <div require>
                  <label class="col-md-2 control-label" for="notes">Notes</label>
                </div>
                <div class="col-md-10">
                  <textarea name="notes" style="height:320px" class="form-control" id="notes" ng-model="ctrl.event.notes"></textarea>
                </div>
              </div>
            </div>

            <div class="row fileUploadSection">
              <div class="form-group col-md-12 fileUploadRow">
                <div require>
                  <label class="col-md-2  control-label" for="contactEmail">FileUpload</label>
                </div>
                <div class="col-md-3">
                  <input class="form-control input-md" type="file" name="files" file-model="ctrl.myFiles" ng-model="ctrl.myFiles" multiple />

                </div>
              </div>
            </div>
          </div>

          <div class="form-group col-md-6" ng-show="ctrl.repeatDisplay">

            <div class="row">
              <div class="form-group col-lg-12">
                <label class="col-md-2 control-label" for="frequency">Repeat</label>
                <div class="col-md-3">
                  <select class="col-md-12 form-control" ng-init=" ctrl.event.frequency=ctrl.event.frequency|| 'DAILY'" ng-model="ctrl.event.frequency" name="frequency" ng-options="frequency as frequency for frequency in ctrl.eventFrequencies"></select>
                </div>
                <span class="repeat-interval-panel">
                                <label class="control-label col-md-1 repeat-interval-pretext">every</label>
                                <span class="input-group spinner  col-md-2">
                                   <select class="col-md-12 form-control"  ng-model="ctrl.event.interval" name="interval" ng-options="i as i for i in ctrl.eventIntervals" >
							       </select>
                                </span>
                <span ng-show="ctrl.event.frequency=='DAILY'"><label class="control-label">&nbsp; day(s) </label></span>
                <span ng-show="ctrl.event.frequency=='WEEKLY'"><label class="control-label">&nbsp; week(s) on </label></span>
                <span ng-show="ctrl.event.frequency=='MONTHLY'"><label class="control-label">&nbsp; month(s) </label> </span>
                <span ng-show="ctrl.event.frequency=='YEARLY'"><label class="control-label">&nbsp; year(s)  </label></span>
                </span>

              </div>
            </div>

            <div class="row">
              <div class="form-group col-md-12" ng-show="ctrl.event.frequency=='WEEKLY'">

                <div class="btn-group col-md-offset-2" role="group">
                  <label ng-repeat="item in ctrl.eventOnWeekDays" btn-checkbox="item.shortName" class="item btn btn-default " id="{{item.shortName}}" ng-change="ctrl.sync(bool, item)"  ng-model="bool" ng-checked="ctrl.isChecked(item.id)">
                   <div ng-show="false" > {{bool=ctrl.isChecked(item.id)||false}} </div>
                    {{item.shortName}}
                  </label>
                </div>

              </div>
            </div>


            <div ng-init="ctrl.onDayorThe=ctrl.onDayorThe||true" ng-show="ctrl.event.frequency=='MONTHLY' || ctrl.event.frequency=='YEARLY'">
              <div class="row">

                <div class="form-group col-md-12">

                  <div class="col-md-2">
                    <label class="col-md-12 control-label text-center">
                      <input type="radio" ng-model="ctrl.onDayorThe" ng-value="true">&nbsp;on<span ng-show="ctrl.event.frequency=='MONTHLY'">&nbsp;day</span> <span ng-show="ctrl.event.frequency=='YEARLY'">&nbsp;&nbsp;&nbsp;&nbsp;</span>
                    </label>
                  </div>
                  <div class="col-md-3" ng-show="ctrl.event.frequency=='YEARLY'">
                    <select class="form-control col-md-7" ng-disabled="ctrl.onDayorThe==false" ng-model="ctrl.event.month" name="onMonth" ng-options="ctrl.event.month as eventMonth.description for eventMonth in ctrl.eventMonths track by eventMonth.id"></select>
                  </div>
                  <div class="col-md-3">
                    <select class="form-control col-md-4" ng-disabled="ctrl.onDayorThe==false" ng-model="ctrl.event.onDay"  name="ondays" ng-options=+(i) for i in ctrl.eventOnDays "></select>
                  </div>
                  <div class="col-md-3"> </div>
                </div>
              </div>

              <div class="row">
                <div class="form-group col-md-12">

                  <div class="col-md-2">
                    <label class="col-md-12 control-label text-center">
                      <input type="radio" ng-model="ctrl.onDayorThe" ng-value="false">&nbsp;on the
                    </label>
                  </div>
                  <div class="col-md-3">
                    <select class="form-control col-md-12" ng-disabled="ctrl.onDayorThe==true" ng-model="ctrl.eventOnWeek.id" name="onWeek" ng-options="weekNo.id as weekNo.description for weekNo in ctrl.eventOnWeeks"></select>
                  </div>

                  <div class="col-md-3">
                    <select class="form-control col-md-9" ng-disabled="ctrl.onDayorThe==true" c ng-model="ctrl.eventOnWeekDay.shortName" name="weekday1" ng-options="weekday.shortName as weekDay.description for weekDay in ctrl.eventOnWeekDays track
              by weekday.id"></select>
                  </div>
                  <div class="col-md-1" ng-show="ctrl.event.frequency=='YEARLY'">
                    <label class="col-md-12 control-label text-center"> of </label>
                  </div>
                  <div class="col-md-3" ng-show="ctrl.event.frequency=='YEARLY'">
                    <select class="form-control col-md-7" ng-disabled="ctrl.onDayorThe==true" ng-model="ctrl.eventMonth" name="onMonth" ng-options="eventMonth.description for eventMonth in ctrl.eventMonths track by eventMonth.id"></select>
                  </div>
                </div>
              </div>
            </div>

            <div class="row">
              <div class="form-group col-md-12">
                <label class="col-md-2 control-label" for="end">End</label>
                <div class="col-md-3">
                  <select class="col-md-8 form-control" ng-model="ctrl.eventEndOption" name="end" ng-options="i as i for i in ctrl.eventEndOptions"></select>
                </div>
                <div ng-show="ctrl.eventEndOption=='After'" class="col-md-1">
                  <input type="text" ng-model="ctrl.eventEndCount" id="count" name="eventCount" class="username form-control input-md" placeholder="Enter count" required ng-minlength="1" />
                  <div class="has-error" ng-show="myForm.$dirty">
                    <span ng-show="myForm.eventEndCount.$error.required">This is a required field</span>
                    <span ng-show="myForm.eventEndCount.$error.minlength">Minimum length required is 5</span>
                    <span ng-show="myForm.eventEndCount.$invalid">This field is invalid </span>
                  </div>
                </div>
                <div ng-show="ctrl.eventEndOption=='On date'" class="col-md-4">
                  <div class="col-md-9 input-group date" id="appointment" ng-model="ctrl.eventUntil" date1-picker>
                    <input type="text" class="form-control netto-input col-md-12" ng-model="ctrl.eventUntil" date-picker-input>
                    <span class="input-group-addon">
           													<span class="glyphicon glyphicon-calendar"></span>
                    </span>
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