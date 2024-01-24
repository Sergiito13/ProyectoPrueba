package somosdeweb.edu.aut05_04myikea.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import somosdeweb.edu.aut05_04myikea.Models.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

}