package aop.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
//@Component
public class LoggingAspect {
    @Before("@annotation(aop.aspect.LoggingEnabled)")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Вызов метода : " + joinPoint.getSignature().getName());
        System.out.println("Аргументы метода: " + Arrays.toString(joinPoint.getArgs()));
    }
    @AfterReturning(
            pointcut ="@annotation(aop.aspect.LoggingEnabled)",
            returning= "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        System.out.println("Завершили метод : " + joinPoint.getSignature().getName());
        System.out.println("Метод вернул результат:" + result);
    }
}
