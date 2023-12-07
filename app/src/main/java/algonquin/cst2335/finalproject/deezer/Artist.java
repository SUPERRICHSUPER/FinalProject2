package algonquin.cst2335.finalproject.deezer;

import java.io.Serializable;

/**
 * The {@code Artist} class represents information about a music artist.
 * It implements the {@code Serializable} interface to support object serialization.
 */
public class Artist implements Serializable {
    /** The unique identifier of the artist. */
    private String id;

    /** The name of the artist. */
    private String name;

    /** The link to the artist's profile. */
    private String link;

    /** The URL of the artist's picture. */
    private String picture;

    /** The number of albums the artist has. */
    private String nb_album;

    /** The number of fans or followers of the artist. */
    private String nb_fan;

    /** The radio associated with the artist. */
    private String radio;

    /** The URL of the artist's tracklist. */
    private String tracklist;

    /** Indicates whether the artist is part of a collection. */
    private boolean isCollection;

    /**
     * Constructs an {@code Artist} object with the specified details.
     *
     * @param id            The unique identifier of the artist.
     * @param name          The name of the artist.
     * @param link          The link to the artist's profile.
     * @param picture       The URL of the artist's picture.
     * @param nb_album      The number of albums the artist has.
     * @param nb_fan        The number of fans or followers of the artist.
     * @param radio         The radio associated with the artist.
     * @param tracklist     The URL of the artist's tracklist.
     * @param isCollection  Indicates whether the artist is part of a collection.
     */
    public Artist(String id, String name, String link, String picture, String nb_album, String nb_fan, String radio, String tracklist, boolean isCollection) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.picture = picture;
        this.nb_album = nb_album;
        this.nb_fan = nb_fan;
        this.radio = radio;
        this.tracklist = tracklist;
        this.isCollection = isCollection;
    }

    /**
     * Gets the unique identifier of the artist.
     *
     * @return The artist's unique identifier.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the artist.
     *
     * @param id The new unique identifier for the artist.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the name of the artist.
     *
     * @return The name of the artist.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the artist.
     *
     * @param name The new name for the artist.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the link to the artist's profile.
     *
     * @return The link to the artist's profile.
     */
    public String getLink() {
        return link;
    }

    /**
     * Sets the link to the artist's profile.
     *
     * @param link The new link to the artist's profile.
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Gets the URL of the artist's picture.
     *
     * @return The URL of the artist's picture.
     */
    public String getPicture() {
        return picture;
    }

    /**
     * Sets the URL of the artist's picture.
     *
     * @param picture The new URL of the artist's picture.
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     * Gets the number of albums the artist has.
     *
     * @return The number of albums the artist has.
     */
    public String getNb_album() {
        return nb_album;
    }

    /**
     * Sets the number of albums the artist has.
     *
     * @param nb_album The new number of albums for the artist.
     */
    public void setNb_album(String nb_album) {
        this.nb_album = nb_album;
    }

    /**
     * Gets the number of fans or followers of the artist.
     *
     * @return The number of fans or followers of the artist.
     */
    public String getNb_fan() {
        return nb_fan;
    }

    /**
     * Sets the number of fans or followers of the artist.
     *
     * @param nb_fan The new number of fans or followers for the artist.
     */
    public void setNb_fan(String nb_fan) {
        this.nb_fan = nb_fan;
    }

    /**
     * Gets the radio associated with the artist.
     *
     * @return The radio associated with the artist.
     */
    public String getRadio() {
        return radio;
    }

    /**
     * Sets the radio associated with the artist.
     *
     * @param radio The new radio associated with the artist.
     */
    public void setRadio(String radio) {
        this.radio = radio;
    }

    /**
     * Gets the URL of the artist's tracklist.
     *
     * @return The URL of the artist's tracklist.
     */
    public String getTracklist() {
        return tracklist;
    }

    /**
     * Sets the URL of the artist's tracklist.
     *
     * @param tracklist The new URL of the artist's tracklist.
     */
    public void setTracklist(String tracklist) {
        this.tracklist = tracklist;
    }

    /**
     * Checks whether the artist is part of a collection.
     *
     * @return {@code true} if the artist is part of a collection, {@code false} otherwise.
     */
    public boolean isCollection() {
        return isCollection;
    }

    /**
     * Sets whether the artist is part of a collection.
     *
     * @param collection {@code true} if the artist is part of a collection, {@code false} otherwise.
     */
    public void setCollection(boolean collection) {
        isCollection = collection;
    }

    /**
     * Returns a string representation of the {@code Artist} object.
     *
     * @return A string representation of the {@code Artist} object.
     */
    @Override
    public String toString() {
        return "Artist{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", picture='" + picture + '\'' +
                ", nb_album='" + nb_album + '\'' +
                ", nb_fan='" + nb_fan + '\'' +
                ", radio='" + radio + '\'' +
                ", tracklist='" + tracklist + '\'' +
                ", isCollection=" + isCollection +
                '}';
    }
}
