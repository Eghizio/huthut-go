package com.jw.huthutgo.domain.model

import java.util.UUID
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

/*
Apartments:
- Get all apartments
- Add an apartment
- Search apartment by params:
    - name
    - location? TBD
- Filter apartment by:
    - area
    - floor
    - rooms
    - price

    - loggia
    - garden

    - parking lot
    - storage room

 - Add favourites? Create some lists?

ParkingLot:
 - Get All

StorageRoom:
 - Get All
*/

data class Location(
    val lat: Double,
    val lon: Double
//    val city: String?,
//    val country: String?
) {
    companion object {
        const val EARTH_RADIUS_KM = 6371.0
    }

    fun inRadiusRange(location: Location, radius: Double): Boolean = distanceTo(location) <= radius

    private fun distanceTo(location: Location): Double {
        val aLat = Math.toRadians(lat)
        val aLon = Math.toRadians(lon)
        val bLat = Math.toRadians(location.lat)
        val bLon = Math.toRadians(location.lon)

        // Haversine formula
        val dLon = bLon - aLon
        val dLat = bLat - aLat
        val a = (sin(dLat / 2).pow(2.0) + (cos(aLat) * cos(bLat) * sin(dLon / 2).pow(2.0)))
        val c = 2 * asin(sqrt(a))

        return c * EARTH_RADIUS_KM
    }
}

data class Apartment(
    val id: UUID,
    val name: String,
    val area: Double,
    val floor: Int,
    val rooms: Int,
    val price: Int, // PLN * 100
    val location: Location,
    val parkingLots: List<ParkingLot>,
    val storageRooms: List<StorageRoom>,
    val loggias: List<Loggia>,
    val gardens: List<Garden>
)

data class ParkingLot(
    val id: UUID,
    val name: String,
    val area: Double,
    val price: Int
)

data class StorageRoom(
    val id: UUID,
    val name: String,
    val area: Double,
    val price: Int
)

data class Loggia(
    val id: UUID,
    val name: String,
    val area: Double
)

data class Garden(
    val id: UUID,
    val name: String,
    val area: Double
)

// TODO: Does it make any sense? As it will probably be written in SQL queries in the Repository layer.
// https://github.schibsted.io/spt-payment/query-service/blob/master/src/main/kotlin/com/schibsted/payment/queryservice/orders/infrastructure/ElasticsearchOrderRepository.kt#L100
// https://github.schibsted.io/spt-payment/query-service/blob/master/src/main/kotlin/com/schibsted/payment/queryservice/orders/infrastructure/search.kt
// Builder for SQL query step by step - Composite pattern
// Todo: Move it to different package, Filters or some utils?
interface ApartmentsFilter {
    fun filter(apartments: List<Apartment>): List<Apartment>
}

data class ApartmentTextFilter(val propertySelector: (Apartment) -> String, val text: String?) : ApartmentsFilter {
    override fun filter(apartments: List<Apartment>): List<Apartment> =
        apartments.filter { propertySelector(it).lowercase().contains(text?.lowercase() ?: "") }
}

data class ApartmentLocationFilter(val location: Location, val radius: Double) : ApartmentsFilter {
    override fun filter(apartments: List<Apartment>): List<Apartment> =
        apartments.filter { it.location.inRadiusRange(location, radius) }
}

data class ApartmentNumberRangeFilter(
    val propertySelector: (Apartment) -> Number,
    val min: Number?,
    val max: Number?
) : ApartmentsFilter {

    override fun filter(apartments: List<Apartment>): List<Apartment> {
        val lowerBound = min?.toDouble() ?: Double.NEGATIVE_INFINITY
        val upperBound = max?.toDouble() ?: Double.POSITIVE_INFINITY

        return apartments.filter { apartment -> propertySelector(apartment).toDouble() in lowerBound..upperBound }
    }
}
