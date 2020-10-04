package com.bigcity.apiweb;

import com.bigcity.apiweb.dao.IBookingRepository;
import com.bigcity.apiweb.dto.BookCategoryDTO;
import com.bigcity.apiweb.dto.BookDTO;
import com.bigcity.apiweb.dto.BookingDTO;
import com.bigcity.apiweb.dto.LoginDTO;
import com.bigcity.apiweb.specifications.BookCriteria;
import com.bigcity.apiweb.dto.UserDTO;
import com.bigcity.apiweb.entity.Book;
import com.bigcity.apiweb.entity.BookCategory;
import com.bigcity.apiweb.entity.Booking;
import com.bigcity.apiweb.entity.BookingStatus;
import com.bigcity.apiweb.entity.Role;
import com.bigcity.apiweb.entity.User;
import com.bigcity.apiweb.services.interfaces.IBookCategoryService;
import com.bigcity.apiweb.services.interfaces.IBookService;
import com.bigcity.apiweb.services.interfaces.IRoleService;
import com.bigcity.apiweb.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.List;
import com.bigcity.apiweb.services.interfaces.IBookingService;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
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

    @Autowired
    private IBookingRepository iBookingRepository;

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
        String email = "employe@mail.com";

        UserDTO uV1 = new UserDTO();

        uV1.setFirstname(firstname);
        uV1.setLastname(lastname);
        uV1.setPassword(password);
        uV1.setEmail(email);

        
        //registerEmploye
        User u = iUserService.registerForMembre(uV1);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");

        System.out.println("\n register admin : " + u.toString() + "\n");

        // register default User / Client
        firstname = "laure";
        lastname = "desdevises";
        password = "123";
        email = "usager@mail.com";

        UserDTO uV2 = new UserDTO();

        uV2.setFirstname(firstname);
        uV2.setLastname(lastname);
        uV2.setPassword(password);
        uV2.setEmail(email);

        User u2 = iUserService.registerByDefault(uV2);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");

        System.out.println("\n register usagé : " + u2.toString() + "\n");

        // register membre admin
        firstname = "root";
        lastname = "admin";
        password = "123";
        email = "admin@mail.com";

        UserDTO uVa = new UserDTO();

        uVa.setFirstname(firstname);
        uVa.setLastname(lastname);
        uVa.setPassword(password);
        uVa.setEmail(email);

        User ua = iUserService.registerForMembre(uVa);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");

        System.out.println("\n register admin : " + ua.toString() + "\n");

        // register one bookCategory
        String bookCategoryLabel = "Polar";

        BookCategoryDTO bookCategoryDTO = new BookCategoryDTO();

        bookCategoryDTO.setLabel(bookCategoryLabel);

        BookCategory bc = iBookCategoryService.register(bookCategoryDTO);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");

        System.out.println("\n register bookCategory : " + bc.toString() + "\n");

        // register one more bookCategory
        bookCategoryLabel = "Science";

        bookCategoryDTO = new BookCategoryDTO();

        bookCategoryDTO.setLabel(bookCategoryLabel);

        bc = iBookCategoryService.register(bookCategoryDTO);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");

        System.out.println("\n register bookCategory : " + bc.toString() + "\n");

        // register one book
        int x = 1;
        String bookTitle;

        Book b;

        do {
            String isbn = "561352" + x;
            String author = "Rowston Thebest";
            if (x < 2) {

                bookTitle = "Java pour les nuls";

            } else {
                bookTitle = "Java pour les nuls Volume " + x;
            }
            int copiesAvailable = 5;
            String bcLabel = "Polar";
            String resume = "Vol " + x + "  : Le dev ninja délivre ses conseils pour savoir repérer les beaux codes du XXIe siècle : "
                    + "script contemporains, hi-fi d'antan, nouvelles matières comme le Formica ou luminaires, modèles iconiques.";

            BookDTO bookDTO = new BookDTO();

            bookDTO.setIsbn(isbn);
            bookDTO.setAuthor(author);
            bookDTO.setBookTitle(bookTitle);
            bookDTO.setCopiesAvailable(copiesAvailable);
            bookDTO.setBookCategoryLabel(bcLabel);
            bookDTO.setSummary(resume);

            b = iBookService.register(bookDTO);

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");

            System.out.println("\n register book : " + b.toString() + "\n");

            x++;

        } while (x < 4);

        // get book
        Book getbook = null;

        try {

            getbook = iBookService.getBook(5L);

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");

            System.out.println("\n get book ID : " + getbook.toString() + "\n");

        } catch (Exception e) {

        }

        // register seconde book => error
        String isbn = "561352";
        String author = "Rowston Thebest";
        bookTitle = "Java pour les nuls";
        int copiesAvailable = 5;
        String resume = "Le commissaire-priseur délivre ses conseils pour savoir repérer les beaux objets du XXe siècle :"
                + " céramique et mobilier contemporains, hi-fi d'antan, nouvelles matières comme le Formica ou luminaires, modèles iconiques.";

        BookDTO bookDTO2 = new BookDTO();
        Book b2;

        bookDTO2.setIsbn(isbn);
        bookDTO2.setAuthor(author);
        bookDTO2.setBookTitle(bookTitle);
        bookDTO2.setCopiesAvailable(copiesAvailable);
        bookDTO2.setSummary(resume);

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
        String bcLabel = "Science";
        resume = "Une présentation des techniques de base de dessin et du matériel,"
                + " et des projets exposés pas à pas pour découvrir des techniques particulières.";

        BookDTO bookDTO3 = new BookDTO();
        Book b3 = null;

        bookDTO3.setIsbn(isbn);
        bookDTO3.setAuthor(author);
        bookDTO3.setBookTitle(bookTitle);
        bookDTO3.setCopiesAvailable(copiesAvailable);
        bookDTO3.setBookCategoryLabel(bcLabel);
        bookDTO3.setSummary(resume);

        try {

            b3 = iBookService.register(bookDTO3);

        } catch (Exception e) {

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");
            System.out.println("book 3 : " + e.getMessage());

        }

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");

        System.out.println("\n register book 3 : " + b3.toString() + "\n");

        // register quatre book
        isbn = "56135300";
        author = "Peter Bishop";
        bookTitle = "Vive le quantique";
        copiesAvailable = 5;
        bcLabel = "Science";
        resume = "Une présentation des techniques de base,"
                + " et des projets exposés pas à pas pour découvrir des techniques de physique quantique.";

        BookDTO bookDTO4 = new BookDTO();
        Book b4 = null;

        bookDTO4.setIsbn(isbn);
        bookDTO4.setAuthor(author);
        bookDTO4.setBookTitle(bookTitle);
        bookDTO4.setCopiesAvailable(copiesAvailable);
        bookDTO4.setBookCategoryLabel(bcLabel);
        bookDTO4.setSummary(resume);

        try {

            b4 = iBookService.register(bookDTO4);

        } catch (Exception e) {

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");
            System.out.println("book 4 : " + e.getMessage());

        }

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");

        System.out.println("\n register book 4 : " + b4.toString() + "\n");

        // register five book
        isbn = "561353000";
        author = "Walter Bishop";
        bookTitle = "Carnet de sciences";
        copiesAvailable = 5;
        bcLabel = "Science";
        resume = "Carnet de travail des techniques alternatives,"
                + " et des projets exposés pas à pas pour découvrir des techniques de physique avancées.";

        BookDTO bookDTO5 = new BookDTO();
        Book b5 = null;

        bookDTO5.setIsbn(isbn);
        bookDTO5.setAuthor(author);
        bookDTO5.setBookTitle(bookTitle);
        bookDTO5.setCopiesAvailable(copiesAvailable);
        bookDTO5.setBookCategoryLabel(bcLabel);
        bookDTO5.setSummary(resume);

        try {

            b5 = iBookService.register(bookDTO5);

        } catch (Exception e) {

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");
            System.out.println("book 5 : " + e.getMessage());

        }

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");

        System.out.println("\n register book 5 : " + b5.toString() + "\n");

        // register old booking
        String librarianEmail = u.getEmail();
        String bookingEmail = u2.getEmail();
        String bookIsbn = b5.getIsbn();

        BookingDTO bookingDTOOld = new BookingDTO();

        bookingDTOOld.setLibrarianEmail(librarianEmail);
        bookingDTOOld.setBookingUserEmail(bookingEmail);
        bookingDTOOld.setBookIsbn(bookIsbn);

        Booking old = iBookingService.register(bookingDTOOld);

        Date bookingStartDate = new SimpleDateFormat("yyyy-MM-dd").parse("2020-02-14");
        old.setBookingStartDate(bookingStartDate);

        Date bookingEndDate = new SimpleDateFormat("yyyy-MM-dd").parse("2020-02-28");
        old.setBookingEndDate(bookingEndDate);

        iBookingRepository.saveAndFlush(old);

        // register old booking 2 with termine
        bookingDTOOld.setLibrarianEmail(librarianEmail);
        bookingDTOOld.setBookingUserEmail(bookingEmail);
        bookingDTOOld.setBookIsbn("5613521");
        
        old = iBookingService.register(bookingDTOOld);
        
        old.setBookingStartDate(bookingStartDate);
        old.setBookingEndDate(bookingEndDate);
        old.setBookingStatus(BookingStatus.TERMINE.getValue());
        
        iBookingRepository.saveAndFlush(old);

        // register one booking
        librarianEmail = u.getEmail();
        bookingEmail = u2.getEmail();
        bookIsbn = b.getIsbn();

        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setLibrarianEmail(librarianEmail);
        bookingDTO.setBookingUserEmail(bookingEmail);
        bookingDTO.setBookIsbn(bookIsbn);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");
        System.out.println(">>>>>>>>>>>>>>>Book avant booking -> : " + b.getCopiesAvailable());

        Booking r = iBookingService.register(bookingDTO);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");

        System.out.println("\n AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA get booking ID : " + r.toString() + "\n");

        // register second booking => error
        try {
            iBookingService.register(bookingDTO);

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

        System.out.println("Book après booking -> : " + iBookService.getBook(b.getBookId()).getCopiesAvailable());

        // book search by MC
        BookCriteria bsdto = new BookCriteria();

        author = "";
        bookTitle = "";
        isbn = "";
        bookCategoryLabel = "science";
        int page = 0;
        int size = 5;

        bsdto.setAuthor(author);
        bsdto.setTitle(bookTitle);
        bsdto.setIsbn(isbn);

        Page<Book> l = iBookService.getAllBooksByCriteria(isbn, author, bookTitle, bookCategoryLabel, page, size);

        if (l.isEmpty()) {

            System.out.println("PAS DE RESULTAT");
        }

        for (Book book : l) {

            System.out.println("book -> : " + book.toString());

        }

        LoginDTO loginDTO = new LoginDTO();

        loginDTO.setEmail("nicolas.desdevises@yahoo.com");
        loginDTO.setPassword("123");

        User z = iUserService.login(loginDTO);

        if (z == null) {

            System.out.println(">>>>>>>>>>>>>>>>>< LOGIN IS NOT OK !");

        } else {

            System.out.println(">>>>>>>>>>>>>>>>>< LOGIN IS OK !");

        }

        // Liste booking relance
        librarianEmail = u.getEmail();
        bookingEmail = u2.getEmail();

        bookingDTO = new BookingDTO();

        bookingDTO.setLibrarianEmail(librarianEmail);
        bookingDTO.setBookingUserEmail(bookingEmail);
        bookingDTO.setBookIsbn("56135300");

        r = iBookingService.register(bookingDTO);

    }

//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        return bCryptPasswordEncoder;
//    }
}
