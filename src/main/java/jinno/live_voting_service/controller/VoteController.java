package jinno.live_voting_service.controller;

import com.google.gson.Gson;
import jinno.live_voting_service.dto.VoteMessage;
import jinno.live_voting_service.model.VoteType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Controller
public class VoteController {

    private final Map<String, Integer> voteMap = new ConcurrentHashMap<>();
    private final Set<String> votedUsers = ConcurrentHashMap.newKeySet();
    private final Gson gson = new Gson();

    @MessageMapping("/vote")
    @SendTo("/topic/result")
    public VoteMessage handleVote(VoteMessage message) {

        log.info("받은 메시지: {}", message);

        switch (message.getType()) {
            case RESET -> {
                voteMap.clear();
                votedUsers.clear();
                return new VoteMessage(VoteType.RESULT, null, gson.toJson(voteMap));
            }
            case VOTE -> {
                if (votedUsers.contains(message.getUser())) {
                    return new VoteMessage(VoteType.ERROR, message.getUser(), "이미 투표하셨습니다!");
                }
                voteMap.put(message.getOption(), voteMap.getOrDefault(message.getOption(), 0) + 1);
                votedUsers.add(message.getUser());
                return new VoteMessage(VoteType.RESULT, null, gson.toJson(voteMap));
            }
            default -> {
                return new VoteMessage(VoteType.ERROR, null, "알 수 없는 메시지 타입입니다.");
            }
        }
    }
}
