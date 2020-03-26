package com.panyukov.lifehacktesttask.service

import com.panyukov.lifehacktesttask.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

object ImagePuller {

    fun pullImage(imgUrl: String): RequestCreator? {
        return Picasso.get()
            .load("http://megakohz.bget.ru/test_task/$imgUrl")
            .placeholder(R.drawable.img_placeholder)
            .fit()
            .centerCrop()
    }
}
