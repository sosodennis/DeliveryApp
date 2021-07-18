package com.dw.deliveryapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dw.deliveryapp.util.AmountUtils
import com.dw.deliveryapp.util.DateTimeFormat
import com.dw.deliveryapp.util.DateTimeFormatUtil
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "deliveries")
data class Delivery(
    @PrimaryKey val id: String,
    val remarks: String,
    val pickupTime: String,
    val goodsPicture: String,
    val deliveryFee: String,
    val surcharge: String,
    val routeStart: String,
    val routeEnd: String,
    val senderPhone: String,
    val senderName: String,
    val senderEmail: String,
    //For sorting if required.
    var totalPrice: Double
) : Parcelable {
    @IgnoredOnParcel
    var fav: Boolean = false

    fun displayTotalPrice(): String {
        return AmountUtils.format(totalPrice)
    }

    fun convertedPickupTime(): String {
        return try {
            DateTimeFormatUtil.convertFormat(
                pickupTime,
                "yyyy-MM-dd'T'HH:mm:ssz",
                DateTimeFormat.FORMAT_yyyy_MM_DD_HH_mm_ss
            )
        } catch (e: Exception) {
            ""
        }
    }

}
