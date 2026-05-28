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


    public Financeiro() throws FileNotFoundException {

    }

    public Financeiro(double valor, LocalDate data, Categoria categoria) throws FileNotFoundException {
        setValor(valor);
        setData(data);
        setCategoria(categoria);
    }

   public void adicionarArquivo(Financeiro financeiro) throws IOException {

        File arquivo = new File("C:/Users/mdlima/Documents/programas/trabalhoFinal/despesas.csv");

        if (financeiro == null) {
            throw new IllegalArgumentException("Financeiro não pode ser nulo.");
        }

       FileOutputStream fop = new FileOutputStream(arquivo, true);
       DataOutputStream dop = new DataOutputStream(fop);

        try {

            arquivo.createNewFile();

            if (!arquivo.exists() || arquivo.length() == 0) {
                dop.writeUTF("Valor;Data;Categoria");
                dop.writeUTF("\n");
            }

            String texto = String.valueOf(financeiro.getValor()) + ";" + financeiro.data.toString() + ";" + financeiro.getCategoria().toString() + "\n";
            dop.writeUTF(texto);

        } catch (IOException e) {
            throw new IOException("Erro ao escrever no arquivo: " + e.getMessage());
        } finally {
            dop.close();
        }
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

    public void adicionarFinanceiro(Financeiro financeiro) throws IOException {
        if (financeiro == null) {
            throw new IllegalArgumentException("Financeiro não pode ser nulo.");
        }

        adicionarArquivo(financeiro);
    }

    public ArrayList<Financeiro> lerArquivo(){
        ArrayList<Financeiro> lista = new ArrayList<>();

        File arquivo = new File("C:/Users/mdlima/Documents/programas/trabalhoFinal/despesas.csv");
        String separador = ";";

        try (FileInputStream fis = new FileInputStream(arquivo);
             BufferedInputStream bis = new BufferedInputStream(fis);
             DataInputStream dis = new DataInputStream(bis);
             BufferedReader br = new BufferedReader(new InputStreamReader(dis, "UTF-8"))) {

            String linha;

            // Lê o cabeçalho e ignora (se houver)
            String cabecalho = br.readLine();

            // Loop para ler o restante das linhas
            while ((linha = br.readLine()) != null) {
                // Divide a linha em colunas usando o separador
                String[] colunas = linha.split(separador);

                // Verifica se a linha possui o número correto de colunas para evitar erros
                if (colunas.length >= 3) {
                    String coluna1 = colunas[0].trim();
                    String coluna2 = colunas[1].trim();
                    String coluna3 = colunas[2].trim();

                    Financeiro f = new Financeiro(Double.parseDouble(coluna1), LocalDate.parse(coluna2), Categoria.valueOf(coluna3));
                    lista.add(f);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return lista;
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
        ArrayList<Financeiro> lista = lerArquivo();
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
        ArrayList<Financeiro> lista = lerArquivo();
        lista.stream().sorted(Comparator.comparing(Financeiro::getData));

        return lista;
    }

}

