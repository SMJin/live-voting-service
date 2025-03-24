package jinno.live_voting_service.controller;

import jinno.live_voting_service.dto.VoteRoomDTO;
import jinno.live_voting_service.model.VoteType;
import jinno.live_voting_service.redis.RedisPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MessageController {

    private final RedisPublisher redisPublisher;

    @PostMapping("/vote")
    public ResponseEntity<String> handleVote(@RequestBody VoteRoomDTO room) {
        log.info("roomName:{}, userName:{}, message:{}",
                room.getRoomName(), room.getCreateUser(), room);
//        VoteRoomDTO room = createVoteRoom(roomName, userName);
//        redisPublisher.publish("voteroom", message);
        return ResponseEntity.ok("메시지 발행 완료!");
    }

    private VoteRoomDTO createVoteRoom(String roomName, String userName) {
        String roomId = UUID.randomUUID().toString();
        Set<String> participants = new HashSet<>();
        participants.add(userName);
//        return new VoteRoomDTO(roomId, roomName, VoteType.VOTE, participants);
        return new VoteRoomDTO(roomName, VoteType.VOTE, participants, userName);
    }
}
