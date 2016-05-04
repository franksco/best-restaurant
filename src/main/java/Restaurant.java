import java.util.List;
import org.sql2o.*;

public class Restaurant {
  private int id;
  private String rest;
  private String cuisine;
  private String description;
  private String dish;

  public Restaurant(String rest, String cuisine, String description, String dish) {
    this.rest = rest;
    this.cuisine = cuisine;
    this.description = description;
    this.dish = dish;
  }

  public String getName() {
    return rest;
  }

  public String getCuisine() {
    return cuisine;
  }

  public String getDescription() {
    return description;
  }

  public String getDish() {
    return dish;
  }

  public int getId() {
    return id;
  }

  public static List<Restaurant> all() {
    String sql = "SELECT * FROM rests ORDER BY rest ASC";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Restaurant.class);
    }
  }
  public static List<String> allCuisines() {
    String sql = "SELECT cuisine FROM cuisines";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(String.class);
    }
  }

  public static List<Restaurant> listRestaurantsByCuisine(String spec) {
    String special = spec;
    String sql = "SELECT * FROM rests WHERE cuisine=:special ORDER BY rest ASC";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .addParameter("special", special)
      .executeAndFetch(Restaurant.class);
    }
  }

  // public List<Patient> getPatients() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT * FROM patients where doctorid=:id";
  //     return con.createQuery(sql)
  //     .addParameter("id", this.id)
  //     .executeAndFetch(Patient.class);
  //   }
  // }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO rests(rest, cuisine, description, dish) VALUES (:rest, :cuisine, :description, :dish)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("rest", this.rest)
      .addParameter("cuisine", this.cuisine)
      .addParameter("description", this.description)
      .addParameter("dish", this.dish)
      .executeUpdate()
      .getKey();
    }
  }

  public static Restaurant find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM rests where id=:id";
      return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Restaurant.class);
    }
  }
}