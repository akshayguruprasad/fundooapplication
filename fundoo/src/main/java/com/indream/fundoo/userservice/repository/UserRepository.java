package com.indream.fundoo.userservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.indream.fundoo.userservice.model.UserEntity;

/**
 * user custom repository method
 * 
 * @author Akshay
 *
 */
@Repository("repository")
public interface UserRepository extends MongoRepository<UserEntity, String> {
    UserEntity getByEmail(String email);

}
