package dev.chijiokeibekwe.jwtsecurity.annotation;

import dev.chijiokeibekwe.jwtsecurity.config.WithMockUserSecurityContextFactory;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockUserSecurityContextFactory.class)
public @interface WithMockAdmin {

    String id() default "1";

    String email() default "john.doe@library.com";

    String phone() default "+2348012345678";

}
