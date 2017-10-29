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

package com.pacobenitezchico.custom_spring_security.listener;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pacobenitezchico.custom_spring_security.exception.CustomException;

/**
 * ------------------------------------------------
 * @author Francisco Manuel Benitez Chico
 * ------------------------------------------------
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint
{
	@Override
	public void commence(final HttpServletRequest request,
						 final HttpServletResponse response,
						 final AuthenticationException authenticationException) throws IOException, ServletException
	{
		final CustomException customException = new CustomException(HttpServletResponse.SC_UNAUTHORIZED, "My custom error from AuthenticationEntryPoint", authenticationException) ;
		customException.setMessage(authenticationException.getMessage()) ;
		customException.setPath(request.getServletPath()) ;
        
		response.setContentType(MediaType.APPLICATION_JSON_VALUE) ;
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED) ;
        
        final ObjectMapper mapper = new ObjectMapper() ;
        mapper.writeValue(response.getOutputStream(), customException.getBodyExceptionMessage()) ;
	}
}
