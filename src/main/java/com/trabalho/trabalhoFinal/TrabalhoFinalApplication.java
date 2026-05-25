package com.trabalho.trabalhoFinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootApplication
public class TrabalhoFinalApplication {

	public static void main(String[] args) {

		Financeiro f = new Financeiro();
		LocalDate data = LocalDate.of(2024, 6, 1);
		Receita salario = new Receita(5000, data, Categoria.SALARIO);
		f.adicionarFinanceiro(salario);

		Despesa despesa = new Despesa(1500, data, Categoria.RESIDENCIA);
		f.adicionarFinanceiro(despesa);

		System.out.println("Saldo total: " + f.saldoTotal());

	}

}
