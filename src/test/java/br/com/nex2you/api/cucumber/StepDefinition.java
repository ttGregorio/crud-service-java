package br.com.nex2you.api.cucumber;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import org.apache.http.HttpStatus;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.nex2you.api.model.Item;
import br.com.nex2you.api.response.Response;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class StepDefinition {

	@LocalServerPort
	private int port;

	private TestRestTemplate testRestTemplate;

	private String postUrl = "http://localhost:";

	private Item item;

	private String headerId;

	@SuppressWarnings("rawtypes")
	private ResponseEntity response;

	private StepDefinition() {
		testRestTemplate = new TestRestTemplate();
	}

	@Given("^De nome (.*)$")
	public void de_nome(String user) throws Exception {
		item = Item.builder().name(user).build();
	}

	@Given("^Nome (.*), id do objeto (\\d+) e id do header (\\d+)$")
	public void nome_User_id_do_objeto_e_id_do_header(String user, String id, String headerId) throws Exception {
		item = Item.builder().name(user).id(id).build();
		this.headerId = headerId;
	}

	@Given("^De id (\\d+)$")
	public void de_id(String id) throws Exception {
		this.headerId = id;
	}

	@When("^executar o post para o servico$")
	public void executar_o_post_para_o_servico() throws Exception {
		this.response = this.testRestTemplate.postForEntity(
				new URI(postUrl.concat(Integer.toString(port)).concat("/api")), this.item, Response.class);
	}

	@When("^executar o put para o servico$")
	public void executar_o_put_para_o_servico() throws Exception {
		this.response = this.testRestTemplate.exchange(
				postUrl.concat(Integer.toString(port)).concat("/api/").concat(headerId), HttpMethod.PUT,
				new HttpEntity<Item>(this.item), Response.class);
	}

	@When("^executar o get por id para o servico$")
	public void executar_o_get_por_id_para_o_servico() throws Exception {
		this.response = this.testRestTemplate.exchange(
				postUrl.concat(Integer.toString(port)).concat("/api/").concat(headerId), HttpMethod.GET, null,
				Item.class);
	}

	@When("^executar o delete para o servico$")
	public void executar_o_delete_para_o_servico() throws Exception {
		this.response = this.testRestTemplate.exchange(
				postUrl.concat(Integer.toString(port)).concat("/api/").concat(headerId), HttpMethod.DELETE, null,
				Response.class);
	}

	@When("executar o get para o servico")
	public void executar_o_get_para_o_servico() throws Exception {
		this.response = this.testRestTemplate.exchange(postUrl.concat(Integer.toString(port)).concat("/api"),
				HttpMethod.GET, null, Response.class);
	}

	@Then("^Deve retornar o status (\\d+) na requisicao$")
	public void deve_retornar_o_status_na_requisicao(int status) throws Exception {
		switch (status) {
		case HttpStatus.SC_OK:
			assertEquals(HttpStatus.SC_OK, response.getStatusCodeValue());
			break;
		case HttpStatus.SC_BAD_REQUEST:
			assertEquals(HttpStatus.SC_BAD_REQUEST, response.getStatusCodeValue());
			break;
		default:
			break;
		}
	}

}
