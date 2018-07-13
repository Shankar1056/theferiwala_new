package apextechies.starbasket.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.google.gson.annotations.SerializedName

class CategoryModel {
    @SerializedName("status")
val status: String?= null
    @SerializedName("msg")
val msg: String?= null
    @SerializedName("data")
val data: ArrayList<CategoryDataModel>?= null
}

@Parcelize
 data class CategoryDataModel(
    @SerializedName("id")
    val id: String?= null,
    @SerializedName("name")
    val name: String?= null,
    @SerializedName("icon")
    val icon: String?= null,
    @SerializedName("subcat")
    val subcat: ArrayList<CategorysSubcatModel>?= null
): Parcelable

@Parcelize
data class CategorysSubcatModel(
    @SerializedName("id")
    val id: String?= null,
    @SerializedName("cat_id")
    val cat_id: String?= null,
    @SerializedName("name")
    val name: String?= null,
    @SerializedName("icon")
    val icon: String?= null
): Parcelable
