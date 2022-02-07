package de.maxizink.lightcontroller.discovery.room.model;

public enum RoomArchetype {

  LIVING_ROOM,
  KITCHEN,
  DINING,
  BEDROOM,
  KIDS_BEDROOM,
  BATHROOM,
  NURSERY,
  RECREATION,
  OFFICE,
  GYM,
  HALLWAY,
  TOILET,
  FRONT_DOOR,
  GARAGE,
  TERRACE,
  GARDEN,
  DRIVEWAY,
  CARPORT,
  HOME,
  DOWNSTAIRS,
  UPSTAIRS,
  TOP_FLOOR,
  ATTIC,
  GUEST_ROOM,
  STAIRCASE,
  LOUNGE,
  MAN_CAVE,
  COMPUTER,
  STUDIO,
  MUSIC,
  TV,
  READING,
  CLOSET,
  STORAGE,
  LAUNDRY_ROOM,
  BALCONY,
  PORCH,
  BARBECUE,
  POOL,
  OTHER;

  public static RoomArchetype getRoomArchetype(final String string) {
    return valueOf(string.toLowerCase());
  }

  }
