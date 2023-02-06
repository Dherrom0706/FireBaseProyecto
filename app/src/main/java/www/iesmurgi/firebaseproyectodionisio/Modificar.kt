package www.iesmurgi.firebaseproyectodionisio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import www.iesmurgi.firebaseproyectodionisio.databinding.ActivityModificarBinding

class Modificar : AppCompatActivity() {
    private lateinit var binding: ActivityModificarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityModificarBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var correo = intent.extras?.getString("EMAIL")
        binding.btnCambiar.setOnClickListener {
            var user: String
            var nacionalidad: String
            var edad: Int
            if (binding.etUsuarioCambiar.text.isNotEmpty()){
                user = binding.etUsuarioCambiar.text.toString()
            }else{
                user = "nouser"
            }
            if (binding.etNacionalidadCambiar.text.isNotEmpty()){
                nacionalidad = binding.etNacionalidadCambiar.text.toString()
            }else{
                nacionalidad = "nonacionalidad"
            }
            if (binding.etEdadCambiar.text.isNotEmpty()){
                edad = binding.etEdadCambiar.text.toString().toInt()
            }else{
                edad = 0
            }

            val documentReference = Firebase.firestore.collection("user").document(correo.toString())
            documentReference.update("usuario",user)
            documentReference.update("nacionalidad",nacionalidad)
            documentReference.update("edad",edad)

            Toast.makeText(this,resources.getString(R.string.cambio_correcto),Toast.LENGTH_SHORT).show()
            var intent = Intent(this,PerfilActivity::class.java)
            intent.putExtra("RELOAD",true)
            startActivity(intent)
        }

    }
}