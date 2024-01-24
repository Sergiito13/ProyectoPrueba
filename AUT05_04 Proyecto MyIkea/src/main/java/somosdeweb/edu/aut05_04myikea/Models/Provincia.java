package somosdeweb.edu.aut05_04myikea.Models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "provincias", schema = "myikea", catalog = "")
public class Provincia {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_provincia")
    private short idProvincia;

    @Basic
    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "provincia")
    private List<Municipio> municipios;

    public short getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(short idProvincia) {
        this.idProvincia = idProvincia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Municipio> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Provincia that = (Provincia) o;

        if (idProvincia != that.idProvincia) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) idProvincia;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        return result;
    }
}
