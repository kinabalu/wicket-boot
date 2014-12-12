package demo;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by kinabalu on 12/11/14.
 */
public class HelloWorld extends WebPage {

    @SpringBean
    CatBar catBar;

    public HelloWorld() {
        add(new Label("message", catBar.whatever));
    }
}