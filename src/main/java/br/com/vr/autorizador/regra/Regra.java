package br.com.vr.autorizador.regra;

public interface Regra<T, E extends Exception> {
	void validar(T cartao) throws E;
}