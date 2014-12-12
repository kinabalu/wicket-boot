package demo;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class MyWebApplication extends WebApplication {

    @Override
    public Class<? extends Page> getHomePage() {
        return HelloWorld.class;
    }
}
