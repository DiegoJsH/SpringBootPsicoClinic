package com.psicoclinic.psicoclinic.controllers;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.psicoclinic.psicoclinic.models.PersonalModel;
import com.psicoclinic.psicoclinic.services.PersonalService;

@RestController
@RequestMapping("/personal")
public class PersonalController {

    @Autowired
    PersonalService personalService;

    @GetMapping()
    public ArrayList<PersonalModel> obtenerPersonal() {
        return personalService.obtenerPersonal();
    }

    @PostMapping()
    public PersonalModel guardarPersonal(@RequestBody PersonalModel personal) {
        return this.personalService.guardarPersonal(personal);
    }

    @GetMapping(path = "/{id}")
    public Optional<PersonalModel> obtenerPersonalPorId(@PathVariable("id") Long id) {
        return this.personalService.obtenerPorId(id);
    }

    @GetMapping("/query")
    public ArrayList<PersonalModel> obtenerPersonalPorNombre(@RequestParam("nombre") String nombre) {
        return this.personalService.obtenerPorNombre(nombre);
    }

    @DeleteMapping(path = "/{id}")
    public String eliminarPorId(@PathVariable("id") Long id) {
        boolean ok = this.personalService.eliminarPersonal(id);
        return ok ? "Se eliminó el personal con id " + id : "No se pudo eliminar el personal con id " + id;
    }

    @PutMapping(path = "/{id}")
    public PersonalModel actualizarPersonal(@PathVariable("id") Long id, @RequestBody PersonalModel personal) {
        personal.setId(id);
        return this.personalService.guardarPersonal(personal);
    }
}
