package com.indream.fundoo.noteservice.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.indream.fundoo.noteservice.model.Note;
import com.indream.fundoo.noteservice.model.NoteResponse;
import com.indream.fundoo.noteservice.service.NoteService;

@RestController
public class NoteController {

	@Autowired
	NoteService noteService;

	@RequestMapping(path = "/note", method = RequestMethod.POST)
	public ResponseEntity<NoteResponse> createNote(@RequestBody Note note, HttpServletRequest request) {
		String token = request.getHeader("authorization");
		noteService.createNote(note, token);

		return new ResponseEntity<NoteResponse>(new NoteResponse("Note created success", 11), HttpStatus.OK);
	}

	@RequestMapping(path = "/note", method = RequestMethod.PUT)
	public ResponseEntity<NoteResponse> updateNote(@RequestBody Note note, HttpServletRequest request) {
		String token = request.getHeader("authorization");
//		noteService.updateNote(note, token);
		return new ResponseEntity<NoteResponse>(new NoteResponse("Note updated success", 11), HttpStatus.OK);

	}

	@RequestMapping(path = "/note/{id:.*}", method = RequestMethod.DELETE)
	public ResponseEntity<NoteResponse> deleteNote(@PathVariable("id") String noteId, HttpServletRequest request) {
		String token = request.getHeader("authorization");
		noteService.deleteNote(noteId, token);
		return new ResponseEntity<NoteResponse>(new NoteResponse("Note deleted success", 11), HttpStatus.OK);

	}

	@RequestMapping(path = "/note", method = RequestMethod.GET)
	public ResponseEntity<NoteResponse> listNotes(HttpServletRequest request) {
		String token = request.getHeader("authorization");
		noteService.selectNote(token);
		return new ResponseEntity<NoteResponse>(new NoteResponse("Note created success", 11), HttpStatus.OK);

	}

}
