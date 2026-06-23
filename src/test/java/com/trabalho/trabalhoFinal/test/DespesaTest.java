package com.trabalho.trabalhoFinal.test;

import com.trabalho.trabalhoFinal.Categoria;
import com.trabalho.trabalhoFinal.Despesa;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários da classe {@link Despesa}.
 * Valida a criação de despesas com dados válidos e o comportamento
 * esperado ao fornecer dados inválidos.
 */
public class DespesaTest {


    @Test
    public void deveCriarDespesaComAlimentacao() {
        Despesa d = new Despesa(150.0, LocalDate.of(2025, 1, 10), Categoria.ALIMENTACAO);
        assertEquals(150.0, d.getValor());
        assertEquals(LocalDate.of(2025, 1, 10), d.getData());
        assertEquals(Categoria.ALIMENTACAO, d.getCategoria());
    }


    @Test
    public void deveLancarExcecaoComValorNegativo() {
        assertThrows(IllegalArgumentException.class, () ->
            new Despesa(-50.0, LocalDate.of(2025, 1, 1), Categoria.ALIMENTACAO)
        );
    }


    @Test
    public void deveLancarExcecaoComDataNula() {
        assertThrows(IllegalArgumentException.class, () ->
            new Despesa(100.0, null, Categoria.ALIMENTACAO)
        );
    }


    @Test
    public void deveLancarExcecaoComCategoriaDeReceita() {
        assertThrows(IllegalArgumentException.class, () ->
            new Despesa(100.0, LocalDate.of(2025, 1, 1), Categoria.SALARIO)
        );
    }

}
