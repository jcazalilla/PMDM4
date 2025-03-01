package dam.pmdm.spyrothedragon.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import dam.pmdm.spyrothedragon.LlamaFuegoCanvas;
import dam.pmdm.spyrothedragon.R;
import dam.pmdm.spyrothedragon.models.Character;

import java.util.List;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder> {

    private List<Character> list;

    public CharactersAdapter(List<Character> charactersList) {
        this.list = charactersList;
    }

    @Override
    public CharactersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new CharactersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CharactersViewHolder holder, int position) {
        Character character = list.get(position);
        holder.nameTextView.setText(character.getName());

        // Cargar la imagen (simulado con un recurso drawable)
        int imageResId = holder.itemView.getContext().getResources().getIdentifier(character.getImage(), "drawable", holder.itemView.getContext().getPackageName());
        holder.imageImageView.setImageResource(imageResId);

        //drawable referencia = spyro
        Drawable drawableReferencia = ContextCompat.getDrawable(
                holder.itemView.getContext(), R.drawable.spyro);
        //drawable en pulsación prolongada
        Drawable drawableActual = holder.imageImageView.getDrawable();

        holder.imageImageView.setOnLongClickListener(view -> {

            if (drawableActual != null && drawableReferencia != null
                    && drawableActual.getConstantState() != null
                    && drawableActual.getConstantState().equals(drawableReferencia.getConstantState())) {



            }
            return true;
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CharactersViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        ImageView imageImageView;
        LlamaFuegoCanvas llamaFuego;
        public CharactersViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            imageImageView = itemView.findViewById(R.id.image);
            llamaFuego = itemView.findViewById(R.id.llamaFuego);
        }
    }
}
