package jinno.live_voting_service.controller;

import jinno.live_voting_service.dto.VoteMessage;
import jinno.live_voting_service.redis.RedisPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final RedisPublisher redisPublisher;

    @MessageMapping("/vote/send.{roomId}")
    public void sendVote(@DestinationVariable String roomId, @Payload VoteMessage message) {
        ChannelTopic topic = new ChannelTopic("room." + roomId);
        redisPublisher.publish(topic, message);
    }
}
