package com.psicoclinic.psicoclinic.services;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.psicoclinic.psicoclinic.models.PersonalModel;
import com.psicoclinic.psicoclinic.repositories.PersonalRepository;

@Service
public class PersonalService {
    @Autowired
    PersonalRepository personalRepository;

    public ArrayList<PersonalModel> obtenerPersonal() {
        return (ArrayList<PersonalModel>) personalRepository.findAll();
    }

    public PersonalModel guardarPersonal(PersonalModel personal) {
        return personalRepository.save(personal);
    }

    public Optional<PersonalModel> obtenerPorId(Long id) {
        return personalRepository.findById(id);
    }

    public ArrayList<PersonalModel> obtenerPorNombre(String nombre) {
        return personalRepository.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(nombre, nombre);
    }

    public boolean eliminarPersonal(Long id) {
        try {
            personalRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }
}
