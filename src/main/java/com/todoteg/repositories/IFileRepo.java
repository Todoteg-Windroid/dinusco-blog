package com.todoteg.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.todoteg.models.Image;

@Repository
public interface IFileRepo extends CrudRepository<Image, UUID>{

}
