/*
 * Copyright (c) 2026 Your(kana-tutor.com
 * * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.example.myapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.simpleFab1.setOnClickListener {
            Toast.makeText(this, "simpleFab clicked", Toast.LENGTH_SHORT).show()
        }
        binding.showButton1.setOnClickListener{
            binding.simpleFab1.visibility = View.VISIBLE
        }
        binding.hideButton1.setOnClickListener{
            binding.simpleFab1.visibility = View.INVISIBLE
        }
        savedInstanceState?.let {
            val visibility = savedInstanceState.getInt("VISIBILITY_1")
            binding.simpleFab1.visibility = visibility
        }

        binding.simpleFab2.setOnClickListener {
            Toast.makeText(this, "simpleFab clicked", Toast.LENGTH_SHORT).show()
        }
        binding.showButton2.setOnClickListener{
            binding.simpleFab2.visibility = View.VISIBLE
        }
        binding.hideButton2.setOnClickListener{
            binding.simpleFab2.visibility = View.INVISIBLE
        }
        savedInstanceState?.let {
            val visibility = savedInstanceState.getInt("VISIBILITY_2")
            binding.simpleFab2.visibility = visibility
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save whether the view is currently visible
        outState.putInt("VISIBILITY_1", binding.simpleFab1.visibility)
        outState.putInt("VISIBILITY_2", binding.simpleFab2.visibility)
    }
}
