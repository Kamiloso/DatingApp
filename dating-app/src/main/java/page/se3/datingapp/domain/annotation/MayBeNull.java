package page.se3.datingapp.domain.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({
        ElementType.RECORD_COMPONENT,
        ElementType.FIELD,
        ElementType.PARAMETER,
        ElementType.METHOD
})
public @interface MayBeNull {} // does completely nothing
