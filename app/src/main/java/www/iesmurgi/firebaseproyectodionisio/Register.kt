package www.iesmurgi.firebaseproyectodionisio

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import www.iesmurgi.firebaseproyectodionisio.databinding.ActivityRegisterBinding
import java.lang.Exception

class Register : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var email: String
        var pass: String
        binding.btnRegistro.setOnClickListener {
            if (binding.etContra.text.toString() == binding.etContraConfirmar.text.toString()){
                if (binding.etContra.text.length < 6){
                    Toast.makeText(this,resources.getString(R.string.contra_corta),Toast.LENGTH_SHORT).show()
                }else {
                    email = binding.etUsuarioRegistro.text.toString()
                    pass = binding.etContra.text.toString()
                    println("email $email pass $pass")
                    crearUsuario(email,pass)
                }
            }
            else{
                Toast.makeText(this,resources.getString(R.string.contraFallida),Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun writeNewUser(email: String) {
        val db = Firebase.firestore

        val data = hashMapOf(
            "email" to email,
            "usuario" to "nouser",
            "nacionalidad" to "nonacionalidad",
            "edad" to 0
        )

        db.collection("user").document(email)
            .set(data)
            .addOnSuccessListener { Log.d(ContentValues.TAG,"DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writting document", e) }
    }

    private fun showErrorAlert(exception: Exception?) {

        Toast.makeText(this, "Registro fallido", Toast.LENGTH_SHORT).show()
    }

    fun abrirPerfil() {
        startActivity(Intent(this, PerfilActivity::class.java))
        finish()
    }

    fun crearUsuario(email: String, clave: String) {


        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
            email,
            clave
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                writeNewUser(email)
                abrirPerfil()
            } else {
                showErrorAlert(it.exception)
            }
        }

    }

}