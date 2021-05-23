package com.homeowner.cesde;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import Modelos.ApartamentosModelo;

public class Usuario_invitado_Activity extends AppCompatActivity {


    TextView jtvrol,jTvNombre;
    RecyclerView jrvFirestoreApartamentsList;

    String email;



    FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_invitado_);

        getSupportActionBar().hide();

        jTvNombre=findViewById(R.id.etNombre);
        jtvrol=findViewById(R.id.rol);

        jrvFirestoreApartamentsList=findViewById(R.id.rvFirestoreApartamentsList);




        email=getIntent().getStringExtra("coleccion");


        DocumentReference docRef = db.collection("users").document(getIntent().getStringExtra("coleccion"));
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("Mensaje1", "DocumentSnapshot data: " + document.getData());
                        String nombre=document.getString("Name");
                        String rol=document.getString("Rol");

                        jTvNombre.setText(nombre);
                        jtvrol.setText(rol);

                    } else {
                        Log.d("mensaje2", "No such document");
                    }
                } else {
                    Log.d("Mensaje3", "get failed with ", task.getException());
                }
            }
        });


        Query query=db.collection("Apartaments");

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

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }



            @NonNull
            @Override
            public UsuarioActivity.ApartamentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.apartament_list,parent,false);


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

}