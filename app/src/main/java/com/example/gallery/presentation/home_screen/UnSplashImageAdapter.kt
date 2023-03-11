package com.example.gallery.presentation.home_screen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.gallery.R
import com.example.gallery.databinding.UnsplashImageBinding
import com.example.gallery.models.UnSplashImage
import com.example.gallery.presentation.home_screen.UnSplashImageAdapter.UnSplashViewHolder


class UnSplashImageAdapter(private val listener: OnItemClickListener)
    : PagingDataAdapter<UnSplashImage, UnSplashViewHolder>(photos) {

     inner class UnSplashViewHolder(private val binding: UnsplashImageBinding)
        :RecyclerView.ViewHolder(binding.root){

         init {
             binding.root.setOnClickListener {
                 val position = bindingAdapterPosition
                 if (position != RecyclerView.NO_POSITION){
                     val item = getItem(position)
                     if (item != null){
                         listener.onItemClick(item)
                     }
                 }
             }
         }



            @SuppressLint("SetTextI18n")
            fun bind(image: UnSplashImage) {
                binding.apply {
                    Glide.with(itemView) //<--- item view is the whole layout, and we haven't reference for HomeFragment
                        .load(image.urls.regular)
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .error(R.drawable.ic_error)
                        .into(photo)
                    photographer.text = "photo by ${image.user.username} on UnSplash"
                    likes.text = image.likes.toString()

                }
            }
        }

    interface OnItemClickListener {
        fun onItemClick(image: UnSplashImage)
    }


    override fun onBindViewHolder(holder: UnSplashViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnSplashViewHolder {
        val binding = UnsplashImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UnSplashViewHolder(binding)
    }

    companion object {
        private val photos = object : DiffUtil.ItemCallback<UnSplashImage>() {
            override fun areItemsTheSame(oldItem: UnSplashImage, newItem: UnSplashImage): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(
                oldItem: UnSplashImage,
                newItem: UnSplashImage
            ): Boolean {
                return oldItem == newItem
            }
        }
    }




}
















