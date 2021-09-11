package com.example.service;


import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.BooksModel;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomBookInformationService {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomBookInformationService.class);
	
	@Autowired
	private BooksInformationService booksInformationService;
	
	public BooksModel updateBooks(BooksModel booksModel) {
		logger.info("We are in update book method");
		
		Optional<BooksModel> bookModelOptional = booksInformationService.findById(booksModel.getId());
		
		booksInformationService.save(booksModel);
		
		
		return bookModelOptional.get();
		
	}
	

}
