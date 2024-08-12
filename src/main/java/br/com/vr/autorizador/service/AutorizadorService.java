package br.com.vr.autorizador.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.vr.autorizador.dao.CartaoDAO;
import br.com.vr.autorizador.dto.Cartao;
import br.com.vr.autorizador.dto.CartaoNovo;
import br.com.vr.autorizador.regra.Regra;
import br.com.vr.autorizador.regra.RegraCartaoInexistente;
import br.com.vr.autorizador.regra.RegraEnum;
import br.com.vr.autorizador.regra.RegraSaldoInsuficiente;
import br.com.vr.autorizador.regra.RegraSaldoZerado;
import br.com.vr.autorizador.regra.RegraSenhaInvalida;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
 * AutorizadorService - Classe de servico
 * @author Diego
 */
@Slf4j
@Service
public class AutorizadorService {
	
	@Autowired
	private CartaoDAO cartaoDAO;
	
	@Autowired
	EntityManager _em;
	
	/**
	 * Verifica se Cartao Existente
	 * @param cartao
	 * @return boolean se cartao existe
	 * @throws Exception
	 */
	public boolean isCartaoExistente(String numeroCartao) throws Exception {
		
		log.info("Service - isCartaoExistente : " + numeroCartao);
		return cartaoDAO.verificaExistenciaCartao(numeroCartao);
	}
	
	/**
	 * Cria Novo Cartao
	 * @param CartaoNovo
	 * @return ResponseEntity com informacoes de novo Cartao
	 * @throws Exception
	 */
	public ResponseEntity<CartaoNovo> criarNovoCartao(CartaoNovo cartaoNovo) throws Exception {

		log.info("Service - criarNovoCartao: " + cartaoNovo.getNumeroCartao());
		cartaoDAO.criaNovoCartao(cartaoNovo);
		log.info("Service - criarNovoCartao - sucesso na criação cartão: " + cartaoNovo.getNumeroCartao());

		return new ResponseEntity<CartaoNovo>(cartaoNovo, HttpStatus.CREATED);
	}
	
	/**
	 * Obtem Saldo Cartao
	 * @param numeroCartao 
	 * @return ResponseEntity com Saldo do Cartao
	 * @throws Exception
	 */
	public ResponseEntity<Double> obterSaldoCartao(String numeroCartao) throws Exception {
		
		log.info("Service - obterSaldoCartao: " + numeroCartao);
		return new ResponseEntity<Double>(cartaoDAO.buscaSaldoCartao(numeroCartao), HttpStatus.OK);
	}
	
	/**
	 * Realiza Transacao
	 * @param Cartao
	 * @return ResponseEntity com resultado da transacao
	 * @throws Exception
	 */
	@Transactional
	public ResponseEntity<String> realizarTransacao(Cartao cartao) throws Exception {
		
		log.info("Service - realizarTransacao: " + cartao.getNumeroCartao());
		
		// inclusao das regras para verificacao
		List<Regra<Cartao, Exception>> regras = Arrays.asList(
			new RegraCartaoInexistente(),
			new RegraSenhaInvalida(),
			new RegraSaldoZerado(), 
			new RegraSaldoInsuficiente()
		);
		log.info("Service - realizarTransacao - regras criadas");
		
		// verifica se o cartao existe
		cartao.setCartaoExistente(cartaoDAO.verificaExistenciaCartao(cartao.getNumeroCartao()));
		log.info("Service - realizarTransacao - verificado Existencia do Cartao");
		
		// verifica se a senha eh valida
		cartao.setSenhaValida(cartaoDAO.obterSenhaCartao(cartao));
		log.info("Service - realizarTransacao - verificado Senha Valida");
		
		// obtem o saldo do cartao
		Double saldo = cartaoDAO.buscaSaldoCartao(cartao.getNumeroCartao());
		cartao.setSaldo(saldo);
		log.info("Service - realizarTransacao - busca de saldo realizada");
		
		// realiza verificacao das regras propostas
		for (Regra<Cartao, Exception> regra : regras) {
			try {
				regra.validar(cartao);
			} catch (Exception e) {
				// se alguma regra barrar a transacao retorna o erro
				log.info("Não autorizado pela regra: " + e.getMessage());
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
			}
		}
		log.info("Service - realizarTransacao - regras validadas");
		
		// realizando o calculo para reducao do saldo
		cartao.setSaldo(saldo - cartao.getValor());
		
		// atualizando o saldo do cartao
		cartaoDAO.atualizaSaldoCartao(cartao);
		log.info("Service - realizarTransacao - atualizado novo saldo");

		return new ResponseEntity<String>(RegraEnum.OK.toString(), HttpStatus.CREATED);
	}
}
