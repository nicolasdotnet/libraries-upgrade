package com.bigcity.apiweb;

import com.bigcity.apiweb.dao.IBookRepository;
import com.bigcity.apiweb.dao.IBookingRepository;
import com.bigcity.apiweb.dto.BookCategoryDTO;
import com.bigcity.apiweb.dto.BookDTO;
import com.bigcity.apiweb.dto.BookingDTO;
import com.bigcity.apiweb.dto.UserDTO;
import com.bigcity.apiweb.entity.Book;
import com.bigcity.apiweb.entity.BookCategory;
import com.bigcity.apiweb.entity.Booking;
import com.bigcity.apiweb.entity.User;
import com.bigcity.apiweb.exceptions.BookingNotPossibleException;
import com.bigcity.apiweb.services.interfaces.IBookCategoryService;
import com.bigcity.apiweb.services.interfaces.IBookService;
import com.bigcity.apiweb.services.interfaces.IBookingService;
import com.bigcity.apiweb.services.interfaces.IRoleService;
import com.bigcity.apiweb.services.interfaces.IUserService;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
public class ApiWebApplication extends SpringBootServletInitializer implements CommandLineRunner {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IRoleService iUserCategoryService;

    @Autowired
    private IBookService iBookService;

    @Autowired
    private IBookCategoryService iBookCategoryService;

    @Autowired
    private IBookingService iBookingService;

    @Autowired
    private IBookingRepository iBookingRepository;

    @Autowired
    private IBookRepository iBookRepository;

    public static void main(String[] args) {
        SpringApplication.run(ApiWebApplication.class, args);
    }

    public void run(String... args) throws Exception {

        // register default client for reservation
        // register default client for reservation
        String firstname = "nicole";
        String lastname = "desdevises";
        String password = "123";
        String email = "usager2@mail.com";

        UserDTO uV3 = new UserDTO();

        uV3.setFirstname(firstname);
        uV3.setLastname(lastname);
        uV3.setPassword(password);
        uV3.setEmail(email);

        User u3 = iUserService.registerByDefault(uV3);

        // register default client 2 for reservation
        firstname = "Alain";
        lastname = "desdevises";
        password = "123";
        email = "usager3@mail.com";

        UserDTO uV4 = new UserDTO();

        uV4.setFirstname(firstname);
        uV4.setLastname(lastname);
        uV4.setPassword(password);
        uV4.setEmail(email);

        User u4 = iUserService.registerByDefault(uV4);

        // register one bookCategory
        String bookCategoryLabel = "Histoire";

        BookCategoryDTO bookCategoryDTO = new BookCategoryDTO();

        bookCategoryDTO.setLabel(bookCategoryLabel);

        BookCategory bc = iBookCategoryService.register(bookCategoryDTO);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");

        System.out.println("\n register bookCategory : " + bc.toString() + "\n");

        // register one book
        String isbn = "56135289";
        String author = "Nicole Thebest";
        String bookTitle = "Histoire du Java";
        int copiesAvailable = 5;
        String bcLabel = "Histoire";
        String resume = "Vol : Le dev ninja délivre ses conseils pour savoir repérer les beaux codes du XXIe siècle : "
                + "script contemporains, hi-fi d'antan, nouvelles matières comme le Formica ou luminaires, modèles iconiques.";

        BookDTO bookDTO = new BookDTO();

        bookDTO.setIsbn(isbn);
        bookDTO.setAuthor(author);
        bookDTO.setBookTitle(bookTitle);
        bookDTO.setCopiesAvailable(copiesAvailable);
        bookDTO.setBookCategoryLabel(bcLabel);
        bookDTO.setSummary(resume);

        Book b = iBookService.register(bookDTO);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");

        System.out.println("\n register book : " + b.toString() + "\n");

        // get book
        Book getbook = null;

        try {

            getbook = iBookService.getBook(21L);

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");

            System.out.println("\n get book ID : " + getbook.toString() + "\n");

        } catch (Exception e) {

        }

        // register one booking
        String librarianEmail = uV3.getEmail();
        String bookingEmail = uV3.getEmail();
        String bookIsbn = b.getIsbn();

        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setLibrarianEmail(librarianEmail);
        bookingDTO.setBookingUserEmail(bookingEmail);
        bookingDTO.setBookIsbn(bookIsbn);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");
        System.out.println(">>>>>>>>>>>>>>>Copie du Book avant booking -> : " + b.getCopiesAvailable());

        Booking r = iBookingService.register(bookingDTO);

        // get booking 
        Booking booking = iBookingService.getBooking(22L);

        // créa date : 
        LocalDate localdate = LocalDate.now().minusDays(29L);
        Date dateEnd = java.sql.Date.valueOf(localdate);
        
        booking.setBookingEndDate(dateEnd);
        iBookingRepository.saveAndFlush(booking);

        System.out.println("DATE END BOOKING : " + booking.getBookingEndDate());
        System.out.println("DATE TEST  : " + dateEnd);

        try {
            
            iBookingService.extendBooking(22L);
            
        } catch (Exception e) {
            
            System.err.println("Prolonger le prêt après date de finde prêt : "+e.getMessage());
        }
        

//        if (booking.getBookingEndDate().before(dateEnd)) {
//
//            throw new BookingNotPossibleException("La date de fin de prêt est dépassé ! Vous ne pouvez pas le prolonger");
//
//        }
    }
}
