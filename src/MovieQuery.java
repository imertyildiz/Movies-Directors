import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class MovieQuery {
    public static void queryAll(String inFileName, String outFileName) {
        try {
            // Initialization of fileOutputStreams
            FileOutputStream fileOutputStream1 = new FileOutputStream(outFileName + "-1.out");
            FileOutputStream fileOutputStream2 = new FileOutputStream(outFileName + "-2.out");
            FileOutputStream fileOutputStream3 = new FileOutputStream(outFileName + "-3.out");
            FileOutputStream fileOutputStream4 = new FileOutputStream(outFileName + "-4.out");
            FileOutputStream fileOutputStream5 = new FileOutputStream(outFileName + "-5.out");
            // Extraction of Movies from .csv file with using Stream
            List<Movie> movies = new ArrayList<>();
            Files.lines(Path.of(inFileName + ".csv"))
                    .skip(1)
                    .map(MovieQuery::getMovies)
                    .forEach(movies::add);

            // Question 1
            {
                // Movies are mapped to Titles with uppercase. Then, they are written to a file1
                movies.stream()
                        .map(movie -> movie.getTitle().toUpperCase(Locale.ROOT))
                        .sorted()
                        .forEach(title -> {
                            try {
                                fileOutputStream1.write((title + "\n").getBytes());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            }
            // Question 2
            {
                // Movies are mapped to Directors with filter using distinct and sorted. Then, they are written to a file2
                movies.stream()
                        .filter(movie -> movie.getRating() >= 8.5)
                        .map(Movie::getDirector)
                        .distinct()
                        .sorted()
                        .forEach(director -> {
                            try {
                                fileOutputStream2.write((director.toUpperCase(Locale.ROOT) + "\n").getBytes());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
            }
            // Question 3
            {
                // First, filter is applied and movie with min gross value is fetched. then it's director is written to file3
                movies.stream()
                        .filter(movie -> movie.getGenre().contains("Adventure") && movie.getGross() > -1)
                        .min(Comparator.comparing(Movie::getGross))
                        .ifPresent(movie -> {
                            try {
                                fileOutputStream3.write(movie.getDirector().toUpperCase(Locale.ROOT).getBytes());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
            }
            // Question 4
            {
                // First, filter1=(rating <= 8.0) is applied and directors are fetched.
                // Then the directors are filtered if it is in directors wrt. filter2=(rating >= 8.5)
                // Then, sorted and written into a file4
                movies.stream()
                        .filter(movie -> movie.getRating() <= 8.0)
                        .map(Movie::getDirector)
                        .distinct()
                        .filter(director ->
                                movies.stream().filter(movie1 -> movie1.getRating() >= 8.5).map(Movie::getDirector).distinct().toList().contains(director)
                        )
                        .sorted()
                        .forEach(director -> {
                            try {
                                fileOutputStream4.write((director.toUpperCase(Locale.ROOT) + "\n").getBytes());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
            }
            // Question 5
            {
                // Movies are filtered acc. to director which produced the earliest movie.
                // Then, their runtimes are summed and written into file5.
                try {
                    fileOutputStream5.write(
                            String.valueOf(
                                    movies.stream()
                                            .filter(movie -> movie.getDirector().equals(
                                                    movies.stream().min(Comparator.comparing(Movie::getYear)).get().getDirector()
                                            ))
                                            .map(Movie::getRunTime)
                                            .mapToLong(time -> time).sum()).getBytes()
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            // Streams are closed.
            fileOutputStream1.close();
            fileOutputStream2.close();
            fileOutputStream3.close();
            fileOutputStream4.close();
            fileOutputStream5.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Static method for getting Movies from lines.
    private static Movie getMovies(String line) {
        String[] fields = line.split(",");
        return new Movie(fields[0], Long.parseLong(fields[1]), new ArrayList<>(List.of(fields[2].split(";"))), Long.parseLong(fields[3]), Double.parseDouble(fields[4]), Long.parseLong(fields[5]), fields[6], fields[7], Double.parseDouble(fields[8]));
    }
}
