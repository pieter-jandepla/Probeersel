package fr.isen.pieterjandepla.probeersel

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.pieterjandepla.probeersel.ui.theme.ProbeerselTheme

class DishDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fun Text(
            text: String,
            color: Int,
            fontSize: TextUnit,
            fontStyle: FontStyle,
            fontWeight: FontWeight,
            modifier: Modifier,
        ) {
            TODO("Not yet implemented")
        }
        setContent {
            


        }
    }
}
@Composable
fun Dishlist (text: String) {
    val resources = LocalContext.current.resources
    val dishes = when (text) {
        "Entrées" -> resources.getStringArray(R.array.entrees)
        "Plats" -> resources.getStringArray(R.array.plats)
        "Desserts" -> resources.getStringArray(R.array.desserts)
        else -> arrayOf()
    }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxSize()
            .padding(70.dp)
    ) {
        dishes.forEach { dish ->
            CustomText3(text = dish)
        }
        class DishDetailActivity : ComponentActivity() {

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)

                val selectedDish = intent.getStringExtra("selected_dish") ?: "Error" // Haal het geselecteerde gerecht op uit de Intent

                setContent {
                    ProbeerselTheme {
                        // A surface container using the 'background' color from the theme
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = Color(android.graphics.Color.parseColor("#FFFFFF"))
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Text(
                                    text = selectedDish,
                                    color = Color(0xFFFFA500),
                                    fontStyle = FontStyle.Normal,
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                                // Hier kun je meer details over het geselecteerde gerecht toevoegen
                            }
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun DishDetailScreen() {
    CustomText("Your dish name here")
}

@Composable
fun CustomText3(text: String) {
    val context = LocalContext.current
    Text(
        text = text,
        color = Color.Black,
        fontSize = 20.sp,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(10.dp)
            .clickable {

                val intent = Intent(context, DishDetailActivity::class.java)
                intent.putExtra("selected_dish", text)
                context.startActivity(intent)
            }
    )
}



