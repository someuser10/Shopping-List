package com.mayokun.shoppinglist.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mayokun.shoppinglist.data.database.ShoppingItemDao
import kotlinx.coroutines.*
import com.mayokun.shoppinglist.data.model.ShoppingItem as Item

/**
 * Created by Mayokun Adeniyi on 2019-12-21.
 */

class ItemDetailFragmentViewModel(
    val dataSource: ShoppingItemDao,
    val itemId: Long
) : ViewModel(){

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    private val _item = MutableLiveData<Item>()
    val item: LiveData<Item>
    get() = _item

    init {
        getItem()
    }

    private fun getItem() {
        uiScope.launch {
            withContext(Dispatchers.IO){
                _item.postValue(dataSource.getItemById(itemId))
            }
        }
    }
}