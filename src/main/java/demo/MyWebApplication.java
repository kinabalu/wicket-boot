package demo;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
 import org.springframework.stereotype.Component;

@Component
public class MyWebApplication extends WebApplication {

    @Override
    public Class<? extends Page> getHomePage() {
        return HelloWorld.class;
    }
}
