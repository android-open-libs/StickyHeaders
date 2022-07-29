package android.libs.demo.stickyheaders

import java.io.Serializable

class StockInfo(var itemType: Int) : Serializable {
    var stickyHeadName: String = ""
    var rate = 0.0
    var current_price: String = ""
    var stock_code: String = ""
    var stock_name: String = ""

    constructor(itemType: Int, stickyHeadName: String) : this(itemType) {
        this.stickyHeadName = stickyHeadName
    }

    var check = false
}