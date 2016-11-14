package sample;

import com.sun.istack.internal.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class CardApi {

    @Nullable
    public ArrayList<Card> doCall() {
        try {
            String JsonResponse = HttpUtils.get("https://api.magicthegathering.io/v1/cards?page=5&pageSize=20");
            return getCards(JsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Recoge las cartas de una API y almacena datos en un array de objetos carta
     * con los datos que queremos guardar
     * @return
     */
    private ArrayList<Card> getCards(String jsonResponse) {

        ArrayList<Card> cartas = new ArrayList<>();

        try {
            JSONObject data  = new JSONObject(jsonResponse);
            JSONArray jsonCards = data.getJSONArray("cards");

            for (int i = 0; i < jsonCards.length(); i++) {

                JSONObject jsonCard = jsonCards.getJSONObject(i);

                Card carta = new Card();

                carta.setName(jsonCard.getString("name"));
                carta.setRarity(jsonCard.getString("rarity"));
                carta.setType(jsonCard.getString("type"));
                carta.setImage(jsonCard.getString("imageUrl"));
                //carta.setId(jsonCard.getString("id"));

                if(jsonCard.has("text")) carta.setText(jsonCard.getString("text"));

                if(jsonCard.has("colors")) {
                    String [] colors = new String[jsonCard.getJSONArray("colors").length()];
                    for (int j = 0; j <jsonCard.getJSONArray("colors").length() ; j++) {
                        colors[i] = jsonCard.getJSONArray("colors").get(i).toString();
                    }
                    carta.setColores(colors);
                }

                cartas.add(carta);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cartas;
    }
}
