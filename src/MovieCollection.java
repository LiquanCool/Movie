import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
            System.out.println("- list top 50 (h)igest revenue movies");
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
                //add the Movie objest to the results list
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

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<String> results = new ArrayList<String>();

        ArrayList<String> cast = new ArrayList<String>();
        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String[] castPeople;
            castPeople = movies.get(i).getCast().split("\\|");
            for (int j = 0; j<castPeople.length;j++)
            {

                boolean yes = false;
                for (int k = 0; k<cast.size();k++)
                {
                    if(castPeople[j].equals(cast.get(k)))
                    {
                        yes = true;
                    }
                }
                if (!yes)
                {
                    cast.add(castPeople[j]);
                }
            }
        }
        ArrayList<String> searchedCast = new ArrayList<String>();
        for (int i = 0; i < cast.size(); i++)
        {
            String castMember = cast.get(i);
            castMember = castMember.toLowerCase();
            if (castMember.indexOf(searchTerm)!=-1)
            {
                results.add(cast.get(i));
            }
        }


        // sort the results by title
        Collections.sort(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String actor = results.get(i);

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + actor);
        }

        System.out.println("Which cast member would you like to see the movies that they are in?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String selectedActor = results.get(choice-1);
        ArrayList<Movie> resultMovies = new ArrayList<Movie>();
        for (int i = 0; i < movies.size(); i++)
        {
            String[] castPeople;
            castPeople = movies.get(i).getCast().split("\\|");
            for (int j = 0; j<castPeople.length;j++)
            {
                if (castPeople[j].contains(selectedActor))
                {
                    resultMovies.add(movies.get(i));
                }
            }
        }
        sortResults(resultMovies);

        // now, display them all to the user
        for (int i = 0; i < resultMovies.size(); i++)
        {
            Movie movie = resultMovies.get(i);

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + movie.getTitle());
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        choice = scanner.nextInt();
        scanner.nextLine();


        Movie selectedMovie = resultMovies.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getKeywords();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String keyword = results.get(i).getKeywords();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + keyword);
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
        // arraylist to hold search results
        ArrayList<String> results = new ArrayList<String>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String[] oneGenre;
            oneGenre = movies.get(i).getGenres().split("\\|");
            for (int j = 0; j<oneGenre.length;j++)
            {
                boolean yes = false;
                for (int k = 0; k<results.size();k++)
                {
                    if(oneGenre[j].equals(results.get(k)))
                    {
                        yes = true;
                    }
                }
                if (!yes)
                {
                    results.add(oneGenre[j]);
                }
            }
        }

        // sort the results by title
        Collections.sort(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String genre = results.get(i);

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + genre);
        }

        System.out.println("Which genre's movies would you like to see?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String selectedGenre = results.get(choice-1);
        ArrayList<Movie> resultMovies = new ArrayList<Movie>();
        for (int i = 0; i < movies.size(); i++)
        {
            String[] genres;
            genres = movies.get(i).getGenres().split("\\|");
            for (int j = 0; j<genres.length;j++)
            {
                if (genres[j].contains(selectedGenre))
                {
                    resultMovies.add(movies.get(i));
                }
            }
        }
        sortResults(resultMovies);

        // now, display them all to the user
        for (int i = 0; i < resultMovies.size(); i++)
        {
            Movie movie = resultMovies.get(i);

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + movie.getTitle());
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        choice = scanner.nextInt();
        scanner.nextLine();


        Movie selectedMovie = resultMovies.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRated()
    {
        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();
        // search through ALL movies in collection
        results.add(movies.get(0));
        for (int i = 1; i < movies.size(); i++)
        {
            boolean used = false;
            int c = 0;
            while(!used && c<results.size())
            {
                if (movies.get(i).getUserRating()>results.get(c).getUserRating())
                {
                    used=true;
                    if (results.size()<50)
                    {
                        results.add(c,movies.get(i));
                    }
                    else
                    {
                        results.set(c,movies.get(i));
                    }
                }
                c++;
            }
        }



        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            Movie movie = results.get(i);

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + movie.getTitle() + " " + movie.getUserRating());
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

    private void listHighestRevenue()
    {
// arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();
        // search through ALL movies in collection
        for (int i = 0; i < 50; i++)
        {
            results.add(movies.get(i));
        }

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            Movie movie = results.get(i);

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + movie.getTitle() + " " + movie.getRevenue());
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