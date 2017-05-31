'use strict';

app.controller('LeadController',
    ['LeadService', 'GenderService', '$scope','$compile', 'DTOptionsBuilder', 'DTColumnBuilder',  function( LeadService, GenderService, $scope,$compile, DTOptionsBuilder, DTColumnBuilder) {

        var self = this;
        self.lead = {};
        self.leads=[];
        self.genders=[];
        self.display =false;
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
            DTColumnBuilder.newColumn('id').withTitle('ID')
            .renderWith(function (data, type, full, meta) {
              return '<input type="checkbox" ng-model="ctrl.displayEditButton"  ng-change="ctrl.checkBoxChange(ctrl.displayEditButton, '+data+')" />';
            }).withClass("text-center"),
            DTColumnBuilder.newColumn('firstName').withTitle('First Name'),
            DTColumnBuilder.newColumn('lastName').withTitle('Last Name'),
            DTColumnBuilder.newColumn('lastName').withTitle('Last Name'),
                DTColumnBuilder.newColumn('dob').withTitle('Date Of Birth')
          ];
     
       
        self.dtOptions = DTOptionsBuilder.fromFnPromise(LeadService.loadAllLeads())
        .withDataProp('response.data.content')
        .withOption('columnDefs', [ {
            orderable: false,
            className: 'select-checkbox',
            targets:   0
        } ])
        .withOption('select', {
                style:    'os',
                selector: 'td:first-child'
            })
    .withOption('createdRow', createdRow)
 .withPaginationType('full_numbers');
        
       
       function createdRow(row, data, dataIndex) {
            // Recompiling so we can bind Angular directive to the DT
            $compile(angular.element(row).contents())($scope);
      console.log("test");
        }
        
       function checkBoxChange(displayButton, leadId){
    	   alert('displayButton '+displayButton + 'leadId '+leadId );
    		   
       }
       
        function submit() {
            console.log('Submitting');
            if (self.lead.id === undefined || self.lead.id === null) {
                console.log('Saving New Lead', self.lead);
                createLead(self.lead);
            } else {
                updateLead(self.lead, self.lead.id);
                console.log('Lead updated with id ', self.lead.id);
            }
        }

        function createLead(lead) {
            console.log('About to create lead');
            LeadService.createLead(lead)
                .then(
                    function (response) {
                        console.log('Lead created successfully');
                        self.successMessage = 'Lead created successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.display =false;
                        self.lead={};
                        $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while creating Lead');
                        self.errorMessage = 'Error while creating Lead: ' + errResponse.data.errorMessage;
                        self.successMessage='';
                    }
                );
        }


        function updateLead(lead, id){
            console.log('About to update lead');
            LeadService.updateLead(lead, id)
                .then(
                    function (response){
                        console.log('Lead updated successfully');
                        self.successMessage='Lead updated successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.display =false;
                        $scope.myForm.$setPristine();
                    },
                    function(errResponse){
                        console.error('Error while updating Lead');
                        self.errorMessage='Error while updating Lead '+errResponse.data;
                        self.successMessage='';
                    }
                );
        }


        function removeLead(id){
            console.log('About to remove Lead with id '+id);
            LeadService.removeLead(id)
                .then(
                    function(){
                        console.log('Lead '+id + ' removed successfully');
                    },
                    function(errResponse){
                        console.error('Error while removing lead '+id +', Error :'+errResponse.data);
                    }
                );
        }


        function getAllLeads(){
            return LeadService.getAllLeads();
        }

        function getAllGenders(){
            return GenderService.getAllGenders();
        }
        
        function editLead(id) {
            self.successMessage='';
            self.errorMessage='';
            self.genders= getAllGenders();
            LeadService.getLead(id).then(
                function (lead) {
                    self.lead = lead;
                    self.display =true;
                },
                function (errResponse) {
                    console.error('Error while removing lead ' + id + ', Error :' + errResponse.data);
                }
            );
        }
        function addLead() {
            self.successMessage='';
            self.errorMessage='';
            self.display =true;
            self.genders= getAllGenders();
        }
        
        function reset(){
            self.successMessage='';
            self.errorMessage='';
            self.lead={};
            $scope.myForm.$setPristine(); //reset Form
        }
    }


    ]);