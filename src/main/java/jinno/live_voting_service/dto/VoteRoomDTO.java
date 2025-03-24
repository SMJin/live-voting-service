package jinno.live_voting_service.dto;

import jinno.live_voting_service.model.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteRoomDTO {
//    private String roomId;
    private String roomName;
    private VoteType type;
    private Set<String> participants = new HashSet<>();

    private String createUser;
}
