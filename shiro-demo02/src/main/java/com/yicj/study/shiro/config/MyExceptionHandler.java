package com.yicj.study.shiro.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackages ="com.yicj.study.shiro.controller")
public class MyExceptionHandler {
	
	@ResponseBody
    @ExceptionHandler(value = Exception.class)
	public Map<String,Object> exceptionHandler(Exception ex) {
		Map<String, Object> retObj = new HashMap<String, Object>();
		if (ex instanceof UnauthenticatedException) {
			retObj.put("code", "2000001");
			retObj.put("msg", "token错误");
		} else if (ex instanceof UnauthorizedException) {
			retObj.put("code", "2000002");
			retObj.put("msg", "用户无权限");
		} else {
			retObj.put("code", "2000003");
			retObj.put("msg", ex.getMessage());
		}
		return retObj;
	}

}
