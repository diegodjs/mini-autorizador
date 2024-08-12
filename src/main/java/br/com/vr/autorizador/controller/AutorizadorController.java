package br.com.vr.autorizador.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vr.autorizador.dto.Cartao;
import br.com.vr.autorizador.dto.CartaoNovo;
import br.com.vr.autorizador.service.AutorizadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

/**
 * AutorizadorController - Classe de controle com exposicao dos servicos
 * @author Diego
 */
@Slf4j
@RestController
@RequestMapping("/")
public class AutorizadorController {

	@Autowired
	private AutorizadorService autorizadorService;
	
	/**
	 * Criar novo cartao
	 * @param CartaoNovo
	 * @return ResponseEntity com informacao do cartao
	 * @throws Exception
	 */
	@PostMapping("/cartoes")
	@Operation(
			description = "Criar novo cartão", 
			responses = {
					@ApiResponse(responseCode = "201", description = "Criado com sucesso"),
					@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
					@ApiResponse(responseCode = "422", description = "Já existente"),
					@ApiResponse(responseCode = "500", description = "Erro interno")
			}

		)
	public ResponseEntity<?> criarNovoCartao(@Valid @RequestBody CartaoNovo cartao) throws Exception {
		try {
			log.info("Controller - criarNovoCartao");
			return autorizadorService.isCartaoExistente(cartao.getNumeroCartao()) ? 
					new ResponseEntity<CartaoNovo>(cartao, HttpStatus.UNPROCESSABLE_ENTITY) : 
						autorizadorService.criarNovoCartao(cartao);
		}catch (Exception e) {
			return ResponseEntity.internalServerError().body("Error Message = " + e.getMessage());
		}
	}
	
	/**
	 * Obter saldo do Cartao
	 * @param numeroCartao
	 * @return ResponseEntity com o saldo
	 * @throws Exception
	 */
	@GetMapping("/cartoes/{numeroCartao}")
	@Operation(
			description = "Obter saldo do Cartão", 
			responses = {
					@ApiResponse(responseCode = "200", description = "Sucesso na consulta"),
					@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
					@ApiResponse(responseCode = "404", description = "Inexistente"),
					@ApiResponse(responseCode = "500", description = "Erro interno")
			}

		)
	public ResponseEntity<?> obterSaldoCartao(@Valid @NotNull @PathVariable String numeroCartao) throws Exception {
		try {
			log.info("Controller - obterSaldoCartao");
			return autorizadorService.isCartaoExistente(numeroCartao) ? 
					autorizadorService.obterSaldoCartao(numeroCartao) : 
						new ResponseEntity<CartaoNovo>(HttpStatus.NOT_FOUND);
		}catch (Exception e) {
			return ResponseEntity.internalServerError().body("Error Message = "+e.getMessage());
		}
	}
	
	/**
	 * Realizar uma Transacao
	 * @param Cartao
	 * @return ResponseEntity com resposta da transacao
	 * @throws Exception
	 */
	@PostMapping("/transacoes")
	@Operation(
			description = "Realizar uma Transação", 
			responses = {
					@ApiResponse(responseCode = "201", description = "Realizada com sucesso"),
					@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
					@ApiResponse(responseCode = "422", description = "Erro de autorização"),
					@ApiResponse(responseCode = "500", description = "Erro interno")
			}

		)
	public ResponseEntity<?> realizarTransacao(@Valid @RequestBody Cartao cartao) throws Exception {
		try {
			log.info("Controller - realizarTransacao");
			return autorizadorService.realizarTransacao(cartao) ;
		}catch (Exception e) {
			return ResponseEntity.internalServerError().body("Error Message = "+e.getMessage());
		}
	}
}
