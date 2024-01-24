package somosdeweb.edu.aut05_04myikea.Services;

import somosdeweb.edu.aut05_04myikea.Models.Carrito;
import somosdeweb.edu.aut05_04myikea.Models.Producto;
import somosdeweb.edu.aut05_04myikea.Models.User;
import somosdeweb.edu.aut05_04myikea.Repositories.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarritoService {
    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private UserService userService;

    private ProductoService productoRepository;


    public CarritoService(CarritoRepository carritoRepository, UserService userService, ProductoService productoRepository) {
        this.productoRepository = productoRepository;
        this.userService = userService;
        this.carritoRepository = carritoRepository;
    }

    public Carrito obtenerCarrito() {
        Carrito carrito = carritoRepository.findFirstByActivoTrue();
        if (carrito == null) {
            carrito = crearCarrito();
        }
        return carrito;
    }

    public Carrito obtenerCarritoPorUsuario(User usuario) {
        List<Carrito> carritos = usuario.getCarritos();

        // Verificar si todos los carritos están inactivos
        boolean todosInactivos = carritos.stream().allMatch(carrito -> !carrito.isActivo());

        if (carritos.isEmpty() || todosInactivos) {
            // Crear un nuevo carrito activo
            Carrito carritoNuevo = crearCarrito();
            carritoNuevo.setUser(usuario);
            usuario.agregarCarrito(carritoNuevo); // Agrega el nuevo carrito a la lista de carritos del usuario
            userService.saveUser(usuario);
            carritoRepository.save(carritoNuevo);
            return carritoNuevo;
        } else {
            // Encontrar el primer carrito activo si existe
            Optional<Carrito> carritoActivo = carritos.stream().filter(Carrito::isActivo).findFirst();

            // Devolver el primer carrito activo o el último carrito inactivo si no hay activos
            return carritoActivo.orElseGet(() -> carritos.get(carritos.size() - 1));
        }
    }


    public void agregarProductoAlCarrito(Producto producto, Carrito carrito) {
        carrito.agregarProducto(producto);
        productoRepository.guardarProducto(producto); // Guardar producto antes de guardarlo en el carrito
        carritoRepository.save(carrito);
    }


    public void eliminarProductoDelCarrito(Producto producto, Carrito carrito) {
        carrito.eliminarProducto(producto);
        carritoRepository.save(carrito);
        productoRepository.guardarProducto(producto);
    }

    public void saveCarrito(Carrito carrito) {
        carritoRepository.save(carrito);
    }

    public Carrito crearCarrito() {
        Carrito nuevoCarrito = new Carrito();
        nuevoCarrito.setActivo(true);
        return carritoRepository.save(nuevoCarrito);
    }
}
