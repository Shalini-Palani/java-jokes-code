package com.ness.java.jokes.JavaJokesExercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ness.java.jokes.exercise.model.response.JokesResponse;
import com.ness.java.jokes.exercise.service.impl.RenderJokeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class RenderJokeServiceImplTest {
	
	@Autowired
	private MockMvc mockMvc; 
	
	@InjectMocks
	RenderJokeServiceImpl jokeService; //service under test
	
	private static String jokesApiUrl;
		
	@BeforeEach
    public void setUp(){
		jokesApiUrl = "https://official-joke-api.appspot.com/random_joke";
    }
	
	@Test
	public void whenNoCountIsSpecified() throws Exception {
		System.out.println(jokesApiUrl);
		//JokesResponse reponse = jokeService.getAJoke(1);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				jokesApiUrl);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
	
		//System.out.println(reponse);
	}
}
