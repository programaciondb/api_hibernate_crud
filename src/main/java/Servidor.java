import dao.MuebleDAO;
import servicios.MueblesAPIREST;

public class Servidor {

    public static void main(String[] args) {

        MueblesAPIREST api=new MueblesAPIREST(new MuebleDAO());
    }
}
