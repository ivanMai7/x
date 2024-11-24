package xyz.ivan.x.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.ivan.x.util.Result;
import xyz.ivan.x.util.UserHolder;

@Slf4j
@RestControllerAdvice
public class WebExceptionAdvice {
    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeException(RuntimeException e){
        Long userId = UserHolder.getUser().getId();
        log.error("用户：{}执行时500！", userId);
        log.error(e.toString(), e);
        return Result.fail(500, "服务器异常");
    }
}
