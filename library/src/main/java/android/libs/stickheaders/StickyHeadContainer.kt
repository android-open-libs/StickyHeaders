package android.libs.stickheaders

import android.content.Context
import android.libs.stickheaders.StickyHeadContainer
import android.libs.stickheaders.StickyHeadContainer.DataCallback
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import androidx.core.view.ViewCompat

/**
 * 如果不设置 mDataCallback 则不显示吸顶栏 [DataCallback]
 */
class StickyHeadContainer(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : ViewGroup(context, attrs, defStyleAttr) {

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    companion object {
        val TAG = StickyHeadContainer::class.java.simpleName
    }

    init {
        setOnClickListener {} // 屏蔽点击事件
    }

    private var mOffset = 0
    private var mLastOffset = Int.MIN_VALUE
    private var mLastStickyHeadPosition = Int.MIN_VALUE
    private var mLeft = 0
    private var mRight = 0
    private var mTop = 0
    private var mBottom = 0
    private var mDataCallback: DataCallback? = null
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var desireHeight: Int
        var desireWidth: Int
        val count = childCount
        require(count == 1) { "只允许容器添加1个子View！" }
        val child = getChildAt(0)
        // 测量子元素并考虑外边距
        measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0)
        // 获取子元素的布局参数
        val lp = child.layoutParams as MarginLayoutParams
        // 计算子元素宽度，取子控件最大宽度
        desireWidth = child.measuredWidth + lp.leftMargin + lp.rightMargin
        // 计算子元素高度
        desireHeight = child.measuredHeight + lp.topMargin + lp.bottomMargin

        // 考虑父容器内边距
        desireWidth += paddingLeft + paddingRight
        desireHeight += paddingTop + paddingBottom
        // 尝试比较建议最小值和期望值的大小并取大值
        desireWidth = Math.max(desireWidth, suggestedMinimumWidth)
        desireHeight = Math.max(desireHeight, suggestedMinimumHeight)
        // 设置最终测量值
        setMeasuredDimension(resolveSize(desireWidth, widthMeasureSpec), resolveSize(desireHeight, heightMeasureSpec))
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val child = getChildAt(0)
        val lp = child.layoutParams as MarginLayoutParams
        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        mLeft = paddingLeft + lp.leftMargin
        mRight = child.measuredWidth + mLeft
        mTop = paddingTop + lp.topMargin + mOffset
        mBottom = child.measuredHeight + mTop
        child.layout(mLeft, mTop, mRight, mBottom)
    }

    // 生成默认的布局参数
    override fun generateDefaultLayoutParams(): LayoutParams = super.generateDefaultLayoutParams()

    // 生成布局参数,将布局参数包装成我们的
    override fun generateLayoutParams(p: LayoutParams): LayoutParams = MarginLayoutParams(p)

    // 生成布局参数,从属性配置中生成我们的布局参数
    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams = MarginLayoutParams(context, attrs)

    // 查当前布局参数是否是我们定义的类型这在code声明布局参数时常常用到
    override fun checkLayoutParams(p: LayoutParams): Boolean = p is MarginLayoutParams

    fun scrollChild(offset: Int) {
        if (mLastOffset != offset) {
            mOffset = offset
            ViewCompat.offsetTopAndBottom(getChildAt(0), mOffset - mLastOffset)
        }
        mLastOffset = mOffset
    }

    val childHeight: Int
        get() = getChildAt(0).height

    fun onDataChange(stickyHeadPosition: Int) {
        if (mDataCallback != null && mLastStickyHeadPosition != stickyHeadPosition) {
            mDataCallback!!.onDataChange(stickyHeadPosition)
        }
        if (null == mDataCallback) {
            Log.w(TAG, "mDataCallback is null")
        }
        mLastStickyHeadPosition = stickyHeadPosition
    }

    fun reset() {
        mLastStickyHeadPosition = Int.MIN_VALUE
    }

    interface DataCallback {
        fun onDataChange(pos: Int)
    }

    fun setDataCallback(dataCallback: DataCallback?) {
        mDataCallback = dataCallback
    }
}