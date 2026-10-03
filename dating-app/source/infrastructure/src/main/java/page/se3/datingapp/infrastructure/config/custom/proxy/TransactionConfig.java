package page.se3.datingapp.infrastructure.config.custom.proxy;

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

    private static class ApplicationTransactionAnnotationParser implements TransactionAnnotationParser {

        private interface Isolation {

            int ANY = TransactionDefinition.ISOLATION_DEFAULT;
            int READ_COMMITTED = TransactionDefinition.ISOLATION_READ_COMMITTED;
            int REPEATABLE_READ = TransactionDefinition.ISOLATION_REPEATABLE_READ;
            int SERIALIZABLE = TransactionDefinition.ISOLATION_SERIALIZABLE;
        }

        private interface Propagation {

            int REQUIRED = TransactionDefinition.PROPAGATION_REQUIRED;
            int MANDATORY = TransactionDefinition.PROPAGATION_MANDATORY;
            int NEVER = TransactionDefinition.PROPAGATION_NEVER;
        }

        @Override
        public TransactionAttribute parseTransactionAnnotation(@NonNull AnnotatedElement element) {
            if (AnnotatedElementUtils.hasAnnotation(element, ReadCommitted.class)) {
                return createAttribute(Isolation.READ_COMMITTED, Propagation.REQUIRED);
            }
            if (AnnotatedElementUtils.hasAnnotation(element, RepeatableRead.class)) {
                return createAttribute(Isolation.REPEATABLE_READ, Propagation.REQUIRED);
            }
            if (AnnotatedElementUtils.hasAnnotation(element, Serializable.class)) {
                return createAttribute(Isolation.SERIALIZABLE, Propagation.REQUIRED);
            }
            if (AnnotatedElementUtils.hasAnnotation(element, RequiresTransaction.class)) {
                return createAttribute(Isolation.ANY, Propagation.MANDATORY);
            }
            if (AnnotatedElementUtils.hasAnnotation(element, ExplicitNoTransaction.class)) {
                return createAttribute(Isolation.ANY, Propagation.NEVER);
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
