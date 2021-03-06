package com.homeowner.cesde;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

import Modelos.ApartamentosModelo;

public class verAtptosReservadosMainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirestoreRecyclerAdapter adapter;

    TextView jtvNombre1,jtvrol;
    RecyclerView jrvFirestoreApartamentsList;
    String email;
    /* =getIntent().getStringExtra("usuario");;
      String rol=getIntent().getStringExtra("rol");
      String password=getIntent().getStringExtra("password");*/




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_atptos_reservados_main);
        getSupportActionBar().hide();

        jtvNombre1=findViewById(R.id.etNombre);
        jtvrol=findViewById(R.id.rol);
        jrvFirestoreApartamentsList=findViewById(R.id.rvFirestoreApartamentsList);


        email=getIntent().getStringExtra("usuario");


      DocumentReference docRef = db.collection("users").document(getIntent().getStringExtra("usuario"));

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("Mensaje1", "DocumentSnapshot data: " + document.getData());
                        String nombre=document.getString("Name");
                        String rol=document.getString("Rol");

                        jtvNombre1.setText(nombre);
                        jtvrol.setText(rol);

                    } else {
                        Log.d("no encuentra", "No such document");
                        Toast.makeText(verAtptosReservadosMainActivity.this, "No encuentra usuario", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Log.d("Mensaje3", "get failed with ", task.getException());
                    Toast.makeText(verAtptosReservadosMainActivity.this, "No encuentra coleccion", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Query query=db.collection("Apartaments").whereEqualTo("state","Reservado").whereEqualTo("client",email);

        FirestoreRecyclerOptions<ApartamentosModelo> options= new FirestoreRecyclerOptions.Builder<ApartamentosModelo>()
                .setQuery(query, ApartamentosModelo.class).build();



        adapter= new FirestoreRecyclerAdapter<ApartamentosModelo, UsuarioActivity.ApartamentsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UsuarioActivity.ApartamentsViewHolder holder, int position, @NonNull ApartamentosModelo model) {

                holder.tvPais.setText(model.getCountry());
                holder.tvciudad.setText(model.getCity());
                holder.tvDireccion.setText(model.getAdress());
                holder.tvDescripcion.setText(model.getDescription());
                holder.tvNhabitaciones.setText(model.getBedrooms());
                holder.tvVnoche.setText(model.getPriceNight());
                holder.TvEstado.setText(model.getState());

                final String id=getSnapshots().getSnapshot(position).getId();




                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(verAtptosReservadosMainActivity.this);
                        builder.setTitle("Reservar Apartamento.");
                        builder.setMessage("Deesea Descartar este Apartamento?")
                                .setPositiveButton("S??", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {


                                        Map<String, Object> reserva = new HashMap<>();

                                        reserva.put("state","Disponible" );
                                        reserva.put("client","");


                                        db.collection("Apartaments").document(id).update(reserva);

                                        Toast.makeText(getApplicationContext(),"DescartadoApartamento"
                                                ,Toast.LENGTH_LONG).show();

                                    }
                                })
                                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();

                    }
                });
            }



            @NonNull
            @Override
            public UsuarioActivity.ApartamentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.apartament_list_invitado1,parent,false);


                return new UsuarioActivity.ApartamentsViewHolder(view);
            }
        };

        jrvFirestoreApartamentsList.setHasFixedSize(true);
        jrvFirestoreApartamentsList.setLayoutManager(new LinearLayoutManager(this));
        jrvFirestoreApartamentsList.setAdapter(adapter);


    }

    private class ApartamentsViewHolder extends RecyclerView.ViewHolder{

        TextView tvPais,tvciudad,tvDireccion,tvNhabitaciones,tvVnoche,TvEstado2,tvDescripcion;

        public ApartamentsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPais=itemView.findViewById(R.id.tvPais);
            tvciudad= itemView.findViewById(R.id.tvciudad);
            tvDireccion=itemView.findViewById(R.id.tvDireccion);
            tvNhabitaciones=itemView.findViewById(R.id.tvNhabitaciones);
            tvVnoche=itemView.findViewById(R.id.tvVnoche2);
            TvEstado2=itemView.findViewById(R.id.TvEstado);
            tvDescripcion=itemView.findViewById(R.id.tvDescripcion);




        }
    }

    @Override
    protected void onStart() {

        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        adapter.stopListening();
    }

    public void regresar(View view){

        Intent intent= new Intent(verAtptosReservadosMainActivity.this,Usuario_invitado_Activity.class);
      //  intent.putExtra("usuario",email);
      //  intent.putExtra("rol",rol);
       // intent.putExtra("password",password);

    startActivity(intent);

    }




}