package com.damcoassignment.adapter

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.damcoassignment.ImageSearchApplication
import com.damcoassignment.R
import com.damcoassignment.base.BaseViewHolder
import com.damcoassignment.base.ItemClickListener
import com.damcoassignment.databinding.ColorsItemLayoutBinding
import com.damcoassignment.model.ColorsListModel

class ColorsViewHolder(
    private var colorsItemLayoutBinding: ColorsItemLayoutBinding,
    private var context: Context,
    private var listener: ItemClickListener
) : BaseViewHolder<ColorsListModel>(colorsItemLayoutBinding.root) {

    override fun bindItem(item: ColorsListModel) {
        colorsItemLayoutBinding.headingTitle.text =
            ImageSearchApplication.instance.getString(R.string.title)
        colorsItemLayoutBinding.headingHex.text =
            ImageSearchApplication.instance.getString(R.string.hex)
        colorsItemLayoutBinding.textTitle.text = item.title
        colorsItemLayoutBinding.textHex.text = item.hex
        Glide.with(context).load(item.imageUrl).diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(colorsItemLayoutBinding.colorImageView)
        colorsItemLayoutBinding.iconHeart.setOnClickListener {
            listener.onLikeUnlikeClick(adapterPosition)
        }

        itemView.setOnClickListener { listener.onItemClick(adapterPosition) }

        if (item.isLiked) {
            colorsItemLayoutBinding.iconHeart.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_like_icon
                )
            )
        } else {
            colorsItemLayoutBinding.iconHeart.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_unlike_icon
                )
            )
        }
    }

    override fun onLongClick(p0: View?): Boolean {
        TODO("Not yet implemented")
    }


}