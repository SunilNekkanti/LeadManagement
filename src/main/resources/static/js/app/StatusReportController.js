(function() {
  'use strict';
  var app = angular.module('my-app');

  app.controller('StatusReportController', ['StatusReportService', 'LeadStatusService', 'RoleService', 'UserService', 'EventService', '$scope', '$compile', '$state', 'DTOptionsBuilder', 'DTColumnBuilder', function(StatusReportService, LeadStatusService, RoleService, UserService, EventService, $scope, $compile, $state, DTOptionsBuilder, DTColumnBuilder) {

      var self = this;
      self.display = false;
      self.displayEditButton = false;
      self.getAllLeadStatuses = getAllLeadStatuses;
      self.getAllRoles = getAllRoles;
      self.getAllUsers = getAllUsers;
      self.getAllEvents = getAllEvents;
      self.leadStatuses = getAllLeadStatuses() || [];
      self.roles = getAllRoles() || [];
      self.users = getAllUsers() || [];
      self.events = getAllEvents() || [];
      self.selectedStatuses = self.leadStatuses || [];
      self.selectedRoles = self.roles || [];
      self.selectedUsers = self.users || [];
      self.selectedEvents = self.events || [];
      var date = new Date();
      self.startDate = moment(date).format('MM/DD/YYYY') ;
      self.endDate = moment(date.setDate(date.getDate() + 1)).format('MM/DD/YYYY') ;
      self.dtInstance = {};
      self.generate = generate;
      self.reset = reset;
      self.cancelEdit = cancelEdit;
      self.dtInstance = {};
      self.successMessage = '';
      self.errorMessage = '';
      self.done = false;
      self.onlyIntegers = /^\d+$/;
      self.onlyNumbers = /^\d+([,.]\d+)?$/;
      self.dtInstance = {};
      self.checkBoxChange = checkBoxChange;
      self.dtColumns = [

        DTColumnBuilder.newColumn('userName').withTitle('USER'),
        DTColumnBuilder.newColumn('status').withTitle('LEAD_STATUS'),
        DTColumnBuilder.newColumn('event').withTitle('EVENT'),
        DTColumnBuilder.newColumn('count').withTitle('COUNT')
      ];


      self.dtOptions = DTOptionsBuilder.newOptions()
        .withDisplayLength(500)
        .withDOM('ft')
        .withOption('bServerSide', true)
        .withOption("bLengthChange", false)
        .withOption("bPaginate", true)
        .withOption('bProcessing', true)
        .withOption('bDeferRender', true)
        .withOption('bDestroy', true)
        .withOption('createdRow', createdRow)
        .withOption('scrollY', 450)
        .withOption('scrollX', '100%')
        .withFnServerData(serverData);

      function serverData(sSource, aoData, fnCallback) {


        // All the parameters you need is in the aoData
        // variable
        var order = aoData[2].value;
        var page = aoData[3].value / aoData[4].value;
        var length = aoData[4].value;
        var search = aoData[5].value;

        var paramMap = {};
        for (var i = 0; i < aoData.length; i++) {
          paramMap[aoData[i].name] = aoData[i].value;
        }

        var sortCol = '';
        var sortDir = '';
        // extract sort information
        if (paramMap['columns'] !== undefined && paramMap['columns'] !== null && paramMap['order'] !== undefined && paramMap['order'] !== null) {
          sortCol = paramMap['columns'][paramMap['order'][0]['column']].data;
          sortDir = paramMap['order'][0]['dir'];
        }

        var statuses = (self.selectedStatuses === undefined || self.selectedStatuses === null) ? 0 : self.selectedStatuses.map(a => a.id).join();
        var roles = (self.selectedRoles === undefined || self.selectedRoles === null) ? 0 : self.selectedRoles.map(a => a.id).join();
        var userNames = (self.selectedUsers === undefined || self.selectedUsers === null) ? 0 : self.selectedUsers.map(a => a.username).join();
        var events = (self.selectedEvents === undefined || self.selectedEvents === null) ? 0 : self.selectedEvents.map(a => a.id).join();
        // Then just call your service to get the
        // records from server side
        StatusReportService
          .loadStatusReport(page, length, search.value, sortCol + ',' + sortDir, statuses, roles, userNames, events, self.startDate, self.endDate)
          .then(
            function(result) {
              var records = {
                'recordsTotal': result.data.totalElements || 0,
                'recordsFiltered': result.data.totalElements || 0,
                'data': result.data || {}
              };
              fnCallback(records);
            });
      }

      function reloadData() {
        var resetPaging = false;
        self.dtInstance.reloadData(callback,
          resetPaging);
      }

      function createdRow(row, data, dataIndex) {
        // Recompiling so we can bind Angular directive to the DT
        $compile(angular.element(row).contents())($scope);
      }

      function checkBoxChange(checkStatus, leadStatusDetailId) {
        self.displayEditButton = checkStatus;
        self.leadStatusDetailId = leadStatusDetailId

      }


      function generate() {
        self.displayTable = true;
        if( !angular.equals(self.dtInstance, {}) ) {self.dtInstance.rerender();}

      }



      function reset() {
        self.successMessage = '';
        self.errorMessage = '';
        self.leadStatusDetail = {};
      }

      function cancelEdit() {
        self.successMessage = '';
        self.errorMessage = '';
        self.leadStatusDetail = {};
        self.display = false;
        $state.go('main.leadStatusDetail', {}, {
          reload: false
        });
      }

      function getAllLeadStatuses() {
        return LeadStatusService.getAllLeadStatuses();
      }

      function getAllRoles() {
        return RoleService.getAllRoles();
      }

      function getAllUsers() {
        return UserService.getAllUsers();
      }

      function getAllEvents() {
        return EventService.getAllEvents();
      }

    }


  ]);
})();
