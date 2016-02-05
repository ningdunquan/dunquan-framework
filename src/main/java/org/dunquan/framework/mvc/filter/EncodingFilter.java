package org.dunquan.framework.mvc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.dunquan.framework.mvc.constant.MvcConstant;

@WebFilter("/*")
public class EncodingFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		request.setCharacterEncoding(MvcConstant.UTF_8);
		filterChain.doFilter(request, response);
		response.setCharacterEncoding(MvcConstant.UTF_8);

	}

	public void init(FilterConfig arg0) throws ServletException {
	}

}
