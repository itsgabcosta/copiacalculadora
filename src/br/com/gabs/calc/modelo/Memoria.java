package br.com.gabs.calc.modelo;

import java.util.ArrayList;
import java.util.List;

public class Memoria {
	
	private enum TipoComando {
		PORCENTAGEM, CANCELARREGISTRO, LIMPAR, APAGAR, UMSOBREX, XELEVADOADOIS,
		DOISRAIZQUADRADADEX, DIVISAO, MULTIPLICAR, SUBTRACAO, SOMA, 
		POSITIVOENEGATIVO, PONTO, IGUAL, NUMEROS;
	};
	
	private static final Memoria instancia = new Memoria();
	private final List<MemoriaObservador> observadores = new ArrayList<>();
	public static Memoria getInstancia() {
		return instancia;
	}
	
	private TipoComando ultimaOperacao = null;
	private boolean substituir = false;
	private String textoAtual = "";
	private String textoBuffer = "";
	
	public String getTextoAtual() {
		return textoAtual.isEmpty() ? "0" : textoAtual;
	}


	private Memoria(){
		
	}
	
	public void adicionarObservador(MemoriaObservador observador) {
		observadores.add(observador);
	}
	
	public void processarComando(String comando) {
		
		TipoComando tipoComando = detectarTipoComando(comando);
		
		if(tipoComando == null) {
			return;
		}else if(tipoComando == TipoComando.CANCELARREGISTRO
				|| tipoComando == TipoComando.APAGAR
				|| tipoComando == TipoComando.LIMPAR) {
			textoAtual = "";
			textoBuffer = "";
			substituir = false;
			ultimaOperacao = null;
		}else if(tipoComando == TipoComando.POSITIVOENEGATIVO && comando.contains("-")) {
			textoAtual = "-" + textoAtual.substring(1);
		}else if(tipoComando == TipoComando.POSITIVOENEGATIVO && !comando.contains("-")) {
			textoAtual = "-" + textoAtual;
		}else if(tipoComando == TipoComando.NUMEROS 
				|| tipoComando == TipoComando.PONTO) {
			textoAtual = substituir ? comando : textoAtual + comando;
			substituir = false;
		}else {
			substituir = true;
			textoAtual = obterResultadoTotal();
			textoBuffer = textoAtual;
			ultimaOperacao = tipoComando;
		}
		
		observadores.forEach(oDeObservador -> oDeObservador.valorAlterado(getTextoAtual()));
	}


	private String obterResultadoTotal() {
		if(ultimaOperacao == null || ultimaOperacao == TipoComando.IGUAL) {
			return textoAtual;
		}
		double numeroBuffer = 
				Double.parseDouble(textoBuffer.replace(".","."));
		double numeroAtual =
				Double.parseDouble(textoAtual.replace(".", "."));
		double resultado = 0;
		
		if(ultimaOperacao == TipoComando.PORCENTAGEM) {
			resultado = numeroBuffer % numeroAtual;
		}else if(ultimaOperacao == TipoComando.DIVISAO) {
			resultado = numeroBuffer / numeroAtual;
		}else if(ultimaOperacao == TipoComando.MULTIPLICAR) {
			resultado = numeroBuffer * numeroAtual;
		}else if(ultimaOperacao == TipoComando.SUBTRACAO) {
			resultado = numeroBuffer - numeroAtual;
		}else if(ultimaOperacao == TipoComando.SOMA) {
			resultado = numeroBuffer + numeroAtual;
		}else if(ultimaOperacao == TipoComando.XELEVADOADOIS) {
			resultado = numeroBuffer * numeroBuffer;
		}else if(ultimaOperacao == TipoComando.UMSOBREX) {
			resultado = 1 / numeroBuffer;
		}else if(ultimaOperacao == TipoComando.DOISRAIZQUADRADADEX) {
			resultado = Math.sqrt(numeroBuffer);
		}
		
		String texto = Double.toString(resultado)
				.replace(".", ".");
		boolean inteiro = texto.endsWith(",0");
		return inteiro ? texto.replace(",0", "") : texto;
	}


	private TipoComando detectarTipoComando(String comando) {
		if(textoAtual.isEmpty() && comando == "0") {
			return null;
		}
		
		try {
			Integer.parseInt(comando);
			return TipoComando.NUMEROS;
		} catch (NumberFormatException e) {
			if("%".equals(comando)) {
				return TipoComando.PORCENTAGEM;
			}else if("CE".equals(comando)) {
				return TipoComando.CANCELARREGISTRO;
			}else if("C".equals(comando)) {
				return TipoComando.LIMPAR;
			}else if("<=".equals(comando)) {
				return TipoComando.APAGAR;
			}else if("1/x".equals(comando)) {
				return TipoComando.UMSOBREX;
			}else if("x²".equals(comando)) {
				return TipoComando.XELEVADOADOIS;
			}else if("2/x".equals(comando)) {
				return TipoComando.DOISRAIZQUADRADADEX;
			}else if("/".equals(comando)) {
				return TipoComando.DIVISAO;
			}else if("X".equals(comando)) {
				return TipoComando.MULTIPLICAR;
			}else if("-".equals(comando)) {
				return TipoComando.SUBTRACAO;
			}else if("+".equals(comando)) {
				return TipoComando.SOMA;
			}else if("+/-".equals(comando)) {
				return TipoComando.POSITIVOENEGATIVO;
			}else if (".".equals(comando) && !textoAtual.contains(".")) {
				return TipoComando.PONTO;
			} else if("=".equals(comando)) {
				return TipoComando.IGUAL;
			} else {
				return null;
			}
		}
	}
}
