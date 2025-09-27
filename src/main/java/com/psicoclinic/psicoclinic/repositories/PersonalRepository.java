package com.psicoclinic.psicoclinic.repositories;

import java.util.ArrayList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.psicoclinic.psicoclinic.models.PersonalModel;

@Repository
public interface PersonalRepository extends CrudRepository<PersonalModel, Long> {
    public abstract ArrayList<PersonalModel> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(String nombre, String apellido);
}
