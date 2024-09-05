
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import DAO_files.MovieDAO;
import DAO_files.UserDAO;
import profile_files.*;

public class App {
    // Method for checking if movieslist isempty
    public static void checkIfEmpty(List<Movies> mveList) {
        Boolean check = mveList.isEmpty();
        if (check == true) {
            System.out.println("OOPS! SORRY, Your requirement is unavailable in the given Movie Database!");
        } else {
            mveList.forEach(System.out::println);
        }
    }

    public static void main(String[] args) {

        // Creation of Movies from csv
        // Using try and catch
        try {
            BufferedReader reader = new BufferedReader(new FileReader(
                "C:\\Users\\Nirbhay Gupta\\OneDrive\\Desktop\\dbpracticejdbc_project\\dbpracticejdbc\\src\\csv_files\\Movies1.csv"));
            String movieLine;
            int alreadyinsertedvalue = 0;
            while ((movieLine = reader.readLine()) != null) {
                Movies m = new Movies(movieLine);
                MovieDAO mov = new MovieDAO();

                // Listing all Movie objects
                List<Movies> mlist = mov.getAllObjects();
                if (mlist.isEmpty()) {
                            return;
                } else {
                    for (Movies movies : mlist) {
                        if (m.getId() == movies.getId()) {
                            alreadyinsertedvalue = m.getId();
                            break;
                        }
                    }
                }
                mov.createObjects(m, alreadyinsertedvalue);
            }
            reader.close();
        } catch (IOException e) {
            e.getMessage();
        }

        // Creation of Users from csv
        try {
            BufferedReader reader = new BufferedReader(new FileReader(
                "C:\\Users\\Nirbhay Gupta\\OneDrive\\Desktop\\dbpracticejdbc_project\\dbpracticejdbc\\src\\csv_files\\Users.csv"));
            String userLine;
            int a = 0;
            while ((userLine = reader.readLine()) != null) {
                UserDAO user = new UserDAO();
                user.createObjects(new Users(userLine), a);
            }
            reader.close();
        } catch (IOException e) {
            e.getMessage();
        }

        // setting Movie ratings
        MovieDAO.setAllMovieRatings();

        // switch cases for arg[i]/arguments given in query
        switch (args[0]) {
            case "-print":
                List<Movies> allMovies1 = MovieDAO.setAllMovieUsers();
                System.out.println("--------------");
                allMovies1.forEach(System.out::println);
                System.out.println("--------------");
                break;

            case "-search":
                if (args[2].equalsIgnoreCase("Rating")) {
                    List<Movies> Movies = MovieDAO.Checkforrating(args[1], args[3]);
                    checkIfEmpty(Movies);
                } else if (args[1].equalsIgnoreCase("Id")) {
                    List<Movies> Movies = MovieDAO.Checkforrating(args[2]);
                    checkIfEmpty(Movies);
                } else if (args[4].equalsIgnoreCase("Rating")) {
                    List<Movies> Movies = MovieDAO.Checkforrating(args[1], args[2], args[3], args[5]);
                    checkIfEmpty(Movies);

                }
                break;

            case "-searchMovie_title":
                if (args[1].equalsIgnoreCase("MovieTitle")) {
                    {
                        List<Movies> MovieTitle_search = MovieDAO.getMovieByTitle(args[2]);
                        checkIfEmpty(MovieTitle_search);
                    }
                }
                break;

            case "-count":
                Movies m = new Movies();
                int count = m.count();
                System.out.println("there are " + count + "rows");
                break;
            case "-delete":
                MovieDAO.deletemovie(args[1], args[2]);
                List<Movies> allMovies = MovieDAO.setAllMovieUsers();
                System.out.println("Now the elements left in the database are: ");
                allMovies.forEach(System.out::println);
                break;
            case "-insert":
                Movies movie = new Movies();
                int id = Integer.parseInt(args[1]);
                movie.setId(id);
                movie.setMovie_name(args[2]);
                movie.setLanguage(args[3]);
                movie.setGenere(args[4]);
                double rating = Double.parseDouble(args[5]);
                movie.setRating(rating);
                MovieDAO.insertMovies(movie);
                List<Movies> allMovies3 = MovieDAO.setAllMovieUsers();
                System.out.println("Now the elements left in the database are: ");
                allMovies3.forEach(System.out::println);
                break;

            case "-searchmovie":
                List<Movies> movies = MovieDAO.Suggestion(args[1], args[2]);
                checkIfEmpty(movies);
                break;

            case "-sort":
                List<Movies> mve = MovieDAO.sortRating();
                checkIfEmpty(mve);
                break;

            case "-partialString":
                List<Movies> mov = MovieDAO.PartialStringSearch(args[1]);
                checkIfEmpty(mov);
                break;

            case "-deleteAllMovies":
                MovieDAO.truncatemov();
                break;

            case "-deleteAllUsers":
                UserDAO.truncateuser();
                break;

            case "-printusers":
                List<Movies> allMovieslist = MovieDAO.setAllMovieUsers();
                // MovieDAO.setAllMovieRatings();
                for (Movies mov1 : allMovieslist) {
                    System.out.println(mov1);
                    System.out.println("Individual ratings given by Users to this movie are: ");
                    List<Users> uList = mov1.getAlluser();

                    uList.forEach(System.out::println);
                    System.out.println("\n");
                }
                break;
            case "-individual_ratings":
                List<Movies> allMovieslist1 = MovieDAO.setAllMovieUsers();
                if (args[1].equalsIgnoreCase("id")) {
                    int a = Integer.parseInt(args[2]);
                    for (Movies mov1 : allMovieslist1) {
                        if (a == mov1.getId()) {
                            List<Users> users = mov1.getAlluser();
                            users.forEach(System.out::println);
                        }
                    }
                }
                if (args[1].equalsIgnoreCase("movietitle")) {
                    for (Movies mov1 : allMovieslist1) {
                        if (args[2].equalsIgnoreCase(mov1.getMovie_name())) {
                            List<Users> users = mov1.getAlluser();
                            users.forEach(System.out::println);
                        }
                    }
                }
                break;
            case "-filter":
                List<Movies> mList1 = MovieDAO.filtermovies(args[1], args[2]);
                checkIfEmpty(mList1);
                break;
            default:
                printHelp();
                break;
        }
    }

    private static void printHelp() {

        System.out
                .println("---------------------------------OOP MINIPROJECT------------------------------------------");
        System.out
                .println(
                        "----------------------------MOVIE DATABASE MANAGEMENT-------------------------------------\n");

        System.out.println("Help Menu for commands:\n\n\n");
        System.out.println("Print all movies: \nSyntax: -print \n\n");
        System.out.println(
                "Insert a movie into the database: \nSyntax: -insert <Id> <Movie_Name> <Language> <Genere> <Rating>\nEg:-insert 50 dune english comedy 10.0\n\n");
        System.out.println(
                "Individual_ratings gives all the individual ratings for a particular movieid: \nSyntax: -individual_ratings id <movie_id>\nEg:-individual_ratings id 1\n\n");
        System.out.println(
                "Individual_ratings gives all the individual ratings for a particular moviename: \nSyntax: -individual_ratings movietitle <movietitle>\nEg:-individual_ratings movietitle Mahanati\n\n");
        System.out.println("Search movies using id: \nSyntax: -search Id <Id>\nEg:-search Id 1\n\n");
        System.out.println(
                "Search movie by title: \nSyntax: -searchMovie_title MovieTitle <movie_title>\nEg:-searchMovie_title MovieTitle movie1\n\n");
        System.out.println(
                "Search using Partial movie name: \nSyntax: -partialString movietitle\nEg:-partialString movietitle\n\n");
        System.out.println(
                "Search for movies which have rating less than a particular value: \nSyntax: -search -lt Rating <rating>\nEg:-search -lt Rating 9.0\n\n");
        System.out.println(
                "Search for movies which have rating above a particular value: \nSyntax: -search -gt Rating <rating>\nEg:-search -gt Rating 7.0\n\n");
        System.out.println(
                "Search for movies which have rating less than a particular value: \nSyntax: -search -equal Rating <rating>\nEg:-search -equal Rating 9.0\n\n");
        System.out.println(
                "Search for movies in a particular language: \nSyntax: -searchmovie Language <language>\nEg: -searchmovie Language english\n\n");
        System.out.println(
                "Search for movies of a particular genre: \nSyntax: -searchmovie Genere <genre>\nEg:-searchmovie Genere comedy\n\n");
        System.out.println(
                "Search for a movie of a particular language/Genere and having rating above/below/equal a particular value: \nSyntax: -search -gt/-lt/-equal Language/Genere <language/genre> Rating <rating>\nEg:-search -gt/-lt/-equal Language/Genere english Rating 8.0\n\n");
        System.out.println(
                "Delete movies using id: \nSyntax: -delete Id <Id> (or) Syntax: -delete MovieTitle <movie_title>\nEg1: -delete Id 1\nEg2: -delete MovieTitle Dune\n\n");
        System.out.println("Count number of movies: \nSyntax: -count\n\n");
        System.out.println("Print movies algong with individual user ratings: \nSyntax:-printusers\n\n");
        System.out.println(
                "Filter returns the movies with rating between rating1 and rating2: \nSyntax: -filter <rating1> <rating2>\n\n");
        System.out.println("Sort movies in descending order of ratings:\nSyntax: -sort\n\n");
        System.out.println("Help menu: \nSyntax: -h(or any key will work)\n\n");
    }
}