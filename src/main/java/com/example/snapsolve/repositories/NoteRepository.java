package com.example.snapsolve.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.snapsolve.models.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long>{
    
}
