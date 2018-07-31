/**
 * 
 */
package com.indream.fundoo.noteservice.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.indream.fundoo.noteservice.model.NoteEntity;

/**
 * @author bridgeit
 *
 */
@Repository("noteSearch")
public interface NoteElasticRepository extends ElasticsearchRepository<NoteEntity, String>{

}
