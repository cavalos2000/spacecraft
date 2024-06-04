package w2m.travel.spacecraft.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class NegativeIdAspect {

    @Before("@annotation(CheckNegativeId) && args(id,..)")
    public void checkNegativeId(JoinPoint joinPoint, Long id) {
        if (id < 0) {
            log.info("line added by aspect when Id is negative");
            throw new IllegalArgumentException("ID cannot be negative: " + id);
        }
    }
}

