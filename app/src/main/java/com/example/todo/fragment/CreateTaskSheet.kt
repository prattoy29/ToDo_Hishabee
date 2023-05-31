package com.example.todo.fragment

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager

import androidx.lifecycle.lifecycleScope
import com.example.todo.R
import com.example.todo.database.Client
import com.example.todo.databinding.FragmentCreateTaskBinding
import com.example.todo.listener.TodoListener
import com.example.todo.model.Task
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import kotlinx.coroutines.launch


class CreateTaskSheet(listener: TodoListener) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentCreateTaskBinding
    private val listener: TodoListener = listener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentCreateTaskBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addTask.setOnClickListener {
            createTask()

        }

    }

    private fun createTask() {
        class saveTaskInBackend :
            AsyncTask<Void?, Void?, Void?>() {
            @SuppressLint("WrongThread")

            override fun onPostExecute(aVoid: Void?) {
                super.onPostExecute(aVoid)
                Toast.makeText(getActivity(), "Todo Created", Toast.LENGTH_SHORT).show()
                dismiss()
                listener.noteAdded()
            }

            override fun doInBackground(vararg params: Void?): Void? {
                val createTask = Task()
                createTask.taskId = 0
                createTask.taskTitle = binding.addTaskTitle.getText().toString()
                createTask.taskActive = "0"
                Client.getInstance(requireActivity())!!.appDatabase
                    .dataBaseAction()
                    ?.insertDataIntoTaskList(createTask)
                return null
            }


        }

        val st = saveTaskInBackend()
        st.execute()
    }

    companion object {
        @JvmStatic
        fun display(
            fragmentManager: FragmentManager?,
            listener: TodoListener,
            tag: String?
        ) {
            val dialog = CreateTaskSheet(listener).apply {
            }
            fragmentManager?.let { dialog.show(it, tag) }
        }
    }


}







