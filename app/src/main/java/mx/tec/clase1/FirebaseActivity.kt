package mx.tec.clase1

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import mx.tec.clase1.ui.theme.Clase1Theme

// firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// propiedades
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation

class FirebaseActivity : ComponentActivity() {

    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Clase1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FirebaseExample(auth, this)
                }
            }
        }

        auth = Firebase.auth
    }

    public override fun onStart() {
        super.onStart()

        // lugar idóneo para verificar validez de usuario
        val currentUser = auth.currentUser
        if(currentUser == null){
            // sin usuario - lánzalo a interfaz de login
        }
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirebaseExample(auth : FirebaseAuth, activity : Activity? = null){

    // add 2 properties
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        OutlinedTextField(
            value = login,
            onValueChange = { login = it },
            label = { Text("Login") }
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Button(
            onClick = {
                if(activity != null){
                    auth.signInWithEmailAndPassword(login, password)
                        .addOnCompleteListener(activity){task ->
                            if(task.isSuccessful){
                                Toast.makeText(
                                    activity,
                                    "VALID LOGIN: ${auth.currentUser?.uid}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    activity,
                                    "INVALID LOGIN: ${task.exception?.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
        ){
            Text("Login")
        }

        Button(
            onClick = {
                // vamos a necesitar referencia a la actividad
                // motivo: hay una task que necesita una actividad para ser escuchada
                // null safety en kotlin -
                // 1. safe call
                // 2. utilizar if
                if(activity != null){
                    auth.createUserWithEmailAndPassword(login, password)
                        .addOnCompleteListener(activity){task ->
                            // RECUERDEN!
                            // codigo asíncrono termina en un tiempo no determinado
                            // AQUÍ ponemos lo que queremos que pase al terminar
                            if(task.isSuccessful){
                                Toast.makeText(activity, "SIGN UP SUCCESSFUL ${auth.currentUser?.uid}", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(activity, "ERROR: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }

                    // aquí NO va lo que queremos que pase al terminar la ejecución
                    // del código asíncrono

                }
            }
        ){
            Text("Sign up")
        }

        Button(
            onClick = {
                val db = Firebase.firestore

                // crear los datos del nuevo registro
                val doggy = hashMapOf(
                    "name" to "Killer",
                    "weight" to 2,
                    "age" to 5
                )

                // guardar datos en DB
                if(activity != null){

                    db.collection("animals")
                        .add(doggy)
                        .addOnSuccessListener { newDoc ->
                            Toast.makeText(activity, "NEW DOC: ${newDoc.id}", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener{ exception ->
                            Toast.makeText(activity, "ERROR: ${exception.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        ){
            Text("Add record")
        }

        Button(
            onClick = {

                val db = Firebase.firestore

                if(activity != null) {

                    db.collection("animals")
                        .get()
                        .addOnSuccessListener { snapshot ->
                            // ejemplito simple - iterar a través del snapshot
                            for(doc in snapshot){
                                Log.d("FIREBASE-TEST", "${doc.id} - ${doc.data}")
                            }
                        }
                        .addOnFailureListener{ exception ->

                            Log.wtf("FIREBASE-TEST", "${exception.message}")
                        }
                }
            }
        ){
            Text("Query")
        }

    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    Clase1Theme {
        Greeting2("Android")
    }
}