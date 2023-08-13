package com.example.hub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hub.databinding.ActivityNavigationHeaderBinding
import com.google.firebase.auth.FirebaseAuth

class NavigationHeaderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavigationHeaderBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationHeaderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
        //
    }

    private fun checkUser() {
        //get current user
        val firebaseUser=firebaseAuth.currentUser
        if(firebaseUser == null){
            //not logged in,user can stay in
            binding.navUserName.text="Not Logged In"
        }
        else {//logged in , get and show user info
            val email= firebaseUser.email
            //set textview of toolbar
            binding.navUserName.text=email
        }
    }
}

