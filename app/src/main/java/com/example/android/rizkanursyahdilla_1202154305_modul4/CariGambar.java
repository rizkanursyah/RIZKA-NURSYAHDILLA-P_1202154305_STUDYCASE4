package com.example.android.rizkanursyahdilla_1202154305_modul4;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;
import java.net.URL;

public class CariGambar extends AppCompatActivity {

    private EditText aInput; //membuat objek
    private Button aBtnCari;
    private ImageView aGambar;
    private ProgressDialog aProgressDialog;


// sebelumnya masukkan <uses-permission ke manifest

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_cari_gambar);

        aInput = findViewById(R.id.Link); //ambil data dari xml menggunakan objek
        aBtnCari = findViewById(R.id.btnCari);
        aGambar = findViewById(R.id.GambarTampil);
    }

    public void Cari(View view) { //button cari
        loadImageInit();
    } //button cari

    private void loadImageInit(){ //method untuk gambar yang akan ditampilkan
        String ImgUrl = aInput.getText().toString();
        //AsyncTask mencari gambar di internet
        new loadImage().execute(ImgUrl);
    }

    private class loadImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() { //method yang dijalankan sebelum task nya di mulai kemudian dialog di update setelah progressnya berjalan
            super.onPreExecute();

            // Membuat Progress Dialog
            aProgressDialog = new ProgressDialog(CariGambar.this);

            // Set message Progress Dialog
            aProgressDialog.setMessage("Loading...");

            // menampilkan Progress Dialog
            aProgressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... params) { //mengimplementasikan kode untuk mengeksekusi pekerjaan yang akan dilakukan untuk thread terpisah
            Bitmap bitmap = null;
            try {
                // mendownload gambar dari url
                URL url = new URL(params[0]);
                // mengkonversikan gambar ke bitmat (decode to bitmap)
                bitmap = BitmapFactory.decodeStream((InputStream)url.getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {//digunakan untuk memperbarui hasil ke UI setelah AsyncTask telah selesai dimuat./melakukan sesuatu setelah asynctask dijalankan
            super.onPostExecute(bitmap);
            // menampung gambar ke imageView kemudian ditampilkan
            aGambar.setImageBitmap(bitmap);

            // menghilangkan Progress Dialog
            aProgressDialog.dismiss();
        }
    }
}
