package com.jw.huthutgo.domain

import com.jw.huthutgo.domain.model.Apartment
import com.jw.huthutgo.domain.model.ApartmentsFilter
import com.jw.huthutgo.domain.model.Location
import java.util.UUID

interface ApartmentsService {
    fun getAllApartments(page: Int = 1, limit: Int = 20): List<Apartment>
    fun addApartment(apartment: Apartment): UUID

    fun searchApartmentsByName(name: String): List<Apartment>
    fun searchApartmentsByLocation(location: Location, radius: Double): List<Apartment>
    fun filterApartments(ranges: List<ApartmentsFilter>): List<Apartment>

    fun getFavouriteApartments(): List<Apartment>
    fun addFavouriteApartment(id: UUID): UUID
    fun removeFavouriteApartment(id: UUID): UUID
}
