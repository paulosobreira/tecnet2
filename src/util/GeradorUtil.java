package util;


import java.util.*;

public class GeradorUtil extends Object {

  public static String setJavaName(String NAME) {
    String new_name = NAME;
    if (NAME.length() > 1) {
      if (NAME.substring(1, 2).toUpperCase().equals(NAME.substring(1, 2))) {
        new_name = NAME.toLowerCase();
      }
    }
    if (new_name.length() > 1) {
      new_name = new_name.substring(0, 1).toUpperCase() + new_name.substring(1, new_name.length());
    } else if (new_name.length() == 1) {
      new_name = new_name.toUpperCase();
    }
    return new_name;
  }

  public static String setFirstLetter(String NAME) {
    if (NAME.length() > 1) {
      NAME = NAME.substring(0, 1).toUpperCase() + NAME.substring(1, NAME.length());
    } else if (NAME.length() == 1) {
      NAME = NAME.toUpperCase();
    }
    return NAME;
  }

  public static String setFirstLetterLower(String NAME) {
    if (NAME.length() > 1) {
      NAME = NAME.substring(0, 1).toLowerCase() + NAME.substring(1, NAME.length());
    } else if (NAME.length() == 1) {
      NAME = NAME.toUpperCase();
    }
    return NAME;
  }

  public static String setAttributeName(String NAME) {
    String new_name = NAME;
    if (NAME.length() > 1) {
      if (NAME.substring(1, 2).toUpperCase().equals(NAME.substring(1, 2))) {
        new_name = NAME.toLowerCase();
      }
    }
    if (new_name.length() > 1) {
      new_name = new_name.substring(0, 1).toUpperCase() + new_name.substring(1, new_name.length());
    } else if (new_name.length() == 1) {
      new_name = new_name.toUpperCase();
    }
    return new_name;
  }

  public static String getAttributeValuePar(Object field_tag) throws Exception {
    StringBuffer txt = new StringBuffer();
    Class object_class = field_tag.getClass();
    java.lang.reflect.Field[] class_fields = object_class.getDeclaredFields();
    java.lang.reflect.Method[] methods = object_class.getMethods();
    for (int i = 0; i < methods.length; i++) {
      java.lang.reflect.Method method = methods[i];
      if (method.getName().startsWith("get") && method.getReturnType().getName().equals("java.lang.String")) {
        String value = (String)method.invoke(field_tag, new Object[0]);
        if (value != null && value.length() > 0 && (!method.getName().equals("getType"))) {
          String attribute = method.getName().substring(3, 4).toLowerCase() + method.getName().substring(4, method.getName().length());
          txt.append(" " + attribute + "=\"" + value + "\"");
        }
      }
    }
    return txt.toString();
  }

  public static Vector getObjectList(String class_path, String base_package) {
    Vector list = new Vector(2, 1);
    String dbobj_path = "\\dbobj\\table\\";
    String file_path = setSlash(class_path + base_package + dbobj_path);
    java.io.File dir = new java.io.File(file_path);
    if (dir != null) {
      String[] list_table = dir.list();
      if (list_table != null) {
        dbobj_path = base_package.replace("\\".charAt(0), ".".charAt(0)) + dbobj_path.replace("\\".charAt(0), ".".charAt(0));
        dbobj_path = dbobj_path.replace("/".charAt(0), ".".charAt(0));
        for (int i = 0; i < list_table.length; i++) {
          if (list_table[i] != null && !list_table[i].endsWith("MetaData.class") && list_table[i].endsWith(".class")) {
            list.addElement(dbobj_path + list_table[i].substring(0, list_table[i].indexOf(".class")));
          }
        }
      }
    }
    dbobj_path = "\\dbobj\\view\\";
    file_path = setSlash(class_path + base_package + dbobj_path);
    dir = new java.io.File(file_path);
    if (dir != null) {
      String[] list_view = dir.list();
      if (list_view != null) {
        dbobj_path = base_package.replace("\\".charAt(0), ".".charAt(0)) + dbobj_path.replace("\\".charAt(0), ".".charAt(0));
        dbobj_path = dbobj_path.replace("/".charAt(0), ".".charAt(0));
        for (int i = 0; i < list_view.length; i++) {
          if (list_view[i] != null && !list_view[i].endsWith("MetaData.class") && list_view[i].endsWith(".class")) {
            list.addElement(dbobj_path + list_view[i].substring(0, list_view[i].indexOf(".class")));
          }
        }
      }
    }
    dbobj_path = "\\dbobj\\procedure\\";
    file_path = setSlash(class_path + base_package + dbobj_path);
    dir = new java.io.File(file_path);
    if (dir != null) {
      String[] list_view = dir.list();
      if (list_view != null) {
        dbobj_path = base_package.replace("\\".charAt(0), ".".charAt(0)) + dbobj_path.replace("\\".charAt(0), ".".charAt(0));
        dbobj_path = dbobj_path.replace("/".charAt(0), ".".charAt(0));
        for (int i = 0; i < list_view.length; i++) {
          if (list_view[i] != null && !list_view[i].endsWith("MetaData.class") && list_view[i].endsWith(".class")) {
            list.addElement(dbobj_path + list_view[i].substring(0, list_view[i].indexOf(".class")));
          }
        }
      }
    }
    Collections.sort(list);
    return list;
  }

  public static Vector getViewList(String class_path, String base_package) {
    Vector list = new Vector(2, 1);
    String dbobj_path = "\\dbobj\\view\\";
    String file_path = setSlash(class_path + base_package + dbobj_path);
    java.io.File dir = new java.io.File(file_path);
    if (dir != null) {
      String[] list_table = dir.list();
      if (list_table != null) {
        dbobj_path = base_package.replace("\\".charAt(0), ".".charAt(0)) + dbobj_path.replace("\\".charAt(0), ".".charAt(0));
        dbobj_path = dbobj_path.replace("/".charAt(0), ".".charAt(0));
        for (int i = 0; i < list_table.length; i++) {
          if (list_table[i] != null && !list_table[i].endsWith("MetaData.class") && list_table[i].endsWith(".class")) {
            list.addElement(dbobj_path + list_table[i].substring(0, list_table[i].indexOf(".class")));
          }
        }
      }
    }
    Collections.sort(list);
    return list;
  }

  public static Vector getMeta_infoList(String source_path, String base_package) {
    Vector list = getFileList(source_path, base_package, "META-INFO", "xml", "");
    Collections.sort(list);
    return list;
  }

  public static Vector getFileList(String source_path, String base_package, String MODULE) {
    Vector list = new Vector();
    String file_path = setSlash(source_path + base_package);
    java.io.File dir = null;
    try {
      dir = new java.io.File(file_path);
    } catch (Exception e) {
    }
    if (dir != null) {
      String[] list_table = dir.list();
      if (list_table != null) {
        for (int i = 0; i < list_table.length; i++) {
          list.addElement(list_table[i]);
        }
        Collections.sort(list);
      }
    }
    return list;
  }

  public static Vector getFileList(String source_path, String base_package, String MODULE, String TYPE) {
    Vector list = getFileList(source_path, base_package, MODULE, TYPE, null);
    return list;
  }

  public static Vector getFileList(String source_path, String base_package, String MODULE, String TYPE, String FILTRO) {
    Vector list = new Vector(2, 1);
    String type = "";
    if (TYPE != null && TYPE.length() > 0) {
      type = "." + TYPE;
    }
    if (FILTRO != null && FILTRO.length() > 0) {
      type = FILTRO + "." + TYPE;
    }
    if (MODULE == null || MODULE.length() == 0) {
      MODULE = "";
    } else {
      MODULE = MODULE + "/";
    }
    String file_path = setSlash(source_path + base_package + "/" + MODULE);
    java.io.File dir = null;
    try {
      dir = new java.io.File(file_path);
    } catch (Exception e) {
    }
    if (dir != null) {
      String[] list_table = dir.list();
      if (list_table != null) {
        for (int i = 0; i < list_table.length; i++) {
          if (list_table[i] != null && list_table[i].indexOf(".") == -1) {
            String module = list_table[i];
            Vector sub_list = getFileList(source_path, base_package, MODULE + module, TYPE, FILTRO);
            for (int j = 0; j < sub_list.size(); j++) {
              list.addElement((String)sub_list.elementAt(j));
            }
          }
          if (list_table[i] != null && list_table[i].endsWith(type)) {
            list.addElement(file_path + list_table[i]);
          }
        }
        Collections.sort(list);
      }
    }
    return list;
  }

  public static Vector getTemplateList(String develop_path) {
    Vector list = new Vector(2, 1);
    String template_path = "/template/";
    String file_path = setSlash(develop_path + template_path);
    java.io.File dir = new java.io.File(file_path);
    String[] list_table = dir.list();
    for (int i = 0; i < list_table.length; i++) {
      if (list_table[i] != null && list_table[i].endsWith(".xml")) {
        list.addElement(file_path + list_table[i]);
      }
    }
    Collections.sort(list);
    return list;
  }

  public static Vector getLovList(String html_path) {
    Vector list = new Vector(2, 1);
    String lov_path = "lov/";
    String file_path = setSlash(html_path + lov_path);
    java.io.File dir = new java.io.File(file_path);
    String[] list_table = dir.list();
    if (list_table != null) {
      for (int i = 0; i < list_table.length; i++) {
        if (list_table[i] != null && list_table[i].endsWith("Lov.jsp")) {
          list.addElement(file_path + list_table[i]);
        }
      }
    }
    Collections.sort(list);
    return list;
  }

  public static String getAttributeValueParXml(Object field_tag) throws Exception {
    StringBuffer txt = new StringBuffer();
    Class object_class = field_tag.getClass();
    java.lang.reflect.Method[] methods = object_class.getMethods();
    for (int i = 0; i < methods.length; i++) {
      java.lang.reflect.Method method = methods[i];
      if (method.getName().startsWith("get") && method.getReturnType().getName().equals("java.lang.String")) {
        String value = (String)method.invoke(field_tag, new Object[0]);
        if (value != null && value.length() > 0) {
          String attribute = method.getName().substring(3, 4).toLowerCase() + method.getName().substring(4, method.getName().length());
          txt.append("<" + attribute + ">" + value + "</" + attribute + ">\n");
        }
      }
    }
    return txt.toString();
  }

  public static void reset_session(javax.servlet.http.HttpServletRequest request) throws Exception {
    javax.servlet.http.HttpSession session = request.getSession(true);
    Enumeration atts = session.getAttributeNames();
    java.util.Vector v = new java.util.Vector();
    while (atts.hasMoreElements()) {
      v.addElement((String)atts.nextElement());
    }
    for (int i = 0; i < v.size(); i++) {
      String att = (String)v.elementAt(i);
      if (att != null && !(att.equals("org.apache.struts.action.LOCALE"))) {
        if (!(att.equals("project"))) {
          session.removeAttribute(att);
        }
      }
    }
  }

  public static Vector getJspList(String path) {
    Vector list = new Vector(2, 1);
    String type = ".jsp";
    String file_path = setSlash(path);
    java.io.File dir = new java.io.File(file_path);
    String[] list_table = dir.list();
    if (list_table != null) {
      for (int i = 0; i < list_table.length; i++) {
        if (list_table[i] != null && list_table[i].indexOf(".") == -1) {
          String module = list_table[i];
          Vector sub_list = getJspList(module);
          for (int j = 0; j < sub_list.size(); j++) {
            list.addElement((String)sub_list.elementAt(j));
          }
        }
        if (list_table[i] != null && list_table[i].endsWith(type)) {
          list.addElement(file_path + list_table[i]);
        }
      }
    }
    Collections.sort(list);
    return list;
  }
  public static String setSlash(String PATH) {
      char point = ".".charAt(0);
      char slash = "/".charAt(0);
      char backSlash = "\\".charAt(0);
      String new_path = PATH.replace(point,slash);
      String os = System.getProperty("os.name");
      if ( os!=null && os.toUpperCase().startsWith("WINDOWS") ) {
        new_path = new_path.replace(slash,backSlash);
      } else {
        new_path = new_path.replace(backSlash,slash);
      }
      return new_path;
    }

    public static String compact(boolean fl_compact, String CODE) {
      String txt = CODE;
      if (fl_compact) {
        StringBuffer b = new StringBuffer(txt);
        int pos = 0;
        while (pos!=-1) {
          pos = b.indexOf("\n");
          if (pos>0) {
            b.delete(pos,pos+1);
          }
        }
        pos = 0;
        while (pos!=-1) {
          pos = b.indexOf("  ");
          if (pos>0) {
            b.delete(pos,pos+1);
          }
        }
        pos = 0;
        while (pos!=-1) {
          pos = b.indexOf(" =");
          if (pos>0) {
            b.delete(pos,pos+1);
          }
        }
        pos = 0;
        while (pos!=-1) {
          pos = b.indexOf("= ");
          if (pos>0) {
            b.delete(pos+1,pos+2);
          }
        }
        pos = 0;
        while (pos!=-1) {
          pos = b.indexOf(" %");
          if (pos>0) {
            b.delete(pos,pos+1);
          }
        }
        pos = 0;
        while (pos!=-1) {
          pos = b.indexOf("> <");
          if (pos>0) {
            b.delete(pos+1,pos+2);
          }
        }
        txt = b.toString();
      }
      return txt;
    }
}
