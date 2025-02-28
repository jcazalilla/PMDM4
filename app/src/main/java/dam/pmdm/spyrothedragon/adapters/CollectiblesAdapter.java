package dam.pmdm.spyrothedragon.adapters;

import static android.view.View.VISIBLE;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dam.pmdm.spyrothedragon.R;
import dam.pmdm.spyrothedragon.models.Collectible;

public class CollectiblesAdapter extends RecyclerView.Adapter<CollectiblesAdapter.CollectiblesViewHolder> {

    private List<Collectible> list;

    public CollectiblesAdapter(List<Collectible> collectibleList) {
        this.list = collectibleList;
    }

    @Override
    public CollectiblesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new CollectiblesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CollectiblesViewHolder holder, int position) {
        Collectible collectible = list.get(position);
        holder.nameTextView.setText(collectible.getName());

        // Cargar la imagen (simulado con un recurso drawable)
        int imageResId = holder.itemView.getContext().getResources().getIdentifier(collectible.getImage(), "drawable", holder.itemView.getContext().getPackageName());
        holder.imageImageView.setImageResource(imageResId);

        //EASTER EGG === video =====================
        final int[] toques = {0}; //contador de toques en pantalla
        final int TOQUES_NECESARIOS = 4;


        holder.imageImageView.setOnClickListener(view -> {
            toques[0]++;
            if (toques[0] >= TOQUES_NECESARIOS) {
                Drawable drawableActual = holder.imageImageView.getDrawable();
                Drawable drawableReferencia = ContextCompat.getDrawable(
                        holder.itemView.getContext(), R.drawable.gems);

                if (drawableActual != null && drawableReferencia != null
                        && drawableActual.getConstantState() != null
                        && drawableActual.getConstantState().equals(drawableReferencia.getConstantState())) {

                    showEasterEgg(holder.imageImageView.getContext());
                    toques[0] = 0; //reiniciamos contador de toques
                }
            }
        });
        //====================================
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CollectiblesViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        ImageView imageImageView;

        public CollectiblesViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            imageImageView = itemView.findViewById(R.id.image);
        }
    }

    private void showEasterEgg(Context context) {

        // Crear un diálogo
        Dialog dialog = new Dialog(context, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.setContentView(R.layout.fragment_collectibles);

        // Obtener el VideoView del Layout Collectibles
        VideoView videoView = dialog.findViewById(R.id.video_wiew);
        //Hacer visible el videoView del layout del fragmentCollectibles
        videoView.setVisibility(VISIBLE);

        String videoPath = "android.resource://" + context.getPackageName() + "/" + R.raw.demo_video_spyro;
        videoView.setVideoURI(Uri.parse(videoPath));

        videoView.start();

        dialog.show();

        // Cuando el video termina
        videoView.setOnCompletionListener(mp -> dialog.dismiss());

    }
}
