/**
 * 
 */
package com.indream.fundoo.noteservice.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.indream.fundoo.noteservice.model.LabelEntity;

/**
 * @author bridgeit
 *
 */
public interface LabelElasticRepository extends ElasticsearchRepository<LabelEntity, String>{

}
