package com.trabalho.trabalhoFinal;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DespesaTest {

    @Test
    @DisplayName("Deve lançar exceção ao construir Despesa com categoria de receita")
    void testConstrutorCategoriaInvalida() {
        assertThrows(IllegalArgumentException.class,
                () -> new Despesa(100, LocalDate.now(), Categoria.SALARIO),
                "Construtor com categoria de receita deve lançar IllegalArgumentException");
    }
}
