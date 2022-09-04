package com.mhmdawad.androidtestingplayground.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.mhmdawad.androidtestingplayground.R
import kotlinx.android.synthetic.main.item_image.view.*
import javax.inject.Inject


class ImageAdapter @Inject constructor(
    val glide: RequestManager
): RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private val _images = mutableListOf<String>()
    fun addImages(images: List<String>){
        _images.clear()
        _images.addAll(images)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent,false))
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(_images[position])
    }

    override fun getItemCount(): Int = _images.size

    inner class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(image: String){
            glide.load(image).into(itemView.ivShoppingImage)

            itemView.setOnClickListener {
                onImageClickListener?.let {
                    it(image)
                }
            }
        }
    }

    private var onImageClickListener: ((String)->Unit)? = null
    fun setonImageClickListener(item: (String)-> Unit){
        onImageClickListener = item
    }
}