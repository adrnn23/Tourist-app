package com.example.touristapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AddedPlaceView(place: AddedPlace) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        if (place.name.isNotEmpty() && place.desc.isNotEmpty() && place.category.isNotEmpty() && place.visited.isNotEmpty() && place.badge.isNotEmpty()) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .background(colorResource(id = R.color.primary))
            ) {
                Row {
                    val category = place.category
                    val cImage = CategoryList().getCategoryImg(category)
                    Image(
                        painter = painterResource(id = cImage.imageId),
                        contentDescription = stringResource(cImage.categoryId),
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .padding(start = 20.dp, top = 20.dp)
                            .size(150.dp)
                    )
                    Column(modifier = Modifier.padding(top = 5.dp)) {
                        Text(
                            text = place.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .padding(start = 10.dp, 10.dp)
                        )
                        Text(
                            text = "Visited: " + place.visited,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(10.dp, top = 10.dp)
                        )
                        if (place.badge == "Gold badge") {
                            val bImage = Badge().getBadgeImg()
                            Image(
                                painter = painterResource(id = bImage.imageId),
                                contentDescription = stringResource(bImage.badgeId),
                                modifier = Modifier
                                    .padding(4.dp, top = 10.dp)
                                    .size(64.dp)
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(colorResource(id = R.color.secondary))
                ) {
                    Text(
                        text = place.desc,
                        modifier = Modifier.padding(8.dp),
                        fontSize = 14.sp
                    )
                }
            }

        }
    }
}