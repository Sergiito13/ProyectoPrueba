package somosdeweb.edu.aut05_04myikea.Controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import somosdeweb.edu.aut05_04myikea.Models.Carrito;
import somosdeweb.edu.aut05_04myikea.Models.Producto;
import somosdeweb.edu.aut05_04myikea.Models.User;
import somosdeweb.edu.aut05_04myikea.Services.CarritoService;
import somosdeweb.edu.aut05_04myikea.Services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import somosdeweb.edu.aut05_04myikea.Services.UserService;

@Controller
@RequestMapping("/Carrito")
public class CarritoController {

    @Autowired
    private final CarritoService carritoService;

    @Autowired
    private final ProductoService productoService;

    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(CarritoController.class);
    //Constructor
    @Autowired
    public CarritoController(CarritoService carritoService, ProductoService productoService, UserService userService) {
        this.userService = userService;
        this.carritoService = carritoService;
        this.productoService = productoService;
    }

    //Métodos
    @GetMapping("")
    public String verCarrito(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Obtener el usuario actual
        User usuarioActual = userService.findByUsername(userDetails.getUsername());
        logger.info("Usuario actual: " + usuarioActual.getUsername());

        // Obtener el carrito correspondiente al usuario actual
        Carrito carritoActual = carritoService.obtenerCarritoPorUsuario(usuarioActual);
        logger.info("Carrito actual: " + carritoActual.getId());


        if (carritoActual.isActivo() || carritoActual == null) {
            model.addAttribute("carrito", carritoActual.getProductos());
            model.addAttribute("total", carritoActual.getProductos().stream().mapToDouble(Producto::getProductPrice).sum());

            logger.info("Productos en el carrito: " + carritoActual.getProductos().toString());

        } else {
            model.addAttribute("total", 0.0);  // No hay productos en el carrito nuevo
        }

        return "Carrito/index";
    }


    @GetMapping("/Eliminar/{id}")
    public String eliminarProductoCarrito(@PathVariable("id") Long productId, @AuthenticationPrincipal UserDetails userDetails) {
        User usuarioActual = userService.findByUsername(userDetails.getUsername());
        Carrito carritoActual = carritoService.obtenerCarritoPorUsuario(usuarioActual);
        Producto producto = productoService.obtenerProductoPorId(productId);

        carritoService.eliminarProductoDelCarrito(producto, carritoActual);

        return "redirect:/Carrito";
    }

    @GetMapping("/Comprar/{id}")
    public String comprarProducto(@PathVariable("id") Long productId, @AuthenticationPrincipal UserDetails userDetails) {
        // Obtener el usuario actual
        User usuarioActual = userService.findByUsername(userDetails.getUsername());
        logger.info("Usuario actual: " + usuarioActual.getUsername());

        // Obtener el carrito correspondiente al usuario actual
        Carrito carritoActual = carritoService.obtenerCarritoPorUsuario(usuarioActual);
        logger.info("Carrito actual: " + carritoActual.getId());

        // Obtener el producto
        Producto producto = productoService.obtenerProductoPorId(productId);
        logger.info("Producto: " + producto.getProductName());

        // Asignar el usuario al carrito
        // carritoActual.setUser(usuarioActual);
        // logger.info("Usuario del carrito: " + carritoActual.getUser().getUsername());

        // Agregar el producto al carrito
        carritoService.agregarProductoAlCarrito(producto, carritoActual);
        logger.info("Producto agregado al carrito: " + producto.getProductName());

        // Guardar el carrito después de agregar el producto
        carritoService.saveCarrito(carritoActual);
        logger.info("Carrito guardado: " + carritoActual.getId());

        return "redirect:/Carrito";
    }
}
