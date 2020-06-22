package com.bigcity.services;

import com.bigcity.beans.Book;
import com.bigcity.criteria.BookCriteria;
import com.bigcity.services.interfaces.IBookService;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private RestTemplate restTemplate;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${apiServerPort}")
    private String serverPort;

    private String baseUrl = "http://localhost:" + "8080";

    private HttpHeaders headers = new HttpHeaders();

    @Override
    public List<Book> getAllBooks() throws URISyntaxException {

        URI uri = new URI(baseUrl + "/api/user/books/all");

// add basic authentication header
        headers.setBasicAuth("nicolas.desdevises@yahoo.com", "123");

        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<Book[]> result = restTemplate.exchange(uri, HttpMethod.GET, request, Book[].class);
        
        return Arrays.asList(result.getBody());

    }

    @Override
    public Book getBook(Long id) throws URISyntaxException {

        URI uri = new URI(baseUrl + "/api/user/books/" + id);

        ResponseEntity<Book> result = restTemplate.getForEntity(uri, Book.class);

        return result.getBody();

    }

    @Override
    public Page<Book> getAllBooksByCriteria(String isbn, String author, String bookTitle, String categoryName, int p, int s) throws URISyntaxException {

        // TODO revoir url avec multi param
        BookCriteria criteria = new BookCriteria();

        criteria.setAuthor(author);
        criteria.setBookTitle(bookTitle);
        criteria.setCategoryName(categoryName);
        criteria.setIsbn(isbn);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity requestBody = new HttpEntity<>(headers);

        System.out.println("com.b : " + requestBody.toString());

        URI uri = new URI(baseUrl + "/api/user/books?page=" + p + "&size=" + s);
//        Class<Page<Book>> responseType;

//        ResponseEntity<Page<Book>> r = restTemplate.exchange(uri, HttpMethod.GET, entity, responseType.class);
        ParameterizedTypeReference<RestResponsePage<Book>> responseType = new ParameterizedTypeReference<RestResponsePage<Book>>() {
        };

        ResponseEntity<RestResponsePage<Book>> result = restTemplate.exchange(uri, HttpMethod.GET, requestBody, responseType);

        RestResponsePage<Book> searchResult = result.getBody();

        return result.getBody();

    }

}
