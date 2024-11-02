import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Comparator;

public class Backend implements BackendInterface {

    // Change from Tree_Placeholder to IterableSortedCollection<Song>
    private IterableSortedCollection<Song> songTree;
    private Integer loudnessLow;
    private Integer loudnessHigh;
    private Integer bpmFilter; 

    /**
     * Constructor: accepts the IterableSortedCollection<Song> object which contains the song data.
     * @param tree an IterableSortedCollection<Song> object representing the song collection
     */
    public Backend(IterableSortedCollection<Song> tree) {
        this.songTree = tree;
        this.loudnessLow = null;
        this.loudnessHigh = null;
        this.bpmFilter = null;  
    }

    /**
     * Reads CSV file with song information (e.g., title, artist, BPM, etc.), 
     * creates Song objects, and adds them to the songTree.
     * Also handles potential invalid data types and file structure.
     * @param filename the CSV file containing the song data
     * @throws IOException if there is an error reading the file
     */
    @Override
    public void readData(String filename) throws IOException {
        try (Scanner scanner = new Scanner(new File(filename))) {
            // Skip header row
            if (scanner.hasNextLine()) {
                scanner.nextLine(); // Skip the header row
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");

                // Ensure we have the expected number of columns (14 columns total)
                if (values.length != 14) {
                    System.err.println("Invalid row: " + line);
                    continue; // Skip this row if the column count is invalid
                }

                try {
                    String title = values[0];         // Title of the song
                    String artist = values[1];        // Artist of the song
                    String genre = values[2];         // Top genre
                    int year = Integer.parseInt(values[3]);    // Year
                    int bpm = Integer.parseInt(values[4]);     // Beats per minute (BPM)
                    int energy = Integer.parseInt(values[5]);  // Energy (nrgy)
                    int danceability = Integer.parseInt(values[6]);  // Danceability (dnce)
                    int loudness = Integer.parseInt(values[7]);      // Loudness (dB)
                    int liveness = Integer.parseInt(values[8]);      // Liveness (live)

                    // Create and insert a song object into the tree
                    Song song = new Song(title, artist, genre, year, bpm, energy, danceability, loudness, liveness);
                    songTree.insert(song);
                } catch (NumberFormatException e) {
                    // Handle invalid number formatting
                    System.err.println("Invalid data in row: " + line);
                }
            }
        } catch (IOException e) {
            // Handle file read errors
            System.err.println("Error reading file: " + filename);
            throw e;
        }
    }

    /**
     * Retrieves the list of song titles within the specified loudness range.
     * @param low the lower bound of the loudness range (null if no lower bound)
     * @param high the upper bound of the loudness range (null if no upper bound)
     * @return a list of song titles that fall within the specified loudness range
     */
    @Override
    public List<String> getRange(Integer low, Integer high) {
        List<String> titles = new ArrayList<>();

        for (Song song : songTree) {
            int loudness = song.getLoudness();

            // Check if the song's loudness is within the specified range
            boolean withinLowBound = (low == null || loudness >= low);
            boolean withinHighBound = (high == null || loudness <= high);

            if (withinLowBound && withinHighBound) {
                titles.add(song.getTitle());
            }
        }

        // Returns the list of songs within the specified loudness range
        return titles;
    }

    /**
     * Sets a BPM (Beats Per Minute) filter and returns the filtered list of song titles.
     * This method filters songs by BPM and also ensures that the songs meet the loudness range criteria.
     * @param threshold the BPM threshold; only songs with BPM higher than the threshold will be included
     * @return a list of filtered song titles that meet the BPM and loudness range criteria
     */
    @Override
    public List<String> setFilter(Integer threshold) {
        // Update BPM filter to set new threshold
        this.bpmFilter = threshold;

        List<String> filteredSongs = new ArrayList<>();
        for (Song song : songTree) {
            // Apply BPM filter and loudness range filter
            boolean bpmFilterCondition = (this.bpmFilter == null || song.getBPM() > this.bpmFilter);
            boolean withinLowBound = (loudnessLow == null || song.getLoudness() >= loudnessLow);
            boolean withinHighBound = (loudnessHigh == null || song.getLoudness() <= loudnessHigh);

            if (bpmFilterCondition && withinLowBound && withinHighBound) {
                filteredSongs.add(song.getTitle());
            }
        }

        return filteredSongs;
    }

    /**
     * Retrieves the top 5 most danceable songs that meet the BPM and loudness filter criteria.
     * @return a list of the titles of the top 5 most danceable songs
     */
    @Override
    public List<String> fiveMost() {
        List<Song> filteredSongs = new ArrayList<>();

        // Filter songs based on the most recent loudness range and BPM filter
        for (Song song : songTree) {
            boolean withinLowBound = (loudnessLow == null || song.getLoudness() >= loudnessLow);
            boolean withinHighBound = (loudnessHigh == null || song.getLoudness() <= loudnessHigh);
            boolean bpmFilterCondition = (bpmFilter == null || song.getBPM() > bpmFilter);

            if (withinLowBound && withinHighBound && bpmFilterCondition) {
                filteredSongs.add(song);
            }
        }

        // Sort songs by danceability and return the top 5 most danceable songs
        filteredSongs.sort(Comparator.comparingInt(Song::getDanceability).reversed());

        // Retrieve the titles of the top 5 (or fewer) most danceable songs and return them in a list
        List<String> mostDanceableTitles = new ArrayList<>();
        for (int i = 0; i < Math.min(5, filteredSongs.size()); i++) {
            mostDanceableTitles.add(filteredSongs.get(i).getTitle());
        }

        return mostDanceableTitles;
    }
}
