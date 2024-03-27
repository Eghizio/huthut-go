package com.jw.huthutgo.repositories

import com.jw.huthutgo.data.apartments
import com.jw.huthutgo.data.favourites
import com.jw.huthutgo.domain.model.Apartment
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class DbApartmentsRepository : ApartmentsRepository {

    override fun getAllApartments(page: Int, limit: Int): List<Apartment> = apartments // Todo: Pagination?

    override fun addApartment(apartment: Apartment): UUID {
        apartments.add(apartment)
        return apartment.id
    }

    override fun getFavouriteApartments(): List<Apartment> =
        apartments.filter { favourites.contains(it.id) }

    override fun addFavourite(id: UUID): UUID {
        favourites.add(id)
        return id
    }

    override fun removeFavouriteApartment(id: UUID): UUID {
        favourites.remove(id)
        return id
    }
}
