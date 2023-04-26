sealed class QueryParam(
    val key: String,
    val value: String
) {
    class Adults(val adults: String) : QueryParam("adults", adults)
    class BestRateOnly(val bestRateOnly: String) : QueryParam("bestRateOnly", bestRateOnly)
    class CheckInDate(val checkInDate: String) : QueryParam("checkInDate", checkInDate)
    class CheckOutDate(val checkOutDate: String) : QueryParam("checkOutDate", checkOutDate)
    class CityCode(val cityCode: String) : QueryParam("cityCode", cityCode)
    class CountryCode(val countryCode: String) : QueryParam("countryCode", countryCode)
    class HotelSource(val hotelSource: String) : QueryParam("hotelSource", hotelSource)
    class HotelIds(val hotelIds: String) : QueryParam("hotelIds", hotelIds)
    class Keyword(val keyword: String) : QueryParam("keyword", keyword)
    class Max(val max: String) : QueryParam("max", max)
    class MaxPrice(val maxPrice: String) : QueryParam("maxPrice", maxPrice)
    class Origin(val origin: String) : QueryParam("origin", origin)
    class PaymentPolicy(val paymentPolicy: String) : QueryParam("paymentPolicy", paymentPolicy)
    class Radius(val radius: String) : QueryParam("radius", radius)
    class RadiusUnit(val radiusUnit: String) : QueryParam("radiusUnit", radiusUnit)
    class RoomQuantity(val roomQuantity: String) : QueryParam("roomQuantity", roomQuantity)
    class SubType(val subType: String) : QueryParam("subType", subType)
}



