package com.wenitech.cashdaily.framework.features

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseAuth.getInstance
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.commons.AuthenticationStatus
import com.wenitech.cashdaily.commons.Resource
import com.wenitech.cashdaily.databinding.ActivityNavMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainNavActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var authStateListener: AuthStateListener
    private lateinit var _binding: ActivityNavMainBinding
    private val binding get() = _binding

    private val viewModel by viewModels<MainViewModel>()
    private var auth: FirebaseAuth = getInstance()

    /**
     * On create Method
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNavMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupFirebaseAuthListener()

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.clientFragment,
                R.id.cajaFragment,
                R.id.informeFragment,
                R.id.fragmentLogin
            ),
            binding.drawerLayoutMainActivity
        )

        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.homeFragment || destination.id == R.id.clientFragment || destination.id == R.id.cajaFragment || destination.id == R.id.informeFragment) {
                binding.appBarLayout2.visibility = View.VISIBLE
                binding.drawerLayoutMainActivity.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                binding.bottomNavigationView.visibility = View.VISIBLE
            } else if (destination.id == R.id.fragmentLogin) {
                binding.appBarLayout2.visibility = View.GONE
                binding.drawerLayoutMainActivity.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                binding.bottomNavigationView.visibility = View.GONE
            }
        }

        setupAppbarConfiguration(navController, appBarConfiguration)
        setupNavigationView(navController)
        setupBottomNavigation(navController)

        observerViewModel()
    }

    private fun setupFirebaseAuthListener() {
        authStateListener = AuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser == null) {
                viewModel.setStateAuth(AuthenticationStatus.UNAUTHENTICATED)
                navigateToLogin()
            } else {
                viewModel.setStateAuth(AuthenticationStatus.AUTHENTICATED)
            }
        }
    }

    private fun setupAppbarConfiguration(
        navController: NavController,
        appBarConfiguration: AppBarConfiguration
    ) {
        NavigationUI.setupWithNavController(binding.toolbarMain, navController, appBarConfiguration)
    }

    private fun setupNavigationView(navController: NavController) {
        binding.navigationViewMainActivity.setupWithNavController(navController)

        val navHeader =
            binding.navigationViewMainActivity.getHeaderView(0) //instance of header drawer
        val profilePicture = navHeader.findViewById<ImageView>(R.id.image_view_drawer_perfil)
        val userName = navHeader.findViewById<TextView>(R.id.text_view_drawer_name)
        val emailAddress = navHeader.findViewById<TextView>(R.id.text_view_drawer_email)

        profilePicture.setOnClickListener {
            openDialogLSessionClose()
        }

        viewModel.user.observe(this, { resource ->
            when (resource) {
                is Resource.Failure -> {
                    userName.text = resource.throwable.message
                    emailAddress.text = ""
                }
                is Resource.Loading -> {
                    userName.text = getString(R.string.default_loading)
                    emailAddress.text = getString(R.string.default_loading)
                }
                is Resource.Success -> {
                    userName.text = resource.data.fullName
                    emailAddress.text = resource.data.email
                }
            }
        })
    }

    private fun setupBottomNavigation(navController: NavController) {
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    private fun observerViewModel() {
        viewModel.stateAuth.observe(this, { status ->
            when (status!!) {
                AuthenticationStatus.AUTHENTICATED -> {
                    viewModel.getProfile(auth.uid.toString())
                }
                AuthenticationStatus.AUTHENTICATING -> {
                    // Todo: Authenticating
                }
                AuthenticationStatus.UNAUTHENTICATED -> {
                    navigateToLogin()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val restValue = super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_main_toolbar, menu)
        return restValue
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.fragment)) ||
                super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun onStart() {
        super.onStart()
        // check if user is login
        val currentUser = auth.currentUser
        if (currentUser == null) {
            navigateToLogin()
        }
        auth.addAuthStateListener(authStateListener)
    }

    override fun onStop() {
        super.onStop()
        auth.removeAuthStateListener(authStateListener)
    }

    private fun openDialogLSessionClose() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.dialog_session_close_title))
            .setMessage(getString(R.string.dialog_session_close_message))
            .setPositiveButton(getString(R.string.dialog_session_close_positive_button)) { _, _ -> signOut() }
            .setNegativeButton(getString(R.string.dialog_session_close_negative_button)) { _, _ -> }
            .show()
    }

    private fun navigateToLogin() {
        navController.navigate(R.id.action_global_fragmentLogin)
    }

    private fun signOut() {
        auth.signOut()
    }

}