/**
 * 
 */
package com.indream.fundoo.noteservice.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.indream.fundoo.noteservice.model.LabelEntity;
import com.indream.fundoo.noteservice.model.NoteEntity;

/**
 * @author bridgeit
 *
 */

@Component
public class NoteDaoImpl implements NoteDao {

	private static final ObjectMapper jacksonMapper = new ObjectMapper();
	private final static String NOTE_INDEX = "noteindex";
	private final static String NOTE_TYPE = "notes";
	private final static String LABEL_INDEX = "labelindex";
	private final static String LABEL_TYPE = "labels";
	private static final String USER_ID = null;
	@Autowired
	private RestHighLevelClient client;

	/*
	 * @purpose
	 *
	 *
	 * @author akshay
	 * 
	 * @com.indream.fundoo.noteservice.dao
	 * 
	 * @since 04-Aug-2018
	 *
	 */
	@Override
	public String saveElasticNote(NoteEntity noteEntity) throws IOException {

		@SuppressWarnings("unchecked")
		Map<String, Object> value = jacksonMapper.convertValue(noteEntity, Map.class);
		IndexRequest indexRequest = new IndexRequest(NOTE_INDEX, NOTE_TYPE, noteEntity.getId());
		String id = null;
		indexRequest.source(value, XContentType.JSON);
		IndexResponse response;

		response = client.index(indexRequest);
		id = response.getId();

		return id;
	}

	/*
	 * @purpose
	 *
	 *
	 * @author akshay
	 * 
	 * @com.indream.fundoo.noteservice.dao
	 * 
	 * @since 04-Aug-2018
	 *
	 */
	@Override
	public String updateElasticNote(NoteEntity noteEntity) throws IOException {

		UpdateRequest updateRequest = new UpdateRequest(NOTE_INDEX, NOTE_TYPE, noteEntity.getId());

		Map<String, Object> source = jacksonMapper.convertValue(noteEntity, Map.class);

		updateRequest.doc(source, XContentType.JSON);

		UpdateResponse response = client.update(updateRequest);

		return response.getId();

	}

	/*
	 * @purpose
	 *
	 *
	 * @author akshay
	 * 
	 * @com.indream.fundoo.noteservice.dao
	 * 
	 * @since 04-Aug-2018
	 *
	 */
	@Override
	public String deleteElasticNote(String noteId) throws IOException {

		DeleteRequest deleteRequest = new DeleteRequest(NOTE_INDEX, NOTE_TYPE, noteId);

		client.delete(deleteRequest);

		return "SUCCESS";

	}

	/*
	 * @purpose
	 *
	 *
	 * @author akshay
	 * 
	 * @com.indream.fundoo.noteservice.dao
	 * 
	 * @since 04-Aug-2018
	 *
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String saveElasticLabel(LabelEntity labelEntity) throws IOException {
		Map<String, Object> value = jacksonMapper.convertValue(labelEntity, Map.class);
		IndexRequest indexRequest = new IndexRequest(LABEL_INDEX, LABEL_TYPE, labelEntity.getId());
		String id = null;
		indexRequest.source(value, XContentType.JSON);
		IndexResponse response;
		response = client.index(indexRequest);
		id = response.getId();
		return id;
	}

	/*
	 * @purpose
	 *
	 *
	 * @author akshay
	 * 
	 * @com.indream.fundoo.noteservice.dao
	 * 
	 * @since 04-Aug-2018
	 *
	 */
	@Override
	public String updateElasticLabel(LabelEntity labelEntity) throws IOException {
		UpdateRequest updateRequest = new UpdateRequest(LABEL_INDEX, LABEL_TYPE, labelEntity.getId());
		Map<String, Object> value = jacksonMapper.convertValue(labelEntity, Map.class);
		updateRequest.doc(value, XContentType.JSON);
		UpdateResponse response = client.update(updateRequest);
		return response.getId();
	}

	/*
	 * @purpose
	 *
	 *
	 * @author akshay
	 * 
	 * @com.indream.fundoo.noteservice.dao
	 * 
	 * @since 04-Aug-2018
	 *
	 */
	@Override
	public String deleteElasticLabel(String labelId) throws IOException {
		DeleteRequest deleteRequest = new DeleteRequest(LABEL_INDEX, LABEL_TYPE, labelId);
		client.delete(deleteRequest);
		return "SUCCESS";
	}

	/*
	 * @purpose
	 *
	 *
	 * @author akshay
	 * 
	 * @com.indream.fundoo.noteservice.dao
	 * 
	 * @since 04-Aug-2018
	 *
	 */
	@Override
	public List<?> searchElasticLabel(String userId) throws IOException {

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchQuery(USER_ID, userId));
		String[] indices = { LABEL_INDEX };
		SearchRequest searchRequest = new SearchRequest(indices, searchSourceBuilder);
		SearchResponse response = client.search(searchRequest);
		SearchHit[] responseArray = response.getHits().getHits();
		List<LabelEntity> labelList = new ArrayList<LabelEntity>();
		for (SearchHit searchHit : responseArray) {

			LabelEntity labelEntity = jacksonMapper.convertValue(searchHit.getSourceAsMap(), LabelEntity.class);
			labelList.add(labelEntity);

		}

		return labelList;
	}

	private <T> List<?> getEntities(T[] data, Class<?> classType) {

		return jacksonMapper.convertValue(data,
				jacksonMapper.getTypeFactory().constructCollectionType(List.class, classType));

	}

	/*
	 * @purpose
	 *
	 *
	 * @author akshay
	 * 
	 * @com.indream.fundoo.noteservice.dao
	 * 
	 * @since 04-Aug-2018
	 *
	 */
	@Override
	public List<?> searchElasticNote(String userId) throws IOException {

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchQuery(USER_ID, userId));
		String[] indices = { NOTE_INDEX };
		SearchRequest searchRequest = new SearchRequest(indices, searchSourceBuilder);
		SearchResponse response = client.search(searchRequest);
		List<NoteEntity> noteList = new ArrayList<NoteEntity>();
		for (SearchHit searchHit : response.getHits().getHits()) {
			NoteEntity noteEntity = jacksonMapper.convertValue(searchHit.getSourceAsMap(), NoteEntity.class);
			noteList.add(noteEntity);
		}
		return noteList;
	}

	/*
	 * @purpose
	 *
	 *
	 * @author akshay
	 * 
	 * @com.indream.fundoo.noteservice.dao
	 * 
	 * @since 04-Aug-2018
	 *
	 */
	@Override
	public LabelEntity getElasticLabelEntity(String key, String value) throws IOException {
		String[] indices = { LABEL_INDEX };
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchQuery(key, value));
		SearchRequest searchRequest = new SearchRequest(indices, searchSourceBuilder);
		SearchResponse response = client.search(searchRequest);
		SearchHit[] searches = response.getHits().getHits();
		return jacksonMapper.convertValue(searches[0].getSourceAsMap(), LabelEntity.class);
	}

	/*
	 * @purpose
	 *
	 *
	 * @author akshay
	 * 
	 * @com.indream.fundoo.noteservice.dao
	 * 
	 * @since 04-Aug-2018
	 *
	 */
	@Override
	public NoteEntity getElasticNoteEntity(String key, String value) throws IOException {
		String[] indices = { NOTE_INDEX };
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchQuery(key, value));
		SearchRequest searchRequest = new SearchRequest(indices, searchSourceBuilder);
		SearchResponse response = client.search(searchRequest);
		SearchHit[] searches = response.getHits().getHits();
		System.out.println("till here its clean ");
		return jacksonMapper.convertValue(searches[0].getSourceAsMap(), NoteEntity.class);
	}

}
