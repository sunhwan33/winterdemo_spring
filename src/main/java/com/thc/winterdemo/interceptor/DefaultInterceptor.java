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
    private final TokenFactory tokenFactory;
    public DefaultInterceptor(TokenFactory tokenFactory){
        this.tokenFactory = tokenFactory;
    }
    //컨트롤러 진입 전 메서드
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("preHandle / request [{}]", request);
//        request.setAttribute("test", "test1234");
//        response.setHeader("testheader", "112233");
//        logger.info("1-1 preHandle / request test [{}]", request.getAttribute("test"));
//        logger.info("1-1 preHandle / response test [{}]", request.getHeader("testheader"));
        String accesstoken = request.getHeader("Authorization");
        if(accesstoken != null){
            Long userId = tokenFactory.validateAccessToken(accesstoken);
            if(userId != null && userId > 0) {
                request.setAttribute("reqUserId", userId);
            }
        }

        return true;
    }
    //컨트롤러 실행 후 메서드
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("postHandle / request [{}]", request);
//        logger.info("2-1 postHandle / request test [{}]", request.getAttribute("test"));
//        logger.info("2-1 postHandle / response test [{}]", request.getHeader("testheader"));
    }
    //모든 것을 마친 후 실행되는 메서드
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("afterCompletion / request [{}]", request);
//

    }
}
