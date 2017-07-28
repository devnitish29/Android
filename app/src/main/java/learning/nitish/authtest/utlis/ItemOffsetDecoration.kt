/*
 * Created by Nitish Singh on 29/7/17 4:42 AM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 29/7/17 4:33 AM
 */

package learning.nitish.authtest.utlis

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by Nitish Singh Rathore on 29/7/17.
 */


/**
 * A Decorator class to provide padding in list items used in recyclerview
 */
class ItemOffsetDecoration(private val offset: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State?) {

        // Add padding only to the zeroth item
        if (parent.getChildAdapterPosition(view) == 0) {

            outRect.right = offset
            outRect.left = offset
            outRect.top = offset
            outRect.bottom = offset
        }
    }
}

