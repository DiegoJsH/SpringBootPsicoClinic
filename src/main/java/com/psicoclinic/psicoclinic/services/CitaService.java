package com.psicoclinic.psicoclinic.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psicoclinic.psicoclinic.models.CitaModel;
import com.psicoclinic.psicoclinic.repositories.CitaRepository;

@Service
public class CitaService {
    
    @Autowired
    CitaRepository citaRepository;
    
    public ArrayList<CitaModel> obtenerCitas() {
        return (ArrayList<CitaModel>) citaRepository.findAll();
    }
    
    public CitaModel guardarCita(CitaModel cita) {
        return citaRepository.save(cita);
    }
    
    public Optional<CitaModel> obtenerPorId(Long id) {
        return citaRepository.findById(id);
    }
    
    public ArrayList<CitaModel> obtenerPorPaciente(Long pacienteId) {
        return (ArrayList<CitaModel>) citaRepository.findByPacienteId(pacienteId);
    }
    
    public ArrayList<CitaModel> obtenerPorEspecialista(Long especialistaId) {
        return (ArrayList<CitaModel>) citaRepository.findByEspecialistaId(especialistaId);
    }
    
    public ArrayList<CitaModel> obtenerPorFecha(LocalDate fecha) {
        return (ArrayList<CitaModel>) citaRepository.findByFecha(fecha);
    }
    
    public ArrayList<CitaModel> obtenerPorEstado(String estado) {
        return (ArrayList<CitaModel>) citaRepository.findByEstado(estado);
    }
    
    public ArrayList<CitaModel> obtenerPorEspecialistaYFecha(Long especialistaId, LocalDate fecha) {
        return (ArrayList<CitaModel>) citaRepository.findByEspecialistaIdAndFecha(especialistaId, fecha);
    }
    
    public boolean eliminarCita(Long id) {
        try {
            citaRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }
}
