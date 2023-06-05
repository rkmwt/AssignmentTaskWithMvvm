package com.example.myapplication.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {
    public MutableLiveData<String> action = new MutableLiveData<>();
    public MutableLiveData<Event> event = new MutableLiveData<>();

    public BaseViewModel() { }


}
