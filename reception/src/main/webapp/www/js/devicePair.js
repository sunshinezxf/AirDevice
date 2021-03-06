app.controller('DevicePairCtrl',function($stateParams,$http,$scope,$state, $interval,$ionicLoading) {
	$scope.deviceID = $stateParams.deviceID;
	var complete = function(){
		$http({  
	        url    : '/reception/own/register/complete',  
	        method : "get",  
	        params   : { serial : $scope.deviceID},  
	    }).success(function(response){
	    	if(response.status == 1){
	    		$state.go('home.device')
	    	}else{
	    		$scope.info = response.info
	    	}
	    });
	}
	
	var timer = $interval(complete, 2 * 1000)
	  $scope.$on('$ionicView.beforeLeave',function(){
		  $interval.cancel(timer);
	  })
	
})