package br.com.nex2you.api.service;

import java.util.List;
import java.util.Optional;

import br.com.nex2you.api.exception.ItemNotFoundException;
import br.com.nex2you.api.model.Item;

public interface ItemService {

	Item save(Item item);

	List<Item> findAll();

	Optional<Item> findById(String id);

	void delete(String id) throws ItemNotFoundException;

}