package io.realm;


import android.util.JsonReader;
import android.util.JsonToken;
import com.example.lucasabadie.projetandroidtp.Score;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.ColumnType;
import io.realm.internal.ImplicitTransaction;
import io.realm.internal.LinkView;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Table;
import io.realm.internal.TableOrView;
import io.realm.internal.android.JsonUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ScoreRealmProxy extends Score
    implements RealmObjectProxy {

    static final class ScoreColumnInfo extends ColumnInfo {

        public final long dateIndex;
        public final long scoreIndex;

        ScoreColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(2);
            this.dateIndex = getValidColumnIndex(path, table, "Score", "date");
            indicesMap.put("date", this.dateIndex);

            this.scoreIndex = getValidColumnIndex(path, table, "Score", "score");
            indicesMap.put("score", this.scoreIndex);

            setIndicesMap(indicesMap);
        }
    }

    private final ScoreColumnInfo columnInfo;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("date");
        fieldNames.add("score");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    ScoreRealmProxy(ColumnInfo columnInfo) {
        this.columnInfo = (ScoreColumnInfo) columnInfo;
    }

    @Override
    @SuppressWarnings("cast")
    public Date getDate() {
        realm.checkIfValid();
        if (row.isNull(columnInfo.dateIndex)) {
            return null;
        }
        return (java.util.Date) row.getDate(columnInfo.dateIndex);
    }

    @Override
    public void setDate(Date value) {
        realm.checkIfValid();
        if (value == null) {
            row.setNull(columnInfo.dateIndex);
            return;
        }
        row.setDate(columnInfo.dateIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public double getScore() {
        realm.checkIfValid();
        return (double) row.getDouble(columnInfo.scoreIndex);
    }

    @Override
    public void setScore(double value) {
        realm.checkIfValid();
        row.setDouble(columnInfo.scoreIndex, value);
    }

    public static Table initTable(ImplicitTransaction transaction) {
        if (!transaction.hasTable("class_Score")) {
            Table table = transaction.getTable("class_Score");
            table.addColumn(ColumnType.DATE, "date", Table.NULLABLE);
            table.addColumn(ColumnType.DOUBLE, "score", Table.NOT_NULLABLE);
            table.setPrimaryKey("");
            return table;
        }
        return transaction.getTable("class_Score");
    }

    public static ScoreColumnInfo validateTable(ImplicitTransaction transaction) {
        if (transaction.hasTable("class_Score")) {
            Table table = transaction.getTable("class_Score");
            if (table.getColumnCount() != 2) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field count does not match - expected 2 but was " + table.getColumnCount());
            }
            Map<String, ColumnType> columnTypes = new HashMap<String, ColumnType>();
            for (long i = 0; i < 2; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            final ScoreColumnInfo columnInfo = new ScoreColumnInfo(transaction.getPath(), table);

            if (!columnTypes.containsKey("date")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'date' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("date") != ColumnType.DATE) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'Date' for field 'date' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.dateIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'date' is required. Either set @Required to field 'date' or migrate using io.realm.internal.Table.convertColumnToNullable().");
            }
            if (!columnTypes.containsKey("score")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'score' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("score") != ColumnType.DOUBLE) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'double' for field 'score' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.scoreIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'score' does support null values in the existing Realm file. Use corresponding boxed type for field 'score' or migrate using io.realm.internal.Table.convertColumnToNotNullable().");
            }
            return columnInfo;
        } else {
            throw new RealmMigrationNeededException(transaction.getPath(), "The Score class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_Score";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static Score createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        Score obj = realm.createObject(Score.class);
        if (json.has("date")) {
            if (json.isNull("date")) {
                obj.setDate(null);
            } else {
                Object timestamp = json.get("date");
                if (timestamp instanceof String) {
                    obj.setDate(JsonUtils.stringToDate((String) timestamp));
                } else {
                    obj.setDate(new Date(json.getLong("date")));
                }
            }
        }
        if (json.has("score")) {
            if (json.isNull("score")) {
                throw new IllegalArgumentException("Trying to set non-nullable field score to null.");
            } else {
                obj.setScore((double) json.getDouble("score"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    public static Score createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        Score obj = realm.createObject(Score.class);
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("date")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setDate(null);
                } else if (reader.peek() == JsonToken.NUMBER) {
                    long timestamp = reader.nextLong();
                    if (timestamp > -1) {
                        obj.setDate(new Date(timestamp));
                    }
                } else {
                    obj.setDate(JsonUtils.stringToDate(reader.nextString()));
                }
            } else if (name.equals("score")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field score to null.");
                } else {
                    obj.setScore((double) reader.nextDouble());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return obj;
    }

    public static Score copyOrUpdate(Realm realm, Score object, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        if (object.realm != null && object.realm.getPath().equals(realm.getPath())) {
            return object;
        }
        return copy(realm, object, update, cache);
    }

    public static Score copy(Realm realm, Score newObject, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        Score realmObject = realm.createObject(Score.class);
        cache.put(newObject, (RealmObjectProxy) realmObject);
        realmObject.setDate(newObject.getDate());
        realmObject.setScore(newObject.getScore());
        return realmObject;
    }

    @Override
    public String toString() {
        if (!isValid()) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Score = [");
        stringBuilder.append("{date:");
        stringBuilder.append(getDate() != null ? getDate() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{score:");
        stringBuilder.append(getScore());
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        String realmName = realm.getPath();
        String tableName = row.getTable().getName();
        long rowIndex = row.getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoreRealmProxy aScore = (ScoreRealmProxy)o;

        String path = realm.getPath();
        String otherPath = aScore.realm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;;

        String tableName = row.getTable().getName();
        String otherTableName = aScore.row.getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (row.getIndex() != aScore.row.getIndex()) return false;

        return true;
    }

}
