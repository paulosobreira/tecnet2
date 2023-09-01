package util;


/**
 * Classe que recebe um <i>int</i> por parmetro que indica o nmero do ms atual 
 * e retorna o nome do ms em <i>String</i>.
 * 
 * @author Rafael Carneiro - <a href = "mailto:rafael@portaljava.com">
 * rafael@portaljava.com</a>
 *
 */
public class Mes {
    public static String mes(int mes) {
        String str = null;

        switch (mes) {
        case 1:
            return "Janeiro";

        case 2:
            return "Fevereiro";

        case 3:
            return "Maro";

        case 4:
            return "Abril";

        case 5:
            return "Maio";

        case 6:
            return "Junho";

        case 7:
            return "Julho";

        case 8:
            return "Agosto";

        case 9:
            return "Setembro";

        case 10:
            return "Outubro";

        case 11:
            return "Novembro";

        case 12:
            return "Dezembro";

        default:
            return "Ms Invlido";
        }
    }
}
