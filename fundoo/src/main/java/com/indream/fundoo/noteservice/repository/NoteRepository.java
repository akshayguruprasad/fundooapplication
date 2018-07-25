package com.indream.fundoo.noteservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.indream.fundoo.noteservice.model.NoteEntity;

/**
 * CUSTOM NOTE REPOSITORY
 * 
 * @author Akshay
 *
 */
@Repository("noteRepository")
public interface NoteRepository extends MongoRepository<NoteEntity, String> {
    List<NoteEntity> getByUserId(String userId);// GET VALUE BY USER ID
    List<NoteEntity> findBy_id(String idValue);

}
