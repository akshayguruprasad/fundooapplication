/**
 * 
 */
package com.indream.fundoo.noteservice.dao;

import java.io.IOException;
import java.util.List;

import com.indream.fundoo.noteservice.model.LabelEntity;
import com.indream.fundoo.noteservice.model.NoteEntity;

/**
 * @author bridgeit
 *
 */
public interface NoteDao  {

	String saveElasticNote(NoteEntity noteEntity) throws IOException;

	String updateElasticNote(NoteEntity noteEntity) throws IOException;

	String deleteElasticNote(String noteId) throws IOException;

	List<?> searchElasticNote(String userId) throws IOException;

	String saveElasticLabel(LabelEntity labelEntity) throws IOException;

	String updateElasticLabel(LabelEntity labelEntity) throws IOException;

	String deleteElasticLabel(String noteId) throws IOException;

	List<?> searchElasticLabel(String userId) throws IOException;

	LabelEntity getElasticLabelEntity(String key, String value) throws IOException;

	NoteEntity getElasticNoteEntity(String key, String value) throws IOException;
}
