package com.example.Lab05_Pregunta01_JosueGonzales.controller;

import com.example.Lab05_Pregunta01_JosueGonzales.model.Formulario;
import com.example.Lab05_Pregunta01_JosueGonzales.repository.FormularioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute; 
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FormularioController {
    @Autowired
    private FormularioRepository formularioRepository;
    
    // Procesar el formulario y guardarlo en la base de datos
    @PostMapping("/submitFormulario")
    public String procesarFormulario(@ModelAttribute Formulario formulario, Model model) {
        // Guardar el formulario en la base de datos
        formularioRepository.save(formulario);
        // Agregar el formulario al modelo para mostrarlo en la vista
        model.addAttribute("formulario", formulario);
        return "redirect:/listarFormularios";
    }
    
    // Mostrar todos los formularios guardados
    @GetMapping("/listarFormularios")
    public String listarFormularios(Model model) {
        // Obtener todos los formularios desde la base de datos 
        List<Formulario> formularios = formularioRepository.findAll();

        // Agregar la lista de formularios al modelo 
        model.addAttribute("formularios", formularios);
        model.addAttribute("formulario", new Formulario());
        return "listar"; // Vista donde mostraremos los datos en una tabla
    }
    
    // Mostrar formulario para editar (cargar datos de la base)
    @GetMapping("/editarFormulario/{id}")
    public String editarFormulario(@PathVariable("id") Long id, Model model) {
        Formulario formulario = formularioRepository.findById(id).orElse(null);
        if (formulario != null) {
            model.addAttribute("formulario", formulario); // Cargar el formulario para editar 
            return "formulario";
        }
        return "redirect:/listarFormularios"; // Si no se encuentra, redirigimos
    }
    
    // Procesar la edición del formulario
    @PostMapping("/submitEdicionFormulario")
    public String submitEdicion(@ModelAttribute Formulario formulario, Model model) { 
            if (formulario.getId() != null) {
                // Buscar el formulario en la base de datos
                Formulario formularioExistente = formularioRepository.findById(formulario.getId()).orElse(null);
                if (formularioExistente != null) {
                    // Actualizamos los campos del formulario con los nuevos datos 
                    formularioExistente.setNombre(formulario.getNombre());
                    formularioExistente.setEmail(formulario.getEmail());
                    formularioExistente.setMensaje(formulario.getMensaje());
                    
                    // Guardamos el formulario actualizado (esto realiza un UPDATE) 
                    formularioRepository.save(formularioExistente);
                    model.addAttribute("formulario", formularioExistente);
                }
            }
            return "redirect:/listarFormularios"; // Redirigir si no se encuentra el formulario
    }
    
    // Eliminar un formulario
    @GetMapping("/eliminarFormulario/{id}")
    public String eliminarFormulario(@PathVariable("id") Long id) {

        formularioRepository.deleteById(id); // Eliminamos el formulario por su ID
        return "redirect:/listarFormularios"; // Redirigir al listado después de eliminar
    }
}
