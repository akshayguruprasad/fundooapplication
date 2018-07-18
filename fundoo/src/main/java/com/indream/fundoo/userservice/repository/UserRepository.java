package com.indream.fundoo.userservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.indream.fundoo.userservice.model.UserEntity;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
UserEntity getByEmail(String email);

}
