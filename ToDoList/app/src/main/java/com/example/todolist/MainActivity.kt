package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    private val sharedPreferences by lazy {
        getSharedPreferences("To-DoList", MODE_PRIVATE)
    }

    private val toDoList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val addTaskButton = findViewById<Button>(R.id.addTaskButton)
        val taskEditText = findViewById<EditText>(R.id.taskEditText)
        val toDoListView = findViewById<ListView>(R.id.toDoListView)

        addTaskButton.setOnClickListener {
            val task = taskEditText.text.toString()
            if (task.isNotBlank()) {
                toDoList.add(task)
                taskEditText.text = null

                // Save the updated To-Do list to SharedPreferences
                val editor = sharedPreferences.edit()
                editor.putStringSet("tasks", toDoList.toSet())
                editor.apply()

                // Update the ListView
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, toDoList)
                toDoListView.adapter = adapter
            }
        }

        // Load the saved To-Do list from SharedPreferences
        val savedTasks = sharedPreferences.getStringSet("tasks", emptySet())
        if (savedTasks != null) {
            toDoList.addAll(savedTasks)
        }

        // Update the ListView with the loaded data
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, toDoList)
        toDoListView.adapter = adapter
    }
}