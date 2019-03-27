
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import static org.telegram.telegrambots.meta.api.objects.EntityType.URL;

public class Weather {


    public static String getWeather(String inMessage, Model model) throws IOException {
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" +
                            inMessage + "&units=metric&appid=e7872468c34da1464ecb6471b4f511d4");

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }



        JSONObject object = new JSONObject(result);
        model.setName(object.getString("name"));
        JSONObject main = object.getJSONObject("main");
        model.setTemp(main.getDouble("temp"));
        model.setHumidity(main.getDouble("humidity"));


        JSONArray getArray = object.getJSONArray("weather");
        for (int i=0; i < getArray.length(); i++ ) {
            JSONObject obj = getArray.getJSONObject(i);
            model.setIcon((String) obj.get("icon"));
            model.setMain((String) obj.get("main"));
        }
        result = "города: "+model.getName()+
                "\nТемпература: "+model.getTemp()+" C"+
                "\nВлажность: "+model.getHumidity()+" %"+
                "\nПогода: "+model.getMain();
                //"\nhttp://openweathermap.org/img/w"+model.getIcon()+".png";

        return result;
    }

}
