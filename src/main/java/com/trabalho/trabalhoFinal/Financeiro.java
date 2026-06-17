package com.trabalho.trabalhoFinal;

import java.io.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class Financeiro  extends Lancamento{
    
   public Financeiro(double valor, LocalDate data, Categoria categoria) throws FileNotFoundException {
		super(valor, data, categoria);
		// TODO Auto-generated constructor stub
	}

   public ArrayList<Lancamento> getReceitas(){
   	ArrayList<Lancamento> receitas = new ArrayList();
   	for(Lancamento f: getLancamentos()) {
   		if (f instanceof Receita) {
   			receitas.add(f);
   		}
   	}
   	return receitas;
   }
   
   public ArrayList<Lancamento> getDespesas(){
   	ArrayList<Lancamento> despesas = new ArrayList();
   	
   	for(Lancamento f: getLancamentos()) {
   		if (f instanceof Despesa) {
   			despesas.add(f);
   		}
   	}
   	return despesas;
   }

   
    public double saldoTotal(){
        double total = 0;

        for (Lancamento f: getLancamentos()){
            if (f instanceof Receita){
                total += f.getValor();
            } else if (f instanceof Despesa){
                total -= f.getValor();
            }
        }

        return total;
    }
    
    public double saldoData(LocalDate data){
    	ArrayList<Lancamento> ordenada = new ArrayList();
    	ordenada = listarData();
    	
        double total = 0;
        for (Lancamento f: ordenada){
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

    public double extratoReceita() {
    	double total = 0;
    	
    	for(Lancamento l: getLancamentos()) {
    		if (l instanceof Receita){
                total += l.getValor();
            }
    	}
    	
    	return total;
    }
    
    public double extratoDespeca() {
    	double total = 0;
    	
    	ArrayList<Lancamento> lancamentos = new ArrayList<>(getLancamentos()); 
    	
    	for(Lancamento l: lancamentos) {
    		if (l instanceof Despesa){
                total -= l.getValor();
            }
    	}
    	
    	return total;
    }
    
    public ArrayList<Lancamento> listarData() {
        ArrayList<Lancamento> lista = new ArrayList<>();

        for (int i = 0; i < getLancamentos().size() - 1; i++) {
            Lancamento atual = getLancamentos().get(i);
            Lancamento proximo = getLancamentos().get(i + 1);

            if (atual.getData().isEqual(proximo.getData())) {
                lista.add(atual);
                lista.add(proximo);
            }
        }

        return lista;
    }
}

