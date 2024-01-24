package somosdeweb.edu.aut05_04myikea.Services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import somosdeweb.edu.aut05_04myikea.Models.Producto;
import somosdeweb.edu.aut05_04myikea.Repositories.ProductoRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }
    // Obtener producto por id
    public Producto obtenerProductoPorId(Long productId) {
        return productoRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado con ID: " + productId));
    }

    public void guardarProducto(Producto producto) {
        // Lógica para guardar el producto en la base de datos
        productoRepository.save(producto);
    }



}


