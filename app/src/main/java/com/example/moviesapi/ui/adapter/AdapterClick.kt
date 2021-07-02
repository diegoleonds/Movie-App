package com.example.moviesapi.ui.adapter
/**
 * an interface to set the adapter click and to make it possible to do this in fragments
 *
 * T is the adapter list type
 */
interface AdapterClick<T> {
    fun simpleClick(type: T);
}