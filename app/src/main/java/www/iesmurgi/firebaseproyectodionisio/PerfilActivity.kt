package www.iesmurgi.firebaseproyectodionisio

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
            setUp()
        }

        var btnBorrarUsuario = binding.btnBorrarUser
        btnBorrarUsuario.setOnClickListener {
            borrarDatosUser()
            borrarUsuario()
        }

        binding.btnModificar.setOnClickListener {
            var intent = Intent(this,Modificar::class.java)
            intent.putExtra("EMAIL",binding.tvCorreo.text.toString())
            startActivity(intent)
        }

        binding.btnCerrar.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this,MainActivity::class.java))
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

    private fun setUp() {

        var titulo = binding.tvTituloLogueado
        var correo = binding.tvCorreo
        var usuario = binding.tvUsuario
        var nacionalidad = binding.tvNacionalidad
        var edad = binding.tvEdad
        val db = Firebase.firestore

        db.collection("user").document(user?.email.toString()).get().addOnSuccessListener {
            val email: String? = it.getString("email")
            val user: String? = it.getString("usuario")
            val nacionality: String? = it.getString("nacionalidad")
            val age = it.get("edad")

            titulo.text = titulo.text.toString()+" "+user
            correo.text = email
            usuario.text = user
            nacionalidad.text = nacionality
            edad.text = age.toString()
        }

    }
}