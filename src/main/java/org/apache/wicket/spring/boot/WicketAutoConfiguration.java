package org.apache.wicket.spring.boot;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.apache.wicket.spring.SpringWebApplicationFactory;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.lang.reflect.Method;

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

        // - use a SpringWebApplicationFactory subclass and then let that discover the WebApplication from a Spring context
        String springAppFactoryClassName = WicketAutoConfiguration.BootWebApplicationFactory.class.getName();
        registration.addInitParameter(WicketFilter.APP_FACT_PARAM, springAppFactoryClassName);

        return registration;
    }

    public static class SpringWebApplicationBeanPostProcessor implements BeanPostProcessor {
        @Override
        public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
            return o;
        }

        @Override
        public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
            if (o instanceof WebApplication) {
                return webApplication((WebApplication) o);
            }
            return o;
        }
    }

    @Bean
    public SpringWebApplicationBeanPostProcessor springWebApplicationBeanPostProcessor() {
        return new SpringWebApplicationBeanPostProcessor();
    }


    static WebApplication webApplication(WebApplication webApplication) {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(webApplication);
        proxyFactoryBean.setProxyTargetClass(true);

        proxyFactoryBean.addAdvice(new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation methodInvocation) throws Throwable {
                Method method = methodInvocation.getMethod();
                String methodName = method.getName();
                if (methodName.equals("init")) {
                    Method initMethod = webApplication.getClass().getMethod("init");
                    initMethod.setAccessible(true);
                    initMethod.invoke(webApplication);
                    webApplication.getComponentInstantiationListeners().add(
                            new SpringComponentInjector(webApplication));
                }
                return methodInvocation.proceed();

            }
        });
        Object proxy = proxyFactoryBean.getObject();
        WebApplication returnVal = (WebApplication) proxy;
        return returnVal;
    }

/*
    @Bean
    @ConditionalOnMissingBean
    public FilterRegistrationBean wicketSessionFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new WicketSessionFilter());
        registration.setName("wicketSessionFilter");
        registration.setUrlPatterns();
        registration.setUrlPatterns(Arrays.asList(this.wicketPath));
        registration.addInitParameter("filterName", "wicketFilter");
        return registration;
    }*/

    public static class BootWebApplicationFactory extends SpringWebApplicationFactory {
    }

}
