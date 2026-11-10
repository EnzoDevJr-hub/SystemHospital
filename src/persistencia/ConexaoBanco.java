package persistencia;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexaoBanco {

    private static final Properties PROPS = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            PROPS.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível ler o config.properties", e);
        }
    }

    public Connection getConexao() throws SQLException {
        Connection c = null;
        try {
            c = DriverManager.getConnection(
                PROPS.getProperty("db.url"),
                PROPS.getProperty("db.usuario"),
                PROPS.getProperty("db.senha")
            );
        } catch (SQLException se) {
            throw new SQLException("Erro ao conectar! " + se.getMessage());
        }
        return c;
    }

    public static void fecharConexao(Connection c) {
        try {
            if (c != null && !c.isClosed()) {
                c.close();
                System.out.println("Conexão encerrada com sucesso.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar a conexão: " + e.getMessage());
        }
    }
}