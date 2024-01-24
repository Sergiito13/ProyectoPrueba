package somosdeweb.edu.aut05_04myikea.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import somosdeweb.edu.aut05_04myikea.Models.Pedido;
import somosdeweb.edu.aut05_04myikea.Repositories.PedidoRepository;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    // Guardar un nuevo pedido
    public void guardarPedido(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

    // Obtener el último pedido (puedes ajustarlo según tus necesidades)
    public Pedido obtenerUltimoPedido() {
        return null;
    }

    // Obtener un pedido por su ID
    public Pedido obtenerPedidoPorId(Long pedidoId) {
        return pedidoRepository.findById(pedidoId).orElse(null);
    }

    // Obtener todos los pedidos
    public List<Pedido> obtenerTodosLosPedidos() {
        return pedidoRepository.findAll();
    }
}
