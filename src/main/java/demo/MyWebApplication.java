package demo;

import org.apache.wicket.Page;
import org.apache.wicket.application.IComponentInstantiationListener;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

public class MyWebApplication extends WebApplication {

    @SuppressWarnings("unchecked")
    @Override
    protected void init() {
        super.init();

        getComponentInstantiationListeners().add(getSpringComponentInjector(this));

    }

    protected IComponentInstantiationListener getSpringComponentInjector(WebApplication application) {
        return new SpringComponentInjector(application);
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return HelloWorld.class;
    }
}
