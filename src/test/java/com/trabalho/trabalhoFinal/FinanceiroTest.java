package com.trabalho.trabalhoFinal;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FinanceiroTest {

    private Financeiro financeiro;

    @BeforeEach
    void setUp() throws FileNotFoundException {
        financeiro = new Financeiro(10, LocalDate.now(), Categoria.SALARIO);
    }

    @Test
    @DisplayName("Deve adicionar um financeiro válido sem lançar exceção")
    void testAdicionarFinanceiroValido() throws FileNotFoundException {
        Receita receita = new Receita(500, LocalDate.now(), Categoria.SALARIO);
        assertDoesNotThrow(() -> financeiro.adicionarFinanceiro(receita),
                "Adicionar financeiro válido não deve lançar exceção");
    }

    @Test
    @DisplayName("Deve lançar exceção ao adicionar financeiro nulo")
    void testAdicionarFinanceiroNulo() {
        assertThrows(IllegalArgumentException.class, () -> financeiro.adicionarFinanceiro(null),
                "Financeiro nulo deve lançar IllegalArgumentException");
    }

    @Test
    @DisplayName("Deve retornar saldo zero quando não há lançamentos")
    void testSaldoTotalVazio() {
        assertEquals(0, financeiro.saldoTotal(), "Saldo total sem lançamentos deve ser zero");
    }

    @Test
    @DisplayName("Deve somar receitas e subtrair despesas no saldo total")
    void testSaldoTotalComLancamentos() throws IOException {
        Receita receita = new Receita(1000, LocalDate.now(), Categoria.SALARIO);
        Despesa despesa = new Despesa(300, LocalDate.now(), Categoria.ALIMENTACAO);

        financeiro.adicionarFinanceiro(receita);
        financeiro.adicionarFinanceiro(despesa);

        assertEquals(700, financeiro.saldoTotal(), 0.001,
                "Saldo total deve ser receita - despesa");
    }

    @Test
    @DisplayName("Deve retornar saldo negativo quando despesas superam receitas")
    void testSaldoTotalNegativo() throws IOException {
        Receita receita = new Receita(100, LocalDate.now(), Categoria.SALARIO);
        Despesa despesa = new Despesa(500, LocalDate.now(), Categoria.SAUDE);

        financeiro.adicionarFinanceiro(receita);
        financeiro.adicionarFinanceiro(despesa);

        assertEquals(-400, financeiro.saldoTotal(), 0.001,
                "Saldo negativo deve ser calculado corretamente");
    }

    @Test
    @DisplayName("Deve retornar zero para data anterior a todos os lançamentos")
    void testSaldoDataAnteriorAosLancamentos() throws IOException {
        LocalDate hoje = LocalDate.now();
        Receita receita = new Receita(500, hoje, Categoria.SALARIO);
        financeiro.adicionarFinanceiro(receita);

        double saldo = financeiro.saldoData(hoje.minusDays(1));
        assertEquals(0, saldo, 0.001,
                "Saldo antes de qualquer lançamento deve ser zero");
    }

    @Test
    @DisplayName("Deve considerar apenas lançamentos até a data informada")
    void testSaldoDataComLancamentos() throws IOException {
        LocalDate passado = LocalDate.now().minusDays(10);
        LocalDate futuro = LocalDate.now().plusDays(10);

        Receita receitaPassado = new Receita(800, passado, Categoria.SALARIO);
        Despesa despesaFuturo = new Despesa(200, futuro, Categoria.TRANSPORTE);

        financeiro.adicionarFinanceiro(receitaPassado);
        financeiro.adicionarFinanceiro(despesaFuturo);

        double saldo = financeiro.saldoData(LocalDate.now());
        assertEquals(800, saldo, 0.001,
                "Saldo até hoje deve incluir apenas lançamentos passados");
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há lançamentos")
    void testListarDataVazia() {
        ArrayList<Financeiro> lista = financeiro.listarData();
        assertTrue(lista.isEmpty(), "Lista deve estar vazia quando não há lançamentos");
    }

    @Test
    @DisplayName("Deve retornar lançamentos ordenados por data")
    void testListarDataOrdenada() throws IOException {
        LocalDate data1 = LocalDate.of(2025, 1, 10);
        LocalDate data2 = LocalDate.of(2025, 3, 5);
        LocalDate data3 = LocalDate.of(2025, 6, 1);

        Receita r1 = new Receita(100, data2, Categoria.SALARIO);
        Receita r2 = new Receita(200, data1, Categoria.FERIAS);
        Despesa d1 = new Despesa(50, data3, Categoria.ALIMENTACAO);

        financeiro.adicionarFinanceiro(r1);
        financeiro.adicionarFinanceiro(r2);
        financeiro.adicionarFinanceiro(d1);

        ArrayList<Financeiro> lista = financeiro.listarData();

        assertTrue(lista.get(0).getData().isBefore(lista.get(1).getData()),
                "Primeiro elemento deve ter data anterior ao segundo");
        assertTrue(lista.get(1).getData().isBefore(lista.get(2).getData()),
                "Segundo elemento deve ter data anterior ao terceiro");
    }
}
