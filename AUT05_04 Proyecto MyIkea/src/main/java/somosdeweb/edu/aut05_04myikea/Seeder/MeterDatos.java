package somosdeweb.edu.aut05_04myikea.Seeder;

import somosdeweb.edu.aut05_04myikea.Models.Producto;
import somosdeweb.edu.aut05_04myikea.Models.Roles;
import somosdeweb.edu.aut05_04myikea.Models.User;
import somosdeweb.edu.aut05_04myikea.Services.UserService;



import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class MeterDatos {

    private final UserService userService;

    @Autowired
    public MeterDatos(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void MeterDatos() {
        if (userService.getAllUsers().isEmpty()){
            seedUsuarios();
        }
    }



    private void seedUsuarios() {

        Roles userRole = userService.getOrCreateRole("ROLE_USER");
        if (userRole == null) {
            userRole = new Roles("ROLE_USER");
            userService.saveRole(userRole);
        }

        Roles adminRole = userService.getOrCreateRole("ROLE_ADMIN");
        if (adminRole == null) {
            adminRole = new Roles("ROLE_ADMIN");
            userService.saveRole(adminRole);
        }

        Roles managerRole = userService.getOrCreateRole("ROLE_MANAGER");
        if (managerRole == null) {
            managerRole = new Roles("ROLE_MANAGER");
            userService.saveRole(managerRole);
        }

        User user1 = new User();
        user1.setUsername("User");
        user1.setUserMail("User@myikea.com");
        user1.setPassword("Qwerty1234");
        user1.setRoles(new HashSet<>(Collections.singletonList(userRole)));  // Usar una lista para asegurar independencia
        userService.createUser(user1);

        User user2 = new User();
        user2.setUsername("Admin");
        user2.setUserMail("Admin@myikea.com");
        user2.setPassword("Qwerty1234");
        Set<Roles> rolesUsuario2 = new HashSet<>(Arrays.asList(adminRole));
        user2.setRoles(rolesUsuario2);

        userService.createUser(user2);

        User user3 = new User();
        user3.setUsername("Manager");
        user3.setUserMail("Manager@myikea.com");
        user3.setPassword("Qwerty1234");
        user3.setRoles(new HashSet<>(Collections.singletonList(managerRole)));
        userService.createUser(user3);

    }


}
