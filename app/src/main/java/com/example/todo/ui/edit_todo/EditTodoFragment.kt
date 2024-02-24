package com.example.todo.ui.edit_todo

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.todo.Constants
import com.example.todo.database.MyDatebase
import com.example.todo.database.model.Todo
import com.example.todo.databinding.FragmentEditTodoBinding
import java.util.Date

class EditTodoFragment : Fragment() {
    private lateinit var binding: FragmentEditTodoBinding
    private lateinit var todo: Todo
    @RequiresApi(Build.VERSION_CODES.N)
    val selectedDate = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPassedTask()
        bindTask(todo)
        binding.selectTimeTv.setOnClickListener {
            binding.selectedDateTv.setOnClickListener {
                showDatePicker()
            }
            binding.saveChangesBtn.setOnClickListener {
                saveTask()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun showDatePicker() {
        val datePicker = DatePickerDialog(
            requireActivity(),
            { picker, year, month, day ->
                selectedDate.set(java.util.Calendar.YEAR, year)
                selectedDate.set(java.util.Calendar.DAY_OF_MONTH, day)
                selectedDate.set(java.util.Calendar.MONTH, month)
                binding.selectedDateTv.text = "${selectedDate.get(java.util.Calendar.DAY_OF_MONTH)} " +
                        "/${selectedDate.get(java.util.Calendar.MONTH) + 1} / ${selectedDate.get(
                            java.util.Calendar.YEAR)}"
            },
            selectedDate.get(java.util.Calendar.YEAR),
            selectedDate.get(java.util.Calendar.MONTH),
            selectedDate.get(java.util.Calendar.DAY_OF_MONTH)
        )
        datePicker.datePicker.minDate = java.util.Calendar.getInstance().timeInMillis
        datePicker.show()
    }

    private fun getPassedTask() {
        arguments?.let {
            todo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requireArguments().getParcelable(Constants.PASSED_TASK, Todo::class.java) ?: TODO()
            } else {
                requireArguments().getParcelable(Constants.PASSED_TASK) ?: TODO()

            }
        }
    }

    private fun bindTask(todo: Todo) {
        binding.title.setText(todo.title.toString())
        binding.taskDetails.setText(todo.description.toString())
        binding.selectedDateTv.setText(todo.date.toString())
        binding.isDoneCheck.isChecked = todo.isDone ?: false
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun saveTask() {
        val selectedDateText = binding.selectedDateTv.text.toString()
        val selectedDate: Long = selectedDateText.toLongOrNull()
            ?: 0L // Parsing text to Long or using a default value if parsing fails
        val newTodo = Todo(
            todo.id,
            binding.title.text.toString(),
            binding.taskDetails.text.toString(),
            selectedDate,
            binding.isDoneCheck.isChecked
        )
        MyDatebase.getInstance(requireContext()).todoDao().update(newTodo)
        // Now you can use newTodo object as needed
    }

}