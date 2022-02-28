package com.elina.app.crudkotlin.features.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.elina.app.crudkotlin.db.repository.LocalRepository
import com.elina.app.crudkotlin.entity.Student
import com.elina.app.crudkotlin.features.base.BaseViewModel


class DetailViewModel @ViewModelInject constructor(private val localRepository: LocalRepository): BaseViewModel() {

    val mTriger : MutableLiveData<String> = MutableLiveData()
    val student : LiveData<Student> = Transformations.switchMap(mTriger) {
        localRepository.getDataById(it)
    }

    fun getStudentbyId() = student

    fun triggerFetchData(id: String){
        mTriger.value = id
    }

    fun deleteData(student: Student){
        localRepository.deleteData(student)
    }

    fun updateData(id: Long, name: String, nim: String, gen: String){
        localRepository.updateData(id, name, nim, gen)
    }

}