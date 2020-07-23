package com.benhurqs.network.entities

import java.io.Serializable

open class Spotlight : Serializable{
    var id: Int = 0
    var title: String? = null
    var publisher: String? = null
    var image: String? = null
    var discount: Double = 0.0
    var price: Double = 0.0
    var description: String? = null
    var rating: Float = 0f
    var stars: Float = 0f
    var reviews: Int = 0
}