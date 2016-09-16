package learn.basicandroiddev;


/**
 * Created by Jerry on 9/9/2016.
 */
public class Calculator
{
    // TODO: better error checking
    public static final int ERROR_LIST_BEGIN = -1000;

    public static final int ERROR_STAT_TOOHIGH = -1001;
    public static final int ERROR_STAT_TOOLOW = -1002;

    public static final int ERROR_IV_TOOHIGH = -1003;
    public static final int ERROR_IV_TOOLOW = -1004;

    public static final int ERROR_EV_TOOHIGH = -1005;
    public static final int ERROR_EV_TOOLOW = -1006;

    public static final int ERROR_LIST_END = -1007;

    public Calculator()
    {
    }

    // sets (pokeObject) IVs, IVsMin and IVsMax
    // based on current stats and ststs gained from level up
    // retains IVsMin/IvsMax that are closer to the correct IV value
    public void calculateIvs(PokeObject pokeObject)
    {
        // control pokes
        // make sure stats are not higher or lower than these
        // TODO: Calculator.calculateIvs() add control pokemon for EVs and IVs
        PokeObject maxStats = new PokeObject();
        PokeObject minStats = new PokeObject();
        {
            maxStats.level = pokeObject.level;
            maxStats.species = pokeObject.species;
            maxStats.nature = pokeObject.nature;

            minStats.level = pokeObject.level;
            minStats.species = pokeObject.species;
            minStats.nature = pokeObject.nature;

            for(int z = 0; z < StatData.NUMBER_STATS; z++)
            {
                maxStats.IVs[z] = 31;
                maxStats.IVsMax[z] = 31;
                maxStats.IVsMin[z] = 31;
                maxStats.EVs[z] = 255;

                minStats.IVs[z] = 0;
                minStats.IVsMax[z] = 0;
                minStats.IVsMin[z] = 0;
                minStats.EVs[z] = 0;
            }
        }

        // create copy with max IV to compare
        PokeObject comparePoke = new PokeObject();
        comparePoke.level = pokeObject.level;
        comparePoke.species = pokeObject.species;
        comparePoke.nature = pokeObject.nature;

        for(int i = StatData.STAT_HP; i < StatData.NUMBER_STATS; i++)
        {
            comparePoke.EVs[i] = pokeObject.EVs[i];
            comparePoke.IVsMax[i] = 32;
            comparePoke.IVsMin[i] = 0;
        }


        // check stats
        for(int i = StatData.STAT_HP; i < StatData.NUMBER_STATS; i++)
        {
            // Brute force method - check IVs

            calculateStat(maxStats, i);
            calculateStat(minStats, i);

            // 31 -> 0 because high values will happen more often
            for (int j = 31; j >= 0; j--)
            {
                comparePoke.IVs[i] = j;
                calculateStat(comparePoke, i);

                // check for max - runs once when stats first match
                if (pokeObject.stats[i] == comparePoke.stats[i] && comparePoke.IVsMax[i] == 32)
                {
                    comparePoke.IVsMax[i] = j;
                }

                // check for min - runs while stats match
                if (pokeObject.stats[i] == comparePoke.stats[i])
                {
                    comparePoke.IVsMin[i] = j;
                }

            } // end check IVs

            // limit IV
            // possible iv range can be calculated lower than 0 or higher than 31
            if(pokeObject.IVsMax[i] > 31)
                pokeObject.IVsMax[i] = 31;
            else if(pokeObject.IVsMin[i] < 0)
                pokeObject.IVsMin[i] = 0;

            // TODO: Calculator() add error checking
            /* // check stat limits
            if(pokeObject.stats[i] > maxStats.stats[i])
                pokeObject.stats[i] = ERROR_STAT_TOOHIGH;
            else if(pokeObject.stats[i] < minStats.stats[i])
                pokeObject.stats[i] = ERROR_STAT_TOOLOW;
             */

            // check if max is closer to true iv than last result
            if (pokeObject.IVsMax[i] > comparePoke.IVsMax[i])
                pokeObject.IVsMax[i] = comparePoke.IVsMax[i];

            // check if min is closer to true iv than last result
            if (pokeObject.IVsMin[i] < comparePoke.IVsMin[i])
                pokeObject.IVsMin[i] = comparePoke.IVsMin[i];

            // set IVs if min and max are equal
            if (pokeObject.IVsMin[i] == pokeObject.IVsMax[i])
                pokeObject.IVs[i] = pokeObject.IVsMin[i];
        } // end check stats

        // Shedenja check
        if (pokeObject.species == 292)
        {
            pokeObject.IVs[StatData.STAT_HP] = 31;
        }
    }

    // returns stat total for (statEnum)
    // for (pokeObject) using baseStats, Nature, Level, EV and IV
    public void calculateStat(PokeObject pokeObject, int statEnum)
    {
        double result;

        final int lv = pokeObject.level;
        final int baseStat = PokeData.baseStats[pokeObject.species][statEnum];
        final int EV = pokeObject.EVs[statEnum];
        final int IV = pokeObject.IVs[statEnum];

        // HP
        if(statEnum == StatData.STAT_HP)
        {
            // check if Shedenja
            if (pokeObject.species == 292)
            {
                result = 1;
            }
            else
            {
                //result = Math.floor(((IV + 2 * baseStat + Math.floor(EV/4))* lv/100) + 10 + lv);
                result = ((IV + 2 * baseStat + (EV/4))* lv/100) + 10 + lv;
            }
        }
        // all other stats
        else
        {
            //result = Math.floor(((IV + 2 * baseStat + Math.floor(EV/4))* lv/100) + 5);
            result = ((IV + 2 * baseStat + (EV/4))* lv/100) + 5;

            // nature modifier - don't do on HP
            if (statEnum == StatData.Modifier[pokeObject.nature][0])
                result = result * 1.1;
            if (statEnum == StatData.Modifier[pokeObject.nature][1])
                result = result * 0.9;
        }

        pokeObject.stats[statEnum] = (int) Math.floor(result);
    }

    // calls calculateStat() for all stats
    public void calculateAllStats(PokeObject pokeObject)
    {
        // other stats
        for(int i = StatData.STAT_HP; i < StatData.NUMBER_STATS; i++)
        {
            calculateStat(pokeObject, i);
        }
    }

}
