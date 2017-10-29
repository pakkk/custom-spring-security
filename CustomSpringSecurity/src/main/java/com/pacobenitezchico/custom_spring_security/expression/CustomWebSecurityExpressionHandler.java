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

import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.stereotype.Component;

/**
 * ------------------------------------------------
 * @author Francisco Manuel Benitez Chico
 * ------------------------------------------------
 */
@Component
public class CustomWebSecurityExpressionHandler extends DefaultWebSecurityExpressionHandler
{
	/** Constant - Custom expression - Prefix */
	public static final String MY_CUSTOM_EXPRESSION = "my_custom_expression" ;
	
	/**
	 * Public constructor
	 */
	public CustomWebSecurityExpressionHandler()
	{
		this.setExpressionParser(new CustomExpressionParser(this.getExpressionParser())) ;
	}

	@Override
	protected StandardEvaluationContext createEvaluationContextInternal(final Authentication authentication, final FilterInvocation filterInvocation)
	{
		// New instance of Standard evaluation context
		final StandardEvaluationContext standardEvaluationContext = super.createEvaluationContextInternal(authentication, filterInvocation) ;
		
		// Set the prefix for all the expressions calls
		standardEvaluationContext.setVariable(MY_CUSTOM_EXPRESSION, new CustomExpressionMethods(authentication)) ;
		
		// Return the instance
		return standardEvaluationContext ;
	}
}
