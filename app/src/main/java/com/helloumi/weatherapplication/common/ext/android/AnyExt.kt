package com.helloumi.weatherapplication.common.ext.android

/**
 * Checks whether this match any of the given values.
 *
 * @param values the values to compare.
 *
 * @return true if there is one matching value, false otherwise.
 */
inline fun <reified E : Any> E?.matchAny(vararg values: E) = this in values

/**
 * Checks whether this does not match any of the given values.
 *
 * @param values the values to compare.
 *
 * @return true if there is no matching value, false otherwise.
 */
inline fun <reified E : Any> E?.matchNone(vararg values: E) = this !in values
