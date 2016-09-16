package learn.basicandroiddev;

/**
 * Created by Jerry on 9/9/2016.
 */
public class PokeObject
{
    public int level = 0;
    public int species = 0;
    public int nature = StatData.NATURE_NONE;
    public int code = 0;
    public int[] IVs = {-3,-3,-3,-3,-3,-3};
    public int[] IVsMin = {-2,-2,-2,-2,-2,-2};
    public int[] IVsMax = {32,32,32,32,32,32};
    public int[] EVs = {0,0,0,0,0,0};
    public int[] stats = {0,0,0,0,0,0};

    public PokeObject()
    {
        level = 0;
        species = PokeData.SPECIES_NONE;
        nature = StatData.NATURE_NONE;
        code = 0;
        for(int i = 0; i < StatData.NUMBER_STATS; i++)
        {
            IVs[i] = -3;
            IVsMin[i] = -2;
            IVsMax[i] = 32;
            EVs[i] = 0;
            stats[i] = 0;
        }
    }
}
