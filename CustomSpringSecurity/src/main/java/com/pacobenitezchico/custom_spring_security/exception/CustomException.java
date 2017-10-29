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

package com.pacobenitezchico.custom_spring_security.exception ;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date ;
import java.util.HashMap ;
import java.util.Map ;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties ;
import com.fasterxml.jackson.annotation.JsonProperty ;
import com.fasterxml.jackson.annotation.JsonView ;
import com.fasterxml.jackson.core.JsonProcessingException ;
import com.pacobenitezchico.custom_spring_security.util.JacksonMapper;
import com.pacobenitezchico.custom_spring_security.util.JacksonViews.AuthorizationEnablerView;

/**
 * ------------------------------------------------
 * @author Francisco Manuel Benitez Chico
 * ------------------------------------------------
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class CustomException extends Exception
{
	/** Constant - Field - Status */
	private static final String FIELD_STATUS = "status" ;
	
	/** Constant - Field - Error */
	private static final String FIELD_ERROR = "error" ;
	
	/** Constant - Field - Exception */
	private static final String FIELD_EXCEPTION = "exception" ;
	
	/** Constant - Field - Message */
	private static final String FIELD_MESSAGE = "message" ;
	
	/** Constant - Field - Path */
	private static final String FIELD_PATH = "path" ;
	
	/** Constant - Field - Timestamp */
    private static final String FIELD_TIMESTAMP = "timestamp" ;

	/** Serial Version - UID */
	private static final long serialVersionUID = 1L ;
	
	/** Constant - default error code */
	private static final int DEFAULT_ERR_CODE = 500 ;
	
	/** Attribute - Status */
	@JsonView(AuthorizationEnablerView.class)
	private final int status ;
	
	/** Attribute - Error */
	@JsonView(AuthorizationEnablerView.class)
    private String error ;	
	
	/** Attribute - Message */
	@JsonView(AuthorizationEnablerView.class)
    private String message ;

	/** Attribute - Exception */
    @JsonView(AuthorizationEnablerView.class)
    private String exception ;

	/** Attribute - Path */
    @JsonView(AuthorizationEnablerView.class)
    private String path ;

	/** Attribute - Timestamp */
    @JsonView(AuthorizationEnablerView.class)
    private long timestamp ;

	/**
	 * Empty constructor
	 */
    public CustomException()
    {
    	// Default error value
        this(DEFAULT_ERR_CODE) ;
    }
    
    /**
	 * @param error     with the error
	 * @param exception with the exception
	 */
	public CustomException(final String error, final Exception exception)
	{
		// Default error value
		this(DEFAULT_ERR_CODE) ;
       
		this.error     = error ;
		this.message   = exception.getMessage() ;
		this.exception = this.getStackTrace(exception) ;
	}
    
    /**
	 * @param error     with the error
	 * @param message   with the message
	 * @param exception with the exception
	 */
    public CustomException(final String error, final String message, final String exception)
    {
    	// Default error value
        this(DEFAULT_ERR_CODE) ;
        
        this.error     = error ;
        this.message   = message ;
        this.exception = exception ;
    }
    
	/**
	 * @param status    with the status
	 * @param error     with the error
	 * @param exception with the exception
	 */
	public CustomException(final int status, final String error, final Exception exception)
	{
		// Default error value
		this(status) ;
       
		this.error     = error ;
		this.message   = exception.getMessage() ;
		this.exception = this.getStackTrace(exception) ;
	}
    
    /**
     * @param status    with the status
	 * @param error     with the error
	 * @param message   with the message
	 * @param exception with the exception
	 */
    public CustomException(final int status, final String error, final String message, final String exception)
    {
    	// Default error value
        this(status) ;
        
        this.error     = error ;
        this.message   = message ;
        this.exception = exception ;
    }

	/**
	 * @param status with the status
	 */
    public CustomException(final int status)
    {
        this.status    = status ;
        
		final Date now = new Date() ;
    	this.timestamp = now.getTime() ;
    }
    
    /**
     * @return the status
     */
    @JsonProperty(FIELD_STATUS)
    public int getStatus()
    {
    	return this.status ;
    }

    /**
	 * @param error with the error
	 */
    public void setError(final String error)
    {
        this.error = error ;
    }

    /**
     * @return the error
     */
    @JsonProperty(FIELD_ERROR)
    public String getError()
    {
        return this.error ;
    }
    
    /**
	 * @param exception with the exception
	 */
    public void setException(final String exception)
    {
        this.exception = exception ;
    }

    /**
     * @return the exception
     */
    @JsonProperty(FIELD_EXCEPTION)
    public String getException()
    {
        return this.exception ;
    }
    
    /**
	 * @param message with the message
	 */
    public void setMessage(final String message)
    {
        this.message = message ;
    }

    /**
     * @return the message
     */
    @JsonProperty(FIELD_MESSAGE)
    public String getMessage()
    {
        return this.message ;
    }
    
    /**
	 * @param path with the path
	 */
    public void setPath(final String path)
    {
        this.path = path ;
    }

    /**
     * @return the path
     */
    @JsonProperty(FIELD_PATH)
    public String getPath()
    {
        return this.path ;
    }
    
    /**
	 * @param timestamp with the timestamp
	 */
    public void setTimestamp(final long timestamp)
    {
        this.timestamp = timestamp ;
    }

    /**
     * @return the timestamp
     */
    @JsonProperty(FIELD_TIMESTAMP)
    public long getTimestamp()
    {
        return this.timestamp ;
    }

    /**
     * @return the body exception message
     */
    public Object getBodyExceptionMessage()
    {
        final Map<String, Object> mapBodyException = new HashMap<String, Object>() ;
        
        mapBodyException.put(FIELD_ERROR    , this.error) ;
        mapBodyException.put(FIELD_MESSAGE  , this.message) ;
        mapBodyException.put(FIELD_EXCEPTION, this.exception) ;
        mapBodyException.put(FIELD_PATH     , this.path) ;
        mapBodyException.put(FIELD_TIMESTAMP, this.timestamp) ;
        
        return mapBodyException ;
    }
    
    @Override
    public String toString()
    {
		try
		{
			return JacksonMapper.getInstance().writerWithDefaultPrettyPrinter().writeValueAsString(this) ;
	    }
	    catch (JsonProcessingException jsonProcessingExc)
	    {
	    	return "Exception converting to Json string: " + jsonProcessingExc.getMessage() ;
	    }
    }
    
    /**
	 * @param exception with the exception to convert to String
	 * @return stack trace converted to string
	 */
	private String getStackTrace(final Exception exception)
	{
		final StringWriter stringWriter = new StringWriter() ;
		final PrintWriter printWriter   = new PrintWriter(stringWriter) ;
		exception.printStackTrace(printWriter) ;
		
		return stringWriter.toString() ; 
	}
}
