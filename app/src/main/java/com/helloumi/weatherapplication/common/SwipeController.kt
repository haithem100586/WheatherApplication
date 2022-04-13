package com.helloumi.weatherapplication.common

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.RecyclerView
import com.helloumi.weatherapplication.R
import com.helloumi.weatherapplication.common.ext.android.matchAny

/**
 * Creates a swipe menu for a RecyclerView with an action button.
 */
@SuppressLint("ClickableViewAccessibility")
class SwipeController(val context: Context,
                      private val buttonsActions: SwipeControllerActions,
                      private val iconAction: Drawable?
) : ItemTouchHelper.Callback() {

    private var swipeBack = false
    private var buttonShownState = ButtonsState.GONE
    private var buttonInstance: RectF? = null
    private var currentItemViewHolder: RecyclerView.ViewHolder? = null
    private var buttonWidth = context.resources.getDimension(R.dimen.button_swipe_action_width)

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return makeMovementFlags(0, LEFT)
    }

    override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        if (swipeBack) {
            swipeBack = buttonShownState != ButtonsState.GONE
            return 0
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection)
    }

    override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
    ) {
        var newDX = dX
        if (actionState == ACTION_STATE_SWIPE) {
            if (buttonShownState != ButtonsState.GONE) {
                if (buttonShownState == ButtonsState.RIGHT_VISIBLE) newDX = dX.coerceAtMost(-buttonWidth)
                super.onChildDraw(c, recyclerView, viewHolder, newDX, dY, actionState, isCurrentlyActive)
            } else {
                val params = TouchListenerParams(c, recyclerView, viewHolder, newDX, dY, actionState, isCurrentlyActive)
                setTouchListener(params)
            }
        }
        if (buttonShownState == ButtonsState.GONE) {
            super.onChildDraw(c, recyclerView, viewHolder, newDX, dY, actionState, isCurrentlyActive)
        }
        currentItemViewHolder = viewHolder
    }

    /**
     * Handles swipe on item list.
     *
     * @param touchListenerParams the listener's params.
     */
    private fun setTouchListener(touchListenerParams: TouchListenerParams) {
        touchListenerParams.recyclerView.setOnTouchListener { _, event ->
            swipeBack = event.action.matchAny(MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP)
            if (swipeBack) {
                if (touchListenerParams.dX < -buttonWidth) buttonShownState = ButtonsState.RIGHT_VISIBLE
                if (buttonShownState != ButtonsState.GONE) {
                    setTouchDownListener(touchListenerParams)
                    setItemsClickable(touchListenerParams.recyclerView, false)
                }
            }
            false
        }
    }

    /**
     * Handles click on button without removing the other listener items.
     *
     * @param touchListenerParams the listener's params.
     */
    private fun setTouchDownListener(touchListenerParams: TouchListenerParams) {
        touchListenerParams.recyclerView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                setTouchUpListener(touchListenerParams)
            }
            false
        }
    }

    /**
     * Handles click on button without removing the other listener items.
     *
     * @param touchListenerParams the listener's params.
     */
    private fun setTouchUpListener(touchListenerParams: TouchListenerParams) {
        touchListenerParams.recyclerView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                super@SwipeController.onChildDraw(
                        touchListenerParams.c,
                        touchListenerParams.recyclerView,
                        touchListenerParams.viewHolder,
                        0f,
                        touchListenerParams.dY,
                        touchListenerParams.actionState,
                        touchListenerParams.isCurrentlyActive
                )
                touchListenerParams.recyclerView.setOnTouchListener { _, _ -> false }
                setItemsClickable(touchListenerParams.recyclerView, true)
                swipeBack = false
                if (buttonInstance?.contains(event.x, event.y) == true) {
                    if (buttonShownState == ButtonsState.RIGHT_VISIBLE) {
                        buttonsActions.onRightClicked(touchListenerParams.viewHolder.adapterPosition)
                    }
                }
                buttonShownState = ButtonsState.GONE
                currentItemViewHolder = null
            }
            false
        }
    }

    /**
     * Makes item clickable.
     *
     * @param recyclerView the recyclerView.
     * @param isClickable whether or not the view is clickable.
     */
    private fun setItemsClickable(recyclerView: RecyclerView, isClickable: Boolean) {
        for (i in 0 until recyclerView.childCount) {
            recyclerView.getChildAt(i).isClickable = isClickable
        }
    }

    /**
     * Draws the action button.
     *
     * @param c the canvas to draw into.
     * @param viewHolder The view that was interacted by the user.
     */
    private fun drawButtons(c: Canvas, viewHolder: RecyclerView.ViewHolder) {
        // Calculate position of action icon
        val itemView: View = viewHolder.itemView

        buttonInstance = null

        val itemHeight = itemView.bottom - itemView.top
        val intrinsicWidth = iconAction?.intrinsicWidth ?: 0
        val intrinsicHeight = iconAction?.intrinsicHeight ?: 0
        val iconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val iconMargin = (itemHeight - intrinsicHeight) / 2
        val iconLeft = itemView.right - iconMargin - intrinsicWidth
        val iconRight = itemView.right - iconMargin
        val iconBottom = iconTop + intrinsicHeight

        val rightButton = RectF(iconLeft.toFloat(), iconTop.toFloat(), iconRight.toFloat(), iconBottom.toFloat())

        iconAction?.setBounds(iconLeft, iconTop, iconRight, iconBottom)
        if (buttonShownState == ButtonsState.RIGHT_VISIBLE) {
            buttonInstance = rightButton
            iconAction?.draw(c)
        }
    }

    /**
     * Draws buttons on the current viewHolder.
     *
     * @param c canvas to draw into.
     */
    fun onDraw(c: Canvas) {
        currentItemViewHolder?.let {
            drawButtons(c, it)
        }
    }

    /**
     * @property c the canvas which RecyclerView is drawing its children.
     * @property recyclerView the RecyclerView to which ItemTouchHelper is attached to.
     * @property viewHolder the ViewHolder which is being interacted by the User or it was
     * interacted and simply animating to its original position.
     * @property dX the amount of horizontal displacement caused by user's action.
     * @property dY the amount of vertical displacement caused by user's action.
     * @property actionState the type of interaction on the View. Is either {@link
     * #ACTION_STATE_DRAG} or {@link #ACTION_STATE_SWIPE}.
     * @property isCurrentlyActive true if this view is currently being controlled by the user or
     * false it is simply animating back to its original state.
     */
    private data class TouchListenerParams(val c: Canvas,
                                           val recyclerView: RecyclerView,
                                           val viewHolder: RecyclerView.ViewHolder,
                                           val dX: Float,
                                           val dY: Float,
                                           val actionState: Int,
                                           val isCurrentlyActive: Boolean)

    enum class ButtonsState {
        GONE, RIGHT_VISIBLE
    }

}
