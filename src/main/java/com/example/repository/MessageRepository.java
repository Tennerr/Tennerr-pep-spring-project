package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{

    Message findMessageByPostedBy(int postedBy);
 
    @Query("SELECT m FROM Message m WHERE m.postedBy = :postedBy")
    List<Message> findAllByPostedBy(@Param("postedBy") int postedBy);
    
}
