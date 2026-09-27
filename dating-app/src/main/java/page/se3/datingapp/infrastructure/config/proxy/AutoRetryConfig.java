package page.se3.datingapp.infrastructure.config.proxy;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.PessimisticLockingFailureException;
import page.se3.datingapp.application.annotation.AutoRetry;
import page.se3.datingapp.domain.config.RetryConfig;
import page.se3.datingapp.domain.core.util.ThreadUtils;
import page.se3.datingapp.domain.exception.UnexpectedConflictException;
import page.se3.datingapp.domain.exception.abort.AbortException;

@Configuration
@RequiredArgsConstructor
public class AutoRetryConfig {

    private final RetryConfig retryConfig;

    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor() {
        Pointcut classPointcut = new AnnotationMatchingPointcut(AutoRetry.class, true);
        Pointcut methodPointcut = AnnotationMatchingPointcut.forMethodAnnotation(AutoRetry.class);

        return new DefaultPointcutAdvisor(
                new ComposablePointcut(classPointcut).union(methodPointcut),
                new AutoRetryInterceptor(retryConfig)
        );
    }

    @RequiredArgsConstructor
    private static class AutoRetryInterceptor implements MethodInterceptor {

        private final RetryConfig retryConfig;

        @Override
        public Object invoke(@NonNull MethodInvocation invocation) throws Throwable {
            int retriesLeft = retryConfig.maxRetries();
            int sleepMillis = retryConfig.initialSleepMillis();
            double sleepTimeMultiplier = retryConfig.sleepTimeMultiplier();

            while (true) {
                try {
                    return invocation.proceed();
                } catch (Throwable e) {
                    if (!shouldRetry(e) || retriesLeft <= 0) {
                        throw e;
                    }

                    retriesLeft--;
                    ThreadUtils.sleep(sleepMillis);
                    sleepMillis = (int) (sleepMillis * sleepTimeMultiplier);
                }
            }
        }

        private boolean shouldRetry(Throwable e) {
            return e instanceof AbortException
                    || e instanceof UnexpectedConflictException
                    || e instanceof PessimisticLockingFailureException;
        }
    }
}
