package com.franckstefani.e22labo3.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.franckstefani.e22labo3.R;
import com.franckstefani.e22labo3.classes.Category;
import java.util.List;

public class Category_Adapter extends RecyclerView.Adapter<Category_Adapter.ViewHolder> {

    private final List<Category> listCategories;
    private Context context;
    private OnItemClicked onClick;

    public Category_Adapter(List<Category> listCategories, Context context) {
        this.context = context;
        this.listCategories = listCategories;
    }

    public interface OnItemClicked {
        void onItemClick(Category categorie);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView titre;
        public ImageView image;
        public androidx.cardview.widget.CardView layout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.titre = itemView.findViewById(R.id.textTitreC);
            this.image = itemView.findViewById(R.id.imageView);
            this.layout = itemView.findViewById(R.id.layoutExo);

            itemView.setOnClickListener(view -> { });
        }
    }

    @NonNull
    @Override
    public Category_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listItem = inflater.inflate(R.layout.list_category, parent, false);

        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Category myListData = listCategories.get(position);
        holder.titre.setText(myListData.getTitre());

        String uri = "@drawable/" + myListData.getImage();
        int imageResource = holder.itemView.getResources().getIdentifier(uri, null, holder.itemView.getContext().getPackageName());
        holder.image.setImageResource(imageResource);

        holder.layout.setOnClickListener(v -> {
            onClick.onItemClick(myListData);
            //Toast.makeText(v.getContext(),"click on item: " + myListData.getTitre(),Toast.LENGTH_LONG).show();
        });

    }

    @Override
    public int getItemCount() {
        return listCategories.size();
    }

    public void setOnClick(OnItemClicked onClick){
        this.onClick=onClick;
    }

}
