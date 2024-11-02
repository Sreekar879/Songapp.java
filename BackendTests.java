import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class BackendTests {

    Backend backend;
    Tree_Placeholder placeholderTree;

    @BeforeEach
    public void setup() {
        placeholderTree = new Tree_Placeholder();
        backend = new Backend(placeholderTree);
    }
    
    @Test
    public void backendTest1()
    {
    	roleTest1();
    	roleTest2();
    	roleTest3();
    }

    @Test
    public void roleTest1() {
        try {
            // Create a temporary file with mock CSV data
            File tempFile = File.createTempFile("testSongs", ".csv");
            FileWriter writer = new FileWriter(tempFile);
            writer.write("title,artist,top genre,year,bpm,nrgy,dnce,dB,live,val,dur,acous,spch,pop\n");
            writer.write("Test Song,Test Artist,Pop,2022,120,80,90,-5,10,50,200,0.5,0.1,90\n");
            writer.close();

            // Call the original readData method with the temp file path
            backend.readData(tempFile.getAbsolutePath());

            // Verify that the song was inserted correctly
            assertTrue(placeholderTree.contains(new Song("Test Song", "Test Artist", "Pop", 2022, 120, 80, 90, -5, 10)));

            // Delete the temporary file after the test
            tempFile.delete();

        } catch (IOException e) {
            fail("IOException should not have been thrown: " + e.getMessage());
        }
    }




    /**
     * roleTest2: Tests the getRange method of the backend.
     * This test checks if the correct songs are returned based on the loudness range.
     */
    @Test
    public void roleTest2() {
        // Insert mock data into the tree (in addition to the 3 hardcoded songs)
        Song song3 = new Song("Song3", "Artist3", "Genre3", 2022, 120, 90, 80, -3, 15);
        placeholderTree.insert(song3);

        // Setting a loudness range and retrieving songs within the range
        List<String> result = backend.getRange(-8, 0);

        // Expecting 4 songs (3 hardcoded and 1 manually inserted) to be within the range
        assertEquals(4, result.size());
        assertTrue(result.contains("A L I E N S"));
        assertTrue(result.contains("BO$$"));
        assertTrue(result.contains("Cake By The Ocean"));
        assertTrue(result.contains("Song3"));
    }



    /**
     * roleTest3: Tests the setFilter and fiveMost methods of the backend.
     * This test checks if the speed (BPM) filter works and if fiveMost method correctly
     * returns the five most danceable songs after applying both the loudness and speed filters.
     */
    @Test
    public void roleTest3() {
        // Insert only the last song you want to be included in the filtered list
        Song song3 = new Song("Song3", "Artist3", "Genre3", 2022, 130, 90, 95, -3, 15);  // BPM = 130
        placeholderTree.insert(song3);  // Only the last inserted song will be remembered

        // Check if songs are inserted correctly (tree size should still be 4, since it includes hardcoded data)
        System.out.println("Checking tree size: " + placeholderTree.size());

        // Set a speed (BPM) filter
        backend.setFilter(100);  // Filter should allow songs with BPM > 100

        // Call fiveMost to get the five most danceable songs
        List<String> result = backend.fiveMost();

        // Print the result to debug which songs are in the final list
        System.out.println("Resulting songs: " + result);

        // Expect 4 songs (3 hardcoded and 1 inserted that match the BPM filter)
        assertEquals(4, result.size());
        assertTrue(result.contains("BO$$"));  // Hardcoded song likely passes filter
        assertTrue(result.contains("Cake By The Ocean"));  // Hardcoded song likely passes filter
        assertTrue(result.contains("Song3"));  // Inserted song with BPM = 130 (last added)
        assertTrue(result.contains("A L I E N S"));  // Hardcoded song passes filter
    }







}


