import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class IPResponse {
    @SerializedName("ip")
    private String IP;
    private String region;
    @SerializedName("country_name")
    private String countryName;
    @SerializedName("in_eu")
    private String inEU;
    private String latitude;
    private String longitude;
    @SerializedName("org")
    private String organization;

}