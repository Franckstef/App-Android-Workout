package com.franckstefani.e22labo3.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.franckstefani.e22labo3.R;
import com.franckstefani.e22labo3.classes.Exercice;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Exercice_Adapter extends RecyclerView.Adapter<Exercice_Adapter.ViewHolder> {

    private final List<Exercice> listExercices;
    private Context context;
    private OnItemClicked onClick;

    public Exercice_Adapter(List<Exercice> listExercices, Context context) {
        this.context = context;
        this.listExercices = listExercices;
    }

    public interface OnItemClicked {
        void onItemClick(Exercice exercice);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView titre;
        public ImageView image;
        public TextView description;
        public ImageButton update;
        public ImageButton delete;
        public androidx.cardview.widget.CardView layout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.titre = itemView.findViewById(R.id.textTitreE);
            this.image = itemView.findViewById(R.id.imageView);
            this.description = itemView.findViewById(R.id.textDescriptionE);
            this.update = itemView.findViewById(R.id.updateButton);
            this.delete = itemView.findViewById(R.id.deleteButton);
            this.layout = itemView.findViewById(R.id.layoutExercice);

            itemView.setOnClickListener(view -> { });
        }
    }

    @NonNull
    @Override
    public Exercice_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listItem = inflater.inflate(R.layout.list_exercice, parent, false);
        return new Exercice_Adapter.ViewHolder(listItem);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(Exercice_Adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Exercice myListData = listExercices.get(position);
        String url = "http://10.0.2.2/tp3_musculation/Controlleurs/exerciceControleurJSON.php";

        holder.titre.setText(myListData.getTitre());
        String uri = "@drawable/" + myListData.getImage();
        int imageResource = holder.itemView.getResources().getIdentifier(uri, null, holder.itemView.getContext().getPackageName());
        holder.image.setImageResource(imageResource);
        holder.description.setText(myListData.getDescription());

        holder.layout.setOnClickListener(v -> {
            onClick.onItemClick(myListData);
            //Toast.makeText(v.getContext(),"click on item: " + myListData.getTitre(),Toast.LENGTH_LONG).show();
        });

        holder.delete.setOnClickListener(view -> {
            AlertDialog.Builder adb = new AlertDialog.Builder(this.context);
            adb.setTitle("Etes vous sur de vouloir supprimer cet exercice ?");
            adb.setPositiveButton("OK", (dialog, which) -> {

                StringRequest requete = new StringRequest(Request.Method.POST, url,
                        response -> {
                            try {
                                Log.d("RESULTAT", response);
                                JSONArray jsonResponse = new JSONArray(response);
                                String msg = jsonResponse.getString(0);
                                if(msg.equals("OK")){
                                    Toast.makeText(context, "Exercice enleve", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(context, "Probleme de suppression", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        error -> Toast.makeText(context, "ERREUR", Toast.LENGTH_SHORT).show()
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("action", "enlever");
                        params.put("id", String.valueOf(myListData.get_id()));
                        return params;
                    }
                };
                Volley.newRequestQueue(context).add(requete);
                notifyDataSetChanged();
                listExercices.remove(position);
                notifyItemRemoved(position);
            });
            adb.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            adb.show();
        });

        holder.update.setOnClickListener(view -> {
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.activity_update);

            EditText titre = dialog.findViewById(R.id.editTextTitre);
            EditText description = dialog.findViewById(R.id.editTextDescription);
            Button update2 = dialog.findViewById(R.id.update);
            Button cancel = dialog.findViewById(R.id.cancel);
            titre.setText(myListData.getTitre());
            description.setText(myListData.getDescription());

            update2.setOnClickListener(View -> {
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
                                        Toast.makeText(context, "Exercice modifié !", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(context, "Probleme pour modifier...", Toast.LENGTH_SHORT).show();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            },
                            error -> Toast.makeText(context, "ERREUR", Toast.LENGTH_SHORT).show()
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("action", "maj");
                            params.put("id", String.valueOf(myListData.get_id()));
                            params.put("titre", titre.getText().toString());
                            params.put("image", String.valueOf(myListData.getImage()));
                            params.put("description", description.getText().toString());
                            params.put("idc", String.valueOf(myListData.get_idFK()));
                            return params;
                        }
                    };
                    Volley.newRequestQueue(context).add(requete);
                    notifyDataSetChanged();
                    Exercice exo = new Exercice(myListData.get_id(), titre.getText().toString().toUpperCase(Locale.ROOT), myListData.getImage(), description.getText().toString());
                    listExercices.set(position, exo);
                    notifyItemChanged(position);
                    dialog.dismiss();
                }
            });
            cancel.setOnClickListener(view1 -> dialog.dismiss());
            dialog.show();
        });
    }
    @Override
    public int getItemCount() {
        return listExercices.size();
    }

    public void setOnClick(OnItemClicked onClick){
        this.onClick=onClick;
    }

}
