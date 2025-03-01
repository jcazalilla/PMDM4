package dam.pmdm.spyrothedragon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import androidx.annotation.NonNull;

public class LlamaFuegoCanvas extends View {
    private Paint paint;
    private Bitmap originalBitmap, bitmapScaled;

    public LlamaFuegoCanvas(Context context) {
        super(context);
        //recurso llama azul
        originalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.llama_azul);
        bitmapScaled = Bitmap.createScaledBitmap(originalBitmap,500,200,true);


        init();
    }

    private void init() {
        setBackgroundColor(Color.TRANSPARENT);
        paint = new Paint();
        paint.setAntiAlias(true); // Suavizado de bordes
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);


        if (bitmapScaled != null){
            //dibujar el bitmap
            canvas.drawBitmap(bitmapScaled,0,0,paint);
        }

    }
}