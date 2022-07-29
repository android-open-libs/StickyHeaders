package android.libs.demo.stickyheaders

import java.io.Serializable

class StockEntity(
    var amplitude_list: List<StockInfo> = arrayListOf(), // 增幅榜
    var down_list: List<StockInfo> = arrayListOf(), // 跌幅榜
    var change_list: List<StockInfo> = arrayListOf(), // 换手率
    var increase_list: List<StockInfo> = arrayListOf() // 涨幅榜
) : Serializable
