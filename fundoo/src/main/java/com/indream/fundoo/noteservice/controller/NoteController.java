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

import com.indream.fundoo.configuration.I18NSpec;
import com.indream.fundoo.noteservice.model.NoteDto;
import com.indream.fundoo.noteservice.model.NoteEntity;
import com.indream.fundoo.noteservice.model.NoteResponse;
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

	
	@Autowired
	I18NSpec i18N;
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
		try {
			String token = (String) request.getSession().getAttribute("token");
			noteService.createNote(noteEntityDTO, token,request);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ResponseEntity<NoteResponse>(new NoteResponse(i18N.getMessage("note.create.success"), 11), HttpStatus.OK);
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
		String token = (String) request.getSession().getAttribute("token");
		noteService.updateNote(noteEntityDTO, token);
		return new ResponseEntity<NoteResponse>(new NoteResponse(i18N.getMessage("note.update.success"), 11), HttpStatus.OK);

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
		String token = (String) request.getSession().getAttribute("token");
		
		System.out.println("Token in contorller "+token);
		noteService.deleteNote(noteId, token);
		return new ResponseEntity<NoteResponse>(new NoteResponse(i18N.getMessage("note.delete.success"), 11), HttpStatus.OK);
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
		String token = (String) request.getSession().getAttribute("token");
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
		String token = (String) request.getSession().getAttribute("token");
		noteService.pinNote(noteId, token);
		return new ResponseEntity<String>(i18N.getMessage("note.pin.success"), HttpStatus.OK);
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
		String token = (String) request.getSession().getAttribute("token");
		noteService.deleteNoteToTrash(noteId, token);
		return new ResponseEntity<String>(i18N.getMessage("note.trash.success"), HttpStatus.OK);
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
		String token = (String) request.getSession().getAttribute("token");
		noteService.archiveNote(noteId, token);
		return new ResponseEntity<String>(i18N.getMessage("note.archive.success"), HttpStatus.OK);
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
		String token = (String) request.getSession().getAttribute("token");
		noteService.restoreNote(noteId, token);
		return new ResponseEntity<String>(i18N.getMessage("note.restore.success"), HttpStatus.OK);
	}

	@RequestMapping(path = "/remindernote", method = RequestMethod.POST)
	public ResponseEntity<String> reminderNote(@RequestBody NoteDto noteDto, HttpServletRequest request) {
		String token = (String) request.getSession().getAttribute("token");
		System.out.println(noteDto);
		noteService.reminderNote(noteDto, token);
		return new ResponseEntity<String>(i18N.getMessage("note.reminder.success"), HttpStatus.OK);
	}

	@RequestMapping(path = "/createlabel", method = RequestMethod.POST)
	public ResponseEntity<String> createLabel(String label, HttpServletRequest request) {
		String token = (String) request.getSession().getAttribute("token");
		System.out.println("Sression object token is "+token);
		noteService.createLabel(label, token);
		return new ResponseEntity<String>(i18N.getMessage("label.create.success"), HttpStatus.OK);
	}

	@RequestMapping(path = "/editlabel", method = RequestMethod.PUT)
	public ResponseEntity<String> editLabel(String label, HttpServletRequest request, String labelId) {
		String token = (String) request.getSession().getAttribute("token");
		noteService.editLabelName(token, label, labelId);
		return new ResponseEntity<String>(i18N.getMessage("label.update.success"), HttpStatus.OK);
	}

	@RequestMapping(path = "/deletelabel", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteLabel(String label, HttpServletRequest request) {
		String token = (String) request.getSession().getAttribute("token");
		noteService.deleteLabel(label, token);
		return new ResponseEntity<String>(i18N.getMessage("label.delete.success"), HttpStatus.OK);
	}

	@RequestMapping(path = "/labelnote", method = RequestMethod.PUT)
	public ResponseEntity<String> labelNote(String label, String noteId, HttpServletRequest request) {
		String token = (String) request.getSession().getAttribute("token");
		noteService.setLabelNote(noteId, token, label);
		return new ResponseEntity<String>(i18N.getMessage("note.label.success"), HttpStatus.OK);
	}

	@RequestMapping(path = "/getTrashnote", method = RequestMethod.PUT)
	public ResponseEntity<String> getTrashNotes(HttpServletRequest request) {
		String token = (String) request.getSession().getAttribute("token");
		List<NoteEntity> entities = noteService.selectTrashNote(token);
		return new ResponseEntity<String>(entities.toString(), HttpStatus.OK);
	}

	@RequestMapping(path = "/getArchivednote", method = RequestMethod.PUT)
	public ResponseEntity<String> getArchivedNote(HttpServletRequest request) {
		String token = (String) request.getSession().getAttribute("token");
		List<NoteEntity> entities = noteService.selectArchiveNote(token);
		return new ResponseEntity<String>(entities.toString(), HttpStatus.OK);
	}

	@RequestMapping(path = "/getPinnednote", method = RequestMethod.PUT)
	public ResponseEntity<String> getPinnedNote(HttpServletRequest request) {
		String token = (String) request.getSession().getAttribute("token");
		List<NoteEntity> entities = noteService.selectPinNote(token);
		return new ResponseEntity<String>(entities.toString(), HttpStatus.OK);
	}

	@RequestMapping(path = "/unpinnote", method = RequestMethod.PUT)
	public ResponseEntity<String> unPinNote(String noteId, HttpServletRequest request) {
		String token = (String) request.getSession().getAttribute("token");
		noteService.unPinNote(noteId, token);
		return new ResponseEntity<String>(i18N.getMessage("note.unpin.success"), HttpStatus.OK);
	}

	@RequestMapping(path = "/unarchivenote", method = RequestMethod.PUT)
	public ResponseEntity<String> unArchiveNote(String noteId, HttpServletRequest request) {
		String token = (String) request.getSession().getAttribute("token");
		noteService.unArchiveNote(noteId, token);
		return new ResponseEntity<String>(i18N.getMessage("note.unarchive.success"), HttpStatus.OK);
	}
	
	
	@RequestMapping(path = "/addUrl", method = RequestMethod.PUT)
	public ResponseEntity<String> updateUrlNote(String  noteId,String url, HttpServletRequest request) {
		String token = (String) request.getSession().getAttribute("token");
		noteService.urlNote(url,noteId, token);
		return new ResponseEntity<String>(i18N.getMessage("note.url.add.success"), HttpStatus.OK);
	}
	
}
