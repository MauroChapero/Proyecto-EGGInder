package com.proyecto.egginder.repositorios;

import com.proyecto.egginder.entidades.Alumno;
import com.proyecto.egginder.entidades.Materia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepositorio extends JpaRepository<Alumno, String>{
    
    @Query("SELECT a FROM Alumno a WHERE a.email = :email")
    public Alumno findByEmail(@Param("email") String email);
    
    @Query("SELECT a FROM Alumno a WHERE a.voto.materia = :materia")
    public List<Alumno> listaUsuariosMateriasCoinciden(@Param("materia") Materia materia);
}
