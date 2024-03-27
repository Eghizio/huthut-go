package com.jw.huthutgo.domain

import com.jw.huthutgo.data.apartments
import com.jw.huthutgo.domain.model.Apartment
import com.jw.huthutgo.domain.model.ApartmentsFilter
import com.jw.huthutgo.domain.model.Location
import com.jw.huthutgo.repositories.ApartmentsRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class DefaultApartmentsService(private val apartmentsRepository: ApartmentsRepository) : ApartmentsService {
    // Todo: Logging

    override fun getAllApartments(page: Int, limit: Int): List<Apartment> =
        apartmentsRepository.getAllApartments(page, limit) // Todo: Pagination?

    override fun addApartment(apartment: Apartment): UUID =
        apartmentsRepository.addApartment(apartment)

    override fun searchApartmentsByName(name: String): List<Apartment> =
        apartments.filter { it.name.lowercase().contains(name.lowercase()) }

    override fun searchApartmentsByLocation(location: Location, radius: Double): List<Apartment> =
        apartments.filter { it.location.inRadiusRange(location, radius) }

    override fun filterApartments(ranges: List<ApartmentsFilter>): List<Apartment> =
        ranges.fold(apartments.toList()) { filteredApartments, apartmentFilter -> apartmentFilter.filter(filteredApartments) }
//    {
//        val apartments = apartmentsRepository.getAllApartments(1, 20)
//        return ranges.flatMap { range -> range.filter(apartments) }.distinct()
//    }

    override fun getFavouriteApartments(): List<Apartment> =
        // Todo: Should it be a favouriteRepository? If so then maybe a different service/controller?
        apartmentsRepository.getFavouriteApartments()

    override fun addFavouriteApartment(id: UUID): UUID =
        apartmentsRepository.addFavourite(id)

    override fun removeFavouriteApartment(id: UUID): UUID =
        apartmentsRepository.removeFavouriteApartment(id)
}
