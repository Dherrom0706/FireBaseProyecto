package www.iesmurgi.firebaseproyectodionisio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ContraOlvidada : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contra_olvidada)

        findViewById<Button>(R.id.btn_contra_olvidada).setOnClickListener {
            val email: String = findViewById<EditText>(R.id.et_password_resert).text.toString().trim { it <= ' ' }
            if (email.isEmpty()){
                Toast.makeText(this,resources.getString(R.string.introduzca_mail),Toast.LENGTH_SHORT).show()
            }else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(this,resources.getString(R.string.email_enviado),Toast.LENGTH_SHORT).show()
                        finish()
                    }else{
                        Toast.makeText(this,it.exception!!.message.toString(),Toast.LENGTH_LONG).show()
                    }

                }
            }
        }
    }
}