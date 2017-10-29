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

package com.pacobenitezchico.custom_spring_security;

import java.io.UnsupportedEncodingException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.pacobenitezchico.custom_spring_security.util.Constants;

/**
 * ------------------------------------------------
 * @author Francisco Manuel Benitez Chico
 * ------------------------------------------------
 */
public class JwtGenerator
{
	/** Attribute - Algorithm */
	private final Algorithm algorithm ;
	
	/**
	 * Private constructor
	 * 
	 * @throws IllegalArgumentException     with an occurred exception
	 * @throws UnsupportedEncodingException with an occurred exception
	 */
	private JwtGenerator() throws IllegalArgumentException, UnsupportedEncodingException
	{
		this.algorithm = Algorithm.HMAC256("sampleSecret") ;
	}
	
	/**
	 * Generate JWTs
	 */
	private void generateJwts()
	{
		// Generate a valid JWT
		this.generateValidJwt() ;
		
		// Generate a invalid JWT values
		this.generateInvalidJwtValues() ;
	}
	
	/**
	 * Generate a valid JWT values
	 */
	private void generateValidJwt()
	{
		final String subject 	 	= "mySubjectName" ;
		final String customField 	= "validCustomFieldValue" ;
		final String[] customScopes = new String[] { "validScopeValue", "anotherScope" } ;
		
		System.out.println("This is a valid JWT: \n") ;
		System.out.println(this.jwtCall(subject, customField, customScopes)) ;
	}

	/**
	 * Generate an invalid JWT values
	 */
	private void generateInvalidJwtValues()
	{
		final String subject 	 	= "mySubjectName" ;
		final String customField 	= "invalidCustomFieldValue" ;
		final String[] customScopes = new String[] { "invalidScopeValue" } ;
		
		System.out.println("\nThis is an invalid JWT (invalid values): \n") ;
		System.out.println(this.jwtCall(subject, customField, customScopes)) ;
	}

	/**
	 * @param subject	   with the subject
	 * @param customField  with the custom field
	 * @param customScopes with the custom scopes
	 * @return the JWT
	 */
	private String jwtCall(final String subject, final String customField, final String[] customScopes)
	{
	    return JWT.create()
	    		  .withSubject(subject)
	    		  .withClaim(Constants.COMPULSORY_CUSTOM_FIELD_JWT, customField)
	    		  .withArrayClaim(Constants.COMPULSORY_CUSTOM_SCOPES_JWT, customScopes)
	    		  .sign(this.algorithm) ;
	}
	
	/**
	 * @throws IllegalArgumentException     with an occurred exception
	 * @throws UnsupportedEncodingException with an occurred exception
	 */
	public static void main(final String[] args) throws IllegalArgumentException, UnsupportedEncodingException
	{
		final JwtGenerator jwtGenerator = new JwtGenerator() ;
		
		jwtGenerator.generateJwts() ;
	}
}
