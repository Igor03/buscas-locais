package Algoritmos;

import java.util.Random;

import Estruturas.*;
import Problemas.Rainhas;

public class TemperaSimulada {

	public void executar(Rainhas problema) {

		No atual = new No(deepCopy(problema.tabuleiro));
		No vizinho = new No(deepCopy(problema.tabuleiro));
		Random rand = new Random();
		int delta;
		double temperatura = 1000; // Temperatura inicial
		
		System.out.println("---------------------- ESTADO INICIAL --------------------");
		problema.mostrarTabuleiro(atual.estado);
		
		for (double tempo = 0; tempo <10000; tempo = tempo + 0.03) {

			if (problema.calculaCustoAtual(atual.estado) == 0) {
				System.out.println(" \n----------------------- ESTADO FINAL -----------------------");
				problema.mostrarTabuleiro(atual.estado);
				return;
			}

			temperatura = 1/tempo;
			
			vizinho.estado[rand.nextInt(8)].posicaoRainha = rand.nextInt(8);
			//problema.calculaCustoHeuristico(vizinho.estado);
			
			delta = problema.calculaCustoAtual(vizinho.estado) - problema.calculaCustoAtual(atual.estado);
			
			if (delta < 0) {
				atual.estado = deepCopy(vizinho.estado);
			} else {
				if (rand.nextDouble() <= 1/Math.exp(delta / temperatura)) {
					atual.estado = deepCopy(vizinho.estado);
				}
			}
		}
		System.out.println(" \n-------------------------- ESTADO FINAL -------------------------");
		problema.mostrarTabuleiro(atual.estado);
	}

	/* Para tabuleiro */
	private Coluna[] deepCopy(Coluna[] tabuleiro) {

		Coluna[] temp = new Coluna[tabuleiro.length];

		for (int i = 0; i < tabuleiro.length; i++) {
			temp[i] = new Coluna(tabuleiro[i].posicaoRainha);
			temp[i].rainha = new Rainha(tabuleiro[i].rainha.nome, tabuleiro[i].rainha.id);
			temp[i].custoCelulas = tabuleiro[i].custoCelulas.clone();
		}
		return temp;
	}

	public static void main(String[] args) {

		// Random rand = new Random();
		Rainhas problema = new Rainhas();
		TemperaSimulada agente = new TemperaSimulada();
		problema.inicializarTabuleiro();
		agente.executar(problema);

		// System.out.println(rand.nextDouble());

	}
}
