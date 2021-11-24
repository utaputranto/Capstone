package com.utaputranto.capstone.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.utaputranto.capstone.R
import com.utaputranto.capstone.databinding.ActivityMainBinding
import com.utaputranto.core.base.ui.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>({ ActivityMainBinding.inflate(it)}) {

    private lateinit var navController: NavController

    override fun ActivityMainBinding.onCreate(savedInstanceState: Bundle?) {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.navView.setupWithNavController(navController)
    }

    override fun observeViewModel() {}
}