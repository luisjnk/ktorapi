package com.example.kotlin.data

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import java.time.LocalDate

data class TodoItem (
    val id: Int,
    val title: String,
    val details: String,
    val assignedTo: String,
   /* @JsonSerialize(using = ToStringSerializer::class)
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    val dueDate: LocalDate,*/
    val importance: Int
)

val todo1 = TodoItem(
    1,
    "Add RestAPI ....",
    "Add Suport",
    "Luis",
    //LocalDate.of(2018,12,18),
    1
)

val todo2 = TodoItem(
    2,
    "Add RestAPI1 ....",
    "Add Suport1",
    "Luis",
    //LocalDate.of(2018,12,18),
    1
)