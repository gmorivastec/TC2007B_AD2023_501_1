package mx.tec.clase1

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import mx.tec.clase1.ui.theme.Clase1Theme

// vamos a utilizar estados (Ahorita lo definimos)
// para ello necesitamos importar algunos recursos
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // sintaxis general de invocación de composables:
        // nombre de composable (argumentos si hay, opcional) {
        //  contenido si es contenedor, opcional
        // }
        setContent {
            Clase1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),

                    color = MaterialTheme.colorScheme.background
                ) {
                    //botoncito()
                    Ejemplo1(this)
                }
            }
        }
    }


    // ejemplo de composable con scope de clase
    @Composable
    fun botoncito() {
        Button(
            onClick = {
                Toast.makeText(this, "HOLA!", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text("DECIR HOLA.")
        }
    }
}


// 1er concepto de compose (que se traslada semi-transparente a flutter y react native)
// GUI modular

// cómo definir un composable
// definimos una función con la anotación @Composable
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


// hasta ahorita hemos definido todos los composables fuera de la clase
// la diferencia de un función dentro o fuera de una clase es cuestión de scope
// scope - alcance
@Composable
fun Ejemplo1(activity: Activity? = null) {
    // como agregar varios composables en un solo composable, osea usar contenedores
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("otro texto")
        Text(text = "EJEMPLO 1")
        Image(
            painter = painterResource(id = R.drawable.puppy),
            contentDescription = "a very cute puppy"
        )
        botonSaludador(activity = activity)
        pruebaInput(activity)
    }

}
@Composable
fun botonSaludador(activity: Activity? = null){
    Button(
        onClick = {
            if(activity != null)
                Toast.makeText(activity, "HOLA OTRA VEZ!", Toast.LENGTH_SHORT).show()
        }
    ) {
        Text("HOLA OTRA VEZ")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun pruebaInput(activity: Activity? = null) {

    // estado - variables internas de un composable
    // Que guardan info relevante a este elemento

    // evento - sucesos que acontecen que pueden detonar
    // un "recompose" (por ejemplo, un cambio de estado)

    var nombre by remember { mutableStateOf("firulais") }
    Column {
        Text("El perrito se llama $nombre")
        TextField(
            value = nombre,
            onValueChange = {
                nombre = it
            }
        )
        Button(
            onClick = {
                Toast.makeText(activity, "HOLA $nombre", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text("SALUDAR A PERRITO")
        }
    }
}

@Composable
fun listRow(id : Int, text : String){
    Row {
        Image(
            painter = painterResource(id = id),
            contentDescription = "un Row"
        )
        Text(text)
    }
}

// podemos tener un preview que sirve para previsualizar
// nuestro trabajo
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Clase1Theme {
        Ejemplo1()
    }
}