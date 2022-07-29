package android.libs.demo.stickyheaders

import android.content.Context
import android.libs.stickheaders.OnItemClickListener
import android.libs.stickheaders.RecyclerViewAdapter
import android.libs.stickheaders.StickyHeadContainer
import android.libs.stickheaders.model.StickyHeadEntity
import android.libs.stickheaders.stickyheader.OnStickyChangeListener
import android.libs.stickheaders.stickyheader.StickyItemDecoration
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView
    private var mAdapter: MyAdapter? = null
    private var mStickyPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        loadData()
    }

    private fun loadData() {
        object : AsyncTask<Void, Void, String>() {
            override fun doInBackground(vararg voids: Void): String? {
                return getStrFromAssets(this@MainActivity, "rasking.json")
            }

            override fun onPostExecute(result: String) {
                parseAndSetData(result)
            }

        }.execute()
    }
    private fun initView() {
        val container = findViewById<StickyHeadContainer>(R.id.shc)
        val tvStockName = container.findViewById<TextView>(R.id.tv_stock_name)
        val checkBox = container.findViewById<CheckBox>(R.id.checkbox)

        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            mAdapter?.data?.get(mStickyPosition)?.data?.check = isChecked
            mAdapter?.notifyItemChanged(mStickyPosition)
        }

        container.setDataCallback(object : StickyHeadContainer.DataCallback {
            override fun onDataChange(pos: Int) {
                mStickyPosition = pos
                val item: StockInfo? = mAdapter?.data?.get(pos)?.data
                tvStockName.text = item?.stickyHeadName
                checkBox.isChecked = item?.check!!
                Log.e("11111111","pos = $pos")
            }
        })

        container.setOnClickListener {
            Toast.makeText(this, "点击了粘性头部：" + tvStockName.text, Toast.LENGTH_SHORT).show()
            showDatePickerDialog()
        }

        mRecyclerView = findViewById(R.id.recycler_view)
        mRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val stickyItemDecoration = StickyItemDecoration(container, RecyclerViewAdapter.TYPE_STICKY_HEAD)
        stickyItemDecoration.setOnStickyChangeListener(object : OnStickyChangeListener {
            override fun onScrollable(offset: Int) {
                container.scrollChild(offset)
                container.visibility = View.VISIBLE
            }

            override fun onInVisible() {
                container.reset()
                container.visibility = View.INVISIBLE
            }
        })
        mRecyclerView.addItemDecoration(stickyItemDecoration)

        mAdapter = MyAdapter(arrayListOf())
        mAdapter?.setItemClickListener(object : OnItemClickListener<StockInfo>{
            override fun onItemClick(view: View, data: StockInfo, position: Int) {
                if (data.itemType == RecyclerViewAdapter.TYPE_DATA) {
                    Toast.makeText(this@MainActivity, "点击了TYPE_DATA Item", Toast.LENGTH_SHORT).show()
                } else if (data.itemType == RecyclerViewAdapter.TYPE_STICKY_HEAD) {
                    Toast.makeText(this@MainActivity, "点击了TYPE_STICKY_HEAD Item", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun showDatePickerDialog() {
        Toast.makeText(this, "lalala", Toast.LENGTH_SHORT).show()
    }

    /**
     * @return Json数据（String）
     * @description 通过assets文件获取json数据，这里写的十分简单，没做循环判断。
     */
    fun getStrFromAssets(context: Context, name: String): String {
        val assetManager = context.assets
        try {
            val `is` = assetManager.open(name)
            val br = BufferedReader(InputStreamReader(`is`))
            val sb = StringBuilder()
            var str: String?
            while (br.readLine().also { str = it } != null) {
                sb.append(str)
            }
            return sb.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }

    private fun parseAndSetData(result: String) {
        val gson = Gson()
        val stockEntity: StockEntity = gson.fromJson(result, StockEntity::class.java)
        val data: MutableList<StockInfo> = ArrayList<StockInfo>()
        data.add(StockInfo(RecyclerViewAdapter.TYPE_STICKY_HEAD, "涨幅榜"))
        for (info in stockEntity.increase_list) {
            info.itemType = RecyclerViewAdapter.TYPE_DATA
            data.add(info)
        }
        data.add(StockInfo(RecyclerViewAdapter.TYPE_STICKY_HEAD, "跌幅榜"))
        for (info in stockEntity.down_list) {
            info.itemType = RecyclerViewAdapter.TYPE_DATA
            data.add(info)
        }
        data.add(StockInfo(RecyclerViewAdapter.TYPE_STICKY_HEAD, "换手率"))
        for (info in stockEntity.change_list) {
            info.itemType = RecyclerViewAdapter.TYPE_DATA
            data.add(info)
        }
        data.add(StockInfo(RecyclerViewAdapter.TYPE_STICKY_HEAD, "振幅榜"))
        for (info in stockEntity.amplitude_list) {
            info.itemType = RecyclerViewAdapter.TYPE_DATA
            data.add(info)
        }
        val list: MutableList<StickyHeadEntity<StockInfo>> = ArrayList<StickyHeadEntity<StockInfo>>(data.size)
        for (info in data) {
            list.add(StickyHeadEntity(info, info.itemType, "${info.stickyHeadName}"))
        }
        list.add(0,StickyHeadEntity(stockEntity.increase_list[0], 4, "stickyHeadName"))
        mAdapter?.setData(list)
        mRecyclerView.setAdapter(mAdapter)
    }
}