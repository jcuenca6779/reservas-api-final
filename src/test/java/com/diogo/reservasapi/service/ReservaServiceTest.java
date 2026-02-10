package com.diogo.reservasapi.service;

import com.diogo.reservasapi.entity.Reserva;
import com.diogo.reservasapi.exception.NotFoundException;
import com.diogo.reservasapi.repository.ReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservaServiceTest {

    private ReservaRepository reservaRepository;
    private ReservaService reservaService;

    @BeforeEach
    void setUp() {
        reservaRepository = Mockito.mock(ReservaRepository.class);
        reservaService = new ReservaService(reservaRepository);
    }

    @Test
    void crear_deberiaGuardarReserva() {
        Reserva r = nuevaReservaValida();
        when(reservaRepository.save(any(Reserva.class))).thenAnswer(inv -> inv.getArgument(0));

        Reserva creada = reservaService.crear(r);

        assertNotNull(creada);
        verify(reservaRepository, times(1)).save(r);
    }

    @Test
    void obtenerPorId_cuandoNoExiste_deberiaLanzarNotFound() {
        when(reservaRepository.findById(9999L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> reservaService.obtenerPorId(9999L));
        verify(reservaRepository, times(1)).findById(9999L);
    }

    @Test
    void eliminar_cuandoExiste_deberiaEliminar() {
        Reserva r = nuevaReservaValida();
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(r));

        reservaService.eliminar(1L);

        verify(reservaRepository, times(1)).delete(r);
    }

    private Reserva nuevaReservaValida() {
        Reserva r = new Reserva();
        r.setNombreCliente("Juan Perez");
        r.setCancha("Cancha 1");
        r.setFecha(LocalDate.now().plusDays(1));
        r.setHoraInicio(LocalTime.of(15, 0));
        r.setDuracionMin(60);
        return r;
    }
}
