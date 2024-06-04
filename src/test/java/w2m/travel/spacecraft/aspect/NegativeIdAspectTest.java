package w2m.travel.spacecraft.aspect;

import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@EnableAspectJAutoProxy
class NegativeIdAspectTest {

    @Mock
    private Logger logger;

    @InjectMocks
    private NegativeIdAspect negativeIdAspect;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        negativeIdAspect = new NegativeIdAspect();
    }

    @Test
    void checkNegativeId_whenIdIsNegative_shouldThrowException() {
        JoinPoint joinPoint = mock(JoinPoint.class);
        Long negativeId = -1L;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            negativeIdAspect.checkNegativeId(joinPoint, negativeId);
        });

        assertEquals("ID cannot be negative: -1", exception.getMessage());
        //verify(logger, times(1)).info("line added by aspect when Id is negative");
    }

    @Test
    void checkNegativeId_whenIdIsPositive_shouldNotThrowException() {
        JoinPoint joinPoint = mock(JoinPoint.class);
        Long positiveId = 1L;

        assertDoesNotThrow(() -> {
            negativeIdAspect.checkNegativeId(joinPoint, positiveId);
        });

        verify(logger, never()).info("line added by aspect when Id is negative");
    }
}
