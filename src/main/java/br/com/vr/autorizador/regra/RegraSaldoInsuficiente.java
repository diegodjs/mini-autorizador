package br.com.vr.autorizador.regra;

import org.springframework.util.Assert;

import br.com.vr.autorizador.dto.Cartao;

/**
 * Regra Saldo Insuficiente
 * @author Diego
 */
public class RegraSaldoInsuficiente implements Regra<Cartao, Exception> {

	@Override
	public void validar(Cartao cartao) throws Exception {
		Assert.isTrue( cartao.getSaldo() >= cartao.getValor(), RegraEnum.SALDO_INSUFICIENTE.toString());
	}
}
