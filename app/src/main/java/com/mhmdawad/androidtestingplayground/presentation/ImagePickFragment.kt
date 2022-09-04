package com.mhmdawad.androidtestingplayground.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mhmdawad.androidtestingplayground.R
import com.mhmdawad.androidtestingplayground.presentation.adapter.ImageAdapter
import kotlinx.android.synthetic.main.fragment_image_pick.*
import javax.inject.Inject

class ImagePickFragment @Inject constructor(
    val imageAdapter: ImageAdapter
): Fragment(R.layout.fragment_image_pick) {

    lateinit var shoppingViewModel: ShoppingViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shoppingViewModel = ViewModelProvider(requireActivity())[ShoppingViewModel::class.java]

        imageAdapter.setonImageClickListener {
            findNavController().popBackStack()
            shoppingViewModel.setCurrentImage(it)
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        rvImages.apply {
            adapter = imageAdapter
        }
    }
}