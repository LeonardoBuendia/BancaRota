package com.buendiasoftware.bancarota;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import com.buendiasoftware.bancarota.GastosDao;
import com.buendiasoftware.bancarota.Gastostb;


/**
 * Created by leonardo on 5/03/18.
 */
@Database(entities={Gastostb.class}, version=1)
@TypeConverters(DateConverter.class)
public abstract class GastosDB extends RoomDatabase{
        // no era necesario ponerle de nombre getGastosDao
        public abstract GastosDao getGastosDao();

        private static GastosDB gastosdb;

        public static GastosDB getInstance(Context context){

            if(null==gastosdb){
                gastosdb= buildDatabaseIntance(context);
                /*
                * Poblar DB por primera vez
                * */
                    /*
                    Gastostb poncharInit = new Gastostb(1,"Leonardo","08062019","8:01","10:25","10:44","13:05");
                    gastosdb.getGastosDao().insert(poncharInit);
*/
              }
            return gastosdb;
        }



            public static GastosDB buildDatabaseIntance(Context context){
                return Room.databaseBuilder(context,GastosDB.class, "DB_NAME").allowMainThreadQueries().build();
            }

            public void cleanUp(){
                gastosdb=null;
            }
}

