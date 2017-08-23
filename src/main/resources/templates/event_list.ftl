<div class="generic-container">

  <div class="panel panel-default" ng-hide="ctrl.display">
    <!-- Default panel contents -->
    <div class="panel-heading"><span class="user">List of Events </span>
      <button type="button" ng-click="ctrl.addEvent()" ng-hide="ctrl.displayEditButton" class="btn btn-success btn-xs custom-width floatRight"> Add </button>
      <button type="button" ng-click="ctrl.editEvent(ctrl.eventId)" ng-show="ctrl.displayEditButton" class="btn btn-primary btn-xs custom-width floatRight">Edit</button>
      <button type="button" ng-click="ctrl.removeEvent(ctrl.eventId)" ng-show="ctrl.displayEditButton" class="btn btn-danger btn-xs custom-width floatRight">Remove</button>
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
                  <select ng-model="ctrl.event.state" name="state" class="form-control"  ng-options="state.description for state in ctrl.states track by state.description" required></select>
                  <div class="has-error" ng-show="myForm.$dirty">
                    <span ng-show="myForm.state.$error.required">This is a required field</span>
                  </div>
                </div>

                <label class="col-md-1  control-label" for="zipcode">Zipcode</label>
                <div class="col-md-3">
                  <select ng-model="ctrl.event.zipCode" name="zipCode" class="form-control" ng-options="zipCode.code for zipCode in ctrl.event.state.zipCodes track by zipCode.code" required></select>
                  <div class="has-error" ng-show="myForm.$dirty">
                    <span ng-show="myForm.zipCode.$error.required">This is a required field</span>
                  </div>
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

                <label class="col-md-2  control-label" for="facilityType">Facility</label>
                <div class="col-md-2">
                  <select class="col-md-12 form-control" ng-model="ctrl.event.facilityType" name="facilityType" ng-options="facilityType.description for facilityType in ctrl.facilityTypes track by facilityType.description" required></select>
                  <div class="has-error" ng-show="myForm.$dirty">
                    <span ng-show="myForm.facilityType.$error.required">This is a required field</span>
                  </div>
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
              
               <div class="form-group col-md-12 uploadedFiles" ng-if="ctrl.event.id">
                <div require>
                  <label class="col-md-2  control-label" for="contactEmail">Uploaded Files</label>
                </div>
                <div ng-repeat="attachment in ctrl.event.attachments" class="col-md-3">
						<a  class="form-control no-border" ng-click="ctrl.readUploadedFile(attachment.id, attachment.contentType)"  style="display:block;">
          					<span class="glyphicon glyphicon-file"></span>
        				</a>
                </div>
              </div>
              
              
            </div>
          </div>

      


        </form>
      </div>
    </div>
  </div>
</div>