package Estruturas;

public class Coluna {
	
	public Rainha rainha;
	public int posicaoRainha;
	public int[] custoCelulas = new int[8];
	
	public Coluna(int posicaoRainha) {
		super();
		this.posicaoRainha = posicaoRainha;
	}	
}