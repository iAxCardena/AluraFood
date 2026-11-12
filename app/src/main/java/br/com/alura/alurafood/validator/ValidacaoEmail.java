package br.com.alura.alurafood.validator;

import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class ValidacaoEmail implements Validador {
    public static final String EMAIL_INVALIDO = "E-mail inválido";
    private final TextInputLayout textInputEmail;
    private final EditText campoEmail;
    private final ValidacaoPadrao validador;

    public ValidacaoEmail(TextInputLayout textInputEmail) {
        this.textInputEmail = textInputEmail;
        this.campoEmail = textInputEmail.getEditText();
        this.validador = new ValidacaoPadrao(this.textInputEmail);
    }

    private boolean validacaoPadrao(String email) {
        if(email.matches(".+@.+\\..+")) return true;
        textInputEmail.setError(EMAIL_INVALIDO);
        return false;
    }

    @Override
    public boolean estaValido() {
        String email = campoEmail.getText().toString();
        if(!validador.estaValido()) return false;
        if(!validacaoPadrao(email)) return false;
        return true;
    }
}
