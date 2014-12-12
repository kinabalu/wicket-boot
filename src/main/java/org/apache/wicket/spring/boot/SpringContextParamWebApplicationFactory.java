package org.apache.wicket.spring.boot;

import org.apache.wicket.Application;
import org.apache.wicket.IApplicationListener;
import org.apache.wicket.protocol.http.ContextParamWebApplicationFactory;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

/**
 * Created by kinabalu on 12/11/14.
 */
public class SpringContextParamWebApplicationFactory extends ContextParamWebApplicationFactory {

    public WebApplication createApplication(WicketFilter filter)
    {
        final String applicationClassName = filter.getFilterConfig().getInitParameter(
                APP_CLASS_PARAM);

        WebApplication webApplication = super.createApplication(applicationClassName);
        webApplication.getApplicationListeners().add(new SpringApplicationListener());
//        webApplication.getComponentInstantiationListeners().add(new SpringComponentInjector(webApplication));
        return webApplication;
    }

    protected WebApplication createApplication(final String applicationClassName) {
        WebApplication webApplication = super.createApplication(applicationClassName);
        webApplication.getApplicationListeners().add(new SpringApplicationListener());
//        webApplication.getComponentInstantiationListeners().add(new SpringComponentInjector(webApplication));
        return webApplication;
    }

}

class SpringApplicationListener implements IApplicationListener {

    @Override
    public void onAfterInitialized(Application application) {
        application.getComponentInstantiationListeners().add(new SpringComponentInjector((WebApplication)application));
    }

    @Override
    public void onBeforeDestroyed(Application application) {

    }
}