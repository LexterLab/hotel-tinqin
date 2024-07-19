package com.tinqinacademy.hotel.api;

public class RestAPIRoutes {
    public static final String ROOT = "/api/v1";
    public static final String HOTEL = ROOT + "/hotel";
    public static final String SYSTEM = ROOT + "/system";
    public static final String AUTH = ROOT + "/auth";
    public static final String SEARCH_ROOMS = HOTEL + "/rooms";
    public static final String GET_ROOM_DETAILS = HOTEL + "/{roomId}";
    public static final String BOOK_ROOM = HOTEL + "/{roomId}";
    public static final String UNBOOK_ROOM = HOTEL + "/{roomId}";
    public static final String REGISTER_VISITOR = SYSTEM + "/register";
    public static final String GET_VISITORS_REPORT = SYSTEM + "/register";
    public static final String CREATE_ROOM = SYSTEM + "/room";
    public static final String UPDATE_ROOM = SYSTEM + "/room/{roomId}";
    public static final String PARTIAL_UPDATE_ROOM = SYSTEM + "/room/{roomId}";
    public static final String DELETE_ROOM = SYSTEM + "/room/{roomId}";
    public static final String SIGN_UP = AUTH + "/signup";
}
