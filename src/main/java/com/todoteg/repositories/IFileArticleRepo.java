package com.todoteg.repositories;

import org.springframework.data.repository.CrudRepository;

import com.todoteg.models.ImageArticle;

public interface IFileArticleRepo extends CrudRepository<ImageArticle, Integer> {

}
