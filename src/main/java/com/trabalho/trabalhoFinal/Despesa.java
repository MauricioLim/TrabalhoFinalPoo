package com.trabalho.trabalhoFinal;

import java.time.LocalDate;

/**
 * Representa uma despesa financeira doméstica.
 * Herda de {@link Lancamento} e valida que a categoria pertence ao grupo de despesas:
 * ALIMENTACAO, TRANSPORTE, RESIDENCIA, SAUDE, EDUCACAO, ENTRETENIMENTO ou OUTRAS_DESPESAS.
 */
public class Despesa extends Lancamento {

    /**
     * Construtor de uma despesa financeira.
     *
     * @param valor     Valor da despesa. Deve ser maior ou igual a zero.
     * @param data      Data da despesa. Não pode ser nula.
     * @param categoria Categoria da despesa. Deve ser uma categoria válida de despesa.
     */
    public Despesa(double valor, LocalDate data, Categoria categoria) {
        super(valor, data, categoria);
    }

    /**
     * Define a categoria da despesa, validando se pertence ao grupo de despesas.
     *
     * @param categoria Categoria a ser definida.
     * @throws IllegalArgumentException se a categoria não for válida para despesa.
     */
    @Override
    public void setCategoria(Categoria categoria) {
        if (categoria == Categoria.ALIMENTACAO
                || categoria == Categoria.TRANSPORTE
                || categoria == Categoria.RESIDENCIA
                || categoria == Categoria.SAUDE
                || categoria == Categoria.EDUCACAO
                || categoria == Categoria.ENTRETENIMENTO
                || categoria == Categoria.OUTRAS_DESPESAS) {
            super.setCategoria(categoria);
        } else {
            throw new IllegalArgumentException("Categoria inválida para despesa: " + categoria);
        }
    }
}
