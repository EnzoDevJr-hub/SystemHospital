package validacao;

import java.util.List;
import modelo.Paciente;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Suíte de testes de PacienteValidador.
 * Cobre RN01 a RN07 (Cadastro de Paciente) com cenários de valor válido,
 * inválido, limite e vazio/nulo, além do método agregador validar(...).
 *
 * @author enzof
 */
public class PacienteValidadorTest {

    // ---------- RN01: Nome — obrigatório, máx. 55 caracteres ----------

    @Test
    public void nomeValidoDeveSerAceito() {
        assertTrue(PacienteValidador.validarNome("João da Silva"));
    }

    @Test
    public void nomeNuloDeveSerRejeitado() {
        assertFalse(PacienteValidador.validarNome(null));
    }

    @Test
    public void nomeVazioDeveSerRejeitado() {
        assertFalse(PacienteValidador.validarNome(""));
    }

    @Test
    public void nomeCom55CaracteresDeveSerAceito() {
        String nome = "a".repeat(55);
        assertTrue(PacienteValidador.validarNome(nome));
    }

    @Test
    public void nomeComMaisDe55CaracteresDeveSerRejeitado() {
        String nome = "a".repeat(56);
        assertFalse(PacienteValidador.validarNome(nome));
    }

    // ---------- RN02: CPF — obrigatório, 11 dígitos numéricos ----------

    @Test
    public void cpfCom11DigitosDeveSerAceito() {
        assertTrue(PacienteValidador.validarCpf("12345678901"));
    }

    @Test
    public void cpfComMenosDe11DigitosDeveSerRejeitado() {
        assertFalse(PacienteValidador.validarCpf("1234567890"));
    }

    @Test
    public void cpfComMaisDe11DigitosDeveSerRejeitado() {
        assertFalse(PacienteValidador.validarCpf("123456789012"));
    }

    @Test
    public void cpfComLetrasDeveSerRejeitado() {
        assertFalse(PacienteValidador.validarCpf("1234567890A"));
    }

    @Test
    public void cpfComPontuacaoDeveSerRejeitado() {
        assertFalse(PacienteValidador.validarCpf("123.456.789-01"));
    }

    @Test
    public void cpfNuloDeveSerRejeitado() {
        assertFalse(PacienteValidador.validarCpf(null));
    }

    // ---------- RN03: Data de nascimento — obrigatória, válida (DD/MM/AAAA) ----------

    @Test
    public void dataValidaDeveSerAceita() {
        assertTrue(PacienteValidador.validarDataNascimento("15/05/1990"));
    }

    @Test
    public void dataEmFormatoAmericanoDeveSerRejeitada() {
        // 12/31/1990 em dd/MM/yyyy seria dia 12, mês 31 -> inválido
        assertFalse(PacienteValidador.validarDataNascimento("12/31/1990"));
    }

    @Test
    public void dataInexistenteDeveSerRejeitada() {
        // 31 de fevereiro não existe
        assertFalse(PacienteValidador.validarDataNascimento("31/02/2020"));
    }

    @Test
    public void dataVaziaDeveSerRejeitada() {
        assertFalse(PacienteValidador.validarDataNascimento(""));
    }

    @Test
    public void dataNulaDeveSerRejeitada() {
        assertFalse(PacienteValidador.validarDataNascimento(null));
    }

    // ---------- RN04: Endereço — obrigatório, máx. 200 caracteres ----------

    @Test
    public void enderecoValidoDeveSerAceito() {
        assertTrue(PacienteValidador.validarEndereco("Rua das Flores, 123"));
    }

    @Test
    public void enderecoVazioDeveSerRejeitado() {
        assertFalse(PacienteValidador.validarEndereco(""));
    }

    @Test
    public void enderecoCom200CaracteresDeveSerAceito() {
        String endereco = "a".repeat(200);
        assertTrue(PacienteValidador.validarEndereco(endereco));
    }

    @Test
    public void enderecoComMaisDe200CaracteresDeveSerRejeitado() {
        String endereco = "a".repeat(201);
        assertFalse(PacienteValidador.validarEndereco(endereco));
    }

    // ---------- RN05: Telefone — obrigatório, máx. 15, formato (xx)xxxx-xxxx ----------

    @Test
    public void telefoneFixoDeveSerAceito() {
        assertTrue(PacienteValidador.validarTelefone("(11)1234-5678"));
    }

    @Test
    public void telefoneCelularCom9DigitosDeveSerAceito() {
        assertTrue(PacienteValidador.validarTelefone("(11)91234-5678"));
    }

    @Test
    public void telefoneSemParentesesDeveSerRejeitado() {
        assertFalse(PacienteValidador.validarTelefone("11 1234-5678"));
    }

    @Test
    public void telefoneSemTracoDeveSerRejeitado() {
        assertFalse(PacienteValidador.validarTelefone("(11)12345678"));
    }

    @Test
    public void telefoneComMaisDe15CaracteresDeveSerRejeitado() {
        assertFalse(PacienteValidador.validarTelefone("(11)91234-567890"));
    }

    @Test
    public void telefoneNuloDeveSerRejeitado() {
        assertFalse(PacienteValidador.validarTelefone(null));
    }

    // ---------- RN06: E-mail — opcional; se informado, deve ser válido ----------

    @Test
    public void emailValidoDeveSerAceito() {
        assertTrue(PacienteValidador.validarEmail("paciente@exemplo.com"));
    }

    @Test
    public void emailVazioDeveSerAceito() {
        // opcional: vazio é permitido
        assertTrue(PacienteValidador.validarEmail(""));
    }

    @Test
    public void emailNuloDeveSerAceito() {
        // opcional: nulo é permitido
        assertTrue(PacienteValidador.validarEmail(null));
    }

    @Test
    public void emailSemArrobaDeveSerRejeitado() {
        assertFalse(PacienteValidador.validarEmail("pacienteexemplo.com"));
    }

    @Test
    public void emailSemDominioDeveSerRejeitado() {
        assertFalse(PacienteValidador.validarEmail("paciente@"));
    }

    // ---------- RN07: Convênio — obrigatório, deve estar cadastrado ----------

    @Test
    public void convenioSelecionadoDeveSerAceito() {
        assertTrue(PacienteValidador.validarConvenio(1));
    }

    @Test
    public void convenioNaoSelecionadoDeveSerRejeitado() {
        assertFalse(PacienteValidador.validarConvenio(0));
    }

    @Test
    public void convenioComIdNegativoDeveSerRejeitado() {
        assertFalse(PacienteValidador.validarConvenio(-1));
    }

    // ---------- validar(Paciente) e validar(Paciente, String) ----------

    private Paciente pacienteValido() {
        return new Paciente(1, "João da Silva", "Rua das Flores, 123", null,
                "(11)91234-5678", "12345678901", "123456789", "joao@exemplo.com", 1);
    }

    @Test
    public void validarPacienteNuloDeveRetornarErroDePacienteNaoInformado() {
        List<String> erros = PacienteValidador.validar(null);
        assertEquals(1, erros.size());
        assertTrue(erros.contains("Paciente não informado."));
    }

    @Test
    public void validarPacienteComTodosCamposValidosDeveRetornarListaVazia() {
        Paciente pac = pacienteValido();
        List<String> erros = PacienteValidador.validar(pac);
        assertTrue(erros.isEmpty());
    }

    @Test
    public void validarPacienteComCamposInvalidosDeveRetornarErrosCorrespondentes() {
        // nome vazio, cpf inválido, endereço vazio, telefone inválido, e-mail inválido, sem convênio
        Paciente pac = new Paciente(1, "", "", null, "11912345678", "123", "123456789",
                "email-invalido", 0);
        List<String> erros = PacienteValidador.validar(pac);
        assertEquals(6, erros.size());
    }

    @Test
    public void validarPacienteComDataValidaDeveRetornarListaVazia() {
        Paciente pac = pacienteValido();
        List<String> erros = PacienteValidador.validar(pac, "15/05/1990");
        assertTrue(erros.isEmpty());
    }

    @Test
    public void validarPacienteComDataInvalidaDeveRetornarErroDeData() {
        Paciente pac = pacienteValido();
        List<String> erros = PacienteValidador.validar(pac, "31/02/2020");
        assertEquals(1, erros.size());
        assertTrue(erros.get(0).contains("[RN03]"));
    }

}