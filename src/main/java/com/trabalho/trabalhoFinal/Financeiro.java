package com.trabalho.trabalhoFinal;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Financeiro {

    private static final String NOME_ARQUIVO = "despesas.csv";
    private static final String SEPARADOR = ";";

    private ArrayList<Lancamento> lancamentos = new ArrayList<>();

    public Financeiro() {
        lerArquivo();
    }


    /**
     * @param lancamento Lançamento a ser adicionado. Não pode ser nulo.
     * @throws IllegalArgumentException se o lançamento for nulo.
     * @throws IOException              se ocorrer erro ao gravar no arquivo.
     */
    public void adicionarLancamento(Lancamento lancamento) throws IOException {
        if (lancamento == null) {
            throw new IllegalArgumentException("Lançamento não pode ser nulo.");
        }
        lancamentos.add(lancamento);
        gravarNoArquivo(lancamento);
    }


    /**
     *
     * @return saldo total (receitas menos despesas).
     */
    public double saldoTotal() {
        double total = 0;
        for (Lancamento l : lancamentos) {
            if (l instanceof Receita) {
                total += l.getValor();
            } else if (l instanceof Despesa) {
                total -= l.getValor();
            }
        }
        return total;
    }

    /**
     * @param data Data de corte para o cálculo do saldo.
     * @return saldo acumulado até a data informada.
     */
    public double saldoAteData(LocalDate data) {
        double total = 0;
        for (Lancamento l : lancamentos) {
            if (!l.getData().isAfter(data)) {
                if (l instanceof Receita) {
                    total += l.getValor();
                } else if (l instanceof Despesa) {
                    total -= l.getValor();
                }
            }
        }
        return total;
    }


    /**
     * @return lista contendo apenas os lançamentos do tipo {@link Receita}.
     */
    public ArrayList<Lancamento> getReceitas() {
        ArrayList<Lancamento> receitas = new ArrayList<>();
        for (Lancamento l : lancamentos) {
            if (l instanceof Receita) {
                receitas.add(l);
            }
        }
        return receitas;
    }

    /**
     *
     * @return lista contendo apenas os lançamentos do tipo {@link Despesa}.
     */
    public ArrayList<Lancamento> getDespesas() {
        ArrayList<Lancamento> despesas = new ArrayList<>();
        for (Lancamento l : lancamentos) {
            if (l instanceof Despesa) {
                despesas.add(l);
            }
        }
        return despesas;
    }

    /**
     *
     * @return lista de lançamentos ordenados cronologicamente.
     */
    public ArrayList<Lancamento> listarOrdenadoPorData() {
        ArrayList<Lancamento> ordenados = new ArrayList<>(lancamentos);
        ordenados.sort(Comparator.comparing(Lancamento::getData));
        return ordenados;
    }

    /**

     * @return lista de strings representando cada linha do extrato
     */
    public ArrayList<String> gerarExtrato() {
        ArrayList<String> extrato = new ArrayList<>();
        ArrayList<Lancamento> ordenados = listarOrdenadoPorData();
        double saldoAcumulado = 0;

        for (Lancamento l : ordenados) {
            String tipo;
            if (l instanceof Receita) {
                saldoAcumulado += l.getValor();
                tipo = "RECEITA";
            } else {
                saldoAcumulado -= l.getValor();
                tipo = "DESPESA";
            }

            String linha = String.format("Data: %s | Tipo: %s | Categoria: %s | Valor: R$ %.2f | Saldo: R$ %.2f", l.getData(),
                tipo,
                l.getCategoria(),
                l.getValor(),
                saldoAcumulado);
            extrato.add(linha);
        }

        return extrato;
    }

    /**
     * @return lista de todos os lançamentos.
     */
    public ArrayList<Lancamento> getLancamentos() {
        return lancamentos;
    }


    /**
     * @param lancamento Lançamento a ser gravado.
     * @throws IOException se ocorrer erro ao escrever no arquivo.
     */
    private void gravarNoArquivo(Lancamento lancamento) throws IOException {
        File arquivo = new File(NOME_ARQUIVO);
        boolean arquivoNovo = !arquivo.exists() || arquivo.length() == 0;

        try (FileOutputStream fos = new FileOutputStream(arquivo, true);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"))) {

            if (arquivoNovo) {
                bw.write("Valor;Data;Categoria");
                bw.newLine();
            }

            bw.write(lancamento.getValor() + SEPARADOR
                    + lancamento.getData().toString() + SEPARADOR
                    + lancamento.getCategoria().toString());
            bw.newLine();

        } catch (IOException e) {
            throw new IOException("Erro ao gravar no arquivo: " + e.getMessage());
        }
    }

    /**
     * Lê os lançamentos do arquivo CSV e os carrega na lista em memória.
     * Identifica receitas e despesas pela categoria lida.
     * Chamado automaticamente no construtor.
     */
    public void lerArquivo() {
        File arquivo = new File(NOME_ARQUIVO);
        if (!arquivo.exists()) {
            return;
        }

        try (FileInputStream fis = new FileInputStream(arquivo);
             BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"))) {

            String cabecalho = br.readLine(); // ignora o cabeçalho
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] colunas = linha.split(SEPARADOR);
                if (colunas.length >= 3) {
                    double valor = Double.parseDouble(colunas[0].trim());
                    LocalDate data = LocalDate.parse(colunas[1].trim());
                    Categoria categoria = Categoria.valueOf(colunas[2].trim());

                    if (categoria == Categoria.SALARIO
                            || categoria == Categoria.DECIMO_TERCEIRO
                            || categoria == Categoria.FERIAS
                            || categoria == Categoria.OUTRAS_RECEITAS) {
                        lancamentos.add(new Receita(valor, data, categoria));
                    } else {
                        lancamentos.add(new Despesa(valor, data, categoria));
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
