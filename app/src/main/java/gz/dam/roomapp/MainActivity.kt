package gz.dam.roomapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import gz.dam.roomapp.ui.theme.RoomAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoomAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        // instanciamos la base de datos
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries()
            .build()

        // usamos el DAO para acceder a los datos
        val userDao = db.userDao()
        // recogemnos todos los usuarios
        val users: List<User> = userDao.getAll()
        Log.d("ROOMTEST", "Users: $users" )
        // insertamos un nuevo usuario
        val newUser = User(1,"John", "Doe")
        userDao.insertAll(newUser)
        // recogemos todos los usuarios actualizados
        val updatedUsers: List<User> = userDao.getAll()
        Log.d("ROOMTEST", "Updated Users: $updatedUsers" )

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RoomAppTheme {
        Greeting("Android")
    }
}