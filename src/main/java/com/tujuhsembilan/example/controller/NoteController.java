package com.tujuhsembilan.example.controller;

import java.util.stream.Collectors;
import java.util.List;
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
import com.tujuhsembilan.example.controller.dto.RequestNoteDto;
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
    String username = authentication.getName();

    List<Note> noteList = repo.findAll();

    Set<NoteDto> userNotes = noteList.stream()
        .filter(note -> note.getUsername().equals(username))
        .map(note -> {
            NoteDto noteDto = mdlMap.map(note, NoteDto.class);
            noteDto.setUsername(username); 
            return noteDto;
        })
        .collect(Collectors.toSet());

    if (!userNotes.isEmpty()) {
        return ResponseEntity.ok(userNotes);
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tidak ada catatan ditemukan.");
    }
  }

  @PostMapping
  public ResponseEntity<?> saveNote(@RequestBody RequestNoteDto body, Authentication authentication) {
    var newNote = mdlMap.map(body, Note.class);
    newNote = repo.save(newNote);

    // System.out.println(newNote);
    return ResponseEntity.status(HttpStatus.CREATED).body(newNote);
  }

}
