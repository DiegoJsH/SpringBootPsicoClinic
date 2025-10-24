package com.psicoclinic.psicoclinic.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.psicoclinic.psicoclinic.models.CitaModel;

@Repository
public interface CitaRepository extends CrudRepository<CitaModel, Long> {
    
    // Buscar citas por paciente ID
    List<CitaModel> findByPacienteId(Long pacienteId);
    
    // Buscar citas por especialista ID
    List<CitaModel> findByEspecialistaId(Long especialistaId);
    
    // Buscar citas por fecha
    List<CitaModel> findByFecha(LocalDate fecha);
    
    // Buscar citas por estado
    List<CitaModel> findByEstado(String estado);
    
    // Buscar citas por especialista y fecha
    List<CitaModel> findByEspecialistaIdAndFecha(Long especialistaId, LocalDate fecha);
}
