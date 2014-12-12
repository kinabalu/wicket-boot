package demo;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.protocol.http.WicketFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

@RestController
class Test {
    @RequestMapping("/foo")
    String hi() {
        return "Hi";
    }
}

@Configuration
class WicketServletConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "wicketFilter")
    public FilterRegistrationBean wicketFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();

        WicketFilter filter = new WicketFilter();


        registration.setFilter(filter);
        registration.setName("wicketFilter");
        registration.addInitParameter("applicationClassName", MyWebApplication.class.getName());
        registration.setUrlPatterns( Arrays.asList(this.wicketPath));
        return registration;
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

    private String wicketPath = "/wicket/*";
}

class HelloWorld extends WebPage {
    public HelloWorld() {
        add(new Label("message", "Hello World!"));
    }
}

/*

<?xml version="1.0" encoding="ISO-8859-1"?>
<web-app xmlns="http://java.sun.com/xml/ns/j2ee"
	 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	 xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd"
	 version="2.4">

	<display-name>mysticpaste</display-name>

    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:/applicationContext.xml</param-value>
    </context-param>

    <context-param>
        <param-name>wicket.configuration</param-name>
        <param-value>DEPLOYMENT</param-value>
    </context-param>

    <filter-mapping>
		<filter-name>wicket.mysticpaste</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>

	<!--
		The SpringWebApplicationFactory will need access to a Spring
		Application context, configured like this...
	-->
	<listener>
		<listener-class>
		  org.springframework.web.context.ContextLoaderListener
		</listener-class>
	</listener>

</web-app>
 */