package com.example.mykidsreg.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mykidsreg.R

class HelpAndContactFragment : Fragment() {

    private lateinit var phoneNumberTextView: TextView
    private lateinit var emailTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_help_and_contact, container, false)

        // Initialize views
        phoneNumberTextView = view.findViewById(R.id.textViewPhoneNumber)
        emailTextView = view.findViewById(R.id.textViewEmail)

        // Set click listeners
        phoneNumberTextView.setOnClickListener { dialPhoneNumber(phoneNumberTextView.text.toString()) }
        emailTextView.setOnClickListener { sendEmail(emailTextView.text.toString()) }

        return view
    }

    private fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun sendEmail(emailAddress: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$emailAddress")
        }
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
    }
}
