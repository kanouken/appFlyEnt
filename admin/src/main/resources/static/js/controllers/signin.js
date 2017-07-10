'use strict';

/* Controllers */
// signin controller
app.controller('SigninFormController', [ '$scope', '$http', '$state',
                                         '$sessionStorage','$rootScope',
		function($scope, $http, $state,$sessionStorage,$rootScope) {
			$scope.rootScope = $rootScope;
			$scope.user = {};
			$scope.authError = null;
			$scope.login = function() {
				$scope.authError = null;
				// Try to login
				$http({
					method : 'post',
					url : 'https://api.onestong.cn/8002/crm/web/user/login',
					data:$.param({email: $scope.user.email, password: md5($scope.user.password) }),
					headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
				}).then(function successCallback(response) {
					$sessionStorage.userInfo = response.data.data;
					console.log(response.data.data);
					$scope.rootScope.$broadcast('user.login.success');
					$state.go("app.applist");
				}, function errorCallback(response) {
					$scope.authError = 'Server Error';
				});

			};
		} ]);
