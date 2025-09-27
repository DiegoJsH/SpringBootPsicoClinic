package com.psicoclinic.psicoclinic.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psicoclinic.psicoclinic.models.PacienteModel;
import com.psicoclinic.psicoclinic.repositories.PacienteRepository;

@Service
public class PacienteService {
    @Autowired
    PacienteRepository pacienteRepository;

    public ArrayList<PacienteModel> obtenerPacientes(){
        return (ArrayList<PacienteModel>) pacienteRepository.findAll();
    }

    public PacienteModel guardarPaciente(PacienteModel paciente){
        return pacienteRepository.save(paciente);
    }

    public Optional<PacienteModel> obtenerPorId(Long id){
        return pacienteRepository.findById(id);
    }

    public ArrayList<PacienteModel> obtenerPorNombre(String nombre){
        return pacienteRepository.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(nombre, nombre);
    }

    public boolean eliminarPaciente(Long id){
        try{
            pacienteRepository.deleteById(id);
            return true;
        }catch(Exception err){
            return false;
        }
    }
}
