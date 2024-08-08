package com.tinqinacademy.hotel.api;

public class RouteExports {
    public static final String GET_ROOM = "GET /api/v1/hotel/{roomId}";
    public static final String SEARCH_ROOM = "GET /api/v1/hotel/rooms" +
            "?startDate={startDate}&endDate={endDate}&bedCount={bedCount}&bedSize={bedSize}&bathroomType={bathroomType}";
    public static final String BOOK_ROOM = "POST " + RestAPIRoutes.BOOK_ROOM;
    public static final String UNBOOK_ROOM = "DELETE " + RestAPIRoutes.UNBOOK_ROOM;
    public static final String REGISTER_GUESTS = "POST " + RestAPIRoutes.REGISTER_VISITOR;
    public static final String GET_GUEST_REPORTS = "GET /api/v1/system/register?startDate={startDate}&endDate={endDate}" +
            "&firstName={firstName}&lastName={lastName}&phoneNo={phoneNo}&idCardNo={idCardNo}&idCardValidity={idCardValidity}" +
            "&idCardIssueAuthority={idCardIssueAuthority}&idCardIssueDate={idCardIssueDate}&roomNo={roomNo}";
    public static final String CREATE_ROOM = "POST " + RestAPIRoutes.CREATE_ROOM;
    public static final String UPDATE_ROOM = "PUT " + RestAPIRoutes.UPDATE_ROOM;
    public static final String PARTIAL_UPDATE_ROOM = "PATCH " + RestAPIRoutes.PARTIAL_UPDATE_ROOM;
    public static final String DELETE_ROOM = "DELETE " + RestAPIRoutes.DELETE_ROOM;
}