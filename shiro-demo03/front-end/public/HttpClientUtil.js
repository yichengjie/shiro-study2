var httpUtil = {};
httpUtil.fillHeader = function(option){
	var token = Common.getToken() ;
	var headers = {
		headers:{"X-ACCESS-TOKEN":token}
	} ;
	$.extend(option,headers) ;
	console.info('ajax option: ', option)
}

httpUtil.dealAjaxRequestWithoutParam = function(serverURL){//异步操作
	 var defer = $.Deferred();
	 var option = {
	   url:serverURL,
	   type: 'GET',
	   dataType:'json',
	   timeout : 100000, //超时时间设置，单位毫秒
	   error: function (err) {
		   defer.reject(err) ;
	   },
	   success:function (result) {
		   defer.resolve(result);
	   }
	 };
	 //向header中填充token信息
	 httpUtil.fillHeader(option) ;
	 $.ajax(option); //发送ajax请
	 return defer.promise();
}

httpUtil.dealAjaxRequest4SimpleParam = function(serverURL,simpleJsonData){//异步操作
	 var defer = $.Deferred();
	 var option = {
	   url:serverURL,
	   type: 'POST',
	   timeout : 100000, //超时时间设置，单位毫秒
	   data:simpleJsonData,
	   dataType:'json',
	   error: function (err) {
		   defer.reject(err) ;
	   },
	   success:function (result) {
		   defer.resolve(result);
	   }
	 };
	 //向header中填充token信息
	 httpUtil.fillHeader(option) ;
	 $.ajax(option); //发送ajax请
	 return defer.promise() ;
}

httpUtil.dealAjaxRequest4JSObj = function(serverURL,jsObjData){//异步操作
	var defer = $.Deferred();
	var option = {
	   contentType:'application/json' ,
	   url:serverURL,
	   type: 'POST',
	   timeout : 100000, //超时时间设置，单位毫秒
	   data:JSON.stringify(jsObjData),
	   dataType:'json',
	   error: function (err) {
		   defer.reject(err) ;
	   },
	   success:function (result) {
		   defer.resolve(result);
	   }
	};
	//向header中填充token信息
	httpUtil.fillHeader(option) ;
	$.ajax(option); //发送ajax请
	return defer.promise() ;
}

httpUtil.dealSYNCHAjaxRequestWithoutParam = function(serverURL){//同步操作无参数访问
	 var defer = $.Deferred();
	 var option = {
	   url:serverURL,
	   async : false,
	   type: 'GET',
	   dataType:'json',
	   timeout : 100000, //超时时间设置，单位毫秒
	   error: function (err) {
		   defer.reject(err) ;
	   },
	   success:function (result) {
		   defer.resolve(result);
	   }
	 };
	 //向header中填充token信息
	 httpUtil.fillHeader(option) ;
	 $.ajax(option); //发送ajax请
	 return defer.promise();
}

httpUtil.dealSYNCHAjaxRequest4SimpleParam = function(serverURL,simpleJsonData){//同步ajax访问
    var defer = $.Deferred();
    var option = {
	   url:serverURL,
	   async : false,
	   type: 'POST',
	   timeout : 100000, //超时时间设置，单位毫秒
	   data:simpleJsonData,
	   dataType:'json',
	   error: function (err) {
		   defer.reject(err) ;
	   },
	   success:function (result) {
		   defer.resolve(result);
	   }
    };
	//向header中填充token信息
	httpUtil.fillHeader(option) ;
    $.ajax(option); //发送ajax请
    return defer.promise() ;
}

httpUtil.dealSYNCHAjaxRequest4JSObj = function(serverURL,jsObjData){//同步操作
	var defer = $.Deferred();
	var option = {
	   contentType:'application/json' ,
	   url:serverURL,
	   timeout : 100000, //超时时间设置，单位毫秒
	   async : false,
	   type: 'POST',
	   data:JSON.stringify(jsObjData),
	   dataType:'json',
	   error: function (err) {
		   defer.reject(err) ;
	   },
	   success:function (result) {
		   defer.resolve(result);
	   }
	};
	//向header中填充token信息
	httpUtil.fillHeader(option) ;
	$.ajax(option); //发送ajax请
	return defer.promise() ;
}
