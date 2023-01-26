package www.iesmurgi.firebaseproyectodionisio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import www.iesmurgi.firebaseproyectodionisio.databinding.ActivityPerfilBinding

class PerfilActivity : AppCompatActivity() {

    val auth:FirebaseAuth = FirebaseAuth.getInstance()
    val user:FirebaseUser? = auth.currentUser

    private lateinit var binding: ActivityPerfilBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (user != null){
            setUp(user?.email.toString())
        }

        var btnRestablecerContra = binding.btnRecuperarContra
        btnRestablecerContra.setOnClickListener {
            restablecerClave()
        }

        var btnBorrarUsuario = binding.btnBorrarUser
        btnBorrarUsuario.setOnClickListener {
            borrarDatosUser()
            borrarUsuario()
        }

    }

    private fun borrarDatosUser() {
        Firebase.firestore.collection("user").document(user?.email.toString()).delete()
    }

    private fun borrarUsuario() {
        user?.delete()?.addOnCompleteListener {

            if (it.isSuccessful){
                startActivity(Intent(this,MainActivity::class.java))
                Toast.makeText(this,this.resources.getString(R.string.borrar_completado),Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()

            }

        }
    }

    private fun restablecerClave() {
        auth.sendPasswordResetEmail(user?.email.toString()).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(this,this.resources.getString(R.string.msg_restablecer),Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUp(email: String) {

        var correo = binding.tvCorreo
        correo.text = email

        binding.btnCerrar.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this,MainActivity::class.java))
        }

    }
}