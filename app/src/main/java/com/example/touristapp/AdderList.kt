package com.example.touristapp

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

fun isCorrectDate(iVisited: String): Boolean {
    var dd = 0
    var mm = 0
    var yyyy = 0

    if (iVisited[2] == '/' && iVisited[5] == '/') {
        dd = iVisited[0].toString().toInt() * 10 + iVisited[1].toString().toInt()
        mm = iVisited[3].toString().toInt() * 10 + iVisited[4].toString().toInt()
        yyyy = iVisited[6].toString().toInt() * 1000 + iVisited[7].toString()
            .toInt() * 100 + iVisited[8].toString().toInt() * 10 + iVisited[9].toString().toInt()
        if (dd in 1..31) {
            if (mm in 1..12) {
                if (yyyy in 1900..2100) return true
            }
        }
        if (mm == 2 && dd > 29) return false
    }
    return false
}

private fun newPlaceToast(context: Context) {
    Toast.makeText(context, "New place has been added.", Toast.LENGTH_LONG).show()
}

private fun editPlaceToast(context: Context) {
    Toast.makeText(context, "Place has been edited.", Toast.LENGTH_LONG).show()
}

private fun deletePlaceToast(context: Context) {
    Toast.makeText(context, "Place is deleted.", Toast.LENGTH_LONG).show()
}

@Composable
fun AddedPlaceView(place: AddedPlace) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
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
                            .clip(RoundedCornerShape(50.dp))
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
                            val badge = place.badge
                            val bImage = Badge().getBadgeImg(badge)
                            Image(
                                painter = painterResource(id = bImage.imageId),
                                contentDescription = stringResource(bImage.starId),
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


@Composable
fun AdderList(navController: NavController) {
    fun removePlace(place: AddedPlace, list: List<AddedPlace>): List<AddedPlace> {
        val newList = mutableListOf<AddedPlace>()

        for (item in list) {
            if (item.name == place.name && item.desc == place.desc && item.visited == place.visited) {
                continue
            } else {
                newList.add(item)
            }
        }

        return newList
    }

    var state by remember { mutableStateOf("Content") }
    var addedPlaceList by remember {
        mutableStateOf(
            listOf<AddedPlace>(
                AddedPlace("Lake", "Lake", "Lake", "02/05/2023", "Gold badge")
            )
        )
    }
    var placeToEdit: AddedPlace
    val mContext: Context = LocalContext.current
    var iName by remember { mutableStateOf("") }
    var iDesc by remember { mutableStateOf("") }
    var iCategory by remember { mutableStateOf("") }
    var iVisited by remember { mutableStateOf("") }
    var iBadge by remember { mutableStateOf("") }

    when (state) {
        "Adder" -> Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Divider(modifier = Modifier.padding(bottom = 8.dp))
            Text(
                text = "Enter new place",
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 20.dp, end = 130.dp)
            )
            EditTextField(
                value = iName,
                onValueChange = { iName = it },
                label = "Name",
                modifier = Modifier
            )
            EditTextField(
                value = iDesc,
                onValueChange = { iDesc = it },
                label = "Description",
                modifier = Modifier
            )
            EditTextField(
                value = iVisited,
                onValueChange = { iVisited = it },
                label = "DD/MM/YYYY",
                modifier = Modifier
            )

            RadioButtons(onCategorySelected = { iCategory = it })
            Spacer(modifier = Modifier.height(20.dp))
            RadioButtonsBadge(onCategorySelected = { iBadge = it })

            Row {
                Button(
                    onClick = { state = "Content" },
                    modifier = Modifier
                        .padding(start = 5.dp, top = 10.dp)
                        .fillMaxWidth(0.5f)
                ) {
                    Text(
                        text = "Back",
                        fontSize = 14.sp
                    )
                }
                Button(
                    onClick = {
                        if (iName.isNotBlank() && iDesc.isNotBlank() && iCategory.isNotBlank() && iVisited.isNotBlank()) {
                            iVisited = iVisited.toString()
                            if (isCorrectDate(iVisited)) {
                                val newPlace = AddedPlace(iName, iDesc, iCategory, iVisited, iBadge)
                                addedPlaceList += newPlace
                                iName = ""
                                iDesc = ""
                                iVisited = ""
                                iBadge = ""
                                state = "Content"
                                println(state)
                                newPlaceToast(mContext)
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(start = 5.dp, top = 10.dp, end = 5.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Add new place",
                        fontSize = 14.sp
                    )
                }
            }

            Divider(modifier = Modifier.padding(top = 8.dp))
        }

        "Content" -> Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { state = "Adder" },
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Add new place",
                        fontSize = 14.sp
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    if (addedPlaceList.isNotEmpty()) {
                        items(addedPlaceList) { place ->
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable {
                                        placeToEdit = place
                                        iName = placeToEdit.name
                                        iDesc = placeToEdit.desc
                                        iBadge = placeToEdit.badge
                                        iVisited = placeToEdit.visited
                                        iCategory = placeToEdit.category
                                        placeToEdit =
                                            AddedPlace(iName, iDesc, iCategory, iVisited, iBadge)
                                        addedPlaceList = removePlace(placeToEdit, addedPlaceList)
                                        state = "Edit"
                                    }
                            ) {
                                AddedPlaceView(place)
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                }
            }

        }

        "Edit" -> Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Divider(modifier = Modifier.padding(bottom = 8.dp))
            Text(
                text = "Edit place",
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 20.dp, end = 130.dp)
            )
            EditTextField(
                value = iName,
                onValueChange = { iName = it },
                label = "Name",
                modifier = Modifier
            )
            EditTextField(
                value = iDesc,
                onValueChange = { iDesc = it },
                label = "Description",
                modifier = Modifier
            )
            EditTextField(
                value = iVisited,
                onValueChange = { iVisited = it },
                label = "DD/MM/YYYY",
                modifier = Modifier
            )

            RadioButtons(onCategorySelected = { iCategory = it })
            Spacer(modifier = Modifier.height(20.dp))
            RadioButtonsBadge(onCategorySelected = { iBadge = it })

            Row {
                Button(
                    onClick = {
                        state = "Content"
                        deletePlaceToast(mContext)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(red = 255, green = 84, blue = 73)
                    ),
                    modifier = Modifier
                        .padding(start = 5.dp, top = 10.dp)
                        .fillMaxWidth(0.5f)
                ) {
                    Text(
                        text = "Delete",
                        fontSize = 14.sp
                    )
                }
                Button(
                    onClick = {
                        if (iName.isNotBlank() && iDesc.isNotBlank() && iCategory.isNotBlank() && iVisited.isNotBlank()) {
                            iVisited = iVisited.toString()
                            if (isCorrectDate(iVisited)) {
                                val newPlace = AddedPlace(iName, iDesc, iCategory, iVisited, iBadge)
                                addedPlaceList += newPlace
                                iName = ""
                                iDesc = ""
                                iVisited = ""
                                iBadge = ""
                                state = "Content"
                                editPlaceToast(mContext)
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(start = 5.dp, top = 10.dp, end = 5.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Edit place",
                        fontSize = 14.sp
                    )
                }
            }

            Divider(modifier = Modifier.padding(top = 8.dp))
        }
    }
}


@Composable
fun EditTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        singleLine = true,
        modifier = Modifier
            .padding(bottom = 20.dp)
    )
}


data class RBInfo(
    val isChecked: Boolean,
    val text: String,
)

@Composable
fun RadioButtonsBadge(onCategorySelected: (String) -> Unit) {
    val radioButtons = remember {
        mutableStateListOf(
            RBInfo(
                isChecked = true,
                text = "Without badge"
            ),
            RBInfo(
                isChecked = false,
                text = "Gold badge"
            )
        )
    }
    Text(
        text = "Add badge",
        fontSize = 13.sp,
        modifier = Modifier.padding(start = 20.dp, top = 10.dp)
    )
    Row() {
        radioButtons.forEachIndexed { index, info ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable {
                        radioButtons.replaceAll {
                            it.copy(
                                isChecked = it.text == info.text
                            )
                        }
                        onCategorySelected(info.text)
                    }
                    .padding(end = 4.dp)
            ) {
                RadioButton(
                    selected = info.isChecked,
                    onClick = {
                        radioButtons.replaceAll {
                            it.copy(
                                isChecked = it.text == info.text
                            )
                        }
                        onCategorySelected(info.text)
                    }
                )
                Text(
                    text = info.text,
                    fontSize = 9.sp
                )
            }
        }
    }
}

@Composable
fun RadioButtons(onCategorySelected: (String) -> Unit) {
    val radioButtons = remember {
        mutableStateListOf(
            RBInfo(
                isChecked = true,
                text = "Mountains"
            ),
            RBInfo(
                isChecked = false,
                text = "Lake"
            ),
            RBInfo(
                isChecked = false,
                text = "City"
            ),
            RBInfo(
                isChecked = false,
                text = "Beach"
            ),
            RBInfo(
                isChecked = false,
                text = "River"
            )
        )
    }
    Text(
        text = "Category",
        fontSize = 13.sp,
        modifier = Modifier.padding(start = 20.dp, top = 10.dp)
    )
    Row(horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(start=30.dp)) {
        radioButtons.forEachIndexed { index, info ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable {
                        radioButtons.replaceAll {
                            it.copy(
                                isChecked = it.text == info.text
                            )
                        }
                        onCategorySelected(info.text)
                    }
                    .padding(end = 20.dp)
            ) {
                RadioButton(
                    selected = info.isChecked,
                    onClick = {
                        radioButtons.replaceAll {
                            it.copy(
                                isChecked = it.text == info.text
                            )
                        }
                        onCategorySelected(info.text)
                    }
                )
                Text(
                    text = info.text,
                    fontSize = 9.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdderListPreview() {
    var navController = rememberNavController()
    AdderList(navController)
}