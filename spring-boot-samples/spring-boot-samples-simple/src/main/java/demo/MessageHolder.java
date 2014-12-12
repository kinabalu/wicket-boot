package demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MessageHolder {

    @Value("${message}")
    private String message;

    public String getMessage() {
        return message;
    }
}
