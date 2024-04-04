package schema;

/**
 * Class representing Comics table from DB
 * comicId: Unique identifier of comic in DB
 * name: Name of comic
 * description: Short description of comic
 * rating: User rating of the comic (0-10)
 * category: Type of comic
 * recommended: User recommends the comic
 */
public class Comic {
    private int comicId;
    private String name;
    private String description;
    private int rating;
    private String category;
    private boolean recommended;

    public Comic(int comicId, String name, String description, int rating, String category, boolean recommended) {
        this.comicId = comicId;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.category = category;
        this.recommended = recommended;
    }

    public int getComicId() {
        return comicId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getRating() {
        return rating;
    }

    public String getCategory() {
        return category;
    }

    public boolean isRecommended() {
        return recommended;
    }
}
