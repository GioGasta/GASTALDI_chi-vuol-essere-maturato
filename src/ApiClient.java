import com.google.gson.Gson;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient
{
    // final --> oggetto non modificabile
    private final HttpClient client = HttpClient.newHttpClient();

    public String fetchQuestions(int amount, String type, String difficulty)
    {
        String url = "https://opentdb.com/api.php?amount=" + amount + "&difficulty=" + difficulty + "&type" + type;

        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json") //stiamo chiedendo un'application.json
                .uri(java.net.URI.create(url)) //crea url
                .GET() //cosa si vuole prendere
                .build(); //costruisce

        HttpResponse<String> response;

        try
        {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (IOException | InterruptedException e)
        {
            throw new  RuntimeException("no response from API");
        }

        Gson gson = new Gson();

        ApiResponse ApiResponse = gson.fromJson(response.body(), ApiResponse.class);

        for (ApiQuestion q : ApiResponse.results)
        {
            System.out.println(q.question);
            System.out.println("Risposta corretta:" + q.correct_answer);
        }

        return response.body();
    }


}
