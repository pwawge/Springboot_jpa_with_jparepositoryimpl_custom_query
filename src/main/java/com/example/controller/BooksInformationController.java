package com.example.controller;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.BooksModel;
import com.example.service.BooksInformationService;
import com.example.service.CustomBookInformationService;

@RestController
public class BooksInformationController {
	private static final Logger logger = LoggerFactory.getLogger(BooksInformationController.class);

	@Autowired
	private BooksInformationService booksInformationService;
	
	@Autowired
	private CustomBookInformationService customBookInformationService;

	@PostMapping(value = "/save-books", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> saveBooks(@RequestBody BooksModel booksModel) {
		logger.info("we are in saveBooks");

		if (booksModel.getName().isEmpty() || booksModel.getPrice() == 0) {
			return new ResponseEntity<>("Please enter correct books name and price! ", HttpStatus.BAD_REQUEST);
		}

		BooksModel model = booksInformationService.save(booksModel);

		if (model != null) {
			return new ResponseEntity<>("Your data has been successfully saved! ", HttpStatus.OK);
		}

		return new ResponseEntity<>("Please enter correct books name and price! ", HttpStatus.BAD_REQUEST);

	}

	@GetMapping(value = "/books-by-name/{name}")
	@ResponseBody
	public ResponseEntity<?> getBooksByName(@PathVariable(name = "name") String booksName) {
		logger.info("we are in get books by name");

		if (booksName.isEmpty()) {
			return new ResponseEntity<>("Please enter correct books name! ", HttpStatus.BAD_REQUEST);
		}

		List<BooksModel> bookList = booksInformationService.findByName(booksName);

		if (!bookList.isEmpty()) {

			return new ResponseEntity<>(bookList, HttpStatus.OK);
		}
		
		return new ResponseEntity<>("We dont have data for this book name  "+booksName, HttpStatus.NOT_FOUND);
		
	}
	
	@DeleteMapping({"/delete-by-id/{id}","/delete-by-id/"})
	@ResponseBody
	public ResponseEntity<?> deleteBookById(@PathVariable(name = "id",required = false) Integer id){
		
		if (id == null) {
			return new ResponseEntity<>("Please enter correct book id! ", HttpStatus.BAD_REQUEST);
		}
		
		booksInformationService.deleteById(id);
		
		
		return new ResponseEntity<>("Data deleted successfully!", HttpStatus.OK);
		
	}

	@PutMapping("/update")
	@ResponseBody
	public ResponseEntity<?> updateBookInformation(@RequestBody BooksModel booksModel){
		
		if (booksModel.getName().isEmpty() || booksModel.getPrice() == 0) {
			return new ResponseEntity<>("Please enter correct books name and price! ", HttpStatus.BAD_REQUEST);
		}
		BooksModel booksModelOld = customBookInformationService.updateBooks(booksModel);
		
		if(booksModelOld!=null) {
			return new ResponseEntity<>("Data updated successfully!", HttpStatus.OK);
		}
				
		return new ResponseEntity<>("Data not found for update! ", HttpStatus.NOT_FOUND);
		
	}
	@PatchMapping("/update")
	@ResponseBody
	public ResponseEntity<?> patchBookInformation(@RequestBody BooksModel booksModel){
		
		
		return null;
		
	}
	
	@GetMapping("/find-books-by-name-author")
	@ResponseBody
	public ResponseEntity<?> findBooksByNameAndAuthor(@RequestParam String name , @RequestParam String author){
		logger.info("we are in find book by author and book name");
		
		if(Objects.isNull(author) || Objects.isNull(name)) {
			return new ResponseEntity<>("Please enter author name and book name correctly",HttpStatus.BAD_REQUEST);
		}
		
		BooksModel booksModel =  booksInformationService.findBookByTitleAndAuthorIndexJpql(name, author);
		
		if(booksModel!=null) {
			return new ResponseEntity<>(booksModel, HttpStatus.OK);
		}
		
		return new ResponseEntity<>("Data not found ! ", HttpStatus.NOT_FOUND);
		
	}
	
	@GetMapping("/get-books-by-name")
	@ResponseBody
	public ResponseEntity<?> getBooksByNameSorting(@RequestParam String bookName){
		
		if(Objects.isNull(bookName)) {
			return new ResponseEntity<>("Please enter book name correctly",HttpStatus.BAD_REQUEST);
		}
		List<BooksModel> booksModelList =  booksInformationService.findBookByTitleAndSort(bookName, Sort.by("author").ascending());
		
		if(booksModelList.size()>0) {
			return new ResponseEntity<>(booksModelList, HttpStatus.OK);
		}
		
		return new ResponseEntity<>("Data not found ! ", HttpStatus.NOT_FOUND);		
	}
	
	
	
	
}
