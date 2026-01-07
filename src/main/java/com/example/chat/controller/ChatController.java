package com.example.chat.controller;

import com.example.chat.model.Message;
import com.example.chat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/messages")
@CrossOrigin(origins = "*")
public class ChatController {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private MessageRepository messageRepository;

    // Send private message
    @MessageMapping("/private")
    public void sendPrivateMessage(@Payload Message message){
        message.setTimestamp(new Timestamp(System.currentTimeMillis()));
        messageRepository.save(message);
        template.convertAndSend("/user/" + message.getReceiver() + "/queue/messages", message);
    }

    // Get chat history between two users
    @GetMapping("/{user1}/{user2}")
    public List<Message> getChatHistory(@PathVariable String user1,
                                        @PathVariable String user2) {
        return messageRepository.findBySenderAndReceiverOrReceiverAndSenderOrderByTimestampAsc(
                user1, user2, user1, user2
        );
    }
}




