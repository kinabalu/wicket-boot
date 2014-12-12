package demo;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class MyWebApplication extends WebApplication {

    @SuppressWarnings("unchecked")
    @Override
    protected void init() {
        super.init();
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return HelloWorld.class;
    }
}
