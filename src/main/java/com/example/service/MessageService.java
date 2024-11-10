package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.MessageRepository;
import java.util.List;
import java.util.Optional;

import com.example.entity.Message;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository)
    {
        this.messageRepository = messageRepository;
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message findMessage(int messageId)
    {

        return messageRepository.findById(messageId).orElse(null);
    }

    public boolean deleteById(int messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return !messageRepository.existsById(messageId);
        }
        return false;
    }

    public Message userExists(int postedBy)
    {
        return messageRepository.findMessageByPostedBy(postedBy);
    }

    public Message saveMessage (Message message)
    {
        return messageRepository.save(message);
    }
 
     public List<Message> getMessagesFromUser(int postedBy)
    {
        return messageRepository.findAllByPostedBy(postedBy);

    } 
        
    
    
    
    
    
}
