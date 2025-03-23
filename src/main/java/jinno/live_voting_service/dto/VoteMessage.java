package jinno.live_voting_service.dto;

import jinno.live_voting_service.model.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteMessage {
    private VoteType type;
    private String user;
    private String option;
}
