package learn.basicandroiddev;

/**
 * Created by Jerry on 9/9/2016.
 */
public class PokeObject
{
    public int evHistoryCount = 0;
    public int level = 0;
    public int species = PokeData.SPECIES_NONE;
    public int nature = StatData.NATURE_NONE;
    public int heldItem = 0;
    public int pokerus = 0;
    public int[] IVs = {-3,-3,-3,-3,-3,-3};
    public int[] IVsMin = {-2,-2,-2,-2,-2,-2};
    public int[] IVsMax = {32,32,32,32,32,32};
    public int[] EVs = {0,0,0,0,0,0};
    public int[] stats = {0,0,0,0,0,0};

    public PokeObject()
    {
        evHistoryCount = 0;
        level = 0;
        species = PokeData.SPECIES_NONE;
        nature = StatData.NATURE_NONE;
        heldItem = 0;
        pokerus = 0;
        for(int i = 0; i < StatData.NUMBER_STATS; i++)
        {
            IVs[i] = -3;
            IVsMin[i] = -2;
            IVsMax[i] = 32;
            EVs[i] = -1;
            stats[i] = -1;
        }
    }
}
