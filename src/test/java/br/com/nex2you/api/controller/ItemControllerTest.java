package br.com.nex2you.api.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;

import br.com.nex2you.api.exception.ItemNotFoundException;
import br.com.nex2you.api.model.Item;
import br.com.nex2you.api.service.ItemService;

@RunWith(SpringRunner.class)
@WebMvcTest(ItemController.class)
public class ItemControllerTest {

	@Autowired
	protected MockMvc mockMvc;

	@MockBean
	private ItemService itemService;

	@Before
	public void contextLoads() throws JsonParseException, JsonMappingException, IOException, ItemNotFoundException {
		when(itemService.save(getItemToCreate(true))).thenReturn(getItemCreated());
		//when(itemService.save(Item.builder().name("item 2").build())).thenThrow(Exception.class);
		when(itemService.findById("id")).thenReturn(Optional.of(getItemCreated()));
		when(itemService.findAll()).thenReturn(getItensList());
		doThrow(ItemNotFoundException.class).when(itemService).delete("id2");
	}

	@Test
	public void testCreate() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "123123123");
		try {
			MvcResult result = this.mockMvc
					.perform(post("/api").headers(headers).content(new Gson().toJson(getItemToCreate(true)))
							.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk()).andReturn();
			assertNotNull(result.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testCreateException() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "123123123");
		try {
			MvcResult result = this.mockMvc
					.perform(
							post("/api").headers(headers).content(new Gson().toJson(Item.builder().name("item 2").build()))
									.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk()).andReturn();
			assertNotNull(result.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testCreateWithoutName() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "123123123");
		try {
			this.mockMvc
					.perform(post("/api").headers(headers).content(new Gson().toJson(getItemToCreate(false)))
							.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isBadRequest()).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testUpdate() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "123123123");
		try {
			MvcResult result = this.mockMvc
					.perform(put("/apiid").headers(headers).content(new Gson().toJson(getItemCreated()))
							.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk()).andReturn();
			assertNotNull(result.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testUpdateWithoutName() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "123123123");
		try {
			this.mockMvc
					.perform(put("/apiid").headers(headers).content(new Gson().toJson(getItemToCreate(false)))
							.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isBadRequest()).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testUpdateDifferentId() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "123123123");
		try {
			this.mockMvc
					.perform(put("/apiid2").headers(headers).content(new Gson().toJson(getItemCreated()))
							.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isBadRequest()).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testUpdateWithoutId() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "123123123");
		try {
			this.mockMvc
					.perform(put("/apiid").headers(headers).content(new Gson().toJson(getItemToCreate(false)))
							.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isBadRequest()).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testFindById() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "123123123");
		try {
			MvcResult result = this.mockMvc.perform(get("/apiid").headers(headers).contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
			assertNotNull(result.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testFindByIdNotExists() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "123123123");
		try {
			MvcResult result = this.mockMvc.perform(get("/apiid2").headers(headers).contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andReturn();
			assertNotNull(result.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testFindAll() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "123123123");
		try {
			MvcResult result = this.mockMvc.perform(get("/api").headers(headers).contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
			assertNotNull(result.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testDelete() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "123123123");
		try {
			MvcResult result = this.mockMvc.perform(delete("/apiid").headers(headers)
					.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk()).andReturn();
			assertNotNull(result.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testDeleteIdNotFound() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "123123123");
		try {
			MvcResult result = this.mockMvc.perform(delete("/apiid2").headers(headers)
					.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isBadRequest()).andReturn();
			assertNotNull(result.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	private Item getItemToCreate(boolean hasName) {
		return Item.builder().name(hasName ? "name" : null).build();
	}

	private Item getItemCreated() {
		return Item.builder().id("id").name("name").active(true).build();
	}

	private List<Item> getItensList() {
		List<Item> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			list.add(Item.builder().id("id").name("name").active(true).build());
		}

		return list;
	}

}
