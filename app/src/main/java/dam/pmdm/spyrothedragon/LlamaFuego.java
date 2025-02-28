package dam.pmdm.spyrothedragon;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import androidx.annotation.NonNull;

public class LlamaFuego extends View {

    private float scale = 1.0f; // Escala inicial
    private final Paint paint = new Paint();


    public LlamaFuego(Context context) {
        super(context);

    }


    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);


        // Dibuja la llama con la escala actual
        canvas.save();
        canvas.scale(scale, scale, getWidth() / 2f, getHeight() / 2f);
        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, 50, paint);
        canvas.restore();
    }

    public void updateScale(float newScale) {
        this.scale = newScale;
        invalidate(); // Forza un redibujo
    }
    public void init(){
        paint.setColor(Color.RED); // Color de la llama
    }
}