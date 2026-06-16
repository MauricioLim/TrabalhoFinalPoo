package com.trabalho.trabalhoFinal;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FinanceiroTest {
	
	private Financeiro financeiro; 
	
	
	@BeforeEach
    void setUp() throws FileNotFoundException {
		LocalDate data = LocalDate.now();
        Financeiro financeiro = new Financeiro (10, data, Categoria.SALARIO);
    }
	
	@Test
	@DisplayName("Deve garantir que o valor seja definido e recuperado corretamente")
	void testGetSetValor() {
		double valor = 10;
		financeiro.setValor(valor);
		assertEquals(valor, financeiro.getValor(), "Valor do get deve ser igual ao definido pelo setter");
	}

	
	
	
	@Test
	@DisplayName("Deve garantir que o construtor inicializa os atributos corretamente")
	void testConstrutor () {
		
	}

}
