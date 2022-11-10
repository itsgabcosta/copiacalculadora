package br.com.gabs.calc.visao;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import br.com.gabs.calc.modelo.Memoria;

@SuppressWarnings("serial")
public class Teclado extends JPanel implements ActionListener{
	
	private final Color COR_CINZA = new Color(60, 60, 60);
	private final Color COR_AZUL_CLARO = new Color(173, 171, 210);
	
	public Teclado() {
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints grades = new GridBagConstraints();
		
		setLayout(layout);
		
		grades.weightx = 1;
		grades.weighty = 1;
		grades.fill = GridBagConstraints.BOTH;
		
		//primeira linha
		adicionarBotao("%", COR_CINZA, grades, 0, 0);
		adicionarBotao("CE", COR_CINZA, grades, 1, 0);
		adicionarBotao("C", COR_CINZA, grades, 2, 0);
		adicionarBotao("<=", COR_CINZA, grades, 3, 0);
		
		//segunda linha
		adicionarBotao("1/x", COR_CINZA, grades, 0, 1);
		adicionarBotao("x²", COR_CINZA, grades, 1, 1);
		adicionarBotao("²/x", COR_CINZA, grades, 2, 1);
		adicionarBotao("/", COR_CINZA, grades, 3, 1);
		
		//terceira linha
		adicionarBotao("7", COR_CINZA, grades, 0, 2);
		adicionarBotao("8", COR_CINZA, grades, 1, 2);
		adicionarBotao("9", COR_CINZA, grades, 2, 2);
		adicionarBotao("X", COR_CINZA, grades, 3, 2);
		
		//quarta linha
		adicionarBotao("4", COR_CINZA, grades, 0, 3);
		adicionarBotao("5", COR_CINZA, grades, 1, 3);
		adicionarBotao("6", COR_CINZA, grades, 2, 3);
		adicionarBotao("-", COR_CINZA, grades, 3, 3);
		
		//quinta linha
		adicionarBotao("1", COR_CINZA, grades, 0, 4);
		adicionarBotao("2", COR_CINZA, grades, 1, 4);
		adicionarBotao("3", COR_CINZA, grades, 2, 4);
		adicionarBotao("+", COR_CINZA, grades, 3, 4);
		
		//sexta linha
		adicionarBotao("+/-", COR_CINZA, grades, 0, 5);
		adicionarBotao("0", COR_CINZA, grades, 1, 5);
		adicionarBotao(".", COR_CINZA, grades, 2, 5);
		adicionarBotao("=", COR_AZUL_CLARO, grades, 3, 5);
		
	}

	private void adicionarBotao(String texto, Color cor, GridBagConstraints grades, int x, int y) {
		grades.gridx = x;
		grades.gridy = y;
		Botao botao = new Botao(texto, cor);
		botao.addActionListener(this);
		add(botao, grades);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent evento) {
		if(evento.getSource() instanceof JButton) {
		JButton botao = (JButton) evento.getSource();
		Memoria.getInstancia().processarComando(botao.getText());
		}
	}
}
