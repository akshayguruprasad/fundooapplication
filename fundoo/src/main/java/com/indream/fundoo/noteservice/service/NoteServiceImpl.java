package com.indream.fundoo.noteservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.indream.fundoo.noteservice.model.Note;
import com.indream.fundoo.noteservice.model.NoteEntityDTO;
import com.indream.fundoo.noteservice.repository.NoteRepository;
import com.indream.fundoo.util.MessageService;
import com.indream.fundoo.util.TokenManager;

import io.jsonwebtoken.Claims;

@SuppressWarnings("unused")
public class NoteServiceImpl implements NoteService {

	@Override
	public void createNote(NoteEntityDTO noteEntityDTO, String token) {

		
		
		
	}

	@Override
	public void updateNote(String noteId, String content, String token) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteNote(String noteId, String token) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Note> selectNote(String token) {
		// TODO Auto-generated method stub
		return null;
	}}
