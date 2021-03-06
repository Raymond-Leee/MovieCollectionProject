import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)ighest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie object to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void sortResults2(ArrayList<String> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            String temp = listToSort.get(j);

            int possibleIndex = j;
            while (possibleIndex > 0 && temp.compareTo(listToSort.get(possibleIndex - 1)) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        System.out.print("Enter a cast search term: ");
        String searchTerm = scanner.nextLine();

        searchTerm = searchTerm.toLowerCase();

        ArrayList<String> allCast = new ArrayList<String>();

        for (int i = 0; i < movies.size(); i++)
        {
            String[] castInMovies = movies.get(i).getCast().split("\\|");
            for (int j = 0; j < castInMovies.length; j++)
            {
                boolean inList = false;
                for (int k = 0; k < allCast.size(); k++)
                {
                    if (allCast.get(k).equals(castInMovies[j]));
                    {
                        inList = true;
                    }
                }
                if (!inList)
                {
                    allCast.add(castInMovies[j]);
                }
            }
        }

        ArrayList<String> equal = new ArrayList<String>();
        for (int i = 0; i < allCast.size(); i++)
        {
            if (allCast.get(i).toLowerCase().contains(searchTerm.toLowerCase()))
            {
                equal.add(allCast.get(i));
            }
        }

        sortResults2(equal);

        for (int i = 0; i < equal.size(); i++)
        {
            String cast = equal.get(i);

            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + cast);
        }

        System.out.println("Which cast member would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String selectedMember = allCast.get(choice - 1);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword search term: ");
        String searchTerm = scanner.nextLine();

        searchTerm = searchTerm.toLowerCase();

        ArrayList<Movie> results = new ArrayList<Movie>();

        for (int i = 0; i < movies.size(); i++)
        {
            String movieKeyWord = movies.get(i).getKeywords();
            movieKeyWord = movieKeyWord.toLowerCase();

            if (movieKeyWord.indexOf(searchTerm) != -1)
            {
                results.add(movies.get(i));
            }
        }

        sortResults(results);

        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres()
    {
        ArrayList<String> genres = new ArrayList<String>();

        for (int i = 0; i < movies.size(); i++)
        {
            String[] genresInMovies = movies.get(i).getGenres().split("\\|");
            for (int j = 0; j < genresInMovies.length; j++)
            {
                boolean inList = false;
                for (int k = 0; k < genres.size(); k++)
                {
                    if (genres.get(k).equals(genresInMovies[j]));
                    {
                        inList = true;
                    }
                }
                if (!inList)
                {
                    genres.add(genresInMovies[j]);
                }
            }
        }

        sortResults2(genres);
        
    }

    private void listHighestRated()
    {
        ArrayList<Movie> movie = new ArrayList<Movie>();
        ArrayList<String> results = new ArrayList<String>();
        ArrayList<Double> ratings = new ArrayList<Double>();
        double highestRating = 0;

        for (int i = 0; i < movies.size() - 1; i++)
        {
            String title = movies.get(i).getTitle();
            double rating = movies.get(i).getUserRating();
            if (i == 0)
            {
                highestRating = movies.get(i).getUserRating();
                results.add(title);
                ratings.add(rating);
                movie.add(movies.get(i));
            }
            if (movies.get(i).getUserRating() >= highestRating)
            {
                highestRating = rating;
                results.add(0, title);
                ratings.add(0, rating);
                movie.add(0, movies.get(i));
            }
            if (movies.get(i).getUserRating() >= movies.get(i + 1).getUserRating())
            {
                results.add(i, title);
                ratings.add(i, rating);
                movie.add(i, movies.get(i));
            }
            else
            {
                results.add(title);
                ratings.add(rating);
                movie.add(movies.get(i));
            }
        }

        while (results.size() != 50)
        {
            results.remove(50);
            ratings.remove(50);
            movie.remove(50);
        }

        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i);
            double rating = ratings.get(i);

            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title + ": " + rating);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = movie.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRevenue()
    {
        ArrayList<Movie> movie = new ArrayList<Movie>();
        ArrayList<String> results = new ArrayList<String>();
        ArrayList<Integer> revenue = new ArrayList<Integer>();
        int highestEarning = 0;

        for (int i = 0; i < movies.size() - 1; i++)
        {
            String title = movies.get(i).getTitle();
            int earnings = movies.get(i).getRevenue();
            if (i == 0)
            {
                highestEarning = movies.get(i).getRevenue();
                results.add(title);
                revenue.add(earnings);
                movie.add(movies.get(i));
            }
            else if (movies.get(i).getRevenue() >= highestEarning)
            {
                highestEarning = earnings;
                results.add(0, title);
                revenue.add(0, earnings);
                movie.add(0, movies.get(i));
            }
            if (movies.get(i).getRevenue() >= movies.get(i + 1).getRevenue())
            {
                results.add(i, title);
                revenue.add(i, earnings);
                movie.add(i, movies.get(i));
            }
            else
            {
                results.add(title);
                revenue.add(earnings);
                movie.add(movies.get(i));
            }
        }

        while (results.size() != 50)
        {
            results.remove(50);
            revenue.remove(50);
            movie.remove(50);
        }

        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i);
            int earnings = revenue.get(i);

            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title + ": $" + earnings);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = movie.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}