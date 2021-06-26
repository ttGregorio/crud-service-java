package br.com.nex2you.api.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nex2you.api.exception.ItemNotFoundException;
import br.com.nex2you.api.model.Item;
import br.com.nex2you.api.response.Response;
import br.com.nex2you.api.service.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api")
@Api(value = "Crud")
public class ItemController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ItemService itemService;

	@ApiOperation(value = "Inclusão de itens")
	@PostMapping
	public ResponseEntity<Response<Item>> create(HttpServletRequest request, @RequestBody Item item,
			BindingResult result) {
		Response<Item> response = new Response<>();
		try {
			logger.info("[{}][create][request: {}][item: {}]", this.getClass().getName(), request.toString(),
					item.toString());
			validateCreateItem(item, result);
			item.setActive(true);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}

			Item partnerPersisted = (Item) itemService.save(item);
			response.setData(partnerPersisted);
			logger.info("[{}][create][response: {}]", this.getClass().getName(), response.toString());

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();
			response.getErrors().add(e.getMessage());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.ok(response);
		}
	}

	@ApiOperation(value = "Atualização de itens")
	@PutMapping(value = "{id}")
	public ResponseEntity<Response<Item>> update(HttpServletRequest request, @RequestBody Item item,
			BindingResult result, @PathVariable String id) {
		logger.info("[{}][update][request: {}][item: {}][id: {}]", this.getClass().getName(), request.toString(),
				item.toString(), id);
		Response<Item> response = new Response<>();
		validateUpdateItem(id, item, result);
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		Item partnerPersisted = (Item) itemService.save(item);
		response.setData(partnerPersisted);
		logger.info("[{}][update][response: {}]", this.getClass().getName(), response.toString());

		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "Consulta de itens por id")
	@GetMapping(value = "{id}")
	public ResponseEntity<Response<Item>> findById(@PathVariable String id) {
		Response<Item> response = new Response<>();
		logger.info("[{}][findById][id: {}]", this.getClass().getName(), id);

		Optional<Item> productType = itemService.findById(id);

		if (productType.isPresent()) {
			response.setData(productType.get());
			logger.info("[{}][findById][response: {}]", this.getClass().getName(), response.toString());
		} else {
			response.getErrors().add("Register not found for id ".concat(id));
			logger.error("[{}][findById][response: {}]", this.getClass().getName(), response.toString());
			return ResponseEntity.badRequest().body(response);
		}

		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "Lista de itens")
	@GetMapping
	public ResponseEntity<Response<List<Item>>> findAll() {
		logger.info("[{}][findAll]", this.getClass().getName());
		Response<List<Item>> response = new Response<>();

		response.setData(itemService.findAll());
		logger.info("[{}][findAll][response: {}]", this.getClass().getName(), response.toString());

		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "Exclusão de itens por id")
	@DeleteMapping(value = "{id}")
	public ResponseEntity<Response<String>> delete(@PathVariable String id) {
		Response<String> response = new Response<>();
		logger.info("[{}][delete][id: {}]", this.getClass().getName(), id);
		try {
			itemService.delete(id);
			response.setData("Payment ".concat(id).concat(" removed."));
			logger.info("[{}][delete][response: {}]", this.getClass().getName(), response.toString());
		} catch (ItemNotFoundException e) {
			response.getErrors().add("Register not found for id ".concat(id));
			logger.error("[{}][delete][response: {}]", this.getClass().getName(), response.toString());
			return ResponseEntity.badRequest().body(response);
		}
		logger.info("[{}][delete][response: {}]", this.getClass().getName(), response.toString());

		return ResponseEntity.ok(response);
	}

	private void validateCreateItem(Item item, BindingResult result) {
		if (item.getName() == null || item.getName().isEmpty()) {
			result.addError(new ObjectError("Item", "nameNotInformed"));
		}
	}

	private void validateUpdateItem(String id, Item item, BindingResult result) {
		if (item.getName() == null || item.getName().isEmpty()) {
			result.addError(new ObjectError("Item", "nameNotInformed"));
		}

		if (item.getId() == null) {
			result.addError(new ObjectError("Item", "idNotInformed"));
		}

		if (!id.equals(item.getId())) {
			result.addError(new ObjectError("Item", "idNotMatch"));
		}

	}
}
