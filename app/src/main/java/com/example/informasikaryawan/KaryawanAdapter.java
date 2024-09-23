package com.example.informasikaryawan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class KaryawanAdapter extends ArrayAdapter<Karyawan> {

    private Context context;
    private ArrayList<Karyawan> karyawanList;
    private DatabaseHelper dbHelper;

    public KaryawanAdapter(Context context, ArrayList<Karyawan> karyawanList) {
        super(context, 0, karyawanList);
        this.context = context;
        this.karyawanList = karyawanList;
        this.dbHelper = new DatabaseHelper(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_karyawan, parent, false);
        }

        TextView tvNama = convertView.findViewById(R.id.tvNama);
        TextView tvGaji = convertView.findViewById(R.id.tvGaji);
        Button btnEdit = convertView.findViewById(R.id.btnEdit);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);

        final Karyawan karyawan = getItem(position);

        if (karyawan != null) {

            tvNama.setText(karyawan.getNama());

            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.GERMAN);
            String formattedGaji = "Rp " + numberFormat.format(karyawan.getGaji());
            tvGaji.setText(formattedGaji);

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditKaryawanActivity.class);
                    intent.putExtra("karyawanId", karyawan.getId());
                    context.startActivity(intent);
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dbHelper.deleteKaryawan(karyawan.getId());

                    karyawanList.remove(position);

                    notifyDataSetChanged();
                }
            });
        }

        return convertView;
    }
}
