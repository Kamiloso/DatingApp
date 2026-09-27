package page.se3.datingapp.application.annotation.transaction;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface ReadCommitted {}
