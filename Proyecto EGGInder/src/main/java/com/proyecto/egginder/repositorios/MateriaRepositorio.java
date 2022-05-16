package com.proyecto.egginder.repositorios;

import com.proyecto.egginder.entidades.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaRepositorio extends JpaRepository<Materia,String> {
    
    @Query("SELECT c FROM Materia c WHERE c.nombre = :nombre") 
    public Materia buscarPorNombre(@Param("nombre") String nombre);
    
}
