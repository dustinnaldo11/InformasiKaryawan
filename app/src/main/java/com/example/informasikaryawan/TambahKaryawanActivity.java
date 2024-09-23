package com.example.informasikaryawan;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class TambahKaryawanActivity extends AppCompatActivity {

    private EditText etNama, etPassword, etGaji;
    private Button btnSimpan;
    private DatabaseHelper dbHelper;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_karyawan);

        etNama = findViewById(R.id.etNama);
        etPassword = findViewById(R.id.etPassword);
        etGaji = findViewById(R.id.etGaji);
        btnSimpan = findViewById(R.id.btnSimpan);
        drawerLayout = findViewById(R.id.drawer_layout); // Pastikan ID ini sesuai dengan layout Anda
        NavigationView navigationView = findViewById(R.id.navigation_view);

        dbHelper = new DatabaseHelper(this);

        // Setup Hamburger Menu
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set listener untuk item menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_tambah_karyawan) {

                } else if (id == R.id.nav_informasi) {
                    Intent intentInformasi = new Intent(TambahKaryawanActivity.this, informasi.class);
                    startActivity(intentInformasi);
                } else if (id == R.id.nav_dash) {
                    Intent intentInformasi = new Intent(TambahKaryawanActivity.this, MainActivity.class);
                    startActivity(intentInformasi);
                } else if (id == R.id.nav_logout) {
                    Intent intentLogout = new Intent(TambahKaryawanActivity.this, LoginActivity.class);
                    startActivity(intentLogout);
                    finish();
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanKaryawan();
            }
        });
    }

    private void simpanKaryawan() {
        String nama = etNama.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String gajiStr = etGaji.getText().toString().trim();

        if (nama.isEmpty() || password.isEmpty() || gajiStr.isEmpty()) {
            Toast.makeText(TambahKaryawanActivity.this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        double gaji = Double.parseDouble(gajiStr);

        boolean isInserted = dbHelper.addKaryawan(nama, password, gaji);

        if (isInserted) {
            Toast.makeText(TambahKaryawanActivity.this, "Karyawan berhasil ditambahkan", Toast.LENGTH_SHORT).show();
            Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent);
            finish();
        } else {
            Toast.makeText(TambahKaryawanActivity.this, "Terjadi kesalahan, coba lagi", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
