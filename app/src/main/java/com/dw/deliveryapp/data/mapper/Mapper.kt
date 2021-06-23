package com.dw.deliveryapp.data.mapper

interface Mapper<E, D> {
    fun fromEntity(entity: E): D
    fun toEntity(domain: D): E
    fun fromEntityList(initial: List<E>): List<D>
    fun toEntityList(initial: List<D>): List<E>
}