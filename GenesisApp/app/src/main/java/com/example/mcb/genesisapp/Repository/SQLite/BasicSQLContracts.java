package com.example.mcb.genesisapp.Repository.SQLite;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.renderscript.Sampler;

/**
 * Holds SQL statements for creating, accessing and destroying tables in the SQLlite database.
 * Static class
 * Created bhy mcb on 11.02.18.
 */

public class BasicSQLContracts {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public BasicSQLContracts() {}

    private static final String TEXT_TYPE = " TEXT ";
    // private static final String IMAGE_TYPE = " BLOB ";
    private static final String COMMA_SEP = ",";
    private static final String SEMICOLON_SEP=";";
    private static final String NOT_NULL = " NOT NULL ";
    private static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS ";
    private static final String DROP_INDEX_IF_EXISTS = "DROP INDEX IF EXISTS ";
    private static final String CREATE_TABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS ";
    private static final String INTEGER_PRIMARY_KEY = " INTEGER PRIMARY KEY ";
    private static final String INTEGER = " INTEGER ";
    private static final String DEFAULT_CURRENT_TIME = " DEFAULT CURRENT_TIMESTAMP ";
    private static final String CREATE_INDEX_IF_NOT_EXISTENT=" CREATE INDEX IF NOT EXISTS ";

    public static void createTables(SQLiteDatabase db) {

        //   db.execSQL(PrimitiveAttributes.SQL_CREATE);
        db.execSQL(BasicTokenTable.SQL_CREATE);
        db.execSQL(AddressTokenValueTable.SQL_CREATE);
        db.execSQL(ValueTransactionTable.SQL_CREATE);

        // custom
        db.execSQL(WebsiteAccount.SQL_CREATE);
        db.execSQL(UserAccount.SQL_CREATE);
      //  db.execSQL(UserAccount.CREATE_INDEX_NAME_COLUMN);

        // Create Indices
        db.execSQL(BasicTokenTable.CREATE_INDEX_NAME_COLUMN);
        //db.execSQL(UserAccount.CREATE_INDEX_NAME_COLUMN);

        db.execSQL(AddressTokenValueTable.CREATE_INDEX_TOKEN_ID);
        db.execSQL(ValueTransactionTable.CREATE_INDEX_TOKEN_ID);
        db.execSQL(ValueTransactionTable.CREATE_INDEX_TOKEN_SENDER);
        db.execSQL(ValueTransactionTable.CREATE_INDEX_TOKEN_RECEIVER);
    }

    public static void dropTables(SQLiteDatabase db) {
        //Drop Indices
        db.execSQL(DROP_INDEX_IF_EXISTS + BasicTokenTable.INDEX_NAME_NAME_COLUMN + SEMICOLON_SEP);
        db.execSQL(DROP_INDEX_IF_EXISTS + ValueTransactionTable.INDEX_NAME_TOKEN_ID + SEMICOLON_SEP);
        db.execSQL(DROP_INDEX_IF_EXISTS + ValueTransactionTable.INDEX_NAME_ADDRESS_SENDER + SEMICOLON_SEP);
        db.execSQL(DROP_INDEX_IF_EXISTS + ValueTransactionTable.INDEX_NAME_ADDRESS_RECEIVER + SEMICOLON_SEP);
        db.execSQL(DROP_INDEX_IF_EXISTS + AddressTokenValueTable.INDEX_NAME_TOKEN_ID + SEMICOLON_SEP);
        //DROP Tables
        db.execSQL(DROP_TABLE_IF_EXISTS + BasicTokenTable.TABLE_NAME + SEMICOLON_SEP);
        db.execSQL(DROP_TABLE_IF_EXISTS + ValueTransactionTable.TABLE_NAME + SEMICOLON_SEP);
        db.execSQL(DROP_TABLE_IF_EXISTS + AddressTokenValueTable.TABLE_NAME + SEMICOLON_SEP);

        // custom
        db.execSQL(DROP_INDEX_IF_EXISTS + WebsiteAccount.INDEX_NAME_NAME_COLUMN + SEMICOLON_SEP);
        db.execSQL(DROP_INDEX_IF_EXISTS + UserAccount.INDEX_NAME_NAME_COLUMN + SEMICOLON_SEP);
    }

    public static abstract class AddressTokenValueTable implements BaseColumns{

        public static final String TABLE_NAME = "AddressTokenValueTable";
        public static final String COLUMN_NAME_TOKEN_ID="TokenID";
        public static final String COLUMN_NAME_VALUE= "Value";
        public static final String COLUMN_NAME_ADDRESS_CREATION_TIME="CreationTime";

        public static final String INDEX_NAME_TOKEN_ID="indexTokenID";

        public static final int id=0;
        public static final int tokenId=1;
        public static final int value=2;
        public static final int creationTime=3;

        // create statement
        public static final String SQL_CREATE =
                CREATE_TABLE_IF_NOT_EXISTS + TABLE_NAME + " (" +
                        _ID + INTEGER_PRIMARY_KEY + NOT_NULL + COMMA_SEP +
                        COLUMN_NAME_TOKEN_ID + INTEGER + NOT_NULL + COMMA_SEP +
                        COLUMN_NAME_VALUE + INTEGER + NOT_NULL + COMMA_SEP +
                        COLUMN_NAME_ADDRESS_CREATION_TIME + TEXT_TYPE + DEFAULT_CURRENT_TIME  + COMMA_SEP+
                        " FOREIGN KEY( " + COLUMN_NAME_TOKEN_ID + ") REFERENCES "
                        + BasicTokenTable.TABLE_NAME + "(" + BasicTokenTable._ID + ")" +
                        " );";

        // all column names - handy for querries
        public static String[] allColumns = {_ID, COLUMN_NAME_TOKEN_ID, COLUMN_NAME_VALUE,
                COLUMN_NAME_ADDRESS_CREATION_TIME};

        /*
         * Create and destroy index
         */
        public static String CREATE_INDEX_TOKEN_ID = CREATE_INDEX_IF_NOT_EXISTENT+ INDEX_NAME_TOKEN_ID +" ON "
                + TABLE_NAME + " ("+COLUMN_NAME_TOKEN_ID+" ASC )";
    }

    public static abstract class ValueTransactionTable implements BaseColumns{

        public static final String TABLE_NAME = "ValueTransactionTable";
        public static final String COLUMN_NAME_TOKEN_ID="TokenID";
        public static final String COLUMN_NAME_ADDRESS_SENDER= "AddressSender";
        public static final String COLUMN_NAME_ADDRESS_RECEIVER="AddressReceiver";
        public static final String COLUMN_NAME_VALUE="Value";
        public static final String COLUMN_NAME_TRANSACTION_TIME="TransactionTime";

        public static final String INDEX_NAME_TOKEN_ID="indexTokenID";
        public static final String INDEX_NAME_ADDRESS_SENDER="indexAnddressSender";
        public static final String INDEX_NAME_ADDRESS_RECEIVER="indexAddressReceiver";

        public static final int id=0;
        public static final int tokenId=1;
        public static final int addressSender=2;
        public static final int addressReceiver=3;
        public static final int value=4;
        public static final int transactionTime=5;

        // create statement
        public static final String SQL_CREATE =
                CREATE_TABLE_IF_NOT_EXISTS + TABLE_NAME + " (" +
                        _ID + INTEGER_PRIMARY_KEY + NOT_NULL + COMMA_SEP +
                        COLUMN_NAME_TOKEN_ID + INTEGER + NOT_NULL + COMMA_SEP +
                        COLUMN_NAME_ADDRESS_SENDER + INTEGER + NOT_NULL + COMMA_SEP +
                        COLUMN_NAME_ADDRESS_RECEIVER + INTEGER + NOT_NULL + COMMA_SEP +
                        COLUMN_NAME_VALUE + INTEGER + NOT_NULL + COMMA_SEP +
                        COLUMN_NAME_TRANSACTION_TIME + TEXT_TYPE + DEFAULT_CURRENT_TIME  + COMMA_SEP +
                        "FOREIGN KEY(" + COLUMN_NAME_TOKEN_ID + ") REFERENCES "
                        + BasicTokenTable.TABLE_NAME + "(" + BasicTokenTable._ID + ")" + COMMA_SEP+
                        "FOREIGN KEY(" + COLUMN_NAME_ADDRESS_SENDER + ") REFERENCES "
                        + AddressTokenValueTable.TABLE_NAME + "(" + AddressTokenValueTable._ID + ")" + COMMA_SEP +
                        "FOREIGN KEY(" + COLUMN_NAME_ADDRESS_RECEIVER + ") REFERENCES "
                        + AddressTokenValueTable.TABLE_NAME + "(" + AddressTokenValueTable._ID + ")" +
                        " );";

        // all column names - handy for querries
        public static String[] allColumns = {_ID, COLUMN_NAME_TOKEN_ID, COLUMN_NAME_ADDRESS_SENDER,
                COLUMN_NAME_ADDRESS_RECEIVER, COLUMN_NAME_VALUE,
                COLUMN_NAME_TRANSACTION_TIME};

        /*
         * Create and destroy index
         */
        public static String CREATE_INDEX_TOKEN_ID = CREATE_INDEX_IF_NOT_EXISTENT+ INDEX_NAME_TOKEN_ID +" ON "
                + TABLE_NAME + " ("+COLUMN_NAME_TOKEN_ID+" ASC )";
        public static String CREATE_INDEX_TOKEN_SENDER = CREATE_INDEX_IF_NOT_EXISTENT + INDEX_NAME_ADDRESS_SENDER + " ON "+
                TABLE_NAME + " ( " +COLUMN_NAME_ADDRESS_SENDER + " ASC )";
        public static String CREATE_INDEX_TOKEN_RECEIVER = CREATE_INDEX_IF_NOT_EXISTENT + INDEX_NAME_ADDRESS_RECEIVER + " ON "+
                TABLE_NAME + " ( " +COLUMN_NAME_ADDRESS_RECEIVER + " ASC )";

        public static String DROP_INDEX_TOKEN_ID = "DROP INDEX "+ INDEX_NAME_TOKEN_ID;
        public static String DROP_INDEX_ADDRESS_SENDER = " DROP INDEX " + INDEX_NAME_ADDRESS_SENDER;
        public static String DROP_INDEX_ADDRESS_RECEIVER = " DROP INDEX " + INDEX_NAME_ADDRESS_RECEIVER;



    }


    /**
     * Table holding the basic Properties of a Token
     */
    public static abstract class BasicTokenTable implements BaseColumns {
        public static final String TABLE_NAME = "BasicTokenTable";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUMN_NAME_SYMBOL="Symbol";
        public static final String COLUMN_NAME_DECIMALS = "Decimals";
        public static final String COLUMN_NAME_GENERAL_SUPPLY ="GeneralSupply";
        public static final String COLUMN_NAME_GENESIS_SUPPLY= "GenesisSupply";
        public static final String COLUMN_NAME_CREATION_DATE = "CreationDate";

        public static final String INDEX_NAME_NAME_COLUMN = "nameIndex";


        public static final int idColumn =0;
        public static final int nameColumng=1;
        public static final int symbolColumn=2;
        public static final int decimalsColumn=3;
        public static final int totalSupplyColumn=4;
        public static final int genesisSupplyColumn =5;
        public static final int creationDateColumn =6;

        // create statement
        public static final String SQL_CREATE =
                CREATE_TABLE_IF_NOT_EXISTS + TABLE_NAME + " (" +
                        _ID + INTEGER_PRIMARY_KEY + NOT_NULL + COMMA_SEP +
                        COLUMN_NAME_NAME + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                        COLUMN_NAME_SYMBOL + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                        COLUMN_NAME_DECIMALS + INTEGER + NOT_NULL + COMMA_SEP +
                        COLUMN_NAME_GENERAL_SUPPLY + INTEGER + NOT_NULL + COMMA_SEP +
                        COLUMN_NAME_GENESIS_SUPPLY + INTEGER + NOT_NULL + COMMA_SEP +
                        COLUMN_NAME_CREATION_DATE + TEXT_TYPE + DEFAULT_CURRENT_TIME  +
                        " );";
        // all column names - handy for querries
        public static String[] allColumns = {_ID, COLUMN_NAME_NAME, COLUMN_NAME_SYMBOL,
                COLUMN_NAME_DECIMALS, COLUMN_NAME_GENERAL_SUPPLY,
                COLUMN_NAME_GENESIS_SUPPLY,  COLUMN_NAME_CREATION_DATE};

        /*
         * Create and destroy index
         */
        public static String CREATE_INDEX_NAME_COLUMN = " CREATE INDEX IF NOT EXISTS "+ INDEX_NAME_NAME_COLUMN +" ON "
                + TABLE_NAME + " ("+COLUMN_NAME_NAME+" ASC )";
        public static String DROP_INDEX_NAME_COLUMN = "DROP INDEX "+ INDEX_NAME_NAME_COLUMN;



    }

    /**
     * Table holding the users balances
     */
    public static abstract class UserAccount implements BaseColumns {

        public static final String TABLE_NAME = "UserAccount";
        public static final String COLUMN_NAME_NAME = "User";
        public static final String COLUMN_NAME_DECIMALS = "Decimals";
        public static final String COLUMN_NAME_EVALBAL ="EvalBal";
        public static final String COLUMN_NAME_TRUSTBAL = "TrustBal";
        public static final String INDEX_NAME_NAME_COLUMN = "nameIndex";


        public static final int idColumn =0;
        public static final int nameColumng=1;
        public static final int decimalColumn=2;
        public static final int evalbalColumn=3;
        public static final int trustbalColumn=4;
        //public static final int genesisSupplyColumn =5;
        //public static final int creationDateColumn =6;

        // create statement
        public static final String SQL_CREATE =
                CREATE_TABLE_IF_NOT_EXISTS + TABLE_NAME + " (" +
                        _ID + INTEGER_PRIMARY_KEY + NOT_NULL + COMMA_SEP +
                        COLUMN_NAME_NAME + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                        COLUMN_NAME_DECIMALS + INTEGER + NOT_NULL + COMMA_SEP +
                        COLUMN_NAME_EVALBAL + INTEGER + NOT_NULL + COMMA_SEP +
                        COLUMN_NAME_TRUSTBAL + INTEGER + NOT_NULL +
                        " );";
        // all column names - handy for querries
        public static String[] allColumns = {_ID, COLUMN_NAME_NAME, COLUMN_NAME_DECIMALS,
                COLUMN_NAME_EVALBAL, COLUMN_NAME_TRUSTBAL };

    }

    /**
     * Table holding the websites
     */
    public static abstract class WebsiteAccount implements BaseColumns {

        public static final String TABLE_NAME = "WebsiteAccount";
        public static final String COLUMN_NAME_NAME = "website";
        public static final String COLUMN_NAME_DECIMALS = "Decimals";
        public static final String COLUMN_NAME_EVALBAL = "EvalBal";
        public static final String COLUMN_NAME_URI = "Uri";
        public static final String COLUMN_NAME_OFFSET = "Offset";
        public static final String COLUMN_NAME_VOTERS = "Voters";
        public static final String INDEX_NAME_NAME_COLUMN = "nameIndex";


        public static final int idColumn =0;
        public static final int nameColumng=1;
        public static final int decimalColumn=2;
        public static final int evalbalColumn=3;
        public static final int uriColumn =4;
        public static final int offsetColumn =5;
        public static final int votersColumn =6;

        // create statement
        public static final String SQL_CREATE =
                CREATE_TABLE_IF_NOT_EXISTS + TABLE_NAME + " (" +
                        _ID + INTEGER_PRIMARY_KEY + NOT_NULL + COMMA_SEP +
                        COLUMN_NAME_NAME + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                        COLUMN_NAME_DECIMALS + INTEGER + NOT_NULL + COMMA_SEP +
                        COLUMN_NAME_EVALBAL + INTEGER + NOT_NULL + COMMA_SEP +
                        COLUMN_NAME_URI + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                        COLUMN_NAME_OFFSET + INTEGER + NOT_NULL + COMMA_SEP +
                        COLUMN_NAME_VOTERS + INTEGER + NOT_NULL +
                        " );";

        // all column names - handy for querries
        public static String[] allColumns = {_ID, COLUMN_NAME_NAME, COLUMN_NAME_DECIMALS,
                COLUMN_NAME_EVALBAL, COLUMN_NAME_OFFSET, COLUMN_NAME_VOTERS };


    }

}
