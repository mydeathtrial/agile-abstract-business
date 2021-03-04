package cloud.agileframework.abstractbusiness.conf;

import cloud.agileframework.abstractbusiness.controller.BaseBusinessService;
import cloud.agileframework.abstractbusiness.service.BaseService;
import cloud.agileframework.jpa.config.DaoAutoConfiguration;
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
    @ConditionalOnMissingBean(BaseService.class)
    public BaseService baseService() {
        return new BaseService();
    }

    @Bean
    @ConditionalOnMissingBean(BaseBusinessService.class)
    public BaseBusinessService baseBusinessController() {
        return new BaseBusinessService();
    }
}
