package apextechies.starbasket.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

class SubCategoryModel {
    @SerializedName("status")
val status: String?= null
    @SerializedName("msg")
val msg: String?= null
    @SerializedName("data")
val data: String?= null
}

@Parcelize
data class SubCategoryDataModel(
        @SerializedName("id")
        val id: String?= null,
        @SerializedName("cat_id")
        val cat_id: String?= null,
        @SerializedName("name")
        val name: String?= null,
        @SerializedName("icon")
        val icon: String?= null
): Parcelable
