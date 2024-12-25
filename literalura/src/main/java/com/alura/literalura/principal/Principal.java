package com.alura.literalura.principal;


import com.alura.literalura.model.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.AutorService;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import jakarta.transaction.Transactional;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Principal {
    List<Libro> listaLibros = new ArrayList<>();
    List<Autor> listaAutores = new ArrayList<>();
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "http://gutendex.com/books/?search=";
    private LibroRepository repositorio;
    private AutorRepository autorRepositorio;
    private final AutorService autorServicio;


    public Principal(LibroRepository repository, AutorRepository autorRepository, AutorService autorService) {
        this.repositorio = repository;
        this.autorRepositorio = autorRepository;
        this.autorServicio = autorService;

    }


    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    
                    Por favor elije una opcion a travez de su numero:
                    
                    1 - Buscar libro por titulo 
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma 
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();


            switch (opcion) {
                case 1:
                    obtenerLibro();
                    break;
                case 2:
                    List<Libro> librosRegistrados = repositorio.findAll();
                    librosRegistrados.stream().forEach(System.out::println);
                    break;
                case 3:
                    List<Libro> libroObjeto = repositorio.findAll();
                    List<Autor> id_NoRepetidos = autorRepositorio.findAll();
                    List<Long> nombres = new ArrayList<>();
                    List<String> titulosDeLibros = new ArrayList<>();

                    Long iD_autor = 0L;
                    for (int i=0; i< id_NoRepetidos.size(); i++){
                        nombres.add(id_NoRepetidos.get(i).getId());
                        
                        if (iD_autor == 0L){
                            iD_autor = nombres.get(i);
                            var datosFinal = repositorio.findByAutorId(iD_autor);
                            titulosDeLibros.add(String.valueOf(datosFinal));
                        } else if (iD_autor == nombres.get(i)) {
                            System.out.println("repetido 1");
                            var datosFinal = repositorio.findByAutorId(iD_autor);
                            titulosDeLibros.add(String.valueOf(datosFinal));
                        } else if (!(iD_autor == nombres.get(i))) {
                            var autorInfo= autorRepositorio.findById(iD_autor);
                            System.out.println(autorInfo.get());
                            System.out.println("Titulos del escritor:");
                            titulosDeLibros.forEach(System.out::println);
                            iD_autor = nombres.get(i);
                            // Aca cambia imprime y sigue con el nuevo libro

                            titulosDeLibros.clear();
                            var datosFinal = repositorio.findByAutorId(iD_autor);
                            titulosDeLibros.add(String.valueOf(datosFinal));
                        }
                    }
                    break;
                case 4:
                    System.out.println("Por favor proporcione el año vivo de autor(es) que desea buscar");
                    var fecha = 0;
                    fecha = teclado.nextInt();
                    teclado.nextLine();
                    Pattern pattern = Pattern.compile("\\d{4}");
                    Matcher matcher = pattern.matcher(String.valueOf(fecha));
                    boolean matchFound = matcher.find();
                    var datosFinal = autorRepositorio.findAutoByFechaFallecido(fecha);
                    if(matchFound && !datosFinal.isEmpty()) {
                        System.out.println("Match found");
                        datosFinal.stream().forEach(System.out::println);
                    } else {
                        System.out.println("Puede que la fecha no este en la base de datos o colocaste un numero de menos de 4 digitos, regresando a menu principal");
                    }
                    break;
                case 5:
                    List<Libro> titulosPorIdioma = new ArrayList<>();
                    var menu2 = """
                    
                    Por favor elije el idioma con el siguiente formato :
                    
                    1 - en - Para ingles 
                    2 - es - Para español 
                    3 - la - Para latino 
                    4 - pt - Para portugues  
                    5 - fr - Para frances 
                    
                    """;
                    System.out.println(menu2);
                    var opcion2 = " ";
                    opcion2 = teclado.next();
                    teclado.nextLine();

                    titulosPorIdioma.addAll(repositorio.findByLenguajeLibro(opcion2));
                    titulosPorIdioma.forEach(System.out::println);

                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida seleccionada un numero del Menu ");
            }
        }

    }

    public void obtenerLibro (){
        System.out.println("Escribe el nombre del libro o autor que buscas");
        var busqueda = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + busqueda.replace(" ", "%20"));
        Busqueda datos = conversor.obtenerDatos(json, Busqueda.class);
        //Revisa que el resultado no sea null o empty
        if (!datos.libros().isEmpty() || datos.libros().size() == 0){
         List<Autor> objetoAutores = datos.libros().stream().flatMap(t->t.autorinfo().stream())
                .map(a-> new Autor(a))
                .limit(1)
                .collect(Collectors.toList());

        listaAutores.addAll(objetoAutores);
        List<Libro> librosEncontrado = datos.libros().stream()
                .map(d-> new Libro(d, objetoAutores.getFirst()))
                .limit(1)
                .collect(Collectors.toList());

        listaLibros.addAll(librosEncontrado);
        var autorRepetido = autorRepositorio.findByAutorContainsIgnoreCase(objetoAutores.getFirst().getAutor());

        if (!autorRepetido.isEmpty()){
            Libro guardandoLibro = new Libro(datos.libros().getFirst());
            repositorio.save(guardandoLibro);
            autorServicio.updateBookAuthorIdByTitle(autorRepetido.getFirst().getId(), librosEncontrado.getFirst().getTituloLibro());
        }else{
            Libro guardandoLibro = new Libro(datos.libros().getFirst(), objetoAutores.getFirst());
            repositorio.save(guardandoLibro);
            System.out.println(guardandoLibro.toString());


        }
        }else {
            System.out.println("Libro no encontrado, regresando a Menu principal");

        }









    }



}





