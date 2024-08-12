package br.com.vr.autorizador.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Cartao -  Classe com informacoes de Cartao
 * @author Diego
 */
@Setter
@Getter
public class Cartao {
	
	@NotNull(message = "numeroCartao não pode ser nulo")
	@NotEmpty(message = "numeroCartao não pode ser vazio")
	String numeroCartao;
	
	@NotNull(message = "senha não pode ser nulo")
	@NotEmpty(message = "senha não pode ser vazio")
	String senhaCartao;
	
	Double saldo;
	
	Double valor;
	
	boolean isCartaoExistente;
	
	boolean isSenhaValida;
}
