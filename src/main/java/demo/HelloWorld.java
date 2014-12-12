package demo;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class HelloWorld extends WebPage {

    @SpringBean
    Foo catBar;

    public HelloWorld() {
        add(new Label("message", catBar.message));
    }
}