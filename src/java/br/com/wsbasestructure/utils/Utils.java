package br.com.wsbasestructure.utils;

/**
 *
 * @author Andrew Ribeiro
 */
public class Utils {

    public static <T> T initializeAndUnproxy(T entity) {
        if (entity == null) {
            return null;
        }

        org.hibernate.Hibernate.initialize(entity);
        if (entity instanceof org.hibernate.proxy.HibernateProxy) {
            entity = (T) ((org.hibernate.proxy.HibernateProxy) entity).getHibernateLazyInitializer()
                    .getImplementation();
        }
        return entity;
    }

    public static String convertStreamToString(java.io.InputStream is) {
        if (is == null) {
            return "";
        } else {
            java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        }
    }
    
    public static void buildCriteriaFilters(org.hibernate.Criteria c, java.util.List f){
        if(f != null && !f.isEmpty()){
            org.hibernate.criterion.Criterion[] criterion = new org.hibernate.criterion.Criterion[f.size()];
            for (int i = 0; i < f.size(); i++) {
                String[] splitted = ((String) f.get(i)).split("filter-value");
                if(splitted.length < 2) return;
                Object o;
                try {
                    o = Long.parseLong(splitted[1]);
                }catch(NumberFormatException nfe){
                    o = splitted[1];
                }
                criterion[i] = org.hibernate.criterion.Restrictions.eq(splitted[0], o);
                
                splitted = splitted[0].split(".");
                if(splitted.length == 2){
                    c.createAlias(splitted[0], splitted[0]);
                }
                
            }
            c.add(org.hibernate.criterion.Restrictions.conjunction(criterion));
        }
    }

}
