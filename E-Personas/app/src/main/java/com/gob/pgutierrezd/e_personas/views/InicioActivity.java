package com.gob.pgutierrezd.e_personas.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gob.pgutierrezd.e_personas.R;

public class InicioActivity extends AppCompatActivity {

    private Button btnGoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        findViews();

        btnGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InicioActivity.this,LoginActivity.class));
            }
        });
    }

    private void findViews() {
        btnGoLogin = (Button) findViewById(R.id.btn_go_login);
    }
}
