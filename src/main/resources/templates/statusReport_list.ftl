<div class="generic-container " >
  <div class="panel panel-success" ng-if="!ctrl.display" >
    <!-- Default panel contents -->
    <div class="panel-heading"><span class="statusReport">Status Report </span> </div>
    <div class="table-responsive">
      <div class="panel-body">
        <div class="panel-body">
          <div class="formcontainer" >
            <div class="col-sm-2">
              <div class="form-group col-sm-12">
                <label for="plan">Statuses</label>
                 <multiselect ng-model="ctrl.selectedStatuses" ng-change="ctrl.reset()"  placeholder="Choose Status(es)"   options="ctrl.leadStatuses" id-prop="id" display-prop="description" show-search="true" show-select-all="true" show-unselect-all="true" search-limit="10"></multiselect>
              </div>
            </div>
            <div class="col-sm-1">
              <div class="form-group col-sm-12">
                <label for="plan">Roles</label>
                <multiselect ng-model="ctrl.selectedRoles" ng-change="ctrl.reset()"  placeholder="Choose Role(s)"   options="ctrl.roles" id-prop="id" display-prop="role" show-search="true" show-select-all="true" show-unselect-all="true" search-limit="10"></multiselect>
              </div>
            </div>

            <div class="col-sm-1">
              <div class="form-group col-sm-12">
                <label for="plan">Users</label>
                <multiselect ng-model="ctrl.selectedUsers"  ng-change="ctrl.reset()"  placeholder="Choose User(s)" options="ctrl.users" id-prop="id" display-prop="name" show-search="true" show-select-all="true" show-unselect-all="true"  search-limit="10"></multiselect>
              </div>
            </div>

            <div class="col-sm-3">
              <div class="form-group col-sm-12">
                <label for="plan">Events</label>
                <multiselect ng-model="ctrl.selectedEvents"  ng-change="ctrl.reset()"  placeholder="Choose Event(s)" options="ctrl.events" id-prop="id" display-prop="eventName" show-search="true" show-select-all="true" show-unselect-all="true"  search-limit="10"></multiselect>
              </div>
            </div>


            <div class="col-sm-2">
               <label class="control-label" for="StartDate">Start Date</label>
              <div class="input-group date" id="startDate"  ng-model="ctrl.startDate"  ng-change="ctrl.validEventDate(ctrl.StartDate,ctrl.endDate)" date1-picker   >
                    <input type="text" class="form-control netto-input" name="StartDate"  ng-model="ctrl.startDate" date-picker-input  ng-change="ctrl.validEventDate(ctrl.startDate,ctrl.endDate)" required>
                    <span class="input-group-addon">
           				<span class="glyphicon glyphicon-calendar"></span>
                    </span>
                  </div>
                   <div class="has-error" ng-show="myForm.$dirty">
                        <span ng-show="myForm.startDate.$invalid">This field is invalid </span>
                        <span ng-show="myForm.startDate.$error.required">This is a required field</span>
                   </div>
            </div>

             <div class="col-sm-2">
               <label class="control-label" for="EndDate">End Date</label>
              <div class="input-group date" id="endDate"  ng-model="ctrl.endDate"  ng-change="ctrl.validEventDate(ctrl.StartDate,ctrl.endDate)" date1-picker   >
                    <input type="text" class="form-control netto-input" name="EndDate"  ng-model="ctrl.endDate" date-picker-input  ng-change="ctrl.validEventDate(ctrl.startDate,ctrl.endDate)" required>
                    <span class="input-group-addon">
           				<span class="glyphicon glyphicon-calendar"></span>
                    </span>
                  </div>
                   <div class="has-error" ng-show="myForm.$dirty">
                        <span ng-show="myForm.endDate.$invalid">This field is invalid </span>
                        <span ng-show="myForm.endDate.$error.required">This is a required field</span>
                   </div>
            </div>


            <div class="col-sm-1 container">
                <div class="btn-holder">
                <button type="button"   ng-click="ctrl.generate()" class="btn btn-warning btn-xs " ng-disabled="((ctrl.selectedStatuses.length ===0) || (ctrl.selectedRoles.length ===0)|| (ctrl.selectedUsers.length ===0) || (ctrl.selectedEvents.length ===0) || !ctrl.startDate || !ctrl.endDate )">Generate</button>
                </div> 
            </div>
          </div>
        </div>

         <div style="height:600px" ng-if="!ctrl.displayTable"> </div>

         <div style="height:600px" ng-if="ctrl.displayTable">
         <tabset>
                 <tab  heading="Summary"  ng-if="ctrl.displayTable" >
	                    <table datatable="" id="content" dt-options="ctrl.dtOptions" dt-columns="ctrl.dtColumns"  dt-instance="ctrl.dtInstance" dt-disable-deep-watchers="true" class="table table-hover table-responsive  bordered table-striped table-condensed datatable dt-responsive nowrap dataTable row-border hover"
	                    cellspacing="0" width="100%"></table>
	 			</tab>
         </tabset>
             <div  class="table-responsive" ng-show="ctrl.chartTabShow" >
             <canvas class="chart chart-line" id="chart" chart-data="ctrl.graph.data" chart-labels="ctrl.graph.labels" chart-series="ctrl.graph.series" chart-options="ctrl.graph.options"  chart-dataset-override="ctrl.graph.datasetOverride"   height="70"> </canvas>
             </div>

              <div  class="table-responsive" ng-show="ctrl.chartTabShow1" >
             <canvas class="chart chart-line" id="chart2" chart-data="ctrl.graph.data1" chart-labels="ctrl.graph.labels1" chart-series="ctrl.graph.series1" chart-options="ctrl.graph.options1"  chart-dataset-override="ctrl.graph.datasetOverride1"   height="70"> </canvas>
             </div>
         </div>

      </div>
    </div>
  </div>


</div>
