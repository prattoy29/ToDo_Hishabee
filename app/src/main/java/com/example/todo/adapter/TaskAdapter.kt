package com.example.todo.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.databinding.ItemTodoBinding
import com.example.todo.listener.TodoListener
import com.example.todo.model.Task


class TaskAdapter(
    var dataArrayList: MutableList<Task>,
    private val listener: TodoListener,
    private var onEditOrderClicked: ((item: Task) -> Unit),
) : RecyclerView.Adapter<TaskAdapter.MyViewHOlder>() {

    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHOlder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.context)
        }
        val binding = ItemTodoBinding.inflate(
            layoutInflater!!, viewGroup, false
        )
        return MyViewHOlder(binding)
    }

    override fun onBindViewHolder(itemView: MyViewHOlder, position: Int) {
        itemView.bind(dataArrayList[position], position)
    }

    override fun getItemCount(): Int {
        return dataArrayList.size
    }

    fun addNewData(orderHistoryItemList: List<Task>) {
        dataArrayList.clear()
        dataArrayList.addAll(orderHistoryItemList)
        notifyDataSetChanged()
    }

    inner class MyViewHOlder(
        private val binding: ItemTodoBinding,
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        var context: Context = binding.card.context

        fun bind(item: Task, position: Int) {
            binding.todoText.text = item.taskTitle
            if (item.taskActive == "1"){
                binding.checkbox.isChecked = true
                binding.todoText.setPaintFlags(binding.todoText.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
                binding.todoText.setTextColor(Color.GRAY)
            }
            else{
                binding.checkbox.isChecked = false
                binding.todoText.setTextColor(Color.BLACK)
                binding.todoText.setPaintFlags(binding.todoText.getPaintFlags() and Paint.STRIKE_THRU_TEXT_FLAG.inv())
            }
            binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked){
                    listener.noteInactive(item.taskId, true)
                }
                else{
                    listener.noteActive(item.taskId, true)
                }
            }
        }

    }
}