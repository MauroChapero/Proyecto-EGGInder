/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.egginder.repositorios;

import com.proyecto.egginder.entidades.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author laura
 */
@Repository
public interface VotoRepositorio extends JpaRepository<Voto, String>{
    
    @Query("SELECT c FROM Voto c WHERE c.materia = :id")
    public Voto buscarVotoPorIdMateria(@Param("id") String id);
    
    /*
    @Query("SELECT  c FROM Voto c Where = :id")   //  1. Query inservible, ya que existe el "findById" en JpaRepository
    public Voto buscarPorId(@Param("id")String id); //  2. Mal redactada la query, no le indicaste que atributo utilizaba del objeto
    
    @Query("SELECT c FROM Voto c WHERE c.id = :id") // *(2.) Tenes que indicarle el "c.id" para que compare el id del Voto
    public Voto buscarPorId(@Param("id") String id); // con el id que le envias por parametro (:id -> @Param("id") String id)
    */
}
