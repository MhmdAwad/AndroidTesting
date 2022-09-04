package com.mhmdawad.androidtestingplayground.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.mhmdawad.androidtestingplayground.R
import com.mhmdawad.androidtestingplayground.common.Resource
import kotlinx.android.synthetic.main.fragment_add_shopping_item.*
import javax.inject.Inject

class AddShoppingItemFragment @Inject constructor(
    private val glide: RequestManager
): Fragment(R.layout.fragment_add_shopping_item) {

    lateinit var shoppingViewModel: ShoppingViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shoppingViewModel = ViewModelProvider(requireActivity())[ShoppingViewModel::class.java]

        ivShoppingImage.setOnClickListener {
            findNavController().navigate(
                AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment()
            )
        }
        val callback = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                shoppingViewModel.setCurrentImage("")
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
        subscribeToObservers()
        btnAddShoppingItem.setOnClickListener {
            shoppingViewModel.insertShoppingItem(
                etShoppingItemName.text.toString(),
                etShoppingItemAmount.text.toString(),
                etShoppingItemPrice.text.toString(),
            )
        }
    }

    private fun subscribeToObservers() {
        shoppingViewModel.currentSelectedImage.observe(viewLifecycleOwner){
            glide.load(it).into(ivShoppingImage)
        }

        shoppingViewModel.insertShoppingItemStatus.observe(viewLifecycleOwner){
            it.getContentIfNotHandled()?.let { resource->
                when(resource){
                    is Resource.Success->{
                        Toast.makeText(requireContext(), "Shopping Item has been Added!", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }
                    is Resource.Error->{
                        Toast.makeText(requireContext(), resource.msg?: "An error occurred!", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading->{
                        /* START LOADING DIALOG */
                    }
                    is Resource.Idle->{
                        /* STOP LOADING DIALOG */
                    }
                }
            }
        }
    }
}