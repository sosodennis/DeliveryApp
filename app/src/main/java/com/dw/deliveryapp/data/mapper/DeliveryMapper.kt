package com.dw.deliveryapp.data.mapper

import com.dw.deliveryapp.data.dto.DeliveryDto
import com.dw.deliveryapp.data.model.Delivery

class DeliveryMapper : Mapper<Delivery, DeliveryDto> {
    override fun fromEntity(entity: Delivery) = DeliveryDto(
        entity.id,
        entity.remarks,
        entity.pickupTime,
        entity.goodsPicture,
        entity.deliveryFee,
        entity.surcharge,
        DeliveryDto.Route(entity.routeStart, entity.routeEnd),
        DeliveryDto.Sender(entity.senderPhone, entity.senderName, entity.senderEmail),
        entity.page
    )

    override fun toEntity(domain: DeliveryDto) = Delivery(
        domain.id,
        domain.remarks,
        domain.pickupTime,
        domain.goodsPicture,
        domain.deliveryFee,
        domain.surcharge,
        domain.route.start,
        domain.route.end,
        domain.sender.phone,
        domain.sender.name,
        domain.sender.email,
        domain.page
    )

    override fun fromEntityList(initial: List<Delivery>): List<DeliveryDto> {
        return initial.map { fromEntity(it) }
    }

    override fun toEntityList(initial: List<DeliveryDto>): List<Delivery> {
        return initial.map { toEntity(it) }
    }

}