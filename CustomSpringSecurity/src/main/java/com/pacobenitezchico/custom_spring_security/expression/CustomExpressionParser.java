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

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.ParserContext;
import org.springframework.util.Assert;

/**
 * ------------------------------------------------
 * @author Francisco Manuel Benitez Chico
 * ------------------------------------------------
 */
public class CustomExpressionParser implements ExpressionParser
{
	/** Attribute - Delegate */
	private final ExpressionParser delegate ;

	/**
	 * @param delegate with the delegate
	 */
	public CustomExpressionParser(final ExpressionParser delegate)
	{
		Assert.notNull(delegate, "delegate cannot be null") ;
		
		this.delegate = delegate ;
	}

	/**
	 * @param expressionString with the expression string
	 * @return the expression
	 */
	public Expression parseExpression(final String expressionString) throws ParseException
	{
		return this.delegate.parseExpression(this.wrapExpression(expressionString)) ;
	}

	/**
	 * @param expressionString with the expression string
	 * @param parserContext    with the parser context
	 * @return the expression
	 */
	public Expression parseExpression(final String expressionString, final ParserContext parserContext) throws ParseException
	{
		return this.delegate.parseExpression(this.wrapExpression(expressionString), parserContext) ;
	}

	/**
	 * @param expressionString with the expression string
	 * @return the wrapped expression
	 */
	private String wrapExpression(final String expressionString)
	{
		return "#" + CustomWebSecurityExpressionHandler.MY_CUSTOM_EXPRESSION + ".throwOnError(" + expressionString + ")" ;
	}
}
