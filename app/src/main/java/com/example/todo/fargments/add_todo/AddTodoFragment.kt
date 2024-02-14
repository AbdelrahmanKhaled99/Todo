package com.example.todo.fargments.add_todo

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.core.widget.addTextChangedListener
import com.example.todo.clearTime
import com.example.todo.database.MyDatebase
import com.example.todo.database.model.Todo
import com.example.todo.databinding.FragmentAddTodoBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar

class AddTodoFragment(val onAddClick: () -> Unit) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddTodoBinding
    var selectedDate = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.selectedDateTv.text = "${selectedDate.get(Calendar.DAY_OF_MONTH)} " +
                "/${selectedDate.get(Calendar.MONTH) + 1} / ${selectedDate.get(Calendar.YEAR)}"
        initListeners()
    }

    private fun initListeners() {
        binding.addTodoBtn.setOnClickListener {
            if (validate()) {
                val title = binding.titleTextInput.editText!!.text.toString()
                val description = binding.descriptionTextInput.editText!!.text.toString()
                selectedDate.clearTime()
                val todo = Todo(
                    id = id,
                    title = title,
                    description = description,
                    isDone = false,
                    date = selectedDate.timeInMillis
                )
                MyDatebase.getInstance(requireActivity().applicationContext).todoDao().insert(todo)
                dismiss()
                onAddClick.invoke()
            }
        }
        binding.selectedDateTv.setOnClickListener {
            val datePicker = DatePickerDialog(
                requireActivity(),
                { picker, year, month, day ->
                    selectedDate.set(Calendar.YEAR, year)
                    selectedDate.set(Calendar.DAY_OF_MONTH, day)
                    selectedDate.set(Calendar.MONTH, month)
                    binding.selectedDateTv.text = "${selectedDate.get(Calendar.DAY_OF_MONTH)} " +
                            "/${selectedDate.get(Calendar.MONTH) + 1} / ${selectedDate.get(Calendar.YEAR)}"
                },
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.datePicker.minDate = Calendar.getInstance().timeInMillis
                datePicker.show()
        }
        binding.titleTextInput.editText!!.addTextChangedListener {
            validate()
        }

        binding.descriptionTextInput.editText!!.addTextChangedListener {
            validate()
        }
    }


    fun validate(): Boolean {
        var isValid = true
        val title = binding.titleTextInput.editText!!.text.toString()
        val description = binding.descriptionTextInput.editText!!.text.toString()
        if (title.isEmpty()) {
            binding.titleTextInput.error = "Please enter a valid title"
            isValid = false
        } else {
            binding.titleTextInput.error = null
        }

        if (description.isEmpty()) {
            binding.descriptionTextInput.error = "Please enter a description"
            isValid = false
        } else {
            binding.descriptionTextInput.error = null
        }
        return isValid
    }
}