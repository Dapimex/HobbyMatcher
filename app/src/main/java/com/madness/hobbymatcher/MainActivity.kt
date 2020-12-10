package com.madness.hobbymatcher

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.madness.hobbymatcher.loginmanager.view.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

        bottom_navigation.setupWithNavController(
            Navigation.findNavController(
                this,
                R.id.navHostFragment
            )
        )
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            NavigationUI.onNavDestinationSelected(
                item,
                Navigation.findNavController(this, R.id.navHostFragment)
            )
        }
    }
}