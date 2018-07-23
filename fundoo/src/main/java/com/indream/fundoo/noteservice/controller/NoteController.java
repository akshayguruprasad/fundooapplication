package com.indream.fundoo.noteservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.indream.fundoo.noteservice.model.NoteEntity;
import com.indream.fundoo.noteservice.model.NoteDto;
import com.indream.fundoo.noteservice.model.NoteResponse;
import com.indream.fundoo.noteservice.service.NoteService;

@RestController
public class NoteController {

	@Autowired
	NoteService noteService;

	@RequestMapping(path = "/createnote", method = RequestMethod.POST)
	public ResponseEntity<NoteResponse> createNote(@RequestBody NoteDto noteEntityDTO,  HttpServletRequest request) {
		String token = request.getHeader("authorization");
		noteService.createNote(noteEntityDTO, token);
		return new ResponseEntity<NoteResponse>(new NoteResponse("Note created success", 11), HttpStatus.OK);
	}

	@RequestMapping(path = "/updatenote", method = RequestMethod.PUT)
	public ResponseEntity<NoteResponse> updateNote(@RequestBody NoteDto noteEntityDTO,@RequestHeader  HttpServletRequest request) {
		String token = request.getHeader("authorization");
		noteService.updateNote(noteEntityDTO, token);
		return new ResponseEntity<NoteResponse>(new NoteResponse("Note updated success", 11), HttpStatus.OK);

	}

	@RequestMapping(path = "/deletenote/{id:.*}", method = RequestMethod.DELETE)
	public ResponseEntity<NoteResponse> deleteNote(@PathVariable("id") String noteId,@RequestHeader  HttpServletRequest request) {
		String token = request.getHeader("authorization");
		noteService.deleteNote(noteId, token);
		return new ResponseEntity<NoteResponse>(new NoteResponse("Note deleted success", 11), HttpStatus.OK);

	}

	@RequestMapping(path = "/listnote", method = RequestMethod.GET)
	public ResponseEntity<NoteResponse> listNotes(String noteId,@RequestHeader  HttpServletRequest request) {
		String token = request.getHeader("authorization");
		List<NoteEntity> notes = noteService.selectNote(token);
		return new ResponseEntity<NoteResponse>(new NoteResponse(notes.toString(), 11), HttpStatus.OK);

	}

	@RequestMapping(path = "/pinnote", method = RequestMethod.PUT)
	public ResponseEntity<String> pinNote(String noteId,@RequestHeader  HttpServletRequest request) {
		String token = request.getHeader("authorization");
		noteService.pinNote(noteId, token);
		return new ResponseEntity<String>("Status updated successful", HttpStatus.OK);

	}

	@RequestMapping(path = "/trashnote", method = RequestMethod.PUT)
	public ResponseEntity<String> trashNote(String noteId,@RequestHeader  HttpServletRequest request) {
		String token = request.getHeader("authorization");
		noteService.deleteNoteToTrash(noteId, token);
		return new ResponseEntity<String>("Status updated successful", HttpStatus.OK);

	}

	@RequestMapping(path = "/archivenote", method = RequestMethod.PUT)
	public ResponseEntity<String> archiveNote(String noteId,@RequestHeader  HttpServletRequest request) {
		String token = request.getHeader("authorization");
		noteService.archiveNote(noteId, token);
		return new ResponseEntity<String>("Status updated successful", HttpStatus.OK);

	}
	
	
	@RequestMapping(path = "/restorenote", method = RequestMethod.PUT)
	public ResponseEntity<String> restoreNote(String noteId,@RequestHeader  HttpServletRequest request) {
		String token = request.getHeader("authorization");
		noteService.restoreNote(noteId, token);
		return new ResponseEntity<String>("Note restored success", HttpStatus.OK);

	}
	
	
}
