package somosdeweb.edu.aut05_04myikea.Models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pedidos", schema = "myikea", catalog = "")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pedido_id")
    private int pedido_id;

    @Column(name = "fecha_pedido", nullable = false)
    private LocalDate fecha_pedido;

    @Column(name = "precio_total", nullable = false)
    private float precio_total;

    @ManyToOne
    @JoinColumn(name = "carrito_id", referencedColumnName = "carrito_id")
    private Carrito carrito;

    @ManyToMany
    @JoinTable(
            name = "pedido_producto",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private Set<Producto> productos = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "clase_id")
    private User user;

    // ... otros métodos ...

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getPedido_id() {
        return pedido_id;
    }

    public void setPedido_id(int pedido_id) {
        this.pedido_id = pedido_id;
    }

    public LocalDate getFecha_pedido() {
        return fecha_pedido;
    }

    public void setFecha_pedido(LocalDate fecha_pedido) {
        this.fecha_pedido = fecha_pedido;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }

    public float getPrecio_total() {
        return precio_total;
    }

    public void setPrecio_total(float precio_total) {
        this.precio_total = precio_total;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }
}
