import Objektai.Egzaminas;
import Objektai.Studentas;
import Objektai.Vertinimas;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class EgzaminuVertinimoSistema {
    List<Egzaminas> egzaminai = new ArrayList<>();
    List<Studentas> studentai = new ArrayList<>();
    List<Vertinimas> vertinimai = new ArrayList<>();
    public void gautiStudentuAtsakymai(String keliasIkiStudentuAtsakymu) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(keliasIkiStudentuAtsakymu)) {

            Object obj = jsonParser.parse(reader);
            JSONArray studentoAtsakymaiList = (JSONArray) obj;
            studentoAtsakymaiList.forEach(jsonObj -> {
                Studentas studentas = new Studentas();
                JSONObject jsonObject = (JSONObject) jsonObj;

                studentas.fromJson(jsonObject);
                studentai.add(studentas);
                System.out.println("Studento ID : " + studentas.id);
                System.out.println("Studento VARDAS : " + studentas.vardas);
                System.out.println("Studento PAVARDE : " + studentas.pavarde);
            });
        } catch (FileNotFoundException e) {
            System.out.println("failas nerastas,meginkite is naujo");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("blogas Json failas");
        }
    }

    public void gautiEgzaminoKataloga(String keliasIkiEgzaminoKatalogo) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(keliasIkiEgzaminoKatalogo)) {

            Object obj = jsonParser.parse(reader);
            JSONArray egzaminuList = (JSONArray) obj;
            //  studentoAtsakymaiList.forEach(std );
            egzaminuList.forEach(jsonObj -> {
                Egzaminas egzaminas = new Egzaminas();
                JSONObject jsonObject = (JSONObject) jsonObj;//raudonavo neisejo padaryti kitaip

                egzaminas.fromJson(jsonObject);
                egzaminai.add(egzaminas);
                System.out.println("Egzamino ID : " + egzaminas.id);
                System.out.println("Egzamino PAVADINIMAS : " + egzaminas.pavadinimas);
                System.out.println("Egzamino TIPAS : " + egzaminas.tipas);
              //  System.out.println("Egzamino teisingas atsakymas : " + egzaminas.atsakymai);
            });
        } catch (FileNotFoundException e) {
            System.out.println("failas nerastas,meginkite is naujo");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("blogas Json failas");
        }

    }

    public void palyginti() {
        studentai.forEach(studentas -> {
            System.out.println(studentas.vardas + " " + studentas.pavarde);
            Vertinimas vertinimas = new Vertinimas();
            vertinimas.id = 0;//nezinau kaip padaryti
            vertinimas.vardas = studentas.vardas;
            vertinimas.pavarde = studentas.pavarde;
            AtomicInteger teisingaiAtsakyti = new AtomicInteger();

            Egzaminas laikytasEgzaminas = egzaminai.stream().filter(egz -> egz.id == studentas.egzaminas.id).findFirst().get();
            studentas.atsakymai.forEach(ats -> {
                if (laikytasEgzaminas.atsakymuMap.get(ats.klausimas).equals(ats.atsakymas)) {
                    teisingaiAtsakyti.getAndIncrement();
                    System.out.println("Teisingas atsakymas");
                } else {
                    System.out.println("Neteisingas atsakymas");
                    System.out.println(laikytasEgzaminas.atsakymuMap.get(ats.klausimas));
                    System.out.println(ats.atsakymas);
                }
            });
            int balasUzVienaTeisingaAtsakyma = 10/ laikytasEgzaminas.atsakymuMap.size();
            vertinimas.egzaminoId = laikytasEgzaminas.id;
            vertinimas.ivertinimas = (int) teisingaiAtsakyti.get()*balasUzVienaTeisingaAtsakyma;
            vertinimai.add(vertinimas);
            System.out.println( "Pazymys : "+ vertinimas.ivertinimas);

        });
    }
    public void issaugotiIvertinimoFaila(){
        JSONArray jsonEgzaminai = new JSONArray();
        egzaminai.forEach(egz-> {
            JSONArray studentuRezultatai = new JSONArray();
            vertinimai.stream().filter(vertinimas -> vertinimas.egzaminoId == egz.id).forEach(surastasVertinimas->{
                JSONObject jsonVertinimas = new JSONObject();
                jsonVertinimas.put("id",surastasVertinimas.id);
                jsonVertinimas.put("vardas",surastasVertinimas.vardas);
                jsonVertinimas.put("pavarde",surastasVertinimas.pavarde);
                jsonVertinimas.put("ivertinimas",surastasVertinimas.ivertinimas);
                studentuRezultatai.add(jsonVertinimas);
            });
            JSONObject jsonEgzaminas = new JSONObject();
            jsonEgzaminas.put("id",egz.id);
            jsonEgzaminas.put("pavadinimas",egz.pavadinimas);
            jsonEgzaminas.put("studentuRezultatai",studentuRezultatai);
            jsonEgzaminai.add(jsonEgzaminas);

        });
        JSONObject jsonEgzaminuFailas = new JSONObject();
        jsonEgzaminuFailas.put("egzaminai",jsonEgzaminai);

        try (FileWriter file = new FileWriter("Ivertinimai.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(jsonEgzaminuFailas.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
