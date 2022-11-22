package cloud.agileframework.abstractbusiness.conf;

import cloud.agileframework.abstractbusiness.controller.BaseBusinessService;
import cloud.agileframework.abstractbusiness.service.GenericService;
import cloud.agileframework.abstractbusiness.service.ISecurityService;
import cloud.agileframework.abstractbusiness.service.SecurityService;
import cloud.agileframework.abstractbusiness.service.TemplateEngin;
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
    @ConditionalOnMissingBean(GenericService.class)
    public GenericService baseService(Dao dao, ISecurityService security) {
        return new GenericService(dao, security);
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
    
    @Bean
    public TemplateEngin templateEngin(){
        return new TemplateEngin();
    }

}
