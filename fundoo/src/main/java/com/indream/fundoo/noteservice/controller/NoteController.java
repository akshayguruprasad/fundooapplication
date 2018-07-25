package com.indream.fundoo.noteservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.indream.fundoo.noteservice.model.NoteDto;
import com.indream.fundoo.noteservice.model.NoteEntity;
import com.indream.fundoo.noteservice.model.NoteResponse;
import com.indream.fundoo.noteservice.model.Token;
import com.indream.fundoo.noteservice.service.NoteService;

/**
 * NOTE CONTROLLER FOR THE NOTE REQUESTS
 * 
 * @author Akshay
 *
 */
@RestController
@RequestMapping(path = "noteapplication")
public class NoteController {

    @Autowired
    NoteService noteService;

    /*
     * @purpose REQUEST MAPPING FOR THE CREATE NOTE
     *
     * @author akshay
     * 
     * @com.indream.fundoo.noteservice.controller
     * 
     * @since Jul 24, 2018
     *
     */
    @RequestMapping(path = "/createnote", method = RequestMethod.POST)
    public ResponseEntity<NoteResponse> createNote(@RequestBody NoteDto noteEntityDTO, HttpServletRequest request) {
	Token token = (Token) request.getSession().getAttribute("token");
	noteService.createNote(noteEntityDTO, token);
	return new ResponseEntity<NoteResponse>(new NoteResponse("Note created success", 11), HttpStatus.OK);
    }

    /*
     * @purpose REQUEST MAPPING FOR THE UPDATE NOTE
     *
     * @author akshay
     * 
     * @com.indream.fundoo.noteservice.controller
     * 
     * @since Jul 24, 2018
     *
     */
    @RequestMapping(path = "/updatenote", method = RequestMethod.PUT)
    public ResponseEntity<NoteResponse> updateNote(@RequestBody NoteDto noteEntityDTO, HttpServletRequest request) {
	Token token = (Token) request.getSession().getAttribute("token");
	noteService.updateNote(noteEntityDTO, token);
	return new ResponseEntity<NoteResponse>(new NoteResponse("Note updated success", 11), HttpStatus.OK);

    }

    /*
     * @purpose REQUEST MAPPING FOR THE DELETE NOTE
     *
     * @author akshay
     * 
     * @com.indream.fundoo.noteservice.controller
     * 
     * @since Jul 24, 2018
     *
     */
    @RequestMapping(path = "/deletenote/{id:.*}", method = RequestMethod.DELETE)
    public ResponseEntity<NoteResponse> deleteNote(@PathVariable("id") String noteId, HttpServletRequest request) {
	Token token = (Token) request.getSession().getAttribute("token");
	noteService.deleteNote(noteId, token);
	return new ResponseEntity<NoteResponse>(new NoteResponse("Note deleted success", 11), HttpStatus.OK);
    }

    /*
     * @purpose REQUEST MAPPING FOR THE LIST NOTE
     *
     * @author akshay
     * 
     * @com.indream.fundoo.noteservice.controller
     * 
     * @since Jul 24, 2018
     *
     */
    @RequestMapping(path = "/listnote", method = RequestMethod.GET)
    public ResponseEntity<NoteResponse> listNotes(HttpServletRequest request) {
	Token token = (Token) request.getSession().getAttribute("token");
	List<NoteEntity> notes = noteService.selectNote(token);
	return new ResponseEntity<NoteResponse>(new NoteResponse(notes.toString(), 11), HttpStatus.OK);
    }

    /*
     * @purpose REQUEST MAPPING FOR THE PIN NOTE
     *
     * @author akshay
     * 
     * @com.indream.fundoo.noteservice.controller
     * 
     * @since Jul 24, 2018
     *
     */
    @RequestMapping(path = "/pinnote", method = RequestMethod.PUT)
    public ResponseEntity<String> pinNote(String noteId, HttpServletRequest request) {
	Token token = (Token) request.getSession().getAttribute("token");
	noteService.pinNote(noteId, token);
	return new ResponseEntity<String>("Status updated successful", HttpStatus.OK);
    }

    /*
     * @purpose REQUEST MAPPING FOR THE TRASH NOTE
     *
     * @author akshay
     * 
     * @com.indream.fundoo.noteservice.controller
     * 
     * @since Jul 24, 2018
     *
     */
    @RequestMapping(path = "/trashnote", method = RequestMethod.DELETE)
    public ResponseEntity<String> trashNote(String noteId, HttpServletRequest request) {
	Token token = (Token) request.getSession().getAttribute("token");
	noteService.deleteNoteToTrash(noteId, token);
	return new ResponseEntity<String>("Status updated successful", HttpStatus.OK);
    }

    /*
     * @purpose REQUEST MAPPING FOR THE ARCHIVE NOTE
     *
     * @author akshay
     * 
     * @com.indream.fundoo.noteservice.controller
     * 
     * @since Jul 24, 2018
     *
     */
    @RequestMapping(path = "/archivenote", method = RequestMethod.PUT)
    public ResponseEntity<String> archiveNote(String noteId, HttpServletRequest request) {
	Token token = (Token) request.getSession().getAttribute("token");
	noteService.archiveNote(noteId, token);
	return new ResponseEntity<String>("Status updated successful", HttpStatus.OK);
    }
    /*
     * @purpose REQUEST MAPPING FOR THE RESTORE NOTE
     *
     * @author akshay
     * 
     * @com.indream.fundoo.noteservice.controller
     * 
     * @since Jul 24, 2018
     *
     */

    @RequestMapping(path = "/restorenote", method = RequestMethod.POST)
    public ResponseEntity<String> restoreNote(String noteId, HttpServletRequest request) {
	Token token = (Token) request.getSession().getAttribute("token");
	noteService.restoreNote(noteId, token);
	return new ResponseEntity<String>("Note restored success", HttpStatus.OK);
    }

    @RequestMapping(path = "/remindernote", method = RequestMethod.POST)
    public ResponseEntity<String> reminderNote(@RequestBody NoteDto noteDto, HttpServletRequest request) {
	Token token = (Token) request.getSession().getAttribute("token");
	System.out.println(noteDto);
	noteService.reminderNote(noteDto, token);
	return new ResponseEntity<String>("Note reminder success", HttpStatus.OK);
    }

    @RequestMapping(path = "/createlabel", method = RequestMethod.POST)
    public ResponseEntity<String> createLabel(String label, HttpServletRequest request) {
	Token token = (Token) request.getSession().getAttribute("token");
	noteService.createLabel(label, token);
	return new ResponseEntity<String>("Note reminder success", HttpStatus.OK);
    }

    @RequestMapping(path = "/editlabel", method = RequestMethod.PUT)
    public ResponseEntity<String> editLabel(String label, HttpServletRequest request, String labelId) {
	Token token = (Token) request.getSession().getAttribute("token");
	noteService.editLabelName(token, label, labelId);
	return new ResponseEntity<String>("Note reminder success", HttpStatus.OK);
    }

    @RequestMapping(path = "/deletelabel", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteLabel(String label, HttpServletRequest request) {
	Token token = (Token) request.getSession().getAttribute("token");
	noteService.deleteLabel(label, token);
	return new ResponseEntity<String>("Note reminder success", HttpStatus.OK);
    }

    @RequestMapping(path = "/labelnote", method = RequestMethod.PUT)
    public ResponseEntity<String> labelNote(String label, String noteId, HttpServletRequest request) {
	Token token = (Token) request.getSession().getAttribute("token");
	noteService.setLabelNote(noteId, token, label);
	return new ResponseEntity<String>("Note reminder success", HttpStatus.OK);
    }

}
