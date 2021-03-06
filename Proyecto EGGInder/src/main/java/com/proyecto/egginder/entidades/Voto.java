package com.proyecto.egginder.entidades;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Voto {

    //IDENTIFICADOR UNICO
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    //MATERIA QUE EL ALUMNO QUIERE APRENDER
    @OneToOne
    private Materia materia;

    //CONSTRUCTOR VACIO
    public Voto() {
    }

    //CONSTRUCTOR PARAMETRIZADO

    public Voto(Materia materia) {
        this.materia = materia;
    }

    //GETTERS Y SETTERS

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }
    
}
