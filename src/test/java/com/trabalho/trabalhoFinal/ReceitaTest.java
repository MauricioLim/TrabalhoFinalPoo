package com.trabalho.trabalhoFinal;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReceitaTest {

    @Test
    @DisplayName("Deve lançar exceção ao construir Receita com categoria de despesa")
    void testConstrutorCategoriaInvalida() {
        assertThrows(IllegalArgumentException.class,
                () -> new Receita(100, LocalDate.now(), Categoria.ALIMENTACAO),
                "Construtor com categoria de despesa deve lançar IllegalArgumentException");
    }
}
