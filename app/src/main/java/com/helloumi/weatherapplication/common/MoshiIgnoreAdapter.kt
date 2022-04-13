package com.helloumi.weatherapplication.common

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

/**
 * Moshi adapter that ignores properties with [MoshiIgnore] annotation.
 */
class MoshiIgnoreAdapter<T>(
    private val delegate: JsonAdapter<T>
) : JsonAdapter<T>() {

    companion object {

        val FACTORY = object : Factory {

            override fun create(
                type: Type,
                annotations: Set<Annotation>,
                moshi: Moshi
            ): JsonAdapter<*>? {
                if (annotations.any { it.annotationClass == MoshiIgnore::class }) {
                    val nextAnnotations =
                        Types.nextAnnotations(annotations, MoshiIgnore::class.java).orEmpty()
                    return MoshiIgnoreAdapter<Any>(moshi.adapter(type, nextAnnotations))
                }
                return null
            }

        }
    }

    override fun toJson(writer: JsonWriter, value: T?) {
        delegate.toJson(writer, null)
    }

    override fun fromJson(reader: JsonReader): T? {
        reader.skipValue()
        return null
    }

}

@JsonQualifier
@MustBeDocumented
@Target(
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.PROPERTY
)
/**
 * Use MoshiIgnore annotation to ignore properties during serialization and deserialization.
 */
@Retention(AnnotationRetention.RUNTIME)
annotation class MoshiIgnore
