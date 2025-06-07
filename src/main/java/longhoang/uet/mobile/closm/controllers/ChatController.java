    package longhoang.uet.mobile.closm.controllers;

    import longhoang.uet.mobile.closm.dtos.response.socket.ChatMessage;
    import org.springframework.messaging.handler.annotation.MessageMapping;
    import org.springframework.messaging.handler.annotation.Payload;
    import org.springframework.messaging.handler.annotation.SendTo;
    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.RestController;

    @RestController
    public class ChatController {

        @MessageMapping("/chat.send") // client gửi tới /app/chat.send
        @SendTo("/topic/app")      // broadcast tới tất cả client đang sub /topic/public
        public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
            return chatMessage;
        }
    }