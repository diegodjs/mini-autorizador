package br.com.vr.autorizador;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import br.com.vr.autorizador.dao.CartaoDAO;
import br.com.vr.autorizador.dto.Cartao;
import br.com.vr.autorizador.dto.CartaoNovo;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BancoDadosAutorizadorTest {

    @Autowired
	private CartaoDAO cartaoDAO;
    
   	@Test
	public void criaNovoCartaoTest() throws Exception {
		
		CartaoNovo cartaoNovo = new CartaoNovo();
		cartaoNovo.setNumeroCartao("6549873025634501");
		cartaoNovo.setSenha("1234");
		
		boolean resultado = cartaoDAO.criaNovoCartao(cartaoNovo);
		System.out.println("criaNovoCartaoTest: " + resultado);
		Assert.isTrue(resultado, "Erro");
	}
   	
   	@Test
	public void verificaExistenciaCartaoTest() throws Exception {
		
		boolean resultado = cartaoDAO.verificaExistenciaCartao("6549873025634501");
		System.out.println("verificaExistenciaCartaoTest: " + resultado);
		Assert.isTrue(resultado, "Erro");
	}
   	
   	@Test
	public void buscaSaldoCartaoTest() throws Exception {
		
		Double resultado = cartaoDAO.buscaSaldoCartao("6549873025634501");
		System.out.println("buscaSaldoCartao: " + resultado);
		Assert.isTrue(resultado instanceof Double, "Erro");
	}
	
   	@Test
	public void obterSenhaCartaoTest() throws Exception {
   		
   		Cartao cartao = new Cartao();
		cartao.setNumeroCartao("6549873025634501");
		cartao.setSenhaCartao("1234");
		
		boolean resultado = cartaoDAO.obterSenhaCartao(cartao);
		System.out.println("obterSenhaCartao: " + resultado);
		Assert.isTrue(resultado, "Erro");
	}
   	
   	@Test
	public void atualizaSaldoCartaoTest() throws Exception {
   		
   		Cartao cartao = new Cartao();
		cartao.setNumeroCartao("6549873025634501");
		cartao.setSaldo(10.00);
		
		boolean resultado = cartaoDAO.atualizaSaldoCartao(cartao);
		System.out.println("atualizaSaldoCartao: " + resultado);
		Assert.isTrue(resultado, "Erro");
	}

}
