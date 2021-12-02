package cloud.agileframework.abstractbusiness.service;

import cloud.agileframework.security.filter.login.CustomerUserDetails;
import cloud.agileframework.spring.util.SecurityUtil;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityService implements ISecurityService {
    @Override
    public Long currentUser() {
        UserDetails user = SecurityUtil.currentUser();
        if (user instanceof CustomerUserDetails) {
            return ((CustomerUserDetails) user).id();
        }
        return null;
    }
}
