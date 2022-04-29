/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.egginder.Servicios;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author laura
 */
@Service
public class VotoServicio {
    
    @Autowired
    private VotoRepositorio votoRepositorio;
    
    @Transactional
    public Voto save(Boolean votoAprenderMateria, Boolean votoEnseniarMateria){
        validator(votoAprenderMateria,votoEnseniarMateria);
        Voto entidad = new Voto();
        entidad.setvotoAprenderMateria(votoAprenderMateria);
        entidad.votoEnseniarMateria(votoEnseniarMateria);
        return votoRepositorio.save(entidad);    
        
    }
    @Transactional
    public Voto edit(String id,Boolean votoAprenderMateria, Boolean votoEnseniarMateria){
        Optional<Voto> respuesta = votoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            validator(votoAprenderMateria,votoEnseniarMateria);
            Voto u = respuesta.get();
            u.setvotoAprenderMateria(votoAprenderMateria);
            u.setvotoEnseniarMateria(votoEnseniarMateria);
            return votoRepositorio.save(u);
        }else{
            return null;
        }
        
    }
    @Transactional
    public void delete(String id){
        votoRepositorio.deleteById(id);
    }
    
    @Transactional
    public List<Voto> finAll(){
        return votoRepositorio.findAll();
    }
    
    @Transactional
    public Voto getone(String id){
        return votoRepositorio.getById(id);
    }
    
    
    
    public void validator(Boolean votoAprenderMateria, Boolean votoEnseniarMateria){
       
        if(votoAprenderMateria==null){
            throw new Exception("Ingreso un voto invalido");
        }
        if(votoEnseniarMateria==null){
            throw new Exception("Ingreso un voto invalido");
        }
    }  
   
}