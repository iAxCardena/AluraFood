package br.com.alura.alurafood.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.alurafood.R;
import br.com.alura.alurafood.formatter.FormatadorTelefoneComDdd;
import br.com.alura.alurafood.validator.ValidacaoCpf;
import br.com.alura.alurafood.validator.ValidacaoEmail;
import br.com.alura.alurafood.validator.ValidacaoPadrao;
import br.com.alura.alurafood.validator.ValidacaoTelefoneComDdd;
import br.com.alura.alurafood.validator.Validador;
import br.com.caelum.stella.format.CPFFormatter;

public class FormularioCadastroActivity extends AppCompatActivity {
    private static final String ERRO_FORMATACAO_CPF = "erro formatação CPF";
    private final List<Validador> validadores = new ArrayList<>();
    TextInputLayout textInputNomeCompleto;
    TextInputLayout textInputCPF;
    TextInputLayout textInputTelefoneComDDD;
    TextInputLayout textInputEmail;
    TextInputLayout textInputSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_cadastro);

        inicializaCamposFormularioCadastro();
    }

    private void inicializaCamposFormularioCadastro() {
        configuraCampoNomeCompleto();
        configuraCampoCPF();
        configuraCampoTelefoneComDdd();
        configuraCampoEmail();
        configuraCampoSenha();
        Button botaoCadastrar = findViewById(R.id.formulario_cadastro_botao_cadastrar);
        botaoCadastrar.setOnClickListener(view -> {
            boolean formularioEstaValido = true;
            for (Validador validador : validadores) {
                if(!validador.estaValido()) {
                    formularioEstaValido = false;
                }
            }
            if(formularioEstaValido) {
                Toast.makeText(
                        this,
                        "Cadastro realizado com sucesso!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configuraCampoSenha() {
        textInputSenha = findViewById(R.id.formulario_cadastro_campo_senha);
        validaSePreenchido(textInputSenha);
    }

    private void configuraCampoCPF() {
        textInputCPF = findViewById(R.id.formulario_cadastro_campo_cpf);
        validaCPF(textInputCPF);
    }

    private void configuraCampoNomeCompleto() {
        textInputNomeCompleto = findViewById(R.id.formulario_cadastro_campo_nome_completo);
        validaSePreenchido(textInputNomeCompleto);
    }

    private void configuraCampoEmail() {
        textInputEmail = findViewById(R.id.formulario_cadastro_campo_email);
        EditText campoEmail = textInputEmail.getEditText();
        ValidacaoEmail validador = new ValidacaoEmail(textInputEmail);
        validadores.add(validador);
        campoEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus) {
                    validador.estaValido();
                }
            }
        });
    }

    private void configuraCampoTelefoneComDdd() {
        textInputTelefoneComDDD = findViewById(R.id.formulario_cadastro_campo_telefone_com_ddd);
        EditText campoTelefoneComDdd = textInputTelefoneComDDD.getEditText();
        ValidacaoTelefoneComDdd validador = new ValidacaoTelefoneComDdd(textInputTelefoneComDDD);
        validadores.add(validador);
        FormatadorTelefoneComDdd formatador = new FormatadorTelefoneComDdd();
        campoTelefoneComDdd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                String telefoneComDdd = campoTelefoneComDdd.getText().toString();
                if(hasFocus) {
                    String telefoneComDddFormatado = formatador.remove(telefoneComDdd);
                    campoTelefoneComDdd.setText(telefoneComDddFormatado);
                } else {
                    validador.estaValido();
                }
            }
        });
    }

    private void validaCPF(TextInputLayout textInputCampo) {
        EditText campoCpf = textInputCampo.getEditText();
        final CPFFormatter cpfFormatter = new CPFFormatter();
        ValidacaoCpf validador = new ValidacaoCpf(textInputCampo);
        validadores.add(validador);
        campoCpf.setOnFocusChangeListener((view, hasFocus) -> {
            if(hasFocus) {
                try {
                    adicionaFormatacaoCpf(campoCpf, cpfFormatter);
                }catch(IllegalArgumentException e) {
                    Log.e(ERRO_FORMATACAO_CPF, e.getMessage());
                }
            } else {
                validador.estaValido();
            }
        });

    }

    private void adicionaFormatacaoCpf(EditText campoCpf, CPFFormatter cpfFormatter) {
        String cpf = campoCpf.getText().toString();
        String cpfSemFormato = cpfFormatter.unformat(cpf);
        campoCpf.setText(cpfSemFormato);
    }

    private void validaSePreenchido(final TextInputLayout textInputCampo) {
        EditText campo = textInputCampo.getEditText();
        ValidacaoPadrao validador = new ValidacaoPadrao(textInputCampo);
        validadores.add(validador);
        campo.setOnFocusChangeListener((view, hasFocus) -> {
            if(!hasFocus) {
                validador.estaValido();
            }
        });
    }
}