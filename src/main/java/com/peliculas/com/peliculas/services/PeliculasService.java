package com.peliculas.com.peliculas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PeliculasService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void insertData(){

        jdbcTemplate.execute("DROP TABLE IF EXISTS relacionPeliculasActores");
        System.out.println("BorreRelaciones");
        jdbcTemplate.execute("DROP TABLE IF EXISTS peliculas");
        System.out.println("peliculas se fueron");

        jdbcTemplate.execute("DROP TABLE IF EXISTS categorias");
        System.out.println("Categoriasbye");
        jdbcTemplate.execute("DROP TABLE IF EXISTS actores");
        System.out.println("Actores por la ventana");


        String queryPeliculas = "CREATE TABLE peliculas(id serial NOT NULL, nombre varchar NULL, presupuesto INTEGER NULL, fecha_lanzamiento TIMESTAMP, CategoriaId INTEGER, " +
                " CONSTRAINT peliculas_pkey PRIMARY KEY(id), FOREIGN KEY (CategoriaId) REFERENCES categorias(id))";

        String queryRelacionPeliculasActores = "CREATE TABLE relacionPeliculasActores(peliculaId INTEGER , actorId INTEGER, sueldoActores FLOAT8, CONSTRAINT relacionPeliculasActores_pkey PRIMARY KEY(peliculaId,actorId), FOREIGN KEY (peliculaId) REFERENCES peliculas(id), FOREIGN KEY (actorId) REFERENCES actores(id) )";

        String queryCategorias = "CREATE TABLE categorias(id serial NOT NULL, categoria varchar NULL, " +
                " CONSTRAINT categorias_pkey PRIMARY KEY(id))";

        String queryActores = "CREATE TABLE actores(id serial NOT NULL, nombre varchar NULL, fecha_nacimiento TIMESTAMP NULL, " +
                " CONSTRAINT actores_pkey PRIMARY KEY(id))";

        jdbcTemplate.execute(queryCategorias);
        int peliculas = jdbcTemplate.update(queryPeliculas);

        int actores = jdbcTemplate.update(queryActores);
        int relacionesPeliculasActores = jdbcTemplate.update(queryRelacionPeliculasActores);

        //INSERTAR CATEGORIAS

        String cate1 = "INSERT INTO categorias VALUES(1, 'Drama')";
        String cate2 = "INSERT INTO categorias VALUES(2, 'Accion')";
        String cate3 = "INSERT INTO categorias VALUES(3, 'Aventura')";

        jdbcTemplate.execute(cate1);
        jdbcTemplate.execute(cate2);
        jdbcTemplate.execute(cate3);

       //INSERTAR PELICULAS

        String peli1 = "INSERT INTO peliculas VALUES(1, 'Titanic', 2000, '1987-10-11',  1)";
        String peli2 = "INSERT INTO peliculas VALUES(2, 'Terminator', 5000, '1987-10-11', 2 )";
        String peli3 = "INSERT INTO peliculas VALUES(3, 'Pokemon', 1000, '1987-10-11', 3 )";

        jdbcTemplate.execute(peli1);
        jdbcTemplate.execute(peli2);
        jdbcTemplate.execute(peli3);

        //INSERTAR ACTORES

        String actor1 = "INSERT INTO actores VALUES(1, 'Diegote', '1987-10-11')";
        String actor2 = "INSERT INTO actores VALUES(2, 'DiCaprio', '1974-11-11')";
        String actor3 = "INSERT INTO actores VALUES(3, 'Ash Ketchup', '1997-03-22')";

        jdbcTemplate.execute(actor1);
        jdbcTemplate.execute(actor2);
        jdbcTemplate.execute(actor3);

        //INSERTAR relacionPeliculasActores

        String peliActor1 = "INSERT INTO relacionPeliculasActores VALUES(1, 2, 200)";
        String peliActor2 = "INSERT INTO relacionPeliculasActores VALUES(2, 1, 2000)";
        String peliActor3 = "INSERT INTO relacionPeliculasActores VALUES(3, 3, 10)";

        jdbcTemplate.execute(peliActor1);
        jdbcTemplate.execute(peliActor2);
        jdbcTemplate.execute(peliActor3);


    }

    public List<String> mostrarPeliculasDetalle(){

        String query = "select p.nombre as pelicula,a.nombre as actor,pa.sueldoActores as sueldo, c.categoria as categoria from relacionPeliculasActores pa,peliculas p,actores a, categorias c where p.id=pa.peliculaId and a.id = actorId and c.id = p.CategoriaId";

        List<String> respuesta = new ArrayList<>();

        jdbcTemplate
                .query(
                        query,
                        new Object[]{},
                        (rs, row) ->
                                "Titulo: " + rs.getString("pelicula") +
                                          ", Genero: " + rs.getString("categoria") +
                                          ", Actor: " + rs.getString("actor") +
                                          ", Sueldo: " + rs.getString("sueldo"))

                .forEach(pelicula -> respuesta.add(pelicula));

                return respuesta;




    }
}
