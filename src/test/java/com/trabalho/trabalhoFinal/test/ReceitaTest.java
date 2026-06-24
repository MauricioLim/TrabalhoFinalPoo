package com.trabalho.trabalhoFinal.test;

import com.trabalho.trabalhoFinal.Categoria;
import com.trabalho.trabalhoFinal.Receita;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ReceitaTest {


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
