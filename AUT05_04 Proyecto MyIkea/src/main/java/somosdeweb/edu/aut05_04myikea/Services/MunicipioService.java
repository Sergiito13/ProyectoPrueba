package somosdeweb.edu.aut05_04myikea.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import somosdeweb.edu.aut05_04myikea.Models.Municipio;
import somosdeweb.edu.aut05_04myikea.Repositories.MunicipioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MunicipioService {

    private final MunicipioRepository municipioRepository;

    @Autowired
    public MunicipioService(MunicipioRepository municipioRepository) {
        this.municipioRepository = municipioRepository;
    }

    public List<Municipio> obtenerTodosLosMunicipios() {
        return municipioRepository.findAll();
    }

    // Método para obtener un municipio por su ID
    public Optional<Municipio> obtenerMunicipioPorId(long idMunicipio) {
        return municipioRepository.findById(idMunicipio);
    }

    // Método para guardar un municipio
    public void guardarMunicipio(Municipio municipio) {
        municipioRepository.save(municipio);
    }

}
