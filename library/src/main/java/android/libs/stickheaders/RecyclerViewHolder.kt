package android.libs.stickheaders

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.util.Linkify
import android.util.SparseArray
import android.view.View
import android.view.View.OnLongClickListener
import android.view.View.OnTouchListener
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class RecyclerViewHolder(var mContext: Context, itemView: View) : ViewHolder(itemView) {

    var mViews: SparseArray<View> = SparseArray()

    private fun <T : View> findViewById(viewId: Int): T {
        var view = mViews[viewId]
        if (view == null) {
            view = itemView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T
    }

    fun getView(viewId: Int): View = findViewById(viewId)

    fun getTextView(viewId: Int): TextView = getView(viewId) as TextView

    fun getButton(viewId: Int): Button = getView(viewId) as Button

    fun getCheckBox(viewId: Int): CheckBox = getView(viewId) as CheckBox

    fun getImageView(viewId: Int): ImageView = getView(viewId) as ImageView

    fun getImageButton(viewId: Int): ImageButton = getView(viewId) as ImageButton

    fun getEditText(viewId: Int): EditText = getView(viewId) as EditText

    fun setText(viewId: Int, text: String): RecyclerViewHolder {
        val tv = findViewById<TextView>(viewId)
        tv.text = text
        return this
    }

    fun setText(viewId: Int, text: CharSequence): RecyclerViewHolder {
        val tv = findViewById<TextView>(viewId)
        tv.text = text
        return this
    }

    fun setImageResource(viewId: Int, resId: Int): RecyclerViewHolder {
        val view = findViewById<ImageView>(viewId)
        view.setImageResource(resId)
        return this
    }

    fun setImageBitmap(viewId: Int, bitmap: Bitmap): RecyclerViewHolder {
        val view = findViewById<ImageView>(viewId)
        view.setImageBitmap(bitmap)
        return this
    }

    fun setImageDrawable(viewId: Int, drawable: Drawable): RecyclerViewHolder {
        val view = findViewById<ImageView>(viewId)
        view.setImageDrawable(drawable)
        return this
    }

    fun setBackgroundColor(viewId: Int, color: Int): RecyclerViewHolder {
        val view = findViewById<View>(viewId)
        view.setBackgroundColor(color)
        return this
    }

    fun setBackgroundRes(viewId: Int, backgroundRes: Int): RecyclerViewHolder {
        val view = findViewById<View>(viewId)
        view.setBackgroundResource(backgroundRes)
        return this
    }

    fun setTextColor(viewId: Int, textColor: Int): RecyclerViewHolder {
        val view = findViewById<TextView>(viewId)
        view.setTextColor(textColor)
        return this
    }

    fun setTextColorRes(viewId: Int, textColorRes: Int): RecyclerViewHolder {
        val view = findViewById<TextView>(viewId)
        view.setTextColor(ContextCompat.getColor(mContext, textColorRes))
        return this
    }

    fun setAlpha(viewId: Int, value: Float): RecyclerViewHolder {
        ViewCompat.setAlpha(findViewById(viewId), value)
        return this
    }

    fun setViewGone(viewId: Int, gone: Boolean): RecyclerViewHolder {
        val view = findViewById<View>(viewId)
        view.visibility = if (gone) View.GONE else View.VISIBLE
        return this
    }

    fun setViewInvisible(viewId: Int, invisible: Boolean): RecyclerViewHolder {
        val view = findViewById<View>(viewId)
        view.visibility = if (invisible) View.INVISIBLE else View.VISIBLE
        return this
    }

    fun linkify(viewId: Int): RecyclerViewHolder {
        val view = findViewById<TextView>(viewId)
        Linkify.addLinks(view, Linkify.ALL)
        return this
    }

    fun setTypeface(typeface: Typeface?, vararg viewIds: Int): RecyclerViewHolder {
        for (viewId in viewIds) {
            val view = findViewById<TextView>(viewId)
            view.typeface = typeface
            view.paintFlags = view.paintFlags or Paint.SUBPIXEL_TEXT_FLAG
        }
        return this
    }

    fun setProgress(viewId: Int, progress: Int): RecyclerViewHolder {
        val view = findViewById<ProgressBar>(viewId)
        view.progress = progress
        return this
    }

    fun setProgress(viewId: Int, progress: Int, max: Int): RecyclerViewHolder {
        val view = findViewById<ProgressBar>(viewId)
        view.max = max
        view.progress = progress
        return this
    }

    fun setMax(viewId: Int, max: Int): RecyclerViewHolder {
        val view = findViewById<ProgressBar>(viewId)
        view.max = max
        return this
    }

    fun setRating(viewId: Int, rating: Float): RecyclerViewHolder {
        val view = findViewById<RatingBar>(viewId)
        view.rating = rating
        return this
    }

    fun setRating(viewId: Int, rating: Float, max: Int): RecyclerViewHolder {
        val view = findViewById<RatingBar>(viewId)
        view.max = max
        view.rating = rating
        return this
    }

    fun setTag(viewId: Int, tag: Any?): RecyclerViewHolder {
        val view = findViewById<View>(viewId)
        view.tag = tag
        return this
    }

    fun setTag(viewId: Int, key: Int, tag: Any?): RecyclerViewHolder {
        val view = findViewById<View>(viewId)
        view.setTag(key, tag)
        return this
    }

    fun setChecked(viewId: Int, checked: Boolean): RecyclerViewHolder {
        val view = findViewById<View>(viewId) as Checkable
        view.isChecked = checked
        return this
    }

    /**
     * 关于事件的
     */
    fun setOnClickListener(viewId: Int, listener: View.OnClickListener): RecyclerViewHolder {
        val view = findViewById<View>(viewId)
        view.setOnClickListener(listener)
        return this
    }

    fun setOnTouchListener(viewId: Int, listener: OnTouchListener): RecyclerViewHolder {
        val view = findViewById<View>(viewId)
        view.setOnTouchListener(listener)
        return this
    }

    fun setOnLongClickListener(viewId: Int, listener: OnLongClickListener): RecyclerViewHolder {
        val view = findViewById<View>(viewId)
        view.setOnLongClickListener(listener)
        return this
    }
}