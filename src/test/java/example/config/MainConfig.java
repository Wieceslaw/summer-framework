package example.config;

import com.github.wieceslaw.summer.annotations.*;
import com.github.wieceslaw.summer.context.BeanScope;
import example.beans.Sender;

import java.util.Random;

@Config
@Extend({NetworkConfig.class, PersistenceConfig.class})
public class MainConfig {
    @Bean("sender")
    public Sender sender(@Qualifier("senderIpAddress") String ipAddress,
                         @Qualifier("randomInteger") Integer n) {
        return new Sender(ipAddress, n);
    }

    @Bean("senderIpAddress")
    public String senderIpAddress() {
        return "127.0.0.1";
    }

    @Scope(BeanScope.PROTOTYPE)
    @Bean("randomInteger")
    public Integer randomInteger(@Qualifier("random") Random random) {
        return random.nextInt(5) + 1;
    }

    @Bean("random")
    public Random random() {
        return new Random();
    }
}
