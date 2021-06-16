package br.com.nex2you.api.model;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Document
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@ApiModelProperty(notes = "Id do item")
	private String id;

	@ApiModelProperty(notes = "Nome do item", required = true)
	@NotEmpty
	private String name;

	@ApiModelProperty(notes = "Flag indicando se o item está ativo")
	private boolean active;
}
