package com.ness.java.jokes.exercise.service.impl;

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
	
	
	@Override
	@Async 
	public CompletableFuture<JokesResponse> getAJoke(Integer count) {
		RestTemplate restTemplate = new RestTemplate();
		
		JokesResponse jokeResp = new JokesResponse();
		
		String[] jokes;
		if(count == 1) {
			RenderJokesResponse jokeResponse = restTemplate.getForObject(jokesApiUrl, RenderJokesResponse.class);
			if(jokeResponse != null) {
				StringBuffer joke = new StringBuffer();
				joke.append(jokeResponse.getSetup());
				joke.append(System.getProperty("line.separator"));
				joke.append(jokeResponse.getPunchline());
				
				jokes = new String[1];
				jokes[0] = joke.toString();
				
				jokeResp.setJokeString(jokes);
			}
		}
		else if(count > 1 && count <= 10) {
			jokes = new String[count];
			RenderJokesResponse jokeResponse ;
			System.out.println("2");
			for(int i = 0; i < count; i++) {
				jokeResponse = restTemplate.getForObject(jokesApiUrl, RenderJokesResponse.class);
					
				if(jokeResponse != null) {
					StringBuffer joke = new StringBuffer();
					joke.append(jokeResponse.getSetup());
					joke.append(System.getProperty("line.separator"));
					joke.append(jokeResponse.getPunchline());
					jokes[i] = joke.toString();
				}
			}
			
			
			
			
			jokeResp.setJokeString(jokes);
		}
		/*else if(count > 10) {
			jokes = new String[count];
			int countVar = count;
			System.out.println("count%10 " + countVar%10);
			for(int k = 0; k <= count%10; k++) {
				System.out.println("k " + k);
				
				for(int i = 0; i < 10 ; i++) {
					System.out.println("i " + i);
					RenderJokesResponse jokeResponse = restTemplate.getForObject(jokesApiUrl, RenderJokesResponse.class);
					
					if(jokeResponse != null) {
						StringBuffer joke = new StringBuffer();
						joke.append(jokeResponse.getSetup());
						joke.append(System.getProperty("line.separator"));
						joke.append(jokeResponse.getPunchline());
						jokes[i] = joke.toString();
					}
				}
				
				countVar--;
				
			}
			
			jokeResp.setJokeString(jokes);
		}*/
		
		
		
		return CompletableFuture.completedFuture(jokeResp);
	}

}
