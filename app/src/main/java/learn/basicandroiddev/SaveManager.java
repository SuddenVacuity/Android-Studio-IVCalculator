package learn.basicandroiddev;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by Jerry on 9/15/2016.
 */
public class SaveManager
{
    private final int saveLength = 16384;
    private byte[] saveData = new byte[saveLength];

    private static int entryId = 0;
    private static int numEntries = 0;
    private static int currentVersion = 0;
    private static int saveVersion = 0;

    private static int intLength = 4;
    private static int headerLength = 2;
    private static int entryLength = 33;

    private static int entryLengthBytes = entryLength * intLength;
    private static int headerLengthBytes = headerLength * intLength;

    // entry layout enum
    public static final int SAVE_LEVEL = 0;
    public static final int SAVE_SPECIES = SAVE_LEVEL + 1;
    public static final int SAVE_NATURE = SAVE_SPECIES + 1;
    public static final int SAVE_STAT_HP = SAVE_NATURE + 1;
    public static final int SAVE_STAT_ATK = SAVE_STAT_HP + 1;
    public static final int SAVE_STAT_DEF = SAVE_STAT_ATK + 1;
    public static final int SAVE_STAT_SPATK = SAVE_STAT_DEF + 1;
    public static final int SAVE_STAT_SPDEF = SAVE_STAT_SPATK + 1;
    public static final int SAVE_STAT_SPEED = SAVE_STAT_SPDEF + 1;
    public static final int SAVE_EV_HP = SAVE_STAT_SPEED + 1;
    public static final int SAVE_EV_ATK = SAVE_EV_HP + 1;
    public static final int SAVE_EV_DEF = SAVE_EV_ATK + 1;
    public static final int SAVE_EV_SPATK = SAVE_EV_DEF + 1;
    public static final int SAVE_EV_SPDEF = SAVE_EV_SPATK + 1;
    public static final int SAVE_EV_SPEED = SAVE_EV_SPDEF + 1;
    public static final int SAVE_IV_HP = SAVE_EV_SPEED + 1;
    public static final int SAVE_IV_ATK = SAVE_IV_HP + 1;
    public static final int SAVE_IV_DEF = SAVE_IV_ATK + 1;
    public static final int SAVE_IV_SPATK = SAVE_IV_DEF + 1;
    public static final int SAVE_IV_SPDEF = SAVE_IV_SPATK + 1;
    public static final int SAVE_IV_SPEED = SAVE_IV_SPDEF + 1;
    public static final int SAVE_IVMIN_HP = SAVE_IV_SPEED + 1;
    public static final int SAVE_IVMIN_ATK = SAVE_IVMIN_HP + 1;
    public static final int SAVE_IVMIN_DEF = SAVE_IVMIN_ATK + 1;
    public static final int SAVE_IVMIN_SPATK = SAVE_IVMIN_DEF + 1;
    public static final int SAVE_IVMIN_SPDEF = SAVE_IVMIN_SPATK + 1;
    public static final int SAVE_IVMIN_SPEED = SAVE_IVMIN_SPDEF + 1;
    public static final int SAVE_IVMAX_HP = SAVE_IVMIN_SPEED + 1;
    public static final int SAVE_IVMAX_ATK = SAVE_IVMAX_HP + 1;
    public static final int SAVE_IVMAX_DEF = SAVE_IVMAX_ATK + 1;
    public static final int SAVE_IVMAX_SPATK = SAVE_IVMAX_DEF + 1;
    public static final int SAVE_IVMAX_SPDEF = SAVE_IVMAX_SPATK + 1;
    public static final int SAVE_IVMAX_SPEED = SAVE_IVMAX_SPDEF + 1;

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

    public void autoSaveFile(PokeObject pokeObject)
    {
        // entry# >> level >> species >> nature >> stats >> evs >> ivs >> ivsmin >> ivsmax
        int[] data = new int[entryLength];

        int i = 0;

        data[i++] = pokeObject.level;
        data[i++] = pokeObject.species;
        data[i++] = pokeObject.nature;

        data[i++] = pokeObject.stats[0];
        data[i++] = pokeObject.stats[1];
        data[i++] = pokeObject.stats[2];
        data[i++] = pokeObject.stats[3];
        data[i++] = pokeObject.stats[4];
        data[i++] = pokeObject.stats[5];

        data[i++] = pokeObject.EVs[0];
        data[i++] = pokeObject.EVs[1];
        data[i++] = pokeObject.EVs[2];
        data[i++] = pokeObject.EVs[3];
        data[i++] = pokeObject.EVs[4];
        data[i++] = pokeObject.EVs[5];

        data[i++] = pokeObject.IVs[0];
        data[i++] = pokeObject.IVs[1];
        data[i++] = pokeObject.IVs[2];
        data[i++] = pokeObject.IVs[3];
        data[i++] = pokeObject.IVs[4];
        data[i++] = pokeObject.IVs[5];

        data[i++] = pokeObject.IVsMin[0];
        data[i++] = pokeObject.IVsMin[1];
        data[i++] = pokeObject.IVsMin[2];
        data[i++] = pokeObject.IVsMin[3];
        data[i++] = pokeObject.IVsMin[4];
        data[i++] = pokeObject.IVsMin[5];

        data[i++] = pokeObject.IVsMax[0];
        data[i++] = pokeObject.IVsMax[1];
        data[i++] = pokeObject.IVsMax[2];
        data[i++] = pokeObject.IVsMax[3];
        data[i++] = pokeObject.IVsMax[4];
        data[i++] = pokeObject.IVsMax[5];

        entryId++;
        numEntries = entryId;


        // add version info and numEntries
        for(int j = 0; j < entryLength; j++)
        {
            // version info
            saveData[0] = (byte) (currentVersion >> 24);
            saveData[1] = (byte) (currentVersion >> 16);
            saveData[2] = (byte) (currentVersion >> 8);
            saveData[3] = (byte) (currentVersion);

            // numEntries
            saveData[4] = (byte) (numEntries >> 24);
            saveData[5] = (byte) (numEntries >> 16);
            saveData[6] = (byte) (numEntries >> 8);
            saveData[7] = (byte) (numEntries);
        }

        // get offset
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
            //System.out.println("loadPokeDataFromMemory() data[" + i + "] = " + data[i]);
        }

        int k = 0;
        pokeObject.level = data[k++];
        pokeObject.species = data[k++];
        pokeObject.nature = data[k++];

        pokeObject.stats[0] = data[k++];
        pokeObject.stats[1] = data[k++];
        pokeObject.stats[2] = data[k++];
        pokeObject.stats[3] = data[k++];
        pokeObject.stats[4] = data[k++];
        pokeObject.stats[5] = data[k++];

        pokeObject.EVs[0] = data[k++];
        pokeObject.EVs[1] = data[k++];
        pokeObject.EVs[2] = data[k++];
        pokeObject.EVs[3] = data[k++];
        pokeObject.EVs[4] = data[k++];
        pokeObject.EVs[5] = data[k++];

        pokeObject.IVs[0] = data[k++];
        pokeObject.IVs[1] = data[k++];
        pokeObject.IVs[2] = data[k++];
        pokeObject.IVs[3] = data[k++];
        pokeObject.IVs[4] = data[k++];
        pokeObject.IVs[5] = data[k++];

        pokeObject.IVsMin[0] = data[k++];
        pokeObject.IVsMin[1] = data[k++];
        pokeObject.IVsMin[2] = data[k++];
        pokeObject.IVsMin[3] = data[k++];
        pokeObject.IVsMin[4] = data[k++];
        pokeObject.IVsMin[5] = data[k++];

        pokeObject.IVsMax[0] = data[k++];
        pokeObject.IVsMax[1] = data[k++];
        pokeObject.IVsMax[2] = data[k++];
        pokeObject.IVsMax[3] = data[k++];
        pokeObject.IVsMax[4] = data[k++];
        pokeObject.IVsMax[5] = data[k++];

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
