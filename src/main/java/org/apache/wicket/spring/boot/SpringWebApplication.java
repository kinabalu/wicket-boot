package org.apache.wicket.spring.boot;

import org.apache.wicket.application.IComponentInstantiationListener;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

/**
 *
 * Provides base-class for consumers that handles wiring up Spring itself
 */
public abstract class SpringWebApplication extends WebApplication {

    @Override
    protected void init() {
        super.init();

        this.getComponentInstantiationListeners().add(
            this.getSpringComponentInjector(this));
    }

    protected IComponentInstantiationListener getSpringComponentInjector(WebApplication application) {
        return new SpringComponentInjector(application);
    }

}
