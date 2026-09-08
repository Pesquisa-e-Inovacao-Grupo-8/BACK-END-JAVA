package sptech.school.BACK_END_JAVA.Security.DTO;

public class ValidarAcessoResponse {

    private boolean valido;
    private String tipo;

    public ValidarAcessoResponse() {
        // construtor vazio necessário pro Jackson serializar/desserializar
    }

    public ValidarAcessoResponse(boolean valido, String tipo) {
        this.valido = valido;
        this.tipo = tipo;
    }

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}