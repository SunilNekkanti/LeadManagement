'use strict';

app.controller('UserController',
    ['UserService', 'RoleService', 'LanguageService', 'CountyService', 'BrokerageService','$scope', '$compile','DTOptionsBuilder', 'DTColumnBuilder', function( UserService, RoleService, LanguageService, CountyService,BrokerageService, $scope,$compile,  DTOptionsBuilder, DTColumnBuilder) {

        var self = this;
        self.user = {};
        self.users=[];
        self.display =false;
        self.displayEditButton = false;
        self.submit = submit;
        self.roles=[];
        self.languages=[];
        self.counties=[];
        self.brokerages = [];
        self.getAllUsers = getAllUsers;
        self.createUser = createUser;
        self.updateUser = updateUser;
        self.removeUser = removeUser;
        self.editUser = editUser;
        self.dtInstance = {};
		self.userId = null;
        self.reset = reset;
        self.getAllLanguages = getAllLanguages;
        self.getAllCounties = getAllCounties;
        self.getAllBrokerages = getAllBrokerages;
        self.addUser = addUser;
        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;
        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;
        self.roles = RoleService.getAllRoles();
        self.dtInstance = {};
        self.checkBoxChange = checkBoxChange;
        self.dtColumns = [
            
			DTColumnBuilder.newColumn('id')
			.withTitle('ACTION').renderWith(
					function(data, type, full,
							meta) {
						return '<input type="checkbox" ng-model="ctrl.user['
								+ data
								+ '].checkbox_status" ng-checked="ctrl.user['
								+ data
								+ '].selected" ng-true-value="true" ng-false-value="false" ng-change="ctrl.checkBoxChange(ctrl.user['
								+ data
								+ '].checkbox_status,'
								+ data
								+ ')" />';
					}).withClass("text-center"),
            DTColumnBuilder.newColumn('username').withTitle('USERNAME'),
            DTColumnBuilder.newColumn('language.description').withTitle('LANGUAGE').withOption('defaultContent', ''),
            DTColumnBuilder.newColumn('counties[].description').withTitle('COUNTY').withOption('defaultContent', ''),
            DTColumnBuilder.newColumn('brokerage.description').withTitle('BROKERAGE').withOption('defaultContent', ''),
            DTColumnBuilder.newColumn('phone').withTitle('CELL').withOption('defaultContent', ''),
            DTColumnBuilder.newColumn('email').withTitle('EMAIL').withOption('defaultContent', ''),
            DTColumnBuilder.newColumn('position').withTitle('POSITION').withOption('defaultContent', '')
          ];
     
        
        self.dtOptions = DTOptionsBuilder.newOptions()
		.withOption(
				'ajax',
				{
					url : 'http://localhost:8080/LeadManagement/api/user/',
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
			UserService
					.loadUsers(page, length, search.value, order)
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
        
       function checkBoxChange(checkStatus, userId) {
			self.displayEditButton = checkStatus;
			self.userId = userId

		}
       
       
        function submit() {
            console.log('Submitting');
            if (self.user.id === undefined || self.user.id === null) {
                console.log('Saving New User', self.user);
                createUser(self.user);
            } else {
                updateUser(self.user, self.user.id);
                console.log('User updated with id ', self.user.id);
            }
            self.displayEditButton = false;
        }

        function createUser(user) {
            console.log('About to create user');
            UserService.createUser(user)
                .then(
                    function (response) {
                        console.log('User created successfully');
                        self.successMessage = 'User created successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.display =false;
                        self.languages = getAllLanguages();
                        console.log(self.languages);
                        self.user={};
                        $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while creating User');
                        self.errorMessage = 'Error while creating User: ' + errResponse.data.errorMessage;
                        self.successMessage='';
                    }
                );
        }


        function updateUser(user, id){
            console.log('About to update user'+user);
            UserService.updateUser(user, id)
                .then(
                    function (response){
                        console.log('User updated successfully');
                        self.successMessage='User updated successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.display =false;
                        $scope.myForm.$setPristine();
                    },
                    function(errResponse){
                        console.error('Error while updating User');
                        self.errorMessage='Error while updating User '+errResponse.data;
                        self.successMessage='';
                    }
                );
        }


        function removeUser(id){
            console.log('About to remove User with id '+id);
            UserService.removeUser(id)
                .then(
                    function(){
                        console.log('User '+id + ' removed successfully');
                    },
                    function(errResponse){
                        console.error('Error while removing user '+id +', Error :'+errResponse.data);
                    }
                );
        }


        function getAllUsers(){
             self.users = UserService.getAllUsers();
   
            return self.users;
        }

        function getAllRoles(){
            return RoleService.getAllRoles();
        }
        
        function getAllCounties(){
            return CountyService.getAllCounties();
        }
        
        function getAllLanguages(){
        	return  LanguageService.getAllLanguages();
        }
        
        function getAllBrokerages() {
			return BrokerageService
					.getAllBrokerages();
		}
        
        function editUser(id) {
            self.successMessage='';
            self.errorMessage='';
            UserService.getUser(id).then(
                function (user) {
                    self.user = user;
                    self.roles = getAllRoles();
                    self.languages = getAllLanguages();
                    self.counties = getAllCounties();
                    self.brokerages = getAllBrokerages();
                    self.display = true;
                },
                function (errResponse) {
                    console.error('Error while removing user ' + id + ', Error :' + errResponse.data);
                }
            );
        }
        
        function reset(){
            self.successMessage='';
            self.errorMessage='';
            self.user={};
            $scope.myForm.$setPristine(); //reset Form
        }
       
        function addUser() {
            self.successMessage='';
            self.errorMessage='';
            self.languages = getAllLanguages();
            self.roles = getAllRoles();
            self.counties = getAllCounties();
            self.brokerages = getAllBrokerages();
            console.log('self.brokerages' +self.brokerages);
            self.display =true;
        }
        
    
    }
    

    ]);