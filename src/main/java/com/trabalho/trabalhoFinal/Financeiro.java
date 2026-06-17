package com.trabalho.trabalhoFinal;

import java.io.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class Financeiro {
    private double valor;
    private LocalDate data;
    private Categoria categoria;
    private ArrayList<Financeiro> financeiros = new ArrayList<>();


    public Financeiro(double valor, LocalDate data, Categoria categoria) throws FileNotFoundException {
        setValor(valor);
        setData(data);
        setCategoria(categoria);
    }

   public void adicionarArquivo(Financeiro financeiro) throws IOException {

        File arquivo = new File("despesas.csv");
        if (!arquivo.exists()){
            arquivo.createNewFile();
        }

        if (financeiro == null) {
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

            //String texto = String.valueOf(financeiro.getValor()) + ";" + financeiro.data.toString() + ";" + financeiro.getCategoria().toString() + "\n";
            //dop.writeUTF(texto);
            buf.write(String.valueOf(financeiro.getValor()) + ";" + financeiro.data.toString() + ";" + financeiro.getCategoria().toString());
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

    public ArrayList<Financeiro> getReceitas(){
    	ArrayList<Financeiro> receitas = new ArrayList();
    	for(Financeiro f: this.financeiros) {
    		if (f instanceof Receita) {
    			receitas.add(f);
    		}
    	}
    	
    	return receitas;
    }
    
    public ArrayList<Financeiro> getDespesas(){
    	ArrayList<Financeiro> despesas = new ArrayList();
    	
    	for(Financeiro f: this.financeiros) {
    		if (f instanceof Despesa) {
    			despesas.add(f);
    		}
    	}
    	return despesas;
    }
    
    public void adicionarFinanceiro(Financeiro financeiro) throws IOException {
        if (financeiro == null) {
            throw new IllegalArgumentException("Financeiro não pode ser nulo.");
        }

        this.financeiros.add(financeiro);
        adicionarArquivo(financeiro);
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
                        this.financeiros.add(f);
                    } else {
                        Despesa f = new Despesa(Double.parseDouble(coluna1), LocalDate.parse(coluna2), Categoria.valueOf(coluna3));
                        this.financeiros.add(f);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

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
    	ArrayList<Financeiro> ordenada = new ArrayList();
    	ordenada = listarData();
    	
        double total = 0;
        for (Financeiro f: ordenada){
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
    	ArrayList<Financeiro> lista = new ArrayList<>(this.financeiros);
    	lista.sort(Comparator.comparing(Financeiro::getData));
        return lista;
    }

}

