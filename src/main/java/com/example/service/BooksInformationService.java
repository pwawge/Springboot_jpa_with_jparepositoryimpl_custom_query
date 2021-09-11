package com.example.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.BooksModel;

public interface BooksInformationService extends JpaRepository<BooksModel, Integer>{
	
	List<BooksModel> findByName(String name);
	
	//JPQL Select @Query with Index Parameters
	//In the preceding code, the title method parameter will be assigned to the query parameter with index 1. Similarly, authorName will be assigned to the query parameter with index 2.
	//It is important to note that the order of the query parameter indexes and the method parameters must be the same.
	@Query("SELECT b FROM BooksModel b WHERE b.name = ?1 and b.author = ?2")
	BooksModel findBookByTitleAndAuthorIndexJpql(String title, String authorName);
	
	//Native SQL Select @Query with Index Parameters
	@Query(value = "SELECT * FROM book_information  WHERE name = ?1 and author = ?2",
		       nativeQuery = true)
	BooksModel findBookByTitleAndAuthorIndexNative(String title, String authorName);
	
	//JPQL @Query with Named Parameters
	@Query("SELECT b FROM BooksModel b WHERE b.name = :name and b.author= :author")
	BooksModel findBookByTitleAndAuthorNamedJpql(@Param("name") String title, @Param("author") String author);
	
	@Query("select b from BooksModel b where b.name = ?1")
	List<BooksModel> findBookByTitleAndSort(String name, Sort sort);
	
	
}
