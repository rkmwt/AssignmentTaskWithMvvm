package com.example.myapplication.view.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.myapplication.R
import com.example.myapplication.adapter.ImagePagerAdapter
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.base.Event
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.network.MainApi
import com.example.myapplication.repositories.MainRepository
import okqapps.com.tagslayout.TagItem
import okqapps.com.tagslayout.TagTextSize

class MainActivity: BaseActivity<MainViewModel, ActivityMainBinding, MainRepository>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setObservers()
        handleEvents()
    }

    override fun getActivityBinding(): ViewDataBinding {
        return DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun setBindingLifeCycle() {
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        binding.bottomChat.viewmodel  = viewModel
        viewModel.getProducts()
    }

    override fun getActivityRepository(): MainRepository {
        return  MainRepository(remoteDataSource.buildApi(MainApi::class.java))
    }

    override fun getViewModel() = MainViewModel::class.java

    private fun createBody(): java.util.HashMap<String, String> {
        val paramObject = HashMap<String, String>()
        paramObject.put("category", "123")
        return paramObject
    }

    private fun handleEvents() {
        viewModel.event.observe(this){
            when(it.key){
                Event.EventType.RESPONSE -> {
                    setUpTags()
                    setPager()
                }
                else -> handleEvent(it)
            }
        }
    }
    private fun setUpTags() {
        var tagItems = ArrayList<TagItem>()
        tagItems.add(TagItem(1,"#2023","#FFE6E6E8","#5A6B87",true))
        tagItems.add(TagItem(1,"#TODAYISMONDAY","#FFE6E6E8","#5A6B87",true))
        tagItems.add(TagItem(1,"#TOP","#FFE6E6E8","#5A6B87",true))
        tagItems.add(TagItem(1,"#POPS!","#FFE6E6E8","#5A6B87",true))
        tagItems.add(TagItem(1,"#WOW!","#FFE6E6E8","#5A6B87",true))
        tagItems.add(TagItem(1,"#ROW!","#FFE6E6E8","#5A6B87",true))
        binding.tagsLayout.setTagTextSize(TagTextSize.fromInt(8))
        binding.tagsLayout.initializeTags(this,tagItems)
    }

    private fun setPager() {
        var alBanner = ArrayList<String>()
        alBanner.add("https://wjddnjs754.cafe24.com/web/product/small/202303/5b9279582db2a92beb8db29fa1512ee9.jpg")
        alBanner.add("https://wjddnjs754.cafe24.com/web/product/small/202303/5b9279582db2a92beb8db29fa1512ee9.jpg")
        alBanner.add("https://wjddnjs754.cafe24.com/web/product/small/202303/5b9279582db2a92beb8db29fa1512ee9.jpg")

        val adapter = ImagePagerAdapter(this, alBanner)
        binding.viewPager.adapter = adapter
        binding.circleIndicator.setViewPager(binding.viewPager)
    }
}