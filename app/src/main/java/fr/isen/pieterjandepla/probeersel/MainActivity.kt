package fr.isen.pieterjandepla.probeersel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "MainActivity is destroyed")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selectedCategory = intent.getStringExtra("selected_category")
        val backgroundColor = intent.getStringExtra("background_color") ?: "#FFFFFF"


        setContent {
            //Title App
            MainLayoutComposable { text -> onItemClicked(text) }
        }
    }

    fun onItemClicked(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, DishlistActivity::class.java)
        intent.putExtra("category", text)
        startActivity(intent)
    }

}


@Composable
fun MainLayoutComposable(onItemClicked: (String) -> Unit) {
    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Color(0xFFFFA500)),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = ("   DroidRestaurant"),
                color = (Color.White),
                fontSize = (20.sp),
                fontStyle = (FontStyle.Normal),
                fontWeight = FontWeight.Bold
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(10.dp, 70.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column {
                    Text(
                        text = "Bienvenue Chez",
                        color = Color.Black,
                        fontSize = (25.sp),
                        fontStyle = (FontStyle.Normal),
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "DroitRestaurant",
                        color = Color.Black,
                        fontSize = (25.sp),
                        fontStyle = (FontStyle.Normal),
                        fontWeight = FontWeight.Bold,
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.androitcuisinier),
                    contentDescription = null,
                    modifier = Modifier
                        .scale(3f, 3f)
                        .offset(30.dp, 0.dp)
                )
            }

        }

    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Entrées",
            color = Color(0xFFFFA500),
            fontStyle = FontStyle.Normal,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { onItemClicked("Entrées") }
        )
        Text(
            text = "Plats",
            color = Color(0xFFFFA500),
            fontStyle = FontStyle.Normal,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { onItemClicked("Plats") }
        )
        Text(
            text = "Desserts",
            color = Color(0xFFFFA500),
            fontStyle = FontStyle.Normal,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .clickable {
                    onItemClicked("Desserts")
                }
        )
    }
}

