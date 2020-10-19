package com.example.kotlin.utils

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlin.reflect.KParameter
import kotlin.reflect.jvm.javaType

@JsonDeserialize(using = KotlinDataDeserializer::class)
data class Foo(val x: String, val y: String, val z: Int)

class KotlinDataDeserializer : StdDeserializer<Foo>(Foo::class.java) {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Foo {
        return p.readValueAs(Foo::class.java)
    }

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext, intoValue: Foo): Foo {
        val copy = intoValue::copy
        val tree = p.readValueAsTree<ObjectNode>()

        val args = mutableMapOf<KParameter, Any?>()
        for (param : KParameter in copy.parameters) {
            if (!tree.has(param.name)) {
                if (param.isOptional) {
                    continue
                }
                throw RuntimeException("Missing required field: ${param.name}")
            }

            val node = tree.get(param.name)

            if (node == null && !param.type.isMarkedNullable) {
                throw RuntimeException("Got null value for non-nullable field: ${param.name}")
            }

            val javaType = ctxt.typeFactory.constructType(param.type.javaType)
            val reader = jacksonObjectMapper().readerFor(javaType)
            val obj = reader.readValue<Any?>(node)

            println(param.type)

            args[param] = obj
        }

        val result = copy.callBy(args)


        return result
    }
}
