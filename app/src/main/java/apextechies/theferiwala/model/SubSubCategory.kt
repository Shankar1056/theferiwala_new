package apextechies.starbasket.model

import com.google.gson.annotations.SerializedName

class SubSubCategory {

    @SerializedName("status")
val status: String?= null
    @SerializedName("msg")
val msg: String?= null
    @SerializedName("data")
val data: ArrayList<SubSubDataCategory>?= null
}

class SubSubDataCategory {
@SerializedName("id")
val id: String?= null
    @SerializedName("sub_cat_id")
val sub_cat_id: String?= null
    @SerializedName("name")
val name: String?= null
}
