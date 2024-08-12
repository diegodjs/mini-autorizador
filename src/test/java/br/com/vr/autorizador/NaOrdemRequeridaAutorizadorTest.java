package br.com.vr.autorizador;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.vr.autorizador.dto.Cartao;
import br.com.vr.autorizador.dto.CartaoNovo;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NaOrdemRequeridaAutorizadorTest {

    @Autowired
	private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
   	@Test
	public void criarCartaoNovoTest() throws Exception {
		
		CartaoNovo cartaoNovo = new CartaoNovo();
		cartaoNovo.setNumeroCartao("6549873025634501");
		cartaoNovo.setSenha("1234");
		
		MvcResult result = mockMvc
				.perform(post("/cartoes")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Basic dXNlcm5hbWU6cGFzc3dvcmQ=")
						.content(objectMapper.writeValueAsString(cartaoNovo)))
	            .andExpect(status().isCreated()).andReturn();
		
		System.out.println("Status Code: " + result.getResponse().getStatus());
		System.out.println("Body (json): ");
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void obterSaldoCartaoTest() throws Exception {
		
		MvcResult result = mockMvc
				.perform(get("/cartoes/"+"6549873025634501")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Basic dXNlcm5hbWU6cGFzc3dvcmQ="))
				.andExpect(status().isOk()).andReturn();
		
		System.out.println("Status Code: " + result.getResponse().getStatus());
		System.out.println("Body: " + result.getResponse().getContentAsString());
	}
	
	@Test
	public void realizarTransacaoTest() throws Exception {
		
		Cartao cartao = new Cartao();
		cartao.setNumeroCartao("6549873025634501");
		cartao.setSenhaCartao("1234");
		cartao.setValor(10.00);
		
		MvcResult result = mockMvc
				.perform(post("/transacoes")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Basic dXNlcm5hbWU6cGFzc3dvcmQ=")
						.content(objectMapper.writeValueAsString(cartao)))
	            .andExpect(status().isCreated()).andReturn();
		
		System.out.println("Status Code: " + result.getResponse().getStatus());
		System.out.println("Body: " + result.getResponse().getContentAsString());
	}
	
	@Test
	public void realizarTransacaoSaldoInsuficienteTest() throws Exception {
		
		Cartao cartao = new Cartao();
		cartao.setNumeroCartao("6549873025634501");
		cartao.setSenhaCartao("1234");
		cartao.setValor(600.00);
		
		MvcResult result = mockMvc
				.perform(post("/transacoes")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Basic dXNlcm5hbWU6cGFzc3dvcmQ=")
						.content(objectMapper.writeValueAsString(cartao)))
				 .andExpect(status().isUnprocessableEntity()).andReturn();
		
		System.out.println("Status Code: " + result.getResponse().getStatus());
		System.out.println("Body: " + result.getResponse().getContentAsString());
	}
	
	@Test
	public void realizarTransacaoSenhaInvalidaTest() throws Exception {
		
		Cartao cartao = new Cartao();
		cartao.setNumeroCartao("6549873025634501");
		cartao.setSenhaCartao("1111");
		cartao.setValor(10.00);
		
		MvcResult result = mockMvc
				.perform(post("/transacoes")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Basic dXNlcm5hbWU6cGFzc3dvcmQ=")
						.content(objectMapper.writeValueAsString(cartao)))
	            .andExpect(status().isUnprocessableEntity()).andReturn();
		
		System.out.println("Status Code: " + result.getResponse().getStatus());
		System.out.println("Body: " + result.getResponse().getContentAsString());
	}
	
	@Test
	public void realizarTransacaoCartaoInexistenteTest() throws Exception {
		
		Cartao cartao = new Cartao();
		cartao.setNumeroCartao("1111111111111111");
		cartao.setSenhaCartao("1234");
		cartao.setValor(10.00);
		
		MvcResult result = mockMvc
				.perform(post("/transacoes")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Basic dXNlcm5hbWU6cGFzc3dvcmQ=")
						.content(objectMapper.writeValueAsString(cartao)))
	            .andExpect(status().isUnprocessableEntity()).andReturn();
		
		System.out.println("Status Code: " + result.getResponse().getStatus());
		System.out.println("Body: " + result.getResponse().getContentAsString());
	}
}
