package com.diogo.reservasapi.service;

import com.diogo.reservasapi.entity.Reserva;
import com.diogo.reservasapi.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import com.diogo.reservasapi.exception.NotFoundException;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    // CREAR
    public Reserva crear(Reserva reserva) {
        if (reserva.getFecha().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha no puede ser anterior a hoy");
        }
        return reservaRepository.save(reserva);
    }

    // LISTAR TODAS
    public List<Reserva> listar() {
        return reservaRepository.findAll();
    }

    // BUSCAR POR ID
    public Reserva obtenerPorId(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Reserva no encontrada con id: " + id)
                );
    }

    // ACTUALIZAR
    public Reserva actualizar(Long id, Reserva reservaActualizada) {
        Reserva reservaExistente = obtenerPorId(id);

        reservaExistente.setNombreCliente(reservaActualizada.getNombreCliente());
        reservaExistente.setCancha(reservaActualizada.getCancha());
        reservaExistente.setFecha(reservaActualizada.getFecha());
        reservaExistente.setHoraInicio(reservaActualizada.getHoraInicio());
        reservaExistente.setDuracionMin(reservaActualizada.getDuracionMin());

        return reservaRepository.save(reservaExistente);
    }

    // ELIMINAR
    public void eliminar(Long id) {
        Reserva reserva = obtenerPorId(id);
        reservaRepository.delete(reserva);
    }
}
