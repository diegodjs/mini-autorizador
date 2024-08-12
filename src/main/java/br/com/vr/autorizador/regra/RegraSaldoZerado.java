package br.com.vr.autorizador.regra;

import org.springframework.util.Assert;

import br.com.vr.autorizador.dto.Cartao;

/**
 * Regra Saldo Zerado
 * @author Diego
 */
public class RegraSaldoZerado implements Regra<Cartao, Exception> {

	@Override
	public void validar(Cartao cartao) throws Exception {
		Assert.isTrue(cartao.getSaldo() > 0, RegraEnum.SALDO_INSUFICIENTE.toString());
	}

}
