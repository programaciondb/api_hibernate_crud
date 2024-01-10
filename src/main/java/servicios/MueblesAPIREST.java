package servicios;

import com.google.gson.Gson;
import dao.MuebleDAOInterface;
import dto.MuebleDTO;
import entidades.Mueble;
import spark.Spark;

import java.util.Arrays;
import java.util.List;

public class MueblesAPIREST {
    private MuebleDAOInterface dao;
    private Gson gson = new Gson();

    public MueblesAPIREST(MuebleDAOInterface implementacion) {
        Spark.port(8080);
        dao = implementacion;
        //...

        // Endpoint para obtener todos los muebles




        Spark.get("/muebles", (request, response) -> {
            List<Mueble> muebles = dao.devolverTodos();
            return gson.toJson(muebles);
        });







        //==========================================================================================================

        // Endpoint para obtener muebles más caros
        Spark.get("/muebles/mascaros", (request, response) -> {
            List<Mueble> masCaros = dao.devolverMasCaros();
            System.out.println(masCaros);
            return gson.toJson(masCaros);
        });

        // Endpoint para obtener todas las imágenes de muebles
        Spark.get("/muebles/imagenes", (request, response) -> {
            List<String> imagenes = dao.devolverTodasImages();
            return gson.toJson(imagenes);
        });

        // Endpoint para obtener un resumen con solo el nombre el precio y la URL
        Spark.get("/muebles/resumenobjetos", (request, response) -> {
            //List<Map> resumen = dao.devolverNombreImagenes();
            List<MuebleDTO> resumen = dao.devolverNombreImagenes();
            return gson.toJson(resumen);
        });


        // Endpoint para obtener el total de muebles
        Spark.get("/muebles/total", (request, response) -> {
            Long total = dao.totalMuebles();
            return total.toString();
        });

        // Endpoint para calcular la media de precios de los muebles
        Spark.get("/muebles/mediaprecios", (request, response) -> {
            Double media = dao.mediaPrecios();
            return media.toString();
        });


        // Endpoint para obtener un mueble por su ID


        Spark.get("/muebles/id/:id", (request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            Mueble mueble = dao.buscarPorId(id);
            if (mueble != null) {
                return gson.toJson(mueble);
            } else {
                response.status(404);
                return "Mueble no encontrado";
            }
        });














        //=========================================================================================


        // Endpoint para calcular la media de precios de los muebles de una marca
        Spark.get("/muebles/mediaprecios/:marca", (request, response) -> {
            String marca = request.params(":marca");
            Double media = dao.mediaPreciosMarca(marca);
            return media.toString();
        });

        // Endpoint para buscar muebles por nombre (similar a LIKE)
        Spark.get("/muebles/buscar/:nombre", (request, response) -> {
            String nombre = request.params(":nombre");
            List<Mueble> muebles = dao.buscarPorNombreLike(nombre);
            return gson.toJson(muebles);
        });

        // Endpoint para buscar muebles entre precios mínimos y máximos
        Spark.get("/muebles/buscar/:min/:max", (request, response) -> {
            Double min = Double.parseDouble(request.params(":min"));
            Double max = Double.parseDouble(request.params(":max"));
            List<Mueble> muebles = dao.buscarEntrePrecios(min, max);
            return gson.toJson(muebles);
        });

        // Endpoint para buscar muebles entre precios mínimos y máximos de una marca
        Spark.get("/muebles/buscar/:min/:max/:marca", (request, response) -> {
            Double min = Double.parseDouble(request.params(":min"));
            Double max = Double.parseDouble(request.params(":max"));
            String marca = request.params(":marca");
            List<Mueble> muebles = dao.buscarEntrePreciosMarca(min, max, marca);
            return gson.toJson(muebles);
        });

        // Endpoint para buscar muebles entre precios mínimos y máximos de varias marcas
        Spark.get("/muebles/buscarvarias/:min/:max/:listamarcas", (request, response) -> {
            Double min = Double.parseDouble(request.params(":min"));
            Double max = Double.parseDouble(request.params(":max"));
            String marcasParam = request.params(":listamarcas");

            List<String> marcas = Arrays.asList(marcasParam.split(","));
            System.out.println(marcas);

            List<Mueble> muebles = dao.buscarEntrePreciosMarcas(min, max, marcas);
            return gson.toJson(muebles);
        });

        //CREAR UN MUEBLE CON TODOS LOS DATOS
        Spark.post("/muebles", (request, response) -> {
            String body = request.body();
            Mueble nuevoMueble = gson.fromJson(body, Mueble.class);

            Mueble creado = dao.create(nuevoMueble);
            return gson.toJson(creado);
        });

        // Endpoint para actualizar un mueble por su ID
        Spark.put("/muebles/:id", (request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            String body = request.body();
            Mueble muebleActualizado = gson.fromJson(body, Mueble.class);
            muebleActualizado.setId(id);
            Mueble actualizado = dao.update(muebleActualizado);
            if (actualizado != null) {
                return gson.toJson(actualizado);
            } else {
                response.status(404);
                return "Mueble no encontrado";
            }
        });

        // Endpoint para eliminar un mueble por su ID
        Spark.delete("/muebles/:id", (request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            boolean eliminado = dao.deleteById(id);
            if (eliminado) {
                return "Mueble eliminado correctamente";
            } else {
                response.status(404);
                return "Mueble no encontrado";
            }
        });

        //DEVOLVER DATOS DE MANERA PAGINADA DE TODOS LOS MUEBLES
    }
}
