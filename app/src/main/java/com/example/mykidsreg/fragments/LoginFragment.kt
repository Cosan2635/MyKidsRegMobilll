package com.example.mykidsreg.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mykidsreg.R
import com.example.mykidsreg.databinding.FragmentLoginBinding
import com.example.mykidsreg.services.ApiClient
import com.example.mykidsreg.services.LoginRequest
import com.example.mykidsreg.services.LoginResponse
import com.example.mykidsreg.models.Student
import com.example.mykidsreg.viewmodels.StudentViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var studentViewModel: StudentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        val view = binding.root

        // Initialize ViewModel
        studentViewModel = ViewModelProvider(requireActivity()).get(StudentViewModel::class.java)

        // Set background drawable
        view.background = requireContext().getDrawable(R.drawable.app_background)

        // Customize button appearance
        binding.buttonLogin.setBackgroundColor(Color.WHITE)

        binding.buttonLogin.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()
            if (username.isNotEmpty() && password.isNotEmpty()) {
                login(username, password)
            } else {
                Toast.makeText(requireContext(), "Indtast brugernavn og adgangskode", Toast.LENGTH_SHORT).show()
            }
        }

        binding.forgotPasswordButton.setOnClickListener {
            // Handle forgot password functionality
            Toast.makeText(requireContext(), "Glemt adgangskode?", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun login(username: String, password: String) {
        val request = LoginRequest(username, password)
        ApiClient.apiService.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    // Handle successful login
                    Toast.makeText(requireContext(), "Login succesfuld", Toast.LENGTH_SHORT).show()

                    // Fetch students for the logged-in user
                    fetchStudents()
                } else {
                    // Handle login failure
                    Toast.makeText(requireContext(), "Login mislykket", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Handle API call failure
                Toast.makeText(requireContext(), "Netværksfejl", Toast.LENGTH_SHORT).show()
                Log.e("LoginFragment", "API call failed", t)
            }
        })
    }

    private fun fetchStudents() {
        ApiClient.apiService.getStudents().enqueue(object : Callback<List<Student>> {
            override fun onResponse(call: Call<List<Student>>, response: Response<List<Student>>) {
                if (response.isSuccessful && response.body() != null) {
                    val students = response.body()!!
                    studentViewModel.setStudents(students)

                    // Navigate to FirstFragment
                    findNavController().navigate(R.id.action_loginFragment_to_firstFragment)
                } else {
                    Toast.makeText(requireContext(), "Kunne ikke hente student data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Student>>, t: Throwable) {
                Toast.makeText(requireContext(), "Netværksfejl ved hentning af student data", Toast.LENGTH_SHORT).show()
                Log.e("LoginFragment", "API call failed", t)
            }
        })
    }
}
