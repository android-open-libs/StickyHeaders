package android.libs.demo.stickyheaders

import android.libs.stickheaders.RecyclerViewAdapter
import android.libs.stickheaders.RecyclerViewHolder
import android.libs.stickheaders.model.StickyHeadEntity
import android.libs.stickheaders.stickyheader.FullSpanUtil.onAttachedToRecyclerView
import android.libs.stickheaders.stickyheader.FullSpanUtil.onViewAttachedToWindow
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(data: MutableList<StickyHeadEntity<StockInfo>>) : RecyclerViewAdapter<StockInfo, StickyHeadEntity<StockInfo>>(data), CompoundButton.OnCheckedChangeListener {

    companion object {
        const val TYPE_HEAD = 4
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        onAttachedToRecyclerView(recyclerView, this, TYPE_STICKY_HEAD)
    }

    override fun onViewAttachedToWindow(holder: RecyclerViewHolder) {
        super.onViewAttachedToWindow(holder)
        onViewAttachedToWindow(holder, this, TYPE_STICKY_HEAD)
    }

    override fun getItemLayoutId(viewType: Int): Int {
        when (viewType) {
            TYPE_HEAD -> return R.layout.item_stock_header
            TYPE_STICKY_HEAD -> return R.layout.item_stock_sticky_head
            TYPE_DATA -> return R.layout.item_stock_data
            TYPE_SMALL_STICKY_HEAD_WITH_DATA -> return R.layout.item_stock_small_sticky_data
        }
        return 0
    }

    override fun bindData(holder: RecyclerViewHolder, viewType: Int, position: Int, item: StockInfo) {
        val type = holder.itemViewType
        when (type) {
            TYPE_STICKY_HEAD -> {
                val checkBox = holder.getCheckBox(R.id.checkbox)
                checkBox.tag = position
                checkBox.setOnCheckedChangeListener(this)
                checkBox.isChecked = item.check
                holder.setText(R.id.tv_stock_name, item.stickyHeadName)
            }
            TYPE_DATA -> setData(holder, item)
            TYPE_SMALL_STICKY_HEAD_WITH_DATA -> {
//                setData(holder, item);
//                holder.setText(R.id.tv_stock_name, item.stickyHeadName);
            }
        }
    }

    private fun setData(holder: RecyclerViewHolder, item: StockInfo) {
        val stockNameAndCode = "${item.stock_name}\n${item.stock_code}"
        holder.setText(R.id.tv_stock_name_code, stockNameAndCode)
            .setText(R.id.tv_current_price, item.current_price)
            .setText(R.id.tv_rate, (if (item.rate < 0) String.format("%.2f", item.rate) else "+" + String.format("%.2f", item.rate)) + "%")
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        val pos = buttonView.tag as Int
        data[pos].data.check = isChecked
    }
}