package br.com.nex2you.api.utils;

import java.io.IOException;
import java.util.function.Function;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.nex2you.api.wrapper.ModifyRequestBodyWrapper;
import br.com.nex2you.api.wrapper.ModifyResponseBodyWrapper;
import jodd.servlet.ServletUtil;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpUtil {

	public static void modifyHttpData(ServletRequest originalRequest, ServletResponse originalResponse,
			FilterChain chain, Function<String, String> modifyRequestBodyFun,
			Function<String, String> modifyResponseBodyFun) throws IOException, ServletException {
		modifyHttpData(originalRequest, originalResponse, chain, modifyRequestBodyFun, modifyResponseBodyFun, null);
	}

	public static void modifyHttpData(ServletRequest request, ServletResponse response, FilterChain chain,
			Function<String, String> modifyRequestBodyFun, Function<String, String> modifyResponseBodyFun,
			String requestContentType) throws IOException, ServletException {

		HttpServletRequest originalRequest = (HttpServletRequest) request;
		HttpServletResponse originalResponse = (HttpServletResponse) response;

		String originalRequestBody = ServletUtil.readRequestBody(originalRequest); 
//		String modifyRequestBody = "{ \"name\":\"teste\"}";// modifyRequestBodyFun.apply(originalRequestBody);
		String modifyRequestBody =  modifyRequestBodyFun.apply(originalRequestBody);
		ModifyRequestBodyWrapper requestWrapper = modifyRequestBodyAndContentType(originalRequest, modifyRequestBody,
				requestContentType);

		ModifyResponseBodyWrapper responseWrapper = getHttpResponseWrapper(originalResponse);
		chain.doFilter(requestWrapper, responseWrapper);
		String originalResponseBody = responseWrapper.getResponseBody();
		String modifyResponseBody = modifyResponseBodyFun.apply(originalResponseBody);

		originalResponse.setContentType(requestWrapper.getOrginalRequest().getContentType());
		byte[] responseData = modifyResponseBody.getBytes(responseWrapper.getCharacterEncoding());
		originalResponse.setContentLength(responseData.length);
		@Cleanup
		ServletOutputStream out = originalResponse.getOutputStream();
		out.write(responseData);
	}

	public static ModifyRequestBodyWrapper modifyRequestBody(ServletRequest request, String modifyRequestBody) {
		return modifyRequestBodyAndContentType(request, modifyRequestBody, null);
	}

	public static ModifyRequestBodyWrapper modifyRequestBodyAndContentType(ServletRequest request,
			String modifyRequestBody, String contentType) {
		log.debug("ContentType改为 -> {}", contentType);
		HttpServletRequest orginalRequest = (HttpServletRequest) request;
		return new ModifyRequestBodyWrapper(orginalRequest, modifyRequestBody, contentType);
	}

	public static ModifyResponseBodyWrapper getHttpResponseWrapper(ServletResponse response) {
		HttpServletResponse originalResponse = (HttpServletResponse) response;
		return new ModifyResponseBodyWrapper(originalResponse);
	}
}