package com.indream.fundoo.noteservice.service;

import java.util.List;

import com.indream.fundoo.noteservice.model.NoteEntity;
import com.indream.fundoo.noteservice.model.Token;
import com.indream.fundoo.noteservice.model.NoteDto;

/**
 * NOTESERVICE INTERFACE
 * 
 * @author Akshay
 *
 */
public interface NoteService {

    void createNote(NoteDto note, Token token);

    void updateNote(NoteDto noteDto, Token token);

    void deleteNote(String noteId, Token token);

    void archiveNote(String noteId, Token token);

    void deleteNoteToTrash(String noteId, Token token);

    void pinNote(String noteId, Token token);

    void restoreNote(String noteId, Token token);

    List<NoteEntity> selectNote(Token token);

}
