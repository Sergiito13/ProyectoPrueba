package somosdeweb.edu.aut05_04myikea.Models;


import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;



@Entity
public class Carrito {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carrito_id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "carrito_producto",
            joinColumns = @JoinColumn(name = "carrito_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private List<Producto> productos;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Column (name = "activo")
    private boolean activo;

    public Carrito() {
        this.productos = new ArrayList<>();
    }
    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public void eliminarProducto(Producto producto) {
        productos.remove(producto);
    }

    public Long getId() {
        return carrito_id;
    }

    public void setId(Long id) {
        this.carrito_id = id;
    }

    public List<Producto> getProductos() {
        return productos;
    }
    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }


}
