'use strict';

app.controller('LeadStatusController',
    ['LeadStatusService','$scope', '$compile','DTOptionsBuilder', 'DTColumnBuilder', function( LeadStatusService,  $scope,$compile,  DTOptionsBuilder, DTColumnBuilder) {

        var self = this;
        self.leadStatus = {};
        self.leadStatuses=[];
        self.display =false;
        self.displayEditButton = false;
        self.submit = submit;
        self.getAllLeadStatuses = getAllLeadStatuses;
        self.createLeadStatus = createLeadStatus;
        self.updateLeadStatus = updateLeadStatus;
        self.removeLeadStatus = removeLeadStatus;
        self.editLeadStatus = editLeadStatus;
        self.addLeadStatus = addLeadStatus;
        self.dtInstance = {};
		self.leadStatusId = null;
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
            DTColumnBuilder.newColumn('description').withTitle('LEADSTATUS').renderWith(
					function(data, type, full,
							meta) {
						 return '<a href="javascript:void(0)" class="'+full.id+'" ng-click="ctrl.editLeadStatus('+full.id+')">'+data+'</a>';
					}).withClass("text-left")
          ];
     
        
        self.dtOptions = DTOptionsBuilder.newOptions()
		.withOption(
				'ajax',
				{
					url : '/LeadManagement/api/leadStatus/',
					type : 'GET'
				}).withDataProp('data').withOption('bServerSide', true)
				.withOption("bLengthChange", false)
				.withOption("bPaginate", true)
				.withOption('bProcessing', true)
				.withOption('bSaveState', true)
		        .withDisplayLength(10).withOption( 'columnDefs', [ {
					                                orderable : false,
													className : 'select-checkbox',
													targets : 0,
													sortable : false,
													aTargets : [ 0, 1 ] } ])
				.withOption('select', {
										style : 'os',
										selector : 'td:first-child' })
			    .withOption('createdRow', createdRow)
		        .withPaginationType('full_numbers')
		        
		        .withFnServerData(serverData);

    	function serverData(sSource, aoData, fnCallback) {
			
			
			// All the parameters you need is in the aoData
			// variable
			var order = aoData[2].value;
			var page = aoData[3].value / aoData[4].value;
			var length = aoData[4].value;
			var search = aoData[5].value;

			// Then just call your service to get the
			// records from server side
			LeadStatusService
					.loadLeadStatuses(page, length, search.value, order)
					.then(
							function(result) {
								var records = {
									'recordsTotal' : result.data.totalElements,
									'recordsFiltered' : result.data.totalElements,
									'data' : result.data.content
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
        
       function checkBoxChange(checkStatus, leadStatusId) {
			self.displayEditButton = checkStatus;
			self.leadStatusId = leadStatusId

		}
       
       
        function submit() {
            console.log('Submitting');
            if (self.leadStatus.id === undefined || self.leadStatus.id === null) {
                console.log('Saving New LeadStatus', self.leadStatus);
                createLeadStatus(self.leadStatus);
            } else {
                updateLeadStatus(self.leadStatus, self.leadStatus.id);
                console.log('LeadStatus updated with id ', self.leadStatus.id);
            }
            self.displayEditButton = false;
        }

        function createLeadStatus(leadStatus) {
            console.log('About to create leadStatus');
            LeadStatusService.createLeadStatus(leadStatus)
                .then(
                    function (response) {
                        console.log('LeadStatus created successfully');
                        self.successMessage = 'LeadStatus created successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.display =false;
                        console.log(self.languages);
                        self.leadStatus={};
                        $scope.myForm.$setPristine();
                        self.dtInstance.reloadData();
                        self.dtInstance.rerender();
                    },
                    function (errResponse) {
                        console.error('Error while creating LeadStatus');
                        self.errorMessage = 'Error while creating LeadStatus: ' + errResponse.data.errorMessage;
                        self.successMessage='';
                    }
                );
        }


        function updateLeadStatus(leadStatus, id){
            console.log('About to update leadStatus'+leadStatus);
            LeadStatusService.updateLeadStatus(leadStatus, id)
                .then(
                    function (response){
                        console.log('LeadStatus updated successfully');
                        self.successMessage='LeadStatus updated successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.display =false;
                        $scope.myForm.$setPristine();
                        self.dtInstance.reloadData();
                        self.dtInstance.rerender();
                    },
                    function(errResponse){
                        console.error('Error while updating LeadStatus');
                        self.errorMessage='Error while updating LeadStatus '+errResponse.data;
                        self.successMessage='';
                    }
                );
        }


        function removeLeadStatus(id){
            console.log('About to remove LeadStatus with id '+id);
            LeadStatusService.removeLeadStatus(id)
                .then(
                    function(){
                        console.log('LeadStatus '+id + ' removed successfully');
                    },
                    function(errResponse){
                        console.error('Error while removing leadStatus '+id +', Error :'+errResponse.data);
                    }
                );
        }


        function getAllLeadStatuses(){
             self.leadStatuses = LeadStatusService.getAllLeadStatuses();
   
            return self.leadStatuses;
        }

        function editLeadStatus(id) {
            self.successMessage='';
            self.errorMessage='';
            LeadStatusService.getLeadStatus(id).then(
                function (leadStatus) {
                    self.leadStatus = leadStatus;
                    self.display = true;
                },
                function (errResponse) {
                    console.error('Error while removing leadStatus ' + id + ', Error :' + errResponse.data);
                }
            );
        }
        
        function reset(){
            self.successMessage='';
            self.errorMessage='';
            self.leadStatus={};
            $scope.myForm.$setPristine(); //reset Form
        }
       
        function cancelEdit(){
            self.successMessage='';
            self.errorMessage='';
            self.facilityType={};
            $scope.myForm.$setPristine(); //reset Form
            self.display = false;
        }
        
        function addLeadStatus() {
            self.successMessage='';
            self.errorMessage='';
            self.display =true;
        }
        
    
    }
    

    ]);