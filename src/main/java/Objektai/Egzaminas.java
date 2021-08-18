package Objektai;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Egzaminas {
    public int id;
    public String pavadinimas;
    public String tipas;
    public List<Atsakymas> atsakymai = new ArrayList<>();  //teisingi atsakymai
    public HashMap<Long, String> atsakymuMap = new HashMap<Long, String>();

    public void fromJson(JSONObject jsonEgzaminoKatalogas) {
        JSONObject jsonEgzaminas = (JSONObject) jsonEgzaminoKatalogas.get("egzaminas");
        id = Integer.parseInt((String) jsonEgzaminas.get("id"));
        pavadinimas = (String) jsonEgzaminas.get("pavadinimas");
        tipas = (String) jsonEgzaminas.get("tipas");
        JSONArray jsonAtsakymai = (JSONArray) jsonEgzaminoKatalogas.get("atsakymai");
        jsonAtsakymai.forEach(ats -> {

            Atsakymas atsakymas = new Atsakymas();
            JSONObject jsonAtsakymas = (JSONObject) ats;
            atsakymas.klausimas = (long) jsonAtsakymas.get("klausimas");
            atsakymas.atsakymas = (String) jsonAtsakymas.get("atsakymas");
            atsakymai.add(atsakymas);
            atsakymuMap.put(atsakymas.klausimas, atsakymas.atsakymas);
        });
    }
}
