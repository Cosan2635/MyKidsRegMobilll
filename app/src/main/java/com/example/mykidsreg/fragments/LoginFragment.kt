package com.example.mykidsreg.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mykidsreg.R
import com.example.mykidsreg.databinding.FragmentLoginBinding
import com.example.mykidsreg.models.UserType
import com.example.mykidsreg.viewmodels.UserViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var editTextPassword: EditText
    private lateinit var passwordToggleImageView: ImageView
    private var isPasswordVisible: Boolean = false

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using DataBindingUtil
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        val view = binding.root

        // Find views for password visibility toggle
        editTextPassword = view.findViewById(R.id.editTextPassword)
        passwordToggleImageView = view.findViewById(R.id.passwordToggleImageView)

        passwordToggleImageView.setOnClickListener {
            togglePasswordVisibility()
        }

        // Initialize ViewModel
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // Observe errorMessageLiveData
        userViewModel.errorMessageLiveData.observe(viewLifecycleOwner, Observer { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                Log.e("LOGIN", "Error message: $errorMessage")
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        })

        // Observe loginResult
        userViewModel.loginResult.observe(viewLifecycleOwner, Observer { user ->
            Log.d("LOGIN", "User: ${user}")
            if (user != null) {
                Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()

                // Save user ID in SharedPreferences
                val sharedPref = requireActivity().getSharedPreferences("myKidsReg", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putInt("userId", user.user_Id)
                    apply()
                }

                navigateBasedOnUserType(user.usertype?.value)
            } else {
                Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT).show()
            }
        })

        // Set background drawable
        view.background = requireContext().getDrawable(R.drawable.app_background)

        // Customize button appearance
        binding.buttonLogin.setBackgroundColor(Color.WHITE)

        binding.buttonLogin.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()
            if (username.isNotEmpty() && password.isNotEmpty()) {
                userViewModel.login(username, password)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Indtast brugernavn og adgangskode",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.forgotPasswordButton.setOnClickListener {
            // Handle forgot password functionality
            Toast.makeText(requireContext(), "Glemt adgangskode?", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Hide password
            editTextPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            passwordToggleImageView.setImageResource(R.drawable.icon_password_eye)
        } else {
            // Show password
            editTextPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            passwordToggleImageView.setImageResource(R.drawable.icon_password_eye)
        }
        // Move the cursor to the end of the text
        editTextPassword.setSelection(editTextPassword.text.length)
        isPasswordVisible = !isPasswordVisible
    }

    private fun navigateBasedOnUserType(userType: Int?) {
        when (userType) {
            UserType.Padagogue.value -> {
                findNavController().navigate(R.id.action_loginFragment_to_secondFragment)
            }
            UserType.Parent.value -> {
                findNavController().navigate(R.id.action_loginFragment_to_firstFragment)
            }
            else -> {
                Toast.makeText(requireContext(), "Unsupported user type", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
