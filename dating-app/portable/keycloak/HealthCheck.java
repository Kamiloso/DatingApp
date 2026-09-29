import java.net.HttpURLConnection;
import java.net.URL;

public class HealthCheck {
    public static void main(String[] args) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL("http://localhost:8080/health/ready").openConnection();
            int responseCode = conn.getResponseCode();
            
            if (responseCode == 200) {
                System.exit(0);
            } else {
                System.err.println("Health check failed. HTTP Status: " + responseCode);
                System.exit(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
