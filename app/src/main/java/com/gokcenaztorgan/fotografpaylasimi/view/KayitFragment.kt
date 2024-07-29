package com.gokcenaztorgan.fotografpaylasimi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.gokcenaztorgan.fotografpaylasimi.databinding.FragmentKayitBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth

class KayitFragment : Fragment() {

    private var _binding: FragmentKayitBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = com.google.firebase.ktx.Firebase.auth


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentKayitBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.kayitButton.setOnClickListener{
            kayitOl(it)
        }
        binding.girisButton.setOnClickListener{
            girisYap(it)
        }

        val kullanici = auth.currentUser
        if(kullanici != null){
            val action = KayitFragmentDirections.actionKayitFragmentToAnasayfaFragment()
            Navigation.findNavController(requireView()).navigate(action)
        }
    }

    fun kayitOl(view: View) {

        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty())
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val action = KayitFragmentDirections.actionKayitFragmentToAnasayfaFragment()
                    Navigation.findNavController(requireView()).navigate(action)
                }


            }.addOnFailureListener { e ->
                Toast.makeText(requireContext(), e.localizedMessage,Toast.LENGTH_LONG).show()
            }
    }
    fun girisYap(view: View){
        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()){
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                val action = KayitFragmentDirections.actionKayitFragmentToAnasayfaFragment()
                Navigation.findNavController(requireView()).navigate(action)
            }.addOnFailureListener { e ->
                Toast.makeText(requireContext(), e.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}