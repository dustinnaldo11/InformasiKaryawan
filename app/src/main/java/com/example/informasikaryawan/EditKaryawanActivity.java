package com.example.informasikaryawan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class EditKaryawanActivity extends AppCompatActivity {

    private EditText etNama, etPassword, etGaji;
    private Button btnUpdate;
    private DatabaseHelper dbHelper;
    private int karyawanId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_karyawan);

        etNama = findViewById(R.id.etNama);
        etPassword = findViewById(R.id.etPassword);
        etGaji = findViewById(R.id.etGaji);
        btnUpdate = findViewById(R.id.btnUpdate);

        dbHelper = new DatabaseHelper(this);

        karyawanId = getIntent().getIntExtra("karyawanId", -1);

        loadKaryawanData();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = etNama.getText().toString();
                String password = etPassword.getText().toString();
                double gaji = Double.parseDouble(etGaji.getText().toString());

                dbHelper.updateKaryawan(karyawanId, nama, password, gaji);

                Intent intent = new Intent(EditKaryawanActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadKaryawanData() {
        Karyawan karyawan = dbHelper.getKaryawanById(karyawanId);
        if (karyawan != null) {
            etNama.setText(karyawan.getNama());
            etPassword.setText(karyawan.getPassword());
            etGaji.setText(String.valueOf(karyawan.getGaji()));
        }
    }
}
