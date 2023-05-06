package example;

import com.github.wieceslaw.summer.context.Context;
import example.beans.Sender;
import example.config.MainConfig;

import java.util.Map;

public class Application {
    public static void main(String[] args) {
        Context context = new Context(MainConfig.class);
        context.setUp();
        Map<String, Object> container = context.getContainer();
        Sender sender = (Sender) container.get("sender");
        sender.sendMessage();
    }
}
