package br.com.nex2you.api.wrapper;

import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.NoSuchElementException;

@Data
public class ModifyRequestBodyWrapper extends HttpServletRequestWrapper {

    private HttpServletRequest orginalRequest;

    private String modifyRequestBody;

    private String contentType;


    public ModifyRequestBodyWrapper(HttpServletRequest orginalRequest, String modifyRequestBody) {
        this(orginalRequest, modifyRequestBody, null);
    }


    public ModifyRequestBodyWrapper(HttpServletRequest orginalRequest, String modifyRequestBody, String contentType) {
        super(orginalRequest);
        this.modifyRequestBody = modifyRequestBody;
        this.orginalRequest = orginalRequest;
        this.contentType = contentType;
    }


    @Override
    @SneakyThrows
    public ServletInputStream getInputStream() {
        return new ServletInputStream() {
            private InputStream in = new ByteArrayInputStream(modifyRequestBody.getBytes(orginalRequest.getCharacterEncoding()));

            @Override
            public int read() throws IOException {
                return in.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };
    }


    @Override
    @SneakyThrows
    public int getContentLength() {
        return modifyRequestBody.getBytes(orginalRequest.getCharacterEncoding()).length;
    }


    @Override
    @SneakyThrows
    public long getContentLengthLong() {
        return modifyRequestBody.getBytes(orginalRequest.getCharacterEncoding()).length;
    }


    @Override
    public String getContentType() {
        return StringUtils.isBlank(contentType) ? orginalRequest.getContentType() : contentType;
    }


    @Override
    public Enumeration<String> getHeaders(String name) {
        if (null != name && name.replace("-", "").toLowerCase().equals("contenttype") && !StringUtils.isBlank(contentType)) {
            return new Enumeration<String>() {
                private boolean hasGetted = false;

                @Override
                public boolean hasMoreElements() {
                    return !hasGetted;
                }

                @Override
                public String nextElement() {
                    if (hasGetted) {
                        throw new NoSuchElementException();
                    } else {
                        hasGetted = true;
                        return contentType;
                    }
                }
            };
        }
        return super.getHeaders(name);
    }
}