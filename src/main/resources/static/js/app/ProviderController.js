(function(){
'use strict';
var app = angular.module('my-app');

app.controller('ProviderController',
    ['ProviderService', 'LanguageService', 'BrokerageService','$scope', '$compile','DTOptionsBuilder', 'DTColumnBuilder', function( ProviderService,  LanguageService, BrokerageService, $scope,$compile,  DTOptionsBuilder, DTColumnBuilder) {

        var self = this;
        self.prvdr = {};
        self.prvdrs=[];
        self.display =false;
        self.displayEditButton = false;
        self.submit = submit;
        self.languages=[];
        self.getAllProviders = getAllProviders;
        self.createProvider = createProvider;
        self.updateProvider = updateProvider;
        self.removeProvider = removeProvider;
        self.editProvider = editProvider;
        self.addProvider = addProvider;
        self.dtInstance = {};
		self.prvdrId = null;
        self.reset = reset;
        self.getAllLanguages = getAllLanguages;
        self.getAllBrokerages = getAllBrokerages;
        
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
						return '<input type="checkbox" ng-model="ctrl.prvdr['
								+ data
								+ '].checkbox_status" ng-checked="ctrl.prvdr['
								+ data
								+ '].selected" ng-true-value="true" ng-false-value="false" ng-change="ctrl.checkBoxChange(ctrl.prvdr['
								+ data
								+ '].checkbox_status,'
								+ data
								+ ')" />';
					}).withClass("text-center"),
            DTColumnBuilder.newColumn('name').withTitle('PROVIDER'),
            DTColumnBuilder.newColumn('languages[].description').withTitle('LANGUAGE').withOption('defaultContent', ''),
            DTColumnBuilder.newColumn('phone').withTitle('OFFICE NUMBER').withOption('defaultContent', ''),
            DTColumnBuilder.newColumn('address').withTitle('ADDRESS').withOption('defaultContent', ''),
            DTColumnBuilder.newColumn('hoursOfOperation').withTitle('HOURS OF OPERATION').withOption('defaultContent', ''),
            DTColumnBuilder.newColumn('ageSeen').withTitle('AGE SEEN').withOption('defaultContent', '')
          ];
     
        
        self.dtOptions = DTOptionsBuilder.newOptions()
		.withOption(
				'ajax',
				{
					url : '/LeadManagement/api/provider/',
					type : 'GET'
				}).withDataProp('data').withOption('bServerSide', true)
				.withOption("bLengthChange", false)
				.withOption("bPaginate", true)
				.withOption('bProcessing', true)
				.withOption('stateSave', true)
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
			ProviderService
					.loadProviders(page, length, search.value, order)
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
        
       function checkBoxChange(checkStatus, prvdrId) {
			self.displayEditButton = checkStatus;
			self.prvdrId = prvdrId

		}
       
       
        function submit() {
            console.log('Submitting');
            if (self.prvdr.id === undefined || self.prvdr.id === null) {
                console.log('Saving New Provider', self.prvdr);
                createProvider(self.prvdr);
            } else {
                updateProvider(self.prvdr, self.prvdr.id);
                console.log('Provider updated with id ', self.prvdr.id);
            }
            self.displayEditButton = false;
        }

        function createProvider(prvdr) {
            console.log('About to create prvdr');
            ProviderService.createProvider(prvdr)
                .then(
                    function (response) {
                        console.log('Provider created successfully');
                        self.successMessage = 'Provider created successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.display =false;
                        self.prvdrs = getAllProviders();
                        self.prvdr={};
                        $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while creating Provider');
                        self.errorMessage = 'Error while creating Provider: ' + errResponse.data.errorMessage;
                        self.successMessage='';
                    }
                );
        }


        function updateProvider(prvdr, id){
            console.log('About to update prvdr'+prvdr);
            ProviderService.updateProvider(prvdr, id)
                .then(
                    function (response){
                        console.log('Provider updated successfully');
                        self.successMessage='Provider updated successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.display =false;
                        $scope.myForm.$setPristine();
                    },
                    function(errResponse){
                        console.error('Error while updating Provider');
                        self.errorMessage='Error while updating Provider '+errResponse.data;
                        self.successMessage='';
                    }
                );
        }


        function removeProvider(id){
            console.log('About to remove Provider with id '+id);
            ProviderService.removeProvider(id)
                .then(
                    function(){
                        console.log('Provider '+id + ' removed successfully');
                    },
                    function(errResponse){
                        console.error('Error while removing prvdr '+id +', Error :'+errResponse.data);
                    }
                );
        }


        function getAllProviders(){
             self.prvdrs = ProviderService.getAllProviders();
   
            return self.prvdrs;
        }

        
        function getAllLanguages(){
        	return  LanguageService.getAllLanguages();
        }
        
        function getAllBrokerages() {
			return BrokerageService
					.getAllBrokerages();
		}
        
        function editProvider(id) {
            self.successMessage='';
            self.errorMessage='';
            ProviderService.getProvider(id).then(
                function (prvdr) {
                    self.prvdr = prvdr;
                    self.languages = getAllLanguages();
                    console.log('self.languages ' +self.languages ) ;
                    self.display = true;
                },
                function (errResponse) {
                    console.error('Error while removing prvdr ' + id + ', Error :' + errResponse.data);
                }
            );
        }
        
        function reset(){
            self.successMessage='';
            self.errorMessage='';
            self.prvdr={};
            $scope.myForm.$setPristine(); //reset Form
        }
       
        function addProvider() {
            self.successMessage='';
            self.errorMessage='';
            self.languages = getAllLanguages();
            self.display =true;
        }
        
    
    }
    

    ]);
})();    