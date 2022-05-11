package com.proyecto.egginder.controladores;

import com.proyecto.egginder.Servicios.VotoServicio;
import com.proyecto.egginder.entidades.Alumno;
import com.proyecto.egginder.entidades.Materia;
import com.proyecto.egginder.entidades.Voto;
import com.proyecto.egginder.servicios.AlumnoServicio;
import com.proyecto.egginder.servicios.MateriaServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USUARIO')")
@RequestMapping("/perfil")
public class AlumnoControlador {

    // Servicio del alumno
    @Autowired
    private AlumnoServicio alumnoServicio;

    @Autowired
    private VotoServicio votoServicio;

    @Autowired
    private MateriaServicio materiaServicio;

    @GetMapping("/ver")
    public String pefil(){
        return "/test/perfil";
    }
    
    @GetMapping("/voto")
    public String crearVoto(ModelMap model, HttpSession session) {

        Alumno perfil = (Alumno) session.getAttribute("usuariosession");
        Alumno usuario = alumnoServicio.getOne(perfil.getId()); //  PASE A UN USUARIO CON EL ID DE LA SESSION
        List<Materia> listMaterias = materiaServicio.listarMaterias();
        // la mejor manera de modelar los cambios actualizados, es modelar un usuario en vez de una sesion
        model.addAttribute("materias", listMaterias);
        model.addAttribute("perfil", perfil);
        model.put("voto1", usuario.getVotoAprender());  // USE EL ID DEL USUARIO
        // Se trabaja con el objeto usuario, en vez de la session, porque nunca se actualizan los valores
        model.put("voto2", usuario.getVotoEnseniar());  // USE EL ID DEL USUARIO
        return "/test/voto-formulario";
    }

    @PostMapping("/voto")
    public String crearVoto(ModelMap model, HttpSession session, @RequestParam String idMateria1, 
            @RequestParam String idMateria2) {
        
        try {
            Alumno perfil = (Alumno) session.getAttribute("usuariosession");
            if (perfil == null) {
                throw new Exception("httpSession = null, sesion no iniciada");
            }
            //  VERIFICA SI EL VOTO YA ESTA CARGADO Y ELIMINA LOS ANTERIORES
            
            
            
            Materia materia1 = materiaServicio.getOne(idMateria1);
            Materia materia2 = materiaServicio.getOne(idMateria2);
            
            if (perfil.getVotoAprender()!=null) {
                votoServicio.edit(perfil.getVotoAprender().getId(), materia1);
            } else {
                Voto votoAprender = votoServicio.save(materia1);
                alumnoServicio.crearVotoAprender(perfil.getId(), votoAprender);
            }
            if (perfil.getVotoEnseniar()!=null) {
                votoServicio.edit(perfil.getVotoEnseniar().getId(), materia2);
            } else {
                Voto votoEnseniar = votoServicio.save(materia2);
                alumnoServicio.crearVotoEnseniar(perfil.getId(), votoEnseniar);
            }
            
           //session.setAttribute("usuariosession", perfil);
            
            if (perfil == null) {
                throw new Exception("httpSession = null, sesion no iniciada");
            }
            return "redirect:/inicio";
        } catch (Exception e) {
            Alumno perfil = (Alumno) session.getAttribute("usuariosession");
            
            List<Materia> listMaterias = materiaServicio.listarMaterias();
            model.addAttribute("materias", listMaterias);
            model.put("error", e.getMessage()); 
            model.addAttribute("perfil", perfil);
            return "/test/voto-formulario";
        }
    }
}

    /*
    @PostMapping("/voto")
    public String asignarModificarVoto(ModelMap model, HttpSession session ,//@RequestParam String id,
            @RequestParam(required = false) String idMateria, @RequestParam(required = false) String idMateria2) {
        try {
            Alumno perfil = (Alumno) session.getAttribute("usuariosession");
            
            Materia materia = materiaServicio.getOne(idMateria);
            Materia materia2 = materiaServicio.getOne(idMateria2);

            Voto votoAprender = votoServicio.save(materia);
            Voto votoEnseniar = votoServicio.save(materia2);

            alumnoServicio.asignarCambiarVotos(perfil.getId(), votoAprender, votoEnseniar);
            model.put("exito", "VOTOS ASIGNADOS CORRECTAMENTE");
            return "redirect:/inicio";
        } catch (Exception e) {
            Alumno perfil = (Alumno) session.getAttribute("usuariosession");
            model.put("fallo", e.getMessage());
            model.addAttribute("perfil", perfil);
            return "/test/voto-formulario";
        }*/
 /* @GetMapping("/voto/crear")
    public String generarVoto(){
        return "/test/guardarVoto.html";
    }
    
    @PostMapping("/voto/crear")
    public String generarVoto(ModelMap model, @RequestParam String nombreMateria) throws Exception {
        try {
            Materia materia = materiaServicio.buscarPorNombre(nombreMateria);
            votoServicio.save(materia);
            model.put("exito", "Voto añadido.");
            return "/test/guardarVoto.html";
        } catch (Exception e) {
            model.put("error", "No se pudo Guardar el voto.");
            return "/test/guardarVoto.html";
        }
    }*/
