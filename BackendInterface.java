import java.util.List;
import java.io.IOException;

/**
 * BackendInterface - CS400 Project 1: iSongly
 */
public interface BackendInterface {

  

    /**
     * Loads data from the .csv file referenced by filename. You can rely on
     * the exact headers found in the provided songs.csv, but you should not
     * rely on them always being presented in this order or there not being 
     * additional columns describing other song qualities.
     * After reading songs from the file, the songs are inserted into
     * the tree passed to the constructor.
     * 
     * @param filename the name of the csv file to load data from
     * @throws IOException when there is trouble finding or reading the file
     */
    public void readData(String filename) throws IOException;

    /**
     * Retrieves a list of song titles from the tree passed to the constructor.
     * The songs should be ordered by their Loudness and fall within the specified
     * range of Loudness values. This Loudness range will also be used by future
     * calls to the setFilter and getMostDanceable methods.
     *
     * If a Speed filter has been set using the setFilter method below, then only 
     * songs that pass that filter should be included in the list of titles 
     * returned by this method.
     *
     * When null is passed as either the low or high argument, that end of the 
     * range is understood to be unbounded. For example, a null high argument 
     * means that there is no maximum Loudness to include in the returned list.
     *
     * @param low the minimum Loudness of songs in the returned list
     * @param high the maximum Loudness of songs in the returned list
     * @return List of titles for all songs within the Loudness range, or an empty
     *     list if no such songs have been loaded
     */
    public List<String> getRange(Integer low, Integer high);
    
    /**
     * Retrieves a list of song titles that have a Speed that is
     * larger than the specified threshold.  Similar to the getRange
     * method: this list of song titles should be ordered by the songs'
     * Loudness, and should only include songs that fall within the specified
     * range of Loudness values that was established by the most recent call
     * to getRange.  If getRange has not previously been called, then no low
     * or high Loudness bound should be used.  The filter set by this method
     * will be used by future calls to the getRange and getmost Danceable methods.
     *
     * When null is passed as the threshold to this method, then no Speed
     * threshold should be used.  This effectively clears the filter.
     *
     * @param threshold filters returned song titles to only include songs that
     *     have a Speed that is larger than this threshold.
     * @return List of titles for songs that meet this filter requirement, or
     *     an empty list when no such songs have been loaded
     */
    public List<String> setFilter(Integer threshold);

    /**
     * This method returns a list of song titles representing the five
     * most Danceable songs that both fall within any attribute range specified
     * by the most recent call to getRange, and conform to any filter set by
     * the most recent call to setFilter.  The order of the song titles in this
     * returned list is up to you.
     *
     * If fewer than five such songs exist, return all of them.  And return an
     * empty list when there are no such songs.
     *
     * @return List of five most Danceable song titles
     */
    public List<String> fiveMost();
}
