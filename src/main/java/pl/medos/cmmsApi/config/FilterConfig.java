package pl.medos.cmmsApi.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<IpFilter> ipFilterGate() {
        FilterRegistrationBean<IpFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new IpFilter());
        registrationBean.addUrlPatterns("/api/*"); // Specify the URL patterns to which the filter should be applied
        return registrationBean;
    }
}
