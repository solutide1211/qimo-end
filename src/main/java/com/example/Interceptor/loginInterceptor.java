package com.example.Interceptor;

import com.example.util.JwtUtil;
import com.example.util.ThreadLocalUtil;
import com.example.util.redisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class loginInterceptor implements HandlerInterceptor {
    @Autowired
    redisUtil redisUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //令牌验证
        final String token = request.getHeader("Authorization");

        final Map<String, Object> map = JwtUtil.parseToken(token);
        ThreadLocalUtil.set(map);
        final Integer id = (Integer) map.get("id");
        System.out.println("拦截器中的ID" + id);
//        final String = redisUtil.get("id");
        //从redis中获取token，判断是否存在，存在返回true，并且重新设置有效期，
        // 不存在则返回false设置响应状态401
        if(token != null && redisUtil.get(String.valueOf(id))!=null){
            redisUtil.expire(token,1000*60*60, TimeUnit.SECONDS);
            return true;
        }
        //未授权
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}
