package com.example.storeapplication.abstraction;

import com.orm.SugarRecord;

import java.util.List;

/**
 * All entities which get persisted in the database should inherit from this class.
 * This class also provides utility methods for working with SugarORM.
 *
 * @param <T>
 */
public abstract class AbstractDataModel<T> extends SugarRecord {

    /**
     * Saves a list of items.
     *
     * @param list The list of items to save.
     */
    public static <S extends AbstractDataModel<S>> void saveList(List<S> list) {
        SugarRecord.saveInTx(list);
    }
}
