package org.apache.wicket.spring.boot;

import org.apache.wicket.RuntimeConfigurationType;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.wicket", ignoreUnknownFields = true)
public class WicketProperties {

    private String contextPath = "/wicket/*";

    private org.apache.wicket.RuntimeConfigurationType configuration =
            RuntimeConfigurationType.DEVELOPMENT;

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public RuntimeConfigurationType getConfiguration() {
        return configuration;
    }

    public void setConfiguration(RuntimeConfigurationType configuration) {
        this.configuration = configuration;
    }
}
