package com.dw.deliveryapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dw.deliveryapp.util.AmountUtils
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
    val senderEmail: String
) : Parcelable {
    @IgnoredOnParcel
    var fav: Boolean? = false

    @IgnoredOnParcel
    var displayPrice: String = ""

    fun deliveryFeeInDouble(): Double {
        return try {
            AmountUtils.parseDouble(deliveryFee, "$#,###.##")
        } catch (e: NumberFormatException) {
            0.0
        }
    }

    fun surchargeInDouble(): Double {
        return try {
            AmountUtils.parseDouble(surcharge, "$#,###.##")
        } catch (e: NumberFormatException) {
            0.0
        }
    }
}
