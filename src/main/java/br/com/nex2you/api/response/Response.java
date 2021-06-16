package br.com.nex2you.api.response;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class Response<T> {

	@ApiModelProperty(notes = "Dado a ser retornado")
	private T data;

	@ApiModelProperty(notes = "Lista de erros encontrados")
	private List<String> errors;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<String> getErrors() {
		if (this.errors == null) {
			this.errors = new ArrayList<String>();
		}
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	@Override
	public String toString() {
		return "Response [data=" + data + ", errors=" + errors + "]";
	}

}
