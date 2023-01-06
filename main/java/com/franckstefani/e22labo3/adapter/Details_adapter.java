package com.franckstefani.e22labo3.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.franckstefani.e22labo3.R;
import com.franckstefani.e22labo3.classes.DetailsExercice;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import java.util.List;

public class Details_adapter extends RecyclerView.Adapter<Details_adapter.ViewHolder> {

    private final List<DetailsExercice> listDetails;
    private Context context;
    public TextView description, cible, execution, respiration, consigne, variante, conseil;
    public ImageButton show1, show2, show3, show4, show5, show6;
    public ImageButton mask1, mask2, mask3, mask4, mask5, mask6;
    private YouTubePlayerView video;

    public Details_adapter(List<DetailsExercice> listDetails, Context context) {
        this.context = context;
        this.listDetails = listDetails;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView image;
        public androidx.constraintlayout.widget.ConstraintLayout layout;


        public ViewHolder(View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.imageView);
            description = itemView.findViewById(R.id.textDescriptionDetail);
            cible = itemView.findViewById(R.id.textCibleDetail);
            execution = itemView.findViewById(R.id.textExecutionDetail);
            respiration = itemView.findViewById(R.id.textRespirationDetail);
            consigne = itemView.findViewById(R.id.textConsigneDetail);
            variante = itemView.findViewById(R.id.textVarianteDetail);
            conseil = itemView.findViewById(R.id.textConseilDetail);
            show1 = itemView.findViewById(R.id.show1);
            mask1 = itemView.findViewById(R.id.mask1);
            show2 = itemView.findViewById(R.id.show2);
            mask2 = itemView.findViewById(R.id.mask2);
            show3 = itemView.findViewById(R.id.show3);
            mask3 = itemView.findViewById(R.id.mask3);
            show4 = itemView.findViewById(R.id.show4);
            mask4 = itemView.findViewById(R.id.mask4);
            show5 = itemView.findViewById(R.id.show5);
            mask5 = itemView.findViewById(R.id.mask5);
            show6 = itemView.findViewById(R.id.show6);
            mask6 = itemView.findViewById(R.id.mask6);
            layout = itemView.findViewById(R.id.layoutDetails);
            video = itemView.findViewById(R.id.videoView);

            itemView.setOnClickListener(view -> { });

        }
    }

    @NonNull
    @Override
    public Details_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listItem = inflater.inflate(R.layout.list_details, parent, false);

        return new Details_adapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(Details_adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DetailsExercice myListData = listDetails.get(position);

        String uri = "@drawable/" + myListData.getImage();
        int imageResource = holder.itemView.getResources().getIdentifier(uri, null, holder.itemView.getContext().getPackageName());
        holder.image.setImageResource(imageResource);
        description.setText(myListData.getDescription());
        cible.setText(myListData.getCible());
        execution.setText(myListData.getExecution());
        respiration.setText(myListData.getRespiration());
        consigne.setText(myListData.getConsigne());
        variante.setText(myListData.getVariante());
        conseil.setText(myListData.getConseil());

        video.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = myListData.getVideo();
                youTubePlayer.cueVideo(videoId, 0);
            }
        });

        holder.layout.setOnClickListener(v -> {
            //Toast.makeText(v.getContext(),"click on item: " + myListData.getDescription(),Toast.LENGTH_LONG).show();
        });

        show1.setOnClickListener(v -> {
            cible.setVisibility(View.VISIBLE);
            show1.setVisibility(View.GONE);
            mask1.setVisibility(View.VISIBLE);
        });

        mask1.setOnClickListener(v -> {
            cible.setVisibility(View.GONE);
            show1.setVisibility(View.VISIBLE);
            mask1.setVisibility(View.GONE);
        });

        show2.setOnClickListener(v -> {
            //getLifecycle().addObserver(video);
            execution.setVisibility(View.VISIBLE);
            video.setVisibility(View.VISIBLE);
            show2.setVisibility(View.GONE);
            mask2.setVisibility(View.VISIBLE);
        });

        mask2.setOnClickListener(v -> {
            execution.setVisibility(View.GONE);
            video.setVisibility(View.GONE);
            show2.setVisibility(View.VISIBLE);
            mask2.setVisibility(View.GONE);
        });

        show3.setOnClickListener(v -> {
            respiration.setVisibility(View.VISIBLE);
            show3.setVisibility(View.GONE);
            mask3.setVisibility(View.VISIBLE);
        });

        mask3.setOnClickListener(v -> {
            respiration.setVisibility(View.GONE);
            show3.setVisibility(View.VISIBLE);
            mask3.setVisibility(View.GONE);
        });

        show4.setOnClickListener(v -> {
            consigne.setVisibility(View.VISIBLE);
            show4.setVisibility(View.GONE);
            mask4.setVisibility(View.VISIBLE);
        });

        mask4.setOnClickListener(v -> {
            consigne.setVisibility(View.GONE);
            show4.setVisibility(View.VISIBLE);
            mask4.setVisibility(View.GONE);
        });

        show5.setOnClickListener(v -> {
            variante.setVisibility(View.VISIBLE);
            show5.setVisibility(View.GONE);
            mask5.setVisibility(View.VISIBLE);
        });

        mask5.setOnClickListener(v -> {
            variante.setVisibility(View.GONE);
            show5.setVisibility(View.VISIBLE);
            mask5.setVisibility(View.GONE);
        });

        show6.setOnClickListener(v -> {
            conseil.setVisibility(View.VISIBLE);
            show6.setVisibility(View.GONE);
            mask6.setVisibility(View.VISIBLE);
        });

        mask6.setOnClickListener(v -> {
            conseil.setVisibility(View.GONE);
            show6.setVisibility(View.VISIBLE);
            mask6.setVisibility(View.GONE);
        });

    }

    @Override
    public int getItemCount() {
        return listDetails.size();
    }

}
