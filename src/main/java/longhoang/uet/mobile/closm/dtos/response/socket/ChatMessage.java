package longhoang.uet.mobile.closm.dtos.response.socket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private String role;
    private String sender;
    private String content;
    private String type; // "CHAT", "JOIN", "LEAVE"
}
