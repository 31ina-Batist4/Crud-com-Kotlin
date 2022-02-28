package com.elina.app.crudkotlin.features.main

import androidx.lifecycle.Observer
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.elina.app.crudkotlin.R
import com.elina.app.crudkotlin.adapter.ItemAdapter
import com.elina.app.crudkotlin.databinding.ActivityMainBinding
import com.elina.app.crudkotlin.entity.Student
import com.elina.app.crudkotlin.features.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.input_dialog.view.*

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>() {

    override fun getViewModelBindingVariable(): Int {
        return NO_VIEW_MODEL_BINDING_VARIABLE
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    private val students : ArrayList<Student> = ArrayList()

    lateinit var adapter:ItemAdapter

    private val viewModel: MainActivityViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDataBinding().itemRecyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = ItemAdapter(students, this)
        getDataBinding().itemRecyclerview.adapter = adapter

        viewModel.getStudents().observe(this, Observer<List<Student>> {
            students.clear()
            students.addAll(it!!)
            adapter.notifyDataSetChanged()
        })

        viewModel.loadStudent()

        fab_add.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.input_dialog, null)
            dialogBuilder.setView(view)
            dialogBuilder.setTitle("Dados do estudante")
            val et_name = view.ed_student_name
            val et_nim = view.ed_student_id
            val radioGroupGender = view.radio_group_gender
            dialogBuilder.setPositiveButton("Salvar") { _: DialogInterface, _: Int ->
                val studentName = et_name.text
                val studentNim = et_nim.text
                var gender: String
                val selectedRadioButton = radioGroupGender.checkedRadioButtonId
                Log.v("test", "" + selectedRadioButton)
                when (selectedRadioButton) {
                    R.id.radio_female -> gender = "Feminino"
                    else -> gender = "Masculino"
                }
                viewModel.insertStudent(Student(studentName.toString(),
                        studentNim.toString(), gender))
                applicationContext.toast("Dados inseridos com sucesso!")

            }
            dialogBuilder.setNegativeButton("Cancelar") { _: DialogInterface, _: Int ->
            }
            dialogBuilder.show()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadStudent()

    }

    fun Context.toast(message:CharSequence){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
