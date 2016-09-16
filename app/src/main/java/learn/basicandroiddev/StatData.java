package learn.basicandroiddev;

/**
 * Created by Jerry on 9/6/2016.
 */
public class StatData
{
    public static final String NATURE_OUTPUT_DEFAULT = "Select Nature";

    public static final int NUMBER_NATURES = 25;
    public static final int NUMBER_STATS = 6;

    public static final int NATURE_NONE        = -1;
    public static final int NATURE_ADAMANT     = 0;
    public static final int NATURE_BASHFUL     = 1;
    public static final int NATURE_BOLD        = 2;
    public static final int NATURE_BRAVE       = 3;
    public static final int NATURE_CALM        = 4;
    public static final int NATURE_CAREFUL     = 5;
    public static final int NATURE_DOCILE      = 6;
    public static final int NATURE_GENTLE      = 7;
    public static final int NATURE_HARDY       = 8;
    public static final int NATURE_HASTY       = 9;
    public static final int NATURE_IMPISH      = 10;
    public static final int NATURE_JOLLY       = 11;
    public static final int NATURE_LAX         = 12;
    public static final int NATURE_LONELY      = 13;
    public static final int NATURE_MILD        = 14;
    public static final int NATURE_MODEST      = 15;
    public static final int NATURE_NAIVE       = 16;
    public static final int NATURE_NAUGHTY     = 17;
    public static final int NATURE_QUIET       = 18;
    public static final int NATURE_QUIRKY      = 19;
    public static final int NATURE_RASH        = 20;
    public static final int NATURE_RELAXED     = 21;
    public static final int NATURE_SASSY       = 22;
    public static final int NATURE_SERIOUS     = 23;
    public static final int NATURE_TIMID       = 24;


    public static final int STAT_HP = 0;
    public static final int STAT_ATK = 1;
    public static final int STAT_DEF = 2;
    public static final int STAT_SPATK = 3;
    public static final int STAT_SPDEF = 4;
    public static final int STAT_SPEED = 5;

    public static final String[] STAT_BACKGROUND =
            {
                    "#ffffff",
                    "#ff0000",
                    "#454585",
                    "#7f007f",
                    "#0000ff",
                    "#ffff00"
            };

    public static final String[] StatName =
            {
                    "HP",
                    "Attack",
                    "Defense",
                    "Sp Attack",
                    "Sp Defense",
                    "Speed"
            };

    public static final String[] NatureName =
            {
                    "Adamant",
                    "Bashful",
                    "Bold",
                    "Brave",
                    "Calm",
                    "Careful",
                    "Docile",
                    "Gentle",
                    "Hardy",
                    "Hasty",
                    "Impish",
                    "Jolly",
                    "Lax",
                    "Lonely",
                    "Mild",
                    "Modest",
                    "Naive",
                    "Naughty",
                    "Quiet",
                    "Quirky",
                    "Rash",
                    "Relaxed",
                    "Sassy",
                    "Serious",
                    "Timid",
            };

    // Modifier[i][0] is increase value
    // Modifier[i][1] is decrease value
    public static final int[][] Modifier =
            {
                    {STAT_ATK,   STAT_SPATK},
                    {0,  0},
                    {STAT_DEF,   STAT_ATK},
                    {STAT_ATK,   STAT_SPEED},
                    {STAT_SPDEF, STAT_ATK},
                    {STAT_SPDEF, STAT_SPATK},
                    {0,  0},
                    {STAT_SPDEF, STAT_DEF},
                    {0,  0},
                    {STAT_SPEED, STAT_DEF},
                    {STAT_DEF,   STAT_SPATK},
                    {STAT_SPEED, STAT_SPATK},
                    {STAT_DEF,   STAT_SPDEF},
                    {STAT_ATK,   STAT_DEF},
                    {STAT_SPATK, STAT_DEF},
                    {STAT_SPATK, STAT_ATK},
                    {STAT_SPEED, STAT_SPDEF},
                    {STAT_ATK,   STAT_SPDEF},
                    {STAT_SPATK, STAT_SPEED},
                    {0,  0},
                    {STAT_SPATK, STAT_SPDEF},
                    {STAT_DEF,   STAT_SPEED},
                    {STAT_SPDEF, STAT_SPEED},
                    {0,  0},
                    {STAT_SPEED, STAT_ATK},
            };
}
