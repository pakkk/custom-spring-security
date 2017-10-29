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

package com.pacobenitezchico.custom_spring_security.util;

import java.util.Collection;
import java.util.HashSet;

/**
 * ------------------------------------------------
 * @author Francisco Manuel Benitez Chico
 * ------------------------------------------------
 */
public final class LowerCaseHashSet extends HashSet<String>
{
	/** Constant - Serial Version UID */
	private static final long serialVersionUID = 2137071328847571490L ;

	/**
	 * Protected constructor
	 * 
	 * @param stringCollection with the String collection
	 */
	public LowerCaseHashSet(final Collection<String> stringCollection)
	{
		super(stringCollection) ;
	}
	
	/**
	 * Add the elements in lower case
	 * 
	 * @param string with the string
	 * @return true if everything was OK
	 */
	@Override
	public boolean add(final String string)
	{
		return super.add(string.toLowerCase()) ;
	}

	/**
	 * Check if the element is equals in lower case
	 * 
	 * @param object with the object
	 * @return true if the HashSet contains the element 
	 */
	@Override
	public boolean contains(final Object object)
	{
		return (object instanceof String) && super.contains(((String) object).toLowerCase()) ;
	}
}
