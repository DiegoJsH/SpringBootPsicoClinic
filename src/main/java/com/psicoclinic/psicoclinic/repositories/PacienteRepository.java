package com.psicoclinic.psicoclinic.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.psicoclinic.psicoclinic.models.PacienteModel;

@Repository
public interface PacienteRepository extends CrudRepository<PacienteModel, Long>{
    public abstract ArrayList<PacienteModel> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(String nombre, String apellido);
}
