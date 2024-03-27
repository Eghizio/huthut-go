package com.jw.huthutgo.rest.model

import com.jw.huthutgo.domain.model.Apartment
import com.jw.huthutgo.domain.model.Garden
import com.jw.huthutgo.domain.model.Location
import com.jw.huthutgo.domain.model.Loggia
import com.jw.huthutgo.domain.model.ParkingLot
import com.jw.huthutgo.domain.model.StorageRoom
import java.util.UUID

data class ApartmentInput(
    val name: String,
    val area: Double,
    val floor: Int,
    val rooms: Int,
    val price: Int,
    val location: Location,
    val parkingLots: List<ParkingLotInput>,
    val storageRooms: List<StorageRoomInput>,
    val loggias: List<LoggiaInput>,
    val gardens: List<GardenInput>
) {
    fun toApartment() = Apartment(
        UUID.randomUUID(),
        name,
        area,
        floor,
        rooms,
        price,
        location,
        parkingLots.map { it.toParkingLot() },
        storageRooms.map { it.toStorageRoom() },
        loggias.map { it.toLoggia() },
        gardens.map { it.toGarden() }
    )
}

data class ParkingLotInput(
    val name: String,
    val area: Double,
    val price: Int
) {
    fun toParkingLot() = ParkingLot(UUID.randomUUID(), name, area, price)
}

data class StorageRoomInput(
    val name: String,
    val area: Double,
    val price: Int
) {
    fun toStorageRoom() = StorageRoom(UUID.randomUUID(), name, area, price)
}

data class LoggiaInput(
    val name: String,
    val area: Double
) {
    fun toLoggia() = Loggia(UUID.randomUUID(), name, area)
}

data class GardenInput(
    val name: String,
    val area: Double
) {
    fun toGarden() = Garden(UUID.randomUUID(), name, area)
}
