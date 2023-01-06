package com.franckstefani.e22labo3.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.franckstefani.e22labo3.adapter.Category_Adapter;
import com.franckstefani.e22labo3.R;
import com.franckstefani.e22labo3.classes.Category;
import com.google.android.material.navigation.NavigationView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CategoryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Category_Adapter.OnItemClicked {

    private Toolbar toolbar;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        toolbar = findViewById(R.id.toolbarCateg);
        setSupportActionBar(toolbar);

        this.configureDrawerLayout();
        this.configureNavigationView();

        listerCateg();
    }

    private void configureDrawerLayout(){
        this.drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureNavigationView(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (this.drawer.isDrawerOpen(GravityCompat.START)) {
            this.drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.contacter:
                break;
            case R.id.tel:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ "5146923710"));
                startActivity(intent);
                break;
            case R.id.mail:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:franckstefani1@gmail.com"));
                startActivity(Intent.createChooser(emailIntent, "Send feedback"));
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        this.drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void listerCateg() {
        final ArrayList<Category> tabCategories = new ArrayList<>();
        String url = "http://10.0.2.2:80/tp3_musculation/Controlleurs/categorieControleurJSON.php";

        StringRequest requete = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        Log.d("RESULTAT", response);
                        JSONArray jsonResponse = new JSONArray(response);
                        String msg = jsonResponse.getString(0);
                        if(msg.equals("OK")){
                            JSONObject categ;
                            for(int i = 1;i < jsonResponse.length();i++){
                                categ = jsonResponse.getJSONObject(i);
                                int id = categ.getInt("id");
                                String titre = categ.getString("titre");
                                String image = categ.getString("image");
                                Category newProduct = new Category(id, titre, image);
                                tabCategories.add(newProduct);
                            }

                            RecyclerView recyclerView = findViewById(R.id.recyclerViewCateg);
                            Category_Adapter adapter = new Category_Adapter(tabCategories, CategoryActivity.this);
                            recyclerView.setLayoutManager(new LinearLayoutManager(CategoryActivity.this));
                            recyclerView.setAdapter(adapter);
                            adapter.setOnClick(CategoryActivity.this);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(CategoryActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "listerCateg");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);
    }

    @Override
    public void onItemClick(Category categorie) {
        Intent intent = new Intent(CategoryActivity.this, ExerciceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("titre", categorie.getTitre());
        bundle.putString("image", categorie.getImage());
        bundle.putInt("categorie", categorie.get_id());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}