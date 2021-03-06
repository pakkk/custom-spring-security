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

package com.pacobenitezchico.custom_spring_security.model;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.pacobenitezchico.custom_spring_security.util.LowerCaseHashSet;

/**
 * ------------------------------------------------
 * @author Francisco Manuel Benitez Chico
 * ------------------------------------------------
 */
public class AuthModelJwt implements Authentication
{
	/** Constant - Field - Authorities */
	public static final String FIELD_AUTHORITIES = "authorities" ; 
	
    /** Constant - Field - Custom */
	public static final String FIELD_CUSTOM	 	 = "customField" ;
	
    /** Constant - Field - Scopes */
	public static final String FIELD_SCOPES  	 = "customScopes" ;
	
	/** Constant - Serial Version UID */
	private static final long serialVersionUID   = -7066892149149258162L ;

	/** Attribute - User name */
	private final String userName ;
	
	/** Attribute - custom field */
	private final String customField ;
	
	/** Attribute - custom scopes - List */
	private Set<String> customScopes ;	
	
	/**
	 * Public constructor
	 * 
	 * @param userName 	   with the user name
	 * @param customField  with the custom field
	 * @param customScopes with a list of custom scopes
	 */
    public AuthModelJwt(final String userName, final String customField, final List<String> customScopes)
    {
    	// Set the user name
    	this.userName = userName ;
    	
    	// Set the custom field
    	this.customField = customField ;     
        
    	// Fill the custom set of scopes
    	if (customScopes != null)
    	{
    		this.customScopes = new LowerCaseHashSet(customScopes) ;
    	}
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
    	return null ;
    }

    @Override
    public Object getCredentials()
    {
        return null ;
    }

    @Override
    public Object getDetails()
    {
        return null ;
    }

    @Override
    public Object getPrincipal()
    {
        return null ;
    }

    @Override
    public boolean isAuthenticated()
    {
        return true ;
    }

    @Override
    public void setAuthenticated(final boolean isAuthenticated) throws IllegalArgumentException
    {
    	// Empty method
    }

    @Override
    public String getName()
    {
        return this.userName ;
    }
    
    /**
     * @return the custom field
     */
    public String getCustomField()
    {
    	return this.customField ;
    }
    
    public Set<String> getCustomScopes()
    {
    	return this.customScopes ;
    }
}
