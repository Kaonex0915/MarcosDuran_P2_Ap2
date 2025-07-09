import com.example.marcosduran_p2_ap2.data.remote.dto.ViajeDto
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ViajeApi {
    @GET("api/Viajes")
    @Headers("X-API-Key: test")
    suspend fun getViaje():List<ViajeDto>

    @PUT("api/Viajes/{id}")
    @Headers("X-API-Key: test")
    suspend fun getViajeById(@Path("id") id: Int): Response<ViajeDto>

    @POST("api/Viajes")
    @Headers("X-API-Key: test")
    suspend fun postViaje(@Body viajes: ViajeDto): ViajeDto

    @DELETE("api/Viajes/{id}")
    @Headers("X-API-Key: test")
    suspend fun deleteViaje(@Path("id") id: Int?): Response<Unit>

}
//nota para esclarecer que el dia que se preparo y se indico lo que debe tenerse antes del parcial no me encontraba presente, por lo cual estos archivos son creados ahora.


