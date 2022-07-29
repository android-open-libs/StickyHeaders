package android.libs.stickheaders.stickyheader

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.StaggeredGridLayoutManager

object FullSpanUtil {

    fun onAttachedToRecyclerView(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>, pinnedHeaderType: Int) {
        // 如果是网格布局，这里处理标签的布局占满一行
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            val oldSizeLookup = layoutManager.spanSizeLookup
            layoutManager.spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (adapter.getItemViewType(position) == pinnedHeaderType) {
                        layoutManager.spanCount
                    } else {
                        oldSizeLookup?.getSpanSize(position) ?: 1
                    }
                }
            }
        }
    }

    fun onViewAttachedToWindow(holder: ViewHolder, adapter: RecyclerView.Adapter<*>, pinnedHeaderType: Int) {
        // 如果是瀑布流布局，这里处理标签的布局占满一行
        val lp = holder.itemView.layoutParams
        if (lp is StaggeredGridLayoutManager.LayoutParams) {
            lp.isFullSpan = adapter.getItemViewType(holder.layoutPosition) == pinnedHeaderType
        }
    }
}