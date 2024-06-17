package com.example.mykidsreg.fragments

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.mykidsreg.R

class FooterNavigation : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.footer_navigation)

        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)

        button1.setOnClickListener {
            Toast.makeText(this, "Button 1 clicked", Toast.LENGTH_SHORT).show()
            // Handle click for button 1
        }

        button2.setOnClickListener {
            Toast.makeText(this, "Button 2 clicked", Toast.LENGTH_SHORT).show()
            // Handle click for button 2
        }

        button3.setOnClickListener {
            Toast.makeText(this, "Button 3 clicked", Toast.LENGTH_SHORT).show()
            // Handle click for button 3
        }

        button4.setOnClickListener {
            Toast.makeText(this, "Button 4 clicked", Toast.LENGTH_SHORT).show()
        findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.help_and_contact)
        }
    }
}
