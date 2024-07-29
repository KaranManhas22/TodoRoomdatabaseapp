package com.karan.todoroomdatabaseapp

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.karan.todoroomdatabaseapp.databinding.ActivityMainBinding
import com.karan.todoroomdatabaseapp.databinding.CustomDialogboxBinding
import java.io.File

class MainActivity : AppCompatActivity(), Interface {
    lateinit var binding: ActivityMainBinding
    var array = arrayListOf<ToDoEntity>()
    var toDoAdapter = ToDoAdapter(array, this)
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
                show()
                dialogBinding.btnaddCustom.setOnClickListener {

                    val toDoEntity = ToDoEntity(
                        title = dialogBinding.ettitleCDB.text.toString(),
                        description = dialogBinding.etdescritionCDB.text.toString()
                    )
                    toDoDatabase.todointerface().insertValue(toDoEntity)
                    toDoAdapter.notifyDataSetChanged()
                    dismiss()
                    getData()
                }
                binding.ALLRB.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {

                        getData()
                    }
                }
                binding.LRB.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        array.clear()
                        toDoDatabase.todointerface().insertValue(
                            ToDoEntity(
                                title = dialogBinding.ettitleCDB.text.toString(),
                                description = dialogBinding.etdescritionCDB.text.toString(),
                                priority = 0
                            )
                        )
                        array.addAll(toDoDatabase.todointerface().entityPriority(0))
                        toDoAdapter.notifyDataSetChanged()
                    }
                }
                binding.MRB.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        array.clear()
                        toDoDatabase.todointerface().insertValue(
                            ToDoEntity(
                                title = dialogBinding.ettitleCDB.text.toString(),
                                description = dialogBinding.etdescritionCDB.text.toString(),
                                priority = 1
                            )
                        )
                        array.addAll(toDoDatabase.todointerface().entityPriority(1))
                        toDoAdapter.notifyDataSetChanged()
                    }
                }
                binding.HRB.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        array.clear()
                        toDoDatabase.todointerface().insertValue(
                            ToDoEntity(
                                title = dialogBinding.ettitleCDB.text.toString(),
                                description = dialogBinding.etdescritionCDB.text.toString(),
                                priority = 2
                            )
                        )
                        array.addAll(toDoDatabase.todointerface().entityPriority(2))
                        toDoAdapter.notifyDataSetChanged()
                    }
                }

            }

        }
    }

    override fun DeleteData(position: Int) {
        AlertDialog.Builder(this).apply {
            setTitle("Are you sure")
            setPositiveButton("yes")
            { _, _ ->
                toDoDatabase.todointerface().deleteValue(array[position])
                array.removeAt(position)
                toDoAdapter.notifyDataSetChanged()

            }
            setNegativeButton("No")
            { _, _ ->

            }
            setCancelable(false)
        }
            .show()
        getData()
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
        val oldTitle: String = array[position].title ?: ""
        val oldDescription: String = array[position].description ?: ""
        dialogBinding.ettitleCDB.setText(oldTitle)
        dialogBinding.etdescritionCDB.setText(oldDescription)
        val update = "update"
        dialogBinding.btnaddCustom.text = update
        dialogBinding.btnaddCustom.setOnClickListener {
            if (dialogBinding.ettitleCDB.text.toString().isNullOrEmpty()) {
                dialogBinding.ettitleCDB.error = "Enter Title"
            } else {

                val recentValue = ToDoEntity(
                    id = array[position].id,
                    title = dialogBinding.ettitleCDB.text.toString(),
                    description = dialogBinding.etdescritionCDB.text.toString()
                )
                toDoDatabase.todointerface().updateValue(recentValue)
                array[position] = recentValue
                toDoAdapter.notifyDataSetChanged()
                update_dialog.dismiss()
            }
        }
    }

    fun getData() {
        array.clear()
        array.addAll(toDoDatabase.todointerface().getList())
        toDoAdapter.notifyDataSetChanged()
    }


}