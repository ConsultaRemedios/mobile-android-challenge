package com.benhurqs.network.entities

import java.io.Serializable

class Suggestion : Serializable{
    var id: Int = 0
    var title: String? = null
    var price: Double = 0.0
    var discount: Double = 0.0
}