package dam.pmdm.spyrothedragon.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import dam.pmdm.spyrothedragon.LLamaFuego;
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
                
                showCanvas(holder.imageImageView.getContext());
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

        public CharactersViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            imageImageView = itemView.findViewById(R.id.image);
        }
    }

    private void showCanvas(Context context) {
        // Crear un diálogo personalizado con un Canvas
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.fragment_canvas);

        // Obtener el View personalizado para dibujar
        LLamaFuego lLamaFuego = dialog.findViewById(R.id.llamaFuego);

        // Mostrar el diálogo
        dialog.show();
    }
}
