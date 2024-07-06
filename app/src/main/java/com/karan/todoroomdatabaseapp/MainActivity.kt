package com.karan.todoroomdatabaseapp

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.karan.todoroomdatabaseapp.databinding.ActivityMainBinding
import com.karan.todoroomdatabaseapp.databinding.CustomDialogboxBinding

class MainActivity : AppCompatActivity(),Interface{
    lateinit var binding: ActivityMainBinding
    var array = arrayListOf<ToDoEntity>()
    var toDoAdapter = ToDoAdapter(array,this)
    lateinit var toDoDatabase: ToDoDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        toDoDatabase = ToDoDatabase.getInstance(this)
        var linearLayoutmanager = LinearLayoutManager(this)
        binding.recyclerList.layoutManager = linearLayoutmanager
        binding.recyclerList.adapter = toDoAdapter
        binding.recyclerList.setHasFixedSize(true)
        binding.btnfab.setOnClickListener {
            val dialogBinding = CustomDialogboxBinding.inflate(layoutInflater)
            Dialog(this).apply {
                setContentView(dialogBinding.root)
                window?.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                dialogBinding.btnaddCustom.setOnClickListener {
                    array.addAll(toDoDatabase.todointerface().insertValue(ToDoEntity(title = dialogBinding.ettitleCDB.text.toString())))
                    toDoAdapter.notifyDataSetChanged()
                    dismiss()
                    getData()
                }
                show()
            }
        }
    }

    override fun DeleteData(position: Int) {
        AlertDialog.Builder(this).apply {
            setTitle("Are you sure")
            setPositiveButton("yes")
            { _, _ ->
                array.removeAt(position)
                toDoAdapter.notifyDataSetChanged()
            }
            setNegativeButton("No")
            { _, _ ->

            }
            setCancelable(false)
        }
            .show()
    }

    override fun UpdateData(position: Int) {
        val dialogBinding = CustomDialogboxBinding.inflate(layoutInflater)
        val update_dialog = Dialog(this).apply {
            setContentView(dialogBinding.root)
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            show()
        }
        val oldName: String = array[position].title ?: ""
        dialogBinding.ettitleCDB.setText(oldName)
        val update = "update"
        dialogBinding.btnaddCustom.text = update
        dialogBinding.btnaddCustom.setOnClickListener {
            if (dialogBinding.ettitleCDB.text.toString().isNullOrEmpty()) {
                dialogBinding.ettitleCDB.error = "Enter Title"
            } else {

//                 array[position] = DataClass(dialogBinding.ettitleCDB.text.toString())

              toDoAdapter.notifyDataSetChanged()
                update_dialog.dismiss()
            }
        }
    }
    fun getData()
    {
        array.addAll(toDoDatabase.todointerface().getList())
        toDoAdapter.notifyDataSetChanged()
    }
}