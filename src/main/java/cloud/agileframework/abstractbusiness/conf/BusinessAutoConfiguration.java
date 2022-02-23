package cloud.agileframework.abstractbusiness.conf;

import cloud.agileframework.abstractbusiness.controller.BaseBusinessService;
import cloud.agileframework.abstractbusiness.service.BaseService;
import cloud.agileframework.abstractbusiness.service.ISecurityService;
import cloud.agileframework.abstractbusiness.service.SecurityService;
import cloud.agileframework.data.common.dao.BaseDao;
import cloud.agileframework.jpa.config.DaoAutoConfiguration;
import cloud.agileframework.jpa.dao.Dao;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述：
 * <p>创建时间：2018/12/3<br>
 *
 * @author 佟盟
 * @version 1.0
 * @since 1.0
 */
@ConditionalOnClass(DaoAutoConfiguration.class)
@ConditionalOnProperty(prefix = "agile.jpa", name = {"enable"})
@Configuration
public class BusinessAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(BaseServiceOfController.class)
    public BaseServiceOfController baseService(Dao dao, ISecurityService security) {
        return new BaseServiceOfController(dao, security);
    }

    @Bean
    @ConditionalOnMissingBean(BaseBusinessService.class)
    public BaseBusinessService baseBusinessController() {
        return new BaseBusinessService();
    }

    @Bean
    @ConditionalOnMissingBean(type = "cloud.agileframework.security.config.SecurityAutoConfiguration.class")
    public ISecurityService noSecurityService() {
        return () -> null;
    }

    @Bean
    @ConditionalOnBean(type = "cloud.agileframework.security.config.SecurityAutoConfiguration.class")
    public ISecurityService securityService() {
        return new SecurityService();
    }

    public static class BaseServiceOfController implements BaseService {
        private final BaseDao dao;
        private final ISecurityService security;

        public BaseServiceOfController(BaseDao dao, ISecurityService security) {
            this.dao = dao;
            this.security = security;
        }

        @Override
        public BaseDao dao() {
            return dao;
        }

        @Override
        public ISecurityService security() {
            return security;
        }
    }
}
