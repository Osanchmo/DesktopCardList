package sample;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    public void initialize() {


        CardApi proc = new CardApi();

            ArrayList<Card> cartas = proc.doCall();

         for (Card temp:cartas) {
            System.out.println(temp.getName());
        }
    }
}
