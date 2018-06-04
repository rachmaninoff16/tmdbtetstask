package com.ratiose.testtask.service.tmdb.impl;

import static org.springframework.util.StringUtils.isEmpty;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.ratiose.testtask.service.tmdb.TmdbApi;
import com.ratiose.testtask.service.tmdb.TmdbExcpecion;
import com.ratiose.testtask.service.tmdb.dto.TmdbMovieInfo;

@Service
public class TmdbApiImpl implements TmdbApi {
	
    private static final String ID_FIELD = "id";
	private static final String CHARACTER_FIELD = "character";
	private static final String CAST_FIELD = "cast";
	private static final String RELEASE_DATE_FIELD = "release_date";
	private static final String TITLE_FIELD = "title";
	private static final String MOVIE_CREDITS_URL = "/movie/%d/credits";
    private static final String MOVIE_CREDITS_BY_PERSON_URL = "/person/%d/movie_credits";
	private static final String API_KEY_PARAMETER_NAME = "api_key";
	private static final String LANGUAGE_PARAMETER_NAME = "language";
	private static final String MOVIE_URL = "/movie/";
	private static final String PERSON_URL = "/person/";
	private static final String PERSON_NAME_FIELD = "name";
	
	@Value("${tmdb.apikey}")
    private String tmdbApiKey;
    @Value("${tmdb.language}")
    private String tmdbLanguage;
    @Value("${tmdb.api.base.url}")
    private String tmdbApiBaseUrl;

    public String popularMovies() throws IllegalArgumentException {
        try {
            String url = getTmdbUrl("/movie/popular");
            HttpResponse<JsonNode> jsonResponse = Unirest.get(url).asJson();
            checkForTmdbErrors(jsonResponse);
            return jsonResponse.getBody().toString();            
        } catch (UnirestException e) {
            throw new TmdbExcpecion(e);
        } catch (URISyntaxException e) {
        	throw new TmdbExcpecion(e);
        }        
    }
    
	@Override
	public String getActorNameById(Integer id) {
        try {
            String url = getTmdbUrl(PERSON_URL + id);
            HttpResponse<JsonNode> jsonResponse = Unirest.get(url).asJson();
            checkForTmdbErrors(jsonResponse);
            return jsonResponse.getBody().getObject().getString(PERSON_NAME_FIELD);
        } catch (UnirestException e) {
        	throw new TmdbExcpecion(e);
        } catch (URISyntaxException e) {
        	throw new TmdbExcpecion(e);
        }		
	}

	
	@Override
	public TmdbMovieInfo getMovieInfo(Integer id) {
        try {
            String url = getTmdbUrl(MOVIE_URL + id);
            HttpResponse<JsonNode> jsonResponse = Unirest.get(url).asJson();            
            checkForTmdbErrors(jsonResponse);            
            JSONObject jsonMovieObject = jsonResponse.getBody().getObject();
            return createMovieInfo(jsonMovieObject);
        } catch (UnirestException e) {
        	throw new TmdbExcpecion(e);
        } catch (URISyntaxException e) {
        	throw new TmdbExcpecion(e);
        }
	}
	
	@Override
	public List<Integer> getAllMovieIdsByActor(Integer actorId){
        try {
            String url = getTmdbUrl(String.format(MOVIE_CREDITS_BY_PERSON_URL,  actorId));
            HttpResponse<JsonNode> jsonResponse = Unirest.get(url).asJson();
            checkForTmdbErrors(jsonResponse);
            JSONArray jsonCastArray = jsonResponse.getBody().getObject().getJSONArray(CAST_FIELD);
            return createMovieIdsList(jsonCastArray);
        } catch (UnirestException e) {
        	throw new TmdbExcpecion(e);
        } catch (URISyntaxException e) {
        	throw new TmdbExcpecion(e);
        }
	}
	
	@Override
	public List<Integer> getAllCastByMovie(Integer movieId){
        try {
            String url = getTmdbUrl(String.format(MOVIE_CREDITS_URL, movieId));
            HttpResponse<JsonNode> jsonResponse = Unirest.get(url).asJson();
            checkForTmdbErrors(jsonResponse);
            JSONArray jsonCastArray = jsonResponse.getBody().getObject().getJSONArray(CAST_FIELD);
            return createMovieIdsList(jsonCastArray);
        } catch (UnirestException e) {
        	throw new TmdbExcpecion(e);
        } catch (URISyntaxException e) {
        	throw new TmdbExcpecion(e);
        }
	}
	
	private void checkForTmdbErrors(HttpResponse<JsonNode> jsonResponse) {
		if (jsonResponse.getStatus() != HttpStatus.SC_OK)
			throw new TmdbExcpecion(jsonResponse.getBody().toString());
	}


    private String getTmdbUrl(String tmdbItem) throws URISyntaxException {
        StringBuilder builder = new StringBuilder(tmdbApiBaseUrl);
        builder.append(tmdbItem);
        URIBuilder uriBuilder = new URIBuilder(builder.toString());
        uriBuilder.addParameter(LANGUAGE_PARAMETER_NAME, tmdbLanguage);
        uriBuilder.addParameter(API_KEY_PARAMETER_NAME, tmdbApiKey);
        return uriBuilder.build().toString();
    }
    
	private TmdbMovieInfo createMovieInfo(JSONObject jsonMovieObject) throws JSONException {
		TmdbMovieInfo movieInfo = new TmdbMovieInfo();
		movieInfo.setTitle(jsonMovieObject.getString(TITLE_FIELD));
		movieInfo.setReleaseDate(jsonMovieObject.getString(RELEASE_DATE_FIELD));
		return movieInfo;
	}
	
	private List<Integer> createMovieIdsList(JSONArray jsonCastArray) throws JSONException {
		List<Integer> movieIds = new ArrayList<Integer>();
		for(int index = 0; index < jsonCastArray.length(); index++) 
			if(!isEmpty(jsonCastArray.getJSONObject(index).getString(CHARACTER_FIELD)))
				movieIds.add(jsonCastArray.getJSONObject(index).getInt(ID_FIELD));           
		return movieIds;
	}

}
