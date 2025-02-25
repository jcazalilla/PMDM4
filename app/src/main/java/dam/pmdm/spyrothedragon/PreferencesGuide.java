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

        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    //==============================================================================
    //métodos setter que guardan estado de las preferencias
    //==============================================================================
    public void setPreferencesPersonajes(boolean isCheckedPersonajes) {
        editor.putBoolean(KEY_PERSONAJES, isCheckedPersonajes);

        editor.apply();
    }

    public void setPreferencesMundos(boolean isCheckedMundos) {
        editor.putBoolean(KEY_PERSONAJES, isCheckedMundos);

        editor.apply();
    }

    public void setPreferencesColecciones(boolean isCheckedColecciones) {
        editor.putBoolean(KEY_PERSONAJES, isCheckedColecciones);

        editor.apply();
    }

    //================================================================================
    //métodos getters para recuperar estado de la preferencia
    //================================================================================
    public boolean getKeyPersonajes() {
        return sharedPreferences.getBoolean(KEY_PERSONAJES, false);
    }

    public boolean getKeyMundos() {
        return sharedPreferences.getBoolean(KEY_MUNDOS, false);
    }

    public boolean getKeyColecciones() {
        return sharedPreferences.getBoolean(KEY_COLECCIONES, false);
    }


}
