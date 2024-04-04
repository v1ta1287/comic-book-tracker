package schema;

/**
 * Class representing Characters table from DB
 * characterId: Unique identifier of character in DB
 * name: Name of character
 * description: Short description of character
 * comicName: Name of the comic the character belongs to
 * age: Age of character
 * status: Status of character (Alive, Dead, Unknown)
 */
public class Character {
    private int characterId;
    private String name;
    private String description;
    private String comicName;
    private int age;
    private String status;

    public Character(int characterId, String name, String description, String comic, int age, String status) {
        this.characterId = characterId;
        this.name = name;
        this.description = description;
        this.comicName = comic;
        this.age = age;
        this.status = status;
    }

    public int getCharacterId() {
        return characterId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getComicName() {
        return comicName;
    }

    public int getAge() {
        return age;
    }

    public String getStatus() {
        return status;
    }
}
