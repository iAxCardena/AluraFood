package br.com.alura.alurafood.validator;

import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputLayout;

import br.com.caelum.stella.format.CPFFormatter;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

public class ValidacaoCpf implements Validador {
    private static final String CPF_INVALIDO = "CPF inválido";
    private static final String DEVE_TER_ONZE_DIGITOS = "O CPF precisa ter 11 digitos";
    private final TextInputLayout textInputCpf;
    private final EditText campoCpf;
    private final ValidacaoPadrao validacaoPadrao;
    private final CPFFormatter cpfFormatter = new CPFFormatter();

    public ValidacaoCpf(TextInputLayout textInputCpf) {
        this.textInputCpf = textInputCpf;
        this.campoCpf = this.textInputCpf.getEditText();
        this.validacaoPadrao = new ValidacaoPadrao(textInputCpf);
    }

    private boolean validaCalculoCPF(String cpf) {
        try {
            CPFValidator cpfValidator = new CPFValidator();
            cpfValidator.assertValid(cpf);
        } catch(InvalidStateException e) {
            textInputCpf.setError(CPF_INVALIDO);
            return false;
        }
        return true;
    }

    private boolean validaCpfCom11Digitos(String cpf) {
        if(cpf.length() != 11) {
            textInputCpf.setError(DEVE_TER_ONZE_DIGITOS);
            return false;
        }
        return true;
    }

    @Override
    public boolean estaValido() {
        if(!validacaoPadrao.estaValido()) return false;
        String cpf = getCpf();
        String cpfSemFormato = cpf;
        try {
            cpfSemFormato = cpfFormatter.unformat(cpf);
        } catch(InvalidStateException e) {
            textInputCpf.setError(CPF_INVALIDO);
        }
        if(!validaCpfCom11Digitos(cpfSemFormato)) return false;
        if(!validaCalculoCPF(cpfSemFormato)) return false;
        formataCpf();
        return true;
    }

    private void formataCpf() {
        String cpf = getCpf();
        String cpfFormatado = cpfFormatter.format(cpf);
        campoCpf.setText(cpfFormatado);
    }

    @NonNull
    private String getCpf() {
        return this.campoCpf.getText().toString();
    }
}
