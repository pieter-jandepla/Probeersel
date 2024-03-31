package fr.isen.pieterjandepla.probeersel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import fr.isen.pieterjandepla.probeersel.model.Ingredients
import fr.isen.pieterjandepla.probeersel.model.Items
import fr.isen.pieterjandepla.probeersel.ui.theme.ProbeerselTheme


class DishDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selectedDish = intent.getSerializableExtra("selected_dish") as Items

        setContent {
            ProbeerselTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(android.graphics.Color.parseColor("#FFFFFF"))
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = selectedDish.nameFr ?: "",
                                color = Color(0xFFFFA500),
                                fontStyle = FontStyle.Normal,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            SwipeableImage(selectedDish.images)

                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)// Ensure full width // Occupy all available height
                            ) {
                                item {
                                    Text(
                                        text = "Voici quelques détails sur le plat ${selectedDish.nameFr}. Il est délicieux et très populaire dans notre restaurant.",
                                        color = Color.Black,
                                        fontSize = 20.sp,
                                        fontStyle = FontStyle.Normal,
                                        fontWeight = FontWeight.Normal,
                                        modifier = Modifier.padding(vertical = 16.dp)
                                    )

                                    IngredientsList(selectedDish.ingredients)
                                    QuantitySelector { quantity ->
                                        PriceButton(calculatePrice(selectedDish, quantity))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }



        private fun calculatePrice(selectedDish: Items, quantity: Double): Double {
            // Standaardprijs van het gerecht
            val basePrice = selectedDish.prices.firstOrNull()?.price?.toDouble() ?: 0.0
            // Bereken de totale prijs op basis van de hoeveelheid
            return basePrice * quantity
        }

    }

    @Composable
    fun SwipeableImage(images: List<String>) {
        LazyRow {
            items(images) { imageUrl ->
                Image(
                    painter = rememberImagePainter(imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(200.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }


    @Composable
    fun IngredientsList(ingredients: List<Ingredients>) {
        Column(
            modifier = Modifier.padding(start = 10.dp)
        ) {

                Text(
                    text = "Ingrédients:",
                    color = Color(0xFFFFA500),
                    fontStyle = FontStyle.Normal,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

            ingredients.forEach { ingredient ->
                Text(
                    text = "- ${ingredient.nameFr}",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }


    @Composable
    fun QuantitySelector(onQuantityChanged: @Composable (Double) -> Unit) {
        var quantity by remember { mutableStateOf(1.0) }

        Column(
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text("choisir la quantité:")
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$quantity",
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .border(width = 1.dp, color = Color.Black)
                        .clickable { /* Implementeer indien nodig */ }
                        .padding(8.dp)
                )
                Button(
                    onClick = { quantity++ },
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(text = "+")
                }
                Button(
                    onClick = { if (quantity > 0) quantity-- },
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(text = "-")
                }
            }
        }

        // Roep de callback aan met de nieuwe hoeveelheid, maar zet deze om naar Double
        onQuantityChanged(quantity.toDouble())
    }


    @Composable
    fun PriceButton(price: Double) {
        Button(
            onClick = { /* Actie wanneer op de knop wordt geklikt */ },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Prix: $price")
        }
    }