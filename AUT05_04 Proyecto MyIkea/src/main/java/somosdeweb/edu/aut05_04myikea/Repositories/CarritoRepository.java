package somosdeweb.edu.aut05_04myikea.Repositories;

import somosdeweb.edu.aut05_04myikea.Models.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<Carrito, Integer> {

    Carrito findFirstByActivoTrue();

    Carrito findFirstByOrderByIdDesc();
}
