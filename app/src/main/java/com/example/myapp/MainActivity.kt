package com.example.myapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.simpleFab.setOnClickListener {
            Toast.makeText(this, "simpleFab clicked", Toast.LENGTH_SHORT).show()
        }
        binding.showButton.setOnClickListener{
            binding.simpleFab.show()
        }
        binding.hideButton.setOnClickListener{
            binding.simpleFab.hide()
        }
        var i = 1
        binding.changeButton.setOnClickListener{
            if (i++ and 1 == 1)
                binding.simpleFab.setLayeredAssets(R.drawable.fab_orange_background, R.drawable.ic_add_24)
            else
                binding.simpleFab.setLayeredAssets(R.drawable.fab_purple_background, R.drawable.ic_add_24)

        }
    }
}
