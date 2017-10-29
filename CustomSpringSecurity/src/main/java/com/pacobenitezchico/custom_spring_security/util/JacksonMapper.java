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

package com.pacobenitezchico.custom_spring_security.util ;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * ------------------------------------------------
 * @author Francisco Manuel Benitez Chico
 * ------------------------------------------------
 */
public final class JacksonMapper extends ObjectMapper 
{
	/** Serial Version UID */
	private static final long serialVersionUID = 6983970095455278754L ;
	
	/** Private static instance */
	private static ObjectMapper INSTANCE = new JacksonMapper() ;

	/**
	 * @return the Object Mapper
	 */
	public static ObjectMapper getInstance()
	{
		return INSTANCE ;
	}
	
	/**
	 * ------------------------------------------------
	 * @author Francisco Manuel Benitez Chico
	 * ------------------------------------------------
	 */
	protected static class FilteredTypeResolverBuilder extends DefaultTypeResolverBuilder
	{
		/** Serial Version UID */
		private static final long serialVersionUID = 1044981283003407056L ;

		/**
		 * @param defaultTyping with the default typing
		 */
		public FilteredTypeResolverBuilder(final DefaultTyping defaultTyping)
		{
			super(defaultTyping) ;
		}
	}
	
	/**
	 * Initialize the instance
	 */
	private void initializeInstance()
	{
		this.setDefaultTyping(new FilteredTypeResolverBuilder(ObjectMapper.DefaultTyping.NON_FINAL).init(JsonTypeInfo.Id.MINIMAL_CLASS, null)
			  																					   .inclusion(JsonTypeInfo.As.WRAPPER_OBJECT)) ;
		this.setSerializationInclusion(JsonInclude.Include.NON_NULL) ;
		this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false) ;

		this.setConfig(this.getSerializationConfig().withView(JacksonViews.AuthorizationEnablerView.class)) ;
	}
	
	/**
	 * Private constructor
	 */
	private JacksonMapper()
	{
		// Initialize instance
		this.initializeInstance() ;
	}
}
