package com.indream.fundoo.noteservice.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.indream.fundoo.exceptionhandler.NoteException;
import com.indream.fundoo.exceptionhandler.UserException;
import com.indream.fundoo.noteservice.model.NoteDto;
import com.indream.fundoo.noteservice.model.NoteEntity;
import com.indream.fundoo.noteservice.repository.NoteRepository;
import com.indream.fundoo.userservice.model.UserEntity;
import com.indream.fundoo.userservice.repository.UserRepository;
import com.indream.fundoo.util.TokenManager;
import com.indream.fundoo.util.Utility;

import io.jsonwebtoken.Claims;

public class NoteServiceImpl implements NoteService {
	
	@Autowired
	TokenManager manager;
	@Autowired
	NoteRepository noteRepository;
	@Autowired
	@Qualifier(value = "repository")
	private UserRepository userRepository;

	@Override
	public void createNote(NoteDto noteEntityDTO, String token) {
		try {
			NoteEntity noteEntity = Utility.convert(noteEntityDTO, NoteEntity.class);
			noteEntity.setCreadtedOn(new Date());
			noteEntity.setLastModified(new Date());
			noteEntity.setId(null);
			Claims claims = manager.validateToken(token);
			String userId = claims.get("id").toString();
			validateUser(userId);
			noteEntity.setUserId(userId);
			noteRepository.save(noteEntity);
		} catch (UserException e) {
			throw e;
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}

	}

	@Override
	public void updateNote(NoteDto noteDto, String token) {
		try {
			String userId = getUserId(token);
			validateUser(userId);
			validNote(noteDto.getId().toString(), userId);
			noteDto.setLastModified(new Date());
			NoteEntity note = Utility.convert(noteDto, NoteEntity.class);
			noteRepository.save(note);
		} catch (RuntimeException e) {
			throw e;
		}
	}

	@Override
	public void deleteNote(String noteId, String token) {
		try {
			String userId = getUserId(token);
			validateUser(userId);
			System.out.println(userId);
			List<NoteEntity> noteEntities = noteRepository.getByUserId(userId);
			System.out.println(noteEntities.size() + " the size");
			NoteEntity noteEntity = Utility.getNoteEntity(noteEntities, noteId);
			if (!noteEntity.isTrashed()) {
				throw new NoteException("Note not trashed cannote delete it");
			}
			noteRepository.delete(noteEntity);
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}

	}

	@Override
	public List<NoteEntity> selectNote(String token) {
		try {
			String userId = getUserId(token);
			validateUser(userId);
			return noteRepository.getByUserId(userId);
		} catch (RuntimeException e) {
			throw e;
		}
	}

	@Override
	public void archiveNote(String noteId, String token) {
		NoteEntity noteEntity = getValidNoteEntity(noteId, token);
		noteEntity.setArchived(true);
		noteEntity.setPinned(false);
		noteEntity.setTrashed(false);
		noteRepository.save(noteEntity);
	}

	@Override
	public void deleteNoteToTrash(String noteId, String token) {
		NoteEntity noteEntity = getValidNoteEntity(noteId, token);
		noteEntity.setArchived(false);
		noteEntity.setPinned(false);
		noteEntity.setTrashed(true);
		noteRepository.save(noteEntity);

	}

	@Override
	public void pinNote(String noteId, String token) {
		NoteEntity noteEntity = getValidNoteEntity(noteId, token);
		noteEntity.setArchived(false);
		noteEntity.setPinned(true);
		noteEntity.setTrashed(false);
		noteRepository.save(noteEntity);

	}

	@Override
	public void restoreNote(String noteId, String token) {
		NoteEntity noteEntity = getValidNoteEntity(noteId, token);

		if (!noteEntity.isTrashed()) {
			throw new NoteException("Note not trashed cannot be restored invalid operation ");
		}
		noteEntity.setTrashed(false);
		noteRepository.save(noteEntity);

	}

	public NoteEntity getNote(String noteId) {

		return noteRepository.findOne(noteId);

	}

	private void validNote(String noteId, String userId) {
		List<NoteEntity> noteEntities = noteRepository.getByUserId(userId);
		long count = noteEntities.stream().filter(p -> p.getId().toString().equals(noteId.toString())).count();
		if (count != 1) {
			throw new NoteException("Note not found to be update status");
		}
	}

	private NoteEntity getValidNoteEntity(String noteId, String token) {
		try {
			String userId = getUserId(token);
			this.validateUser(userId);
			this.validNote(noteId, userId);
			NoteEntity noteEntity = this.getNote(noteId);
			return noteEntity;
		} catch (RuntimeException e) {
			throw e;
		}
	}

	private void validateUser(String userId) throws UserException {
		UserEntity userEntity = userRepository.findOne(userId);
		if (userEntity == null) {
			throw new UserException("User not found");
		}
		if (!userEntity.isActive()) {
			throw new UserException("User is inactive");
		}
	}

	private String getUserId(String token) {
		Claims claims = manager.validateToken(token);
		String userId = claims.get("id").toString();
		return userId;
	}

}
