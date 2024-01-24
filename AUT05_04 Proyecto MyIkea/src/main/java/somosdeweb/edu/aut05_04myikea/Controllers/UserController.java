package somosdeweb.edu.aut05_04myikea.Controllers;


import org.springframework.web.bind.annotation.*;
import somosdeweb.edu.aut05_04myikea.Models.User;
import somosdeweb.edu.aut05_04myikea.Services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("register")
    public String registrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("register")
    public String registerUser(@ModelAttribute("user") User user, @RequestParam("confirmPassword") String confirmPassword, Model model) {
        // Verificar si las contraseñas coinciden
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("passwordMismatch", true);
            return "register";
        }

        logger.info("Registrando nuevo usuario: {}", user.getUsername());
        userService.createUser(user);
        logger.info("Usuario registrado exitosamente");
        return "redirect:/login";
    }

    @GetMapping("login")
    public String loginPage() {
        logger.info("Accediendo a la página de inicio de sesión");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }

    @GetMapping("/Usuarios")
    public String listarUsuarios(Model model) {
        List<User> usuarios = userService.listarUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "Usuarios/ListarUsuarios";
    }

    @PostMapping("/Usuarios/Eliminar/{id}")
    public String eliminarUsuario(@PathVariable("id") Long userId) {
        userService.eliminarUsuario(userId);
        return "redirect:/Usuarios";
    }
}
