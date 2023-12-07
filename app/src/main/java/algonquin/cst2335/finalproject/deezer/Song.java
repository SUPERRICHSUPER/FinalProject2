package algonquin.cst2335.finalproject.deezer;

/**
 * The {@code Song} class represents a musical track with associated information.
 */
public class Song {
    private String id;
    private String title;
    private String duration;
    private String albumTitle;
    private String cover;
    private int isCollection;

    /**
     * Constructs a {@code Song} object with the specified parameters.
     *
     * @param id         The unique identifier of the song.
     * @param title      The title of the song.
     * @param duration   The duration of the song.
     * @param albumTitle The title of the album to which the song belongs.
     * @param cover      The URL of the cover image for the song.
     */
    public Song(String id, String title, String duration, String albumTitle, String cover) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.albumTitle = albumTitle;
        this.cover = cover;
        this.isCollection = 0;
    }

    /**
     * Constructs a {@code Song} object with the specified parameters, including a flag indicating if it is part of a collection.
     *
     * @param id           The unique identifier of the song.
     * @param title        The title of the song.
     * @param duration     The duration of the song.
     * @param albumTitle   The title of the album to which the song belongs.
     * @param cover        The URL of the cover image for the song.
     * @param isCollection A flag indicating whether the song is part of a collection.
     */
    public Song(String id, String title, String duration, String albumTitle, String cover, int isCollection) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.albumTitle = albumTitle;
        this.cover = cover;
        this.isCollection = isCollection;
    }

    /**
     * Gets the value of the "isCollection" flag.
     *
     * @return An integer indicating whether the song is part of a collection (1 if true, 0 if false).
     */
    public int getIsCollection() {
        return isCollection;
    }

    /**
     * Sets the value of the "isCollection" flag.
     *
     * @param isCollection An integer indicating whether the song is part of a collection (1 if true, 0 if false).
     */
    public void setIsCollection(int isCollection) {
        this.isCollection = isCollection;
    }

    /**
     * Returns a string representation of the {@code Song} object.
     *
     * @return A string containing the details of the song.
     */
    @Override
    public String toString() {
        return "Song{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", duration='" + duration + '\'' +
                ", albumTitle='" + albumTitle + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }

    /**
     * Gets the unique identifier of the song.
     *
     * @return The unique identifier of the song.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the song.
     *
     * @param id The unique identifier of the song.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the title of the song.
     *
     * @return The title of the song.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the song.
     *
     * @param title The title of the song.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the duration of the song.
     *
     * @return The duration of the song.
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Sets the duration of the song.
     *
     * @param duration The duration of the song.
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * Gets the title of the album to which the song belongs.
     *
     * @return The title of the album.
     */
    public String getAlbumTitle() {
        return albumTitle;
    }

    /**
     * Sets the title of the album to which the song belongs.
     *
     * @param albumTitle The title of the album.
     */
    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    /**
     * Gets the URL of the cover image for the song.
     *
     * @return The URL of the cover image.
     */
    public String getCover() {
        return cover;
    }

    /**
     * Sets the URL of the cover image for the song.
     *
     * @param cover The URL of the cover image.
     */
    public void setCover(String cover) {
        this.cover = cover;
    }
}
