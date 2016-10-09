package learn.basicandroiddev;

/**
 * Created by Jerry on 9/6/2016.
 */
public class StatData
{
    public static final String NATURE_OUTPUT_DEFAULT = "Select Nature";

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
    public static final int NUMBER_NATURES     = 25;

    public static final int STAT_NONE    = -1;
    public static final int STAT_HP      = 0;
    public static final int STAT_ATK     = 1;
    public static final int STAT_DEF     = 2;
    public static final int STAT_SPATK   = 3;
    public static final int STAT_SPDEF   = 4;
    public static final int STAT_SPEED   = 5;
    public static final int NUMBER_STATS = 6;

    public static final int OBJECT_NONE         = 0;
    public static final int OBJECT_HIT_POINTS   = 1;
    public static final int OBJECT_ATTACK       = 2;
    public static final int OBJECT_DEFENSE      = 3;
    public static final int OBJECT_SP_ATTACK    = 4;
    public static final int OBJECT_SP_DEFENSE   = 5;
    public static final int OBJECT_SPEED        = 6;
    public static final int OBJECT_HP_UP        = 7;
    public static final int OBJECT_PROTIEN      = 8;
    public static final int OBJECT_IRON         = 9;
    public static final int OBJECT_CALCIUM      = 11;
    public static final int OBJECT_ZINC         = 12;
    public static final int OBJECT_CARBOS       = 13;
    public static final int OBJECT_POMEG_BERRY  = 14;
    public static final int OBJECT_KELPSY_BERRY = 15;
    public static final int OBJECT_QUALOT_BERRY = 16;
    public static final int OBJECT_HONDEW_BERRY = 17;
    public static final int OBJECT_GREPA_BERRY  = 18;
    public static final int OBJECT_TOMATO_BERRY = 19;
    public static final int OBJECT_HEALTH_WING  = 20;
    public static final int OBJECT_MUSCLE_WING  = 21;
    public static final int OBJECT_RESIST_WING  = 22;
    public static final int OBJECT_GENIUS_WING  = 23;
    public static final int OBJECT_CLEVER_WING  = 24;
    public static final int OBJECT_SWIFT_WING   = 25;
    public static final int NUMBER_OBJECTS      = 26;

    public static final int HELD_NONE         = 0;
    public static final int HELD_MACHO_BRACE  = 1;
    public static final int HELD_POWER_WEIGHT = 2;
    public static final int HELD_BRACER       = 3;
    public static final int HELD_BELT         = 4;
    public static final int HELD_LENS         = 5;
    public static final int HELD_BAND         = 6;
    public static final int HELD_ANKLET       = 7;
    public static final int NUMBER_HELD       = 8;

    public static final String[] STAT_BACKGROUND =
            {
                    "#ffffff",
                    "#ff0000",
                    "#652020",
                    "#00ffff",
                    "#0000df",
                    "#ffff00",
            };

    public static final String[] StatName =
            {
                    "HP",
                    "Attack",
                    "Defense",
                    "Sp Attack",
                    "Sp Defense",
                    "Speed",
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

    public static String[] objectName =
            {
                    "",
                    "Hit Points",
                    "Attack",
                    "Defense",
                    "Sp Attack",
                    "Sp Defense",
                    "Speed",
                    "Hp Up",
                    "Protein",
                    "Iron",
                    "Calcium",
                    "Zinc",
                    "Carbos",
                    "Pomeg Berry",
                    "Kelpsy Berry",
                    "Qualot Berry",
                    "Hondew Berry",
                    "Grepa Berry",
                    "Tomato Berry",
                    "Health Wing",
                    "Muscle Wing",
                    "Resist Wing",
                    "Genius Wing",
                    "Clever Wing",
                    "Swift Wing",
            };

    // 0 = nothing
    // 1 = Macho Brace
    // 2 = Power Weight - HP - gen4+
    // 3 = Power Bracer - ATK - gen4+
    // 4 = Power Belt - DEF - gen4+
    // 5 = Power Lens - SPATK - gen4+
    // 6 = Power Band - SPDEF - gen4+
    // 7 = Power Anklet - SPEED - gen4+
    public static String[] heldName =
            {
                    "",
                    "Macho Brace",
                    "Power Weight",
                    "Power Bracer",
                    "Power Belt",
                    "Power Lens",
                    "Power Band",
                    "Power Anklet",
            };
}
