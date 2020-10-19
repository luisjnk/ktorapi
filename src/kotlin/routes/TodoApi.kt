package com.example.kotlin.routes

import com.example.kotlin.data.TodoItem
import com.example.todos
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*

fun  Routing.todoApi() {
    route("/api") {
        get("/todos") {
            call.respond(todos)
         }

        get("/todos/{id}") {
            val id = call.parameters["id"]!!
            val todo = todos.find { todo -> todo.id.equals(id.toInt()) }!!
            call.respond(todo)
        }

        post("/todos") {
            val todo: TodoItem = call.receive<TodoItem>()
            val newTodo = TodoItem(todos.size +1, todo.title, todo.details, todo.assignedTo,  todo.importance )
            todos = todos + newTodo
            call.respond(HttpStatusCode.Created, todos)
        }

        put("todos/{id}") {
            val id: String? = call.parameters["id"]
            if(id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@put
            }
            val foundItem: TodoItem? = todos.getOrNull(id.toInt())
            if (foundItem == null) {
                call.respond(HttpStatusCode.NotFound)
                return@put
            }
            val todo:TodoItem = call.receive<TodoItem>()

            todos = todos.filter { it.id != todo.id }
            todos = todos + todo

            call.respond(HttpStatusCode.NoContent)

        }

    }
}