package com.example.todo.listener

interface TodoListener {
    fun noteInactive(id : Int, status: Boolean)
    fun noteActive(id : Int, status: Boolean)
    fun noteAdded()
}