package com.damcoassignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.damcoassignment.base.BaseRecyclerAdapter
import com.damcoassignment.base.BaseViewHolder
import com.damcoassignment.base.ItemClickListener
import com.damcoassignment.databinding.ColorsItemLayoutBinding
import com.damcoassignment.model.ColorsListModel

class ColorsListAdapter(
    var context: Context,
    var listener: ItemClickListener
) : BaseRecyclerAdapter<ColorsListModel>() {

    override fun createBaseViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ColorsListModel> = ColorsViewHolder(
        ColorsItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        context,
        listener
    )

    override fun getItemViewType(position: Int): Int {
        return position
    }
}