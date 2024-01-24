package somosdeweb.edu.aut05_04myikea.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import somosdeweb.edu.aut05_04myikea.Models.Municipio;
import somosdeweb.edu.aut05_04myikea.Models.Producto;
import somosdeweb.edu.aut05_04myikea.Models.Provincia;
import somosdeweb.edu.aut05_04myikea.Services.MunicipioService;
import somosdeweb.edu.aut05_04myikea.Services.ProductoService;
import somosdeweb.edu.aut05_04myikea.Services.ProvinciaService;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductosController {

    private final ProductoService productoService;
    private final MunicipioService municipioService;
    private final ProvinciaService provinciaService;

    @Autowired
    public ProductosController(ProductoService productoService, MunicipioService municipioService, ProvinciaService provinciaService) {
        this.productoService = productoService;
        this.municipioService = municipioService;
        this.provinciaService = provinciaService;
    }

    @GetMapping("/Productos")
    public String listarProductos(Model model) {
        // Obtener la lista de productos y agregarla al modelo
        model.addAttribute("productos", productoService.obtenerTodosLosProductos());

        return "Productos/ListarProductos";
    }

    @GetMapping("/Productos/Details/{id}")
    public String productosDetails(@PathVariable Long id, Model model) {
        Producto producto = productoService.obtenerProductoPorId(id);
        model.addAttribute("producto", producto);
        return "Productos/Details";

    }

    @GetMapping("/Productos/Create")
    public String mostrarFormularioNuevo(Model model) {
        // Obtener listas de provincias y municipios desde el servicio
        List<Provincia> provincias = provinciaService.obtenerTodasLasProvincias();
        List<Municipio> municipios = municipioService.obtenerTodosLosMunicipios();

        // Agregar las listas al modelo para que estén disponibles en la vista
        model.addAttribute("provincias", provincias);
        model.addAttribute("municipios", municipios);

        model.addAttribute("producto", new Producto());
        return "Productos/Create";
    }

    @PostMapping("/Productos/Create")
    public String guardarProducto(@ModelAttribute("producto") Producto producto, Model model) {
        // Lógica para guardar el producto usando el servicio
        productoService.guardarProducto(producto);

        // Redirige a la página de listado de productos o a la página de detalles del producto recién creado
        return "redirect:/Productos";
    }

}
