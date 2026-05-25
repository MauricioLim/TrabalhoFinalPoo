package com.trabalho.trabalhoFinal;

import java.time.LocalDate;

public class Despesa extends Financeiro{
    public Despesa(double valor, LocalDate data, Categoria categoria) {
        super(valor, data, categoria);
    }

    @Override
    public void setCategoria(Categoria categoria){
        if (categoria.equals(Categoria.ALIMENTACAO) || categoria.equals(Categoria.TRANSPORTE) || categoria.equals(Categoria.RESIDENCIA) || categoria.equals(Categoria.SAUDE) || categoria.equals(Categoria.EDUCACAO) || categoria.equals(Categoria.ENTRETERIMENTO) || categoria.equals(Categoria.OUTROAS_DESPESAS)){
            super.setCategoria(categoria);
        } else {
            throw new IllegalArgumentException("Categoria inválida para despesa.");
        }
    }
}
