package somosdeweb.edu.aut05_04myikea.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import somosdeweb.edu.aut05_04myikea.Models.Municipio;
import somosdeweb.edu.aut05_04myikea.Models.Producto;
import somosdeweb.edu.aut05_04myikea.Models.Provincia;

public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {

}
