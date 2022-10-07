package com.digitalbboks.test.exception;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.digitalbooks.demo.exception.InternalServerErrorException;
import com.digitalbooks.demo.exception.ResourceNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class TestException {
	
	
	@Test
    public void TestInternalServerErrorException(){
		InternalServerErrorException expected = new InternalServerErrorException("exception");
    }
	
	@Test
    public void TestResourceNotFOundException(){
		ResourceNotFoundException expected = new ResourceNotFoundException("exception");
    }


}
