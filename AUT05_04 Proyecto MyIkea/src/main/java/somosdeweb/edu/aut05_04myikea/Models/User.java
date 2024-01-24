package somosdeweb.edu.aut05_04myikea.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.*;

@Entity
public class User{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "clase_id", nullable = false)
    private int userId;

    @Basic
    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "username", nullable = false, length = 512)
    private String username;

    @Basic
    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "user_mail", unique = true, nullable = false, length = 512)
    private String userMail;

    @Basic
    @NotBlank(message = "")
    @Column(name = "password", nullable = false, length = 512)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Roles> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Carrito> carritos = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Pedido> pedidos = new ArrayList<>();



    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
        pedido.setUser(this);
    }
    public List<Carrito> getCarritos() {
        return carritos;
    }

    public void setCarritos(List<Carrito> carritos) {
        this.carritos = carritos;
    }

    public void agregarCarrito(Carrito carrito) {
        carritos.add(carrito);
        carrito.setUser(this);
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Roles> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && Objects.equals(username, user.username) && Objects.equals(userMail, user.userMail) && Objects.equals(password, user.password) && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, userMail, password, roles);
    }
}
