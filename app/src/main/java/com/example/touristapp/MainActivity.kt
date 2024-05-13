package com.example.touristapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.touristapp.ui.theme.TouristAppTheme
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TouristAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "TouristAppContent") {
                        composable(route = "TouristAppContent") {
                            TouristAppContent(navController = navController)
                        }
                        composable(route = "TouristPlacesContent") {
                            TouristPlacesContent(modifier = Modifier)
                        }
                        composable(route = "AdderList") {
                            AdderList()
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun TouristPlacesContent(modifier: Modifier = Modifier) {
    val categoryList = CategoryList().getList()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn(modifier = modifier.fillMaxWidth()) {
            items(categoryList) { category ->
                PlacesContentView(category)
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun PlacesContentView(category: CategoryImage) {
    Card(modifier = Modifier) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(category.categoryId),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(20.dp),
                style = MaterialTheme.typography.headlineSmall
            )

            Image(
                painter = painterResource(category.imageId),
                contentDescription = stringResource(category.categoryId),
                modifier = Modifier
                    .padding(20.dp)
                    .border(2.dp, Color.Black)
            )
        }
    }
}

@Composable
fun TouristAppContent(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .padding(20.dp)
                .clickable { navController.navigate("TouristPlacesContent") }
                .clip(RoundedCornerShape(25.dp))
                .background(
                    Color(0xFF11512E)
                )
        ) {
            Text(
                text = stringResource(id = R.string.categoryOfPlaces),
                fontSize = 32.sp
            )
        }
        Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .clip(RoundedCornerShape(25.dp))
                .background(Color(0xFF11512E))
                .clickable { navController.navigate("AdderList") })
        {
            Text(
                text = stringResource(id = R.string.visitedPlacesList),
                fontSize = 32.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TouristPlacesContent() {
    val navController = rememberNavController()
    TouristAppContent(navController = navController)
}