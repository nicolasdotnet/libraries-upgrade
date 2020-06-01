package com.bigcity;

import com.bigcity.dto.BookDTO;
import com.bigcity.dto.UserDTO;
import com.bigcity.entity.Book;
import com.bigcity.entity.Booking;
import com.bigcity.entity.Role;
import com.bigcity.entity.User;
import com.bigcity.services.interfaces.IBookService;
import com.bigcity.services.interfaces.IRoleService;
import com.bigcity.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import com.bigcity.specifications.BookingSpecification;
import java.time.LocalDate;
import java.util.List;
import com.bigcity.services.interfaces.IBookingService;

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
        String username = "nico";
        String password = "123";
        String email = "nicolas.desdevises@yahoo.com";

        UserDTO uV1 = new UserDTO();

        uV1.setFirstname(firstname);
        uV1.setLastname(lastname);
        uV1.setUsername(username);
        uV1.setPassword(password);
        uV1.setEmail(email);

        User u = iUserService.registerForMembre(uV1);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");

        System.out.println("\n register : " + u.toString() + "\n");

        // register default User
        firstname = "laure";
        lastname = "desdevises";
        username = "laure";
        password = "123";
        email = "laure@yahoo.com";

        UserDTO uV2 = new UserDTO();

        uV2.setFirstname(firstname);
        uV2.setLastname(lastname);
        uV2.setUsername(username);
        uV2.setPassword(password);
        uV2.setEmail(email);

        User u2 = iUserService.registerByDefault(uV2);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");

        System.out.println("\n register : " + u2.toString() + "\n");

        // register one book
        String isbn = "561352";
        String authorFirstname = "Rowston";
        String authorLastname = "Thebest";
        String bookTitle = "Java pour les nuls";
        int copiesAvailable = 5;

        BookDTO bookDTO = new BookDTO();

        bookDTO.setIsbn(isbn);
        bookDTO.setAuthorFirstname(authorFirstname);
        bookDTO.setAuthorLastname(authorLastname);
        bookDTO.setBookTitle(bookTitle);
        bookDTO.setCopiesAvailable(copiesAvailable);

        Book b = iBookService.register(bookDTO);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");

        System.out.println("\n register : " + b.toString() + "\n");

        // get book
        Book getbook = null;

        try {

            getbook = iBookService.getBook(5L);

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");

            System.out.println("\n get ID : " + getbook.toString() + "\n");

        } catch (Exception e) {

        }

        // register one booking
        Long librarianId = u.getUserId();
        Long bookingUserId = u2.getUserId();
        Long bookId = b.getBookId();

        Booking r = iBookingService.register(librarianId, bookingUserId, bookId);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");

        System.out.println("\n get ID : " + r.toString() + "\n");

        // register one booking => error
        try {
            iBookingService.register(librarianId, bookingUserId, bookId);

        } catch (Exception e) {

        }

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");

        System.out.println("\n get ID : " + r.toString() + "\n");

        try {
            List<Booking> l = iBookingService.getOutdatedBookingList();

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");

            System.out.println("\n get ID : " + l.toString() + "\n");

        } catch (Exception e) {

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");
            System.out.println("com.bigcity.ApiWebApplication.run() : " + e.getMessage());

        }

        System.out.println("equal date ? booking : " + r.getBookingEndDate() + "/ datenow : " + LocalDate.now());

        try {
            List userBookings = iBookingService.getAllBookingByUser(u2.getUsername());

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
        
        System.out.println("PRET -> extension :"+r.getCounterExtension()+" date de fin : "+r.getBookingEndDate());
    }
}
