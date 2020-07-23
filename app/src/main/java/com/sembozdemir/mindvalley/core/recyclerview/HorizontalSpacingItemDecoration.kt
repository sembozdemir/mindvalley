package com.sembozdemir.mindvalley.core.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class HorizontalSpacingItemDecoration(private val horizontalSpacing: Int) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.adapter == null) {
            return
        }
        val itemCount = parent.adapter?.itemCount ?: 0
        if (parent.getChildAdapterPosition(view) != itemCount - 1) {
            outRect.right = horizontalSpacing
        }
    }

}