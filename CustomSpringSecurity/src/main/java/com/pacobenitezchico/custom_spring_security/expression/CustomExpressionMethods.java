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

package com.pacobenitezchico.custom_spring_security.expression;

import java.util.Collection;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;

import com.pacobenitezchico.custom_spring_security.model.AuthModelJwt;

/**
 * ------------------------------------------------
 * @author Francisco Manuel Benitez Chico
 * ------------------------------------------------
 */
public class CustomExpressionMethods
{
	/** Attribute - Authentication */
	private final Authentication authentication ;

	/**
	 * Public constructor
	 * 
	 * @param authentication with the authentication
	 */
	public CustomExpressionMethods(final Authentication authentication)
	{
		this.authentication = authentication ;
	}

	/**
	 * @param decision with the final decision
	 * @return true if the user has permissions. Otherwise, it will throw an exception
	 */
	public boolean throwOnError(final boolean decision)
	{
		if (!decision)
		{
			final Throwable throwable = new RuntimeException("Insufficient scope for this resource") ;
			throw new AccessDeniedException(throwable.getMessage(), throwable) ;
		}
		
		return decision ;
	}
	
	/**
	 * @param configCustomScope with the configuration custom scope
	 * @return true if the scope belongs to the expected ones
	 */
	public boolean hasCustomScope(final String configCustomScope)
	{
		return this.hasAnyCustomScope(new String[] { configCustomScope }) ;
	}

	/**
	 * @param configCustomScope with the configuration custom scope
	 * @return true if one of the scopes belongs to the expected ones
	 */
	public boolean hasAnyCustomScope(final String ... configCustomScope)
	{
		if (this.authentication instanceof AuthModelJwt)
		{
			final Collection<String> authoritiesClientSet = ((AuthModelJwt) this.authentication).getCustomScopes() ;
			
			if (authoritiesClientSet != null)
			{
				for (final String configScope : configCustomScope)
				{
					if (authoritiesClientSet.contains(configScope))
					{
						return true ;
					}
				}
			}
		}
    
		return false ;
	}
	
	/**
	 * @param configCustomField with the configuration custom field
	 * @return true if one of the scopes belongs to the expected ones
	 */
	public boolean hasCustomField(final String configCustomField)
	{
		if (this.authentication instanceof AuthModelJwt)
		{
			return ((AuthModelJwt) this.authentication).getCustomField().equalsIgnoreCase(configCustomField) ;
		}
    
		return false ;
	}
}
