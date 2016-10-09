package learn.basicandroiddev;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by Jerry on 9/15/2016.
 */
public class SaveManager
{
    private final int saveLength = 32768;
    private byte[] saveData = new byte[saveLength];
    private static int intLength = 4;
    // header values are: version number, number of entries, ADDED_EVS[6], evHistory[1024]
    private static int headerLength = 1032;
    private static int entryLength = 36;
    private static int MAX_ENTRIES = 210;

    private static int entryId = 0;
    private static int numEntries = 0;
    private static int currentVersion = 0; // set in main activity onCreate()
    private static int saveVersion = 0;

    private static int entryLengthBytes = entryLength * intLength;
    private static int headerLengthBytes = headerLength * intLength;

    // entry layout enum
    public static final int SAVE_EV_HISTORY_COUNT = 0;
    public static final int SAVE_LEVEL       = 1;
    public static final int SAVE_SPECIES     = 2;
    public static final int SAVE_NATURE      = 3;
    public static final int SAVE_HOLD_ITEM   = 4;
    public static final int SAVE_HAS_POKERUS = 5;
    public static final int SAVE_STAT_HP     = 6;
    public static final int SAVE_STAT_ATK    = 7;
    public static final int SAVE_STAT_DEF    = 8;
    public static final int SAVE_STAT_SPEED  = 9;
    public static final int SAVE_STAT_SPATK  = 10;
    public static final int SAVE_STAT_SPDEF  = 11;
    public static final int SAVE_EV_HP       = 12;
    public static final int SAVE_EV_ATK      = 13;
    public static final int SAVE_EV_DEF      = 14;
    public static final int SAVE_EV_SPEED    = 15;
    public static final int SAVE_EV_SPATK    = 16;
    public static final int SAVE_EV_SPDEF    = 17;
    public static final int SAVE_IV_HP       = 18;
    public static final int SAVE_IV_ATK      = 19;
    public static final int SAVE_IV_DEF      = 20;
    public static final int SAVE_IV_SPEED    = 21;
    public static final int SAVE_IV_SPATK    = 22;
    public static final int SAVE_IV_SPDEF    = 23;
    public static final int SAVE_IVMIN_HP    = 24;
    public static final int SAVE_IVMIN_ATK   = 25;
    public static final int SAVE_IVMIN_DEF   = 26;
    public static final int SAVE_IVMIN_SPEED = 27;
    public static final int SAVE_IVMIN_SPATK = 28;
    public static final int SAVE_IVMIN_SPDEF = 29;
    public static final int SAVE_IVMAX_HP    = 30;
    public static final int SAVE_IVMAX_ATK   = 31;
    public static final int SAVE_IVMAX_DEF   = 32;
    public static final int SAVE_IVMAX_SPEED = 33;
    public static final int SAVE_IVMAX_SPATK = 34;
    public static final int SAVE_IVMAX_SPDEF = 35;

    public int getNumEntries()
    {
        return numEntries;
    }

    public int getCurrentPosition()
    {
        return entryId;
    }

    public int getSaveVersion()
    {
        return saveVersion;
    }

    public SaveManager(int versionNumber)
    {
        currentVersion = versionNumber;

        entryId = 0;
        numEntries = 0;
    }

    public void reset()
    {
        entryId = 0;
        numEntries = 0;

        saveData = new byte[saveLength];
    }


    /** ////////////////////////////////////////////////////////////
     *
     *
     *      SAVE FUNCTIONS
     *
     *
     *
     //////////////////////////////////////////////////////////////*/

    public void saveFile(PokeObject pokeObject)
    {
        String fileName = "testSave.pkm";

        FileEditor.deleteFile(fileName);
        FileEditor.copyFile("saves", "_autoSave.pkm", fileName);

    }

    public void autoSaveFile(PokeObject pokeObject, int[] ADDED_EVS_6, int[] evHistoryData_1024)
    {
        // entry# >> level >> species >> nature >> stats >> evs >> ivs >> ivsmin >> ivsmax
        int[] data = new int[entryLength];

        int i = 0;

        data[i++] = pokeObject.evHistoryCount;
        data[i++] = pokeObject.level;
        data[i++] = pokeObject.species;
        data[i++] = pokeObject.nature;
        data[i++] = pokeObject.heldItem;
        data[i++] = pokeObject.pokerus;

        // TODO: saveManager.autoSaveFile() switch to this for release
        //for( int statEnum = StatData.STAT_HP; statEnum < StatData.NUMBER_STATS; statEnum++)
        //{
        //    data[i++] = pokeObject.stats[statEnum];
        //    data[i++] = pokeObject.EVs[statEnum];
        //    data[i++] = pokeObject.IVs[statEnum];
        //    data[i++] = pokeObject.IVsMin[statEnum];
        //    data[i++] = pokeObject.IVsMax[statEnum];
        //}

        data[i++] = pokeObject.stats[StatData.STAT_HP];
        data[i++] = pokeObject.stats[StatData.STAT_ATK];
        data[i++] = pokeObject.stats[StatData.STAT_DEF];
        data[i++] = pokeObject.stats[StatData.STAT_SPATK];
        data[i++] = pokeObject.stats[StatData.STAT_SPDEF];
        data[i++] = pokeObject.stats[StatData.STAT_SPEED];

        data[i++] = pokeObject.EVs[StatData.STAT_HP];
        data[i++] = pokeObject.EVs[StatData.STAT_ATK];
        data[i++] = pokeObject.EVs[StatData.STAT_DEF];
        data[i++] = pokeObject.EVs[StatData.STAT_SPATK];
        data[i++] = pokeObject.EVs[StatData.STAT_SPDEF];
        data[i++] = pokeObject.EVs[StatData.STAT_SPEED];

        data[i++] = pokeObject.IVs[StatData.STAT_HP];
        data[i++] = pokeObject.IVs[StatData.STAT_ATK];
        data[i++] = pokeObject.IVs[StatData.STAT_DEF];
        data[i++] = pokeObject.IVs[StatData.STAT_SPATK];
        data[i++] = pokeObject.IVs[StatData.STAT_SPDEF];
        data[i++] = pokeObject.IVs[StatData.STAT_SPEED];

        data[i++] = pokeObject.IVsMin[StatData.STAT_HP];
        data[i++] = pokeObject.IVsMin[StatData.STAT_ATK];
        data[i++] = pokeObject.IVsMin[StatData.STAT_DEF];
        data[i++] = pokeObject.IVsMin[StatData.STAT_SPATK];
        data[i++] = pokeObject.IVsMin[StatData.STAT_SPDEF];
        data[i++] = pokeObject.IVsMin[StatData.STAT_SPEED];

        data[i++] = pokeObject.IVsMax[StatData.STAT_HP];
        data[i++] = pokeObject.IVsMax[StatData.STAT_ATK];
        data[i++] = pokeObject.IVsMax[StatData.STAT_DEF];
        data[i++] = pokeObject.IVsMax[StatData.STAT_SPATK];
        data[i++] = pokeObject.IVsMax[StatData.STAT_SPDEF];
        data[i++] = pokeObject.IVsMax[StatData.STAT_SPEED];

        entryId++;
        numEntries = entryId;

        int b = 0;
        // add version info and numEntries
        // version info
        saveData[b++] = (byte) (currentVersion >> 24);
        saveData[b++] = (byte) (currentVersion >> 16);
        saveData[b++] = (byte) (currentVersion >> 8);
        saveData[b++] = (byte) (currentVersion);

        // numEntries
        saveData[b++] = (byte) (numEntries >> 24);
        saveData[b++] = (byte) (numEntries >> 16);
        saveData[b++] = (byte) (numEntries >> 8);
        saveData[b++] = (byte) (numEntries);

        // add ADDED_EVS[6]
        for(int j = 0; j < 6; j++)
        {
            saveData[b++] = (byte) (ADDED_EVS_6[j] >> 24);
            saveData[b++] = (byte) (ADDED_EVS_6[j] >> 16);
            saveData[b++] = (byte) (ADDED_EVS_6[j] >> 8);
            saveData[b++] = (byte) (ADDED_EVS_6[j]);
        }

        // add evHistoryData[6]
        for(int j = 0; j < MainActivity.evHistoryLength; j++)
        {
            saveData[b++] = (byte) (evHistoryData_1024[j] >> 24);
            saveData[b++] = (byte) (evHistoryData_1024[j] >> 16);
            saveData[b++] = (byte) (evHistoryData_1024[j] >> 8);
            saveData[b++] = (byte) (evHistoryData_1024[j]);
        }


        // get offset to start pokeObject entries
        int offset = headerLengthBytes + entryId * entryLengthBytes;

        // add pokedata
        for(int j = 0; j < entryLength; j++)
        {
            saveData[    offset + j * intLength] = (byte) (data[j] >> 24);
            saveData[1 + offset + j * intLength] = (byte) (data[j] >> 16);
            saveData[2 + offset + j * intLength] = (byte) (data[j] >> 8);
            saveData[3 + offset + j * intLength] = (byte) (data[j]);
        }


        String fileName = "_autoSave.pkm";
        FileEditor.writeFile("saves", fileName, saveData);

    }


    /** ////////////////////////////////////////////////////////////
     *
     *
     *      LOAD FUNCTIONS
     *
     *
     *
     //////////////////////////////////////////////////////////////*/


    public boolean loadFileToMemory(String fileName)
    {
        saveData = FileEditor.readBytesFromFile("saves", fileName, saveLength);

        // get version and numEntries
        ByteBuffer bb = ByteBuffer.allocate(intLength * 2);
        bb.order(ByteOrder.BIG_ENDIAN);

        // version
        bb.put(saveData[0]);
        bb.put(saveData[1]);
        bb.put(saveData[2]);
        bb.put(saveData[3]);

        // entries
        bb.put(saveData[4]);
        bb.put(saveData[5]);
        bb.put(saveData[6]);
        bb.put(saveData[7]);

        saveVersion = bb.getInt(0);
        numEntries = bb.getInt(4);

        return true;
    }

    public boolean loadPokeFromMemory(PokeObject pokeObject, int position)
    {
        // entry# >> level >> species >> nature >> stats >> evs >> ivs >> ivsmin >> ivsmax
        byte[] bytes = new byte[entryLengthBytes];

        // get offset
        int pos = headerLengthBytes + position * entryLengthBytes;
        if (pos >= saveLength)
            return false;

        System.out.println("loadPokeDataFromMemory(..., int position) pos = " + pos);

        System.arraycopy(saveData, pos, bytes, 0, entryLengthBytes);

        int[] data = new int[entryLength];

        // copy data to output
        for (int i = 0; i < entryLength; i++ )
        {
            ByteBuffer bb = ByteBuffer.allocate(intLength);
            bb.order(ByteOrder.BIG_ENDIAN);
            bb.put(bytes[    i * intLength]);
            bb.put(bytes[1 + i * intLength]);
            bb.put(bytes[2 + i * intLength]);
            bb.put(bytes[3 + i * intLength]);
            data[i] = bb.getInt(0);
            System.out.println("loadPokeDataFromMemory() data[" + i + "] = " + data[i]);
        }

        int k = 0;
        pokeObject.evHistoryCount = data[k++];
        pokeObject.level = data[k++];
        pokeObject.species = data[k++];
        pokeObject.nature = data[k++];
        pokeObject.heldItem = data[k++];
        pokeObject.pokerus = data[k++];

        //// TODO: saveManager.autoSaveFile() switch to this for release
        //for( int statEnum = StatData.STAT_HP; statEnum < StatData.NUMBER_STATS; statEnum++)
        //{
        //    pokeObject.stats[statEnum] = data[k++];
        //    pokeObject.EVs[statEnum] = data[k++];
        //    pokeObject.IVs[statEnum] = data[k++];
        //    pokeObject.IVsMin[statEnum] = data[k++];
        //    pokeObject.IVsMax[statEnum] = data[k++];
        //}

        pokeObject.stats[StatData.STAT_HP] = data[k++];
        pokeObject.stats[StatData.STAT_ATK] = data[k++];
        pokeObject.stats[StatData.STAT_DEF] = data[k++];
        pokeObject.stats[StatData.STAT_SPATK] = data[k++];
        pokeObject.stats[StatData.STAT_SPDEF] = data[k++];
        pokeObject.stats[StatData.STAT_SPEED] = data[k++];

        pokeObject.EVs[StatData.STAT_HP] = data[k++];
        pokeObject.EVs[StatData.STAT_ATK] = data[k++];
        pokeObject.EVs[StatData.STAT_DEF] = data[k++];
        pokeObject.EVs[StatData.STAT_SPATK] = data[k++];
        pokeObject.EVs[StatData.STAT_SPDEF] = data[k++];
        pokeObject.EVs[StatData.STAT_SPEED] = data[k++];

        pokeObject.IVs[StatData.STAT_HP] = data[k++];
        pokeObject.IVs[StatData.STAT_ATK] = data[k++];
        pokeObject.IVs[StatData.STAT_DEF] = data[k++];
        pokeObject.IVs[StatData.STAT_SPATK] = data[k++];
        pokeObject.IVs[StatData.STAT_SPDEF] = data[k++];
        pokeObject.IVs[StatData.STAT_SPEED] = data[k++];

        pokeObject.IVsMin[StatData.STAT_HP] = data[k++];
        pokeObject.IVsMin[StatData.STAT_ATK] = data[k++];
        pokeObject.IVsMin[StatData.STAT_DEF] = data[k++];
        pokeObject.IVsMin[StatData.STAT_SPATK] = data[k++];
        pokeObject.IVsMin[StatData.STAT_SPDEF] = data[k++];
        pokeObject.IVsMin[StatData.STAT_SPEED] = data[k++];

        pokeObject.IVsMax[StatData.STAT_HP] = data[k++];
        pokeObject.IVsMax[StatData.STAT_ATK] = data[k++];
        pokeObject.IVsMax[StatData.STAT_DEF] = data[k++];
        pokeObject.IVsMax[StatData.STAT_SPATK] = data[k++];
        pokeObject.IVsMax[StatData.STAT_SPDEF] = data[k++];
        pokeObject.IVsMax[StatData.STAT_SPEED] = data[k++];

        entryId = position;

        return true;
    }


    public int getIntFromMemory(int entryPosition, int entryValue)
    {
        int output = 0;
        // entry# >> level >> species >> nature >> stats >> evs >> ivs >> ivsmin >> ivsmax
        byte[] bytes = new byte[intLength];

        final int pos = headerLengthBytes + entryPosition * entryLengthBytes + entryValue * intLength;

        System.arraycopy(saveData, pos, bytes, 0, intLength);

        // copy data to output
        ByteBuffer bb = ByteBuffer.allocate(intLength);
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.put(bytes[0]);
        bb.put(bytes[1]);
        bb.put(bytes[2]);
        bb.put(bytes[3]);
        output = bb.getInt(0);
        //System.out.println("getIntFromMemory() output = " + output);

        return output;
    }
}
