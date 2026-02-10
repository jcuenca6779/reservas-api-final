package com.diogo.reservasapi.controller;

import com.diogo.reservasapi.entity.Reserva;
import com.diogo.reservasapi.exception.NotFoundException;
import com.diogo.reservasapi.service.ReservaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservaController.class)
class ReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservaService reservaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listar_deberiaRetornar200YLista() throws Exception {
        when(reservaService.listar()).thenReturn(List.of(reservaEjemplo(1L)));

        mockMvc.perform(get("/api/reservas"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void obtenerPorId_cuandoNoExiste_deberiaRetornar404() throws Exception {
        when(reservaService.obtenerPorId(9999L))
                .thenThrow(new NotFoundException("Reserva no encontrada con id: 9999"));

        mockMvc.perform(get("/api/reservas/9999"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void crear_valida_deberiaRetornar201() throws Exception {
        Reserva entrada = nuevaReservaValidaSinId();
        Reserva salida = reservaEjemplo(1L);

        when(reservaService.crear(any(Reserva.class))).thenReturn(salida);

        mockMvc.perform(post("/api/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    private Reserva nuevaReservaValidaSinId() {
        Reserva r = new Reserva();
        r.setNombreCliente("Juan Perez");
        r.setCancha("Cancha 1");
        r.setFecha(LocalDate.now().plusDays(1));
        r.setHoraInicio(LocalTime.of(15, 0));
        r.setDuracionMin(60);
        return r;
    }

    private Reserva reservaEjemplo(Long id) {
        Reserva r = nuevaReservaValidaSinId();
        r.setId(id);
        return r;
    }
}