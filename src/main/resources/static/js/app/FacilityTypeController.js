(function(){
'use strict';
var app = angular.module('my-app');

app.controller('FacilityTypeController',
    ['FacilityTypeService', '$scope', '$compile','$state','DTOptionsBuilder', 'DTColumnBuilder',  function( FacilityTypeService, $scope, $compile, $state, DTOptionsBuilder, DTColumnBuilder) {

    	
        var self = this;
        self.facilityType = {};
        self.facilityTypes=[];
        self.display =false;
        self.displayEditButton = false;
        self.submit = submit;
        self.addFacilityType = addFacilityType;
        self.getAllFacilityTypes = getAllFacilityTypes;
        self.createFacilityType = createFacilityType;
        self.updateFacilityType = updateFacilityType;
        self.removeFacilityType = removeFacilityType;
        self.editFacilityType = editFacilityType;
        self.reset = reset;
        self.facilityTypeId = null;
        self.cancelEdit = cancelEdit;
        self.dtInstance = {};
        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;
        self.dtColumns = [
            DTColumnBuilder.newColumn('description').withTitle('FACILITY TYPE').renderWith(
					function(data, type, full,
							meta) {
						 return '<a href="javascript:void(0)" class="'+full.id+'" ng-click="ctrl.editFacilityType('+full.id+')">'+data+'</a>';
					}).withClass("text-left")
          ];
     
        
        self.dtOptions = DTOptionsBuilder.newOptions().withBootstrap()
        .withDisplayLength(20)
		.withOption('bServerSide', true)
				.withOption("bLengthChange", false)
				.withOption("bPaginate", true)
				.withOption('bProcessing', true)
				.withOption('bStateSave', true)
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

			var paramMap = {};
			for ( var i = 0; i < aoData.length; i++) {
			  paramMap[aoData[i].name] = aoData[i].value;
			}
			
			var sortCol ='';
			var sortDir ='';
			// extract sort information
			 if(paramMap['columns'] !== undefined && paramMap['columns'] !== null && paramMap['order'] !== undefined && paramMap['order'] !== null ){
				 sortCol = paramMap['columns'][paramMap['order'][0]['column']].data;
				  sortDir = paramMap['order'][0]['dir'];
			 }
			 
			// Then just call your service to get the
			// records from server side
			FacilityTypeService
					.loadFacilityTypes(page, length, search.value, sortCol+','+sortDir)
					.then(
							function(result) {
								var records = {
									'recordsTotal' : result.data.totalElements||0,
									'recordsFiltered' : result.data.totalElements||0,
									'data' : result.data.content||{}
								};
								fnCallback(records);
							});
		}

		 function reloadData() {
			var resetPaging = false;
			self.dtInstance.reloadData(callback,
					resetPaging);
			self.dtInstance.rerender();
		} 
		 

		function callback(json) {
				console.log(json);
		}
			
		 
       function createdRow(row, data, dataIndex) {
            // Recompiling so we can bind Angular directive to the DT
            $compile(angular.element(row).contents())($scope);
        }
        
       function checkBoxChange(checkStatus, facilityTypeId) {
			self.displayEditButton = checkStatus;
			self.facilityTypeId = facilityTypeId

		}
       
        function submit() {
            console.log('Submitting');
            if (self.facilityType.id === undefined || self.facilityType.id === null) {
                console.log('Saving New FacilityType', self.facilityType);
                createFacilityType(self.facilityType);
            } else {
                updateFacilityType(self.facilityType, self.facilityType.id);
                console.log('FacilityType updated with id ', self.facilityType.id);
            }
            self.displayEditButton = false;
        }

        function createFacilityType(facilityType) {
            console.log('About to create facilityType');
            FacilityTypeService.createFacilityType(facilityType)
                .then(
                    function (response) {
                        console.log('FacilityType created successfully');
                        self.successMessage = 'FacilityType created successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.display =false;
                        self.facilityType={};
                        $scope.myForm.$setPristine();
                        self.dtInstance.reloadData();
                        self.dtInstance.rerender();
                    },
                    function (errResponse) {
                        console.error('Error while creating FacilityType');
                        self.errorMessage = 'Error while creating FacilityType: ' + errResponse.data.errorMessage;
                        self.successMessage='';
                    }
                );
        }


        function updateFacilityType(facilityType, id){
            console.log('About to update facilityType');
            FacilityTypeService.updateFacilityType(facilityType, id)
                .then(
                    function (response){
                        console.log('FacilityType updated successfully');
                        self.successMessage='FacilityType updated successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.display =false;
                        $scope.myForm.$setPristine();
                        self.dtInstance.reloadData();
                        self.dtInstance.rerender();
                    },
                    function(errResponse){
                        console.error('Error while updating FacilityType');
                        self.errorMessage='Error while updating FacilityType '+errResponse.data;
                        self.successMessage='';
                    }
                );
        }


        function removeFacilityType(id){
            console.log('About to remove FacilityType with id '+id);
            FacilityTypeService.removeFacilityType(id)
                .then(
                    function(){
                        console.log('FacilityType '+id + ' removed successfully');
                    },
                    function(errResponse){
                        console.error('Error while removing facilityType '+id +', Error :'+errResponse.data);
                    }
                );
        }


        function getAllFacilityTypes(){
            return FacilityTypeService.getAllFacilityTypes();
        }

        function editFacilityType(id) {
            self.successMessage='';
            self.errorMessage='';
            FacilityTypeService.getFacilityType(id).then(
                function (facilityType) {
                    self.facilityType = facilityType;
                    self.display =true;
                },
                function (errResponse) {
                    console.error('Error while removing facilityType ' + id + ', Error :' + errResponse.data);
                }
            );
        }
        
        function addFacilityType() {
            self.successMessage='';
            self.errorMessage='';
            self.display =true;
        }
        
        
        function reset(){
            self.successMessage='';
            self.errorMessage='';
            self.facilityType={};
            $scope.myForm.$setPristine(); //reset Form
        }
        
        function cancelEdit(){
            self.successMessage='';
            self.errorMessage='';
            self.facilityType={};
            self.display = false;
            $state.go('main.facilityType', {}, {reload: false});
        }
    }

    ]);
 })();    