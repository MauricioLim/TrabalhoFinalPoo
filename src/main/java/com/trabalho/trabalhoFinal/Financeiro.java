package com.trabalho.trabalhoFinal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class Financeiro {
    private double valor;
    private LocalDate data;
    private Categoria categoria;
    private ArrayList<Financeiro> financeiros = new ArrayList<>();

    public Financeiro() {
    }

    public Financeiro(double valor, LocalDate data, Categoria categoria) {
        setCategoria(categoria);
        setValor(valor);
        setCategoria(categoria);
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void adicionarFinanceiro(Financeiro financeiro) {
        if (financeiro == null) {
            throw new IllegalArgumentException("Financeiro não pode ser nulo.");
        }
        financeiros.add(financeiro);
    }

    public ArrayList<Financeiro> getFinanceiros() {
        return financeiros;
    }

    public double saldoTotal(){
        double total = 0;

        for (Financeiro f: this.financeiros){
            if (f instanceof Receita){
                total += f.getValor();
            } else if (f instanceof Despesa){
                total -= f.getValor();
            }
        }

        return total;
    }

    public double saldoData(LocalDate data){
        double total = 0;
        for (Financeiro f: this.financeiros){
            if (f.getData().isAfter(data)){
                return total;
            } else{
                if (f instanceof Receita){
                    total += f.getValor();
                } else if (f instanceof Despesa){
                    total -= f.getValor();
                }
            }
        }
        return total;
    }

    public ArrayList<Financeiro> listarData(){
        ArrayList<Financeiro> lista = (ArrayList<Financeiro>) this.financeiros.stream()
                .sorted(Comparator.comparing(Financeiro::getData))
                .toList();
        return lista;
    }

}
