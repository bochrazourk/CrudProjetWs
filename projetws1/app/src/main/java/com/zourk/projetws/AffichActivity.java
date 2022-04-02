package com.zourk.projetws;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zourk.projetws.adapter.EtudiantAdapter;
import com.zourk.projetws.beans.Etudiant;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class AffichActivity extends AppCompatActivity{
    private static final String TAG = "RecycleActivity";
    private RecyclerView recycle;
    private FloatingActionButton addItem;
    RequestQueue requestQueue;
    String loadUrl = "http://24.10.16.160/ProjetVolley/ws/loadEtudiant.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affich);

        recycle = findViewById(R.id.recycle);
        addItem = findViewById(R.id.addItem);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AffichActivity.this, AddEtudiant.class));
            }
        });

        loadData();
    }

    public void loadData() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST,
                loadUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                Type type = new TypeToken<Collection<Etudiant>>(){}.getType();
                Collection<Etudiant> etudiants = new Gson().fromJson(response, type);
                recycle.setAdapter(new EtudiantAdapter(AffichActivity.this, (List<Etudiant>) etudiants));
                recycle.setLayoutManager(new LinearLayoutManager(AffichActivity.this));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(request);
    }
}