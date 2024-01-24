package somosdeweb.edu.aut05_04myikea.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import somosdeweb.edu.aut05_04myikea.Models.Carrito;
import somosdeweb.edu.aut05_04myikea.Models.Pedido;
import somosdeweb.edu.aut05_04myikea.Models.Producto;
import somosdeweb.edu.aut05_04myikea.Models.User;
import somosdeweb.edu.aut05_04myikea.Services.CarritoService;
import somosdeweb.edu.aut05_04myikea.Services.PedidoService;
import somosdeweb.edu.aut05_04myikea.Services.UserService;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Controller
public class PedidoController {

    @Autowired
    private CarritoService Carrito;
    @Autowired
    private  PedidoService pedidoService;

    @Autowired
    private UserService userService;

    @Autowired
    private CarritoService carritoService;

    @Autowired
    public PedidoController(CarritoService Carrito, PedidoService pedidoService, UserService userService, CarritoService carritoService) {
        this.userService = userService;
        this.Carrito = Carrito;
        this.pedidoService = pedidoService;
        this.carritoService = carritoService;
    }

    @GetMapping("/Pedidos")
    public String mostrarPedidos(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Obtener el usuario actual
        User usuarioActual = userService.findByUsername(userDetails.getUsername());

        // Obtener los pedidos asociados al usuario actual
        List<Pedido> pedidos = usuarioActual.getPedidos();
        model.addAttribute("pedidos", pedidos);
        return "Pedidos/ListarPedidos";
    }

    @GetMapping("/Pedidos/Details/{id}")
    public String verDetallesPedido(@PathVariable("id") Long pedidoId, Model model) {
        Pedido pedido = pedidoService.obtenerPedidoPorId(pedidoId);
        model.addAttribute("pedido", pedido);
        return "Pedidos/Details";
    }

    @GetMapping("/Finalizar")
    public String finalizarPedido(Model model, @AuthenticationPrincipal UserDetails userDetails) {

        // Obtener el usuario actual
        User usuarioActual = userService.findByUsername(userDetails.getUsername());

        // Obtener el carrito actual del usuario
        Carrito carritoActual = Carrito.obtenerCarritoPorUsuario(usuarioActual);
        Iterable<Producto> productosEnCarrito = carritoActual.getProductos();
        float precioTotal = calcularPrecioTotal(productosEnCarrito);

        // Creamos un nuevo pedido
        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setPrecio_total(precioTotal);
        nuevoPedido.setFecha_pedido(java.time.LocalDate.now());
        nuevoPedido.setProductos(new HashSet<>((Collection) productosEnCarrito));
        nuevoPedido.setUser(usuarioActual);  // Asociamos el pedido al usuario actual
        nuevoPedido.setCarrito(carritoActual);

        pedidoService.guardarPedido(nuevoPedido);

        // Desactivamos el carrito
        carritoActual.setActivo(false);
        // Guardamos el carrito
        carritoService.saveCarrito(carritoActual);
        

        return "redirect:/Pedidos";
    }


    private float calcularPrecioTotal(Iterable<Producto> productos) {
        float precioTotal = 0;
        for (Producto producto : productos) {
            precioTotal += producto.getProductPrice();
        }
        return precioTotal;
    }
}
