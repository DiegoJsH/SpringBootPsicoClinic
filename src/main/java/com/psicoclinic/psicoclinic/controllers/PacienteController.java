package com.psicoclinic.psicoclinic.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.psicoclinic.psicoclinic.models.PacienteModel;
import com.psicoclinic.psicoclinic.services.PacienteService;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    PacienteService pacienteService;

    @GetMapping()
    public ArrayList<PacienteModel> obtenerPacientes() {
        return pacienteService.obtenerPacientes();
    }

    @PostMapping()
    public PacienteModel guardarPaciente(@RequestBody PacienteModel paciente) {
        return this.pacienteService.guardarPaciente(paciente);
    }

    @GetMapping(path = "/{id}")
    public Optional<PacienteModel> obtenerPacientePorId(@PathVariable("id") Long id) {
        return this.pacienteService.obtenerPorId(id);
    }

    @GetMapping("/query")
    public ArrayList<PacienteModel> obtenerPacientePorNombre(@RequestParam("nombre") String nombre) {
        return this.pacienteService.obtenerPorNombre(nombre);
    }

    @DeleteMapping(path = "/{id}")
    public String eliminarPorId(@PathVariable("id") Long id) {
        boolean ok = this.pacienteService.eliminarPaciente(id);
        if (ok) {
            return "Se eliminó el paciente con id " + id;
        } else {
            return "No se pudo eliminar el paciente con id " + id;
        }
    }

    @PutMapping(path = "/{id}")
    public PacienteModel actualizarPaciente(@PathVariable("id") Long id, @RequestBody PacienteModel paciente) {
        paciente.setId(id);
        return this.pacienteService.guardarPaciente(paciente);
    }
}
