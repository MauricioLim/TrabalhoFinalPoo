package com.trabalho.trabalhoFinal;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class Lancamento {
	
	    private double valor;
	    private LocalDate data;
	    private Categoria categoria;
	    private ArrayList<Lancamento> lancamentos = new ArrayList<>();


	    public Lancamento(double valor, LocalDate data, Categoria categoria) throws FileNotFoundException {
	        setValor(valor);
	        setData(data);
	        setCategoria(categoria);
	    }

	   public void adicionarArquivo(Lancamento lancamento) throws IOException {

	        File arquivo = new File("despesas.csv");
	        if (!arquivo.exists()){
	            arquivo.createNewFile();
	        }

	        if (lancamento == null) {
	            throw new IllegalArgumentException("Financeiro não pode ser nulo.");
	        }

	       FileOutputStream fop = new FileOutputStream(arquivo, true);
	       BufferedWriter buf = new BufferedWriter(new OutputStreamWriter(fop, "UTF-8"));
	       DataOutputStream dop = new DataOutputStream(fop);
	        try {
	            if (!arquivo.exists() || arquivo.length() == 0) {
	                buf.write("Valor;Data;Categoria");
	                buf.write("\n");
	            }

	            buf.write(String.valueOf(lancamento.getValor()) + ";" + lancamento.data.toString() + ";" + lancamento.getCategoria().toString());
	            buf.write("\n");

	        } catch (IOException e) {
	            throw new IOException("Erro ao escrever no arquivo: " + e.getMessage());
	        } finally {
	            buf.close();
	        }
	   }

	    public double getValor() {
	        return valor;
	    }

	    public ArrayList<Lancamento> getLancamentos(){
	    	return this.lancamentos;
	    }
	    
	    public void setValor(double valor) {
	    	if (valor < 0) {
	    		throw new IllegalArgumentException("Valor inváido");
	    	}
	        this.valor = valor;
	    }

	    public LocalDate getData() {
	        return data;
	    }

	    public void setData(LocalDate data) {
	    	if (data == null) {
	    		throw new IllegalArgumentException("Data inválida");
	    	}
	        this.data = data;
	    }
	    

	    public Categoria getCategoria() {
	        return categoria;
	    }

	    public void setCategoria(Categoria categoria) {
	    	if (categoria == null) {
	    		throw new IllegalArgumentException("Categoria inválida");
	    	}
	        this.categoria = categoria;
	    }

	    public void adicionarFinanceiro(Lancamento lancamento) throws IOException {
	        if (lancamento == null) {
	            throw new IllegalArgumentException("Financeiro não pode ser nulo.");
	        }

	        this.lancamentos.add(lancamento);
	        adicionarArquivo(lancamento);
	    }


	    public void lerArquivo(){
	        File arquivo = new File("despesas.csv");
	        String separador = ";";

	        try (FileInputStream fis = new FileInputStream(arquivo);
	             BufferedInputStream bis = new BufferedInputStream(fis);
	             DataInputStream dis = new DataInputStream(bis);
	             BufferedReader br = new BufferedReader(new InputStreamReader(dis, "UTF-8"))) {

	            String linha;

	            String cabecalho = br.readLine();

	            while ((linha = br.readLine()) != null) {

	                String[] colunas = linha.split(separador);


	                if (colunas.length >= 3) {
	                    String coluna1 = colunas[0].trim();
	                    String coluna2 = colunas[1].trim();
	                    String coluna3 = colunas[2].trim();

	                    if (coluna3.equals("SALARIO") || coluna3.equals("DECIMO_TERCEIRO") || coluna3.equals("FERIAS") || coluna3.equals("OUTRAS_RECEITAS")){
	                        Receita f = new Receita(Double.parseDouble(coluna1), LocalDate.parse(coluna2), Categoria.valueOf(coluna3));
	                        this.lancamentos.add(f);
	                    } else {
	                        Despesa f = new Despesa(Double.parseDouble(coluna1), LocalDate.parse(coluna2), Categoria.valueOf(coluna3));
	                        this.lancamentos.add(f);
	                    }
	                }
	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	    }



}
