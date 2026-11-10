package validacao;

import java.util.regex.Pattern;

// RN02 (visualização): valida os campos de filtro por ID, CPF e Nome.
// Obs: o documento pede "alfabético" tanto para Nome quanto para CPF,
// o que parece um erro de digitação (CPF é numérico), mas implementamos
// exatamente como está escrito no documento de requisitos.
public class FiltroValidador {

    private static final Pattern PADRAO_NUMERICO = Pattern.compile("\\d+");
    private static final Pattern PADRAO_ALFABETICO = Pattern.compile("[\\p{L} ]+");

    private FiltroValidador() {
    }

    public static boolean validarFiltroId(String valor) {
        return valor != null && PADRAO_NUMERICO.matcher(valor).matches();
    }

    public static boolean validarFiltroNome(String valor) {
        return valor != null && PADRAO_ALFABETICO.matcher(valor).matches();
    }

    public static boolean validarFiltroCpf(String valor) {
        return valor != null && PADRAO_ALFABETICO.matcher(valor).matches();
    }
}