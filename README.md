# Penjelasan Kasus - Case Study :: User Access Control: User Data

![Technology - Spring Boot](https://img.shields.io/badge/Technology-Spring_Boot-blue)
![Tracing Difficulty - Easy](https://img.shields.io/badge/Tracing_Difficulty-Easy-green)
![Implementation Difficulty - Medium](https://img.shields.io/badge/Implementation_Difficulty-Medium-yellow)

## The Condition

You are developing an application, in which you must implement a custom JWT-based login.

There are 2 dummy users, called:

```text
username: USER_A
password: USER_A
```

```text
username: USER_B
password: USER_B
```

The Application is about creating a simple note.

The requirement told you that each user must only be able to see their own data, without any ability to see other's data.

## The Problem

You have logged in, either with user A or user B, but both user could see each other data.

## The Objective

Implement a constraint that limits access for both users.

## The Expected Result

Each user can only see data owned by themselves.

## Kondisi Awal
User A dapat melihat semua data termasuk data User B<br/>
<img width="635" alt="image" src="https://github.com/ameliadewi19/UACUserDataCase/assets/95133748/9635696d-d292-4989-9164-53c2e2954318"><br/>
User B dapat melihat semua data termasuk data User A<br/>
<img width="636" alt="image" src="https://github.com/ameliadewi19/UACUserDataCase/assets/95133748/cb5ec277-2ecb-4f5a-baf6-89282ddcbfb2">


# Exsplorasi

## Tools Yang Digunakan
1. VSCode
2. Java 17.0.8
3. Maven 3.9.4

## Running Program

Untuk menjalankan program kami pertama menggunakan IDE Eclipse namun menghadapi masalah anotasi menggunakan library lombok. Sehingga kami menggunakan VSCode untuk menjalankan projeknya.

### 1. Build projek maven
```
mvn clean install
```
### 2. Running program maven
```
mvn spring-boot:run
```

## Eksplor di ChatGPT

### 1. Mencari keyword yang terkait dengan studi kasus dibawah. <br/><br/>

   a. JWT: JWT singkatan dari "JSON Web Token." Merupakan format yang digunakan untuk mengirimkan informasi yang dapat diverifikasi secara aman antara dua pihak. JWT juga  sering digunakan untuk mengamankan komunikasi antara pengguna dan server dalam aplikasi web dan layanan web.<br/><br/>
   b. JWK: JWK adalah singkatan dari "JSON Web Key." Ini adalah standar JSON yang digunakan untuk merepresentasikan kunci kriptografi yang digunakan dalam konteks keamanan web, terutama dalam penggunaan seperti JSON Web Tokens (JWT) dan OAuth 2.0.<br/><br/>
   c. Issuer: Issuer adalah entitas yang menghasilkan atau menerbitkan token. Ini biasanya adalah server atau aplikasi yang mengeluarkan token. Issuer digunakan untuk mengidentifikasi sumber atau asal token. Misalnya, jika memiliki aplikasi web yang mengeluarkan token untuk autentikasi, issuer akan mengidentifikasi aplikasi web tersebut sebagai penerbit token. <br/><br/>
   d. Audience: Audience adalah penerima atau pemilik yang dimaksudkan untuk menerima token. Ini adalah entitas yang dimaksudkan untuk menggunakan token tersebut. Audience digunakan untuk memastikan bahwa token hanya digunakan oleh entitas yang sah. Misalnya, jika mengeluarkan token untuk aplikasi khusus, audience akan menunjukkan bahwa token tersebut hanya ditujukan untuk aplikasi tersebut.<br/><br/>
   e. Subject: Subject adalah subjek atau entitas yang token tersebut mengidentifikasi atau mewakili. Ini adalah informasi tentang pengguna atau objek yang terkait dengan token. Misalnya, jika token digunakan untuk mengautentikasi pengguna, subjeknya bisa berisi nama pengguna atau ID pengguna yang sesuai.<br/><br/>
   f. Basic Auth: Basic Authentication (Otentikasi Dasar) adalah metode otentikasi sederhana yang digunakan dalam protokol HTTP untuk mengamankan akses ke sumber daya web atau API dengan mengirimkan kredensial pengguna dalam bentuk username dan password.<br/><br/>
   g. DTO: DTO adalah singkatan dari "Data Transfer Object." Ini adalah pola desain yang digunakan dalam pengembangan perangkat lunak untuk mengirimkan data antara komponen atau lapisan aplikasi yang berbeda. <br/><br/>
   h. JPA: JPA adalah singkatan dari "Java Persistence API." Ini adalah spesifikasi Java yang mendefinisikan antarmuka standar untuk mengelola data dalam basis data relasional menggunakan bahasa pemrograman Java.<br/><br/>
   i. OAuth2: OAuth (Open Authorization) adalah protokol otentikasi terbuka yang digunakan untuk memberikan izin kepada aplikasi pihak ketiga untuk mengakses sumber daya yang terlindungi atas nama pengguna tanpa harus mengungkapkan kata sandi pengguna yang sebenarnya.<br/><br/>
   j. @PrePersist: digunakan untuk menandai metode yang harus dijalankan sebelum entitas disimpan pertama kali ke dalam basis data (pada saat pembuatan baru). Metode yang dianotasi dengan @PrePersist akan dijalankan sebelum entitas dipersist (disimpan) ke basis data.<br/><br/>
   k. @PreUpdate: digunakan untuk menandai metode yang harus dijalankan sebelum entitas diperbarui dalam basis data (pada saat proses pembaruan). Metode yang dianotasi dengan @PreUpdate akan dijalankan sebelum entitas diperbarui.<br/><br/>
   
### 2. Mencari cara memisahkan akses terhadap data masing-masing user<br/><br/>

# Solusi

### 1. Menambah DTO untuk post dengan nama RequestNoteDto
RequestNoteDto digunakan agar untuk post method, karena field username akan diisi otomatis di model
```
package com.tujuhsembilan.example.controller.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestNoteDto {

  private UUID id;

  private String content;
}
```

### 2. Mengubah NoteDto
Menambahkan field username
```
package com.tujuhsembilan.example.controller.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteDto {

  private UUID id;

  private String content;

  private String username;
}
```

### 3. Mengubah Model Note
Menambah anotasi untuk set username pada proses post 
```
package com.tujuhsembilan.example.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
  
  @PrePersist
  @PreUpdate
  private void populateUsername() {
      String username = SecurityContextHolder.getContext().getAuthentication().getName();
      this.username = username;
  }
  
}
```

### 4. Mengubah fungsi get dan post di NoteController;
Get
```
@GetMapping
  public ResponseEntity<?> getNotes(Authentication authentication) {
    String username = authentication.getName();

    List<Note> noteList = repo.findAll();

    Set<NoteDto> userNotes = noteList.stream()
        .filter(note -> note.getUsername().equals(username)) // filter by username
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
```

Post
```
@PostMapping
  public ResponseEntity<?> saveNote(@RequestBody RequestNoteDto body, Authentication authentication) {
    var newNote = mdlMap.map(body, Note.class);
    newNote = repo.save(newNote);

    // System.out.println(newNote);
    return ResponseEntity.status(HttpStatus.CREATED).body(newNote);
  }
```

# Hasil 
User A hanya bisa melihat data user A menggunakan token JWT<br/>
<img width="636" alt="image" src="https://github.com/ameliadewi19/UACUserDataCase/assets/95133748/02a7a6de-4985-41ec-ac3c-54367feee6d5"><br/>

User B hanya bisa melihat data user B menggunakan token JWT<br/>
<img width="638" alt="image" src="https://github.com/ameliadewi19/UACUserDataCase/assets/95133748/335ba951-3496-40b1-acbb-bb208083b094">
