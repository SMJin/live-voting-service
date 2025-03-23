package jinno.live_voting_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // 클라이언트가 WebSocket 연결 시 사용할 엔드포인트
                .setAllowedOriginPatterns("*") // CORS 허용
                .withSockJS(); // SockJS fallback 지원
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 구독 경로: 클라이언트가 메시지를 수신받을 경로 prefix
        registry.enableSimpleBroker("/topic"); // → 클라이언트는 /topic/result 를 구독하게 됨

        // 발행 경로: 클라이언트가 메시지를 보낼 경로 prefix
        registry.setApplicationDestinationPrefixes("/app"); // → 클라이언트는 /app/vote 로 전송함
    }
}
