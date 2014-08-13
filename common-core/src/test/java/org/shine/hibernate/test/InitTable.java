package org.shine.hibernate.test;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class InitTable {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        Configuration config = new Configuration().configure();
        SchemaExport export = new SchemaExport(config);
        export.create(true, true);

    }

}
