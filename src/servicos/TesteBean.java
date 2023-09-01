package servicos;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import vos.tabelas.Categoria;

import java.lang.reflect.InvocationTargetException;

import java.util.Iterator;
import java.util.Map;


public class TesteBean {
    public static void main(String[] args)
        throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        Categoria categoria = new Categoria();
        Map describe = BeanUtils.describe(categoria);

        for (Iterator iter = describe.keySet().iterator(); iter.hasNext();) {
            String key = (String) iter.next();

            System.out.print("Key : " + key);

            Class type = PropertyUtils.getPropertyType(categoria, key);
            System.out.print("\t Tipo : " + type.getName());
            System.out.println("\t Value : " +
                ((describe.get(key) != null) ? describe.get(key).getClass() : ""));
        }
    }
}
