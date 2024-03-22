package fr.isen.pieterjandepla.probeersel

import android.icu.text.CaseMap.Title
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.pieterjandepla.probeersel.ui.theme.ProbeerselTheme

class DishlistActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selectedCategory = intent.getStringExtra("selected_category")
        val backgroundColor = intent.getStringExtra("background_color") ?: "#FFFFFF"
        val title =
            intent.getStringExtra("selected_category") ?: "Error"// Haal de titel op uit de Intent
        setTitle(title)


        setContent {
            ProbeerselTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(android.graphics.Color.parseColor(backgroundColor))

                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(
                            text = title,
                            color = Color(0xFFFFA500),
                            fontStyle = FontStyle.Normal,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Listesdeplats(title)
                }

            }
        }
    }

}

@Composable
fun Listesdeplats(text: String) {
    val resources = LocalContext.current.resources
    val dishes = when (text) {
        "Entrées" -> resources.getStringArray(R.array.entrees)
        "Plats" -> resources.getStringArray(R.array.plats)
        "Desserts" -> resources.getStringArray(R.array.desserts)
        else -> arrayOf()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxSize()
            .padding(70.dp)
    ) {
        dishes.forEach { dish ->
            CustomText(text = dish)
        }
    }
}


@Composable
fun CustomText(text: String) {
    Text(
        text = text,
        color = Color.Black,
        fontSize = 20.sp,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(10.dp)
    )
}


