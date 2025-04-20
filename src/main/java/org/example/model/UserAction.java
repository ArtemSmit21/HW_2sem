package org.example.model;
 
 import lombok.AllArgsConstructor;
 import lombok.Getter;
 
 import java.time.Instant;
 
 @AllArgsConstructor
 @Getter
 public class UserAction {
   private long id;
   private Instant eventTime;
   private String eventType;
   private String eventDetails;
 }
