package com.trabalho.trabalhoFinal;

import java.time.LocalDate;

/**
 * Representa uma receita financeira doméstica.
 * Herda de {@link Lancamento} e valida que a categoria pertence ao grupo de receitas:
 * SALARIO, DECIMO_TERCEIRO, FERIAS ou OUTRAS_RECEITAS.
 */
public class Receita extends Lancamento {

    /**
     * Construtor de uma receita financeira.
     *
     * @param valor     Valor da receita. Deve ser maior ou igual a zero.
     * @param data      Data da receita. Não pode ser nula.
     * @param categoria Categoria da receita. Deve ser uma categoria válida de receita.
     */
    public Receita(double valor, LocalDate data, Categoria categoria) {
        super(valor, data, categoria);
    }

    /**
     * Define a categoria da receita, validando se pertence ao grupo de receitas.
     *
     * @param categoria Categoria a ser definida.
     * @throws IllegalArgumentException se a categoria não for válida para receita.
     */
    @Override
    public void setCategoria(Categoria categoria) {
        if (categoria == Categoria.SALARIO
                || categoria == Categoria.DECIMO_TERCEIRO
                || categoria == Categoria.FERIAS
                || categoria == Categoria.OUTRAS_RECEITAS) {
            super.setCategoria(categoria);
        } else {
            throw new IllegalArgumentException("Categoria inválida para receita: " + categoria);
        }
    }
}
