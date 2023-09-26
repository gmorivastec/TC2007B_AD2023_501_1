package mx.tec.clase1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class DetalleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        Toast.makeText(
            this,
            "Hola soy ${intent.getStringExtra("nombre")} y tengo ${intent.getFloatExtra("edad", 0f)} años",
            Toast.LENGTH_SHORT)
            .show()
    }

    fun terminarAplicacion(view : View) {
        // como terminar una actividad
        var intentote = Intent()
        intentote.putExtra("saludo", "el perrito te saluda")
        setResult(Activity.RESULT_OK, intentote)
        finish()
    }
}