package dam.pmdm.spyrothedragon;

import static android.view.View.VISIBLE;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
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

import dam.pmdm.spyrothedragon.databinding.ActivityMainBinding;
import dam.pmdm.spyrothedragon.databinding.BienvenidaBinding;
import dam.pmdm.spyrothedragon.databinding.GuidePersonajesBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    NavController navController = null;
    private GuidePersonajesBinding personajesBinding;
    private BienvenidaBinding bienvenidaBinding;

    private boolean needToStartGuide = true;

    private TransitionDrawable transitionComienzo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //inicializamos guide layout para manejarlo
        personajesBinding =binding.includeLayout;
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



        initializeGuide();

    }




    //inicia guia
    private void initializeGuide() {



        personajesBinding.nextToMundos.setOnClickListener(this::onExitGuide);

        if (needToStartGuide) {
            //bloqueamos bottom navigation
            binding.navView.setClickable(false);
            binding.navView.setFocusable(false);
            binding.navView.setEnabled(false);
            //hacemos visible la guia
            personajesBinding.guidePersonajesLayout.setVisibility(View.VISIBLE);
        }
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(
                personajesBinding.pulseImagePersonajes, "scaleX", 1f, 0.5f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(
                personajesBinding.pulseImagePersonajes, "scaleY", 1f, .5f);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(
                personajesBinding.textStep, "alpha", 0f, 1f);

        scaleX.setRepeatCount(3);
        scaleY.setRepeatCount(3);
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.play(scaleX).with(scaleX).before(fadeIn);
        animatorSet.setDuration(1500);
        animatorSet.start();
        animatorSet.addListener((new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
               if(needToStartGuide){
                   super.onAnimationEnd(animation);


                   personajesBinding.pulseImagePersonajes.setVisibility(View.GONE);
                   personajesBinding.textStep.setVisibility(VISIBLE);
               }
            }
        }));
    }


    private void onExitGuide(View view) {

        needToStartGuide = false;

        //Desbloqueamos bottom navigation
        binding.navView.setClickable(true);
        binding.navView.setFocusable(true);
        binding.navView.setEnabled(true);

        //hacemos invisible la guia
        personajesBinding.guidePersonajesLayout.setVisibility(View.INVISIBLE);
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