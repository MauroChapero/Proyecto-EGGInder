/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.egginder.repositorios;

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
    
    @Query("SELECT  c FROM Voto c Where = :id")
    public Voto buscarPorId(@Param("id")String id);
}
