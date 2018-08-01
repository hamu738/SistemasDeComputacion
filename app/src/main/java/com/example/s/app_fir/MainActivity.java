package com.example.s.app_fir;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private ArrayList<String> data = new ArrayList<String>();
    private ArrayList<String> Ubicacion = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    ListView lv;
    Estacionamiento estacionamiento; ///////


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference("Estacionamientos"); //referencia a root

        lv = (ListView) findViewById(R.id.listview);
        estacionamiento = new Estacionamiento(); //empieza vacio
        adapter = new MyListAdapter(this, R.layout.esta_info2, data);
        lv.setAdapter(adapter);


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                data.clear();
                Ubicacion.clear();

                //cambios del childre user01, user02, user03
                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    //adquirimos todos los user data
                    estacionamiento = ds.getValue(Estacionamiento.class);

                    Ubicacion.add(estacionamiento.getUbicacion());

                    data.add("\n Nombre: " + estacionamiento.getNombre().toString() + "\n"  +
                            " Lugares totales: " + estacionamiento.getLugaresTotales().toString() + "\n"  +
                            " Lugares disponibles: " + estacionamiento.getLugaresDisponibles().toString() + "\n\n" );
                }

                lv.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        }


    private class MyListAdapter extends ArrayAdapter<String> {
        private int layout;
        private List<String> mObjects;

        private MyListAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            mObjects = objects;
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder mainViewholder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.title = (TextView) convertView.findViewById(R.id.list_item_text);
                viewHolder.button = (Button) convertView.findViewById(R.id.list_item_btn);
                convertView.setTag(viewHolder);
            }
            mainViewholder = (ViewHolder) convertView.getTag();

            mainViewholder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getContext(), "Button was clicked for list item " + position, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                    //paso de informacion a la otra activity
                    intent.putExtra("posicion", position);
                    intent.putExtra("ubicacion", Ubicacion.get(position));
                    startActivity(intent);
                }
            });
            mainViewholder.title.setText(getItem(position));

            return convertView;
        }
    }
    public class ViewHolder {

        TextView title;
        Button button;
    }
}





