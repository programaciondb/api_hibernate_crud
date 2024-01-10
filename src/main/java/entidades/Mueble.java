package entidades;

import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Muebles")
public class Mueble implements Serializable
{

    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 100, unique=true)
    private String nombre;

    @Column(name = "company", length=30, nullable = false)
    private String marca;

    @Column(name = "image")
    private String imagen;

    @Column(name = "price", nullable = false)
    private Double precio;

    //constructores

   public Mueble(){}

    public Mueble(Long id, String nombre, String marca, String imagen, Double precio) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.imagen = imagen;
        this.precio = precio;

    }

    //getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }


    //tostring

    @Override
    public String toString() {
        return "Mueble{" +
                "id=" + id + "\n"+
                "nombre='" + nombre + "\n"+
                "marca='" + marca + "\n"+
                "imagen='" + imagen + "\n"+
                "precio=" + precio + "\n"+
                "}\n";
    }
}
