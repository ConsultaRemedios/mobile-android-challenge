package mazer.arthur.gamingshop.data.remote.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Cart (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idCart")
    var id: Int? = null,
    var idGameDetails: Int,
    var quantity: Int,
    val gameTitle: String,
    val price: Float,
    val discount: Int,
    val gameImg: String
)