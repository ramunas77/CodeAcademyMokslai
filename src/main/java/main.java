import java.util.Scanner;


public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EgzaminuVertinimoSistema egzaminuVertinimoSistema = new EgzaminuVertinimoSistema();
        while (true) {
            System.out.println("Egzamino vertinimo systema");
            System.out.println("1 kelias iki egzamino katalogo");
            System.out.println("2 kelias iki studentu atsakymo katalogo");
            System.out.println("3 studentu atsakymu ivertinimas");
            System.out.println("0 iseiti");
            String input = sc.nextLine();
            if (input.equals("0")) {
                break;
            }
            switch (input) {
                case "1": {
                   System.out.println("Iveskite kelia iki egzamino katalogo");
                    String keliasIkiEgzaminoKatalogo = sc.nextLine();
                    egzaminuVertinimoSistema.gautiEgzaminoKataloga(keliasIkiEgzaminoKatalogo);
                    break;
                }
                case "2": {
                    System.out.println("iveskite kelio iki studentu atsakymu katalogo");
                    String studentuAtsakymoKatalogas = sc.nextLine();
                    egzaminuVertinimoSistema.gautiStudentuAtsakymai(studentuAtsakymoKatalogas);
                    break;
                }
                case "3": {
                    egzaminuVertinimoSistema.palyginti();
                    egzaminuVertinimoSistema.issaugotiIvertinimoFaila();
                    break;
                }
                default: {
                    System.out.println("neteisingas pasirinkimas");
                    break;
                }

            }

        }


    }

}


