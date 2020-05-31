package com.bigcity;

import com.bigcity.dto.UserDTO;
import com.bigcity.entity.Role;
import com.bigcity.entity.User;
import com.bigcity.services.interfaces.IRoleService;
import com.bigcity.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ApiWebApplication extends SpringBootServletInitializer implements CommandLineRunner {

    // ou méthode CommandLineRunner avec @Bean
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IRoleService iUserCategoryService;

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

        iUserService.registerByDefault(uV2);

    }
}
