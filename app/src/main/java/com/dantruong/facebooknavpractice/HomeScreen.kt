package com.dantruong.facebooknavpractice

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dantruong.facebooknavpractice.data.Dessert
import com.dantruong.facebooknavpractice.data.Fruit
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(navController: NavController) {
    val desserts = remember { mutableStateOf(Dessert.getAllDesserts()) }
    val fruits = remember { mutableStateOf(Fruit.getAllFruits()) }
    val pageSize = 5
    var currentPage = 0

    LazyColumn(
        modifier = Modifier.background(Color.LightGray)
    ) {
        val dessertSize = desserts.value.size
        val fruitSize = fruits.value.size
        while (dessertSize > currentPage * pageSize) {
            val from = currentPage * pageSize
            var to = (currentPage + 1) * pageSize
            if (dessertSize < to) to = dessertSize
            val nextDessert = desserts.value.subList(from, to)
            items(nextDessert) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .background(Color.White)
                        .clickable { val route = Destination.Detail.createRoute(it.id)
                        navController.navigate(route)}) {
                    Text(
                        text = it.title,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(4.dp)
                    )

                    Text(
                        text = it.desc,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(4.dp)
                    )
                    Image(
                        painter = painterResource(id = it.resId),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
            val fruitFrom = minOf(currentPage * pageSize, fruitSize)
            val fruitTo = minOf((currentPage + 1) * pageSize, fruitSize)
            val nextFruits = fruits.value.subList(fruitFrom, fruitTo)
            
            if (nextFruits.isNotEmpty()) {
                item {
                    androidx.compose.foundation.lazy.LazyRow(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp)
                    ) {
                        items(nextFruits) { fruit ->
                            androidx.compose.material3.Card(
                                modifier = Modifier
                                    .width(260.dp)
                                    .clickable { 
                                        val route = Destination.Detail.createRoute(fruit.id)
                                        navController.navigate(route)
                                    },
                                elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 4.dp),
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                            ) {
                                Box(modifier = Modifier.fillMaxWidth()) {
                                    Image(
                                        painter = painterResource(id = fruit.resId),
                                        contentDescription = fruit.name,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(200.dp)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .align(androidx.compose.ui.Alignment.BottomStart)
                                            .background(
                                                androidx.compose.ui.graphics.Brush.verticalGradient(
                                                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                                                )
                                            )
                                            .padding(16.dp)
                                    ) {
                                        Column {
                                            Text(
                                                text = fruit.name,
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 20.sp
                                            )
                                            Text(
                                                text = "Origin: ${fruit.origin}",
                                                color = Color.LightGray,
                                                fontSize = 14.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            currentPage++
        }
    }
}