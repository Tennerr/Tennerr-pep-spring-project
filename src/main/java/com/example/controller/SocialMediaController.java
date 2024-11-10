package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import java.util.List;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 @RestController
public class SocialMediaController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account)
    {
        if(accountService.accountExists(account.getUsername()) != null)
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if(!account.getUsername().isEmpty() && account.getPassword().length() > 3)
        {
            Account savedAccount = accountService.saveAccount(account);
            return ResponseEntity.ok(savedAccount);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account)
    {
        Account authenticatedAccount = accountService.succesfulLogin(account.getUsername(), account.getPassword());
        if(authenticatedAccount != null)
        {
           return ResponseEntity.ok(authenticatedAccount);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAllAcoounts() {
        List<Account> accounts = accountService.getAllAccounts();
        if (accounts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(accounts);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message){
       if(messageService.userExists(message.getPostedBy()) == null || message.getMessageText().isEmpty() || message.getMessageText().length() > 255 )
       {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
       }
        Message newMsg = messageService.saveMessage(message);
        return ResponseEntity.ok(newMsg);

    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        if (messages.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId){
        Message msg = messageService.findMessage(messageId);

        return ResponseEntity.ok(msg);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable Integer messageId)
    {
        int updated = 0;
        if(messageService.deleteById(messageId))
        {
            updated++;
        }
        if(updated > 0)
        {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable Integer messageId, @RequestBody Message message)
    {
        Message oldMessage = messageService.findMessage(messageId);
        if(oldMessage == null || message.getMessageText().isEmpty() || message.getMessageText().length() > 255)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            
        }
        oldMessage = messageService.saveMessage(message);
        return ResponseEntity.ok(1);
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByUser(@PathVariable Integer accountId)
    {
        return ResponseEntity.ok(messageService.getMessagesFromUser(accountId));
    }
        



}
