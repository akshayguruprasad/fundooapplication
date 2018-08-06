package com.indream.fundoo.noteservice.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;

import com.indream.fundoo.exceptionhandler.LabelException;
import com.indream.fundoo.exceptionhandler.NoteException;
import com.indream.fundoo.exceptionhandler.UserException;
import com.indream.fundoo.noteservice.dao.NoteDao;
import com.indream.fundoo.noteservice.model.LabelEntity;
import com.indream.fundoo.noteservice.model.NoteDto;
import com.indream.fundoo.noteservice.model.NoteEntity;
import com.indream.fundoo.noteservice.model.UrlEntity;
import com.indream.fundoo.userservice.model.UserEntity;
import com.indream.fundoo.userservice.repository.UserRepository;
import com.indream.fundoo.util.PreConditions;
import com.indream.fundoo.util.Utility;

/**
 * NOTESERVICE IMPL SERVICE LAYER
 * 
 * @author Akshay
 *
 */
@SuppressWarnings("unchecked")
public class NoteServiceImpl implements NoteService {
	@Autowired
	private NoteDao dao;
	@Autowired
	@Qualifier(value = "repository")
	private UserRepository userRepository;
	@Autowired
	private Environment env;

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
	public String createNote(NoteDto noteEntityDTO, String token, HttpServletRequest request) {
		NoteEntity noteEntity = Utility.convert(noteEntityDTO, NoteEntity.class);// MODEL MAPPING FOR DTO TO ENTITY
		noteEntity.setCreadtedOn(new Date());// CREATED DATE
		noteEntity.setLastModified(new Date());// LASTE MODIFIED DATE
		String userId = this.getUserId(token);// GET USER ID
		validateUser(userId);// VALIDATE THE USER
		noteEntity.setUserId(userId);// IF TRUE THEN SET USERID
		String id;
		try {
			id = dao.saveElasticNote(noteEntity);
			PreConditions.checkNull(id, env.getProperty("note.save.error.message"), NoteException.class);
			return id;
		} catch (IOException e) {
			throw new NoteException(env.getProperty("note.save.error.message"));
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
	public void updateNote(NoteDto noteDto, String token) {
		String userId = getUserId(token);// GET USER ID FROM THE TOKEN
		validateUser(userId);// VALIDATE THE USER (CHECK FOR THE EXISTANCE OF THE USER)
		validNote(noteDto.getId(), userId);// CHECK FOR THE NOTE ID BELONGING TO THE USER ID
		noteDto.setLastModified(new Date());// LAST MODIFIED SET VALUE
		NoteEntity noteEntity = Utility.convert(noteDto, NoteEntity.class);// MODEL MAPPER TO CONVERT
		try {
			dao.updateElasticNote(noteEntity);

		} catch (IOException e) {
			throw new NoteException(env.getProperty("note.update.error.message"));
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
	public void deleteNote(String noteId, String token) {
		String userId = this.getUserId(token);
		try {
			List<NoteEntity> noteEntities = (List<NoteEntity>) dao.searchElasticNote(userId);
			NoteEntity noteEntity = noteEntities.stream().filter(p -> p.getId().equals(noteId)).findFirst()
					.orElse(null);
			PreConditions.checkFalse(noteEntity.isTrashed(), env.getProperty("note.trashed.not.error.message"),
					NoteException.class);
			dao.deleteElasticNote(noteEntity.getId());
		} catch (IOException e) {
			throw new NoteException(env.getProperty("note.delete.error.message"));
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
	public List<NoteEntity> selectNote(String token) {
		String userId = getUserId(token);// GET USER ID FROM THE TOKEN
		validateUser(userId);// VALIDATE THE USER ID CHECK FOR EXISTANCE
		try {
			return (List<NoteEntity>) dao.searchElasticNote(userId);
		} catch (IOException e) {

			throw new NoteException(env.getProperty("note.select.error.message"));

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
	public void archiveNote(String noteId, String token) {
		NoteEntity noteEntity = getValidNoteEntity(noteId, token);// CHECK FOR THE VALID NOTE
		noteEntity.setArchived(true);// MAKE NOTE ARCHIVE
		noteEntity.setPinned(false);
		noteEntity.setTrashed(false);
		try {
			PreConditions.checkNull(dao.updateElasticNote(noteEntity),
					env.getProperty("note.archive.not.error.message"), NoteException.class);
		} catch (IOException e) {

			throw new NoteException(env.getProperty("note.update.error.message"));
		}
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
	public void deleteNoteToTrash(String noteId, String token) {
		NoteEntity noteEntity = getValidNoteEntity(noteId, token);// CHECK FOR THE VALID NOTE
		noteEntity.setArchived(false);
		noteEntity.setPinned(false);
		noteEntity.setTrashed(true);// SET TRASH AS TRUE TO MAKE IT TRASH
		try {
			PreConditions.checkNull(dao.updateElasticNote(noteEntity),
					env.getProperty("note.trashed.not.error.message"), NoteException.class);
		} catch (IOException e) {

			throw new NoteException(env.getProperty("note.update.error.message"));
		}
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
	public void pinNote(String noteId, String token) {
		NoteEntity noteEntity = getValidNoteEntity(noteId, token);// CHECK FOR VALID NOTE
		noteEntity.setArchived(false);
		noteEntity.setPinned(true);// MAKING IT PINNED
		noteEntity.setTrashed(false);
		try {
			PreConditions.checkNull(dao.updateElasticNote(noteEntity), env.getProperty("note.pin.not.error.message"),
					NoteException.class);
		} catch (IOException e) {

			throw new NoteException(env.getProperty("note.update.error.message"));
		}
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
	public void restoreNote(String noteId, String token) {
		NoteEntity noteEntity = getValidNoteEntity(noteId, token);// CHECK FOR THE VALID NOTE
		PreConditions.checkFalse(noteEntity.isTrashed(), env.getProperty("note.trash.error.message"),
				NoteException.class);
		noteEntity.setTrashed(false);// RESTORE FROM TRASH
		try {
			PreConditions.checkNull(dao.updateElasticNote(noteEntity), env.getProperty("note.restore.error.message"),
					NoteException.class);
		} catch (IOException e) {

			throw new NoteException(env.getProperty("note.update.error.message"));
		}
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
	public void reminderNote(NoteDto noteDto, String token) {
		final NoteEntity noteEntity = getValidNoteEntity(noteDto.getId(), token);// CHECK FOR THE VALID NOTE
		class TimerImpl extends TimerTask {
			@Override
			public void run() {
				System.out.println(env.getProperty("note.remined.message") + noteEntity.getTitle());
			}
		}
		TimerImpl timerImpl = new TimerImpl();
		Timer timer = new Timer(true);
//		timer.schedule(timerImpl, noteDto.getReminderDate());
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
		List<NoteEntity> noteEntities;
		try {
			noteEntities = (List<NoteEntity>) dao.searchElasticNote(userId);
		} catch (IOException e) {
			throw new NoteException(env.getProperty("note.found.not.error.message"));
		}
		long count = noteEntities.stream().filter(p -> p.getId().equals(noteId.toString())).count();
		// CHECK FOR ONLY ONE EXISTANCE FOR THAT PARTICULAR NOTE ID
		if (count != 1) {
			throw new NoteException(env.getProperty("note.integirity.error.message"));
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
	private NoteEntity getValidNoteEntity(String noteId, String token) {
		String userId = getUserId(token);// GET THE USER ID
		this.validateUser(userId);// VALIDATE THE USER
		this.validNote(noteId, userId);// VALIDATE NOTE
		NoteEntity noteEntity;
		try {
			noteEntity = dao.getElasticNoteEntity("id", noteId);
			return noteEntity;// RETURN

		} catch (IOException e) {
			throw new NoteException(env.getProperty("note.valid.not.found"));
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
		PreConditions.checkNull(userEntity, env.getProperty("user.found.not.error.message"), UserException.class);
		PreConditions.checkFalse(userEntity.isActive(), env.getProperty("user.inactive.message"), UserException.class);
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
	private String getUserId(String token) {
		return token;// GET THE USER ID FROM THE TOKEN
	}

	/*
	 * @purpose CREATE A LABEL INDEPENDENTLY
	 *
	 * @author akshay
	 * 
	 * @com.indream.fundoo.noteservice.service
	 * 
	 * @since Jul 25, 2018
	 *
	 */
	@Override
	public String createLabel(String label, String token) {
		try {

			String id = null;
			String userId = this.getUserId(token);
			checkLabelLength(label, env.getProperty("label.length.error.message"));
			LabelEntity value = dao.getElasticLabelEntity(env.getProperty("id"), label);
			PreConditions.checkNotNull(value, env.getProperty("label.found.error.message"), LabelException.class);
			LabelEntity labelEntity = new LabelEntity();
			labelEntity.setLabelName(label);
			labelEntity.setUserId(userId);

			id = dao.saveElasticLabel(labelEntity);
			if (id == null) {
				throw new LabelException(env.getProperty("label.save.error.message"));
			}

			return id;
		} catch (IOException e) {

			throw new LabelException(env.getProperty("label.save.error.message"));
		}
	}

	public void checkLabelLength(String label, String message) {
		PreConditions.checkNotPositive(label.trim().length(), message, LabelException.class);
	}

	/*
	 * @purpose SET LABEL VALUE TO NOTE
	 *
	 * @author akshay
	 * 
	 * @com.indream.fundoo.noteservice.service
	 * 
	 * @since Jul 25, 2018
	 *
	 */
	@Override
	public void setLabelNote(String noteId, String token, String labelId) {

		String userId = this.getUserId(token);
		this.validateUser(userId);
		this.validNote(noteId, userId);
		NoteEntity noteEntity = null;
		try {
			noteEntity = dao.getElasticNoteEntity("id", noteId);
			LabelEntity label = this.getLabelEntity(labelId);
			this.validLabel(userId, label);
			noteEntity.getLabel().add(labelId);
			dao.updateElasticNote(noteEntity);
		} catch (IOException e) {
			throw new NoteException(env.getProperty("label.note.set.error.message"));

		}
	}

	/*
	 * @purpose GET LABEL
	 *
	 * @author akshay
	 * 
	 * @com.indream.fundoo.noteservice.service
	 * 
	 * @since Jul 25, 2018
	 *
	 */
	private LabelEntity getLabelEntity(String labelId) {
		LabelEntity label;
		try {
			label = dao.getElasticLabelEntity("id", labelId);
			return PreConditions.checkNull(label, env.getProperty("label.found.not.error.message"),
					LabelException.class);
		} catch (IOException e) {
			throw new LabelException(env.getProperty("label.found.not.error.message"));
		}
	}

	/*
	 * @purpose EDIT THE LABEL
	 *
	 * @author akshay
	 * 
	 * @com.indream.fundoo.noteservice.service
	 * 
	 * @since Jul 25, 2018
	 *
	 */
	@Override
	public void editLabelName(String token, String label, String labelId) {
		String userId = this.getUserId(token);
		this.checkLabelLength(label, env.getProperty("label.length.error.message"));
		LabelEntity labelEntity = this.getLabelEntity(labelId);
		this.validLabel(userId, labelEntity);
		labelEntity.setLabelName(label);
		try {
			dao.updateElasticLabel(labelEntity);
		} catch (IOException e) {

			throw new LabelException(env.getProperty("label.update.error.message"));

		}
	}

	/*
	 * @purpose VALIDATING LABEL AGAINST THE USERID AND THE CORROSPONDING LABEL
	 * FOUND
	 *
	 * @author akshay
	 * 
	 * @com.indream.fundoo.noteservice.service
	 * 
	 * @since Jul 25, 2018
	 *
	 */
	private void validLabel(String userId, LabelEntity label) {
		PreConditions.checkNotMatch(userId, label.getUserId(), env.getProperty("label.valid.error.message"),
				UserException.class);
	}

	/*
	 * @purpose DELETE LABEL FROM THE SYSTEM COMPLETELY
	 *
	 * @author akshay
	 * 
	 * @com.indream.fundoo.noteservice.service
	 * 
	 * @since Jul 25, 2018
	 *
	 */
	@Override
	public void deleteLabel(String labelId, String token) {

		LabelEntity value;
		try {
			value = dao.getElasticLabelEntity("id", labelId);
			PreConditions.checkNotMatch(value.getUserId(), token, env.getProperty("label.authority.error.message"),
					LabelException.class);
			String userId = this.getUserId(token);

			if (dao.deleteElasticLabel(labelId) == null) {
				throw new LabelException(env.getProperty("label.delete.error.message"));

			}
		} catch (IOException e) {
			throw new LabelException(env.getProperty("label.delete.error.message"));

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
	 * @since 02-Aug-2018
	 *
	 */
	@Override
	public List<NoteEntity> selectPinNote(String token) {

		try {
			List<NoteEntity> noteEntities = (List<NoteEntity>) dao.searchElasticNote(token);

			return noteEntities.stream().filter(p -> p.isPinned()).collect(Collectors.toList());

		} catch (IOException e) {
			throw new NoteException(env.getProperty("note.select.pin.error.message"));

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
	 * @since 02-Aug-2018
	 *
	 */
	@Override
	public List<NoteEntity> selectArchiveNote(String token) {

		try {
			List<NoteEntity> noteEntities = (List<NoteEntity>) dao.searchElasticNote(token);

			return noteEntities.stream().filter(p -> p.isArchived()).collect(Collectors.toList());

		} catch (IOException e) {
			throw new NoteException(env.getProperty("note.select.archive.error.message"));

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
	 * @since 02-Aug-2018
	 *
	 */
	@Override
	public List<NoteEntity> selectTrashNote(String token) {
		try {
			List<NoteEntity> noteEntities = (List<NoteEntity>) dao.searchElasticNote(token);

			return noteEntities.stream().filter(p -> p.isTrashed()).collect(Collectors.toList());

		} catch (IOException e) {
			throw new NoteException(env.getProperty("note.select.trash.error.message"));

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
	 * @since 02-Aug-2018
	 *
	 */
	@Override
	public void unArchiveNote(String noteId, String token) {

		try {
			NoteEntity note = dao.getElasticNoteEntity("id", noteId);
			if (!note.getUserId().equals(token)) {
				throw new NoteException(env.getProperty("note.authority.error.message"));

			}
			note.setArchived(false);
			dao.updateElasticNote(note);
		} catch (IOException e) {
			throw new NoteException(env.getProperty("note.unarchive.error.message"));

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
	 * @since 02-Aug-2018
	 *
	 */
	@Override
	public void unPinNote(String noteId, String token) {

		try {
			NoteEntity note = dao.getElasticNoteEntity("id", noteId);
			if (!note.getUserId().equals(token)) {
				throw new NoteException(env.getProperty("note.authority.error.message"));

			}
			note.setPinned(false);
			dao.updateElasticNote(note);
		} catch (IOException e) {
			throw new NoteException(env.getProperty("note.unpin.error.message"));

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
	 * @since 04-Aug-2018
	 *
	 */
	@Override
	public void urlNote(String url, String noteId, String token) {

		try {
			NoteEntity note = dao.getElasticNoteEntity("id", noteId);
			if (!note.getUserId().equals(token)) {
				throw new NoteException(env.getProperty("note.authority.error.message"));
			}
			UrlEntity urlEntity = this.getScrappedData(url);
			note.getEntities().add(urlEntity);
			System.out.println(note);
			dao.updateElasticNote(note);
		} catch (IOException e) {
			throw new NoteException(env.getProperty("note.url.add.error.message"));

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
	 * @since 04-Aug-2018
	 *
	 */
	private UrlEntity getScrappedData(String url) {

		UrlEntity urlEntity = null;

		try {
			Document document = Jsoup.connect(url).get();
			urlEntity = new UrlEntity();
			urlEntity.setTitle(document.title());
			urlEntity.setUrl(url);
			urlEntity.setImage(getImage(document));
			urlEntity.setDetail(document.select("meta[name=description]").get(0).attr("content"));
			System.out.println(urlEntity);
		} catch (IOException e) {
			throw new NoteException(env.getProperty("note.scaping.error.message"));
		}
		return urlEntity;
	}

	/*
	 * @purpose
	 *
	 *
	 * @author akshay
	 * 
	 * @com.indream.fundoo.noteservice.service
	 * 
	 * @since 04-Aug-2018
	 *
	 */
	private String getImage(Document document) {
		Elements images = document.select("img[src~=(?i)\\.(png|jpe?g|gif)]");

		for (Element element : images) {

			return element.attr("src");
		}
		return null;

	}

}
