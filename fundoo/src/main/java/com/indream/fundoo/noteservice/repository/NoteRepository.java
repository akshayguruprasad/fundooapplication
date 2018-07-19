package com.indream.fundoo.noteservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.indream.fundoo.noteservice.model.NoteEntity;

@Repository("noteRepository")
public interface NoteRepository extends MongoRepository<NoteEntity, String> {

	List<NoteEntity> getByUserId(String userId);
	List<NoteEntity> getById(String id);
}
