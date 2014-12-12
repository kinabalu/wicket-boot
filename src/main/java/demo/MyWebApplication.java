package demo;

import org.apache.wicket.Page;
import org.apache.wicket.spring.boot.SpringWebApplication;
import org.springframework.stereotype.Component;

@Component
public class MyWebApplication extends SpringWebApplication {

    @Override
    public Class<? extends Page> getHomePage() {
        return HelloWorld.class;
    }
}
