package xyz.ivan.x.interceptos;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import xyz.ivan.x.dto.UserDTO;
import xyz.ivan.x.util.RedisConstants;
import xyz.ivan.x.util.UserHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class RefreshTokenInterceptor implements HandlerInterceptor {
    private final StringRedisTemplate stringRedisTemplate;

    public RefreshTokenInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tokenAndId = request.getHeader("Authorization");
        if(StrUtil.isBlank(tokenAndId)){
            return true;
        }
        // 将token和用户id分出来
        String[] split = tokenAndId.split(":");
        String token = split[0];
        String userId = split[1];

        // 得到存在redis中的用户
        Map<Object,Object> userMap = stringRedisTemplate.opsForHash().entries(RedisConstants.TOKEN_PREFIX + userId + ":" + token);

        UserDTO userDTO = BeanUtil.fillBeanWithMap(userMap, new UserDTO(), false);

        // 如果查到userDTO
        if(userDTO.getId() != null){
            stringRedisTemplate.expire(RedisConstants.TOKEN_PREFIX + userId + ":" + token,RedisConstants.TOKEN_TTL, TimeUnit.DAYS);
            UserHolder.saveUser(userDTO);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }
}
