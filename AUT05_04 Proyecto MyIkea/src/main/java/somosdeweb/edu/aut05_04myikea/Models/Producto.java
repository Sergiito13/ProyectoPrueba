package somosdeweb.edu.aut05_04myikea.Models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "productoffer", schema = "myikea", catalog = "")
public class Producto {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "product_id")
    private int productId;

    @Basic
    @Column(name = "product_name")
    private String productName;

    @Basic
    @Column(name = "product_price")
    private Double productPrice;

    @Basic
    @Column(name = "product_picture")
    private String productPicture;

    @Basic
    @Column(name = "product_stock")
    private int productStock;

    @ManyToOne
    @JoinColumn(name = "id_municipio", referencedColumnName = "id_municipio")
    private Municipio municipio;

    @ManyToOne
    @JoinColumn(name = "id_provincia", referencedColumnName = "id_provincia")
    private Provincia provincia;

    @ManyToMany(mappedBy = "productos", cascade = CascadeType.ALL)
    private List<Carrito> carritos;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductPicture() {
        return productPicture;
    }

    public void setProductPicture(String productPicture) {
        this.productPicture = productPicture;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Producto that = (Producto) o;

        if (productId != that.productId) return false;
        if (productStock != that.productStock) return false;
        if (productName != null ? !productName.equals(that.productName) : that.productName != null) return false;
        if (productPrice != null ? !productPrice.equals(that.productPrice) : that.productPrice != null) return false;
        if (productPicture != null ? !productPicture.equals(that.productPicture) : that.productPicture != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = productId;
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        result = 31 * result + (productPrice != null ? productPrice.hashCode() : 0);
        result = 31 * result + (productPicture != null ? productPicture.hashCode() : 0);
        result = 31 * result + productStock;
        return result;
    }
}
