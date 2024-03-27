package com.jw.huthutgo.rest

import com.jw.huthutgo.domain.model.Apartment
import com.jw.huthutgo.domain.model.ApartmentLocationFilter
import com.jw.huthutgo.domain.model.ApartmentNumberRangeFilter
import com.jw.huthutgo.domain.model.ApartmentTextFilter
import com.jw.huthutgo.domain.ApartmentsService
import com.jw.huthutgo.domain.model.Location
import com.jw.huthutgo.rest.model.ApartmentInput
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping(value = ["/api/apartments"])
class ApartmentsController(private val apartmentsService: ApartmentsService) {

    @GetMapping()
    fun getAllApartments(
        @RequestParam(required = false, name = "page", defaultValue = "1") page: Int,
        @RequestParam(required = false, name = "limit", defaultValue = "20") limit: Int
    ): ResponseEntity<List<Apartment>> {
        return ResponseEntity.ok(apartmentsService.getAllApartments(page, limit))
    }

    @PostMapping()
    fun addApartment(@RequestBody input: ApartmentInput): ResponseEntity<Map<String, UUID>> {
        val id = apartmentsService.addApartment(input.toApartment())
        return ResponseEntity.ok(mapOf("id" to id))
    }

    // /search /filter
    @GetMapping("/search/all")
    fun searchAllApartments(
        @RequestParam(required = false, name = "name", defaultValue = "") name: String,
        // Todo: Figure out how to handle null lat/lon
        @RequestParam(required = false, name = "lat", defaultValue = "50.0697129") lat: Double,
        @RequestParam(required = false, name = "lon", defaultValue = "19.9527031") lon: Double,
        @RequestParam(required = false, name = "radius", defaultValue = "10") radius: Double,

        @RequestParam(required = false, name = "area[gte]") minArea: Double?,
        @RequestParam(required = false, name = "area[lte]") maxArea: Double?,
        @RequestParam(required = false, name = "floor[gte]") minFloor: Int?,
        @RequestParam(required = false, name = "floor[lte]") maxFloor: Int?,
        @RequestParam(required = false, name = "rooms[gte]") minRooms: Int?,
        @RequestParam(required = false, name = "rooms[lte]") maxRooms: Int?,
        @RequestParam(required = false, name = "price[gte]") minPrice: Int?,
        @RequestParam(required = false, name = "price[lte]") maxPrice: Int?
    ): ResponseEntity<List<Apartment>> {
        val searchResults = apartmentsService.filterApartments(
            listOf(
                ApartmentTextFilter(Apartment::name, name),
                ApartmentLocationFilter(Location(lat, lon), radius),
                ApartmentNumberRangeFilter(Apartment::area, minArea, maxArea),
                ApartmentNumberRangeFilter(Apartment::floor, minFloor, maxFloor),
                ApartmentNumberRangeFilter(Apartment::rooms, minRooms, maxRooms),
                ApartmentNumberRangeFilter(Apartment::price, minPrice, maxPrice)
            )
        )

        return ResponseEntity.ok(searchResults)
    }

    @GetMapping("/favourite")
    fun getFavouriteApartments(): ResponseEntity<List<Apartment>> {
        return ResponseEntity.ok(apartmentsService.getFavouriteApartments())
    }

    @PostMapping("/favourite/{id}")
    fun addFavouriteApartment(@PathVariable(required = true, name = "id") id: UUID): ResponseEntity<UUID> {
        return ResponseEntity.ok(apartmentsService.addFavouriteApartment(id))
    }

    @DeleteMapping("/favourite/{id}")
    fun removeFavouriteApartment(@PathVariable(required = true, name = "id") id: UUID): ResponseEntity<UUID> {
        return ResponseEntity.ok(apartmentsService.removeFavouriteApartment(id))
    }
}
