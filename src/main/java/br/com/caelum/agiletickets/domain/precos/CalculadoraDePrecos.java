package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	private static final double CINCOPORCENTO = 0.05d;
	private static final double CINQUENTAPORCENTO = 0.5d;
	private static final double DEZPORCENTO = 0.1d;
	private static final double VINTEPORCENTO = 0.2d;
	private static final int UMAHORA = 60;

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		
		BigDecimal preco = sessao.getPreco();
		BigDecimal precoInicial = sessao.getPreco();

		double porcentagemDeIngressosRestante = (sessao.getTotalIngressos() - sessao.getIngressosReservados()) / (sessao.getTotalIngressos().doubleValue());
		TipoDeEspetaculo tipoDeEspetcaulo = sessao.getEspetaculo().getTipo();

		if (tipoDeEspetcaulo.equals(TipoDeEspetaculo.CINEMA)
				|| tipoDeEspetcaulo.equals(TipoDeEspetaculo.SHOW)) {
			// quando estiver acabando os ingressos...
			if (porcentagemDeIngressosRestante  <= CINCOPORCENTO) {
				preco = (aumentaPreco(precoInicial, DEZPORCENTO));
			} 
			
		} else if (tipoDeEspetcaulo.equals(TipoDeEspetaculo.BALLET)
				|| tipoDeEspetcaulo.equals(TipoDeEspetaculo.ORQUESTRA)) {
			
			if (porcentagemDeIngressosRestante <= CINQUENTAPORCENTO) {
				preco = (aumentaPreco(precoInicial, VINTEPORCENTO));
			}

			if (sessao.getDuracaoEmMinutos() > UMAHORA) {
				preco = preco.add(precoInicial.multiply(BigDecimal.valueOf(DEZPORCENTO)));
			}
		}

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}
	
	private static BigDecimal aumentaPreco(BigDecimal preco, double porcentagem){
		return preco.add(preco.multiply(BigDecimal.valueOf(porcentagem)));
		
	}

}