package jinno.live_voting_service.config;

import jinno.live_voting_service.redis.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    private final RedisSubscriber redisSubscriber;

    /**
     * LettuceConnectionFactory 는 비동기(Asynchronous) 논블로킹 방식이라 자주 사용.
     * 비동기(작업 요청만 보내고, 응답은 나중에 처리)
     * 논블로킹(자원을 기다리지 않고, 바로 다른 작업 처리)
     * NIO(Java 의 Non-blocking I/O API. 스레드 수 대비 수많은 클라이언트 처리 가능)
     * Spring Data Redis 가 내부적으로 이걸 사용해 Redis 와 연결
     * @return Redis 연결 팩토리(Lettuce 구현체) - Lettuce 는 Netty 기반의, 요청을 날리고 스레드는 멈추지않고 돌아다니며 다른 일 처리하는 `고성능` 구현체
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    /**
     * ` Java 코드에서 Redis 명령어를 사용하게 도와주는 도구 - 자료구조가 내장된 NoSQL in-memory DB`
     * Java 객체 <-> Redis 간 데이터 직렬화/역직렬화에 사용
     * 기본적인 Redis 작업(Set, Get 등)을 처리할 때 사용
     * @return RedisTemplate (일반 Redis 연산용 + Pub/Sub 전송용)
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(String.class));
        return redisTemplate;
    }

//    /**
//     * Redis의 Pub/Sub에서 고정적으로 사용할 채널 명(voteroom)
//     * Subscriber가 이 채널을 구독하고, Publisher는 이 채널로 메시지를 보냄.
//     * @return Redis Pub/Sub 토픽 (고정 채널)
//     */
//    @Bean
//    public ChannelTopic channelTopic() {
//        return new ChannelTopic("voteroom");
//    }

    /**
     * Redis에서 메시지를 수신했을 때, 지정한 객체(redisSubscriber)의 메서드(onMessage)를 호출하도록 해주는 어댑터
     * 내부적으로 Redis 메시지를 Message 객체로 변환해서 넘겨줌
     * @return 메시지 수신 핸들러 어댑터
     */
    @Bean
    public MessageListenerAdapter listenerAdapter() {   // 통역사
        System.out.println("✅ RedisSubscriber: " + redisSubscriber.getClass());
        // 통역사(Adaptor) 는 통역을 수행한 이후에, 확성기(subscriber)를 통해 전달(발행)한다.
        return new MessageListenerAdapter(redisSubscriber, "onMessage");
    }

    /**
     * `Redis 로부터 메시지를 계속 듣고 있다가, 오면 Subscriber에 연결해주는 예`
     * Redis 메시지 리스너를 실행시켜주는 컨테이너
     * 실제로 Redis 채널 구독을 시작하고, 메시지를 수신하면 listenerAdapter를 통해 redisSubscriber가 동작
     * @param connectionFactory
     * @param listenerAdapter
//     * @param channelTopic
     * @return Redis 메시지 리스너 등록 컨테이너 - 귀(I;m all ears) <-?
     */
    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory,
                                                        MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
//        container.addMessageListener(listenerAdapter, channelTopic);
        return container;
    }
}
