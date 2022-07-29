package android.libs.stickheaders

import android.libs.stickheaders.model.StickyHeadEntity
import android.libs.stickheaders.stickyheader.FullSpanUtil.onAttachedToRecyclerView
import android.libs.stickheaders.stickyheader.FullSpanUtil.onViewAttachedToWindow
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerViewAdapter<T, V : StickyHeadEntity<T>>(var mData: MutableList<V>) : RecyclerView.Adapter<RecyclerViewHolder>() {

    companion object {
        const val TYPE_DATA = 1
        const val TYPE_STICKY_HEAD = 2
        const val TYPE_SMALL_STICKY_HEAD_WITH_DATA = 3
    }

    protected var mItemClickListener: OnItemClickListener<T>? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        onAttachedToRecyclerView(recyclerView, this, TYPE_STICKY_HEAD)
    }

    override fun onViewAttachedToWindow(holder: RecyclerViewHolder) {
        onViewAttachedToWindow(holder, this, TYPE_STICKY_HEAD)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val holder = RecyclerViewHolder(parent.context, LayoutInflater.from(parent.context).inflate(getItemLayoutId(viewType), parent, false))
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener { view ->
                val position = holder.layoutPosition
                mItemClickListener!!.onItemClick(view, mData[position].data, position)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        bindData(holder, getItemViewType(position), position, mData[position].data)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun getItemViewType(position: Int): Int {
        return mData[position].itemType
    }

    abstract fun getItemLayoutId(viewType: Int): Int
    abstract fun bindData(holder: RecyclerViewHolder, viewType: Int, position: Int, item: T)

    fun add(pos: Int, item: V) {
        mData.add(pos, item)
        notifyItemInserted(pos)
    }

    fun delete(pos: Int) {
        mData.removeAt(pos)
        notifyItemRemoved(pos)
    }

    fun addMoreData(data: List<V>) {
        val startPos = mData.size
        mData.addAll(data)
        notifyItemRangeInserted(startPos, data.size)
    }

    val data: List<V>
        get() = mData

    fun setData(data: MutableList<V>) {
        mData = data
        notifyDataSetChanged()
    }

    fun setItemClickListener(itemClickListener: OnItemClickListener<T>?) {
        mItemClickListener = itemClickListener
    }
}