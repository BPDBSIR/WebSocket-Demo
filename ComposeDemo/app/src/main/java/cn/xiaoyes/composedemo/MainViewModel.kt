package cn.xiaoyes.composedemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val data = MutableLiveData<List<String>>()
}