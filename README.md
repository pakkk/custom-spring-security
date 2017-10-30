# Custom Spring Security

This is a full example about Spring Security. I will show you how to implement the WebSecurityConfigurerAdapter to have your:

  - Custom exceptions.
  - Custom authorization.
  - Custom regular expressions for authorization.

I have developed several "main" classes:
  - "Application": launch Spring Boot (endpoint & its security)
  - "JwtGenerator": generate two JWT values (valid & an invalid examples)

## Why do I need custom exceptions?

Spring Security has two different exception types: "AccessDeniedException" and "AuthenticationException". The JSON content response for these exceptions are different than the common JSON responses content.

Then, I needed to find a way to generate my custom JSON exception to have the same JSON content than the common JSON responses in Spring.

Finally, you can find these two custom exception in the following package: "**com.pacobenitezchico.custom_spring_security.listener**". The previous custom exceptions are handled by the class "**com.pacobenitezchico.custom_spring_security.web.CustomWebSecurityConfigurerAdapter**":

```java
httpSecurity
    .exceptionHandling().authenticationEntryPoint(this.customAuthenticationEntryPoint)
        .and()
    .exceptionHandling().accessDeniedHandler(this.customAccessDeniedHandler)
```

## Why do I need custom authorization?

Imagine that your code expects a specific input headers like JWT and you want to check previously the content of that to implements your own "**org.springframework.security.core.Authentication**" model.

For this case, I defined a class "**com.pacobenitezchico.custom_spring_security.model.AuthModelJwt**" that implements the previous Authentication interface. This custom authentication model will help you to store a "**state**" among different filters.

The filter I talked about is the class "**com.pacobenitezchico.custom_spring_security.filter.AuthFilterJwt**" which inspects the request to get the "**Authorization**" header, and then get & check the JWT content, to finally generate the custom Authentication "**AuthModelJwt**" model.

This "**AuthModelJwt**" has your custom fields (in my case: "userName", "customField" & "customScopes") that they will be useful in the next epigraph to be checked by the regular expressions.

Finally,  the previous custom filter is handled by the class "**com.pacobenitezchico.custom_spring_security.web.CustomWebSecurityConfigurerAdapter**":

```java
httpSecurity
    ...
    .and()
        .addFilterBefore(this.authFilterJwt, UsernamePasswordAuthenticationFilter.class) ;
```

## Why do I need custom regular expressions for authorization?

This epigraph is the most powerful one. Imagine that you have a specific security requirement for your endpoints and them input parameters, and you want to check it in your "**WebSecurityConfigurerAdapter**". To take a look the final result, you can see the class:

```java
httpSecurity
    ...
    .and()
    .authorizeRequests()
    	.expressionHandler(this.expressionHandler)
    	.antMatchers(HttpMethod.GET, "/api/helloworld").access("(#my_custom_expression.hasCustomScope('validScopeValue') and #my_custom_expression.hasCustomField('validCustomFieldValue'))")
.and()
    .authorizeRequests()
    	.expressionHandler(this.expressionHandler)
    	.antMatchers(HttpMethod.GET, "/api/helloworld/").access("(#my_custom_expression.hasCustomScope('validScopeValue') and #my_custom_expression.hasCustomField('validCustomFieldValue'))")
```

As you can see, we have for every request:

  - A custom expression handler, for this case "**com.pacobenitezchico.custom_spring_security.expression.CustomWebSecurityExpressionHandler**"
  - An accessor validator using the custom regular expressions.
   
The most important class is "**com.pacobenitezchico.custom_spring_security.expression.CustomExpressionMethods**" which defines every custom validator method than we used previously.

This is the custom "scope" validator:

```java
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
```

This is the custom "field" validator:

```java
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
```

## How to test it?

Firstly, you need to launch the web application, using the main class "**com.pacobenitezchico.custom_spring_security.Application**" (this application will be launched in the port 8081).

Secondly, if you want to test it quickly, you will need to launch the JWT generator "**com.pacobenitezchico.custom_spring_security.JwtGenerator**". This application will generate two different JWT for you (valid and invalid examples).

Finally, you must need to use a browser and its RESTFUL tester to simulate calls to the endpoint (**http://localhost:8081/api/helloworld**) and send the previous JWT using a header "**Authorization**" and its value "**Bearer <generated JWT>**"

The expected outcomes are the following:
  -  Without any Authorization header: HTTP error code 401 and our custom JSON error content.
  -  Authorization header + Invalid JWT: HTTP error code 403 and our custom JSON error content.
  -  Authorization header + Valid JWT: HTTP succesful code 200 and the endpoint response.
