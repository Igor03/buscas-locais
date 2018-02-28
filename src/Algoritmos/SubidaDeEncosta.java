package Algoritmos;

import Problemas.Rainhas;
import java.util.LinkedList;
import java.util.Random;

import Estruturas.*;

public class SubidaDeEncosta {

	public void executar(Rainhas problema) {

		No atual = new No(problema.tabuleiro);
		No vizinho;
		int qtdMovimentosPlanos = 0;

		System.out.println("----------------------- ESTADO INICIAL ---------------------");
		problema.mostrarTabuleiro(atual.estado);

		while (qtdMovimentosPlanos < 100 && problema.calculaCustoAtual(atual.estado) != 0) {

			vizinho = new No(problema.funcaoSucessora(atual.estado));

			if (problema.calculaCustoAtual(vizinho.estado) < problema.calculaCustoAtual(atual.estado)) {
				
				atual.estado = deepCopy(vizinho.estado);
				problema.calculaCustoHeuristico(atual.estado);
				
			} else if (veificaPlanicie((atual.estado), problema.calculaCustoAtual(atual.estado)).size() != 1) {
				
				Random rand = new Random();
				int size = veificaPlanicie((atual.estado), problema.calculaCustoAtual(atual.estado)).size();
				int[] indices = veificaPlanicie((atual.estado), problema.calculaCustoAtual(atual.estado))
						.get(rand.nextInt(size - 1));
				atual.estado[indices[0]].posicaoRainha = indices[1];
				problema.calculaCustoHeuristico(atual.estado);
				problema.movimentos.add(indices);
				qtdMovimentosPlanos++;
			}
		}
		
		System.out.println("\n------------------------ ESTADO FINAL ----------------------");;
		problema.mostrarTabuleiro(atual.estado);
		System.out.println("Movimentos planos: " + qtdMovimentosPlanos);
	}

	private Coluna[] deepCopy(Coluna[] tabuleiro) {

		Coluna[] temp = new Coluna[tabuleiro.length];

		for (int i = 0; i < tabuleiro.length; i++) {
			Coluna c = tabuleiro[i];
			int[] cC = tabuleiro[i].custoCelulas.clone();

			temp[i] = new Coluna(c.posicaoRainha);
			temp[i].rainha = new Rainha(tabuleiro[i].rainha.nome, tabuleiro[i].rainha.id);
			temp[i].custoCelulas = cC;

		}
		return temp;
	}

	public LinkedList<int[]> veificaPlanicie(Coluna[] estado, int custo) {

		LinkedList<int[]> pontosPlanos = new LinkedList<>();

		for (int i = 0; i < estado.length; i++)
			for (int j = 0; j < estado.length; j++)
				if (estado[i].custoCelulas[j] == custo)
					pontosPlanos.add(new int[] { i, j });
		pontosPlanos.add(new int[] { -1, -1 });
		return pontosPlanos;
	}

	public static void main(String[] args) {

		Rainhas problema = new Rainhas();
		SubidaDeEncosta agente = new SubidaDeEncosta();
		problema.inicializarTabuleiro();
		agente.executar(problema);

	}
}