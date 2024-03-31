package fr.isen.pieterjandepla.probeersel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.pieterjandepla.probeersel.model.DataResult
import fr.isen.pieterjandepla.probeersel.model.Items
import fr.isen.pieterjandepla.probeersel.ui.theme.ProbeerselTheme
import org.json.JSONObject


class DishlistActivity : ComponentActivity() {
    private val dishes = mutableStateListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val category = intent.getStringExtra("category") ?: ""

        val fakedishes = when(category){
            getString(R.string.home_starters)-> resources.getStringArray(R.array.fake_starters)
            getString(R.string.home_dish)-> resources.getStringArray(R.array.fake_dish)
            getString(R.string.home_desert)-> resources.getStringArray(R.array.fake_desert)
            else -> resources.getStringArray(R.array.fake_dishes)
        }
        setContent {
            ProbeerselTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(android.graphics.Color.parseColor("#FFFFFF"))
                ) {
                    val itemsToDisplay= remember {
                        mutableStateListOf<Items>()
                    }
                    fetchData(category, itemsToDisplay)
                    CategoryComponent(category, itemsToDisplay)
                }
            }
        }
    }
    fun onDishClicked(dish: Items) {
        val intent = Intent(this, DishDetailActivity::class.java)
        intent.putExtra("selected_dish", dish)
        startActivity(intent)
    }
    private fun fetchData(category: String,items: MutableList<Items>) {
        val url= "http://test.api.catering.bluecodegames.com/menu"
        val param =JSONObject()
        param.put("id_shop","1")

        val jsonObjectRequest=JsonObjectRequest(
            Request.Method.POST,url,param,
            {response->
                val result= Gson().fromJson(response.toString(), DataResult::class.java)
                val dishesFromCategory = result.data.find {it.nameFr==category}?.items?: emptyList()
                items.addAll(dishesFromCategory)
            },{error->
                Log.e("CategoryActivity","error:$error")
            }
        )
        Volley.newRequestQueue(this).add(jsonObjectRequest)
    }
}
@Composable
fun CategoryComponent(selectedCategory: String, dishes: MutableList<Items>){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(10.dp)
    ) {
        Text(
            text = selectedCategory,
            color = Color(0xFFFFA500),
            fontStyle = FontStyle.Normal,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
        )
        Listesdeplats(dishes)
    }
}

@Composable
fun Listesdeplats(dishes: MutableList<Items>) {
    LazyColumn (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp)
    ) {
        items(dishes) { dish ->
            CustomText4(dish)
        }
    }
}

@Composable
fun CustomText4(dish: Items) {
    val context = LocalContext.current
    val painter = rememberImagePainter(dish.images.last()) // Nu kun je de dish gebruiken in plaats van de intent
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                if (context is DishlistActivity) {
                    context.onDishClicked(dish)
                }
            }
    ){
        Image(
            painter = painter,
            contentDescription = "Afbeelding van ${dish.nameFr}",
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
        )
        Text(
            text = "${dish.nameFr} - ${dish.prices.firstOrNull()?.price?.let { "€ $it" } ?: ""}",
            color = Color.Black,
            fontSize = 20.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(10.dp)

        )
    }
}

