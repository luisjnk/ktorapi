package com.example.kotlin.routes

import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route

fun Routing.userApi() {
    route("/api") {
        get("/users") {

        }
    }
}