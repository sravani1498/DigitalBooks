package com.digitalbboks.test.security.jwt;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import lombok.val;

import com.digitalbooks.demo.security.jwt.AuthTokenFilter;
import com.digitalbooks.demo.security.jwt.JwtUtils;
import com.digitalbooks.demo.security.services.UserDetailsServiceImpl;

import org.springframework.security.core.userdetails.UserDetails;

@RunWith(MockitoJUnitRunner.class)
public class JWTAuthenticationTokenFilterTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;
    
    @Mock
    private JwtUtils jwtUtils;
    
    @Mock
	private UserDetailsServiceImpl userDetailsService;

    @Mock
    private AuthenticationManager authenticationManager;
    
    @InjectMocks
    private AuthTokenFilter uut;

    @Before
    public void init() {
        uut = new AuthTokenFilter();
    }

    @Test(expected = NullPointerException.class)
    public void throwsWithoutToken() throws Exception {

    	AuthTokenFilter spy = Mockito.spy(uut);

        spy.doFilter(request, response, chain);
		String username = "sravani";
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				userDetails, null, userDetails.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		uut.doFilter(request, response, chain);
    }

//    @Test
//    public void returnsWrappedToken() {
//        String tokenString = "<token>";
//        when(request.getHeader("authorization")).thenReturn("Bearer " + tokenString);
//        uut.setAuthenticationManager(authenticationManager);
//        val authentication = mock(Authentication.class);
//        when(authenticationManager.authenticate(new JWTAuthenticationToken(tokenString)))
//                .thenReturn(authentication);
//
//        val result = uut.attemptAuthentication(request, response);
//
//        assertThat(result).isEqualTo(authentication);
//    }

//    @Test
//    public void dontAttemptAuthenticationWithoutTokenWithAnonymousPath() throws Exception {
//
//        uut.addUnauthenticatedPaths(Collections.singleton("/api"));
//        AuthTokenFilter spy = Mockito.spy(uut);
//        when(request.getServletPath()).thenReturn("/api");
//
//        spy.doFilter(request, response, chain);
//
////        verify(spy, never()).attemptAuthentication(request, response);
//
//    }

//    @Test
//    public void dontAttemptAuthenticationWithoutTokenWithAnonymousPathWildcard() throws Exception {
//
//        uut.addUnauthenticatedPaths(Collections.singleton("/api/*"));
//        JWTAuthenticationTokenFilter spy = Mockito.spy(uut);
//        when(request.getServletPath()).thenReturn("/api/foo");
//
//        spy.doFilter(request, response, chain);
//
//        verify(spy, never()).attemptAuthentication(request, response);
//
//    }
//
//    @Test
//    public void callsFilterChainWithoutTokenWithAnonymousPath() throws Exception {
//
//        uut.addUnauthenticatedPaths(Collections.singleton("/api"));
//        when(request.getServletPath()).thenReturn("/api");
//
//        uut.doFilter(request, response, chain);
//
//        verify(chain).doFilter(request, response);
//
//    }
//
//    @Test
//    public void callsFilterChainWithoutTokenWithoutAnonymousPath() throws Exception {
//
//        final AuthenticationFailureHandler mockAuthenticationFailureHandler = mock(AuthenticationFailureHandler.class);
//        doNothing().when(mockAuthenticationFailureHandler).onAuthenticationFailure(any(HttpServletRequest.class),
//                any(HttpServletResponse.class), any(AuthenticationException.class));
//        uut.setAuthenticationFailureHandler(mockAuthenticationFailureHandler);
//
//        uut.doFilter(request, response, chain);
//
//        verify(chain, never()).doFilter(request, response);
//        verify(mockAuthenticationFailureHandler).onAuthenticationFailure(any(HttpServletRequest.class),
//                        any(HttpServletResponse.class), any(AuthenticationException.class));
//    }
//
//    @Test
//    public void throwsWithoutTokenInSubdirectoryOfAnonymousPath() throws Exception {
//
//        uut.addUnauthenticatedPaths(Collections.singleton("/api"));
//        JWTAuthenticationTokenFilter spy = Mockito.spy(uut);
//        when(request.getServletPath()).thenReturn("/api/foo");
//
//        spy.doFilter(request, response, chain);
//
//        verify(spy, never()).attemptAuthentication(request, response);
//        verify(spy, never()).successfulAuthentication(eq(request), eq(response), eq(chain), any());
//
//    }
//
//    @Test
//    public void callsFilterChainIfSuccessfulAuthentication() throws Exception {
//        val authentication = mock(Authentication.class);
//        uut.successfulAuthentication(request, response, chain, authentication);
//
//        verify(chain).doFilter(request, response);
//    }
//
//    @Test
//    public void returnNullWithoutToken() {
//        Authentication result = uut.attemptAuthentication(request, response);
//
//        assertThat(result).isNull();
//    }

}