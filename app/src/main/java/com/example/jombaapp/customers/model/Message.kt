package com.example.jombaapp.customers.model

data class Message(
    var messageText: String = "", // Default values for properties
    var senderId: String = "",
    var receiverId: String = ""
)