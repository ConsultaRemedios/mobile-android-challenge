package com.example.cheesecakenews.data

import android.provider.BaseColumns

object DBContract {
    class GamesEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "games"
            val COLUMN_ID = "id"
            val COLUMN_NAME = "name"
            val COLUMN_PLATAFORM = "platform"
            val COLUMN_PRICE = "price"
            val COLUMN_IMAGE = "image"
        }
    }


    class CartEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "cart"
            val COLUMN_ID = "id"
            val COLUMN_NAME = "name"
            val COLUMN_PRICE = "price"
            val COLUMN_PLATAFORM = "platform"
            val COLUMN_IMAGE = "image"
            val COLUMN_QUANTITY = "quantity"
        }
    }
}
