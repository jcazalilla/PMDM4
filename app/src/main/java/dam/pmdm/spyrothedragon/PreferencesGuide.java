package dam.pmdm.spyrothedragon;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.CheckBox;

public class PreferencesGuide {

    private static final String PREFERENCES_NAME = "GuidePreferences";
    private static final String KEY_PERSONAJES = "personajes";
    private static final String KEY_MUNDOS = "mundos";
    private static final String KEY_COLECCIONES = "colecciones";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    //constructor
    public PreferencesGuide(Context context) {

        if (context!=null) {
            this.context = context;
            sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
    }

    //==============================================================================
    //métodos setter que guardan estado de las preferencias
    //==============================================================================

    public void setPreferencesPersonajes(boolean isChecked) {
        editor.putBoolean(KEY_PERSONAJES, isChecked);

        editor.apply();
        Log.d("PreferencesGuide", "Personajes guardados: " + isChecked);
    }

    public void setPreferencesMundos(boolean isChecked) {
        editor.putBoolean(KEY_MUNDOS, isChecked);

        editor.apply();
    }

    public void setPreferencesColecciones(boolean isChecked) {
        editor.putBoolean(KEY_COLECCIONES, isChecked);

        editor.apply();
    }

    //================================================================================
    //métodos getters para recuperar estado de la preferencia
    //================================================================================
    public boolean getKeyPersonajes() {
        Boolean value;
        value= sharedPreferences.getBoolean(KEY_PERSONAJES, false);
        Log.d("PreferencesGuide", "Personajes recuperados: " + value);
        return value;
    }

    public boolean getKeyMundos() {
        return sharedPreferences.getBoolean(KEY_MUNDOS,false);
    }

    public boolean getKeyColecciones() {
        return sharedPreferences.getBoolean(KEY_COLECCIONES, false);
    }


}
