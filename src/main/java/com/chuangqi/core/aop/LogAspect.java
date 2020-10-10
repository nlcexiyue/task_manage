package com.chuangqi.core.aop;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @Description aop切面
 * @Author qizhentao
 * @Date 2019/11/14 11:14
 * @Version 1.0
 */
@Component
@Aspect
@Log4j2
public class LogAspect {

    /** 切入点 */
    private final String POINT_CUT = "execution(public * com.chuangqi.controller.*.*(..))";

    /**
     * 环绕通知-代码执行时间
     *
     * @param proceedingJoinPoint
     * @return
     */
    @Around(value = POINT_CUT)
    public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        TimeInterval timer = DateUtil.timer();
        Object obj = null;
        try {
            obj = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        log.info("{}耗时：{}ms", proceedingJoinPoint, timer.interval());
        //return "没有权限";
        return obj;
    }

}
