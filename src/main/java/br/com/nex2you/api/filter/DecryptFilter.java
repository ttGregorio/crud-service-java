package br.com.nex2you.api.filter;

import java.io.IOException;
import java.util.function.Function;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import br.com.nex2you.api.utils.HttpUtil;
import jodd.util.Base64;

@WebFilter(urlPatterns = { "/api/*" }, filterName = "decryptFilter")
public class DecryptFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Function<String, String> modifyRequestBodyFun = Base64::decodeToString;
		Function<String, String> modifyResponseBodyFun = Base64::encodeToString;
		HttpUtil.modifyHttpData(request, response, chain, modifyRequestBodyFun, modifyResponseBodyFun);
	}
}