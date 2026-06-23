package com.trabalho.trabalhoFinal.test;

import com.trabalho.trabalhoFinal.Categoria;
import com.trabalho.trabalhoFinal.Receita;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários da classe {@link Receita}.
 * Valida a criação de receitas com dados válidos e o comportamento
 * esperado ao fornecer dados inválidos.
 */
public class ReceitaTest {

    // -------------------------------------------------------------------------
    // Criação com dados válidos
    // -------------------------------------------------------------------------

    @Test
    public void deveCriarReceitaComSalario() {
        Receita r = new Receita(3000.0, LocalDate.of(2025, 1, 5), Categoria.SALARIO);
        assertEquals(3000.0, r.getValor());
        assertEquals(LocalDate.of(2025, 1, 5), r.getData());
        assertEquals(Categoria.SALARIO, r.getCategoria());
    }

    @Test
    public void deveLancarExcecaoComCategoriaDeSpesa() {
        assertThrows(IllegalArgumentException.class, () ->
            new Receita(1000.0, LocalDate.of(2025, 1, 1), Categoria.ALIMENTACAO)
        );
    }


   
}
