package com.application.prueba.iu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.application.prueba.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private val viewModel:HomeViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnIngresar.setOnClickListener {
                viewModel.login(binding.etUsuario.text, binding.etPassword.text)
        }

        viewModel.authResponse.observe(this, {
            //binding.progressBar.visible(it is Resource.Loading)

            if(it)
                Toast.makeText(this, "No ingresaste tu usuario y/o contraseña", Toast.LENGTH_SHORT).show()
            else {
                val intent = Intent(this,HomeActivity::class.java)
                startActivity(intent)

            }


        })
    }
}