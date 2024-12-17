package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {

    private lateinit var edtEmail1: EditText
    private lateinit var edtPassword1: EditText
    private lateinit var edtPassword2: EditText
    private lateinit var editText: EditText
    private lateinit var sgn: Button
private lateinit var mDbRef:DatabaseReference
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Initialize views

        edtEmail1 = findViewById(R.id.edtEmail1)
        edtPassword2 = findViewById(R.id.edtPassword2)
        edtPassword1 = findViewById(R.id.edtPassword1)
        editText = findViewById(R.id.editText)
        sgn = findViewById(R.id.sgn)

        sgn.setOnClickListener {
            val email = edtEmail1.text.toString().trim()
            val password = edtPassword1.text.toString().trim()
            val conpassword = edtPassword2.text.toString().trim()
            val name = editText.text.toString().trim()

            if (email.isEmpty()) {
                edtEmail1.error = "Email is required"
                edtEmail1.requestFocus()
                return@setOnClickListener
            }

            if (name.isEmpty()) {
                editText.error = "Name is required"
                editText.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                edtPassword1.error = "Password is required"
                edtPassword1.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 6) {
                edtPassword1.error = "Password must be at least 6 characters"
                edtPassword1.requestFocus()
                return@setOnClickListener
            }

            if (password != conpassword) {
                edtPassword2.error = "Passwords do not match"
                edtPassword2.requestFocus()
                return@setOnClickListener
            }

            // Firebase Authentication for new user
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        //add user to the database
                        addUserToDatabase(name,email,auth.currentUser?.uid!!)


                        Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, signinActivity::class.java)
                        startActivity(intent)
                        finish() // Close the signup activity
                    } else {
                        Toast.makeText(
                            this,
                            "Error occurred: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }




                }
        }
    }


    private fun addUserToDatabase(name:String,email:String,uid:String)
    {
mDbRef=FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name,email,uid))
    }
}
