package br.com.vr.autorizador.regra;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import br.com.vr.autorizador.dto.Cartao;

/**
 * Regra Cartao Inexistente
 * @author Diego
 */
@Component
public class RegraCartaoInexistente implements Regra<Cartao, Exception> {

	@Override
	public void validar(Cartao cartao) throws Exception {
		Assert.isTrue(cartao.isCartaoExistente(), RegraEnum.CARTAO_INEXISTENTE.toString());
	}
}
