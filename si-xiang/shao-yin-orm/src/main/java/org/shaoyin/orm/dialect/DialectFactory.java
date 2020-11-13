package org.shaoyin.orm.dialect;

import org.shaoyin.orm.dialect.impl.AnsiSqlDialect;
import org.shaoyin.orm.dialect.impl.MysqlDialect;
import org.yang.localtools.util.StringUtil;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 方言工厂
 */
public class DialectFactory {

    /**
     * JDBC 驱动 MySQL */
    public static final String DRIVER_MYSQL = "com.mysql.jdbc.Driver";
    /** JDBC 驱动 MySQL，在6.X版本中变动驱动类名，且使用SPI机制 */
    public static final String DRIVER_MYSQL_V6 = "com.mysql.cj.jdbc.Driver";
    /** JDBC 驱动 Oracle */
    public static final String DRIVER_ORACLE = "oracle.jdbc.OracleDriver";
    /** JDBC 驱动 Oracle，旧版使用 */
    public static final String DRIVER_ORACLE_OLD = "oracle.jdbc.driver.OracleDriver";
    /** JDBC 驱动 PostgreSQL */
    public static final String DRIVER_POSTGRESQL = "org.postgresql.Driver";
    /** JDBC 驱动 SQLLite3 */
    public static final String DRIVER_SQLLITE3 = "org.sqlite.JDBC";
    /** JDBC 驱动 SQLServer */
    public static final String DRIVER_SQLSERVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    /** JDBC 驱动 Hive */
    public static final String DRIVER_HIVE = "org.apache.hadoop.hive.jdbc.HiveDriver";
    /** JDBC 驱动 Hive2 */
    public static final String DRIVER_HIVE2 = "org.apache.hive.jdbc.HiveDriver";
    /** JDBC 驱动 H2 */
    public static final String DRIVER_H2 = "org.h2.Driver";
    /** JDBC 驱动 Derby */
    public static final String DRIVER_DERBY = "org.apache.derby.jdbc.AutoloadedDriver";
    /** JDBC 驱动 HSQLDB */
    public static final String DRIVER_HSQLDB = "org.hsqldb.jdbc.JDBCDriver";
    /** JDBC 驱动 达梦7 */
    public static final String DRIVER_DM7 = "dm.jdbc.driver.DmDriver";

    private static final Map<DataSource, Dialect> DIALECT_POOL = new ConcurrentHashMap<>();

    /**
     * 禁止创建对象
     */
    private DialectFactory() {
    }

    /**
     * 创建方言
     * @param driverName 驱动名称
     * @return 方言
     */
    public static Dialect newDialect(String driverName){
        final Dialect dialect = internalNewDialect(driverName);
        return dialect;
    }

    /**
     * 根据驱动名创建方言<br>
     * 驱动名是不分区大小写完全匹配的
     *
     * @param driverName JDBC驱动类名
     * @return 方言
     */
    private static Dialect internalNewDialect(String driverName) {
        if (!StringUtil.isNull(driverName)) {
            if (DRIVER_MYSQL.equalsIgnoreCase(driverName) || DRIVER_MYSQL_V6.equalsIgnoreCase(driverName)) {
                return new MysqlDialect();
            }
        }
        // 无法识别可支持的数据库类型默认使用ANSI方言，可兼容大部分SQL语句
        return new AnsiSqlDialect();
    }

}
