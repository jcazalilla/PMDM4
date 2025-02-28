package dam.pmdm.spyrothedragon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


public class LLamaFuego extends View {

    private Paint paint;
    private Bitmap bitmap;

    public LLamaFuego(Context context) {
        super(context);
        init();
    }

    public LLamaFuego(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setBackgroundColor(Color.TRANSPARENT);
        bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.flame);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

         canvas.drawBitmap(bitmap,1f,1f,paint);
    }
}
