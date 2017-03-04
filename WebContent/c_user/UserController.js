'use strict';

app.controller(	'UserController', [	'$scope', 'UserService', '$location','$rootScope','$cookieStore',
						'$http',
						function($scope, UserService, $location, $rootScope,
								$cookieStore,$http) {
							console.log("UserController...")
							var self = this;
							this.user = {userId : '',name : '', password : '',	mobile : '',
								address : '', email : '',isOnline : '',	role : '',
								errorCode : '',	errorMessage : '' , imageUrl:''
							};
							
							this.users = []; //json array
							
							 $scope.orderByMe = function(x) {
							        $scope.myOrderBy = x;
							    }
					

							 this.fetchAllUsers = function() {
								console.log("fetchAllUsers...")
								UserService
										.fetchAllUsers()
										.then(
												function(d) {
													self.users = d;
												},
												function(errResponse) {
													console
															.error('Error while fetching Users');
												});
							};
							
							//self.fatchAllUsers();

							self.createUser = function(user) {
								console.log("createUser...")
								UserService
										.createUser(user)
										.then(
												function(d)
												{
													alert("Thank you for registration")
													$location.path("/")
												},
												function(errResponse) {
													console
															.error('Error while creating User.');
												});
							};
							
							self.myProfile = function() {
								console.log("myProfile...")
								UserService
										.myProfile()
										.then(
												function(d) {
													self.user = d;
													$location.path("/myProfile")
												},
												function(errResponse) {
													console
															.error('Error while fetch profile.');
												});
							};
							
							self.accept = function(id) {
								console.log("accept...")
								UserService
										.accept(id)
										.then(
												function(d) {
													self.user = d;
													self.fetchAllUsers
													$location.path("/manage_users")
													alert(self.user.errorMessage)
													
												},
												
												function(errResponse) {
													console
															.error('Error while updating User.');
												});
							};
							
							self.reject = function( id) {
								console.log("reject...")
								var reason = prompt("Please enter the reason");
								UserService
										.reject(id,reason)
										.then(
												function(d) {
													self.user = d;
													self.fetchAllUsers
													$location.path("/manage_users")
													alert(self.user.errorMessage)
													
												},
												null );
							};

							self.updateUser = function(user) {
								console.log("updateUser...")
								UserService
										.updateUser(user)
										.then(
												self.fetchAllUsers,
												null);
							};
							
							self.update = function() {
								{
									console.log('Update the user details', self.user);
									self.updateUser(self.user);
								}
								self.reset();
							};
							

							self.authenticate = function(user) {
								console.log("authenticate...")
								UserService
										.authenticate(user)
										.then(

												function(d) {

													self.user = d;
													console
															.log("user.errorCode: "
																	+ self.user.errorCode)
													if (self.user.errorCode == "404")

													{
														alert(self.user.errorMessage)

														self.user.userId = "";
														self.user.password = "";

													} else { //valid credentials
														console
																.log("Valid credentials. Navigating to home page")
																
																self.fetchAllUsers(); 
														
														console.log('Current user : '+self.user)
														$rootScope.currentUser = self.user
                                                     	$cookieStore.put('currentUser', self.user);
														
														$http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.currentUser; 
														$location.path('/chat_forum');

													}

												},
												function(errResponse) {

													console
															.error('Error while authenticate Users');
												});
							};

							self.logout = function() {
								console.log("logout")
								$rootScope.currentUser = {};
								$cookieStore.remove('currentUser');
								UserService.logout()
								$location.path('/');

							}

						

							//self.fetchAllUsers();  //calling the method
							
							//better to call fetchAllUsers -> after login ???

							self.login = function() {
								{
									console.log('login validation????????',
											self.user);
									self.authenticate(self.user);
								}

							};

							self.submit = function() {
								{
									console.log('Saving New User', self.user);
									self.createUser(self.user);
								}
								self.reset();
							};

							self.reset = function() {
								self.user = {
									id : '',
									name : '',
									password : '',
									mobile : '',
									address : '',
									email : '',
									isOnline : '',
									errorCode : '',
									errorMessage : ''
								};
								$scope.myForm.$setPristine(); // reset Form
							};

						} ]);













