package com.bigcity.appweb.services;

import com.bigcity.appweb.beans.Book;
import com.bigcity.appweb.beans.Booking;
import com.bigcity.appweb.beans.Reservation;
import com.bigcity.appweb.services.interfaces.IBookService;
import com.bigcity.appweb.services.interfaces.IBookingService;
import com.bigcity.appweb.services.interfaces.IReservationService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
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
import org.springframework.security.core.Authentication;
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
    private IBookingService iBookingService;

    @Autowired
    private IReservationService iReservationService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.server.port}")
    private String serverPort;

    @Value("${baseUrl}")
    private String baseUrl;

    private HttpHeaders headers = new HttpHeaders();

    @Override
    public List<Book> getAllBooks(Authentication authentication) throws URISyntaxException, RestClientException {

        URI uri = new URI(baseUrl + serverPort + "/api/user/books/all");

        headers.setBasicAuth(authentication.getName(), authentication.getCredentials().toString());

        HttpEntity requestEntity = new HttpEntity(headers);

        ResponseEntity<List<Book>> result = null;

        result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Book>>() {
        });

        return result.getBody();

    }

    @Override
    public Book getBook(Long id, Authentication authentication) throws URISyntaxException, RestClientException {

        URI uri = new URI(baseUrl + serverPort + "/api/user/books/" + id);

        headers.setBasicAuth(authentication.getName(), authentication.getCredentials().toString());

        HttpEntity requestEntity = new HttpEntity(headers);

        ResponseEntity<Book> result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, Book.class);

        return result.getBody();

    }

    @Override
    public Book getBookByIsbn(String isbn, Authentication authentication) throws URISyntaxException, RestClientException {

        URI uri = new URI(baseUrl + serverPort + "/api/user/books/" + isbn);

        headers.setBasicAuth(authentication.getName(), authentication.getCredentials().toString());

        HttpEntity requestEntity = new HttpEntity(headers);

        ResponseEntity<Book> bookFind = null;

        bookFind = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, Book.class);

        //        doit y avoir la date de retour prévue la plus proche et le nombre de personnes ayant réservé l’ouvrage.
//                Si la liste d’attente 
//                de l’ouvrage n’est pas complète, il doit pouvoir demander une réservation.
        // booking = retour plus proche : get bookingencoursbybook
        // réservation : + n° personne ayant réservé l'ouvrage : getresebybook
        if (bookFind.getBody().getCopiesAvailable() == 0) {

            List<Booking> bookingsFind = iBookingService.getAllCurrentBookingByBook(isbn, authentication);
            
            Collections.sort(bookingsFind, Booking.ComparatorBookingEndDate);
            bookFind.getBody().setReturnDateBook(bookingsFind.get(0).getBookingEndDate());
            

            List<Reservation> reservationsFind = iReservationService.getAllReservationByBook(isbn, authentication);
            bookFind.getBody().setNumberCurrentReservations(String.valueOf(reservationsFind.size()));

        }

        return bookFind.getBody();

    }

    @Override
    public Page<Book> getAllBooksByCriteria(String isbn, String author, String bookTitle, String categoryName, int p, int s, Authentication authentication)
            throws URISyntaxException, RestClientException {

        URI uri = new URI(baseUrl + serverPort + "/api/user/books?isbn=" + isbn + "&author=" + author + "&bookTitle=" + bookTitle
                + "&categoryName=" + categoryName + "&page=" + p + "&size=" + s);

        headers.setBasicAuth(authentication.getName(), authentication.getCredentials().toString());

        HttpEntity requestEntity = new HttpEntity(headers);

        ResponseEntity<RestResponsePage<Book>> result = null;

        ParameterizedTypeReference<RestResponsePage<Book>> responseType = new ParameterizedTypeReference<RestResponsePage<Book>>() {
        };

        result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, responseType);

        return result.getBody();

    }

}
