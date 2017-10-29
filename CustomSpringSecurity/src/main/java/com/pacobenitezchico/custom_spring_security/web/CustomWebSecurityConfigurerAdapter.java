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

package com.pacobenitezchico.custom_spring_security.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pacobenitezchico.custom_spring_security.expression.CustomWebSecurityExpressionHandler;
import com.pacobenitezchico.custom_spring_security.filter.AuthFilterJwt;
import com.pacobenitezchico.custom_spring_security.listener.CustomAccessDeniedHandler;
import com.pacobenitezchico.custom_spring_security.listener.CustomAuthenticationEntryPoint;

/**
 * ------------------------------------------------
 * @author Francisco Manuel Benitez Chico
 * ------------------------------------------------
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter
{	
	/** Exception handler - Custom - Authentication entry point */
	@Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

	/** Exception handler - Custom - Access denied */
	@Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler ;
	
	/** Authentication - Filter - JWT */
	@Autowired
    private AuthFilterJwt authFilterJwt ;
	
	@Autowired
	private CustomWebSecurityExpressionHandler expressionHandler ;
	
    @Override
    public void configure(final HttpSecurity httpSecurity) throws Exception
    {
    	httpSecurity
    		.csrf().disable()
    		.exceptionHandling().authenticationEntryPoint(this.customAuthenticationEntryPoint)
    	.and()
    		.exceptionHandling().accessDeniedHandler(this.customAccessDeniedHandler)

        .and()
            .authorizeRequests()
            	.expressionHandler(this.expressionHandler)
            	.antMatchers(HttpMethod.GET, "/api/helloworld").access("(#my_custom_expression.hasCustomScope('validScopeValue') and #my_custom_expression.hasCustomField('validCustomFieldValue'))")
        .and()
	        .authorizeRequests()
	        	.expressionHandler(this.expressionHandler)
	        	.antMatchers(HttpMethod.GET, "/api/helloworld/").access("(#my_custom_expression.hasCustomScope('validScopeValue') and #my_custom_expression.hasCustomField('validCustomFieldValue'))")
        .and()
        	.addFilterBefore(this.authFilterJwt, UsernamePasswordAuthenticationFilter.class) ;
    }
}
