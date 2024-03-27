package com.jw.huthutgo.data

import com.jw.huthutgo.domain.model.Apartment
import java.util.UUID

val apartmentA1 = Apartment(
    id = UUID.randomUUID(),
    name = "A1",
    area = 42.12,
    floor = 1,
    rooms = 2,
    price = 52400000,
    location = SchibstedOffice,
    parkingLots = listOf(parkingLotA1),
    storageRooms = listOf(storageRoomA1),
    loggias = listOf(loggiaA1),
    gardens = listOf(),
)

val apartmentA2 = Apartment(
    id = UUID.fromString("00000000-0000-0000-0000-000000000000"),
    name = "A2",
    area = 52.24,
    floor = 0,
    rooms = 3,
    price = 78400000,
    location = SchibstedOffice,
    parkingLots = listOf(parkingLotA2),
    storageRooms = listOf(storageRoomA2),
    loggias = listOf(loggiaA2),
    gardens = listOf(gardenA2),
)

val apartmentA3 = Apartment(
    id = UUID.randomUUID(),
    name = "A3",
    area = 21.37,
    floor = 2,
    rooms = 1,
    price = 54400000,
    location = Ostrava,
    parkingLots = listOf(parkingLotA3),
    storageRooms = listOf(),
    loggias = listOf(),
    gardens = listOf(),
)

val apartmentB1 = Apartment(
    id = UUID.randomUUID(),
    name = "B1",
    area = 42.69,
    floor = 7,
    rooms = 2,
    price = 69200000,
    location = Ostrava,
    parkingLots = listOf(parkingLotB1),
    storageRooms = listOf(),
    loggias = listOf(),
    gardens = listOf(),
)

val apartmentC1 = Apartment(
    id = UUID.randomUUID(),
    name = "C1",
    area = 420.69,
    floor = 0,
    rooms = 9,
    price = 133700000,
    location = McDonalds,
    parkingLots = listOf(parkingLotC1a, parkingLotC1b),
    storageRooms = listOf(storageRoomC1),
    loggias = listOf(loggiaC1a, loggiaC1b),
    gardens = listOf(gardenC1),
)

val apartments = mutableListOf(
    apartmentA1,
    apartmentA2,
    apartmentA3,
    apartmentB1,
    apartmentC1,
)
