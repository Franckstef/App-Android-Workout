package com.franckstefani.e22labo3.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.franckstefani.e22labo3.R;
import com.franckstefani.e22labo3.adapter.Details_adapter;
import com.franckstefani.e22labo3.classes.DetailsExercice;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DetailsExerciceActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ArrayList<DetailsExercice> listDetailsExercice;
    final String KEY_RECYCLER = "key_recycler";
    private ArrayList<DetailsExercice> tabDetails;
    int ide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_exercice);

        toolbar = findViewById(R.id.toolbarDetails);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null) {
            listDetailsExercice = savedInstanceState.getParcelableArrayList(KEY_RECYCLER);
        } else {
            Bundle bundle = getIntent().getExtras();
            ide = bundle.getInt("exercice");
            toolbar.setTitle(bundle.getString("titre"));
        }

        tabDetails = new ArrayList<>();
        listerDetails();

    }

    public void listerDetails() {
        String url = "http://10.0.2.2/tp3_musculation/Controlleurs/detailsControleurJSON.php";

        StringRequest requete = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        Log.d("RESULTAT", response);
                        JSONArray jsonResponse = new JSONArray(response);
                        String msg = jsonResponse.getString(0);
                        if(msg.equals("OK")){
                            JSONObject details;
                            for(int i = 1;i < jsonResponse.length();i++){
                                details = jsonResponse.getJSONObject(i);
                                int id = details.getInt("id");
                                String image = details.getString("image");
                                String description = details.getString("description");
                                String cible = details.getString("cible");
                                String execution = details.getString("execution");
                                String respiration = details.getString("respiration");
                                String consigne = details.getString("consigne");
                                String variante = details.getString("variante");
                                String conseil = details.getString("conseil");
                                int ide = details.getInt("ide");
                                String video = details.getString("video");
                                DetailsExercice newDetails = new DetailsExercice(id, image, description, cible, execution, respiration, consigne, variante, conseil, ide, video);
                                tabDetails.add(newDetails);
                            }

                            RecyclerView recyclerView = findViewById(R.id.recyclerViewDetails);
                            Details_adapter adapter = new Details_adapter(tabDetails, this);
                            recyclerView.setLayoutManager(new LinearLayoutManager(DetailsExerciceActivity.this));
                            recyclerView.setAdapter(adapter);
                        }
                        else{}
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(DetailsExerciceActivity.this, "Erreur", Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "lister");
                params.put("ide", String.valueOf(ide));
                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelableArrayList(KEY_RECYCLER, listDetailsExercice);
        super.onSaveInstanceState(savedInstanceState);
    }

}