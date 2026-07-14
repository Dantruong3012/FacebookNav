package com.dantruong.facebooknavpractice

sealed class Destination(val route: String){
    object Home: Destination("home")
    object Notification: Destination("notification")
    object Detail: Destination("item/{itemId}"){
        fun createRoute (itemId: Int) = "item/$itemId"
    }
}