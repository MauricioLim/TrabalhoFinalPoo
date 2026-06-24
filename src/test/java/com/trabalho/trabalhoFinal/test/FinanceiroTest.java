package com.trabalho.trabalhoFinal.test;

import com.trabalho.trabalhoFinal.*;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários da classe {@link Financeiro}.
 * Cobre todos os métodos públicos que não são getter ou setter:
 * adicionarLancamento, saldoTotal, saldoAteData, getReceitas,
 * getDespesas, listarOrdenadoPorData, gerarExtrato e lerArquivo.
 *
 * Um arquivo CSV temporário é criado e removido a cada teste
 * para garantir isolamento entre os casos.
 */
public class FinanceiroTest {

    private static final String ARQUIVO_CSV = "despesas.csv";
    private Financeiro financeiro;

    @BeforeEach
    public void setUp() {
        File arquivo = new File(ARQUIVO_CSV);
        if (arquivo.exists()) {
            arquivo.delete();
        }
        financeiro = new Financeiro();
    }

    @AfterEach
    public void tearDown() {
        File arquivo = new File(ARQUIVO_CSV);
        if (arquivo.exists()) {
            arquivo.delete();
        }
    }

    @Test
    public void deveAdicionarReceita() throws IOException {
        Receita r = new Receita(2000.0, LocalDate.of(2025, 1, 5), Categoria.SALARIO);
        financeiro.adicionarLancamento(r);
        assertEquals(1, financeiro.getLancamentos().size());
    }

    @Test
    public void deveAdicionarDespesa() throws IOException {
        Despesa d = new Despesa(300.0, LocalDate.of(2025, 1, 10), Categoria.ALIMENTACAO);
        financeiro.adicionarLancamento(d);
        assertEquals(1, financeiro.getLancamentos().size());
    }

    @Test
    public void deveAdicionarMultiplosLancamentos() throws IOException {
        financeiro.adicionarLancamento(new Receita(5000.0, LocalDate.of(2025, 1, 1), Categoria.SALARIO));
        financeiro.adicionarLancamento(new Despesa(200.0, LocalDate.of(2025, 1, 5), Categoria.TRANSPORTE));
        financeiro.adicionarLancamento(new Despesa(100.0, LocalDate.of(2025, 1, 10), Categoria.ALIMENTACAO));
        assertEquals(3, financeiro.getLancamentos().size());
    }

    @Test
    public void deveLancarExcecaoAoAdicionarLancamentoNulo() throws IOException {
        assertThrows(IllegalArgumentException.class, () ->
                financeiro.adicionarLancamento(null)
        );
    }

    @Test
    public void deveCalcularSaldoTotalMistoPositivo() throws IOException {
        financeiro.adicionarLancamento(new Receita(5000.0, LocalDate.of(2025, 1, 1), Categoria.SALARIO));
        financeiro.adicionarLancamento(new Despesa(1000.0, LocalDate.of(2025, 1, 10), Categoria.RESIDENCIA));
        financeiro.adicionarLancamento(new Despesa(300.0, LocalDate.of(2025, 1, 15), Categoria.ALIMENTACAO));
        assertEquals(3700.0, financeiro.saldoTotal());
    }

    @Test
    public void deveConsiderarLancamentosNaDataExata() throws IOException {
        financeiro.adicionarLancamento(new Receita(3000.0, LocalDate.of(2025, 3, 1), Categoria.SALARIO));
        assertEquals(3000.0, financeiro.saldoAteData(LocalDate.of(2025, 3, 1)));
    }

    @Test
    public void deveIgnorarLancamentosAposDataDeCorte() throws IOException {
        financeiro.adicionarLancamento(new Receita(3000.0, LocalDate.of(2025, 1, 1), Categoria.SALARIO));
        financeiro.adicionarLancamento(new Despesa(500.0, LocalDate.of(2025, 6, 1), Categoria.SAUDE));
        // A despesa de junho não deve ser considerada
        assertEquals(3000.0, financeiro.saldoAteData(LocalDate.of(2025, 3, 31)));
    }

    @Test
    public void deveCalcularSaldoAteDataComMistoDeLancamentos() throws IOException {
        financeiro.adicionarLancamento(new Receita(5000.0, LocalDate.of(2025, 1, 1), Categoria.SALARIO));
        financeiro.adicionarLancamento(new Despesa(200.0, LocalDate.of(2025, 1, 10), Categoria.ALIMENTACAO));
        financeiro.adicionarLancamento(new Receita(1000.0, LocalDate.of(2025, 2, 1), Categoria.OUTRAS_RECEITAS));
        financeiro.adicionarLancamento(new Despesa(800.0, LocalDate.of(2025, 3, 1), Categoria.RESIDENCIA));
        // Corte em janeiro: 5000 - 200 = 4800
        assertEquals(4800.0, financeiro.saldoAteData(LocalDate.of(2025, 1, 31)));
    }

    @Test
    public void deveRetornarApenasReceitas() throws IOException {
        financeiro.adicionarLancamento(new Receita(3000.0, LocalDate.of(2025, 1, 1), Categoria.SALARIO));
        financeiro.adicionarLancamento(new Despesa(200.0, LocalDate.of(2025, 1, 5), Categoria.ALIMENTACAO));
        financeiro.adicionarLancamento(new Receita(500.0, LocalDate.of(2025, 1, 10), Categoria.OUTRAS_RECEITAS));

        ArrayList<Lancamento> receitas = financeiro.getReceitas();
        assertEquals(2, receitas.size());
        assertTrue(receitas.stream().allMatch(l -> l instanceof Receita));
    }

    @Test
    public void deveRetornarApenasDespesas() throws IOException {
        financeiro.adicionarLancamento(new Receita(3000.0, LocalDate.of(2025, 1, 1), Categoria.SALARIO));
        financeiro.adicionarLancamento(new Despesa(200.0, LocalDate.of(2025, 1, 5), Categoria.ALIMENTACAO));
        financeiro.adicionarLancamento(new Despesa(100.0, LocalDate.of(2025, 1, 10), Categoria.TRANSPORTE));

        ArrayList<Lancamento> despesas = financeiro.getDespesas();
        assertEquals(2, despesas.size());
        assertTrue(despesas.stream().allMatch(l -> l instanceof Despesa));
    }

    @Test
    public void deveOrdenarLancamentosPorDataCrescente() throws IOException {
        financeiro.adicionarLancamento(new Despesa(100.0, LocalDate.of(2025, 3, 1), Categoria.ALIMENTACAO));
        financeiro.adicionarLancamento(new Receita(3000.0, LocalDate.of(2025, 1, 1), Categoria.SALARIO));
        financeiro.adicionarLancamento(new Despesa(200.0, LocalDate.of(2025, 2, 1), Categoria.TRANSPORTE));

        ArrayList<Lancamento> ordenados = financeiro.listarOrdenadoPorData();

        assertEquals(LocalDate.of(2025, 1, 1), ordenados.get(0).getData());
        assertEquals(LocalDate.of(2025, 2, 1), ordenados.get(1).getData());
        assertEquals(LocalDate.of(2025, 3, 1), ordenados.get(2).getData());
    }

    @Test
    public void deveGerarExtratoOrdenadoPorData() throws IOException {
        financeiro.adicionarLancamento(new Despesa(500.0, LocalDate.of(2025, 3, 1), Categoria.RESIDENCIA));
        financeiro.adicionarLancamento(new Receita(5000.0, LocalDate.of(2025, 1, 1), Categoria.SALARIO));

        ArrayList<String> extrato = financeiro.gerarExtrato();

        // Primeira linha deve ser de janeiro (mais antiga)
        assertTrue(extrato.get(0).contains("2025-01-01"));
        // Segunda linha deve ser de março
        assertTrue(extrato.get(1).contains("2025-03-01"));
    }


    @Test
    public void deveIdentificarTipoCorretamenteNoExtrato() throws IOException {
        financeiro.adicionarLancamento(new Receita(2000.0, LocalDate.of(2025, 1, 1), Categoria.FERIAS));
        financeiro.adicionarLancamento(new Despesa(300.0, LocalDate.of(2025, 1, 5), Categoria.EDUCACAO));

        ArrayList<String> extrato = financeiro.gerarExtrato();

        assertTrue(extrato.get(0).contains("RECEITA"));
        assertTrue(extrato.get(1).contains("DESPESA"));
    }

    @Test
    public void deveLerArquivoECarregarLancamentos() throws IOException {
        // Adiciona lançamentos (grava no CSV) e cria novo Financeiro para simular reinício
        financeiro.adicionarLancamento(new Receita(3000.0, LocalDate.of(2025, 1, 1), Categoria.SALARIO));
        financeiro.adicionarLancamento(new Despesa(200.0, LocalDate.of(2025, 1, 5), Categoria.ALIMENTACAO));

        Financeiro novoFinanceiro = new Financeiro(); // lerArquivo() chamado no construtor
        assertEquals(2, novoFinanceiro.getLancamentos().size());
    }
}
