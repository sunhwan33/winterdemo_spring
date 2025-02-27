package com.thc.winterdemo.interceptor;

import com.thc.winterdemo.util.TokenFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class DefaultInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TokenFactory tokenFactory ;
    public DefaultInterceptor(TokenFactory tokenFactory) {
        this.tokenFactory = tokenFactory;
    }

    //컨트롤러 진입 전 메서드
    public boolean prehandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("prehandle / request [{}]", request);
        String accesstoken = request.getHeader("Authorization");
        if(accesstoken !=null){
            Long userId = tokenFactory.validateAccessToken(accesstoken);
            if(userId != null && userId >0){
                request.setAttribute("reqUserId", userId);
            }
        }
        return true;
    }

    //컨트롤러 실행 후 메서드
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("postHandle / request [{}]", request);

    }

    //모든 것을 마친 후 실행되는 메서드
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("afterCompletion / request [{}]", request);
    }

}
