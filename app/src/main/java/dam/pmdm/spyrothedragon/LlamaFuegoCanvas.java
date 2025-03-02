package dam.pmdm.spyrothedragon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LlamaFuegoCanvas extends View {
    private Paint paint;
    private Bitmap bitmap;

    public LlamaFuegoCanvas(Context context) {
        super(context);

    }

    private void init() {

        paint = new Paint();
        paint.setAntiAlias(true); // Suavizado de bordes
        setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.llama_azul);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 800, 500, true);
        canvas.drawBitmap(scaledBitmap, 0, 0, paint);

    }


}