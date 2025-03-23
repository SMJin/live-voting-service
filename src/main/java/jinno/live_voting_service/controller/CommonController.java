package jinno.live_voting_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {

    @GetMapping("/vote")
    public String votePage() {
        return "vote";
    }
}
