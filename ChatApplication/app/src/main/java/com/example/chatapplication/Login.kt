package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {

    //kullanacagim degiskenlerin hangi tipte oldugunu tanimliyorum..
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: Button
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //supportActionBar?.hide()

        //kimllik dogrulama islemleri icin firebaseAuth sinifi kullanilir.
        //getInstance metoduyla da bu sinifin referans oldugu nesneleri kullanabiliriz.
        mAuth = FirebaseAuth.getInstance()

        //id'sini verdigim objelere gore atama yapiyorum.
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignUp)

        //setOnClickListener: butona tiklandiginda alacagi aksiyomlari tanimlar:
        //signup butonuna bastiginda SignUp sayfasina yonlendirir:
        btnSignUp.setOnClickListener {
            //sayfalar arasi gecis icin 'intent' kullanilir.
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            login(email, password)
        }

    }

    private fun login(email: String, password: String) {
        //logic for logging user:
        //firebase database'ine gidip email ve password'un dogrulugunu kontrol ediyoruz:
        //addOnCompleteListener dinleyicisi ile sonucta yapilacak islemleri yaziyoruz.
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //kullanici girisi
                    val intent = Intent(this@Login, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                    //login islemi basarili degilse bir Toast messaji doner:
                } else {
                    Toast.makeText(this@Login, "Kullanıcı Bulunamadı", Toast.LENGTH_LONG).show()
                }
            }
    }
}