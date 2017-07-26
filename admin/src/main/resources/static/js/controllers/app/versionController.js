'use strict';
app.controller('VersionAddController',
		[
				'$scope',
				'$http',
				'$state',
				'$stateParams','toaster',
				function($scope, $http, $state, $stateParams,toaster) {

					$scope.initData = function() {
						$scope.version = {};
						$scope.version.isTimeToUpdate = 'no';
						$scope.app.id = $stateParams.appId;
						$scope.appname = $stateParams.appName;
						console.log($stateParams.appName);
						$scope.version.updateNow = '0';
						$scope.version.version = '1.0';
					};

					$scope.addVersion = function() {
						toaster.pop('wait', 'info','正在处理');
						$scope.processing = true;
						var appFile = $scope.version.file;
						var fd = new FormData();
						fd.append('appFile', appFile);
						fd.append('version', $scope.version.version);
						fd.append('updateInfo', $scope.version.updateInfo);
						fd.append('updateNow', $scope.version.updateNow);
						fd.append('plat', $scope.version.plat);
						$http.post('app/' + $scope.app.id + '/version', fd, {
							transformRequest : angular.identity,
							headers : {
								'Content-Type' : undefined,
							}
						}).then(function(response) {
							if (response.status == 200) {
								  toaster.clear();
								  $scope.processing = false;
								  $state.go('app.appedit',{id:$scope.app.id});
							}
						}, function(x) {
							toaster.clear();
							$scope.processing = false;
						});
					};

					$scope.initData();
					$scope.today = function() {
						$scope.dt = new Date();
					};
					$scope.today();

					$scope.clear = function() {
						$scope.dt = null;
					};

					// Disable weekend selection
					$scope.disabled = function(date, mode) {
						return (mode === 'day' && (date.getDay() === 0 || date
								.getDay() === 6));
					};

					$scope.toggleMin = function() {
						$scope.minDate = $scope.minDate ? null : new Date();
					};
					$scope.toggleMin();

					$scope.open = function($event) {
						$event.preventDefault();
						$event.stopPropagation();

						$scope.opened = true;
					};

					$scope.dateOptions = {

						startingDay : 1,

						format : 'dd MM yyyy - hh:ii'
					};

					$scope.initDate = new Date('2016-15-20');
					$scope.formats = [ 'mediumTime', 'yyyy/MM/dd',
							'dd.MM.yyyy', 'shortDate' ];
					$scope.format = $scope.formats[0];

				} ]);

app.controller('VersionEditController', [
		'$scope',
		'$http',
		'$state',
		'$stateParams','toaster',
		function($scope, $http, $state, $stateParams,toaster) {
			$scope.queryVersion = function() {
				$http.get(
						'app/' + $stateParams.appId + "/" + $stateParams.plat
								+ "/version").then(function(response) {
					if (response.status == 200) {
						console.log(response.data);
						$scope.version = response.data;
					}
				}, function(x) {
					// alter error
				});
			};
			$scope.queryVersion();

			$scope.updateVersion = function() {
				toaster.pop('wait', 'info','正在处理');
				$scope.processing = true;
				var appFile = $scope.app.file;
				var fd = new FormData();
				fd.append('appFile', appFile);
				fd.append('version', $scope.version.version);
				fd.append('updateInfo', $scope.version.updateInfo);
				fd.append('updateNow', $scope.version.updateNow);
				fd.append('plat', $scope.version.plat);
				$http.put('app/' + $scope.version.appId + '/version', fd, {
					transformRequest : angular.identity,
					headers : {
						'Content-Type' : undefined,
					}
				}).then(function(response) {
					if (response.status == 200) {
						toaster.clear();
						  $scope.processing = false;
					   $state.go('app.appedit',{id:$scope.version.appId});
					}
				}, function(x) {
					toaster.clear();
					  $scope.processing = false;
				});
			};

		} ]);
