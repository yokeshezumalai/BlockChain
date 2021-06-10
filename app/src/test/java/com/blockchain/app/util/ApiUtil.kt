package com.blockchain.app.util

import kotlinx.coroutines.runBlocking
import org.mockito.ArgumentCaptor
import org.mockito.Mockito

object ApiUtil {

    fun <T> verifyBlocking(mock: T, f: suspend T.() -> Unit) {
        val m = Mockito.verify(mock)
        runBlocking { m.f() }
    }

    /**
     * a kotlin friendly mock that handles generics
     */
    inline fun <reified T> mock(): T = Mockito.mock(T::class.java)

    inline fun <reified T> argumentCaptor(): ArgumentCaptor<T> = ArgumentCaptor.forClass(T::class.java)
}