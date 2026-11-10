package validacao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FiltroValidadorTest {

    @Test
    public void idApenasNumericoDeveSerAceito() {
        assertTrue(FiltroValidador.validarFiltroId("12345"));
    }

    @Test
    public void idComLetrasDeveSerRejeitado() {
        assertFalse(FiltroValidador.validarFiltroId("12a45"));
    }

    @Test
    public void idVazioDeveSerRejeitado() {
        assertFalse(FiltroValidador.validarFiltroId(""));
    }

    @Test
    public void idComTentativaDeInjecaoSqlDeveSerRejeitado() {
        assertFalse(FiltroValidador.validarFiltroId("1 OR 1=1"));
    }

    @Test
    public void nomeApenasAlfabeticoDeveSerAceito() {
        assertTrue(FiltroValidador.validarFiltroNome("Maria da Silva"));
    }

    @Test
    public void nomeComNumeroDeveSerRejeitado() {
        assertFalse(FiltroValidador.validarFiltroNome("Maria123"));
    }

    @Test
    public void nomeComCaracterEspecialDeveSerRejeitado() {
        assertFalse(FiltroValidador.validarFiltroNome("Maria%'--"));
    }

    @Test
    public void nomeVazioDeveSerRejeitado() {
        assertFalse(FiltroValidador.validarFiltroNome(""));
    }

    @Test
    public void cpfNumericoNaoDeveSerAceitoConformeDocumentoAtual() {
        assertFalse(FiltroValidador.validarFiltroCpf("12345678901"));
    }

    @Test
    public void cpfComTentativaDeInjecaoSqlDeveSerRejeitado() {
        assertFalse(FiltroValidador.validarFiltroCpf("123' OR '1'='1"));
    }

    @Test
    public void cpfVazioDeveSerRejeitado() {
        assertFalse(FiltroValidador.validarFiltroCpf(""));
    }
}