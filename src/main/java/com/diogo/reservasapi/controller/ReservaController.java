package com.diogo.reservasapi.controller;

import com.diogo.reservasapi.entity.Reserva;
import com.diogo.reservasapi.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    // POST - crear reserva
    @PostMapping
    public ResponseEntity<Reserva> crear(@Valid @RequestBody Reserva reserva) {
        Reserva creada = reservaService.crear(reserva);
        return new ResponseEntity<>(creada, HttpStatus.CREATED);
    }

    // GET - listar todas
    @GetMapping
    public ResponseEntity<List<Reserva>> listar() {
        return ResponseEntity.ok(reservaService.listar());
    }

    // GET - buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.obtenerPorId(id));
    }

    // PUT - actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Reserva reserva) {

        Reserva actualizada = reservaService.actualizar(id, reserva);
        return ResponseEntity.ok(actualizada);
    }

    // DELETE - eliminar
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        reservaService.eliminar(id);
    }
}
