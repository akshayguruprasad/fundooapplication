package com.indream.fundoo.noteservice.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;

import com.indream.fundoo.exceptionhandler.UserException;
import com.indream.fundoo.noteservice.model.NoteEntity;
import com.indream.fundoo.noteservice.model.NoteEntityDTO;
import com.indream.fundoo.noteservice.repository.NoteRepository;
import com.indream.fundoo.userservice.model.UserEntity;
import com.indream.fundoo.userservice.repository.UserRepository;
import com.indream.fundoo.util.TokenManager;
import com.indream.fundoo.util.Utility;

import io.jsonwebtoken.Claims;

public class NoteServiceImpl implements NoteService {

	@Autowired
	ModelMapper mapper;
	@Autowired
	TokenManager manager;

	@Autowired
	NoteRepository noteRepository;

	@Autowired
	private Environment env;

	@Autowired
	@Qualifier(value = "repository")
	private UserRepository userRepository;

	@Override
	public void createNote(NoteEntityDTO noteEntityDTO, String token) {
		try {
			NoteEntity noteEntity = mapper.map(noteEntityDTO, NoteEntity.class);
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

	private void validateUser(String userId) throws UserException {
		UserEntity userEntity = userRepository.findOne(userId);
		if (userEntity == null) {
			throw new UserException("User not found");
		}
		if (!userEntity.isActive()) {
			throw new UserException("User is inactive");
		}
	}

	@Override
	public void updateNote(String noteId, String content, String token) {
		try {
			Claims claims = manager.validateToken(token);
			String userId = claims.get("id").toString();
			validateUser(userId);
			List<NoteEntity> noteEntities = noteRepository.getByUserId(userId);
			NoteEntity noteEntity = Utility.getNoteEntity(noteEntities, noteId);
			noteEntity.setContents(content);
			noteRepository.save(noteEntity);
		} catch (RuntimeException e) {
			throw e;
		}
	}

	@Override
	public void deleteNote(String noteId, String token) {
		try {
			Claims claims = manager.validateToken(token);
			String userId = claims.get("id").toString();
			validateUser(userId);
			System.out.println(userId);
			List<NoteEntity> noteEntities = noteRepository.getByUserId(userId);
			System.out.println(noteEntities.size()+" the size");
			NoteEntity noteEntity = Utility.getNoteEntity(noteEntities, noteId);
			noteRepository.delete(noteEntity);
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}

	}

	@Override
	public List<NoteEntity> selectNote(String token) {
		try {
			Claims claims = manager.validateToken(token);
			String userId = claims.get("id").toString();
			validateUser(userId);
			return noteRepository.getByUserId(userId);
		} catch (RuntimeException e) {
			throw e;
		}
	}
}
