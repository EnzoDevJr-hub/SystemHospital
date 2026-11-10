package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modelo.Convenio;
import persistencia.ConexaoBanco;

public class ConvenioDAO {

    private ConexaoBanco conexao;
    private Connection con;

    public ConvenioDAO() {
        this.conexao = new ConexaoBanco();
    }

    public ArrayList<Convenio> buscarcConvenios() throws SQLException {

        ResultSet rs;

        try {
            String sql = "SELECT * FROM CONVENIO";
            this.con = this.conexao.getConexao();

            PreparedStatement pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            ArrayList<Convenio> listaConvenios = new ArrayList<Convenio>();

            while (rs.next()) {
                Convenio convenio = new Convenio();
                convenio.setIdConvenio(rs.getInt("ID_CONVENIO"));
                convenio.setNomeConvenio(rs.getString("NOME_CONVENIO"));
                convenio.setTempoCarencia(rs.getString("TEMPO_CARENCIA"));
                convenio.setCnpj(rs.getString("CNPJ"));
                listaConvenios.add(convenio);
            }

            return listaConvenios;
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar dados do Banco! " + se.getMessage(), se);
        } finally {
            persistencia.ConexaoBanco.fecharConexao(this.con);
        }
    }

    public Convenio buscarConvenioFiltro(String query) throws SQLException {
        ResultSet rs;

        try {
            String sql = "SELECT * FROM CONVENIO WHERE NOME_CONVENIO LIKE '%" + query + "%';";

            this.con = this.conexao.getConexao();
            PreparedStatement pst = con.prepareStatement(sql);

            rs = pst.executeQuery();

            if (rs.next()) {
                Convenio convenio = new Convenio();
                convenio.setIdConvenio(rs.getInt("ID_CONVENIO"));
                convenio.setNomeConvenio(rs.getString("NOME_CONVENIO"));
                convenio.setTempoCarencia(rs.getString("TEMPO_CARENCIA"));
                convenio.setCnpj(rs.getString("CNPJ"));
                return convenio;
            }

            return null;
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar dados do Banco! " + se.getMessage(), se);
        } finally {
            persistencia.ConexaoBanco.fecharConexao(this.con);
        }
    }

}