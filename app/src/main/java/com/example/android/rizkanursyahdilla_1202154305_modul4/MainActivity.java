package com.example.android.rizkanursyahdilla_1202154305_modul4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void listMahasiswa(View view) { //method button intent list mahasiswa
        Intent i = new Intent(MainActivity.this,ListMahasiswa.class);
        startActivity(i);
    }

    public void cariGambar(View view) { //method button intent list mahasiswa
        Intent i = new Intent(MainActivity.this,CariGambar.class);
        startActivity(i);
    }
}
