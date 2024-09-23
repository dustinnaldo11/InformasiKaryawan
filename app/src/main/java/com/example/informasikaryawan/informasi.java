package com.example.informasikaryawan;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class informasi extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informasi); // Pastikan ini sesuai dengan layout Anda

        drawerLayout = findViewById(R.id.drawer_layout); // Pastikan ID ini benar
        NavigationView navigationView = findViewById(R.id.navigation_view);

        // Setup Hamburger Menu
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_tambah_karyawan) {
                    Intent intentTambah = new Intent(informasi.this, TambahKaryawanActivity.class);
                    startActivity(intentTambah);
                } else if (id == R.id.nav_informasi) {

                } else if (id == R.id.nav_dash) {
                    Intent intentInformasi = new Intent(informasi.this, MainActivity.class);
                    startActivity(intentInformasi);
                } else if (id == R.id.nav_logout) {
                    Intent intentLogout = new Intent(informasi.this, LoginActivity.class);
                    startActivity(intentLogout);
                    finish();
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
