package br.com.vr.autorizador.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * CartaoNovo - Classe com informacoes de Cartao Novo
 * @author Diego
 */
@Setter
@Getter
public class CartaoNovo {
	
	@NotNull(message = "numeroCartao não pode ser nulo")
	@NotEmpty(message = "numeroCartao não pode ser vazio")
	String numeroCartao;
	
	@NotNull(message = "senha não pode ser nulo")
	@NotEmpty(message = "senha não pode ser vazio")
	String senha;
	
	public static Double SALDO_INICIAL = 500.0d;
}
