package com.proyecto.egginder.servicios;

import com.proyecto.egginder.entidades.Materia;
import com.proyecto.egginder.repositorios.MateriaRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MateriaServicio {
    
    @Autowired
    MateriaRepositorio materiaRepository;
    
    @Transactional
    public Materia crearMateria(String nombre) throws Exception {
        
        validarMateria(nombre);
        Materia materia = new Materia();
        materia.setNombre(nombre);
        
        return materiaRepository.save(materia);
    }
    
    @Transactional
    public Materia editarMateria(String nombre, String id) throws Exception {
        validarMateria(nombre);
        Optional <Materia> opt = materiaRepository.findById(id);
        if(opt.isPresent()) {
            Materia materia = opt.get();
            materia.setNombre(nombre);
            return materiaRepository.save(materia);
        } else { 
            throw new Exception("No se halló la materia.");
        }
    }
    
    @Transactional
    public void eliminarMateria(String id) throws Exception {
        Optional<Materia> opt = materiaRepository.findById(id);
        if (opt.isPresent()) {
            materiaRepository.deleteById(id);
        } else {
            throw new Exception("No se halló la materia a eliminar.");
        }
    }
    
    public void validarMateria(String nombre) throws Exception {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("El nombre no puede ser nulo, ni estar vacío.");
        }
    }
    
    @Transactional
    public List<Materia> listarMaterias() {
        return materiaRepository.findAll();
    }
    
    @Transactional
    public Materia getById(String id) throws Exception{
        if (materiaRepository.getById(id) == null){
            throw new Exception("");
        }
        return materiaRepository.getById(id);
    }
    
    @Transactional
    public Materia buscarPorId(String id) throws Exception {
        Optional<Materia> opt = materiaRepository.findById(id);
        if (opt.isPresent()) {
            Materia materia = opt.get();
            return materia;
        } else {
            throw new Exception("No se halló la materia.");
        }
    }
}

