package com.trabalho.trabalhoFinal;

import java.time.LocalDate;

public class Despesa extends Lancamento {


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
        if (categoria == Categoria.ALIMENTACAO || categoria == Categoria.TRANSPORTE || categoria == Categoria.RESIDENCIA
                || categoria == Categoria.SAUDE  || categoria == Categoria.EDUCACAO || categoria == Categoria.ENTRETENIMENTO
                || categoria == Categoria.OUTRAS_DESPESAS) {

            setCategoria(categoria);
        } else {
            throw new IllegalArgumentException("Categoria inválida");
        }
    }
}
