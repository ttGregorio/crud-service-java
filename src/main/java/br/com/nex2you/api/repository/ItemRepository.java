package br.com.nex2you.api.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.nex2you.api.model.Item;

public interface ItemRepository extends MongoRepository<Item, String> {

	List<Item> findByActiveTrue();

}
