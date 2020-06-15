package com.bigcity.services;

import com.bigcity.beans.Book;
import com.bigcity.criteria.BookCriteria;
import com.bigcity.services.interfaces.IBookService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author nicolasdotnet
 */
@Service
@Transactional
public class BookServiceImpl implements IBookService {

    private static final Logger log = LogManager.getLogger(BookServiceImpl.class);

    @Autowired
    RestTemplate restTemplate;

    @Value("${apiServerPort}")
    private String serverPort;

    private String baseUrl = "http://localhost:" + "8080";

    @Override
    public List<Book> getAllBooks() throws URISyntaxException {

        URI uri = new URI(baseUrl + "/api/user/books/all");

        ResponseEntity<Book[]> result = restTemplate.getForEntity(uri, Book[].class);

        return Arrays.asList(result.getBody());

    }

    @Override
    public Book getBook(Long id) throws URISyntaxException {

        URI uri = new URI(baseUrl + "/api/user/book/" + id);

        ResponseEntity<Book> result = restTemplate.getForEntity(uri,Book.class);

        return result.getBody();

    }

    @Override
    public Page<Book> getAllBooksByCriteria(String isbn, String author, String bookTitle, String categoryName, int p, int s)throws URISyntaxException {
        
        BookCriteria criteria = new BookCriteria();
        
        criteria.setAuthor(author);
        criteria.setBookTitle(bookTitle);
        criteria.setCategoryName(categoryName);
        criteria.setIsbn(isbn);
        
        URI uri = new URI(baseUrl + "/api/user/books");
        
//        ResponseEntity<Book[]> r = restTemplate.getForEntit
       
        
        
        
        return null;

    }

}
