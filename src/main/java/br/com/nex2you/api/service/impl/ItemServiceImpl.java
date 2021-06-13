package br.com.nex2you.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nex2you.api.exception.ItemNotFoundException;
import br.com.nex2you.api.model.Item;
import br.com.nex2you.api.repository.ItemRepository;
import br.com.nex2you.api.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ItemRepository itemRepository;

	@Override
	public Item save(Item item) {
		LOGGER.info("[{}][save][item: {}]", this.getClass().getName(), item);

		return itemRepository.save(item);
	}

	@Override
	public List<Item> findAll() {
		LOGGER.info("[{}][findAll]", this.getClass().getName());

		return itemRepository.findByActiveTrue();
	}

	@Override
	public Optional<Item> findById(String id) {
		LOGGER.info("[{}][findById][id: {}]", this.getClass().getName(), id);

		return itemRepository.findById(id);
	}

	@Override
	public void delete(String id) throws ItemNotFoundException {
		LOGGER.info("[{}][delete][id: {}]", this.getClass().getName(), id);

		Optional<Item> optionalItem = findById(id);

		if (optionalItem.isPresent()) {
			LOGGER.debug("[{}][delete][optionalItem: {}]", this.getClass().getName(), optionalItem.get());
			optionalItem.get().setActive(false);
			save(optionalItem.get());
		} else {
			LOGGER.debug("[{}][delete][notFound]", this.getClass().getName());
			throw new ItemNotFoundException("item");
		}
	}

}
