package com.ness.java.jokes.exercise.controller;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ness.java.jokes.exercise.model.response.JokesResponse;
import com.ness.java.jokes.exercise.model.response.RenderJokesResponse;
import com.ness.java.jokes.exercise.service.RenderJokeService;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
public class JavaJokesExerciseController {
	
	//The message to be displayed when max limit for count is greater than 100
	@Value("${message.validation.maxlimit.crossed}")
	private String maxLimitCrossed;
	
	//The message to be displayed when min limit for count is less than 1
	@Value("${message.validation.minlimit}")
	private String minLimit;
	
	@Autowired
	private RenderJokeService renderJokeService;

	/**
	 * This API calls the service to 
	 * render a joke from the external link API specified.
	 * @param count no of jokes to be retrieved.
	 * @return
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	@GetMapping(value = "api/v1/jokes")
	public ResponseEntity  getAJoke(@RequestParam(defaultValue = "5") 
		Integer count) throws InterruptedException, ExecutionException {
		ResponseEntity responseEntity = null;
		
		Map<String, Object> errorResponse;
		
		//If the count is more than 100 to be retrieved then send error msg.
		if(count > 100) {
			errorResponse = Map.of(
				      "message", maxLimitCrossed,
				      "status", HttpStatus.BAD_REQUEST.toString()
			);
			
			
			responseEntity = new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
		}
		//If the count is less than 1 to be retrieved then send error msg.
		else if(count < 1) {
			errorResponse = Map.of(
				      "message", minLimit,
				      "status", HttpStatus.BAD_REQUEST.toString()
			);
			responseEntity = new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
		}
		//Else call the external render joke api
		else {
			CompletableFuture<String> renderJokeResponse =  renderJokeService.getAJoke(count);
			
			responseEntity = new ResponseEntity(renderJokeResponse.get(), HttpStatus.OK);
		}
		
		return responseEntity; 
	}
	
}
