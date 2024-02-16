package com.ness.java.jokes.exercise.service.impl;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ness.java.jokes.exercise.model.response.JokesResponse;
import com.ness.java.jokes.exercise.model.response.RenderJokesResponse;
import com.ness.java.jokes.exercise.service.RenderJokeService;

@Service
public class RenderJokeServiceImpl implements RenderJokeService{
	
	@Value("${jokesApiUrl}")
	private String jokesApiUrl;
	
	@Value("${jokes10ApiUrl}")
	private String jokes10ApiUrl;
	
	
	@Override
	@Async 
	public CompletableFuture<JokesResponse> getAJoke(Integer count) {
		RestTemplate restTemplate = new RestTemplate();
		
		JokesResponse jokeResp = new JokesResponse();
		
		ArrayList<String> jokesList;
		if(count == 1) {
			RenderJokesResponse jokeResponse = restTemplate.getForObject(jokesApiUrl, RenderJokesResponse.class);
			if(jokeResponse != null) {
				StringBuffer joke = new StringBuffer();
				joke.append("1. ");
				joke.append(jokeResponse.getSetup());
				joke.append(System.getProperty("line.separator"));
				joke.append(jokeResponse.getPunchline());
				
				jokesList = new ArrayList<String>();
				jokesList.add(joke.toString());
				
				jokeResp.setJokeString(jokesList);
			}
		}
		else if(count > 1 && count < 10) {
			jokesList = new ArrayList<String>();
			RenderJokesResponse jokeResponse= null ;
			StringBuffer joke;
			for(int i = 0; i < count; i++) {
				jokeResponse = restTemplate.getForObject(jokesApiUrl, RenderJokesResponse.class);
					
				if(jokeResponse != null) {
					joke = new StringBuffer();
					joke.append(i+1 + ". ");
					joke.append(jokeResponse.getSetup());
					joke.append(System.getProperty("line.separator"));
					joke.append(jokeResponse.getPunchline());
					jokesList.add(joke.toString());
				}
			}
			
			jokeResp.setJokeString(jokesList);
		}
		else if(count >= 10) {
			//System.out.println("*************Entering more than 10 records condition********************");
			
			jokesList = new ArrayList<String>();
			ArrayList<String> finalList = new ArrayList<String>();
			RenderJokesResponse[] jokeResponses= null ;
			StringBuffer joke;
			RenderJokesResponse jokeResponse= null ;
			for(int i = 0; i < count % 10; i++) {
				//System.out.println("Value of i " + i);
				jokeResponses = restTemplate.getForObject(jokes10ApiUrl, RenderJokesResponse[].class);
				//System.out.println("jokeResponses " + jokeResponses);
				if(jokeResponses != null) {
					//System.out.println("jokeResponses.length " + jokeResponses.length);
					for(int j=0; j < jokeResponses.length; j++) {
						//System.out.println("Value of j " + j);
						joke = new StringBuffer();
						joke.append(j+1 + ". ");
						joke.append(jokeResponses[j].getSetup());
						joke.append(System.getProperty("line.separator"));
						joke.append(jokeResponses[j].getPunchline());
						//System.out.println("#########countVar " + countVar);
						finalList.add(joke.toString());
					}
					
				}
				
				for(int l = 0; l < count % 10; l++) {
					jokeResponse = restTemplate.getForObject(jokesApiUrl, RenderJokesResponse.class);
					
					if(jokeResponse != null) {
						joke = new StringBuffer();
						joke.append(l+1 + ". ");
						joke.append(jokeResponse.getSetup());
						joke.append(System.getProperty("line.separator"));
						joke.append(jokeResponse.getPunchline());
						finalList.add(joke.toString());
					}
				}
			}
			
			jokeResp.setJokeString(finalList);
		}
		return CompletableFuture.completedFuture(jokeResp);
	}
	
	
}
