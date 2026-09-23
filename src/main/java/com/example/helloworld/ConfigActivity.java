package com.example.helloworld;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ConfigActivity extends AppCompatActivity {
    private EditText nomeInput;
    private RadioGroup radioGroup;
    private CheckBox checkHipolipidica;
    private CheckBox checkHipoglicidica;
    private CheckBox checkSemGluten;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_config);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

            nomeInput = findViewById(R.id.nome_input);
            radioGroup = findViewById(R.id.radio_group);
            checkHipolipidica = findViewById(R.id.check_hipolipidica);
            checkHipoglicidica = findViewById(R.id.check_hipoglicidica);
            checkSemGluten = findViewById(R.id.check_sem_gluten);

            Button botaoConfirmar = findViewById(R.id.btn_confirmar);
            Button botaoCancelar = findViewById(R.id.btn_cancelar);


            sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);

            carregarConfiguracoes();

            botaoConfirmar.setOnClickListener(view -> {
                salvarConfiguracoes();
                Toast.makeText(this, "Configurações salvas", Toast.LENGTH_SHORT).show();
                finish();
            });

            botaoCancelar.setOnClickListener(view -> {
                finish();
            });
    }
    private void salvarConfiguracoes() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Nome
        String nome = nomeInput.getText().toString();
        editor.putString("nome", nome);

        // Sexo (radio button selecionado)
        int radioSelecionadoId = radioGroup.getCheckedRadioButtonId();
        if (radioSelecionadoId != -1) {
            RadioButton radioSelecionado = findViewById(radioSelecionadoId);
            String sexo = radioSelecionado.getText().toString();
            editor.putString("sexo", sexo);
        }

        // Dieta (checkboxes)
        editor.putBoolean("hipolipidica", checkHipolipidica.isChecked());
        editor.putBoolean("hipoglicidica", checkHipoglicidica.isChecked());
        editor.putBoolean("semGluten", checkSemGluten.isChecked());

        editor.apply();
    }

    private void carregarConfiguracoes() {
        String nome = sharedPreferences.getString("nome", "Admin");
        nomeInput.setText(nome);

        String sexo = sharedPreferences.getString("sexo", "Masculino");
        if (sexo.equals("Feminino")) {
            RadioButton radioFeminino = findViewById(R.id.radio_feminino);
            radioFeminino.setChecked(true);
        } else {
            RadioButton radioMasculino = findViewById(R.id.radio_masculino);
            radioMasculino.setChecked(true);
        }

        checkHipolipidica.setChecked(sharedPreferences.getBoolean("hipolipidica", false));
        checkHipoglicidica.setChecked(sharedPreferences.getBoolean("hipoglicidica", false));
        checkSemGluten.setChecked(sharedPreferences.getBoolean("semGluten", false));
    }

}