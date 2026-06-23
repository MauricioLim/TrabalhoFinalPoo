package com.trabalho.trabalhoFinal;

import java.time.LocalDate;


public abstract class Lancamento {

    private double valor;
    private LocalDate data;
    private Categoria categoria;

   
    public Lancamento(double valor, LocalDate data, Categoria categoria) {
        setValor(valor);
        setData(data);
        setCategoria(categoria);
    }

    /**
     * Retorna o valor do lançamento.
     *
     * @return valor do lançamento.
     */
    public double getValor() {
        return valor;
    }

    /**
     * @param valor Valor a ser definido. Deve ser maior ou igual a zero.
     * @throws IllegalArgumentException se o valor for negativo.
     */
    public void setValor(double valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("Valor inválido: não pode ser negativo.");
        }
        this.valor = valor;
    }

    /**
     * Retorna a data do lançamento.
     *
     * @return data do lançamento.
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * @param data Data a ser definida. Não pode ser nula.
     * @throws IllegalArgumentException se a data for nula.
     */
    public void setData(LocalDate data) {
        if (data == null) {
            throw new IllegalArgumentException("Data inválida: não pode ser nula.");
        }
        this.data = data;
    }

    /**
     * Retorna a categoria do lançamento.
     *
     * @return categoria do lançamento.
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * @param categoria Categoria a ser definida. Não pode ser nula.
     * @throws IllegalArgumentException se a categoria for nula.
     */
    public void setCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("Categoria inválida: não pode ser nula.");
        }
        this.categoria = categoria;
    }

 
}
