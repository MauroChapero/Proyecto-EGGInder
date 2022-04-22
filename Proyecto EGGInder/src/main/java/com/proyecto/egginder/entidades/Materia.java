package com.proyecto.egginder.entidades;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Materia {

    //IDENTIFICADOR UNICO
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    //ATRIBUTOS
    private String nombre;

    //CONSTRUCTOR VACIO
    public Materia() {
    }

    //CONSTRUCTOR PARAMETRIZADO
    public Materia(String nombre) {
        this.nombre = nombre;
    }

    //GETTERS Y SETTERS
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
