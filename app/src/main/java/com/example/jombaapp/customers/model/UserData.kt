package com.example.jombaapp.customers.model

data class UserData(
    val id: String,
    val name: String,
    val email: String,
    val phone: String
) {
    // Primary constructor
    constructor() : this("", "", "", "")
}
