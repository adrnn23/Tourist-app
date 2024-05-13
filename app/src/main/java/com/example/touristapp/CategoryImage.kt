package com.example.touristapp

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class CategoryImage(
    @StringRes val categoryId: Int,
    @DrawableRes val imageId: Int,
)

data class BadgeImage(
    @StringRes val badgeId: Int,
    @DrawableRes val imageId: Int,
)

class Badge {
    val goldBadge = BadgeImage(R.string.goldBadge, R.drawable.goldbadge)
    fun getBadgeImg(): BadgeImage {
        return goldBadge
    }
}

class CategoryList {
    val categoryList = listOf<CategoryImage>(
        CategoryImage(R.string.mountains, R.drawable.mountains),
        CategoryImage(R.string.lake, R.drawable.lake),
        CategoryImage(R.string.city, R.drawable.city),
        CategoryImage(R.string.beach, R.drawable.beach),
        CategoryImage(R.string.river, R.drawable.river)
    )

    fun getCategoryImg(category: String): CategoryImage {
        if (category == "Mountains") return categoryList[0]
        else if (category == "Lake") return categoryList[1]
        else if (category == "City") return categoryList[2]
        else if (category == "Beach") return categoryList[3]
        return categoryList[4]
    }

    fun getList(): List<CategoryImage> {
        return categoryList
    }
}
