package com.proyecto.egginder.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Match {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    @OneToOne
    private Alumno alumno1;
    
    @OneToOne
    private Alumno alumno2;

    public Match() {
    }

    public Match(Alumno alumno1, Alumno alumno2) {
        this.alumno1 = alumno1;
        this.alumno2 = alumno2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Alumno getAlumno1() {
        return alumno1;
    }

    public void setAlumno1(Alumno alumno1) {
        this.alumno1 = alumno1;
    }

    public Alumno getAlumno2() {
        return alumno2;
    }

    public void setAlumno2(Alumno alumno2) {
        this.alumno2 = alumno2;
    }
    
    
    
}
