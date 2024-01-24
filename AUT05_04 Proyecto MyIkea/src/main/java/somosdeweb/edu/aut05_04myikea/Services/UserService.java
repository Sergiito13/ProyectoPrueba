package somosdeweb.edu.aut05_04myikea.Services;

import somosdeweb.edu.aut05_04myikea.Models.Roles;
import somosdeweb.edu.aut05_04myikea.Models.User;
import somosdeweb.edu.aut05_04myikea.Repositories.RoleRepository;
import somosdeweb.edu.aut05_04myikea.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Primary
@Service
public class UserService implements UserDetailsService {

    @Lazy
    @Autowired
    private UserRepository userRepository;

    @Lazy
    @Autowired
    private RoleRepository roleRepository;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public User createUser(User newUser) {
        System.out.println(newUser.getUsername());
        System.out.println(newUser.getRoles().toString());

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        if (newUser.getRoles() == null || newUser.getRoles().isEmpty()) {
            Set<Roles> defaultRoles = new HashSet<>();
            defaultRoles.add(getOrCreateRole("ROLE_USER"));
            newUser.setRoles(defaultRoles);
        }

        return userRepository.save(newUser);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> listarUsuarios() {
        return userRepository.findAll();
    }

    public void eliminarUsuario(Long userId) {
        userRepository.deleteById(userId);
    }

    public User findByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.orElse(null);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Roles> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUserMail(usernameOrEmail);

        if (userOptional.isPresent()) {
            // Se encontró el usuario por correo electrónico
            User user = userOptional.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .authorities(getAuthorities(user.getRoles()))
                    .build();
        } else {
            // No se encontró el usuario por correo electrónico, intenta por nombre de usuario
            Optional<User> userByUsernameOptional = userRepository.findByUsername(usernameOrEmail);

            if (userByUsernameOptional.isPresent()) {
                // Se encontró el usuario por nombre de usuario
                User user = userByUsernameOptional.get();
                return org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .authorities(getAuthorities(user.getRoles()))
                        .build();
            } else {
                // No se encontró el usuario por nombre de usuario
                throw new UsernameNotFoundException("Usuario no encontrado: " + usernameOrEmail);
            }
        }
    }



    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void saveRole(Roles role) {
        roleRepository.save(role);
    }

    public Roles getOrCreateRole(String roleName) {
        Optional<Roles> existingRole = roleRepository.findByRole(roleName);
        return existingRole.orElseGet(() -> roleRepository.save(new Roles(roleName)));
    }
}
