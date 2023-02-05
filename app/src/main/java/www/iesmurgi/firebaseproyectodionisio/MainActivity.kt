package www.iesmurgi.firebaseproyectodionisio

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import www.iesmurgi.firebaseproyectodionisio.databinding.ActivityMainBinding

class  MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    companion object {
        private const val RC_SIGN_IN = 423
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.fondo.background = resources.getDrawable(R.drawable.fondo)
        var email:Editable
        var pass: Editable
        val btnIniciar = binding.btnSesion
        btnIniciar.setOnClickListener {
            email = binding.tvUser.text
            pass = binding.tvPass.text
            iniciarSesion(email.toString(), pass.toString())
        }

        binding.tvOlvidar.setOnClickListener {
            var intent = Intent(this,ContraOlvidada::class.java)
            startActivity(intent)
        }

        val tvRegistro = binding.tvRegistrar
        tvRegistro.setOnClickListener {
            var intent = Intent(this,Register::class.java)
            startActivity(intent)
        }

        val btnGoogle = binding.imagenIniciarConGoogle
        btnGoogle.setOnClickListener { iniciarSesionGoogle() }

    }

    private fun iniciarSesionGoogle() {
        val providerGoogle = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providerGoogle).build(),
            Companion.RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Companion.RC_SIGN_IN){
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK){
                val user = FirebaseAuth.getInstance().currentUser

                startActivity(Intent(this,PerfilActivity::class.java))
                finish()

            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            startActivity(Intent(this, PerfilActivity::class.java))
        }
    }

    fun iniciarSesion(email: String, clave: String) {

        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            email,
            clave
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                abrirPerfil()
            } else {
                Toast.makeText(this, "Intento de login fallido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun abrirPerfil() {
        startActivity(Intent(this, PerfilActivity::class.java))
        finish()
    }
}