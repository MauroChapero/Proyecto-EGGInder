package com.proyecto.egginder.controladores;

import com.proyecto.egginder.entidades.Materia;
import com.proyecto.egginder.servicios.MateriaServicio;
import java.util.List;
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
@RequestMapping("/materia")
public class MateriaControlador {

    @Autowired
    MateriaServicio materiaService;

    //Vista del formulario para ver materia. th:href="@{/materia/formulario}"
    @GetMapping("/registrar")
    public String formularioMateria() {
        return "añadirMateria.html";
    }

    //Formulario de registro para materia. th:action="@{/materia/registrar}" method="POST"
    @PostMapping("/registrar")
    public String añadirMateria(ModelMap model, @RequestParam String nombre) throws Exception {
        try {
            materiaService.crearMateria(nombre);
            model.put("añadido", "Materia añadida.");
            return "añadirMateria.html";
        } catch (Exception e) {
            model.put("error", "No se pudo añadir la materia.");
            return "añadirMateria.html";
        }
    }

    // Lista. th:href="@{/materia/lista}"
    // @PreAuthorize("hasRole('ROLE_ADMIN','ROLE_USER')")
    // Sino usar el HttpSession con un if en cada metodo verificando si es admin
    @GetMapping("/lista")
    public String listarMaterias(ModelMap model) throws Exception {
        try {
            List<Materia> listaMaterias = materiaService.listarMaterias();
            model.addAttribute("materia", listaMaterias); //th:each="materia : ${materia}
            return "listaMaterias.html";
        } catch (Exception e) {
            return "añadirMateria.html";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarMaterias(@PathVariable String id, ModelMap model) throws Exception {
        model.put("materia", materiaService.getById(id));
        return "editarMaterias.html";

    }

    /*
    <form th:action="@{/materia/editar/__${materia.id}__}" method="POST">   
        <div class="form-group">
            <label>Nombre</label>
            <input th:value="${materia.nombre}" type="text" class="form-control" name="nombre" placeholder="Nombre de la materia">
    </div> 
     */
    @PostMapping("/editar/{id}")
    public String modificar(ModelMap model, @PathVariable String id, @RequestParam String nombre) throws Exception {
        try {
            Materia materia = materiaService.editarMateria(nombre, id);
            return "redirect:/materia/lista";
        } catch (Exception e) {
            model.put("error", "No se pudo editar la materia");
            model.addAttribute("materia", materiaService.getById(id));
            return "editarMaterias.html";
        }
    }

    //<a class="btn btn-danger" type="button" th:href="@{'/materia/eliminar/' + ${materia.id}}"> Eliminar</a>
    @RequestMapping("/eliminar/{id}")
    public String eliminarMateria(ModelMap model, @PathVariable(name = "id") String id) {
        try {
            materiaService.eliminarMateria(id);
            model.put("exito", "Eliminado");
            return "redirect:/materia/lista";

        } catch (Exception e) {
            model.put("error", "Error");
            return "redirect:/materia/lista";
        }
    }
}
