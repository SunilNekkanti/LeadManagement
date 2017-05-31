'use strict';

app
  .controller(
    'LeadController', [ 'LeadService',  'GenderService',  '$scope',  '$compile',  '$filter',  'DTOptionsBuilder',   'DTColumnBuilder',    function(LeadService, GenderService, $scope, $compile,
        $filter, DTOptionsBuilder, DTColumnBuilder) {

        var self = this;
        self.lead = {};
        self.leads = [];
        self.genders = [];
        self.display = false;
        self.displayEditButton = false;
        self.submit = submit;
        self.addLead = addLead;
        self.getAllLeads = getAllLeads;
        self.createLead = createLead;
        self.updateLead = updateLead;
        self.removeLead = removeLead;
        self.editLead = editLead;
        self.dtInstance = {};

        self.getAllGenders = getAllGenders;
        self.reset = reset;

        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;
        self.checkBoxChange = checkBoxChange;

        self.dtColumns = [
          DTColumnBuilder
          .newColumn('id')
          .withTitle('ID')
          .renderWith(
            function(data, type, full,
              meta) {
              return '<input type="checkbox" ng-model="ctrl.lead[' + data + '].checkbox_status" ng-checked="ctrl.lead[' + data + '].selected" ng-true-value="true" ng-false-value="false" ng-change="ctrl.checkBoxChange(ctrl.lead[' + data + '].checkbox_status,' + data + ')" />';
            }).withClass("text-center"),
          DTColumnBuilder.newColumn('firstName')
          .withTitle('First Name'),
          DTColumnBuilder.newColumn('lastName')
          .withTitle('Last Name'),
          DTColumnBuilder.newColumn('genderId.description')
          .withTitle('GENDER'),
          DTColumnBuilder.newColumn('dob').withTitle(
            'Date Of Birth'),
          DTColumnBuilder.newColumn('hasMedicaid').withTitle(
            'Medicaid'),
          DTColumnBuilder.newColumn('hasMedicare').withTitle(
            'Medicare'),
          DTColumnBuilder.newColumn('hasDisability').withTitle(
            'Disability')
        ];

        // self.dtOptions =
        // DTOptionsBuilder.fromFnPromise(LeadService.loadAllLeads())
        self.dtOptions = DTOptionsBuilder
          .newOptions()
          .withOption(
            'ajax', {
              url: 'http://localhost:8080/LeadManagement/api/lead/',
              type: 'GET',
              data: {
                'page': 0,
                'size': 10
              }
            }).withDataProp('data').withOption(
            'serverSide', true).withOption(
            "bLengthChange", false).withOption(
            "bPaginate", true).withOption(
            'processing', true).withOption(
            'saveState', true)
          .withDisplayLength(10).withOption(
            'columnDefs', [{
              orderable: false,
              className: 'select-checkbox',
              targets: 0,
              sortable: false,
              aTargets: [0, 1]
            }]).withOption('select', {
            style: 'os',
            selector: 'td:first-child'
          }).withOption('createdRow', createdRow)
          .withPaginationType('full_numbers')
          .withFnServerData(serverData);


        function createdRow(row, data, dataIndex) {
          // Recompiling so we can bind Angular directive
          // to the DT
          $compile(angular.element(row).contents())(
            $scope);
          console.log("test");
        }

        function checkBoxChange(checkStatus, leadId) {
          self.displayEditButton = !self.displayEditButton;

        }

        function serverData(sSource, aoData, fnCallback) {
          // All the parameters you need is in the aoData
          // variable

          var draw = aoData[0].value;
          var order = aoData[2].value;
          var page = aoData[3].value / aoData[4].value;
          var length = aoData[4].value;
          var search = aoData[5].value;
          // Then just call your service to get the
          // records from server side
          LeadService.loadLeads(page, length, search, order)
            .then(
              function(result) {
                var records = {
                  'draw':   result.data.number  + 1,
                  'recordsTotal': result.data.totalElements,
                  'recordsFiltered': result.data.totalElements,
                  'data': result.data.content
                };
                fnCallback(records);
              });
        }

        function submit() {
          console.log('Submitting');
          if (self.lead.id === undefined || self.lead.id === null) {
            console.log('Saving New Lead', self.lead);
            createLead(self.lead);
          } else {
            updateLead(self.lead, self.lead.id);
            console.log('Lead updated with id ',
              self.lead.id);
          }
        }

        function createLead(lead) {
          console.log('About to create lead');
          LeadService
            .createLead(lead)
            .then(
              function(response) {
                console
                  .log('Lead created successfully');
                self.successMessage = 'Lead created successfully';
                self.errorMessage = '';
                self.done = true;
                self.display = false;
                self.lead = {};
                $scope.myForm
                  .$setPristine();
              },
              function(errResponse) {
                console
                  .error('Error while creating Lead');
                self.errorMessage = 'Error while creating Lead: ' + errResponse.data.errorMessage;
                self.successMessage = '';
              });
        }

        function updateLead(lead, id) {
          console.log('About to update lead');
          LeadService
            .updateLead(lead, id)
            .then(
              function(response) {
                console
                  .log('Lead updated successfully');
                self.successMessage = 'Lead updated successfully';
                self.errorMessage = '';
                self.done = true;
                self.display = false;
                $scope.myForm
                  .$setPristine();
              },
              function(errResponse) {
                console
                  .error('Error while updating Lead');
                self.errorMessage = 'Error while updating Lead ' + errResponse.data;
                self.successMessage = '';
              });
        }

        function removeLead(id) {
          console.log('About to remove Lead with id ' + id);
          LeadService
            .removeLead(id)
            .then(
              function() {
                console
                  .log('Lead ' + id + ' removed successfully');
              },
              function(errResponse) {
                console
                  .error('Error while removing lead ' + id + ', Error :' + errResponse.data);
              });
        }

        function getAllLeads() {
          return LeadService.getAllLeads();
        }

        function getAllGenders() {
          return GenderService.getAllGenders();
        }

        function editLead(id) {
          self.successMessage = '';
          self.errorMessage = '';
          self.genders = getAllGenders();
          LeadService
            .getLead(id)
            .then(
              function(lead) {
                self.lead = lead;
                self.display = true;
              },
              function(errResponse) {
                console
                  .error('Error while removing lead ' + id + ', Error :' + errResponse.data);
              });
        }

        function addLead() {
          self.successMessage = '';
          self.errorMessage = '';
          self.display = true;
          self.genders = getAllGenders();
        }

        function reset() {
          self.successMessage = '';
          self.errorMessage = '';
          self.lead = {};
          $scope.myForm.$setPristine(); // reset Form
        }


      }
    ]);