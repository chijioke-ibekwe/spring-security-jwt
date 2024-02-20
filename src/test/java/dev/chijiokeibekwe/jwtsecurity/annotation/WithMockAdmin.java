package dev.chijiokeibekwe.jwtsecurity.annotation;

import dev.chijiokeibekwe.jwtsecurity.config.WithMockAdminSecurityContextFactory;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockAdminSecurityContextFactory.class)
public @interface WithMockAdmin {

    String id() default "1";

    String email() default "jane.doe@starter.com";

    String phone() default "+2348012345678";

}
