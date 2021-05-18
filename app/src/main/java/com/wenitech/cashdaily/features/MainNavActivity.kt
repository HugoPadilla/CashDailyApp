package com.wenitech.cashdaily.features

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.databinding.ActivityNavMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainNavActivity : AppCompatActivity() {
    private var _binding: ActivityNavMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var mAuth: FirebaseAuth? = null
    private val mUser: FirebaseUser? = null
    private var authStateListener: AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNavMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        initFirebaseAuth()
        initNavController()
        setupFirebaseAuthListener()
        setupWithNavController()
        setupOnDestinationListenerNavController()
    }

    private fun initNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun initFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance()
    }


    private fun setupOnDestinationListenerNavController() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.homeFragment || destination.id == R.id.clientFragment || destination.id == R.id.cajaFragment || destination.id == R.id.informeFragment) {
                binding.bottomNavigationView.visibility = View.VISIBLE
            } else {
                binding.bottomNavigationView.visibility = View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_notifications -> {
                Toast.makeText(this, "Notificacion", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_item_tema -> {
                Toast.makeText(this, "Temas", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_item_exit -> {
                Toast.makeText(this, "Exit Aplication", Toast.LENGTH_SHORT).show()
                true
            }
            else ->
                super.onOptionsItemSelected(item)

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration!!)
    }

    override fun onStart() {
        super.onStart()
        // check if user is login
        if (mAuth!!.currentUser == null) {
            updateUi()
        } else {
            mAuth!!.addAuthStateListener(authStateListener)
        }
    }

    override fun onStop() {
        super.onStop()
        mAuth!!.removeAuthStateListener(authStateListener)
    }

    private fun updateUi() {
        navController!!.navigate(R.id.activityNavLogin)
        finish()
    }

    private fun setupFirebaseAuthListener() {
        authStateListener = AuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser == null) {
                updateUi()
            }
        }
    }

    private fun setupWithNavController() {

        appBarConfiguration = AppBarConfiguration.Builder(R.id.homeFragment, R.id.clientFragment, R.id.cajaFragment, R.id.informeFragment)
                .setDrawerLayout(binding.drawerLayoutMainActivity)
                .build()

        NavigationUI.setupWithNavController(binding.toolbarMain, navController, appBarConfiguration) //setting Toolbar

        NavigationUI.setupWithNavController(binding.navigationViewMainActivity, navController) //setting navigation view

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController) // setting bottomNavigationView

        inicializarDatosNavigationDrawerHeader()
    }

    private fun inicializarDatosNavigationDrawerHeader() {
        val navHeader = binding!!.navigationViewMainActivity.getHeaderView(0) //instance of header drawer
        val imageView = navHeader.findViewById<ImageView>(R.id.image_view_drawer_perfil)
        val textViewDrawerNombre = navHeader.findViewById<TextView>(R.id.text_view_drawer_name)
        val textViewDrawerCorreo = navHeader.findViewById<TextView>(R.id.text_view_drawer_email)

        imageView.setOnClickListener {
            openDialogLSessionClose()
        }

        if (mUser != null) {
            val nombreUsuario = mUser.displayName
            val correoUsuario = mUser.email
            textViewDrawerNombre.text = nombreUsuario
            textViewDrawerCorreo.text = correoUsuario
        }
    }

    private fun openDialogLSessionClose() {
        MaterialAlertDialogBuilder(this)
                .setTitle("Cerrar sesion")
                .setMessage("Estas a punto de salir de tu cuenta y es posible que exista alguna informacion sin guardar en la nube. Asegurate de haber sincronizado todo antes de salir.")
                .setPositiveButton("Cerrar sesion") { dialog, which -> signOut() }
                .setNegativeButton("Cancelar") { dialog, which -> }
                .show()
    }

    private fun signOut() {
        //todoViewModel.cajaLiveData.removeObservers(this);
        mAuth!!.signOut()
        finish()
    }

}