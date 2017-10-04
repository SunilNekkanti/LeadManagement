'use strict';

app.controller('InsuranceController',
    ['InsuranceService','PlanTypeService', '$scope', '$compile','DTOptionsBuilder', 'DTColumnBuilder', function( InsuranceService,  PlanTypeService,  $scope,$compile,  DTOptionsBuilder, DTColumnBuilder) {

        var self = this;
        self.insurance = {};
        self.insurances=[];
        self.display =false;
        self.displayEditButton = false;
        self.submit = submit;
        self.planTypes=[];
        self.getAllInsurances = getAllInsurances;
        self.createInsurance = createInsurance;
        self.updateInsurance = updateInsurance;
        self.removeInsurance = removeInsurance;
        self.editInsurance = editInsurance;
        self.addInsurance = addInsurance;
        self.getAllPlanTypes = getAllPlanTypes;
        self.dtInstance = {};
		self.insuranceId = null;
        self.reset = reset;
        
        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;
        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;
        self.checkBoxChange = checkBoxChange;
        self.dtColumns = [
            
			DTColumnBuilder.newColumn('id')
			.withTitle('ACTION').renderWith(
					function(data, type, full,
							meta) {
						return '<input type="checkbox" ng-model="ctrl.insurance['
								+ data
								+ '].checkbox_status" ng-checked="ctrl.insurance['
								+ data
								+ '].selected" ng-true-value="true" ng-false-value="false" ng-change="ctrl.checkBoxChange(ctrl.insurance['
								+ data
								+ '].checkbox_status,'
								+ data
								+ ')" />';
					}).withClass("text-center"),
            DTColumnBuilder.newColumn('name').withTitle('INSURANCE'),
            DTColumnBuilder.newColumn('planType.description').withTitle('PLAN TYPE')
          ];
     
        
        self.dtOptions = DTOptionsBuilder.newOptions()
		.withOption(
				'ajax',
				{
					url : '/LeadManagement/api/insurance/',
					type : 'GET'
				}).withDataProp('data').withOption('bServerSide', true)
				.withOption("bLengthChange", false)
				.withOption("bPaginate", true)
				.withOption('bProcessing', true)
				.withOption('bSaveState', true)
		        .withDisplayLength(20).withOption( 'columnDefs', [ {
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
			var page = aoData[3].value / aoData[4].value ;
			var length = aoData[4].value ;
			var search = aoData[5].value;

			
			// Then just call your service to get the
			// records from server side
			InsuranceService
					.loadInsurances(page, length, search.value, order)
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
        
       function checkBoxChange(checkStatus, insuranceId) {
			self.displayEditButton = checkStatus;
			self.insuranceId = insuranceId

		}
       
       
        function submit() {
            console.log('Submitting');
            if (self.insurance.id === undefined || self.insurance.id === null) {
                console.log('Saving New Insurance', self.insurance);
                createInsurance(self.insurance);
            } else {
                updateInsurance(self.insurance, self.insurance.id);
                console.log('Insurance updated with id ', self.insurance.id);
            }
            self.displayEditButton = false;
        }

        function createInsurance(insurance) {
            console.log('About to create insurance');
            InsuranceService.createInsurance(insurance)
                .then(
                    function (response) {
                        console.log('Insurance created successfully');
                        self.successMessage = 'Insurance created successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.display =false;
                        self.insurances = getAllInsurances();
                        self.insurance={};
                        $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while creating Insurance');
                        self.errorMessage = 'Error while creating Insurance: ' + errResponse.data.errorMessage;
                        self.successMessage='';
                    }
                );
        }


        function updateInsurance(insurance, id){
            console.log('About to update insurance'+insurance);
            InsuranceService.updateInsurance(insurance, id)
                .then(
                    function (response){
                        console.log('Insurance updated successfully');
                        self.successMessage='Insurance updated successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.display =false;
                        $scope.myForm.$setPristine();
                    },
                    function(errResponse){
                        console.error('Error while updating Insurance');
                        self.errorMessage='Error while updating Insurance '+errResponse.data;
                        self.successMessage='';
                    }
                );
        }


        function removeInsurance(id){
            console.log('About to remove Insurance with id '+id);
            InsuranceService.removeInsurance(id)
                .then(
                    function(){
                        console.log('Insurance '+id + ' removed successfully');
                    },
                    function(errResponse){
                        console.error('Error while removing insurance '+id +', Error :'+errResponse.data);
                    }
                );
        }


        function getAllInsurances(){
             self.insurances = InsuranceService.getAllInsurances();
   
            return self.insurances;
        }
        
        
        function getAllPlanTypes(){
            self.planTypes = PlanTypeService.getAllPlanTypes();
  
           return self.planTypes;
       }
        
        
        function editInsurance(id) {
            self.successMessage='';
            self.errorMessage='';
            self.planTypes = getAllPlanTypes();
            InsuranceService.getInsurance(id).then(
                function (insurance) {
                    self.insurance = insurance;
                   
                    self.display = true;
                },
                function (errResponse) {
                    console.error('Error while removing insurance ' + id + ', Error :' + errResponse.data);
                }
            );
        }
        
        function reset(){
            self.successMessage='';
            self.errorMessage='';
            self.insurance={};
            $scope.myForm.$setPristine(); //reset Form
        }
       
        function addInsurance() {
            self.successMessage='';
            self.errorMessage='';
            self.planTypes = getAllPlanTypes();
            self.display =true;
        }
        
    
    }
    

    ]);