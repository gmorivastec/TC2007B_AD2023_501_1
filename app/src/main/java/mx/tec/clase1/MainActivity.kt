package mx.tec.clase1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    private lateinit var textito : EditText
    private lateinit var botoncito1 : Button
    private lateinit var botoncito2 : Button

    // definir un lanzador que simplemente es un objeto con el que vamos a solicitar la apertura de la actividad
    // y va a tener un método que escucha un retorno

    // design pattern - observer (también aplica al botonazo)
    val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        if(result.resultCode == Activity.RESULT_OK){
            // en kotlin hay 2 grandes categorías de clases
            // (Que realmente son variantes de los mismos tipos)
            // 1. nullable
            // 2. non-nullable
            Toast.makeText(this, result.data?.getStringExtra("saludo"), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // cómo obtener referencia a widgets de GUI
        // (la manera más elemental, rústica, básica)
        textito = findViewById(R.id.editTextText)
        botoncito1 = findViewById(R.id.button)
        botoncito2 = findViewById(R.id.button2)

        botoncito1.text = "HOLA"
        botoncito2.text = "CAMBIAR ACTIVIDAD"

        // opción 2 para el botonazo
        botoncito2.setOnClickListener {

            // al declarar una variable se puede inferir el tipo
            // intent es una solicitud para abrir una actividad
            // un intent puede abrir con tipo explícito o por acción
            var intentito = Intent(this, DetalleActivity::class.java)
            intentito.putExtra("nombre", "Killer")
            intentito.putExtra("edad", 0.2f)
            //startActivity(intentito)
            launcher.launch(intentito)
        }

        // en kotlin las declaraciones de variables pueden ser explícitas o implícitas
        var var1 = "hola"
        var var2 : String

        // en la declaración de variables especificamos si es
        // mutable
        // inmutable

        val var3 = "saludos a todos"
        var1 = "adios"
        //var3 = "ESTO NO SE PUEDE"

    }

    // como escuchar un push de botón
    // opción 1 - con un método
    fun decirHola(view : View) {

        // toast
        // mecanismo para mostrar una pequeña ventanita emergente a usuario
        // design pattern - factory

        Toast.makeText(this, "HOLA AMIGUITOS! ${textito.text}", Toast.LENGTH_SHORT).show()

        // log
        Log.i("LOGS", "INFO")
        Log.d("LOGS", "DEBUG")
        Log.w("LOGS", "WARNING")
        Log.e("LOGS", "ERROR")
        Log.wtf("LOGS", "WHAT A TERRIBLE FAILURE")
    }

    fun irAComposeActivity(view: View){
        var intent = Intent(this, ComposeActivity::class.java)
        startActivity(intent)
    }
}