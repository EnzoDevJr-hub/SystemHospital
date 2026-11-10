package validacao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import modelo.Paciente;

public class PacienteValidador {

    private static final int NOME_MAX = 55;
    private static final int CPF_TAMANHO = 11;
    private static final int ENDERECO_MAX = 200;
    private static final int TELEFONE_MAX = 15;

    private static final Pattern PADRAO_CPF = Pattern.compile("\\d{" + CPF_TAMANHO + "}");
    private static final Pattern PADRAO_TELEFONE = Pattern.compile("\\(\\d{2}\\)\\d{4,5}-\\d{4}");
    private static final Pattern PADRAO_EMAIL = Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    private PacienteValidador() {
    }

    // RN01: Nome obrigatório, máx. 55 caracteres
    public static boolean validarNome(String nome) {
        return nome != null && !nome.trim().isEmpty() && nome.length() <= NOME_MAX;
    }

    // RN02: CPF obrigatório, 11 caracteres numéricos
    public static boolean validarCpf(String cpf) {
        return cpf != null && PADRAO_CPF.matcher(cpf).matches();
    }

    // RN03: Data de nascimento obrigatória e válida (dd/MM/yyyy)
    public static boolean validarDataNascimento(String data) {
        if (data == null || data.trim().isEmpty()) {
            return false;
        }
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        formato.setLenient(false);
        try {
            formato.parse(data);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    // RN04: Endereço obrigatório, máx. 200 caracteres
    public static boolean validarEndereco(String endereco) {
        return endereco != null && !endereco.trim().isEmpty() && endereco.length() <= ENDERECO_MAX;
    }

    // RN05: Telefone obrigatório, máx. 15 caracteres, formato (xx)xxxx-xxxx
    public static boolean validarTelefone(String telefone) {
        return telefone != null
                && telefone.length() <= TELEFONE_MAX
                && PADRAO_TELEFONE.matcher(telefone).matches();
    }

    // RN06: E-mail opcional; se informado, deve ser válido
    public static boolean validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return true;
        }
        return PADRAO_EMAIL.matcher(email).matches();
    }

    // RN07: Convênio obrigatório (precisa ter sido selecionado)
    public static boolean validarConvenio(int idConvenio) {
        return idConvenio > 0;
    }

    public static List<String> validar(Paciente pac) {
        List<String> erros = new ArrayList<String>();
        if (pac == null) {
            erros.add("Paciente não informado.");
            return erros;
        }
        if (!validarNome(pac.getNome())) {
            erros.add("[RN01] Nome é obrigatório e deve ter no máximo " + NOME_MAX + " caracteres.");
        }
        if (!validarCpf(pac.getCpf())) {
            erros.add("[RN02] CPF é obrigatório e deve conter " + CPF_TAMANHO + " dígitos numéricos.");
        }
        if (!validarEndereco(pac.getEndereco())) {
            erros.add("[RN04] Endereço é obrigatório e deve ter no máximo " + ENDERECO_MAX + " caracteres.");
        }
        if (!validarTelefone(pac.getTelefone())) {
            erros.add("[RN05] Telefone é obrigatório, no máximo " + TELEFONE_MAX
                    + " caracteres, formato (xx)xxxx-xxxx.");
        }
        if (!validarEmail(pac.getEmail())) {
            erros.add("[RN06] E-mail informado não é válido.");
        }
        if (!validarConvenio(pac.getIdConvenio())) {
            erros.add("[RN07] É obrigatório selecionar um convênio cadastrado.");
        }
        return erros;
    }

    public static List<String> validar(Paciente pac, String dataNascimentoTexto) {
        List<String> erros = validar(pac);
        if (!validarDataNascimento(dataNascimentoTexto)) {
            erros.add("[RN03] Data de nascimento é obrigatória e deve ser uma data válida (DD/MM/AAAA).");
        }
        return erros;
    }
}