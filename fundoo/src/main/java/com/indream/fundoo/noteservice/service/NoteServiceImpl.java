
package com.indream.fundoo.noteservice.service;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.indream.fundoo.exceptionhandler.LabelException;
import com.indream.fundoo.exceptionhandler.NoteException;
import com.indream.fundoo.exceptionhandler.UserException;
import com.indream.fundoo.noteservice.model.LabelEntity;
import com.indream.fundoo.noteservice.model.NoteDto;
import com.indream.fundoo.noteservice.model.NoteEntity;
import com.indream.fundoo.noteservice.model.Token;
import com.indream.fundoo.noteservice.repository.LabelCustomRepository;
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
    private NoteRepository noteRepository;
    @Autowired
    @Qualifier(value = "repository")
    private UserRepository userRepository;
    @Autowired
    private LabelCustomRepository labelCustomRepository;

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
	    validNote(noteDto.get_id(), userId);// CHECK FOR THE NOTE ID BELONGING TO THE USER ID
	    noteDto.setLastModified(new Date());// LAST MODIFIED SET VALUE
	    NoteEntity note = Utility.convert(noteDto, NoteEntity.class);// MODEL MAPPER TO CONVERT
	    noteRepository.save(note);// UPDATE THE EXISTING NOTE
	} catch (RuntimeException e) {
	    e.printStackTrace();
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
     * @purpose SET THE REMINDER FOR THE NOTE
     * 
     * @author akshay
     * 
     * @com.indream.fundoo.noteservice.service
     * 
     * @since Jul 25, 2018
     *
     */
    @Override
    public void reminderNote(NoteDto noteDto, Token token) {
	NoteEntity noteEntity = getValidNoteEntity(noteDto.get_id(), token);// CHECK FOR THE VALID NOTE
	try {

	    class TimerImpl extends TimerTask {

		@Override
		public void run() {
		    System.out.println("The alert is on note " + noteEntity.getTitle());

		}

	    }

	    TimerImpl timerImpl = new TimerImpl();

	    Timer timer = new Timer(true);
	    timer.schedule(timerImpl, noteDto.getReminderDate());

	} catch (Exception e) {
	    e.printStackTrace();
	    throw new RuntimeException(e.getMessage());

	}

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
    private NoteEntity getNote(String noteId) {

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
	try {

	    List<NoteEntity> noteEntities = noteRepository.getByUserId(userId);// GET NOTE BY USER ID
	    System.out.println(noteEntities);
	    long count = noteEntities.stream().filter(p -> p.get_id().equals(noteId.toString())).count();
	    // CHECK FOR ONLY ONE EXISTANCE FOR THAT PARTICULAR NOTE ID
	    if (count != 1) {
		throw new NoteException("Note not found to be update status");
	    }
	} catch (Exception e) {
	  throw new NoteException(e.getMessage());
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
	    System.out.println(noteId);
	    System.out.println(userId);

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

    /*
     * @purpose
     *
     *
     * @author akshay
     * 
     * @com.indream.fundoo.noteservice.service
     * 
     * @since Jul 25, 2018
     *
     */
    @Override
    public void createLabel(String label, Token token) {

	try {
	    String userId = this.getUserId(token);

	    if (label.trim().length() == 0)// default label name
		return;
	    LabelEntity labelEntity = new LabelEntity();
	    labelEntity.setLabelName(label);
	    labelEntity.setUserId(userId);
	    labelCustomRepository.save(labelEntity);

	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    /*
     * @purpose
     *
     *
     * @author akshay
     * 
     * @com.indream.fundoo.noteservice.service
     * 
     * @since Jul 25, 2018
     *
     */
    @Override
    public void setLabelNote(String noteId, Token token, String labelId) {

	try {
	    String userId = this.getUserId(token);
	    this.validateUser(userId);
	    this.validNote(noteId, userId);
	    NoteEntity noteEntity = this.getNote(noteId);
	    LabelEntity label = this.getLabelEntity(labelId);
	    this.validLabel(userId, label);
	    noteEntity.getLabel().add(label.get_id());
	    noteRepository.save(noteEntity);
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    /*
     * @purpose
     *
     *
     * @author akshay
     * 
     * @com.indream.fundoo.noteservice.service
     * 
     * @since Jul 25, 2018
     *
     */
    private LabelEntity getLabelEntity(String labelId) {
	LabelEntity label = labelCustomRepository.findOne(labelId);
	if (label == null) {
	    throw new LabelException("Label id is invalid ");

	}
	return label;
    }

    /*
     * @purpose
     *
     *
     * @author akshay
     * 
     * @com.indream.fundoo.noteservice.service
     * 
     * @since Jul 25, 2018
     *
     */
    @Override
    public void editLabelName(Token token, String label, String labelId) {

	try {
	    String userId = this.getUserId(token);
	    if (label.trim().length() == 0)// default label name
		throw new LabelException("Label value cannot be empty");
	    LabelEntity labelEntity = this.getLabelEntity(labelId);
	    this.validLabel(userId, labelEntity);

	    labelEntity.setLabelName(label);
	    labelCustomRepository.save(labelEntity);
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    /*
     * @purpose
     *
     *
     * @author akshay
     * 
     * @com.indream.fundoo.noteservice.service
     * 
     * @since Jul 25, 2018
     *
     */
    private void validLabel(String userId, LabelEntity label) {
	if (!label.getUserId().equals(userId)) {
	    throw new UserException("No user found for the label");
	}

    }

    /*
     * @purpose
     *
     *
     * @author akshay
     * 
     * @com.indream.fundoo.noteservice.service
     * 
     * @since Jul 25, 2018
     *
     */
    @Override
    public void deleteLabel(String labelId, Token token) {

	try {
	    System.out.println("the value of the delete is success");

	} catch (Exception e) {

	    e.printStackTrace();

	}

    }

}
