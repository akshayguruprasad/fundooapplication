package com.indream.fundoo.noteservice.service;

import java.util.List;

import com.indream.fundoo.noteservice.model.Note;
import com.indream.fundoo.noteservice.model.NoteEntityDTO;

public interface NoteService {

	void createNote(NoteEntityDTO note, String token);

	void updateNote(String noteId, String content, String token);

	void deleteNote(String noteId, String token);

	List<Note> selectNote(String token);

}
