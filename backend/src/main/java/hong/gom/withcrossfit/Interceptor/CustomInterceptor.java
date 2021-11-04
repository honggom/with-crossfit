package hong.gom.withcrossfit.Interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CustomInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
    * 컨트롤러 진입 전 동작
    * return true ==> 컨트롤러로 진입
    * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("==================== BEGIN ====================");
        logger.info("Request URI ===> " + "'" + request.getRequestURI() + "'");
        return true;
    }

    /*
     * 컨트롤러 진입 후 View 렌더링 전에 동작
     * */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("==================== END ======================");
    }

    /*
     * afterComplete(HttpServletRequest request, HttpServletResponse response, Object object, Exception ex)
     * 컨트롤러 진입 후 view가 랜더링 된 후에 실행되는 메서드
     * */

    /*
     * afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object h)
     * 비동기 요청 시 PostHandle과 afterCompletion이 수행되지 않고 afterConcurrentHandlingStarted가 수행된다.
     * */

}