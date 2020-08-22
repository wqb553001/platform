package com.activitiserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;

@Component
public class SecurityUtil {

    private Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    // @Qualifier(value = "beanName") and @Autowired 两个配合，等价于 @Resource(name="beanName")
    @Resource(name = "jdbcLocalUserDetailsManager")
    private JdbcLocalUserDetailsManager jdbcLocalUserDetailsManager;

    public void logInAs(String epmNoAndUsername) {

        UserDetails user = jdbcLocalUserDetailsManager.loadUserByUsername(epmNoAndUsername);
        if (user == null) {
            throw new IllegalStateException("User " + epmNoAndUsername + " doesn't exist, please provide a valid user");
        }
        logger.info("> Logged in as: " + epmNoAndUsername);
        SecurityContextHolder.setContext(new SecurityContextImpl(new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return user.getAuthorities();
            }

            @Override
            public Object getCredentials() {
                return user.getPassword();
            }

            @Override
            public Object getDetails() {
                return user;
            }

            @Override
            public Object getPrincipal() {
                return user;
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return user.getUsername();
            }
        }));
        org.activiti.engine.impl.identity.Authentication.setAuthenticatedUserId(epmNoAndUsername);
    }
}
