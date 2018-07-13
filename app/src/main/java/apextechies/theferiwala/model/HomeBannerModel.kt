package apextechies.starbasket.model

import com.google.gson.annotations.SerializedName

class HomeBannerModel {
    @SerializedName("status")
var status: String?= null
    @SerializedName("msg")
var msg: String?= null
    @SerializedName("data")
var data: ArrayList<HomeBannerDataModel>?= null
}

class HomeBannerDataModel {
@SerializedName("id")
var id: String?= null
    @SerializedName("banner")
var banner: String?= null
    @SerializedName("name")
var name: String?= null
}
