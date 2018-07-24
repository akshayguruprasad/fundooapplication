
package com.indream.fundoo.noteservice.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.indream.fundoo.exceptionhandler.NoteException;
import com.indream.fundoo.exceptionhandler.UserException;
import com.indream.fundoo.noteservice.model.NoteDto;
import com.indream.fundoo.noteservice.model.NoteEntity;
import com.indream.fundoo.noteservice.model.Token;
import com.indream.fundoo.noteservice.repository.NoteRepository;
import com.indream.fundoo.userservice.model.UserEntity;
import com.indream.fundoo.userservice.repository.UserRepository;
import com.indream.fundoo.util.Utility;

/**
 * NOTESERVICE IMPL SERVICE LAYER
 * 
 * @author Akshay
 *
 */
public class NoteServiceImpl implements NoteService {
    @Autowired
    NoteRepository noteRepository;
    @Autowired
    @Qualifier(value = "repository")
    private UserRepository userRepository;

    /*
     * @purpose CREATE NOTE METHOD
     *
     * @author akshay
     * 
     * @com.indream.fundoo.noteservice.service
     * 
     * @since Jul 24, 2018
     *
     */
    @Override
    public void createNote(NoteDto noteEntityDTO, Token token) {
	try {

	    NoteEntity noteEntity = Utility.convert(noteEntityDTO, NoteEntity.class);// MODEL MAPPING FOR DTO TO ENTITY
	    noteEntity.setCreadtedOn(new Date());// CREATED DATE
	    noteEntity.setLastModified(new Date());// LASTE MODIFIED DATE
	    noteEntity.setId(null);// SET AS NULL TO AVODI PREDEFINED INITALIZATION
	    String userId = this.getUserId(token);// GET USER ID
	    validateUser(userId);// VALIDATE THE USER
	    noteEntity.setUserId(userId);// IF TRUE THEN SET USERID
	    noteRepository.save(noteEntity);// SAVE THE NOTE
	} catch (UserException e) {
	    throw e;
	} catch (RuntimeException e) {
	    e.printStackTrace();
	    throw e;
	}

    }

    /*
     * @purpose UPDATE NOTE METHOD
     *
     * @author akshay
     * 
     * @com.indream.fundoo.noteservice.service
     * 
     * @since Jul 24, 2018
     *
     */
    @Override
    public void updateNote(NoteDto noteDto, Token token) {
	try {
	    String userId = getUserId(token);// GET USER ID FROM THE TOKEN
	    validateUser(userId);// VALIDATE THE USER (CHECK FOR THE EXISTANCE OF THE USER)
	    validNote(noteDto.getId().toString(), userId);// CHECK FOR THE NOTE ID BELONGING TO THE USER ID
	    noteDto.setLastModified(new Date());// LAST MODIFIED SET VALUE
	    NoteEntity note = Utility.convert(noteDto, NoteEntity.class);// MODEL MAPPER TO CONVERT
	    noteRepository.save(note);// UPDATE THE EXISTING NOTE
	} catch (RuntimeException e) {
	    throw e;
	}
    }

    /*
     * @purpose DELETE NOTE METHOD
     *
     * @author akshay
     * 
     * @com.indream.fundoo.noteservice.service
     * 
     * @since Jul 24, 2018
     *
     */
    @Override
    public void deleteNote(String noteId, Token token) {
	try {
	    String userId = getUserId(token);// GET THE USER ID
	    validateUser(userId);// CHECK FOR THE VALID USER
	    List<NoteEntity> noteEntities = noteRepository.getByUserId(userId);// GET LIST OF NOTES BY USER ID
	    NoteEntity noteEntity = Utility.getNoteEntity(noteEntities, noteId);// CHECKFOR SEPCIFIC NOTE
	    if (!noteEntity.isTrashed()) {
		throw new NoteException("Note not trashed cannote delete it");
	    }
	    noteRepository.delete(noteEntity);// DELETE THE NOTE IF FOUND
	} catch (RuntimeException e) {
	    e.printStackTrace();
	    throw e;
	}

    }

    /*
     * @purpose LIST NOTE METHOD
     *
     * @author akshay
     * 
     * @com.indream.fundoo.noteservice.service
     * 
     * @since Jul 24, 2018
     *
     */
    @Override
    public List<NoteEntity> selectNote(Token token) {
	try {
	    String userId = getUserId(token);// GET USER ID FROM THE TOKEN
	    validateUser(userId);// VALIDATE THE USER ID CHECK FOR EXISTANCE
	    return noteRepository.getByUserId(userId);// GET NOTES FOR THAT USER
	} catch (RuntimeException e) {
	    throw e;
	}
    }

    /*
     * @purpose ARCHIVE NOTE METHOD
     *
     * @author akshay
     * 
     * @com.indream.fundoo.noteservice.service
     * 
     * @since Jul 24, 2018
     *
     */
    @Override
    public void archiveNote(String noteId, Token token) {
	NoteEntity noteEntity = getValidNoteEntity(noteId, token);// CHECK FOR THE VALID NOTE
	noteEntity.setArchived(true);// MAKE NOTE ARCHIVE
	noteEntity.setPinned(false);
	noteEntity.setTrashed(false);
	noteRepository.save(noteEntity);// UPDATE THE NOTE
    }

    /*
     * @purpose DELETE TO TRASH NOTE METHOD
     *
     * @author akshay
     * 
     * @com.indream.fundoo.noteservice.service
     * 
     * @since Jul 24, 2018
     *
     */
    @Override
    public void deleteNoteToTrash(String noteId, Token token) {
	NoteEntity noteEntity = getValidNoteEntity(noteId, token);// CHECK FOR THE VALID NOTE
	noteEntity.setArchived(false);
	noteEntity.setPinned(false);
	noteEntity.setTrashed(true);// SET TRASH AS TRUE TO MAKE IT TRASH
	noteRepository.save(noteEntity);// UPDATE THE NOTE VALUES

    }

    /*
     * @purpose CREATE NOTE METHOD
     *
     * @author akshay
     * 
     * @com.indream.fundoo.noteservice.service
     * 
     * @since Jul 24, 2018
     *
     */
    @Override
    public void pinNote(String noteId, Token token) {
	NoteEntity noteEntity = getValidNoteEntity(noteId, token);// CHECK FOR VALID NOTE
	noteEntity.setArchived(false);
	noteEntity.setPinned(true);// MAKING IT PINNED
	noteEntity.setTrashed(false);
	noteRepository.save(noteEntity);// UPDATE THE NOTE

    }

    /*
     * @purpose RESTORE NOTE METHOD
     *
     * @author akshay
     * 
     * @com.indream.fundoo.noteservice.service
     * 
     * @since Jul 24, 2018
     *
     */
    @Override
    public void restoreNote(String noteId, Token token) {
	NoteEntity noteEntity = getValidNoteEntity(noteId, token);// CHECK FOR THE VALID NOTE

	if (!noteEntity.isTrashed()) {
	    throw new NoteException("Note not trashed cannot be restored invalid operation ");
	}
	noteEntity.setTrashed(false);// RESTORE FROM TRASH
	noteRepository.save(noteEntity);// UPDATE THE VALUE

    }

    /*
     * @purpose GET NOTE METHOD
     *
     * @author akshay
     * 
     * @com.indream.fundoo.noteservice.service
     * 
     * @since Jul 24, 2018
     *
     */
    public NoteEntity getNote(String noteId) {

	return noteRepository.findOne(noteId);// GET NOTE BY NOTEID

    }

    /*
     * @purpose VALIDATE NOTE METHOD
     *
     * @author akshay
     * 
     * @com.indream.fundoo.noteservice.service
     * 
     * @since Jul 24, 2018
     *
     */
    private void validNote(String noteId, String userId) {
	List<NoteEntity> noteEntities = noteRepository.getByUserId(userId);// GET NOTE BY USER ID

	long count = noteEntities.stream().filter(p -> p.getId().toString().equals(noteId.toString())).count();
//CHECK FOR OONLY ONE EXISTANCE FOR THAT PARTICULAR NOTE ID
	if (count != 1) {
	    throw new NoteException("Note not found to be update status");
	}
    }

    /*
     * @purpose GET VALID NOTE ENTITY NOTE METHOD
     *
     * @author akshay
     * 
     * @com.indream.fundoo.noteservice.service
     * 
     * @since Jul 24, 2018
     *
     */
    private NoteEntity getValidNoteEntity(String noteId, Token token) {
	try {
	    String userId = getUserId(token);// GET THE USER ID
	    this.validateUser(userId);// VALIDATE THE USER
	    this.validNote(noteId, userId);// VALIDATE NOTE
	    NoteEntity noteEntity = this.getNote(noteId);// GET BOTE BY NOTE ID
	    return noteEntity;// RETURN
	} catch (RuntimeException e) {
	    throw e;
	}
    }

    /*
     * @purpose VALIDATE USER METHOD
     *
     * @author akshay
     * 
     * @com.indream.fundoo.noteservice.service
     * 
     * @since Jul 24, 2018
     *
     */
    private void validateUser(String userId) throws UserException {
	UserEntity userEntity = userRepository.findOne(userId);// GET USER BY USERID
	if (userEntity == null) {
	    throw new UserException("User not found");
	}
	if (!userEntity.isActive()) {
	    throw new UserException("User is inactive");
	}
    }

    /*
     * @purpose GET USER ID METHOD
     *
     * @author akshay
     * 
     * @com.indream.fundoo.noteservice.service
     * 
     * @since Jul 24, 2018
     *
     */
    private String getUserId(Token token) {
	return token.getId();// GET THE USER ID FROM THE TOKEN
    }

}
