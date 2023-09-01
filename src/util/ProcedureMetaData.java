package util;



public class ProcedureMetaData extends Object {
  private String datatype;
  private String name;
  private String width;
  private String type;
  private String db_type;
  private String catalog;

  /**
   * Constructor
   */
  public ProcedureMetaData() {
  }

  public void setName(String Name) {
    name = Name;
  }
 
  public String getName() {
    return name;
  }
 
  public void setDatatype(String Datatype) {
    datatype = Datatype;
  }
 
  public String getDatatype() {
    return datatype;
  }
 
  public void setWidth(String Width) {
    width = Width;
  }
 
  public String getWidth() {
    return width;
  }
 
  public void setType(String Type) {
    type = Type;
  }

  public String getType() {
    return type;
  }

  public void setDb_type(String Db_type) {
    db_type = Db_type;
  }

  public String getDb_type() {
    return db_type;
  }

  public void setCatalog(String Catalog) {
    catalog = Catalog;
  }

  public String getCatalog() {
    return catalog;
  }

}

