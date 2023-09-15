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

# Penjelasan Kasus - Case Study :: User Access Control: User Data

# Exsplorasi

## Tools Yang Digunakan
1. VSCode
2. Java 17.0.8
3. Maven 3.9.4

## Running Program

Untuk menjalankan program kami pertama menggunakan IDE Eclipse namun menghadapi masalah anotasi menggunakan library lombok. Sehingga kami menggunakan VSCode untuk menjalankan projeknya.

1. Build projek maven
```mvn clean install
```
2. Running program maven
```mvn spring-boot:run
```

## Eksplor di ChatGPT

1. Mencari keyword yang terkait dengan studi kasus dibawah.
   a. JWT: 
   b. JWK: 
   c. Issuer: 
   d. Audience: 
   e. Subject: 
   f. Basic Auth:
   g. DTO: 
   h. JPA: 
   i. H2: 
2. Mencari cara memisahkan akses terhadap data masing-masing user

# Exsplorasi

# Solusi

1. Menambah atribut userId pada model Note dan DTO Note
Note
```public class Note {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String content;

  private String userId;

}
```

NoteDto
```public class NoteDto {

  private UUID id;

  private String content;

  private String userId;
}
```

2. 

# Solusi
