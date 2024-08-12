package br.com.vr.autorizador;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import br.com.vr.autorizador.dto.Cartao;
import br.com.vr.autorizador.dto.CartaoNovo;
import br.com.vr.autorizador.service.AutorizadorService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ServiceAutorizadorTest {

    @Autowired
	private AutorizadorService autorizadorService;
    
   	@Test
	public void isCartaoExistenteTest() throws Exception {
		
		boolean resultado = autorizadorService.isCartaoExistente("6549873025634501");
		System.out.println("isCartaoExistenteTest: " + resultado);
		Assert.isTrue(resultado, "Erro");
	}
   	
   	@Test
	public void criarNovoCartaoTest() throws Exception {
		
   		CartaoNovo cartaoNovo = new CartaoNovo();
   		cartaoNovo.setNumeroCartao("6549873025634501");
   		cartaoNovo.setSenha("1234");
   		
		ResponseEntity<CartaoNovo> resultado = autorizadorService.criarNovoCartao(cartaoNovo);
		System.out.println("criarNovoCartao: " + resultado);
		Assert.isTrue(resultado instanceof ResponseEntity<CartaoNovo>, "Erro");
	}
   	
   	@Test
	public void obterSaldoCartaoTest() throws Exception {
   		
		ResponseEntity<Double> resultado = autorizadorService.obterSaldoCartao("6549873025634501");
		System.out.println("obterSaldoCartao: " + resultado);
		Assert.isTrue(resultado instanceof ResponseEntity<Double>, "Erro");
	}
   	
   	@Test
	public void realizarTransacaoTest() throws Exception {
		
   		Cartao cartao = new Cartao();
   		cartao.setNumeroCartao("6549873025634501");
   		cartao.setSenhaCartao("1234");
   		cartao.setValor(10.00);
   		
		ResponseEntity<String> resultado = autorizadorService.realizarTransacao(cartao);
		System.out.println("realizarTransacao: " + resultado);
		Assert.isTrue(resultado instanceof ResponseEntity<String>, "Erro");
	}
}
