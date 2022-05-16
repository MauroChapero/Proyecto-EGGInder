package com.proyecto.egginder.servicios;

import com.proyecto.egginder.entidades.Alumno;
import com.proyecto.egginder.entidades.Match;
import com.proyecto.egginder.repositorios.AlumnoRepositorio;
import com.proyecto.egginder.repositorios.MatchRepositorio;
import com.proyecto.egginder.repositorios.MateriaRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchServicio {

    @Autowired
    private MatchRepositorio matchRepositorio;
    
    @Autowired
    private AlumnoRepositorio alumnoRepositorio;
    
    @Autowired
    private MateriaRepositorio materiaRepositorio;
    
    @Transactional
    public Match crearMatch(Alumno alumno1, Alumno alumno2) throws Exception{
        validar(alumno1, alumno2);
        
        if (alumno1.getVoto().getMateria().getNombre()
                .equals(alumno2.getVoto().getMateria().getNombre())) {
            Match match = new Match();
            match.setAlumno1(alumno1);
            match.setAlumno2(alumno2);
            
            return matchRepositorio.save(match);
        } else {
            throw new Exception("No coinciden las materias");
        }
    }
    
    @Transactional
    public Match modificarMatch(String id, Alumno alumno1, Alumno alumno2) throws Exception{
        validar(alumno1, alumno2);
        
        Optional<Match> opt = matchRepositorio.findById(id);
        if (opt.isPresent()) {
            Match match = opt.get();
            match.setAlumno1(alumno1);
            match.setAlumno2(alumno2);
            
            return matchRepositorio.save(match);
        } else {
            throw new Exception("No se encontro el match");
        }
    }
    
    @Transactional
    public void eliminarMatch(String id){
        Match match = getOne(id);
        matchRepositorio.delete(match);
    }
    
    @Transactional(readOnly = true)
    public Match getOne(String id){
        return matchRepositorio.getById(id);
    }
    
    public void validar(Alumno alumno1, Alumno alumno2) throws Exception{
        if (alumno1 == null || alumno2 == null) {
            throw new Exception("Error al cargar los alumnos");
        }
        if (alumno1.getId().equals(alumno2.getId())) {
            throw new Exception("Mismo alumno");
        }
        if (alumno1.getVoto()==null || alumno2.getVoto()==null) {
            throw new Exception("No hay voto");
        }
    }
}
