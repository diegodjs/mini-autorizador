package br.com.vr.autorizador.regra;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import br.com.vr.autorizador.dto.Cartao;

/**
 * Regra Senha Invalida
 * @author Diego
 */
@Component
public class RegraSenhaInvalida implements Regra<Cartao, Exception> {

	@Override
	public void validar(Cartao cartao) throws Exception {
		Assert.isTrue(cartao.isSenhaValida(), RegraEnum.SENHA_INVALIDA.toString());
	}
}
