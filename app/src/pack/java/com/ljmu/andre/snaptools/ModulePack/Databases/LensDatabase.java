package com.ljmu.andre.snaptools.ModulePack.Databases;

import android.content.Context;
import android.database.sqlite.SQLiteDiskIOException;
import android.database.sqlite.SQLiteException;

import com.ljmu.andre.CBIDatabase.CBIDatabaseCore;
import com.ljmu.andre.CBIDatabase.CBIObject;
import com.ljmu.andre.CBIDatabase.CBITable;
import com.ljmu.andre.snaptools.ModulePack.Databases.Tables.LensAssetObject;
import com.ljmu.andre.snaptools.ModulePack.Databases.Tables.LensObject;
import com.ljmu.andre.snaptools.ModulePack.Utils.Result;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


import timber.log.Timber;

import static com.ljmu.andre.GsonPreferences.Preferences.getPref;
import static com.ljmu.andre.snaptools.ModulePack.Utils.ModulePreferenceDef.LENS_MERGE_ENABLE;
import static com.ljmu.andre.snaptools.Utils.FrameworkPreferencesDef.DATABASES_PATH;
import static com.ljmu.andre.snaptools.Utils.StringEncryptor.decryptMsg;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class LensDatabase {
	public static final String DB_NAME = /*Lenses.db*/ decryptMsg(new byte[]{73, 44, -43, 18, 45, -11, 33, 22, 79, -127, 64, 47, 98, 13, 78, -15});
	private static final int VERSION = 1;
	private static CBIDatabaseCore databaseCore;

	public static CBIDatabaseCore init(Context context) {
		if (databaseCore == null) {
			String dbPath = getPref(DATABASES_PATH);
			databaseCore = new CBIDatabaseCore(context, dbPath + DB_NAME, VERSION);
		}

		return databaseCore;
	}

	public static Result<Boolean, Long> mergeLensDatabases(Context context, List<String> sourceFiles) {
		CBITable<LensObject> lensTable = LensDatabase.getTable(LensObject.class);

		long startSize = lensTable.getRowCount();
		boolean containsFailures = false;

		for (String lensPath : sourceFiles) {
			CBIDatabaseCore mergeSource = LensDatabase.getLensDatabase(context, lensPath);
			if (!mergeLensDatabases(mergeSource))
				containsFailures = true;

			mergeSource.getDatabase().close();
		}

		long endSize = lensTable.getRowCount();

		return new Result<>(!containsFailures, endSize - startSize);
	}

	public static <T extends CBIObject> CBITable<T> getTable(Class<T> cbiClass) {
		CBITable<T> table = databaseCore.getTable(cbiClass);

		if (table == null) {
			Timber.d("Table Didn't Exist");
			table = databaseCore.registerTable(cbiClass);
		} else
			Timber.d("Table existed!");

		return table;
	}

	public static CBIDatabaseCore getLensDatabase(Context context, String dbLocation) {
		return new CBIDatabaseCore(context, dbLocation, VERSION);
	}

	public static boolean mergeLensDatabases(CBIDatabaseCore source) {
		try {
			CBITable<LensObject> currentLensTable = LensDatabase.getTable(LensObject.class);
			CBITable<LensObject> newTable = new CBITable<>(LensObject.class, source);
			newTable.createTable();

			boolean wasSuccessful = true;

			Set<String> headerBlacklist = new HashSet<>();
			headerBlacklist.add(/*isActive*/ decryptMsg(new byte[]{4, -53, -26, -98, -32, 106, -67, 43, -13, 79, 72, 18, -85, 25, -82, -39}));
			headerBlacklist.add(/*favourited*/ decryptMsg(new byte[]{8, -61, -28, 83, -35, -104, -20, 93, 29, -23, -106, 93, -6, 4, 36, -26}));

			for (LensObject lensObject : newTable.getAll()) {
				if (currentLensTable.contains(lensObject.id))
					continue;

				if (getPref(LENS_MERGE_ENABLE))
					lensObject.isActive = true;

				if (!currentLensTable.insert(lensObject, headerBlacklist))
					wasSuccessful = false;
			}

			CBITable<LensAssetObject> currentLensAssetTable = LensDatabase.getTable(LensAssetObject.class);
			CBITable<LensAssetObject> newAssetTable = new CBITable<>(LensAssetObject.class, source);
			newAssetTable.createTable();

			currentLensAssetTable.insertAll(newAssetTable.getAll());

			return wasSuccessful;
		} catch (SQLiteDiskIOException e) {
			Timber.w(e, "Disk IO Exception");
		} catch (SQLiteException e) {
			Timber.w(e, "SQLiteException");
		}

		return false;
	}

	public static boolean insert(CBIObject object) {
		try {
			CBITable table = getTable(object.getClass());
			return table.insert(object);
		} catch (Throwable t) {
			Timber.e(t);
		}

		return false;
	}
}
