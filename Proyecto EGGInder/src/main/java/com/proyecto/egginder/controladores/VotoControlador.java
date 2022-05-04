/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.egginder.controladores;

import com.proyecto.egginder.Servicios.VotoServicio;
import com.proyecto.egginder.entidades.Materia;
import com.proyecto.egginder.entidades.Voto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author laura
 */
@Controller
@RequestMapping("/voto")
public class VotoControlador {
    @Autowired
    private VotoServicio votoService;
    
    @GetMapping("/formulario")
    public String index(){
        return "guardarVoto.html";
    }
    
     @PostMapping("/save")
    public String saveVoto(ModelMap model, @RequestParam Boolean voto,@RequestParam Materia materia) throws Exception {
        try {
            votoService.save(voto, materia);
            model.put("añadido", "Voto añadido.");
            return "guardarVoto.html";
        } catch (Exception e) {
            model.put("error", "No se pudo Guardar el voto.");
            return "guardarVoto.html";
        }
    }
    @GetMapping("/lista")
    public String listarVotos (ModelMap model) throws Exception {
        try {
            List<Voto> listaVotos = votoService.findAll();
            model.addAttribute("materia", listaVotos); 
            return "listaVotos.html";
        } catch (Exception e) {
            return "añadirVotos.html";
        }
    }
    @GetMapping("/obtenerVoto")
    public String unVoto (@PathVariable String id, ModelMap model) throws Exception {
        try {
            Voto v = votoService.getOne(id);
            model.addAttribute("voto", v); 
            return "unVoto.html";
        } catch (Exception e) {
            return "añadirVotos.html";
        }
    }
  
    @GetMapping("/editar/{id}")
    public String editVoto( @PathVariable String id, @RequestParam Boolean voto,@RequestParam Materia materia, ModelMap model) throws Exception {
        try {   
            votoService.edit(id, voto, materia);
            model.put("añadido", "Voto añadido.");
            return "editarVoto.html";
        } catch (Exception e) {
            model.put("error", "No se pudo Guardar el voto.");
            return "editarVoto.html";
        }
       
    }
    @PostMapping("/editar/{id}")
    public String modificarVoto(ModelMap model, @PathVariable String id, @RequestParam Boolean voto, @RequestParam Materia materia) throws Exception {
        try { 
            Voto v = votoService.modificar(id, voto, materia);
            return "redirect:/voto/lista";
        } catch (Exception e) {
            model.put("error", "No se pudo editar el voto");
            model.addAttribute("voto", votoService.save(voto, materia));
            return "editarVoto.html";
        }
    }
    
    @RequestMapping("/eliminar/{id}")
    public String deleteVoto(ModelMap model, @PathVariable(name = "id") String id) {
        try {
            votoService.delete(id);
            model.put("exito", "Eliminado");
            return "redirect:/voto/save";
            
        } catch (Exception e) {
            model.put("error", "Error");
            return "redirect:/voto/save";
        }
    }


}
