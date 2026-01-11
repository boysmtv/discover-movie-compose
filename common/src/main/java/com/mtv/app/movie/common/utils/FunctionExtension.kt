package com.mtv.app.movie.common.utils

import kotlin.reflect.KClass

import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

@Suppress("UNCHECKED_CAST")
fun <T : Any> T.toMap(): Map<String, Any> {
    return this::class.memberProperties.associate { prop ->
        val kProp = prop as KProperty1<T, *>       // fix out-projection
        val value = kProp.get(this) ?: ""          // MUST return Any, not Any?
        prop.name to value
    }
}

fun <T : Any> Map<String, Any>?.toDataClass(clazz: KClass<T>): T {
    val notNullMap = this ?: emptyMap()

    val constructor = clazz.constructors.first()

    val params = constructor.parameters.associateWith { param ->
        notNullMap[param.name]
    }

    return constructor.callBy(params)
}

fun <T : Any> Map<String, Any>?.toDataClass(clazz: Class<T>): T = this.toDataClass(clazz.kotlin)
