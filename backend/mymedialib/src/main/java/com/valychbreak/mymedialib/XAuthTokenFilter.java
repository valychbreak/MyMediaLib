package com.valychbreak.mymedialib;

import com.valychbreak.mymedialib.services.AuthenticationService;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

/**
 * Created by valych on 4/1/17.
 */
public class XAuthTokenFilter extends GenericFilterBean {

    private AuthenticationService authenticationService;

    public XAuthTokenFilter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (/*!request.getRequestURI().contains("/api") || */request.getRequestURI().contains("/signin")){
            filterChain.doFilter(request, response);
        } else {

            //try {

                /*Cookie jwtCookie = findJwtCookie(request);
                Assert.notNull(jwtCookie,"No jwt cookie found");

                String jwt = jwtCookie.getValue();
                String login = HmacSigner.getJwtClaim(jwt, AuthenticationService.JWT_CLAIM_LOGIN);
                Assert.notNull(login,"No login found in JWT");

                //Get user from cache
                UserDTO userDTO = MockUsers.findByUsername(login);
                Assert.notNull(userDTO,"No user found with login: "+login);

                Assert.isTrue(HmacSigner.verifyJWT(jwt,userDTO.getPrivateSecret()),"The Json Web Token is invalid");

                Assert.isTrue(!HmacSigner.isJwtExpired(jwt),"The Json Web Token is expired");

                String csrfHeader = request.getHeader(AuthenticationService.CSRF_CLAIM_HEADER);
                Assert.notNull(csrfHeader,"No csrf header found");

                String jwtCsrf = HmacSigner.getJwtClaim(jwt, AuthenticationService.CSRF_CLAIM_HEADER);
                Assert.notNull(jwtCsrf,"No csrf claim found in jwt");

                //Check csrf token (prevent csrf attack)
                Assert.isTrue(jwtCsrf.equals(csrfHeader));

                this.authenticationService.tokenAuthentication(login);*/

                Cookie cookie = findJwtCookie(request);
                String username = cookie.getValue();

            this.authenticationService.tokenAuthentication(username);

            filterChain.doFilter(request, response);
            /*} catch (*//*HmacException | ParseException e*//*) {
                e.printStackTrace();
                response.setStatus(403);
            }*/
        }
    }

    private Cookie findJwtCookie(HttpServletRequest request) {
        if(request.getCookies() == null || request.getCookies().length == 0) {
            return null;
        }
        for(Cookie cookie : request.getCookies()) {
            if(cookie.getName().contains("MyCookie")) {
                return cookie;
            }
        }
        return null;
    }
}
