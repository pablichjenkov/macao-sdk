import kotlinx.serialization.SerialName

// TODO: Provide this from BuildConfig
internal val apiHost = "https://test.api.amadeus.com"

@kotlinx.serialization.Serializable
data class AmadeusError(
    val error: String,
    @SerialName("error_description")
    val errorDescription: String,
    val code: Int,
    val title: String
)