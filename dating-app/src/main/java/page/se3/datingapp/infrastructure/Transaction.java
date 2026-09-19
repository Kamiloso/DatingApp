package page.se3.datingapp.infrastructure;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

public final class Transaction {

    private Transaction() {}

    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @Documented
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public @interface ReadCommitted {}

    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @Documented
    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public @interface RepeatableRead {}

    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @Documented
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public @interface Serializable {}

    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @Documented
    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
    public @interface Mandatory {}
}
