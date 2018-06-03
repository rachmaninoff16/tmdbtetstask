package com.ratiose.testtask.service.tmdb.impl;

import static org.springframework.util.StringUtils.isEmpty;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.ratiose.testtask.service.tmdb.TmdbApi;
import com.ratiose.testtask.service.tmdb.dto.TmdbMovieInfo;

@Service
public class TmdbApiImpl implements TmdbApi {
	
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

            if (jsonResponse.getStatus() != HttpStatus.SC_OK) {
                return null;
            }

            String responseJSONString = jsonResponse.getBody().toString();

            return responseJSONString;
        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
    
	@Override
	public String getActorNameById(Long id) {
        try {
            String url = getTmdbUrl(PERSON_URL + id);
            HttpResponse<JsonNode> jsonResponse = Unirest.get(url).asJson();
            if (jsonResponse.getStatus() != HttpStatus.SC_OK)
            	throw new RuntimeException(jsonResponse.getStatus() + " - " + jsonResponse.getStatusText());
            return jsonResponse.getBody().getObject().getString(PERSON_NAME_FIELD);
        } catch (UnirestException e) {
        	throw new RuntimeException(e);
        } catch (URISyntaxException e) {
        	throw new RuntimeException(e);
        }		
	}
	
	@Override
	public TmdbMovieInfo getMovieInfo(Long id) {
        try {
            String url = getTmdbUrl(MOVIE_URL + id);
            HttpResponse<JsonNode> jsonResponse = Unirest.get(url).asJson();
            if (jsonResponse.getStatus() != HttpStatus.SC_OK)
            	throw new RuntimeException(jsonResponse.getStatus() + " - " + jsonResponse.getStatusText());
            TmdbMovieInfo movieInfo = new TmdbMovieInfo();
            JSONObject jsonMovieObject = jsonResponse.getBody().getObject();
            movieInfo.setTitle(jsonMovieObject.getString("title"));
            movieInfo.setReleaseDate(jsonMovieObject.getString("release_date"));
            return movieInfo;
        } catch (UnirestException e) {
        	throw new RuntimeException(e);
        } catch (URISyntaxException e) {
        	throw new RuntimeException(e);
        }
	}
	
	@Override
	public List<Integer> getAllMovieIdsByActor(Long actorId){
        try {
            String url = getTmdbUrl(String.format(MOVIE_CREDITS_BY_PERSON_URL,  actorId));
            HttpResponse<JsonNode> jsonResponse = Unirest.get(url).asJson();
            if (jsonResponse.getStatus() != HttpStatus.SC_OK)
            	throw new RuntimeException(jsonResponse.getBody().toString());
            JSONArray jsonCastArray = jsonResponse.getBody().getObject().getJSONArray("cast");
            List<Integer> movieIds = new ArrayList<Integer>();
            for(int index = 0; index < jsonCastArray.length(); index++) 
            	if(!isEmpty(jsonCastArray.getJSONObject(index).getString("character")))
            		movieIds.add(jsonCastArray.getJSONObject(index).getInt("id"));           
            return movieIds;
        } catch (UnirestException e) {
        	throw new RuntimeException(e);
        } catch (URISyntaxException e) {
        	throw new RuntimeException(e);
        }
	}
	
	@Override
	public List<Integer> getAllCastByMovie(Long movieId){
        try {
            String url = getTmdbUrl(String.format(MOVIE_CREDITS_URL, movieId));
            HttpResponse<JsonNode> jsonResponse = Unirest.get(url).asJson();
            if (jsonResponse.getStatus() != HttpStatus.SC_OK)
            	throw new RuntimeException(jsonResponse.getBody().toString());
            JSONArray jsonCastArray = jsonResponse.getBody().getObject().getJSONArray("cast");
            List<Integer> actorIdList = new ArrayList<Integer>();
            for(int index = 0; index < jsonCastArray.length(); index++) 
            	if(!isEmpty(jsonCastArray.getJSONObject(index).getString("character")))
            		actorIdList.add(jsonCastArray.getJSONObject(index).getInt("id"));           
            return actorIdList;
        } catch (UnirestException e) {
        	throw new RuntimeException(e);
        } catch (URISyntaxException e) {
        	throw new RuntimeException(e);
        }
	}


    private String getTmdbUrl(String tmdbItem) throws URISyntaxException {
        StringBuilder builder = new StringBuilder(tmdbApiBaseUrl);
        builder.append(tmdbItem);
        URIBuilder uriBuilder = new URIBuilder(builder.toString());
        uriBuilder.addParameter(LANGUAGE_PARAMETER_NAME, tmdbLanguage);
        uriBuilder.addParameter(API_KEY_PARAMETER_NAME, tmdbApiKey);
        return uriBuilder.build().toString();
    }

}
