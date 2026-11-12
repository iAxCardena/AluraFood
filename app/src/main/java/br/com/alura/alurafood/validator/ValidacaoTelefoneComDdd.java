package br.com.alura.alurafood.validator;

import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputLayout;

import br.com.alura.alurafood.formatter.FormatadorTelefoneComDdd;

public class ValidacaoTelefoneComDdd implements Validador {
    public static final String DEVE_TER_DEZ_OU_ONZE_DIGITOS = "Telefone deve ter entre 10 e 11 digitos";
    private final TextInputLayout textInputTelefoneComDDD;
    private final EditText campoTelefoneComDdd;
    private final ValidacaoPadrao validacaoPadrao;
    private final FormatadorTelefoneComDdd formatador;

    public ValidacaoTelefoneComDdd(TextInputLayout textInputTelefoneComDDD) {
        this.textInputTelefoneComDDD = textInputTelefoneComDDD;
        this.campoTelefoneComDdd = textInputTelefoneComDDD.getEditText();
        this.validacaoPadrao = new ValidacaoPadrao(textInputTelefoneComDDD);
        this.formatador = new FormatadorTelefoneComDdd();
    }

    private boolean validaEntreDezOuOnzeDigitos(String telefoneComDdd) {
        int digitos = telefoneComDdd.length();
        if(digitos < 10 || digitos > 11) {
            textInputTelefoneComDDD.setError(DEVE_TER_DEZ_OU_ONZE_DIGITOS);
            return false;
        }
        return true;
    }

    @Override
    public boolean estaValido() {
        String telefoneComDdd = campoTelefoneComDdd.getText().toString();
        if(!validacaoPadrao.estaValido()) return false;
        if(!validaEntreDezOuOnzeDigitos(telefoneComDdd)) return false;
        adicionaFormatacao(telefoneComDdd);
        return true;
    }

    private void adicionaFormatacao(String telefoneComDdd) {
        String telefoneComDddFormatado = formatador.formata(telefoneComDdd);
        campoTelefoneComDdd.setText(telefoneComDddFormatado);
    }
}
