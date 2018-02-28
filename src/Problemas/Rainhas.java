package Problemas;

import java.util.LinkedList;
import java.util.Random;

import Estruturas.*;

public class Rainhas {

	public Coluna[] tabuleiro = new Coluna[8];
	public LinkedList<Ataque> ataques = new LinkedList<>();
	public int custoHeuristicoAtual;
	public LinkedList<int[]> movimentos = new LinkedList<>();

	Random rand = new Random();

	// Gera um estado inicial
	public void inicializarTabuleiro() {

		Coluna c0 = new Coluna(rand.nextInt(8));
		Coluna c1 = new Coluna(rand.nextInt(8));
		Coluna c2 = new Coluna(rand.nextInt(8));
		Coluna c3 = new Coluna(rand.nextInt(8));
		Coluna c4 = new Coluna(rand.nextInt(8));
		Coluna c5 = new Coluna(rand.nextInt(8));
		Coluna c6 = new Coluna(rand.nextInt(8));
		Coluna c7 = new Coluna(rand.nextInt(8));

		c0.rainha = new Rainha("R0", 0);
		c1.rainha = new Rainha("R1", 1);
		c2.rainha = new Rainha("R2", 2);
		c3.rainha = new Rainha("R3", 3);
		c4.rainha = new Rainha("R4", 4);
		c5.rainha = new Rainha("R5", 5);
		c6.rainha = new Rainha("R6", 6);
		c7.rainha = new Rainha("R7", 7);

		tabuleiro[0] = c0;
		tabuleiro[1] = c1;
		tabuleiro[2] = c2;
		tabuleiro[3] = c3;
		tabuleiro[4] = c4;
		tabuleiro[5] = c5;
		tabuleiro[6] = c6;
		tabuleiro[7] = c7;

		calculaCustoHeuristico(tabuleiro);

	}

	public void mostrarTabuleiro(Coluna[] tabuleiro) {
		
		System.out.println("------------------------------------------------------------");
		for (int i = 0; i < tabuleiro.length; i++) {
			for (int j = 0; j < tabuleiro.length; j++) {
				if (tabuleiro[j].posicaoRainha == i)
					System.out.print("[" + tabuleiro[j].rainha.nome + "]" + "\t");
				else
					System.out.print(tabuleiro[j].custoCelulas[i] + "\t");
			}
			System.out.println("\n");
		}
		System.out.println("------------------------------------------------------------");
		System.out.println("Ataques em linha: " + verificaAtaquesEmLinha(tabuleiro));
		System.out.println("Ataques em diagonal (1): " + verificaAtaquesEmDiagonal1(tabuleiro));
		System.out.println("Ataques em diagonal (2): " + verificaAtaquesEmDiagonal2(tabuleiro));
	}

	public int calculaCustoAtual(Coluna[] tabuleiro) {
		return verificaAtaquesEmDiagonal1(tabuleiro) + verificaAtaquesEmDiagonal2(tabuleiro)
				+ verificaAtaquesEmLinha(tabuleiro);
	}

	public int verificaAtaquesEmLinha(Coluna[] tabuleiro) {

		int qtdAtaques = 0;

		for (int i = 0; i < tabuleiro.length; i++) {
			for (int j = 0; j < tabuleiro.length; j++) {
				if (tabuleiro[j].posicaoRainha == tabuleiro[i].posicaoRainha && !(tabuleiro[j].equals(tabuleiro[i]))) {

					Ataque ataque1 = new Ataque(tabuleiro[i].rainha, tabuleiro[j].rainha);
					Ataque ataque2 = new Ataque(tabuleiro[j].rainha, tabuleiro[i].rainha);

					if (!(ataqueJaExiste(ataque1) || ataqueJaExiste(ataque2))) {
						ataques.add(ataque1);
						ataques.add(ataque2);
						qtdAtaques++;
					}
				}
			}
		}
		ataques.clear();
		this.custoHeuristicoAtual = qtdAtaques;
		return qtdAtaques;
	}

	public int verificaAtaquesEmDiagonal1(Coluna[] tabuleiro) {

		int qtdAtaques = 0;

		for (int i = 0; i < tabuleiro.length; i++) {
			int index = tabuleiro[i].posicaoRainha - i;

			if (index >= 0) {
				for (int j = 0; j < tabuleiro.length; j++) {
					if (tabuleiro[j].posicaoRainha == Math.abs(index) + j && !(tabuleiro[i].equals(tabuleiro[j]))) {

						Ataque ataque1 = new Ataque(tabuleiro[i].rainha, tabuleiro[j].rainha);
						Ataque ataque2 = new Ataque(tabuleiro[j].rainha, tabuleiro[i].rainha);

						if (!(ataqueJaExiste(ataque1) || ataqueJaExiste(ataque2))) {
							qtdAtaques++;
							ataques.add(ataque1);
							ataques.add(ataque2);
						}
					}
				}
			} else {
				int pos = 0;
				for (int j = Math.abs(index); j < tabuleiro.length; j++) {
					if (tabuleiro[j].posicaoRainha == (pos) && !(tabuleiro[i].equals(tabuleiro[j]))) {

						Ataque ataque1 = new Ataque(tabuleiro[i].rainha, tabuleiro[j].rainha);
						Ataque ataque2 = new Ataque(tabuleiro[j].rainha, tabuleiro[i].rainha);

						if (!(ataqueJaExiste(ataque1) || ataqueJaExiste(ataque2))) {
							qtdAtaques++;
							ataques.add(ataque1);
							ataques.add(ataque2);
						}
					}
					pos++;
				}
			}
		}
		ataques.clear();
		this.custoHeuristicoAtual = qtdAtaques;
		return qtdAtaques;
	}

	public int verificaAtaquesEmDiagonal2(Coluna[] tabuleiro) {

		int qtdAtaques = 0;

		for (int i = 0; i < tabuleiro.length; i++) {
			int index = tabuleiro[i].posicaoRainha + i;

			if (index <= 7) {
				for (int j = 0; j < tabuleiro.length; j++) {
					if (tabuleiro[j].posicaoRainha == (index - j) && !(tabuleiro[i].equals(tabuleiro[j]))) {

						Ataque ataque1 = new Ataque(tabuleiro[i].rainha, tabuleiro[j].rainha);
						Ataque ataque2 = new Ataque(tabuleiro[j].rainha, tabuleiro[i].rainha);

						if (!(ataqueJaExiste(ataque1) || ataqueJaExiste(ataque2))) {
							qtdAtaques++;
							ataques.add(ataque1);
							ataques.add(ataque2);
						}
					}
				}
			} else {
				index = (tabuleiro[i].posicaoRainha + i) - 7;
				int pos = 0;
				for (int j = index; j < tabuleiro.length; j++) {
					if (tabuleiro[j].posicaoRainha == (7 - pos++) && !(tabuleiro[i].equals(tabuleiro[j]))) {

						Ataque ataque1 = new Ataque(tabuleiro[i].rainha, tabuleiro[j].rainha);
						Ataque ataque2 = new Ataque(tabuleiro[j].rainha, tabuleiro[i].rainha);

						if (!(ataqueJaExiste(ataque1) || ataqueJaExiste(ataque2))) {
							qtdAtaques++;
							ataques.add(ataque1);
							ataques.add(ataque2);
						}
					}
				}
			}
		}
		ataques.clear();
		this.custoHeuristicoAtual = qtdAtaques;
		return qtdAtaques;
	}

	// Mesma ideia apresentada no livro
	public void calculaCustoHeuristico(Coluna[] tabuleiro) {

		int vHeuristico;
		int posRainha;
		Coluna[] tabuleiroAux = tabuleiro.clone();

		for (int i = 0; i < tabuleiroAux.length; i++) {
			posRainha = tabuleiroAux[i].posicaoRainha;
			for (int j = 0; j < tabuleiroAux.length; j++) {
				tabuleiroAux[i].posicaoRainha = j;
				vHeuristico = verificaAtaquesEmDiagonal1(tabuleiroAux) + verificaAtaquesEmDiagonal2(tabuleiroAux)
						+ verificaAtaquesEmLinha(tabuleiroAux);
				tabuleiro[i].custoCelulas[j] = vHeuristico;

			}
			tabuleiroAux[i].posicaoRainha = posRainha;
		}
	}

	/* Para subida de encosta */
	public Coluna[] funcaoSucessora(Coluna[] Estado) {

		Coluna[] proximo = Estado;

		int posicaoMenor = 0;
		int colunaMenor = 0;

		int menor = Estado[colunaMenor].custoCelulas[posicaoMenor];

		for (int i = 0; i < Estado.length; i++) {
			for (int j = 0; j < Estado.length; j++) {

				if (Estado[i].custoCelulas[j] < menor) {
					menor = Estado[i].custoCelulas[j];
					posicaoMenor = j;
					colunaMenor = i;
				}
			}
		}
		movimentos.add(new int[]{colunaMenor, posicaoMenor});
		proximo[colunaMenor].posicaoRainha = posicaoMenor;
		return proximo;
	}
	
	public Coluna[] movimentoAletorio (Coluna[] estado) {
		
		Coluna[] proximo = deepCopy(estado);
		
		int coluna = rand.nextInt(8);
		int posRainha = rand.nextInt(8);
		
		proximo[coluna].posicaoRainha = posRainha;
		
		return proximo;
		
	}
	
	/* Para copiar tabuleiro sem referência*/
	private Coluna[] deepCopy(Coluna[] tabuleiro) {

		Coluna[] temp = new Coluna[tabuleiro.length];

		for (int i = 0; i < tabuleiro.length; i++) {
			temp[i] = new Coluna(tabuleiro[i].posicaoRainha);
			temp[i].rainha = new Rainha(tabuleiro[i].rainha.nome, tabuleiro[i].rainha.id);
			temp[i].custoCelulas = tabuleiro[i].custoCelulas.clone();
		}
		return temp;
	}

	public boolean ataqueJaExiste(Ataque ataque) {

		for (int i = 0; i < ataques.size(); i++)
			if (ataque.rainha1.equals(ataques.get(i).rainha1) && ataque.rainha2.equals(ataques.get(i).rainha2))
				return true;
		return false;
	}

	public static void main(String[] args) {

		//Coluna[] col1 = new Coluna();

	}

}
