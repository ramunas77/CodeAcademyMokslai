package Objektai;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Studentas {
public String vardas;
public String pavarde;
public int id;
public Egzaminas egzaminas;
public List< Atsakymas > atsakymai = new ArrayList<Atsakymas>();
public HashMap<Long, String> atsakymuMap = new HashMap<Long , String>();

public void fromJson(JSONObject jsonStudentoAtsakymai){
    JSONObject jsonStudentas = (JSONObject) jsonStudentoAtsakymai.get ("studentas");
    id = Integer.parseInt((String) jsonStudentas.get("id"));
    vardas = (String) jsonStudentas.get("vardas");
    pavarde = (String) jsonStudentas.get("pavarde");


    JSONObject jsonEgzaminas = (JSONObject) jsonStudentoAtsakymai.get ("egzaminas");
    Egzaminas egzas =new Egzaminas();
    egzas.id = Integer.parseInt((String) jsonEgzaminas.get("id"));
    egzas.pavadinimas = (String) jsonEgzaminas.get("pavadinimas");
    egzas.tipas = (String) jsonEgzaminas.get("tipas");
    egzaminas = egzas;


    JSONArray jsonAtsakymai = (JSONArray) jsonStudentoAtsakymai.get ("atsakymai");
    jsonAtsakymai.forEach(ats -> {
        Atsakymas atsakymas = new Atsakymas();
        JSONObject jsonAtsakymas = (JSONObject) ats;
        atsakymas.klausimas =  (long) jsonAtsakymas.get("klausimas");
        atsakymas.atsakymas = (String) jsonAtsakymas.get("atsakymas");
        atsakymai.add(atsakymas);
        atsakymuMap.put(atsakymas.klausimas,atsakymas.atsakymas);
    });
}
}
