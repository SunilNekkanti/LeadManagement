'use strict';

app.controller('UserController',
    ['UserService', 'RoleService', '$scope', 'DTOptionsBuilder', 'DTColumnBuilder', function( UserService, RoleService, $scope,  DTOptionsBuilder, DTColumnBuilder) {


    	
        var self = this;
        self.user = {};
        self.users=[];
        self.display =false;
        self.submit = submit;
        self.roles=[];
        self.getAllUsers = getAllUsers;
        self.createUser = createUser;
        self.updateUser = updateUser;
        self.removeUser = removeUser;
        self.editUser = editUser;
        self.reset = reset;
        self.addUser = addUser;
        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;
        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;
        self.roles = RoleService.getAllRoles();
        self.dtInstance = null;
        self.dtColumns = [
            DTColumnBuilder.newColumn('id').withTitle('ID'),
            DTColumnBuilder.newColumn('username').withTitle('user name'),
            DTColumnBuilder.newColumn('password').withTitle('Password')
          ];
     
       
        self.dtOptions = DTOptionsBuilder.fromFnPromise(UserService.loadAllUsers())
        .withDataProp('response.data.content')
 .withPaginationType('full_numbers');
        
        self.dtIntanceCallback = function (instance) {
            self.dtInstance = instance;
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
        
        function editUser(id) {
            self.successMessage='';
            self.errorMessage='';
            UserService.getUser(id).then(
                function (user) {
                    self.user = user;
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
            self.display =true;
        }
        
        	

    }

    ]);