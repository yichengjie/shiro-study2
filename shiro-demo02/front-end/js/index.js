/**
 * Created by yicj on 2019/6/7.
 */
$(function(){
    $("#user_view_btn").click(function(){
        var serverUrl = "/apis/user/view" ;
        var ajaxing = httpUtil.dealAjaxRequestWithoutParam(serverUrl) ;
        $.when(ajaxing).done(function(resp){
            console.info(resp) ;
            var str = JSON.stringify(resp) ;
            alert(str) ;

        }) ;
    }) ;

    $("#user_create_btn").click(function(){
        var serverUrl = "/apis/user/create" ;
        var ajaxing = httpUtil.dealAjaxRequestWithoutParam(serverUrl) ;
        $.when(ajaxing).done(function(resp){
            console.info(resp) ;
            var str = JSON.stringify(resp) ;
            alert(str) ;
        }) ;
    }) ;
}) ;