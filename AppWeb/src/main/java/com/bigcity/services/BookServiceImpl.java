package com.bigcity.services;

import com.bigcity.beans.Book;
import com.bigcity.services.interfaces.IBookService;
import java.net.URI;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
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

    @Value("${api.server.port}")
    private String serverPort;

    private String baseUrl = "http://localhost:";

    private HttpHeaders headers = new HttpHeaders();

    @Override
    public List<Book> getAllBooks() throws Exception {

        URI uri = new URI(baseUrl + serverPort + "/api/user/books/all");

// add basic authentication header
        headers.setBasicAuth("nicolas.desdevises@yahoo.com", "123");

        HttpEntity requestEntity = new HttpEntity(headers);

        ResponseEntity<List<Book>> result = null;

        try {

            result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Book>>() {
            });

        } catch (RestClientException e) {

            throw new Exception(e.getMessage());

        }

        return result.getBody();

    }

    @Override
    public Book getBook(Long id) throws Exception {

        URI uri = new URI(baseUrl + serverPort + "/api/user/books/" + id);

        // add basic authentication header
        headers.setBasicAuth("nicolas.desdevises@yahoo.com", "123");

        HttpEntity requestEntity = new HttpEntity(headers);

        ResponseEntity<Book> result = null;

        try {

            result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, Book.class);

        } catch (RestClientException e) {

            throw new Exception(e.getMessage());

        }

        return result.getBody();

    }

    @Override
    public Book getBookByIsbn(String isbn) throws Exception {

        URI uri = new URI(baseUrl + serverPort + "/api/user/books/" + isbn);

        // add basic authentication header
        headers.setBasicAuth("nicolas.desdevises@yahoo.com", "123");

        HttpEntity requestEntity = new HttpEntity(headers);

        ResponseEntity<Book> result = null;

        try {

            result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, Book.class);

        } catch (RestClientException e) {

            throw new Exception(e.getMessage());

        }

        return result.getBody();

    }

    @Override
    public Page<Book> getAllBooksByCriteria(String isbn, String author, String bookTitle, String categoryName, int p, int s) throws Exception {

        URI uri = new URI(baseUrl + serverPort + "/api/user/books?isbn=" + isbn + "&author=" + author + "&bookTitle=" + bookTitle
                + "&categoryName" + categoryName + "&page=" + p + "&size=" + s);

        // add basic authentication header
        headers.setBasicAuth("nicolas.desdevises@yahoo.com", "123");

        HttpEntity requestEntity = new HttpEntity(headers);

        ResponseEntity<RestResponsePage<Book>> result = null;

        ParameterizedTypeReference<RestResponsePage<Book>> responseType = new ParameterizedTypeReference<RestResponsePage<Book>>() {
        };

        try {
            result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, responseType);
        } catch (RestClientException e) {

            throw new Exception(e.getMessage());

        }

        return result.getBody();

    }

}
