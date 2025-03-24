package jinno.live_voting_service.redis;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class RedisSubscriber {

    private final SimpMessagingTemplate messagingTemplate;

    public RedisSubscriber(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void onMessage(Message message, byte[] pattern) {
        System.out.println("✅ onMessage 등록됨");
        try {
            String channel = new String(message.getChannel(), StandardCharsets.UTF_8); // ex. room.123
            String payload = new String(message.getBody(), StandardCharsets.UTF_8);
            log.info("[RedisSubscriber] 수신 - 채널:/topic/{}, 메시지:{}", channel, payload);

            // WebSocket 으로 메시지 push
            messagingTemplate.convertAndSend("/topic/" + channel, payload);
        } catch (Exception e) {
            log.error("RedisSubscriber 메시지 처리 오류", e);
        }
    }

    @PostConstruct
    public void testOnMessageMethod() throws Exception {
        Method m = RedisSubscriber.class.getMethod("onMessage", Message.class, byte[].class);
        System.out.println("✅ onMessage method found: " + m);
    }
}
