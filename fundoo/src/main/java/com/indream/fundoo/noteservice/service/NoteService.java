package com.indream.fundoo.noteservice.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.indream.fundoo.noteservice.model.NoteDto;
import com.indream.fundoo.noteservice.model.NoteEntity;

/**
 * NOTESERVICE INTERFACE
 * 
 * @author Akshay
 *
 */
public interface NoteService {
	String createNote(NoteDto note, String token, HttpServletRequest request);

	void updateNote(NoteDto noteDto, String token);

	void deleteNote(String noteId, String token);

	List<NoteEntity> selectNote(String token);

	void archiveNote(String noteId, String token);

	void pinNote(String noteId, String token);

	void deleteNoteToTrash(String noteId, String token);

	void restoreNote(String noteId, String token);

	void reminderNote(NoteDto noteDto, String token);

	String createLabel(String label, String token);

	void deleteLabel(String labelId, String token);

	void editLabelName(String token, String label, String labelId);

	void setLabelNote(String noteId, String token, String label);

	List<NoteEntity> selectPinNote(String token);

	List<NoteEntity> selectArchiveNote(String token);

	List<NoteEntity> selectTrashNote(String token);

	void unArchiveNote(String noteId, String token);

	void unPinNote(String noteId, String token);

	void urlNote(String url, String noteId, String token);

}
