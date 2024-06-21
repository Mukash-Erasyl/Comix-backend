package com.Jabai.WebShop.repository;

import com.Jabai.WebShop.domain.Comix;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComixRepository extends MongoRepository<Comix, String> {
    @Query("{'$or': [{'title': {$regex: ?0, $options: 'i'}}, {'genre': {$regex: ?0, $options: 'i'}}, {'tags': {$regex: ?0, $options: 'i'}}]}")
    List<Comix> findByTitleOrGenreOrTagsContaining(String keyword);
}