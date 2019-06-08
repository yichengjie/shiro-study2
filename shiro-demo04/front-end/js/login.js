/**
 * Created by yicj on 2019/6/7.
 */
$(function(){
    $("#login-btn").click(function(e){
        e.preventDefault() ;
        var username = $("#username").val() ;
        var password = $("#password").val() ;
        var formData = {username:username,password:password} ;
        console.info(formData)
        var serverUrl = "/apis/ajaxLogin" ;
        var ajaxing = httpUtil.dealAjaxRequest4JSObj(serverUrl,formData) ;
        $.when(ajaxing).done(function(resp){
            console.info(resp) ;
            if(resp.code == 200){
                var token = resp.token ;
                Common.saveToken(token) ;
                window.location = '/index.html' ;
            }else{
                alert(resp.msg)
            }
        }) ;
    }) ;
}) ;
