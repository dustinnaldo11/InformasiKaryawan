package com.example.informasikaryawan;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvKaryawan;
    private KaryawanAdapter adapter;
    private ArrayList<Karyawan> karyawanList;
    private DatabaseHelper dbHelper;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvKaryawan = findViewById(R.id.lvKaryawan);
        drawerLayout = findViewById(R.id.drawer_layout);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);  // Reference to SwipeRefreshLayout
        NavigationView navigationView = findViewById(R.id.navigation_view);

        dbHelper = new DatabaseHelper(this);
        karyawanList = new ArrayList<>();
        adapter = new KaryawanAdapter(this, karyawanList);
        lvKaryawan.setAdapter(adapter);

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
                    Intent intentTambah = new Intent(MainActivity.this, TambahKaryawanActivity.class);
                    startActivity(intentTambah);

                } else if (id == R.id.nav_informasi) {
                    Intent intentInformasi = new Intent(MainActivity.this, informasi.class);
                    startActivity(intentInformasi);
                } else if (id == R.id.nav_dash) {

                } else if (id == R.id.nav_logout) {
                    Intent intentLogout = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intentLogout);
                    finish();
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });

        // Setup Swipe to Refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadKaryawanData();  // Refresh the data
                swipeRefreshLayout.setRefreshing(false);  // Stop the refresh animation
            }
        });

        loadKaryawanData();
    }

    private void loadKaryawanData() {
        karyawanList.clear();
        karyawanList.addAll(dbHelper.getAllKaryawan());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Toast.makeText(this, "Karyawan berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
            loadKaryawanData();
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
