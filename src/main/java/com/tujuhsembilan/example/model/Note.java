package com.tujuhsembilan.example.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class Note {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String content;

  private String username;

  @PostLoad
  @PostPersist
  @PostUpdate
  private void populateUsername() {
    this.username = getUsernameFromJwt();
    System.out.println(this.username);
  }

  private String getUsernameFromJwt() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    return username;
  }

}
