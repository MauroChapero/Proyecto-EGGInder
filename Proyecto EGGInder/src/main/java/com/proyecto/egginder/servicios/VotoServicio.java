/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.egginder.Servicios;

import com.proyecto.egginder.entidades.Materia;
import com.proyecto.egginder.entidades.Voto;
import com.proyecto.egginder.repositorios.VotoRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author laura
 */
@Service
public class VotoServicio {
    
    @Autowired
    private VotoRepositorio votoRepositorio;
    
    @Transactional
    public Voto save(Boolean voto, Materia materia) throws Exception{
        //  Validacion del contenido que ingresa el usuario
        validator(voto, materia);
        //  Creamos el Voto y le seteamos el Boolean voto / Materia materia
        Voto entidad = new Voto();
        entidad.setVoto(voto);
        entidad.setMateria(materia);
        //  Guardamos en el repositorio
        return votoRepositorio.save(entidad);
        
    }
    @Transactional
    public Voto edit(String id, Boolean voto, Materia materia) throws Exception{
        //  Validamos los datos del Voto
        validator(voto, materia);
        //  Buscamos al voto por su ID y lo depositamos en un contenedor Optional<Voto>
        Optional<Voto> respuesta = votoRepositorio.findById(id);
        //  Si esta cargado el Contenedor Optional<Voto> entra al if
        if (respuesta.isPresent()) {
            //  Se lo envio a un objeto Voto con los datos traidos y le seteo los cambios.
            Voto u = respuesta.get();
            u.setVoto(voto);
            u.setMateria(materia);
            //  retornas el repositorio.save con el voto cambiado dentro
            return votoRepositorio.save(u);
        //  Si no esta cargado el Contenedor Optional<Voto> entra al else    
        }else{
            //  Tira una exception que indica que no se encontro el voto con el id solicitado
            throw new Exception("No existe un voto con ese ID.");
        }
    }
    
    @Transactional
    public Voto modificar(String id, Boolean voto, Materia materia) throws Exception{
        validator(voto, materia);
        Voto v = votoRepositorio.getById(id);
        if (v!=null) {
            v.setVoto(voto);
            v.setMateria(materia);
            return votoRepositorio.save(v);
        } else {
            throw new Exception("No existe un voto con ese ID.");
        }
    }
    
    @Transactional
    public void delete(String id){
        votoRepositorio.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public List<Voto> findAll(){
        return votoRepositorio.findAll();
    }
    
    @Transactional(readOnly = true)
    public Voto getOne(String id){
        return votoRepositorio.getById(id);
    }
    
    public void validator(Boolean voto, Materia materia) throws Exception{
       
        if(voto==null){
            throw new Exception("Ingreso un voto invalido");
        }
        if(materia==null){
            throw new Exception("Ingreso un voto invalido");
        }
    }  
   
}