package com.yicj.study.shiro.shiro;

import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class MySessionManager extends DefaultWebSessionManager {
	private static final String XACCESSTOKEN = "X-ACCESS-TOKEN";
	private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";
	private Logger logger = LoggerFactory.getLogger(MySessionManager.class) ;
	@Override
	protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
		String tokenId = WebUtils.toHttp(request).getHeader(XACCESSTOKEN);
		// 如果请求头中有 Authorization 则其值为sessionId
		logger.debug("=======> 从x-access-token 获取登录id : " + tokenId);
		if (!StringUtils.isEmpty(tokenId)) {
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, 
					REFERENCED_SESSION_ID_SOURCE);
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, tokenId);
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID,
					Boolean.TRUE);
			return tokenId;
		} else {// 否则按默认规则从cookie取sessionId
			Serializable sessionId = super.getSessionId(request, response);
			logger.debug("从cookies中获取登录id为 : " + sessionId);
			return sessionId ;
		}
	}

}
