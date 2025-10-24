package com.psicoclinic.psicoclinic.controllers;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RestController;

import com.psicoclinic.psicoclinic.models.CitaModel;
import com.psicoclinic.psicoclinic.services.CitaService;

@RestController
@RequestMapping("/citas")
public class CitaController {
    
    @Autowired
    CitaService citaService;
    
    @GetMapping()
    public ArrayList<CitaModel> obtenerCitas() {
        return citaService.obtenerCitas();
    }
    
    @PostMapping()
    public CitaModel guardarCita(@RequestBody CitaModel cita) {
        return citaService.guardarCita(cita);
    }
    
    @GetMapping(path = "/{id}")
    public Optional<CitaModel> obtenerCitaPorId(@PathVariable("id") Long id) {
        return citaService.obtenerPorId(id);
    }
    
    @GetMapping("/paciente/{pacienteId}")
    public ArrayList<CitaModel> obtenerCitasPorPaciente(@PathVariable("pacienteId") Long pacienteId) {
        return citaService.obtenerPorPaciente(pacienteId);
    }
    
    @GetMapping("/especialista/{especialistaId}")
    public ArrayList<CitaModel> obtenerCitasPorEspecialista(@PathVariable("especialistaId") Long especialistaId) {
        return citaService.obtenerPorEspecialista(especialistaId);
    }
    
    @GetMapping("/fecha/{fecha}")
    public ArrayList<CitaModel> obtenerCitasPorFecha(@PathVariable("fecha") String fecha) {
        LocalDate localDate = LocalDate.parse(fecha);
        return citaService.obtenerPorFecha(localDate);
    }
    
    @GetMapping("/estado/{estado}")
    public ArrayList<CitaModel> obtenerCitasPorEstado(@PathVariable("estado") String estado) {
        return citaService.obtenerPorEstado(estado);
    }
    
    @GetMapping("/especialista/{especialistaId}/fecha/{fecha}")
    public ArrayList<CitaModel> obtenerCitasPorEspecialistaYFecha(
            @PathVariable("especialistaId") Long especialistaId,
            @PathVariable("fecha") String fecha) {
        LocalDate localDate = LocalDate.parse(fecha);
        return citaService.obtenerPorEspecialistaYFecha(especialistaId, localDate);
    }
    
    @PutMapping(path = "/{id}")
    public CitaModel actualizarCita(@RequestBody CitaModel request, @PathVariable("id") Long id) {
        return citaService.obtenerPorId(id)
                .map(cita -> {
                    cita.setPaciente(request.getPaciente());
                    cita.setEspecialista(request.getEspecialista());
                    cita.setFecha(request.getFecha());
                    cita.setHora(request.getHora());
                    cita.setTipoCita(request.getTipoCita());
                    cita.setNotas(request.getNotas());
                    cita.setEstado(request.getEstado());
                    return citaService.guardarCita(cita);
                })
                .orElseGet(() -> {
                    request.setId(id);
                    return citaService.guardarCita(request);
                });
    }
    
    @DeleteMapping(path = "/{id}")
    public String eliminarCita(@PathVariable("id") Long id) {
        boolean ok = citaService.eliminarCita(id);
        if (ok) {
            return "Se eliminó la cita con id " + id;
        } else {
            return "No se pudo eliminar la cita con id " + id;
        }
    }
}
