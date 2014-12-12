package org.apache.wicket.spring.boot;

import org.apache.wicket.protocol.http.WicketFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Configuration
@ConditionalOnClass(name = "org.apache.wicket.protocol.http.WicketFilter")
@EnableConfigurationProperties(WicketProperties.class)
public class WicketAutoConfiguration {

    private static final String WICKET_FILTER_NAME = "wicketFilter";

    @Autowired
    private WicketProperties wicketProperties;

    @Bean
    public ServletContextInitializer initializer() {
        return new ServletContextInitializer() {
            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {
                servletContext.setInitParameter("wicket.configuration", wicketProperties.getConfiguration().name());
            }
        };
    }

    @Bean(name = WICKET_FILTER_NAME)
    @ConditionalOnMissingBean(name = WICKET_FILTER_NAME)
    public FilterRegistrationBean wicketFilter() {
        String contextPath = this.wicketProperties.getContextPath();
        FilterRegistrationBean registration = new FilterRegistrationBean();
        WicketFilter filter = new WicketFilter();
        filter.setFilterPath(contextPath);
        registration.setFilter(filter);
        registration.setName(WICKET_FILTER_NAME);
//
//        registration.addInitParameter(ContextParamWebApplicationFactory.APP_CLASS_PARAM, MyWebApplication.class
//                        .getName());
        registration.addInitParameter(WicketFilter.APP_FACT_PARAM, BootWebApplicationFactory.class.getName());

        return registration;
    }


}
