package com.jw.huthutgo.repositories

import com.jw.huthutgo.domain.model.Apartment
import java.util.UUID

interface ApartmentsRepository {
    fun getAllApartments(page: Int, limit: Int): List<Apartment>
    fun addApartment(apartment: Apartment): UUID
    fun getFavouriteApartments(): List<Apartment>
    fun addFavourite(id: UUID): UUID
    fun removeFavouriteApartment(id: UUID): UUID
}
