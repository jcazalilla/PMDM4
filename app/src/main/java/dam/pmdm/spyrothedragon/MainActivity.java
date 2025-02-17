package dam.pmdm.spyrothedragon;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.viewbinding.ViewBinding;

import dam.pmdm.spyrothedragon.databinding.ActivityMainBinding;
import dam.pmdm.spyrothedragon.databinding.GuideBienvenidaBinding;
import dam.pmdm.spyrothedragon.databinding.GuideColeccionablesBinding;
import dam.pmdm.spyrothedragon.databinding.GuideInfoBinding;
import dam.pmdm.spyrothedragon.databinding.GuideMundosBinding;
import dam.pmdm.spyrothedragon.databinding.GuidePersonajesBinding;
import dam.pmdm.spyrothedragon.databinding.GuideResumenBinding;

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
            if (destination.getId() == R.id.navigation_characters ||
                    destination.getId() == R.id.navigation_worlds ||
                    destination.getId() == R.id.navigation_collectibles) {
                // Para las pantallas de los tabs, no queremos que aparezca la flecha de atrás
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            } else {
                // Si se navega a una pantalla donde se desea mostrar la flecha de atrás, habilítala
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        });


        initializeBienvenida();


    }


    //calcula el ancho de pantalla del dsipositivo en píxeles(float)
    private float getScreenWidth() {
        // Obtenemos el ancho de la pantalla
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float screenWidth = displayMetrics.widthPixels;

        return screenWidth;
    }

    private void initializeBienvenida() {

        getSupportActionBar().hide();//ocultamos Actionbar en la guide

        bienvenidaBinding.btncomenzarGuide.setOnClickListener(this::initializeGuide);

        bienvenidaBinding.guidebienvenidaLayout.setVisibility(VISIBLE);
    }


    //inicia guia
    private void initializeGuide(View view) {


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


        ObjectAnimator scaleX = ObjectAnimator.ofFloat(
                personajesBinding.pulseImage, "scaleX", 1f, 0.5f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(
                personajesBinding.pulseImage, "scaleY", getScreenWidth() / 3f, .5f);
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

        mundosBinding.nextToColeccionables.setOnClickListener(this::initalizeColeccionables);
        mundosBinding.exitGuide.setOnClickListener(this::onExitGuide);

        navController.navigate(R.id.navigation_worlds);//Mostramos por debajo el fragment worlds
        personajesBinding.getRoot().setVisibility(GONE);//ocultamos guide del que viene
        mundosBinding.getRoot().setVisibility(VISIBLE);//hacemos visible la guide a la que llegamos


        ObjectAnimator scaleX = ObjectAnimator.ofFloat(
                mundosBinding.pulseImage, "scaleX", 1f, 0.5f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(
                mundosBinding.pulseImage, "scaleY", getScreenWidth() / 2f, .5f);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(
                mundosBinding.textStep, "alpha", 0f, 1f);

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

                    mundosBinding.pulseImage.setVisibility(VISIBLE);
                    mundosBinding.textStep.setVisibility(VISIBLE);
                }
            }
        }));
    }


    private void initalizeColeccionables(View view) {

        coleccionablesBinding.nextToResumen.setOnClickListener(this::initalizeInfo);
        coleccionablesBinding.exitGuide.setOnClickListener(this::onExitGuide);


        navController.navigate(R.id.navigation_collectibles);//mostramos el fragment collectibles
        mundosBinding.getRoot().setVisibility(GONE);//ocultamos guide del que viene
        coleccionablesBinding.getRoot().setVisibility(VISIBLE);//hacemos visible la guide a la que llegamos


        ObjectAnimator scaleX = ObjectAnimator.ofFloat(
                coleccionablesBinding.pulseImage, "scaleX", 1f, 0.5f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(
                coleccionablesBinding.pulseImage, "scaleY", getScreenWidth(), .5f);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(
                coleccionablesBinding.textStep, "alpha", 0f, 1f);

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

                    coleccionablesBinding.pulseImage.setVisibility(VISIBLE);
                    coleccionablesBinding.textStep.setVisibility(VISIBLE);
                }
            }
        }));

    }

    private void initalizeInfo(View view) {

        getSupportActionBar().show();

        infoBinding.nextToResumen.setOnClickListener(this::initalizeResumen);
        infoBinding.exitGuide.setOnClickListener(this::onExitGuide);

        infoBinding.getRoot().setVisibility(VISIBLE);
        coleccionablesBinding.getRoot().setVisibility(GONE);




        ObjectAnimator scaleX = ObjectAnimator.ofFloat(
                infoBinding.pulseImage, "scaleX", 1f, 0.5f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(
                infoBinding.pulseImage, "scaleY", getScreenWidth(), .5f);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(
                infoBinding.textStep, "alpha", 0f, 1f);
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

                    coleccionablesBinding.pulseImage.setVisibility(VISIBLE);
                    coleccionablesBinding.textStep.setVisibility(VISIBLE);
                }
            }
        }));

        showInfoDialog();
    }

    private void initalizeResumen(View view) {

        getSupportActionBar().hide();

        resumenBinding.startGame.setOnClickListener(this::onExitGuide);

        infoBinding.getRoot().setVisibility(GONE);
        resumenBinding.guideResumenLayout.setVisibility(VISIBLE);
    }


    private void onExitGuide(View view) {

        //volvemos a mostrar ActionBar después de la guide
        getSupportActionBar().show();

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


    private boolean selectedBottomMenu(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.nav_characters)
            navController.navigate(R.id.navigation_characters);
        else if (menuItem.getItemId() == R.id.nav_worlds)
            navController.navigate(R.id.navigation_worlds);
        else
            navController.navigate(R.id.navigation_collectibles);
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


}