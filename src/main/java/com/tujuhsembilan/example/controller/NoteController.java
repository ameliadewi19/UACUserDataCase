package com.tujuhsembilan.example.controller;

import java.util.stream.Collectors;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tujuhsembilan.example.controller.dto.NoteDto;
import com.tujuhsembilan.example.model.Note;
import com.tujuhsembilan.example.repository.NoteRepo;

import org.springframework.security.core.Authentication;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/note")
@RequiredArgsConstructor
public class NoteController {

  private final NoteRepo repo;

  private final ModelMapper mdlMap;

  @GetMapping
  public ResponseEntity<?> getNotes(Authentication authentication) {
    String userId = authentication.getName();

    Set<NoteDto> userNotes = repo.findByUserId(userId)
            .stream()
            .map(note -> {
                NoteDto noteDto = mdlMap.map(note, NoteDto.class);
                noteDto.setUserId(userId); // Atur userId di NoteDto
                return noteDto;
            })
            .collect(Collectors.toSet());

    return ResponseEntity.ok(userNotes);
  }

  @PostMapping
  public ResponseEntity<?> saveNote(@RequestBody NoteDto body, Authentication authentication) {
    var newNote = mdlMap.map(body, Note.class);

    // Mendapatkan username pengguna yang sedang login
    String userId = authentication.getName();

    // Menetapkan userId pada NoteDto
    body.setUserId(userId);

    // Menetapkan userId pada objek newNote juga
    newNote.setUserId(userId);

    newNote = repo.save(newNote);
    return ResponseEntity.status(HttpStatus.CREATED).body(newNote);
  }

}
