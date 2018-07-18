package com.indream.fundoo.noteservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.indream.fundoo.noteservice.model.Note;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {

	List<Note> getByUserId(String userEmailId);
}
