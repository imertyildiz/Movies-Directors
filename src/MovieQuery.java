import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MovieQuery {
    public static void queryAll(String inFileName, String outFileName) {
        String line = "";
        String splitBy = ",";

        try {
            FileOutputStream fileOutputStream1 = new FileOutputStream(outFileName + "-1.out");
            FileOutputStream fileOutputStream2 = new FileOutputStream(outFileName + "-2.out");
            FileOutputStream fileOutputStream3 = new FileOutputStream(outFileName + "-3.out");
            FileOutputStream fileOutputStream4 = new FileOutputStream(outFileName + "-4.out");
            FileOutputStream fileOutputStream5 = new FileOutputStream(outFileName + "-5.out");
            // Question 1
            {
                Set<String> lastStr = new TreeSet<>();
                Files.lines(Path.of("imdb3.csv"))
                        .skip(1)
                        .map(MovieQuery::getMovies)
                        .forEach(movie -> lastStr.add(movie.getTitle().toUpperCase(Locale.ROOT) + "\n"));

                lastStr.forEach(title -> {
                            try {
                                fileOutputStream1.write(title.getBytes());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
            }
            // Question 2
            {
                Set<String> lastStr = new TreeSet<>();
                Files.lines(Path.of("imdb3.csv"))
                        .skip(1)
                        .map(MovieQuery::getMovies)
                        .filter(movie -> movie.getRating() >= 8.5)
                        .forEach(movie -> lastStr.add(movie.getDirector().toUpperCase(Locale.ROOT) + "\n"));

                lastStr.forEach(title -> {
                            try {
                                fileOutputStream2.write(title.getBytes());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
            }
            // Question 3
            {
                ArrayList<Movie> lastStr = new ArrayList<>();
                Files.lines(Path.of("imdb3.csv"))
                        .skip(1)
                        .map(MovieQuery::getMovies)
                        .filter(movie -> movie.getGenre().contains("Adventure") && movie.getGross() > -1)
                        .forEach(lastStr::add);

                Collections.sort(lastStr,new myGrossComparator());
                try {
                    fileOutputStream3.write((lastStr.get(0).getDirector().toUpperCase(Locale.ROOT)).getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // Question 4
            {
                TreeSet<String> lowerThanEight = new TreeSet<>();
                Files.lines(Path.of("imdb3.csv"))
                        .skip(1)
                        .map(MovieQuery::getMovies)
                        .filter(movie -> movie.getRating() <=8.0)
                        .forEach(movie -> lowerThanEight.add(movie.getDirector()));

                Files.lines(Path.of("imdb3.csv"))
                        .skip(1)
                        .map(MovieQuery::getMovies)
                        .filter(movie -> movie.getRating() >= 8.5 && lowerThanEight.contains(movie.getDirector()))
                        .map(Movie::getDirector)
                        .distinct()
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
            // TODO: yapilmadi daha
            {
                try {
                    fileOutputStream5.write(
                            String.valueOf(
                            Files.lines(Path.of("imdb3.csv"))
                                    .skip(1)
                                    .map(MovieQuery::getMovies)
                                    .filter(movie -> {
                                        try {
                                            return movie.getDirector().equals(
                                                    Files.lines(Path.of("imdb3.csv"))
                                                            .skip(1)
                                                            .map(MovieQuery::getMovies).min(Comparator.comparing(Movie::getYear)).get().getDirector()
                                            );
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        throw new RuntimeException("There is no movie.");
                                    })
                                    .map(Movie::getRunTime)
                                    .mapToLong(time -> time).sum()).getBytes()
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            fileOutputStream1.close();
            fileOutputStream2.close();
            fileOutputStream3.close();
            fileOutputStream4.close();
            fileOutputStream5.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static Movie getMovies(String line) {
        String[] fields = line.split(",");
        return new Movie(fields[0], Long.parseLong(fields[1]), new ArrayList<>(List.of(fields[2].split(";"))), Long.parseLong(fields[3]), Double.parseDouble(fields[4]), Long.parseLong(fields[5]), fields[6], fields[7], Double.parseDouble(fields[8]));
    }

    static class myGrossComparator implements Comparator<Movie>
    {

        public int compare(Movie s1, Movie s2)
        {
            return s1.getGross().compareTo(s2.getGross());
        }
    }

    public static void main(String[] args) {
        MovieQuery.queryAll("imdb3", "result");
    }
}
