package com.mhmdawad.androidtestingplayground.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.mhmdawad.androidtestingplayground.presentation.AddShoppingItemFragment
import com.mhmdawad.androidtestingplayground.presentation.ImagePickFragment
import com.mhmdawad.androidtestingplayground.presentation.adapter.ImageAdapter
import javax.inject.Inject

class FragmentFactory @Inject constructor(
    private val imageAdapter: ImageAdapter,
    private val glide: RequestManager
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ImagePickFragment::class.java.name-> ImagePickFragment(imageAdapter)
            AddShoppingItemFragment::class.java.name-> AddShoppingItemFragment(glide)
            else -> super.instantiate(classLoader, className)
        }
    }
}