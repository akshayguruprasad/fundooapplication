/**
 * 
 */
package com.indream.fundoo.noteservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.indream.fundoo.noteservice.model.LabelEntity;

/**
 * @author Akshay
 *
 */
@Repository
public interface LabelCustomRepository extends MongoRepository<LabelEntity, String> {

    LabelEntity findByLabelName(String labelName);
    
}
