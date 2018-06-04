package com.ratiose.testtask.service.tmdb;

public class TmdbExcpecion extends RuntimeException{

	public TmdbExcpecion() {
		super();
	}
	
	public TmdbExcpecion(String message) {
		super(message);
	}

	public TmdbExcpecion(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3454640305189091318L;

}
