package page.se3.datingapp.infrastructure.config.proxy;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.annotation.SpringTransactionAnnotationParser;
import org.springframework.transaction.annotation.TransactionAnnotationParser;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import page.se3.datingapp.application.annotation.transaction.*;

import java.lang.reflect.AnnotatedElement;
import java.util.List;

@Configuration
public class TransactionConfig {

    @Bean
    @Primary
    public TransactionAttributeSource customTransactionAttributeSource() {
        return new AnnotationTransactionAttributeSource(
                new SpringTransactionAnnotationParser(),
                new ApplicationTransactionAnnotationParser()
        );
    }

    public static class ApplicationTransactionAnnotationParser implements TransactionAnnotationParser {

        @Override
        public TransactionAttribute parseTransactionAnnotation(@NonNull AnnotatedElement element) {
            if (AnnotatedElementUtils.hasAnnotation(element, ReadCommitted.class)) {
                return createAttribute(
                        TransactionDefinition.ISOLATION_READ_COMMITTED,
                        TransactionDefinition.PROPAGATION_REQUIRED
                );
            }
            if (AnnotatedElementUtils.hasAnnotation(element, RepeatableRead.class)) {
                return createAttribute(
                        TransactionDefinition.ISOLATION_REPEATABLE_READ,
                        TransactionDefinition.PROPAGATION_REQUIRED
                );
            }
            if (AnnotatedElementUtils.hasAnnotation(element, Serializable.class)) {
                return createAttribute(
                        TransactionDefinition.ISOLATION_SERIALIZABLE,
                        TransactionDefinition.PROPAGATION_REQUIRED
                );
            }
            if (AnnotatedElementUtils.hasAnnotation(element, RequiresTransaction.class)) {
                return createAttribute(
                        TransactionDefinition.ISOLATION_DEFAULT,
                        TransactionDefinition.PROPAGATION_MANDATORY
                );
            }
            return null;
        }

        private static TransactionAttribute createAttribute(int isolation, int propagation) {
            RuleBasedTransactionAttribute attribute = new RuleBasedTransactionAttribute();
            attribute.setIsolationLevel(isolation);
            attribute.setPropagationBehavior(propagation);
            attribute.setRollbackRules(List.of(new RollbackRuleAttribute(Exception.class)));
            return attribute;
        }
    }
}
