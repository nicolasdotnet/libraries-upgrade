package com.bigcity;

import com.bigcity.dto.BookCategoryDTO;
import com.bigcity.dto.BookDTO;
import com.bigcity.specifications.BookCriteria;
import com.bigcity.dto.UserDTO;
import com.bigcity.entity.Book;
import com.bigcity.entity.BookCategory;
import com.bigcity.entity.Booking;
import com.bigcity.entity.Role;
import com.bigcity.entity.User;
import com.bigcity.services.interfaces.IBookCategoryService;
import com.bigcity.services.interfaces.IBookService;
import com.bigcity.services.interfaces.IRoleService;
import com.bigcity.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.time.LocalDate;
import java.util.List;
import com.bigcity.services.interfaces.IBookingService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableSwagger2
public class ApiWebApplication extends SpringBootServletInitializer implements CommandLineRunner {

    // ou méthode CommandLineRunner avec @Bean
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

    public static void main(String[] args) {
        SpringApplication.run(ApiWebApplication.class, args);
    }

    public void run(String... args) throws Exception {

        // insert first data
        // register Role
        String label = "bibliothécaire";

        Role uc1 = new Role();

        uc1 = iUserCategoryService.register(label);

        System.out.println(">>>>>>>>>>>>" + uc1.toString() + ">>>>>>>>>><");
        System.out.println(">>>>>>>>>>>>" + uc1.getRoleId() + ">>>>>>>>>><");

        // register Role
        label = "usager";

        Role uc2 = new Role();

        uc2 = iUserCategoryService.register(label);

        // method execution
        // register membre User
        String firstname = "nicolas";
        String lastname = "desdevises";
        String password = "123";
        String email = "nicolas.desdevises@yahoo.com";

        UserDTO uV1 = new UserDTO();

        uV1.setFirstname(firstname);
        uV1.setLastname(lastname);
        uV1.setPassword(password);
        uV1.setEmail(email);

        User u = iUserService.registerForMembre(uV1);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");

        System.out.println("\n register admin : " + u.toString() + "\n");

        // register default User
        firstname = "laure";
        lastname = "desdevises";
        password = "123";
        email = "laure@yahoo.com";

        UserDTO uV2 = new UserDTO();

        uV2.setFirstname(firstname);
        uV2.setLastname(lastname);
        uV2.setPassword(password);
        uV2.setEmail(email);

        User u2 = iUserService.registerByDefault(uV2);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");

        System.out.println("\n register usagé : " + u2.toString() + "\n");

        // register one bookCategory
        String bookCategoryLabel = "Polar";

        BookCategoryDTO bookCategoryDTO = new BookCategoryDTO();

        bookCategoryDTO.setLabel(bookCategoryLabel);

        BookCategory bc = iBookCategoryService.register(bookCategoryDTO);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");

        System.out.println("\n register bookCategory : " + bc.toString() + "\n");

        // register one book
        String isbn = "561352";
        String author = "Rowston Thebest";
        String bookTitle = "Java pour les nuls";
        int copiesAvailable = 5;
        String bcLabel = "Polar";

        BookDTO bookDTO = new BookDTO();

        bookDTO.setIsbn(isbn);
        bookDTO.setAuthor(author);
        bookDTO.setBookTitle(bookTitle);
        bookDTO.setCopiesAvailable(copiesAvailable);
        bookDTO.setBookCategoryLabel(bcLabel);

        Book b = iBookService.register(bookDTO);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");

        System.out.println("\n register book : " + b.toString() + "\n");

        // get book
        Book getbook = null;

        try {

            getbook = iBookService.getBook(5L);

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");

            System.out.println("\n get book ID : " + getbook.toString() + "\n");

        } catch (Exception e) {

        }

        // register seconde book => error
        isbn = "561352";
        author = "Rowston Thebest";
        bookTitle = "Java pour les nuls";
        copiesAvailable = 5;

        BookDTO bookDTO2 = new BookDTO();
        Book b2;

        bookDTO2.setIsbn(isbn);
        bookDTO2.setAuthor(author);
        bookDTO2.setBookTitle(bookTitle);
        bookDTO2.setCopiesAvailable(copiesAvailable);

        try {

            b2 = iBookService.register(bookDTO2);

        } catch (Exception e) {

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");
            System.out.println("book 2 : " + e.getMessage());

        }

        // register trois book
        isbn = "561353";
        author = "Nicolas Junior";
        bookTitle = "Ma vie de dev";
        copiesAvailable = 5;
        bcLabel = "Polar";

        BookDTO bookDTO3 = new BookDTO();
        Book b3 = null;

        bookDTO3.setIsbn(isbn);
        bookDTO3.setAuthor(author);
        bookDTO3.setBookTitle(bookTitle);
        bookDTO3.setCopiesAvailable(copiesAvailable);
        bookDTO3.setBookCategoryLabel(bcLabel);

        try {

            b3 = iBookService.register(bookDTO3);

        } catch (Exception e) {

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");
            System.out.println("book 3 : " + e.getMessage());

        }

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");

        System.out.println("\n register book 3 : " + b3.toString() + "\n");

        // register one booking
        Long librarianId = u.getUserId();
        Long bookingUserId = u2.getUserId();
        Long bookId = b.getBookId();

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");
        System.out.println(">>>>>>>>>>>>>>>Book avant booking -> : " + b.getCopiesAvailable());

        Booking r = iBookingService.register(librarianId, bookingUserId, bookId);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");

        System.out.println("\n AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA get booking ID : " + r.toString() + "\n");

        // register second booking => error
        try {
            iBookingService.register(librarianId, bookingUserId, bookId);

        } catch (Exception e) {

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");
            System.out.println("booking 2 : " + e.getMessage());

        }

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");

        System.out.println("\n get booking ID : " + r.toString() + "\n");

        try {
            List userBookings = iBookingService.getAllBookingByUser(u2.getEmail());

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");

            System.out.println("\n get ID : " + userBookings.isEmpty() + "\n");

        } catch (Exception e) {

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");
            System.out.println("com.bigcity.ApiWebApplication.run() : " + e.getMessage());

        }

        try {
            System.out.println("com.bigcity.ApiWebApplication.run() EXTEND 1");
            iBookingService.extendBooking(r.getBookingId());
        } catch (Exception e) {

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");
            System.out.println("error 1: " + e.getMessage());

        }

        try {
            System.out.println("com.bigcity.ApiWebApplication.run() EXTEND 2");
            iBookingService.extendBooking(r.getBookingId());
        } catch (Exception e) {

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");
            System.out.println("error 2 : " + e.getMessage());

        }

        r = iBookingService.getBooking(r.getBookingId());

        System.out.println("PRET -> extension :" + r.getCounterExtension() + " date de fin : " + r.getBookingEndDate());

        System.out.println("Book après booking -> : " + iBookService.getBook(bookId).getCopiesAvailable());

        // book search by MC
        BookCriteria bsdto = new BookCriteria();
        
        author = "Nicolas";
        bookTitle = "";
        isbn = "";
        
        bsdto.setAuthor(author);
        bsdto.setBookTitle(bookTitle);
        bsdto.setIsbn(isbn);

//        List<Book> l = iBookService.getAllBooksByCriteria(bsdto);
        
//        if (l.isEmpty()) {
//            
//            System.out.println("PAS DE RESULTAT");
//        }
        
//        for (Book book : l) {
//            
//            System.out.println("book -> : "+book.toString());
//            
//        }

    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
}
