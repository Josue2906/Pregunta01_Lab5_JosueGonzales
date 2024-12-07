package com.example.Lab05_Pregunta01_JosueGonzales.repository;

import com.example.Lab05_Pregunta01_JosueGonzales.model.Formulario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormularioRepository extends JpaRepository<Formulario, Long> {
    // Aquí se pueden agregar consultas
}
