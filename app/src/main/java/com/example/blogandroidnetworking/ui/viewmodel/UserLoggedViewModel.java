package com.example.blogandroidnetworking.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.blogandroidnetworking.model.dto.UserDto;

public class UserLoggedViewModel extends ViewModel {
    private MutableLiveData<UserDto> data = new MutableLiveData<>();
    public void setData(UserDto userDto){
        data.setValue(userDto);
    }
    public LiveData<UserDto> getData(){
        return data;
    }
}
