package br.com.vr.autorizador.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.vr.autorizador.config.db.DbConfig;
import br.com.vr.autorizador.dto.Cartao;
import br.com.vr.autorizador.dto.CartaoNovo;
import br.com.vr.autorizador.exception.DatabaseException;
import lombok.extern.slf4j.Slf4j;

/**
 * CartaoDAO - Classe responsavel pelo acesso ao banco de dados
 * @author Diego
 */
@Slf4j
@Component
public class CartaoDAO {

	private DataSource dataSource;

	@Autowired
	private DbConfig dbConfig;

	public CartaoDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * cria Novo Cartao
	 * @param CartaoNovo
	 * @return boolean se o cartao foi criado
	 * @throws SQLException
	 */
    public boolean criaNovoCartao(CartaoNovo cartao) throws SQLException {
		
		log.info("CartaoDAO - criaNovoCartao cartao: " + cartao.getNumeroCartao());
		
		String query = "insert into cartao (NUMERO_CARTAO, SENHA, SALDO) values(?,?,?)";
		
		Connection con = null;
		PreparedStatement pst = null;
		
		try {
			
			dataSource = dbConfig.springDataSource();
			
			con = dataSource.getConnection();
			pst = con.prepareStatement(query);
			
			pst.setObject(1, cartao.getNumeroCartao());
			pst.setObject(2, cartao.getSenha());
			pst.setObject(3, CartaoNovo.SALDO_INICIAL);
			
			pst.executeUpdate();
			
			return true;
			
		} catch (SQLException e) {
			log.error("CartaoDAO - Erro criaNovoCartao: " + e.getMessage());
			throw new DatabaseException("Erro ao Criar Novo Cartao:", e);
			
		} finally {
			pst.close();
			con.close();
		}
	}
    
    /**
	 * verifica Existencia Cartao
	 * @param numeroCartao
	 * @return boolean se cartao existente
	 * @throws SQLException
	 */
	public boolean verificaExistenciaCartao(String numeroCartao) throws SQLException {

		log.info("CartaoDAO - verificaExistenciaCartao : " + numeroCartao);

		String query = "select * from cartao where NUMERO_CARTAO = ?";

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			dataSource = dbConfig.springDataSource();
			
			con = dataSource.getConnection();
			pst = con.prepareStatement(query);

			pst.setObject(1, numeroCartao);

			rs = pst.executeQuery();

			return rs.next();

		} catch (SQLException e) {
			log.error("CartaoDAO - Erro verificaExistenciaCartao: " + e.getMessage());
			throw new DatabaseException("Erro ao Verificar Existencia do Cartao:", e);
		} finally {
			pst.close();
			con.close();
			rs.close();
		}
	}
	
	/**
	 * Busca Saldo Cartao
	 * @param numeroCartao
	 * @return Double com saldo do cartao
	 * @throws SQLException
	 */
	public Double buscaSaldoCartao(String numeroCartao) throws SQLException {

		log.info("CartaoDAO - buscaSaldoCartao : " + numeroCartao);

		String query = "select SALDO from cartao where NUMERO_CARTAO = ?";

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			dataSource = dbConfig.springDataSource();
			
			con = dataSource.getConnection();
			pst = con.prepareStatement(query);

			pst.setObject(1, numeroCartao);

			rs = pst.executeQuery();

			// operador ternario para validar se resultou algo na consulta
			// se tem registro retorna o valor senao retorna 0.0
			return rs.next() == true ?  rs.getDouble(1) : 0d;
			
		} catch (SQLException e) {
			log.error("CartaoDAO - Erro buscaSaldoCartao: " + e.getMessage());
			throw new DatabaseException("Erro ao buscar Saldo do Cartao:", e);
		} finally {
			pst.close();
			con.close();
			rs.close();
		}
	}
	
	
	/**
	 * obter Senha Cartao
	 * @param Cartao
	 * @return boolean se senha encontrada
	 * @throws SQLException 
	 */
	public boolean obterSenhaCartao(Cartao cartao) throws SQLException {

		log.info("CartaoDAO - obterSenhaCartao : " + cartao.getNumeroCartao());

		String query = "select * from cartao where NUMERO_CARTAO = ? and SENHA = ?";

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			dataSource = dbConfig.springDataSource();
			
			con = dataSource.getConnection();
			pst = con.prepareStatement(query);

			pst.setObject(1, cartao.getNumeroCartao());
			pst.setObject(2, cartao.getSenhaCartao());

			rs = pst.executeQuery();

			return rs.next();

		} catch (SQLException e) {
			log.error("CartaoDAO - Erro obterSenhaCartao: " + e.getMessage());
			throw new DatabaseException("Erro ao buscar Senha do Cartao", e);
		} finally {
			pst.close();
			con.close();
			rs.close();
		}
	}
	
	/**
	 * atualiza Saldo Cartao
	 * @param Cartao
	 * @return boolean atualizacao efetuada
	 * @throws SQLException 
	 */
	public boolean atualizaSaldoCartao(Cartao cartao) throws SQLException {
		
		log.info("CartaoDAO - atualizaSaldoCartao cartao: " + cartao.getNumeroCartao());
		
		String query = "update cartao set SALDO = ? where NUMERO_CARTAO = ?";
		
		Connection con = null;
		PreparedStatement pst = null;
		
		try {
			
			dataSource = dbConfig.springDataSource();
			
			con = dataSource.getConnection();
			pst = con.prepareStatement(query);
			
			pst.setObject(1, cartao.getSaldo());
			pst.setObject(2, cartao.getNumeroCartao());
			
			pst.executeUpdate();
			
		} catch (SQLException e) {
			log.error("CartaoDAO - Erro atualizaSaldoCartao: " + e.getMessage());
			return false;
		} finally {
			pst.close();
			con.close();
		}
		return true;
	}

}
