package com.example.carselector

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.carselector.ui.theme.CarSelectorTheme

//Programmer: Yeabsera Damte
//Date: 09/27/2024
//Android Studio Koala | 2024.1.1
//macOS Sonoma 14.4.1
//Description: This app helps the user select different types of cars
//and compare the general design along with common pros and cons of these styles
//in order to select the best vehicle according to their preferences and desired features.

data class CarType(
    val name: String,
    val image: Int,
    val pros: List<String>,
    val cons: List<String>
)

fun getCarOptions(): List<CarType> {
    return listOf(
        CarType(
            "SUV",
            R.drawable.suv,
            listOf("Spacious interior", "More ground clearance"),
            listOf("Less fuel efficient", "More expensive")
        ),
        CarType(
            "Sedan",
            R.drawable.sedan,
            listOf("More fuel efficient", "Cheaper"),
            listOf("Relatively small", "Less powerful")
        ),
        CarType(
            "Van",
            R.drawable.van,
            listOf("Very spacious interior", ""),
            listOf("Less fuel efficient", "More expensive to maintain")
        ),
        CarType(
            "Truck",
            R.drawable.truck,
            listOf("Towing Capabilities", "More powerful"),
            listOf("Less fuel efficient", "Expensive to maintain")
        ),
        CarType(
            "Compact",
            R.drawable.compact,
            listOf("Fuel Efficient", "Budget friendly"),
            listOf("Less room", "Less safety features")
        ),
        CarType(
            "Hatch back",
            R.drawable.hatchback,
            listOf("More Cargo room", "Fuel efficient"),
            listOf("Less powerful")
        ),
        CarType(
            "Sport",
            R.drawable.sport,
            listOf("Aerodynamic", "More powerful"),
            listOf("Less fuel efficient", "Expensive to maintain")
        ),
        CarType(
            "Off Roader",
            R.drawable.offroad,
            listOf("More ground clearance", "AWD"),
            listOf("Less fuel efficient", "Expensive to maintain")
        ),
        CarType(
            "Mini Van",
            R.drawable.minivan,
            listOf("More room", "More powerful"),
            listOf("Less fuel efficient", "Expensive to maintain")
        )
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "carList"){
                        composable("carList"){
                            AppFirstPage(navController)
                        }

                        composable("carDetails/{carName}/{pros}/{cons}/{image}"){ backStackEntry ->
                            val carName = backStackEntry.arguments?.getString("carName")
                            val typePros = backStackEntry.arguments?.getString("pros")?.split(",") ?: emptyList()
                            val typeCons = backStackEntry.arguments?.getString("cons")?.split(",") ?: emptyList()
                            val carImage = backStackEntry.arguments?.getString("image")?.toInt() ?: R.drawable.default_img
                            carInfo(carName ?: "", carImage, typePros, typeCons, navController)
                        }
                    }
                    //AppFirstPage()
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppFirstPage(navController: NavController){
    val carOptions = getCarOptions()

    Scaffold(
        topBar = {
            TopAppBar(
                //title = { Text("Car Selector", fontSize = 22.sp) },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = colorResource(id = R.color.navy_blue),
                    titleContentColor = Color.White
                ),
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ){
                        Text("Car Selector", fontSize = 25.sp)
                    }

                }
            )
        },
        content = {paddingValues ->
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                items(carOptions.size){index ->
                    val carOpt = carOptions[index]
                    CarOptionCard(carOpt){
                        //ADD
                        //println("Selected: ${carOpt.name}")
                        navController.navigate(
                            "carDetails/${carOpt.name}/${carOpt.pros.joinToString(",")}/${carOpt.cons.joinToString(",")}/${carOpt.image}"
                        )
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun carInfo(carName: String, carImg: Int, pros: List<String>, cons: List<String>, navController: NavController){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ){
                        Text(carName, fontSize = 22.sp)
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = colorResource(id = R.color.navy_blue),
                    titleContentColor = Color.White
                ),

                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Return",
                            tint = Color.Green
                        )
                    }
                }
            )
        },

        content = {paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(15.dp),
                contentAlignment = Alignment.Center

            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = painterResource(id = carImg),
                        contentDescription = carName,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(275.dp),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    Text("PROS", fontSize = 20.sp, color = Color.Black)
                    pros.forEach { pro ->
                        Text("-$pro", fontSize = 15.sp, color = Color.Black)
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Text("CONS", fontSize = 20.sp, color = Color.Black)
                    cons.forEach { con ->
                        Text("-$con", fontSize = 15.sp, color = Color.Black)
                    }
                }
            }
        }
    )
}

@Composable
fun CarOptionCard(carOption: CarType, onClick: () -> Unit){
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.navy_blue)
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        border = BorderStroke(2.dp, Color.Red)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row(verticalAlignment = Alignment.CenterVertically){
                Image(
                    painter = painterResource(id = carOption.image),
                    contentDescription = carOption.name,
                    modifier = Modifier
                        .size(85.dp)
                        .clip(RoundedCornerShape(100)),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )

                Column(modifier = Modifier.padding(start = 12.dp)){
                    Text(carOption.name, fontSize = 22.sp, color = Color.White)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text("Press for more", fontSize = 20.sp, color = Color.White)
                }
            }

            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.grey)),
                border = BorderStroke(4.dp, Color.Red)
            ){
                Icon(
                    Icons.Rounded.ArrowForward,
                    contentDescription = "Details",
                    tint = Color.Green
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MaterialTheme {
        val navController = rememberNavController()
        AppFirstPage(navController)
    }
}

