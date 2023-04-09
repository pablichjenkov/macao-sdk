sealed class QueryParam(
    val key: String,
    val value: String
) {
    class Origin(val origin: String) : QueryParam("origin", origin)
    class MaxPrice(val maxPrice: String) : QueryParam("maxPrice", maxPrice)
    class CityCode(val cityCode: String) : QueryParam("cityCode", cityCode)
    class Adults(val adults: String) : QueryParam("adults", adults)
    class Radius(val radius: String) : QueryParam("radius", radius)
    class RadiusUnit(val radiusUnit: String) : QueryParam("radiusUnit", radiusUnit)
    class HotelSource(val hotelSource: String) : QueryParam("hotelSource", hotelSource)
    class HotelIds(val hotelIds: String) : QueryParam("hotelIds", hotelIds)
}



