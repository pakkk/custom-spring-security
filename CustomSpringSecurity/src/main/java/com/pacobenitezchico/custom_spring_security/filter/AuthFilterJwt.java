/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.pacobenitezchico.custom_spring_security.filter ;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pacobenitezchico.custom_spring_security.model.AuthModelJwt;
import com.pacobenitezchico.custom_spring_security.util.Constants;

/**
 * ------------------------------------------------
 * @author Francisco Manuel Benitez Chico
 * ------------------------------------------------
 */
@Component
public class AuthFilterJwt implements Filter
{
    /**
     * Init method
     * 
     * @param filterConfig with the filter configuration
     * @throws ServletException with an occurred exception
     */
	@Override
    public void init(final FilterConfig filterConfig) throws ServletException
    {
		// Empty method
    }

	/**
	 * Do filter method
	 * 
	 * @param servletRequest  with the Servlet request
	 * @param servletResponse with the Servlet response
	 * @param filterChain	  with the filter chain
	 * @throws IOException 	    with an occurred exception
	 * @throws ServletException with an occurred exception
	 */
	@Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException
    {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest ;
        
        // Get the authorization header
        final String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION) ;
        
        if (authorizationHeader != null)
        {
        	// Replace the STRING_BEARER with STRING_EMPTY
        	final String tokenJwt   = authorizationHeader.substring(Constants.STRING_BEARER.length()) ;
        	
        	final DecodedJWT decodedJwt = JWT.decode(tokenJwt) ;
        	
        	// Check the model parameters
        	final boolean fieldsFilled = this.fieldsFilled(decodedJwt) ;
    		if (fieldsFilled)
    		{
	    		// Generate a new instance of AuthModelJwt
	    		final AuthModelJwt authModelJwt = new AuthModelJwt(decodedJwt.getSubject(),
	    														   decodedJwt.getClaim(Constants.COMPULSORY_CUSTOM_FIELD_JWT).asString(),
	    														   decodedJwt.getClaim(Constants.COMPULSORY_CUSTOM_SCOPES_JWT).asList(String.class)) ;
	    		
	        	// Set the Authentication model to the context
	            SecurityContextHolder.getContext().setAuthentication(authModelJwt) ;
    		}
        }
        
        filterChain.doFilter(servletRequest, servletResponse) ;
    }
	
	/**
	 * @param decodedJwt with the decoded JWT
	 * @return true if all the fields were filled
	 */
	private boolean fieldsFilled(final DecodedJWT decodedJwt)
	{
		boolean fieldsFilled = decodedJwt.getSubject() != null && !decodedJwt.getSubject().isEmpty() ;
		
		if (fieldsFilled)
		{
			fieldsFilled = decodedJwt.getClaim(Constants.COMPULSORY_CUSTOM_FIELD_JWT) != null &&
						   decodedJwt.getClaim(Constants.COMPULSORY_CUSTOM_FIELD_JWT).asString() != null &&
						  !decodedJwt.getClaim(Constants.COMPULSORY_CUSTOM_FIELD_JWT).asString().isEmpty() ;
			if (fieldsFilled)
			{
				fieldsFilled = decodedJwt.getClaim(Constants.COMPULSORY_CUSTOM_SCOPES_JWT) != null &&
							   decodedJwt.getClaim(Constants.COMPULSORY_CUSTOM_SCOPES_JWT).asList(String.class) != null &&
							  !decodedJwt.getClaim(Constants.COMPULSORY_CUSTOM_SCOPES_JWT).asList(String.class).isEmpty() ;
			}
		}
		
		return fieldsFilled ;
	}
	
    /**
     * Destroy method
     */
    @Override
    public void destroy()
    {
		// Empty method
    }
}
