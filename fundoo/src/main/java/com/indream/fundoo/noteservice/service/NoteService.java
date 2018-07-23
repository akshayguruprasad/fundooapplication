package com.indream.fundoo.noteservice.service;

import java.util.List;

import com.indream.fundoo.noteservice.model.NoteEntity;
import com.indream.fundoo.noteservice.model.NoteDto;

public interface NoteService {

	void createNote(NoteDto note, String token);

	void updateNote(NoteDto noteDto, String token);

	void deleteNote(String noteId, String token);

	void archiveNote(String noteId,String token);
	void deleteNoteToTrash(String noteId,String token);
	void pinNote(String noteId,String token);
	void restoreNote(String noteId,String token);
	
	List<NoteEntity> selectNote(String token);

}
