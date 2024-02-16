package com.ness.java.jokes.exercise.service;

import java.util.concurrent.CompletableFuture;

import com.ness.java.jokes.exercise.model.response.JokesResponse;
import com.ness.java.jokes.exercise.model.response.RenderJokesResponse;

public interface RenderJokeService {
	public CompletableFuture<String> getAJoke(Integer count);
	
}
