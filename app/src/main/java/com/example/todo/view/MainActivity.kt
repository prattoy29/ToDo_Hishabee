package com.example.todo.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.todo.R
import com.example.todo.adapter.TaskAdapter
import com.example.todo.database.Resource
import com.example.todo.databinding.ActivityMainBinding
import com.example.todo.fragment.CreateTaskSheet
import com.example.todo.listener.TodoListener
import com.example.todo.model.Task
import com.example.todo.viewModel.ToDoViewModel

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), TodoListener {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ToDoViewModel by viewModels()
    var tasks: ArrayList<Task> = ArrayList()
    var taskAdapter: TaskAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setUpAdapter()
        viewModel.getTodoList(context = this)

        binding.addTask.setOnClickListener {
            CreateTaskSheet.display(
                supportFragmentManager, this,"todo"
            )
        }

        viewModel.taskList.observe(this) {
            when (it) {

                is Resource.Success -> {
                    lifecycleScope.launch {
                        taskAdapter!!.addNewData(it.value)

                    }
                }
                is Resource.Failure -> {
                    Toast.makeText(this, "Sorry! Something is wrong", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
        viewModel.successResponse.observe(this) {
            when (it) {

                is Resource.Success -> {
                    lifecycleScope.launch {
                        viewModel.getTodoList(this@MainActivity)

                    }
                }
                is Resource.Failure -> {
                    Toast.makeText(this, "Sorry! Something is wrong", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }
    fun setUpAdapter() {
        taskAdapter = TaskAdapter(ArrayList(), onEditOrderClicked = {}, listener = this)
        binding.taskRecycler.setAdapter(taskAdapter)
    }

    override fun noteInactive(id: Int, status: Boolean) {
        viewModel.makeTaskActive(this@MainActivity, id)

    }

    override fun noteActive(id: Int, status: Boolean) {
        viewModel.makeTaskInactive(this@MainActivity, id)
        viewModel.getTodoList(this@MainActivity)
    }

    override fun noteAdded() {
        viewModel.getTodoList(this)
    }
}