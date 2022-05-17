package com.example.appordertour.model

import java.io.Serializable

data class CategoryTour(
    val id: String? = null,
    val resourceImage: String,
    val description: String,
    val name: String
) : Serializable