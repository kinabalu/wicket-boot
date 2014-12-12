package demo;

import demo.pages.HelloWorld;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.stereotype.Component;

@Component
  class SimpleWebApplication extends WebApplication {

    @Override
    public Class<? extends Page> getHomePage() {
        return HelloWorld.class;
    }
}
