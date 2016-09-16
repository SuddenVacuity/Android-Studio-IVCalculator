package learn.basicandroiddev;

import java.io.File;

/**
 * Created by Jerry on 9/15/2016.
 */
public class SaveManager
{
    private static int entryId = 0;
    private static int numEntries = 0;

    public static int getNumEntries()
    {
        return numEntries;
    }

    public SaveManager()
    {
        entryId = 0;
        numEntries = 0;
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
        // entryId >> level >> species >> nature >> stats >> evs >> ivs/min/max
        int entryLength = 34;
        int[] data = new int[entryLength];

        int i = 0;

        // 0 - 3
        data[i++] = pokeObject.level;
        data[i++] = pokeObject.species;
        data[i++] = pokeObject.nature;

        // 3 - 9
        data[i++] = pokeObject.stats[0];
        data[i++] = pokeObject.stats[1];
        data[i++] = pokeObject.stats[2];
        data[i++] = pokeObject.stats[3];
        data[i++] = pokeObject.stats[4];
        data[i++] = pokeObject.stats[5];

        // 9 - 15
        data[i++] = pokeObject.EVs[0];
        data[i++] = pokeObject.EVs[1];
        data[i++] = pokeObject.EVs[2];
        data[i++] = pokeObject.EVs[3];
        data[i++] = pokeObject.EVs[4];
        data[i++] = pokeObject.EVs[5];

        // 15 - 21
        data[i++] = pokeObject.IVs[0];
        data[i++] = pokeObject.IVs[1];
        data[i++] = pokeObject.IVs[2];
        data[i++] = pokeObject.IVs[3];
        data[i++] = pokeObject.IVs[4];
        data[i++] = pokeObject.IVs[5];

        // 21 - 27
        data[i++] = pokeObject.IVsMin[0];
        data[i++] = pokeObject.IVsMin[1];
        data[i++] = pokeObject.IVsMin[2];
        data[i++] = pokeObject.IVsMin[3];
        data[i++] = pokeObject.IVsMin[4];
        data[i++] = pokeObject.IVsMin[5];

        // 27 - 34
        data[i++] = pokeObject.IVsMax[0];
        data[i++] = pokeObject.IVsMax[1];
        data[i++] = pokeObject.IVsMax[2];
        data[i++] = pokeObject.IVsMax[3];
        data[i++] = pokeObject.IVsMax[4];
        data[i++] = pokeObject.IVsMax[5];
        data[i++] = entryId;

        String fileName = "_autoSave.pkm";

        // first value is number of pokemon entries

        for(int k = 0; k < i; k++)
        {
            FileEditor.writeFile("saves", fileName, data[k]);
        }

        entryId++;
        numEntries = entryId;
    }


    /** ////////////////////////////////////////////////////////////
     *
     *
     *      LOAD FUNCTIONS
     *
     *
     *
     //////////////////////////////////////////////////////////////*/


    public boolean loadPokeFromFile(String fileName, PokeObject pokeObject, int position)
    {
        // entry# >> level >> species >> nature >> stats >> evs >> ivs/min/max

        int entryLength = 34;
        int[] data = new int[entryLength];

        // get number of the last entry
        // fileLength(bytes) / (entryLegth*sizeOfInt) - last entry
        int pos = (int)(FileEditor.getFileLength("saves", fileName) / (entryLength * 4) - 1);

        if(position < pos)
           pos = position;

        // add 1 to positions to account for numEntries first value
        // get data for each pokemon
        int startPosition = pos * entryLength;
        int endPosition = (pos + 1) * entryLength;
        for(int i = startPosition, j = 0; i < endPosition; i++, j++)
        {
            // call each int individually
            // TODO: load from files in larger blocks
            data[j] = FileEditor.readIntFromFile("saves", fileName, i);
        }

        if(data[0] == -1)
            return false;

        int k = 0;
        // 0 - 3
        pokeObject.level = data[k++];
        pokeObject.species = data[k++];
        pokeObject.nature = data[k++];

        // 4 - 9
        pokeObject.stats[0] = data[k++];
        pokeObject.stats[1] = data[k++];
        pokeObject.stats[2] = data[k++];
        pokeObject.stats[3] = data[k++];
        pokeObject.stats[4] = data[k++];
        pokeObject.stats[5] = data[k++];

        // 10 - 15
        pokeObject.EVs[0] = data[k++];
        pokeObject.EVs[1] = data[k++];
        pokeObject.EVs[2] = data[k++];
        pokeObject.EVs[3] = data[k++];
        pokeObject.EVs[4] = data[k++];
        pokeObject.EVs[5] = data[k++];

        // 16 - 21
        pokeObject.IVs[0] = data[k++];
        pokeObject.IVs[1] = data[k++];
        pokeObject.IVs[2] = data[k++];
        pokeObject.IVs[3] = data[k++];
        pokeObject.IVs[4] = data[k++];
        pokeObject.IVs[5] = data[k++];

        // 22 - 27
        pokeObject.IVsMin[0] = data[k++];
        pokeObject.IVsMin[1] = data[k++];
        pokeObject.IVsMin[2] = data[k++];
        pokeObject.IVsMin[3] = data[k++];
        pokeObject.IVsMin[4] = data[k++];
        pokeObject.IVsMin[5] = data[k++];

        // 27 - 34
        pokeObject.IVsMax[0] = data[k++];
        pokeObject.IVsMax[1] = data[k++];
        pokeObject.IVsMax[2] = data[k++];
        pokeObject.IVsMax[3] = data[k++];
        pokeObject.IVsMax[4] = data[k++];
        pokeObject.IVsMax[5] = data[k++];
        entryId = data[k++];

        return true;
    }

    public boolean loadPokeFromFile(String fileName, PokeObject pokeObject)
    {
        // entry# >> level >> species >> nature >> stats >> evs >> ivs/min/max

        int entryLength = 34;
        int[] data = new int[entryLength];

        // get number of the last entry
        // fileLenth(bytes) / (entryLegth*sizeOfInt) - last entry
        int pos = (int)(FileEditor.getFileLength("saves", fileName) / (entryLength * 4) - 1);

        System.out.println("loadPokeFromFile(..., int data) pos = " + pos);

        if(pos == -1)
            return false;

        // add 1 to positions to account for numEntries first value
        // get data for each pokemon
        int startPosition = pos * entryLength;
        int endPosition = (pos + 1) * entryLength;
        for(int i = startPosition, j = 0; i < endPosition; i++, j++)
        {
            // call each int individually
            // TODO: load form files in larger blocks
            data[j] = FileEditor.readIntFromFile("saves", fileName, i);
        }

        if(data[0] == -1)
            return false;

        int k = 0;
        // 0 - 4
        pokeObject.level = data[k++];
        pokeObject.species = data[k++];
        pokeObject.nature = data[k++];

        // 5 - 10
        pokeObject.stats[0] = data[k++];
        pokeObject.stats[1] = data[k++];
        pokeObject.stats[2] = data[k++];
        pokeObject.stats[3] = data[k++];
        pokeObject.stats[4] = data[k++];
        pokeObject.stats[5] = data[k++];

        // 11 - 16
        pokeObject.EVs[0] = data[k++];
        pokeObject.EVs[1] = data[k++];
        pokeObject.EVs[2] = data[k++];
        pokeObject.EVs[3] = data[k++];
        pokeObject.EVs[4] = data[k++];
        pokeObject.EVs[5] = data[k++];

        // 17 - 22
        pokeObject.IVs[0] = data[k++];
        pokeObject.IVs[1] = data[k++];
        pokeObject.IVs[2] = data[k++];
        pokeObject.IVs[3] = data[k++];
        pokeObject.IVs[4] = data[k++];
        pokeObject.IVs[5] = data[k++];

        // 23 - 28
        pokeObject.IVsMin[0] = data[k++];
        pokeObject.IVsMin[1] = data[k++];
        pokeObject.IVsMin[2] = data[k++];
        pokeObject.IVsMin[3] = data[k++];
        pokeObject.IVsMin[4] = data[k++];
        pokeObject.IVsMin[5] = data[k++];

        // 29 - 34
        pokeObject.IVsMax[0] = data[k++];
        pokeObject.IVsMax[1] = data[k++];
        pokeObject.IVsMax[2] = data[k++];
        pokeObject.IVsMax[3] = data[k++];
        pokeObject.IVsMax[4] = data[k++];
        pokeObject.IVsMax[5] = data[k++];
        entryId = data[k++];

        return true;
    }
}
