package com.franckstefani.e22labo3.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.franckstefani.e22labo3.adapter.Exercice_Adapter;
import com.franckstefani.e22labo3.R;
import com.franckstefani.e22labo3.classes.Exercice;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class ExerciceActivity extends AppCompatActivity implements Exercice_Adapter.OnItemClicked{

    final String KEY_RECYCLER = "key_recycler";
    private ArrayList<Exercice> tabExercices;
    int idc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercice);

        Toolbar toolbar = findViewById(R.id.toolbarExo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null) {
            tabExercices = savedInstanceState.getParcelableArrayList(KEY_RECYCLER);
        } else {
            Bundle bundle = getIntent().getExtras();
            idc = bundle.getInt("categorie");
            toolbar.setTitle(bundle.getString("titre"));
        }

        tabExercices = new ArrayList<>();
        listerExo();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageResource(R.drawable.plus);
        fab.setOnClickListener(view -> ajouter());
    }

    public void listerExo() {
        String url = "http://10.0.2.2/tp3_musculation/Controlleurs/exerciceControleurJSON.php";

        StringRequest requete = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        Log.d("RESULTAT", response);
                        JSONArray jsonResponse = new JSONArray(response);
                        String msg = jsonResponse.getString(0);
                        if(msg.equals("OK")){
                            JSONObject exo;
                            for(int i = 1;i < jsonResponse.length();i++){
                                exo = jsonResponse.getJSONObject(i);
                                int id = exo.getInt("id");
                                String titre = exo.getString("titre");
                                String image = exo.getString("image");
                                String description = exo.getString("description");
                                int idc = exo.getInt("idc");
                                Exercice newExo = new Exercice(id, titre, image, description, idc);
                                tabExercices.add(newExo);
                            }
                            RecyclerView recyclerView = findViewById(R.id.recyclerViewExo);
                            Exercice_Adapter adapter = new Exercice_Adapter(tabExercices, this);
                            recyclerView.setLayoutManager(new LinearLayoutManager(ExerciceActivity.this));
                            recyclerView.setAdapter(adapter);
                            adapter.setOnClick(ExerciceActivity.this);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(ExerciceActivity.this, "Erreur", Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "lister");
                params.put("idc", String.valueOf(idc));
                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);
    }

    @SuppressLint({"ResourceType", "SetTextI18n"})
    public void ajouter() {
        String url = "http://10.0.2.2:80/tp3_musculation/Controlleurs/exerciceControleurJSON.php";
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_ajout);
        Bundle bundle = getIntent().getExtras();

        TextView categorie = dialog.findViewById(R.id.textFK2);
        categorie.setText(bundle.getString("titre"));
        categorie.setTextColor(Color.RED);
        EditText titre = dialog.findViewById(R.id.editTextTitre);
        EditText description = dialog.findViewById(R.id.editTextDescription);
        TextView image = dialog.findViewById(R.id.textImage2);
        image.setText(bundle.getString("image"));
        Button ajouter = dialog.findViewById(R.id.ajouter);
        Button cancel = dialog.findViewById(R.id.cancel);

        ajouter.setOnClickListener(view -> {
            boolean isValid = true;

            if (titre.getText().toString().isEmpty()) {
                titre.setError("Nom requis");
                isValid = false;
            }
            if (description.getText().toString().isEmpty()) {
                description.setError("Description requise");
                isValid = false;
            }

            if (isValid) {
                StringRequest requete = new StringRequest(Request.Method.POST, url,
                        response -> {

                            try {
                                Log.d("RESULTAT", response);
                                JSONArray jsonResponse = new JSONArray(response);
                                String msg = jsonResponse.getString(0);
                                if(msg.equals("OK"))
                                    Toast.makeText(ExerciceActivity.this, "Exercices bien enregistre", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(ExerciceActivity.this, "Probleme pour enregistrer l'exercice...", Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        error -> Toast.makeText(ExerciceActivity.this, "ERREUR", Toast.LENGTH_SHORT).show()
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("action", "enregistrer");
                        params.put("titre", titre.getText().toString());
                        params.put("image", image.getText().toString());
                        params.put("description", description.getText().toString());
                        params.put("idc", String.valueOf(bundle.getInt("categorie")));

                        return params;
                    }
            };
                Volley.newRequestQueue(this).add(requete);
                Exercice exo = new Exercice(titre.getText().toString().toUpperCase(Locale.ROOT), description.getText().toString(), image.getText().toString(), bundle.getInt("categorie"));
                tabExercices.add(exo);
                dialog.dismiss();
        }
    });
        cancel.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(Exercice exercice) {
        Intent intent = new Intent(ExerciceActivity.this, DetailsExerciceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("titre", exercice.getTitre());
        bundle.putInt("exercice", exercice.get_id());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelableArrayList(KEY_RECYCLER, tabExercices);
        super.onSaveInstanceState(savedInstanceState);
    }

}