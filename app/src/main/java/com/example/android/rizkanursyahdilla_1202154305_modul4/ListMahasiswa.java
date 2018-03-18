package com.example.android.rizkanursyahdilla_1202154305_modul4;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class ListMahasiswa extends AppCompatActivity {

    ListView listMhs; // membuat objek
    Button aAsyncTask;
    ProgressBar aProgressBar;
    String[] NamaArray = { //array list mahasiswa
            "Rizka", "Dewi", "Fakhri", "Astrid", "Farrel", "Ina", "Caca", "Chika", "Jafar", "Defa"
    };
    AddItemToListView aAddItemToListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mahasiswa);

        listMhs = findViewById(R.id.listNama);//inisiasi objek ambil data dari xml
        aProgressBar = findViewById(R.id.progressBar);
        aAsyncTask = findViewById(R.id.btnMulai);

        //membuat array adapter untuk list nama mahasiswa
        listMhs.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>()));

        aAsyncTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //proses adapter dengan asyncTask
                aAddItemToListView = new AddItemToListView();
                aAddItemToListView.execute();
            }
        });
    }

    public class AddItemToListView extends AsyncTask<Void, String, Void> {

        private ArrayAdapter<String> mAdapter;
        private int counter = 1;
        ProgressDialog myProgressDialog = new ProgressDialog(ListMahasiswa.this);

        @Override
        protected void onPreExecute() { // method yang dijalankan sebelum tasknya di mulai
            mAdapter = (ArrayAdapter<String>) listMhs.getAdapter(); //casting suggestion
            //isi progress dialog
            myProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            myProgressDialog.setTitle("Loading Data");
            myProgressDialog.setCancelable(false);
            myProgressDialog.setMessage("Please wait....");
            myProgressDialog.setProgress(0);
            myProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel Process", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    aAddItemToListView.cancel(true);
                    aProgressBar.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                }
            });
            myProgressDialog.show();
        }
        @Override
        protected Void doInBackground(Void... params) { //mengimplementasikan kode untuk mengeksekusi pekerjaan yang akan dilakukan untuk thread terpisah
            for (String item : NamaArray) {
                publishProgress(item);
                try {//looping
                    Thread.sleep(400); //untuk delay 400 ms untuk melanjutkan
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (isCancelled()) {
                    aAddItemToListView.cancel(true);
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) { //untuk update progress
            mAdapter.add(values[0]);

            Integer current_status = (int) ((counter / (float) NamaArray.length) * 100);
            aProgressBar.setProgress(current_status);

            //set progress only working for horizontal loading
            myProgressDialog.setProgress(current_status);

            //set message will not working when using horizontal loading
            myProgressDialog.setMessage(String.valueOf(current_status + "%"));
            counter++;
        }

        @Override
        protected void onPostExecute(Void aVoid) { ////digunakan untuk memperbarui hasil ke UI setelah AsyncTask telah selesai dimuat./melakukan sesuatu setelah asynctask dijalankan
            //menyembunyikan progressbar
            aProgressBar.setVisibility(View.GONE);

            //hapus progress dialog
            myProgressDialog.dismiss();
            listMhs.setVisibility(View.VISIBLE);
        }
    }
}
