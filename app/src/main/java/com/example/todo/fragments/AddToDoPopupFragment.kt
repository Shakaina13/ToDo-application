package com.example.todo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.todo.databinding.FragmentAddToDoPopupBinding
import com.example.todo.utils.ToDoData
import com.google.android.material.textfield.TextInputEditText


class AddToDoPopupFragment : DialogFragment() {

    private lateinit var binding:FragmentAddToDoPopupBinding
    private var listener : OnDialogNextBtnClickListener? = null
    private var toDoData: ToDoData? = null


    fun setListener(listener: OnDialogNextBtnClickListener) {
        this.listener = listener
    }

    companion object {
        const val TAG = "DialogFragment"
        @JvmStatic
        fun newInstance(taskId: String, task: String) =
            AddToDoPopupFragment().apply {
                arguments = Bundle().apply {
                    putString("taskId", taskId)
                    putString("task", task)
                }
            }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentAddToDoPopupBinding.inflate(inflater , container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null){

            toDoData = ToDoData(arguments?.getString("taskId").toString() ,arguments?.getString("task").toString())
            binding.todoEt.setText(toDoData?.task)
        }


        binding.todoClose.setOnClickListener {
            dismiss()
        }

        binding.todoNextBtn.setOnClickListener {

            val todoTask = binding.todoEt.text.toString()
            if (todoTask.isNotEmpty()){
                if (toDoData == null){
                    listener?.saveTask(todoTask , binding.todoEt)
                }else{
                    toDoData!!.task = todoTask
                    listener?.updateTask(toDoData!!, binding.todoEt)
                }

            }
        }
    }

    interface OnDialogNextBtnClickListener{
        fun saveTask(todoTask:String , todoEdit:TextInputEditText)
        fun updateTask(toDoData: ToDoData , todoEdit:TextInputEditText)
    }

}