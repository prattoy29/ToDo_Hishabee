package com.example.todo.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.database.Repository
import com.example.todo.database.Resource
import com.example.todo.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel  @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _taskList: MutableLiveData<Resource<List<Task>>> =
        MutableLiveData()
    val taskList: LiveData<Resource<List<Task>>> get() = _taskList


    private val _successResponse: MutableLiveData<Resource<String>> =
        MutableLiveData()
    val successResponse: LiveData<Resource<String>> get() = _successResponse

    fun getTodoList(
        context: Context
    ) = viewModelScope.launch {
        _taskList.value = Resource.Loading
        _taskList.value = repository.getTodoList(context)
    }

    fun makeTaskActive(
        context: Context, id : Int
    ) = viewModelScope.launch {
        _successResponse.value = Resource.Loading
        _successResponse.value = repository.taskActive(context, id)
    }
    fun makeTaskInactive(
        context: Context, id : Int
    ) = viewModelScope.launch {
        _successResponse.value = Resource.Loading
        _successResponse.value = repository.taskInactive(context, id)
    }

}