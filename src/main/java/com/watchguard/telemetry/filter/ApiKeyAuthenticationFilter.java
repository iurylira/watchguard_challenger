package com.watchguard.telemetry.filter;

import com.watchguard.telemetry.security.ApiKeyAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class ApiKeyAuthenticationFilter extends GenericFilterBean {
	private static final String API_KEY_HEADER_NAME = "X-WG-Telemetry-Key";
	private static final String API_KEY = "ABC123"; //In the future we should externalize the key

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		final var requestKey = ((HttpServletRequest) request).getHeader(API_KEY_HEADER_NAME);

		if (StringUtils.isNotEmpty(requestKey) && StringUtils.equals(API_KEY, requestKey)) {
			SecurityContextHolder.getContext().setAuthentication(new ApiKeyAuthentication(API_KEY, AuthorityUtils.NO_AUTHORITIES));
		} else {
			((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		filterChain.doFilter(request, response);
	}
}