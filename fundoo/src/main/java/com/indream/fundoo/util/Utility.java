package com.indream.fundoo.util;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.indream.fundoo.noteservice.model.NoteEntity;

/**
 * UTILITY CLASS
 * 
 * @author Akshay
 *
 */
public class Utility {
    final static Logger LOG = Logger.getLogger(Utility.class);// LOGGER
    static final ObjectMapper jacksonMapper = new ObjectMapper();// JACKSON OBJECT MAPPER
    static final ModelMapper mapper = new ModelMapper();// MODEL MAPPER

    /*
     * @purpose MODEL MAPPER USED TO CONVERT BETWEEN THE OBJECTS
     *
     * @author akshay
     * 
     * @com.indream.fundoo.util
     * 
     * @since Jul 24, 2018
     *
     */
    public static <S, D> D convert(S source, Class<D> destination) {
	return mapper.map(source, destination);
    }

    /*
     * @purpose
     * FIND THE NOTE BY ID
     *
     * @author akshay
     * 
     * @com.indream.fundoo.util
     * 
     * @since Jul 24, 2018
     *
     */
    public static NoteEntity getNoteEntity(List<NoteEntity> noteEntities, String noteId) {
	return noteEntities.stream().filter(p -> p.getId().toString().equals(noteId)).findFirst().get();

    }

    /*
     * @purpose FROM OBJ TO JSON
     *
     * @author akshay
     * 
     * @com.indream.fundoo.util
     * 
     * @since Jul 24, 2018
     *
     */
    public static final <T> String covertToJSONString(T object) {

	try {
	    return jacksonMapper.writeValueAsString(object);
	} catch (JsonProcessingException e) {
	    throw new RuntimeException(e.getMessage());
	}
    }

    /*
     * @purpose FROM JSON TO ENTITY
     *
     * @author akshay
     * 
     * @com.indream.fundoo.util
     * 
     * @since Jul 24, 2018
     *
     */
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
