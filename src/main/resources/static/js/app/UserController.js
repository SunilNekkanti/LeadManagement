'use strict';

app.controller('UserController',
    ['UserService', 'RoleService', '$scope', 'DTOptionsBuilder', 'DTColumnBuilder', function( UserService, RoleService, $scope, DTOptionsBuilder, DTColumnBuilder) {


    	
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
        self.buildDatatable = buildDatatable;
        self.reset = reset;
        self.addUser = addUser;
        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;
        buildDatatable();
        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;
        self.roles = RoleService.getAllRoles();
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
            return UserService.getAllUsers();
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
        
        function buildDatatable(){
        	self.dtColumns = [
                //here We will add .withOption('name','column_name') for send column name to the server 
                DTColumnBuilder.newColumn("id", "User ID").withOption('name', 'id'),
                DTColumnBuilder.newColumn("username", "UserName").withOption('name', 'username'),
                DTColumnBuilder.newColumn("password", "Password").withOption('name', 'password'),
            ]
     
            self.dtOptions = DTOptionsBuilder.newOptions().withOption( self.users)
            .withOption('processing', true) //for show progress bar
            .withOption('serverSide', true) // for server side processing
            .withPaginationType('full_numbers') // for get full pagination options // first / last / prev / next and page numbers
            .withDisplayLength(10) // Page size
            .withOption('aaSorting',[0,'asc']) // for default sorting column // here 0 means first column
        }
        
            
    }


    ]);