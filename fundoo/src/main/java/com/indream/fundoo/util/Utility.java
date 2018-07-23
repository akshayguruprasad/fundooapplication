package com.indream.fundoo.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.indream.fundoo.exceptionhandler.NoteException;
import com.indream.fundoo.noteservice.model.NoteEntity;
import com.indream.fundoo.noteservice.repository.NoteRepository;
import com.indream.fundoo.userservice.model.UserEntity;

public class Utility {
	final static Logger LOG = Logger.getLogger(Utility.class);
	static final ObjectMapper jacksonMapper = new ObjectMapper();
	static final ModelMapper mapper = new ModelMapper();

	public static UserEntity getUserEntityFromTokens(Map<String, String> userData) {
		LOG.info("Enter [Utility][getUserEntityFromTokens]");
		UserEntity userEntity = null;
		String name = userData.get("name");
		String email = userData.get("email");
		userEntity = new UserEntity();
		userEntity.setName(name);
		userEntity.setEmail(email);
		userEntity.setActive(true);

		LOG.info("Response data " + userEntity);
		LOG.info("Exit [Utility][activateUser]");
		return userEntity;
	}

	public static Map<String, String> getMapFromTokens(StringTokenizer tokens) {
		LOG.info("Enter [Utility][getMapFromTokens]");
		Map<String, String> userDetails = null;

		userDetails = new HashMap<String, String>();
		while (tokens.hasMoreElements()) {
			String singleToken = tokens.nextElement().toString().trim();

			StringTokenizer tokensa = new StringTokenizer(singleToken, "=");

			while (tokensa.hasMoreElements()) {
				String key = tokensa.nextElement().toString().trim();
				String value = tokensa.nextElement().toString().trim();
				userDetails.put(key, value);
			}

		}

		LOG.info("Response data " + userDetails);
		LOG.info("Exit [Utility][getMapFromTokens]");
		if (userDetails.isEmpty()) {
			return null;
		}
		return userDetails;

	}

	public static Properties loadAllProperties(String file) {
		LOG.info("Enter [Utility][loadAllProperties]");
		Properties prop = null;
		URI fileName = null;
		FileReader fr = null;
		try {

			fileName = ClassLoader.getSystemResource(file).toURI();
			prop = new Properties();
			fr = new FileReader(new File(fileName));
			prop.load(fr);

		} catch (URISyntaxException e) {
			LOG.error("Exception occured  [Utility][loadAllProperties][URISyntaxException] " + e.getMessage());
		} catch (IOException e) {
			LOG.error("Exception occured  [Utility][loadAllProperties][IOException]" + e.getMessage());
		}
		LOG.info("Response data " + prop);
		LOG.info("Exit [Utility][loadAllProperties]");
		return prop;
	}

	public static NoteEntity getNote(String noteName, NoteRepository repository, String email) {

		List<NoteEntity> notes = repository.getByUserId(email);

		for (NoteEntity note : notes) {
			if (note.getTitle().equals(noteName)) {

				System.out.println("We found a note");
				return note;
			}

		}
		return null;

	}

	public static <S, D> D convert(S source, Class<D> destination) {
		return mapper.map(source, destination);
	}

	public static NoteEntity getNoteEntity(List<NoteEntity> noteEntities, String noteId) {

		for (NoteEntity note : noteEntities) {
			System.out.println(String.valueOf(note.getId()) + "  ==  " + noteId);
			if (String.valueOf(note.getId()).equals(noteId)) {
				return note;
			}
		}

		throw new NoteException("note not found");

	}

	public static final <T> String covertToJSONString(T object) {

		try {
			return jacksonMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static <T> T convertFromJSONString(String message, Class<T> class1) {

		try {
			return jacksonMapper.readValue(message, class1);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
