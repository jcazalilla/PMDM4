package dam.pmdm.spyrothedragon;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;


import dam.pmdm.spyrothedragon.databinding.ActivityMainBinding;
import dam.pmdm.spyrothedragon.databinding.GuideBienvenidaBinding;
import dam.pmdm.spyrothedragon.databinding.GuideColeccionablesBinding;
import dam.pmdm.spyrothedragon.databinding.GuideInfoBinding;
import dam.pmdm.spyrothedragon.databinding.GuideMundosBinding;
import dam.pmdm.spyrothedragon.databinding.GuidePersonajesBinding;
import dam.pmdm.spyrothedragon.databinding.GuideResumenBinding;
import dam.pmdm.spyrothedragon.ui.CharactersFragment;
import dam.pmdm.spyrothedragon.ui.CollectiblesFragment;
import dam.pmdm.spyrothedragon.ui.WorldsFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    NavController navController = null;

    //declaramos los layout de la guide
    private GuideBienvenidaBinding bienvenidaBinding;
    private GuidePersonajesBinding personajesBinding;
    private GuideMundosBinding mundosBinding;
    private GuideColeccionablesBinding coleccionablesBinding;
    private GuideInfoBinding infoBinding;
    private GuideResumenBinding resumenBinding;


    private boolean needToStartGuide = true;
    private Object menu;

    private PreferencesGuide preferencesGuide;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        //inicializamos guides layouts para manejarlos
        bienvenidaBinding = binding.guideBienvenidaLayout;
        personajesBinding = binding.guidePersonajesLayout;
        mundosBinding = binding.guideMundosLayout;
        coleccionablesBinding = binding.guideColeccionablesLayout;
        infoBinding = binding.guideInfoLayout;
        resumenBinding = binding.guideResumenLayout;


        setContentView(binding.getRoot());

        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        if (navHostFragment != null) {
            navController = NavHostFragment.findNavController(navHostFragment);
            NavigationUI.setupWithNavController(binding.navView, navController);
            NavigationUI.setupActionBarWithNavController(this, navController);
        }

        binding.navView.setOnItemSelectedListener(this::selectedBottomMenu);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            // Para las pantallas de los tabs, no queremos que aparezca la flecha de atrás
            // Si se navega a una pantalla donde se desea mostrar la flecha de atrás, habilítala
            getSupportActionBar().setDisplayHomeAsUpEnabled(destination.getId() != R.id.navigation_characters &&
                    destination.getId() != R.id.navigation_worlds &&
                    destination.getId() != R.id.navigation_collectibles);
        });


        //inicia la guia
        initializeGuide();


    }

    private boolean selectedBottomMenu(@NonNull MenuItem menuItem) {

        //declaramos previamente el fragemento que vamos
        //a tratar para hacer la animación con bottomNavigation
        Fragment selectedFragment = null;

        if (menuItem.getItemId() == R.id.nav_characters) {

            navController.navigate(R.id.navigation_characters);
            //instanciamos el fragmento declarado en cada opción,
            // esta y las siguientes
            selectedFragment = new CharactersFragment();

        } else if (menuItem.getItemId() == R.id.nav_worlds) {

            navController.navigate(R.id.navigation_worlds);
            selectedFragment = new WorldsFragment();

        } else {

            navController.navigate(R.id.navigation_collectibles);
            selectedFragment = new CollectiblesFragment();
        }
        //configuramos animación entre fragmentos al navegar
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    //usamos nuestras customs
                    .setCustomAnimations(
                            R.anim.exit_to_right,
                            R.anim.enter_from_left,
                            R.anim.exit_to_left,
                            R.anim.enter_from_left)
                    //navegamos al contenedor de fragmento con el fragmento seleccionado
                    //con las custom animation
                    .replace(R.id.navHostFragment, selectedFragment)
                    // guardamos fragmento a retroceder
                    // para tener animación también
                    .addToBackStack(null)
                    .commit();
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Infla el menú
        getMenuInflater().inflate(R.menu.about_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Gestiona el clic en el ítem de información
        if (item.getItemId() == R.id.action_info) {
            showInfoDialog();  // Muestra el diálogo
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showInfoDialog() {
        // Crear un diálogo de información
        new AlertDialog.Builder(this)
                .setTitle(R.string.title_about)
                .setMessage(R.string.text_about)
                .setPositiveButton(R.string.accept, null)
                .show();
    }


    private void initializeGuide() {

        getSupportActionBar().hide();//oculta ActionBar
        bienvenidaBinding.btncomenzarGuide.setOnClickListener(this::initializePersonajes);
        bienvenidaBinding.getRoot().setVisibility(VISIBLE);

    }


    //inicia guia
    private void initializePersonajes(View view) {

        bienvenidaBinding.getRoot().setVisibility(GONE);

        soundCambioFragment();//reproducir sonido

        //comprobamos que se haya visualizado la guide en este punto
        preferencesGuide = new PreferencesGuide(this);
        CheckBox chkPersonajes = findViewById(R.id.chkPersonajes);

        if (preferencesGuide.getKeyPersonajes() == true) {
            initalizeMundos(view);
        } else {

            chkPersonajes.setChecked(true);
            //guardamos el cambio de estado a true del checbox en preferencias
            chkPersonajes.setOnCheckedChangeListener((button, isChecked) -> {
                preferencesGuide.setPreferencesPersonajes(isChecked);
            });
        }

        // Cargar la transición desde el recurso XML
        Transition fade = TransitionInflater.from(MainActivity.this)
                .inflateTransition(R.transition.fade);
        // Iniciar la transición desde el anterior layout
        TransitionManager.beginDelayedTransition(findViewById(R.id.guideBienvenidaLayout), fade);


        //listener de los buttons
        personajesBinding.exitGuide.setOnClickListener(this::onExitGuide);
        personajesBinding.nextToMundos.setOnClickListener(this::initalizeMundos);

        if (needToStartGuide) {
            //bloqueamos bottom navigation
            binding.navView.setClickable(false);
            binding.navView.setFocusable(false);
            binding.navView.setEnabled(false);

            //ocultamos layout de bienvenida
            bienvenidaBinding.guidebienvenidaLayout.setVisibility(GONE);
            //hacemos visible la guia
            personajesBinding.guidePersonajesLayout.setVisibility(VISIBLE);
        }

        positionPulse(-38f,.30f);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(
                personajesBinding.pulseImage, "scaleX", 1f, 0.5f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(
                personajesBinding.pulseImage, "scaleY", 1f, .5f);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(
                personajesBinding.textStep, "alpha", 0f, 1f);

        scaleX.setRepeatCount(3);
        scaleY.setRepeatCount(3);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleX).with(scaleX).before(fadeIn);
        animatorSet.setDuration(1500);
        animatorSet.start();


        animatorSet.addListener((new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (needToStartGuide) {
                    super.onAnimationEnd(animation);

                    personajesBinding.pulseImage.setVisibility(VISIBLE);
                    personajesBinding.textStep.setVisibility(VISIBLE);
                }
            }
        }));
    }

    private void initalizeMundos(View view) {

        navController.navigate(R.id.navigation_worlds);//Mostramos por debajo el fragment worlds
        personajesBinding.getRoot().setVisibility(GONE);//ocultamos guide del que viene
        mundosBinding.getRoot().setVisibility(VISIBLE);//hacemos visible la guide a la que llegamos

        soundCambioFragment();//reproducir sonido

        //comprobamos que se haya visualizado la guide en este punto
        preferencesGuide = new PreferencesGuide(this);
        CheckBox chkMundos = findViewById(R.id.chkMundos);
        Boolean chk = preferencesGuide.getKeyMundos();

        if (preferencesGuide.getKeyMundos() == true) {
            initalizeColeccionables(view);
        } else {
            chkMundos.setChecked(true);
            //guardamos el cambio de estado a true del checbox en preferencias
            chkMundos.setOnCheckedChangeListener((buttonView, isChecked) -> {
                preferencesGuide.setPreferencesMundos(isChecked);
            });
        }

        //positionPulse(0f);
        // Cargar la transición desde el recurso XML
        Transition fade = TransitionInflater.from(MainActivity.this)
                .inflateTransition(R.transition.slide_right);
        // Iniciar la transición desde el anterior layout
        TransitionManager.beginDelayedTransition(findViewById(R.id.guidePersonajesLayout), fade);

        mundosBinding.nextToColeccionables.setOnClickListener(this::initalizeColeccionables);
        mundosBinding.exitGuide.setOnClickListener(this::onExitGuide);

        positionPulse(23,0);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(
                mundosBinding.pulseImage, "scaleX", .5f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(
                mundosBinding.pulseImage, "scaleY", .5f, 1f);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(
                mundosBinding.textStep, "alpha", 0f, 1f);

        scaleX.setRepeatCount(3);
        scaleY.setRepeatCount(3);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleX).with(scaleX).before(fadeIn);
        animatorSet.setDuration(1000);
        animatorSet.start();

        animatorSet.addListener((new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (needToStartGuide) {
                    super.onAnimationEnd(animation);

                    mundosBinding.pulseImage.setVisibility(VISIBLE);
                    mundosBinding.textStep.setVisibility(VISIBLE);
                }
            }
        }));
    }


    private void initalizeColeccionables(View view) {

        navController.navigate(R.id.navigation_collectibles);//mostramos el fragment collectibles
        mundosBinding.getRoot().setVisibility(GONE);//ocultamos guide del que viene
        coleccionablesBinding.getRoot().setVisibility(VISIBLE);//hacemos visible la guide a la que llegamos

        soundCambioFragment();//reproducir sonido

        //comprobamos que se haya visualizado la guide en este punto
        preferencesGuide = new PreferencesGuide(this);
        CheckBox chkColecciones = findViewById(R.id.chkColecciones);

        if (preferencesGuide.getKeyColecciones() == true) {
            initalizeResumen(view);
        } else {
            chkColecciones.setChecked(true);
            //guardamos el cambio de estado a true del checbox en preferencias
            chkColecciones.setOnCheckedChangeListener((buttonView, isChecked) -> {
                preferencesGuide.setPreferencesColecciones(isChecked);
            });
        }

         positionPulse(.5f,.30f);

        // Cargar la transición desde el recurso XML
        Transition fade = TransitionInflater.from(MainActivity.this)
                .inflateTransition(R.transition.slide_left);
        // Iniciar la transición desde el anterior layout
        TransitionManager.beginDelayedTransition(findViewById(R.id.guideMundosLayout), fade);

        coleccionablesBinding.nextToResumen.setOnClickListener(this::initalizeInfo);
        coleccionablesBinding.exitGuide.setOnClickListener(this::onExitGuide);


        ObjectAnimator scaleX = ObjectAnimator.ofFloat(
                coleccionablesBinding.pulseImage, "scaleX", 1f, .5f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(
                coleccionablesBinding.pulseImage, "scaleY", 1f, .5f);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(
                coleccionablesBinding.textStep, "alpha", 0f, 1f);

        scaleX.setRepeatCount(3);
        scaleY.setRepeatCount(3);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleX).with(scaleX).before(fadeIn);
        animatorSet.setDuration(1000);
        animatorSet.start();

        animatorSet.addListener((new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (needToStartGuide) {
                    super.onAnimationEnd(animation);

                    coleccionablesBinding.pulseImage.setVisibility(VISIBLE);
                    coleccionablesBinding.textStep.setVisibility(VISIBLE);
                }
            }
        }));
    }

    private void initalizeInfo(View view) {

        getSupportActionBar().show();
        infoBinding.getRoot().setVisibility(VISIBLE);
        coleccionablesBinding.getRoot().setVisibility(GONE);

        soundCambioFragment();//reproducir sonido

        infoBinding.nextToResumen.setOnClickListener(this::initalizeResumen);
        infoBinding.exitGuide.setOnClickListener(this::onExitGuide);


        ObjectAnimator scaleX = ObjectAnimator.ofFloat(
                infoBinding.pulseImage, "scaleX", 1f, 0.5f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(
                infoBinding.pulseImage, "scaleY", 1f, .5f);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(
                infoBinding.textStep, "alpha", 0f, 1f);
        scaleX.setRepeatCount(3);
        scaleY.setRepeatCount(3);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleX).with(scaleX).before(fadeIn);
        animatorSet.setDuration(1000);
        animatorSet.start();

        animatorSet.addListener((new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (needToStartGuide) {
                    super.onAnimationEnd(animation);

                    coleccionablesBinding.pulseImage.setVisibility(VISIBLE);
                    coleccionablesBinding.textStep.setVisibility(VISIBLE);
                }
            }
        }));
    }

    private void initalizeResumen(View view) {

        infoBinding.getRoot().setVisibility(GONE);
        resumenBinding.guideResumenLayout.setVisibility(VISIBLE);

        soundCambioFragment();

        // Cargar la transición desde el recurso XML
        Transition fade = TransitionInflater.from(MainActivity.this)
                .inflateTransition(R.transition.fade);
        // Iniciar la transición desde el anterior layout
        TransitionManager.beginDelayedTransition(findViewById(R.id.guideColeccionablesLayout), fade);


        resumenBinding.startGame.setOnClickListener(this::onExitGuide);


    }


    private void onExitGuide(View view) {

        soundExit();
        //iniciamos en la pantalla personajes
        navController.navigate(R.id.navigation_characters);


        needToStartGuide = false;

        //Desbloqueamos bottom navigation
        binding.navView.setClickable(true);
        binding.navView.setFocusable(true);
        binding.navView.setEnabled(true);

        //hacemos invisible los layouts de la guia
        personajesBinding.guidePersonajesLayout.setVisibility(GONE);
        mundosBinding.guideMundosLayout.setVisibility(GONE);
        coleccionablesBinding.guideColeccionablesLayout.setVisibility(GONE);
        infoBinding.guideInfoLayout.setVisibility(GONE);
        resumenBinding.guideResumenLayout.setVisibility(GONE);
    }


    private void soundExit() {
        //reproducir sonido salir de la guia
        MediaPlayer mp = MediaPlayer.create(this, R.raw.exit);
        mp.start();

        //liberar recursos del MediaPlayer después de reproducir el sonido
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }

    private void soundCambioFragment() {

        MediaPlayer mp = MediaPlayer.create(this, R.raw.cambio_de_fragment);
        mp.start();

        //liberar recursos del MediaPlayer después de reproducir el sonido
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }

    public void positionPulse(float positionX, float positionY) {

        final ImageView pulseImage = (ImageView) findViewById(R.id.pulseImage);

        // Obtener las dimensiones de la pantalla
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;

        // Obtener los parámetros de diseño actuales de pulseImage
        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) pulseImage.getLayoutParams();

        // Calcular la posición en función de las dimensiones de la pantalla
        lp.leftMargin = (int) (screenWidth * positionX);
        lp.topMargin = (int) (screenHeight * positionY);

        // Aplicar los nuevos parámetros de diseño a pulseImage
        pulseImage.setLayoutParams(lp);
    }


}