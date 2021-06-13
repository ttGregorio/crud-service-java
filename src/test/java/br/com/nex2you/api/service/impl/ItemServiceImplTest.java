package br.com.nex2you.api.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.nex2you.api.exception.ItemNotFoundException;
import br.com.nex2you.api.model.Item;
import br.com.nex2you.api.repository.ItemRepository;

@RunWith(MockitoJUnitRunner.class)
class ItemServiceImplTest {

	@Mock
	private ItemRepository itemRepository;

	@Autowired
	protected MockMvc mockMvc;

	@InjectMocks
	private ItemServiceImpl itemServiceImpl;

	@BeforeEach
	public void contextLoads() throws JsonParseException, JsonMappingException, IOException {
		MockitoAnnotations.openMocks(this);
		when(itemRepository.save(getItemToCreate())).thenReturn(getItemCreated());
		when(itemRepository.findById("1")).thenReturn(Optional.of(getItemCreated()));
		when(itemRepository.save(getItemCreated())).thenReturn(getItemCreated());
		when(itemRepository.findByActiveTrue()).thenReturn(getItemList());
	}

	@Test
	void testSave() {
		assertEquals(getItemCreated(), itemServiceImpl.save(getItemToCreate()));
	}

	@Test
	void testFindAll() {
		assertEquals(getItemList(), itemServiceImpl.findAll());
	}

	@Test
	void testFindById() {
		assertEquals(Optional.of(getItemCreated()), itemServiceImpl.findById("1"));
	}

	@Test
	void testDeleteOk() throws ItemNotFoundException {
		itemServiceImpl.delete("1");
	}

	@Test
	void testDeleteNotFound() {
		try {
			itemServiceImpl.delete("2");
		} catch (ItemNotFoundException e) {
			assertEquals("item not found", e.getMessage());
		}
	}

	private List<Item> getItemList() {
		List<Item> response = new ArrayList();

		for (int i = 0; i < 10; i++) {
			response.add(getItemCreated());
		}
		return response;
	}

	private Item getItemCreated() {
		return Item.builder().active(true).id("1").name("name").build();
	}

	private Item getItemToCreate() {
		return Item.builder().active(true).name("name").build();
	}
}
