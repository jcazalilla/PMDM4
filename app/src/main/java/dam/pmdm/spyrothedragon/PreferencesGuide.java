package dam.pmdm.spyrothedragon;

import android.content.Context;
import android.content.SharedPreferences;

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
    }

    public void setPreferencesMundos(boolean isChecked) {
        editor.putBoolean(KEY_PERSONAJES, isChecked);

        editor.apply();
    }

    public void setPreferencesColecciones(boolean isChecked) {
        editor.putBoolean(KEY_PERSONAJES, isChecked);

        editor.apply();
    }

    //================================================================================
    //métodos getters para recuperar estado de la preferencia
    //================================================================================
    public boolean getKeyPersonajes() {
        return sharedPreferences.getBoolean(KEY_PERSONAJES, false);
    }

    public boolean getKeyMundos() {
        return sharedPreferences.getBoolean(KEY_MUNDOS,false);
    }

    public boolean getKeyColecciones() {
        return sharedPreferences.getBoolean(KEY_COLECCIONES, false);
    }


}
