package altimetrix.filters;

import java.io.IOException;
import java.security.SignatureException;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import altemetrix.constants.RestConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JWTFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;
		final String jwtHeader = request.getHeader(RestConstants.JWT_HEADER);
		final String ignoreAuthHeader = request.getHeader(RestConstants.IGNORE_AUTH_HEADER);

		String path = request.getRequestURI();
		if(path.contains("/login")) {
			request.getRequestDispatcher("/login").forward(request, response);
		}
		
		if ("OPTIONS".equals(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
			chain.doFilter(req, res);
		} else {
			if (!"true".equals(ignoreAuthHeader)) {
				if (jwtHeader == null) {
					throw new ServletException("Missing Authorization Header");
				}
				Claims claims = Jwts.parser().setSigningKey(RestConstants.SIGNING_KEY).parseClaimsJws(jwtHeader)
						.getBody();
				request.setAttribute("claims", claims);
				chain.doFilter(req, res);
			} else {
				throw new ServletException("Please login with user name/password or provide "
						+ RestConstants.IGNORE_AUTH_HEADER + "in request header");
			}
		}
	}
}
