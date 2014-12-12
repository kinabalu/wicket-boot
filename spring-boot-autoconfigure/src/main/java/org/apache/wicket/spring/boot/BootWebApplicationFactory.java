package org.apache.wicket.spring.boot;

import org.apache.wicket.Application;
import org.apache.wicket.IApplicationListener;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.apache.wicket.spring.SpringWebApplicationFactory;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

public class BootWebApplicationFactory extends SpringWebApplicationFactory {

    private IApplicationListener iApplicationListener = new IApplicationListener() {
        @Override
        public void onAfterInitialized(Application application) {
            application.getComponentInstantiationListeners().add(
                    new SpringComponentInjector((WebApplication) application));
        }

        @Override
        public void onBeforeDestroyed(Application application) {
            // noop
        }
    };

    @Override
    public WebApplication createApplication(WicketFilter filter) {
        WebApplication webApplication = super.createApplication(filter);
        webApplication.getApplicationListeners().add(this.iApplicationListener);
        return webApplication;
    }
}
