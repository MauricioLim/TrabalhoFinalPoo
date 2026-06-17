package com.trabalho.trabalhoFinal;

import java.io.FileNotFoundException;
import java.time.LocalDate;

public class Receita extends Lancamento{
    public Receita(double valor, LocalDate data, Categoria categoria) throws FileNotFoundException {
        super(valor, data, categoria);
    }

    @Override
    public void setCategoria(Categoria categoria){
        if (categoria.equals(Categoria.SALARIO) || categoria.equals(Categoria.DECIMO_TERCEIRO) || categoria.equals(Categoria.FERIAS) || categoria.equals(Categoria.OUTRAS_RECEITAS)){
            super.setCategoria(categoria);
        } else {
            throw new IllegalArgumentException("Categoria inválida para receita.");
        }
    }

}
