package com.example.myapplication.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repositories.MainRepository
import com.example.myapplication.view.main.MainViewModel
import com.kleen.laundrypos.base.BaseRepository

class ViewModelFactory(private val repository: BaseRepository):ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(repository as MainRepository) as T
            else -> throw IllegalArgumentException("ViewModelClass not found")
        }
    }
}