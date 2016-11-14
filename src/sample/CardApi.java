package sample;

import com.sun.istack.internal.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class CardApi {

    String urlBase = "https://api.magicthegathering.io/v1/cards?page=5&pageSize=20";
    /**
     * Recoge las cartas de una API y almacena datos en un array de objetos carta
     * con los datos que queremos guardar
     * @return
     */
    private ArrayList<Card> getCards(String jsonResponse) {

        ArrayList<Card> cartas = new ArrayList<>();

        try {
            JSONObject data  = new JSONObject(HttpUtils.get(urlBase));
            JSONArray jsonCards = data.getJSONArray("cards");

            for (int i = 0; i < jsonCards.length(); i++) {

                JSONObject jsonCard = jsonCards.getJSONObject(i);

                Card carta = new Card();

                carta.setName(jsonCard.getString("name"));
                carta.setRarity(jsonCard.getString("rarity"));
                carta.setType(jsonCard.getString("type"));
                carta.setImage(jsonCard.getString("imageUrl"));

                if(!jsonCard.has("text")){
                    carta.setText("no description");
                }else{
                    carta.setText(jsonCard.getString("text"));
                }
                cartas.add(carta);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cartas;
    }
}
