package somosdeweb.edu.aut05_04myikea.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import somosdeweb.edu.aut05_04myikea.Models.Provincia;
import somosdeweb.edu.aut05_04myikea.Repositories.ProvinciaRepository;

import java.util.List;

@Service
public class ProvinciaService {

    private final ProvinciaRepository provinciaRepository;

    @Autowired
    public ProvinciaService(ProvinciaRepository provinciaRepository) {
        this.provinciaRepository = provinciaRepository;
    }

    public List<Provincia> obtenerTodasLasProvincias() {
        return provinciaRepository.findAll();
    }

    // Otros métodos relacionados con la lógica de provincias
}
