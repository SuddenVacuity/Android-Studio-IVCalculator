package learn.basicandroiddev;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import learn.basicandroiddev.fragments.EffortInput;
import learn.basicandroiddev.fragments.Evolution;
import learn.basicandroiddev.fragments.Nature;
import learn.basicandroiddev.fragments.NumberInput;
import learn.basicandroiddev.fragments.PokeInfo;
import learn.basicandroiddev.fragments.PushButton;
import learn.basicandroiddev.fragments.StatInfo;
import learn.basicandroiddev.fragments.StatInput;


public class MainActivity extends AppCompatActivity
{
    public static final int VERSION = VersionManager.v0_1_0;

    PokeInfo fragPokeInfo;
    StatInfo fragStatInfo;
    PushButton fragPushButton;
    Evolution fragEvolution;
    Nature fragNature;
    StatInput fragStatInput;
    EffortInput fragEffortInput;
    NumberInput fragNumber;

    public int selectedStat = StatData.STAT_HP;

    // enumerate menu options
    public static final int MENU_NEW = 1;
    public static final int MENU_SETTINGS = 2;

    public static final int MENU_SETTINGS_HANDEDNESS = 3;
    public static final int MENU_SETTINGS_GENERATION_3 = 4;
    public static final int MENU_SETTINGS_GENERATION_4 = 5;
    public static final int MENU_SETTINGS_GENERATION_5 = 6;
    public static final int MENU_SETTINGS_GENERATION_6 = 7;

    public static final int REQUEST_SPECIES            = 1;

    // UI
    public static final int UI_CURRENT_EFFORT_ITEMS[] = {1, 2, 3, 4, 5, 6}; // num slots in fragEffortInput

    // constants
    public static final int MAX_STAT_VALUE = 999;
    public static final int MAX_LEVEL_VALUE = 100;
    public static final int MAX_EV_VALUE = 255;

    public static SaveManager saveManager = new SaveManager(VERSION);
    public PokeObject POKEMON = new PokeObject();
    public int[] ADDED_STATS = {0,0,0,0,0,0};
    public int[] ADDED_EVS = {0,0,0,0,0,0};
    public int ADDED_EV_OBJECT_TOTAL = 0;
    public int[] ADDED_EV_OBJECT_COUNT = new int[EffortInput.NUMBER_SLOTS];
    public static int evHistoryLength = 1024;
    public int[] EV_HISTORY_DATA = new int[evHistoryLength];

    // manage fragment state
    public int fragTopLeftState = 0;
    public int fragTopRightState = 0;
    public int fragBottomState = 0;
    public final int FRAGSTATE_HIDDEN = 0;
    public final int FRAGSTATE_PUSHBUTTON = 1;
    public final int FRAGSTATE_POKEINFO = 2;
    public final int FRAGSTATE_STATINFO = 3;
    public final int FRAGSTATE_NUMBERPAD = 4;
    public final int FRAGSTATE_STATINPUT = 5;
    public final int FRAGSTATE_NATURE = 6;
    public final int FRAGSTATE_EVOLUTION = 7;
    public final int FRAGSTATE_EFFORT_INPUT = 8;

    // manage data movement
    public int dataTarget = 0;
    public final int TARGET_LEVEL = 1;
    public final int TARGET_SPECIES = 2;
    public final int TARGET_NATURE = 3;
    public final int TARGET_EVS_HP = 4;
    public final int TARGET_EVS_ATK = 5;
    public final int TARGET_EVS_DEF = 6;
    public final int TARGET_EVS_SPATK = 7;
    public final int TARGET_EVS_SPDEF = 8;
    public final int TARGET_EVS_SPEED = 9;
    public final int TARGET_ADDED_EVS_HP = 10;
    public final int TARGET_ADDED_EVS_ATK = 11;
    public final int TARGET_ADDED_EVS_DEF = 12;
    public final int TARGET_ADDED_EVS_SPATK = 13;
    public final int TARGET_ADDED_EVS_SPDEF = 14;
    public final int TARGET_ADDED_EVS_SPEED = 15;
    public final int TARGET_IVS_HP = 16;
    public final int TARGET_IVS_ATK = 17;
    public final int TARGET_IVS_DEF = 18;
    public final int TARGET_IVS_SPATK = 19;
    public final int TARGET_IVS_SPDEF = 20;
    public final int TARGET_IVS_SPEED = 21;
    public final int TARGET_IVS_MIN_HP = 22;
    public final int TARGET_IVS_MIN_ATK = 23;
    public final int TARGET_IVS_MIN_DEF = 24;
    public final int TARGET_IVS_MIN_SPATK = 25;
    public final int TARGET_IVS_MIN_SPDEF = 26;
    public final int TARGET_IVS_MIN_SPEED = 27;
    public final int TARGET_IVS_MAX_HP = 28;
    public final int TARGET_IVS_MAX_ATK = 29;
    public final int TARGET_IVS_MAX_DEF = 30;
    public final int TARGET_IVS_MAX_SPATK = 31;
    public final int TARGET_IVS_MAX_SPDEF = 32;
    public final int TARGET_IVS_MAX_SPEED = 33;
    public final int TARGET_STAT_TOTAL_HP = 34;
    public final int TARGET_STAT_TOTAL_ATK = 35;
    public final int TARGET_STAT_TOTAL_DEF = 36;
    public final int TARGET_STAT_TOTAL_SPATK = 37;
    public final int TARGET_STAT_TOTAL_SPDEF = 38;
    public final int TARGET_STAT_TOTAL_SPEED = 39;

    public int backStack = 0;

    // display variables
    //public static boolean autoUpdateEvs = false;
    public static boolean leftHandedMode = true;
    public static int pokemonGeneration = 6;

    public static boolean initialCalculation = true;
    public static boolean allowChangeStats = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // import config from file
        {
            // get config values
            // TODO: make config manager
            int configInput = FileEditor.readIntFromFile("userConfig.op", 0);
            if (configInput != -1)
            {
                if(configInput != VERSION)
                    VersionManager.handleVersion(VERSION, configInput);
                // generation
                configInput = FileEditor.readIntFromFile("userConfig.op", 1);
                pokemonGeneration = configInput;

                // hand orientation
                configInput = FileEditor.readIntFromFile("userConfig.op", 2);
                leftHandedMode = (configInput == 1);
            }
        } // end import config from file

        //DisplayMetrics dm = getResources().getDisplayMetrics();
        //float fwidth = dm.density * dm.widthPixels;
        //float fheight = dm.density * dm.heightPixels;

        FileEditor.setBaseDirectory(this);

        System.out.println("you got to here");
        //TextView nameOutput = (TextView) findViewById(R.id.main_species_text);
        //nameOutput.setText(PokeData.Name[0]);
        //TextView natureOutput = (TextView) findViewById(R.id.main_nature_text);
        //natureOutput.setText(StatData.NATURE_OUTPUT_DEFAULT);


        if(saveManager.loadFileToMemory("_autoSave.pkm"))
        {
            if(VERSION != saveManager.getSaveVersion())
            {
                VersionManager.handleVersion(VERSION, saveManager.getSaveVersion());
            }

            System.out.println("saveManager.getNumEntries = " + saveManager.getNumEntries());
            saveManager.loadPokeFromMemory(POKEMON, saveManager.getNumEntries());

            final int currentPos = saveManager.getCurrentPosition();

            if(currentPos == 0)
            {
                initialCalculation = true;

                allowChangeStats = true;
            }
            else
            {
                initialCalculation = false;
                // check if previous entry is the same to check if it was evolved
                allowChangeStats = ((currentPos == 1) && !isPrevEntrySpeciesSame());
            }
        }

        if(saveManager.getCurrentPosition() == 0)
            POKEMON = new PokeObject();


        //// auto add pokemon for testing
        //{
        //    // Rayquaza
        //    POKEMON.level = 72;
        //    POKEMON.species = 384;
        //    POKEMON.nature = StatData.NATURE_ADAMANT;
        //    int[] statsss = {254,266,156,208,153,164};
        //    for(int i = 0; i < StatData.NUMBER_STATS; i++)
        //    {
        //        POKEMON.stats[i] = statsss[i];
        //    }
        //    initialCalculation = true;
        //    allowChangeStats = true;
        //}
        // auto add pokemon for testing
        {
            // Eevee
            POKEMON.level = 1;
            POKEMON.species = 133;
            POKEMON.nature = StatData.NATURE_BASHFUL;
            int[] statsss = {11,6,6,6,6,6};
            for(int i = 0; i < StatData.NUMBER_STATS; i++)
            {
                POKEMON.stats[i] = statsss[i];
            }
            initialCalculation = true;
            allowChangeStats = true;
        }

        if(leftHandedMode)
        {
            System.out.println("left mode");
            setContentView(R.layout.activity_main);
        }
        else
        {
            System.out.println("right mode");
            setContentView(R.layout.activity_main_right);
        }

        // initialize all fragments
        fragPushButton = new PushButton();
        fragEvolution = new Evolution();
        fragNature = new Nature();
        fragNumber = new NumberInput();
        fragPokeInfo = new PokeInfo();
        fragStatInfo = new StatInfo();
        fragStatInput = new StatInput();
        fragEffortInput = new EffortInput();

        // set initial fragments
        getFragmentManager().beginTransaction().add(R.id.fragment_container_top_left, fragPushButton).commit();
        fragTopLeftState = FRAGSTATE_PUSHBUTTON;
        getFragmentManager().beginTransaction().add(R.id.fragment_container_top_right, fragPokeInfo).commit();
        fragTopRightState = FRAGSTATE_POKEINFO;
        getFragmentManager().beginTransaction().add(R.id.fragment_container_bottom, fragStatInput).commit();
        fragBottomState = FRAGSTATE_STATINPUT;

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        updateViews();
    }


    @Override
    public void onResume()
    {
        super.onResume();

        updateViews();

        System.out.println("MainActivity.onResume()");
    }


    // create action menu at top of the screen
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add(0, MENU_NEW, 0, "New");
        SubMenu settingsMenu = menu.addSubMenu(0, MENU_SETTINGS, 1,"Settings");

        String handedText = "Toggle Hand Orientation";
        String gen3 =       "Use Generation 3 Settings";
        String gen4 =       "Use Generation 4 Settings";
        String gen5 =       "Use Generation 5 Settings";
        String gen6 =       "Use Generation 6 Settings";

        // add sub menus
        settingsMenu.add(MENU_SETTINGS, MENU_SETTINGS_HANDEDNESS, 0, handedText);
        settingsMenu.add(MENU_SETTINGS, MENU_SETTINGS_GENERATION_3, 1, gen3);
        settingsMenu.add(MENU_SETTINGS, MENU_SETTINGS_GENERATION_4, 2, gen4);
        settingsMenu.add(MENU_SETTINGS, MENU_SETTINGS_GENERATION_5, 3, gen5);
        settingsMenu.add(MENU_SETTINGS, MENU_SETTINGS_GENERATION_6, 4, gen6);

        // add main menues
        menu.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        menu.getItem(1).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        return true;
    }

    // check for options selected in action menu at the top of the screen
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        boolean saveNeeded = false;

        switch(id)
        {
            default: break;
            case MENU_NEW:
            {
                resetAll();
                saveManager.reset();
                FileEditor.deleteFile("saves", "_autoSave.pkm");
                return true;
            }
            case MENU_SETTINGS:
            {
                return true;
            }
            case MENU_SETTINGS_HANDEDNESS:
            {
                leftHandedMode = !leftHandedMode;

                // save to config
                // TODO: make config manager
                int leftModeAsInt = leftHandedMode ? 1 : 0;
                FileEditor.deleteFile("userConfig.op");
                FileEditor.writeFile("userConfig.op", VERSION);
                FileEditor.writeFile("userConfig.op", pokemonGeneration);
                FileEditor.writeFile("userConfig.op", leftModeAsInt);


                String msg = "-Handed Mode Enabled";
                if(leftHandedMode)
                {
                    setContentView(R.layout.activity_main);
                    msg = "Left" + msg;
                }
                else
                {
                    setContentView(R.layout.activity_main_right);
                    msg = "Right" + msg;
                }

                fragPushButton = new PushButton();
                fragStatInput = new StatInput();
                fragEvolution = new Evolution();
                fragNature = new Nature();
                fragNumber = new NumberInput();
                fragPokeInfo = new PokeInfo();
                fragStatInfo = new StatInfo();

                // set initial fragments
                getFragmentManager().beginTransaction().add(R.id.fragment_container_top_left, fragPushButton).commit();
                fragTopLeftState = FRAGSTATE_PUSHBUTTON;
                getFragmentManager().beginTransaction().add(R.id.fragment_container_top_right, fragPokeInfo).commit();
                fragTopRightState = FRAGSTATE_POKEINFO;
                getFragmentManager().beginTransaction().add(R.id.fragment_container_bottom, fragStatInput).commit();
                fragBottomState = FRAGSTATE_STATINPUT;

                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                //updateBasicPokeInfo();
                //updateEvInput();
                //updateStatInput();
                //updateEvDisplay();
                //updateStatTotalDisplay();

                return true;
            }
            case MENU_SETTINGS_GENERATION_3:
            {
                pokemonGeneration = 3;

                // save to config
                // TODO: make config manager
                int leftModeAsInt = leftHandedMode ? 1 : 0;
                FileEditor.deleteFile("userConfig.op");
                FileEditor.writeFile("userConfig.op", VERSION);
                FileEditor.writeFile("userConfig.op", pokemonGeneration);
                FileEditor.writeFile("userConfig.op", leftModeAsInt);

                Toast.makeText(this, "Generation 3 mechanics will be used.", Toast.LENGTH_SHORT).show();
                return true;
            }
            case MENU_SETTINGS_GENERATION_4:
            {
                pokemonGeneration = 4;

                // save to config
                // TODO: make config manager
                int leftModeAsInt = leftHandedMode ? 1 : 0;
                FileEditor.deleteFile("userConfig.op");
                FileEditor.writeFile("userConfig.op", VERSION);
                FileEditor.writeFile("userConfig.op", pokemonGeneration);
                FileEditor.writeFile("userConfig.op", leftModeAsInt);

                Toast.makeText(this, "Generation 4 mechanics will be used.", Toast.LENGTH_SHORT).show();
                return true;
            }
            case MENU_SETTINGS_GENERATION_5:
            {
                pokemonGeneration = 5;

                // save to config
                // TODO: make config manager
                int leftModeAsInt = leftHandedMode ? 1 : 0;
                FileEditor.deleteFile("userConfig.op");
                FileEditor.writeFile("userConfig.op", VERSION);
                FileEditor.writeFile("userConfig.op", pokemonGeneration);
                FileEditor.writeFile("userConfig.op", leftModeAsInt);

                Toast.makeText(this, "Generation 5 mechanics will be used.", Toast.LENGTH_SHORT).show();
                return true;
            }
            case MENU_SETTINGS_GENERATION_6:
            {
                pokemonGeneration = 6;

                // save to config
                // TODO: make config manager
                int leftModeAsInt = leftHandedMode ? 1 : 0;
                FileEditor.deleteFile("userConfig.op");
                FileEditor.writeFile("userConfig.op", VERSION);
                FileEditor.writeFile("userConfig.op", pokemonGeneration);
                FileEditor.writeFile("userConfig.op", leftModeAsInt);

                Toast.makeText(this, "Generation 6 mechanics will be used.", Toast.LENGTH_SHORT).show();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // return from species screen
        if(requestCode == REQUEST_SPECIES)
        {
            if(resultCode == RESULT_CANCELED)
                return;

            TextView message = (TextView)
                    findViewById(R.id.main_species_text);

            int speciesSentBack = data.getIntExtra("returnSpecies", 0);

            POKEMON.species = speciesSentBack;

            System.out.println("MainActivity.onActivityResult() Species Chosen " + PokeData.Name[POKEMON.species]);

            message.setText(PokeData.Name[speciesSentBack]);
        }
        updateViews();
    }

    @Override
    public void onBackPressed()
    {
        //super.onBackPressed();

        if(backStack > 0)
        {
            System.out.println("MainActivity.onBackPressed() Go To Default View");
            fragGetStatInput();
            fragGetPokeInfo();
            fragPokeInfo.setViewsEnabled(true);
            backStack = 0;
            dataTarget = 0;
            updateViews();
        }
        else
        {
            System.out.println("MainActivity.onBackPressed() Exit Program");
            finish();
        }
    }

    public void setData(int data)
    {
        switch(dataTarget)
        {
            default: break;
            case TARGET_LEVEL:            POKEMON.level = data; break;
            case TARGET_SPECIES:          POKEMON.species = data; break;
            case TARGET_NATURE:           POKEMON.nature = data; break;
            case TARGET_EVS_HP:           POKEMON.EVs[StatData.STAT_HP] = data; break;
            case TARGET_EVS_ATK:          POKEMON.EVs[StatData.STAT_ATK] = data; break;
            case TARGET_EVS_DEF:          POKEMON.EVs[StatData.STAT_DEF] = data; break;
            case TARGET_EVS_SPATK:        POKEMON.EVs[StatData.STAT_SPATK] = data; break;
            case TARGET_EVS_SPDEF:        POKEMON.EVs[StatData.STAT_SPDEF] = data; break;
            case TARGET_EVS_SPEED:        POKEMON.EVs[StatData.STAT_SPEED] = data; break;
            case TARGET_ADDED_EVS_HP:     ADDED_EVS[StatData.STAT_HP] = data; break;
            case TARGET_ADDED_EVS_ATK:    ADDED_EVS[StatData.STAT_ATK] = data; break;
            case TARGET_ADDED_EVS_DEF:    ADDED_EVS[StatData.STAT_DEF] = data; break;
            case TARGET_ADDED_EVS_SPATK:  ADDED_EVS[StatData.STAT_SPATK] = data; break;
            case TARGET_ADDED_EVS_SPDEF:  ADDED_EVS[StatData.STAT_SPDEF] = data; break;
            case TARGET_ADDED_EVS_SPEED:  ADDED_EVS[StatData.STAT_SPEED] = data; break;
            case TARGET_IVS_HP:           POKEMON.IVs[StatData.STAT_HP] = data; break;
            case TARGET_IVS_ATK:          POKEMON.IVs[StatData.STAT_ATK] = data; break;
            case TARGET_IVS_DEF:          POKEMON.IVs[StatData.STAT_DEF] = data; break;
            case TARGET_IVS_SPATK:        POKEMON.IVs[StatData.STAT_SPATK] = data; break;
            case TARGET_IVS_SPDEF:        POKEMON.IVs[StatData.STAT_SPDEF] = data; break;
            case TARGET_IVS_SPEED:        POKEMON.IVs[StatData.STAT_SPEED] = data; break;
            case TARGET_IVS_MIN_HP:       POKEMON.IVsMin[StatData.STAT_HP] = data; break;
            case TARGET_IVS_MIN_ATK:      POKEMON.IVsMin[StatData.STAT_ATK] = data; break;
            case TARGET_IVS_MIN_DEF:      POKEMON.IVsMin[StatData.STAT_DEF] = data; break;
            case TARGET_IVS_MIN_SPATK:    POKEMON.IVsMin[StatData.STAT_SPATK] = data; break;
            case TARGET_IVS_MIN_SPDEF:    POKEMON.IVsMin[StatData.STAT_SPDEF] = data; break;
            case TARGET_IVS_MIN_SPEED:    POKEMON.IVsMin[StatData.STAT_SPEED] = data; break;
            case TARGET_IVS_MAX_HP:       POKEMON.IVsMax[StatData.STAT_HP] = data; break;
            case TARGET_IVS_MAX_ATK:      POKEMON.IVsMax[StatData.STAT_ATK] = data; break;
            case TARGET_IVS_MAX_DEF:      POKEMON.IVsMax[StatData.STAT_DEF] = data; break;
            case TARGET_IVS_MAX_SPATK:    POKEMON.IVsMax[StatData.STAT_SPATK] = data; break;
            case TARGET_IVS_MAX_SPDEF:    POKEMON.IVsMax[StatData.STAT_SPDEF] = data; break;
            case TARGET_IVS_MAX_SPEED:    POKEMON.IVsMax[StatData.STAT_SPEED] = data; break;
            case TARGET_STAT_TOTAL_HP:    POKEMON.stats[StatData.STAT_HP] = data; break;
            case TARGET_STAT_TOTAL_ATK:   POKEMON.stats[StatData.STAT_ATK] = data; break;
            case TARGET_STAT_TOTAL_DEF:   POKEMON.stats[StatData.STAT_DEF] = data; break;
            case TARGET_STAT_TOTAL_SPATK: POKEMON.stats[StatData.STAT_SPATK] = data; break;
            case TARGET_STAT_TOTAL_SPDEF: POKEMON.stats[StatData.STAT_SPDEF] = data; break;
            case TARGET_STAT_TOTAL_SPEED: POKEMON.stats[StatData.STAT_SPEED] = data; break;
        }

        dataTarget = 0;
    }

    /*///////////////////////////////////////////////////////////////////////
     *
     *
     *      UPDATE FUNCTIONS
     *
     *
     *
     **//////////////////////////////////////////////////////////////////////

    // update all views
    public void updateViews()
    {
        // update views for poke info
        if(fragTopRightState == FRAGSTATE_POKEINFO)
        {
            fragPokeInfo.updatePokeInfo(POKEMON);
        }
        else if(fragTopRightState == FRAGSTATE_STATINFO)
        {
            fragStatInfo.setStatInfo(POKEMON);
            fragStatInfo.highlightTextView(selectedStat);
        }

        // update views for stat input
        if(fragBottomState == FRAGSTATE_STATINPUT)
        {
            fragStatInput.resetEvInput();
            fragStatInput.resetStatInput();
            fragStatInput.updateEvDisplay(POKEMON, ADDED_EVS);
            fragStatInput.updateIvDisplay(POKEMON);
            fragStatInput.updateStatTotal(POKEMON);
        }
        if(fragBottomState == FRAGSTATE_EFFORT_INPUT)
        {
            for(int i = StatData.STAT_HP; i < StatData.NUMBER_STATS; i++)
            {
                fragEffortInput.updateEvTotalDisplay(POKEMON.EVs[i] + ADDED_EVS[i], i);
            }

            for(int i = 0; i < EffortInput.NUMBER_SLOTS; i++)
            {
                fragEffortInput.updateObjectCountDisplay(ADDED_EV_OBJECT_COUNT[i], i);
                fragEffortInput.updateObjectDisplay(StatData.objectName[UI_CURRENT_EFFORT_ITEMS[i]], i);
            }
        }
    }
    public void resetAll()
    {
        POKEMON = new PokeObject();

        for(int i = 0; i < evHistoryLength; i++)
            EV_HISTORY_DATA[i] = 0;

        ADDED_EV_OBJECT_TOTAL = 0;
        for(int i = 0; i < EffortInput.NUMBER_SLOTS; i++)
        {
            UI_CURRENT_EFFORT_ITEMS[0] = i + 1;
            ADDED_EV_OBJECT_COUNT[i] = 0;
        }

        for(int i = StatData.STAT_HP; i < StatData.NUMBER_STATS; i++)
        {
            ADDED_EVS[i] = 0;
            ADDED_STATS[i] = 0;
        }

        fragGetPokeInfo();
        fragGetStatInput();

        updateViews();

        initialCalculation = true;
        allowChangeStats = true;
    }

    public boolean evolve(View view)
    {
        // get evolution set
        int[] evolutions = {0,0,0,0,0,0,0,0};

        // check if eevee
        if(POKEMON.species == 133)
        {
            evolutions[0] = PokeData.eevolutions[0];
            evolutions[1] = PokeData.eevolutions[1];
            evolutions[2] = PokeData.eevolutions[2];
            evolutions[3] = PokeData.eevolutions[3];
            evolutions[4] = PokeData.eevolutions[4];
            evolutions[5] = PokeData.eevolutions[5];
            evolutions[6] = PokeData.eevolutions[6];
            evolutions[7] = PokeData.eevolutions[7];
        }
        else if(POKEMON.species == 412)
        {
            evolutions[0] = PokeData.burmyevolutions[0];
            evolutions[1] = PokeData.burmyevolutions[1];
            evolutions[2] = PokeData.burmyevolutions[2];
            evolutions[3] = PokeData.burmyevolutions[3];
        }
        else
        {
            evolutions[0] = PokeData.evolutions[POKEMON.species][0];
            evolutions[1] = PokeData.evolutions[POKEMON.species][1];
            evolutions[2] = PokeData.evolutions[POKEMON.species][2];
        }

        // no evolution
        if(evolutions[0] == 0)
        {
            Toast.makeText(this, "This Pokemon does not change form.", Toast.LENGTH_SHORT).show();
            return false;
        }

        fragGetStatInput();

        // one evolution
        if(evolutions[1] == 0)
        {
            POKEMON.species = PokeData.evolutions[POKEMON.species][0];

            POKEMON.stats[StatData.STAT_HP] = 0;
            POKEMON.stats[StatData.STAT_ATK] = 0;
            POKEMON.stats[StatData.STAT_DEF] = 0;
            POKEMON.stats[StatData.STAT_SPATK] = 0;
            POKEMON.stats[StatData.STAT_SPDEF] = 0;
            POKEMON.stats[StatData.STAT_SPEED] = 0;

            allowChangeStats = true;
        }
        else
        {
            fragGetEvolution();
            fragPokeInfo.setViewsEnabled(false);
        }

        updateViews();

        return true;
    }

    public boolean isPrevEntrySpeciesSame()
    {
        // check if poke before is the same species
        final int prevSpecies = saveManager.getIntFromMemory(saveManager.getCurrentPosition() - 1, SaveManager.SAVE_SPECIES);
        System.out.println("isPrevEntrySpeciesSame() " + POKEMON.species + " = " + prevSpecies);

        return (POKEMON.species == prevSpecies);
    }
    public boolean isCurrentEntrySpeciesSame()
    {
        // check if poke before is the same species
        final int prevSpecies = saveManager.getIntFromMemory(saveManager.getCurrentPosition(), SaveManager.SAVE_SPECIES);

        return (POKEMON.species == prevSpecies);
    }

    public void onPushButton(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        // go back to default views
        fragGetPokeInfo();
        fragGetStatInput();

        if(POKEMON.level < 1)
        {
            Toast.makeText(this, "Pokemon Level is not set", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(POKEMON.species <= 0)
        {
            Toast.makeText(this, "Pokemon Species is not set", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(POKEMON.nature == StatData.NATURE_NONE)
        {
            Toast.makeText(this, "Pokemon Nature is not set", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            for (int i = 0; i < StatData.NUMBER_STATS; i++)
            {
                if (POKEMON.stats[i] <= 0)
                {
                    Toast.makeText(this, StatData.StatName[i] + " is not set", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        if(!initialCalculation)
            if(POKEMON.level < MAX_LEVEL_VALUE)
                if(saveManager.getCurrentPosition() > 0) // stay inside saveData limits
                    if(isCurrentEntrySpeciesSame()) //
                        POKEMON.level += 1;

        for(int i = StatData.STAT_HP; i < StatData.NUMBER_STATS; i++)
        {
            if(ADDED_STATS[i] == -1000)
                continue;

            POKEMON.stats[i] += ADDED_STATS[i];
            ADDED_STATS[i] = 0;
            if(POKEMON.stats[i] > MAX_STAT_VALUE)
                POKEMON.stats[i] = MAX_STAT_VALUE;
        }

        // skip added_evs if gen 5+
        if(pokemonGeneration < 5)
            for(int i = StatData.STAT_HP; i < StatData.NUMBER_STATS; i++)
            {
                POKEMON.EVs[i] += ADDED_EVS[i];
                if(POKEMON.EVs[i] > MAX_EV_VALUE)
                    POKEMON.EVs[i] = MAX_EV_VALUE;
                else if(POKEMON.EVs[i] < -MAX_EV_VALUE)
                    POKEMON.EVs[i] = -MAX_EV_VALUE;
            }

        Calculator calculator = new Calculator(pokemonGeneration);
        calculator.calculateIvs(POKEMON);

        initialCalculation = false;
        allowChangeStats = false;

        saveManager.autoSaveFile(POKEMON, ADDED_EVS, EV_HISTORY_DATA);

        updateViews();

    }

    public void fragGetNature()
    {
        if(fragBottomState == FRAGSTATE_NATURE)
        {
            System.out.println("MainActivity.fragGetNature() fragNature already exists");
            return;
        }

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        transaction.replace(R.id.fragment_container_bottom, fragNature);
        transaction.commit();

        backStack = 1;

        fragBottomState = FRAGSTATE_NATURE;
    }

    public void fragGetNumberPad(int currentValue, int maxValue, boolean allowNegative, boolean allowPrevNext)
    {
        if(fragBottomState == FRAGSTATE_NUMBERPAD)
        {
            System.out.println("MainActivity.fragGetNumberPad() fragNumberPad already exists");
            return;
        }

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        transaction.replace(R.id.fragment_container_bottom, fragNumber);
        transaction.commit();

        backStack = 1;

        fragNumber.setupNumberPad(currentValue, maxValue, allowNegative, allowPrevNext);

        fragBottomState = FRAGSTATE_NUMBERPAD;
    }

    public void fragGetStatInput()
    {
        if(fragBottomState == FRAGSTATE_STATINPUT)
        {
            System.out.println("MainActivity.fragGetStatInput() fragStatInput already exists");
            return;
        }

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        transaction.replace(R.id.fragment_container_bottom, fragStatInput);
        transaction.commit();

        fragBottomState = FRAGSTATE_STATINPUT;
    }

    public void fragGetEvolution()
    {
        if(fragBottomState == FRAGSTATE_EVOLUTION)
        {
            System.out.println("MainActivity.fragGetEvolution() fragEvolution already exists");
            return;
        }

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        transaction.replace(R.id.fragment_container_bottom, fragEvolution);
        transaction.commit();

        backStack = 1;

        fragBottomState = FRAGSTATE_EVOLUTION;
    }

    public void fragGetEffortInput()
    {
        if(fragBottomState == FRAGSTATE_EFFORT_INPUT)
        {
            System.out.println("MainActivity.fragGetEffortInput() fragEffortInput already exists");
            return;
        }

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        transaction.replace(R.id.fragment_container_bottom, fragEffortInput);
        transaction.commit();


        fragBottomState = FRAGSTATE_EFFORT_INPUT;
    }

    public void fragGetPokeInfo()
    {
        if (fragTopRightState == FRAGSTATE_POKEINFO)
        {
            System.out.println("MainActivity.fragGetPokeInfo() fragPokeInfo already exists");
            return;
        }

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        transaction.replace(R.id.fragment_container_top_right, fragPokeInfo);
        transaction.commit();
        fragTopRightState = FRAGSTATE_POKEINFO;
    }
    public void fragGetStatInfo()
    {
        if(fragTopRightState == FRAGSTATE_STATINFO)
        {
            System.out.println("MainActivity.fragSetStatInfo() fragStatInfo already exists");
            return;
        }

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        backStack = 1;

        transaction.replace(R.id.fragment_container_top_right, fragStatInfo);
        transaction.commit();
        fragTopRightState = FRAGSTATE_STATINFO;
    }

    /*/////////////////////////////////////////////////////////////////////////////////////////////
    )
    )       Number Fragment onTouch() functions
    )
    )
    )
     *//////////////////////////////////////////////////////////////////////////////////////////////

    public void onNumberInputPrev(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        final int prevDataTarget = dataTarget;

        final int data = fragNumber.getOutput();
        fragNumber.resetOutput();
        setData(data);

        final int startPoint = TARGET_EVS_HP - 1;
        // get stat set - statSets are EVs/IVs/IVsMin/IVsMax/stats
        final int statSet = (prevDataTarget - selectedStat - startPoint) / StatData.NUMBER_STATS;

        // cycle current stat in set
        selectedStat = (selectedStat + (StatData.NUMBER_STATS - 1)) % StatData.NUMBER_STATS;
        dataTarget = 1 + startPoint + (statSet * StatData.NUMBER_STATS) + selectedStat;

        switch(statSet)
        {
            default: System.out.println("MainActivity.onNumberInputPrev() switch(statSet){} = default(" + statSet + ")"); break;
            case 5: if(POKEMON.stats[selectedStat] > -1) fragNumber.setOutput(POKEMON.stats[selectedStat]); else fragNumber.setOutput(0); break;
            case 4: if(POKEMON.IVsMax[selectedStat] < 32) fragNumber.setOutput(POKEMON.IVsMax[selectedStat]); else fragNumber.setOutput(0); break;
            case 3: if(POKEMON.IVsMin[selectedStat] > -1) fragNumber.setOutput(POKEMON.IVsMin[selectedStat]); else fragNumber.setOutput(0); break;
            case 2: if(POKEMON.IVs[selectedStat] > -1) fragNumber.setOutput(POKEMON.IVs[selectedStat]); else fragNumber.setOutput(0); break;
            case 1: fragNumber.setOutput(ADDED_EVS[selectedStat]); break;
            case 0: if(POKEMON.EVs[selectedStat] > -1) fragNumber.setOutput(POKEMON.EVs[selectedStat]); else fragNumber.setOutput(0); break;
        }

        System.out.println("MainActivity.onNumberInputPrev() prevDataTarget(" + prevDataTarget +
                "), dataTarget(" + dataTarget + "), selectedStat(" + selectedStat + "), data(" + data + ")");

        updateViews();
    }
    public void onNumberInputNext(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        final int prevDataTarget = dataTarget;

        final int data = fragNumber.getOutput();
        fragNumber.resetOutput();
        setData(data);

        final int startPoint = TARGET_EVS_HP - 1;
        // get stat set - statSets are EVs/IVs/IVsMin/IVsMax/stats
        final int statSet = (prevDataTarget - selectedStat - startPoint) / StatData.NUMBER_STATS;

        // cycle current stat in set
        selectedStat = (selectedStat + 1) % StatData.NUMBER_STATS;
        dataTarget = 1 + startPoint + (statSet * StatData.NUMBER_STATS) + selectedStat;

        switch(statSet)
        {
            default: System.out.println("MainActivity.onNumberInputNext() switch(statSet){} = default(" + statSet + ")"); break;
            case 5: if(POKEMON.stats[selectedStat] > -1) fragNumber.setOutput(POKEMON.stats[selectedStat]); else fragNumber.setOutput(0); break;
            case 4: if(POKEMON.IVsMax[selectedStat] < 32) fragNumber.setOutput(POKEMON.IVsMax[selectedStat]); else fragNumber.setOutput(0); break;
            case 3: if(POKEMON.IVsMin[selectedStat] > -1) fragNumber.setOutput(POKEMON.IVsMin[selectedStat]); else fragNumber.setOutput(0); break;
            case 2: if(POKEMON.IVs[selectedStat] > -1) fragNumber.setOutput(POKEMON.IVs[selectedStat]); else fragNumber.setOutput(0); break;
            case 1: fragNumber.setOutput(ADDED_EVS[selectedStat]); break;
            case 0: if(POKEMON.EVs[selectedStat] > -1) fragNumber.setOutput(POKEMON.EVs[selectedStat]); else fragNumber.setOutput(0); break;
        }

        System.out.println("MainActivity.onNumberInputNext() prevDataTarget(" + prevDataTarget +
                "), dataTarget(" + dataTarget + "), selectedStat(" + selectedStat + "), data(" + data + ")");

        updateViews();
    }
    public void onNumberInputConfirm(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        int data = fragNumber.getOutput();
        fragNumber.resetOutput();

        if(dataTarget == TARGET_LEVEL)
            fragPokeInfo.setViewsEnabled(true);

        System.out.println("MainActivity.onNumberInputConfirm() data(" + data + "), dataTarget(" + dataTarget + "), selectedStat(" + selectedStat + ")");

        setData(data);
        fragGetStatInput();
        fragGetPokeInfo();

        backStack = 0;

        updateViews();
    }
    public void onNumberInputCancel(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        fragNumber.resetOutput();
        fragGetStatInput();
        fragGetPokeInfo();

        backStack = 0;

        updateViews();
    }
    public void onNumberInputBackspace(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        fragNumber.numberInputBackspace();

        if(fragTopRightState == FRAGSTATE_STATINFO)
        {
            int value = fragNumber.getOutput();
            fragStatInfo.updateStatInfo(value, selectedStat);
        }
        else if(fragTopRightState == FRAGSTATE_POKEINFO)
        {
            fragPokeInfo.setLevelText(fragNumber.getOutputString());
        }
    }
    public void onNumberInputMinus(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        fragNumber.numberInputMinus();

        if(fragTopRightState == FRAGSTATE_STATINFO)
        {
            int value = fragNumber.getOutput();
            fragStatInfo.updateStatInfo(value, selectedStat);
        }
        else if(fragTopRightState == FRAGSTATE_POKEINFO)
        {
            fragPokeInfo.setLevelText(fragNumber.getOutputString());
        }
    }
    public void onNumberInput0(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        fragNumber.addNumber("0");

        if(fragTopRightState == FRAGSTATE_STATINFO)
        {
            int value = fragNumber.getOutput();
            fragStatInfo.updateStatInfo(value, selectedStat);
        }
        else if(fragTopRightState == FRAGSTATE_POKEINFO)
        {
            fragPokeInfo.setLevelText(fragNumber.getOutputString());
        }
    }
    public void onNumberInput1(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        fragNumber.addNumber("1");

        if(fragTopRightState == FRAGSTATE_STATINFO)
        {
            int value = fragNumber.getOutput();
            fragStatInfo.updateStatInfo(value, selectedStat);
        }
        else if(fragTopRightState == FRAGSTATE_POKEINFO)
        {
            fragPokeInfo.setLevelText(fragNumber.getOutputString());
        }
    }
    public void onNumberInput2(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        fragNumber.addNumber("2");

        if(fragTopRightState == FRAGSTATE_STATINFO)
        {
            int value = fragNumber.getOutput();
            fragStatInfo.updateStatInfo(value, selectedStat);
        }
        else if(fragTopRightState == FRAGSTATE_POKEINFO)
        {
            fragPokeInfo.setLevelText(fragNumber.getOutputString());
        }
    }
    public void onNumberInput3(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        fragNumber.addNumber("3");

        if(fragTopRightState == FRAGSTATE_STATINFO)
        {
            int value = fragNumber.getOutput();
            fragStatInfo.updateStatInfo(value, selectedStat);
        }
        else if(fragTopRightState == FRAGSTATE_POKEINFO)
        {
            fragPokeInfo.setLevelText(fragNumber.getOutputString());
        }
    }
    public void onNumberInput4(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        fragNumber.addNumber("4");

        if(fragTopRightState == FRAGSTATE_STATINFO)
        {
            int value = fragNumber.getOutput();
            fragStatInfo.updateStatInfo(value, selectedStat);
        }
        else if(fragTopRightState == FRAGSTATE_POKEINFO)
        {
            fragPokeInfo.setLevelText(fragNumber.getOutputString());
        }
    }
    public void onNumberInput5(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        fragNumber.addNumber("5");

        if(fragTopRightState == FRAGSTATE_STATINFO)
        {
            int value = fragNumber.getOutput();
            fragStatInfo.updateStatInfo(value, selectedStat);
        }
        else if(fragTopRightState == FRAGSTATE_POKEINFO)
        {
            fragPokeInfo.setLevelText(fragNumber.getOutputString());
        }
    }
    public void onNumberInput6(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        fragNumber.addNumber("6");

        if(fragTopRightState == FRAGSTATE_STATINFO)
        {
            int value = fragNumber.getOutput();
            fragStatInfo.updateStatInfo(value, selectedStat);
        }
        else if(fragTopRightState == FRAGSTATE_POKEINFO)
        {
            fragPokeInfo.setLevelText(fragNumber.getOutputString());
        }
    }
    public void onNumberInput7(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        fragNumber.addNumber("7");

        if(fragTopRightState == FRAGSTATE_STATINFO)
        {
            int value = fragNumber.getOutput();
            fragStatInfo.updateStatInfo(value, selectedStat);
        }
        else if(fragTopRightState == FRAGSTATE_POKEINFO)
        {
            fragPokeInfo.setLevelText(fragNumber.getOutputString());
        }
    }
    public void onNumberInput8(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        fragNumber.addNumber("8");

        if(fragTopRightState == FRAGSTATE_STATINFO)
        {
            int value = fragNumber.getOutput();
            fragStatInfo.updateStatInfo(value, selectedStat);
        }
        else if(fragTopRightState == FRAGSTATE_POKEINFO)
        {
            fragPokeInfo.setLevelText(fragNumber.getOutputString());
        }
    }
    public void onNumberInput9(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        fragNumber.addNumber("9");

        if(fragTopRightState == FRAGSTATE_STATINFO)
        {
            int value = fragNumber.getOutput();
            fragStatInfo.updateStatInfo(value, selectedStat);
        }
        else if(fragTopRightState == FRAGSTATE_POKEINFO)
        {
            fragPokeInfo.setLevelText(fragNumber.getOutputString());
        }
    }

    /*/////////////////////////////////////////////////////////////////////////////////////////////
    )
    )       PokeInfo Fragment onTouch() functions
    )
    )
    )
     *//////////////////////////////////////////////////////////////////////////////////////////////

    public void onUndo(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        if(saveManager.getCurrentPosition() == 0)
        {
            Toast.makeText(this, "Cannot Undo", Toast.LENGTH_SHORT).show();
            return;
        }

        final int pos = saveManager.getCurrentPosition() - 1;
        saveManager.loadPokeFromMemory(POKEMON, pos);

        // check if to allow direct stat input
        if(pos == 0)
        {
            POKEMON = new PokeObject();
            initialCalculation = true;
            allowChangeStats = true;
        }

        // check if poke before is the same species
        if(pos > 0)
        {
            // allow changes if previous species is different
            allowChangeStats = !isPrevEntrySpeciesSame();
        }
        else
            allowChangeStats = true;

        fragGetPokeInfo();
        fragGetStatInput();
        dataTarget = 0;

        ADDED_STATS[0] = 0;
        ADDED_STATS[1] = 0;
        ADDED_STATS[2] = 0;
        ADDED_STATS[3] = 0;
        ADDED_STATS[4] = 0;
        ADDED_STATS[5] = 0;

        updateViews();
    }

    public void onRedo(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        if(saveManager.getCurrentPosition() == saveManager.getNumEntries())
        {
            Toast.makeText(this, "Cannot Redo", Toast.LENGTH_SHORT).show();
            return;
        }

        final int pos = saveManager.getCurrentPosition() + 1;
        saveManager.loadPokeFromMemory(POKEMON, pos);

        if(pos == 1)
        {
            initialCalculation = false;
            allowChangeStats = false;
        }
        else // allow changes if previous species is different
            allowChangeStats = !isPrevEntrySpeciesSame();

        fragGetPokeInfo();
        fragGetStatInput();
        dataTarget = 0;

        ADDED_STATS[0] = 0;
        ADDED_STATS[1] = 0;
        ADDED_STATS[2] = 0;
        ADDED_STATS[3] = 0;
        ADDED_STATS[4] = 0;
        ADDED_STATS[5] = 0;

        updateViews();
    }


    // open number activity and get a number
    public void onSetLevel(View view)
    {
        if(!initialCalculation)
            return;
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        fragGetNumberPad(POKEMON.level, MAX_LEVEL_VALUE, false, false);
        fragPokeInfo.setViewsEnabled(false);
        dataTarget = TARGET_LEVEL;
    }

    public void onSetSpecies(View view)
    {
        if(!initialCalculation)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        Intent setLevelInput = new Intent(this, SpeciesActivity.class);
        startActivityForResult(setLevelInput, REQUEST_SPECIES);
    }

    public void onSetNature(View view)
    {
        if(!initialCalculation)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        fragPokeInfo.setViewsEnabled(false);
        dataTarget = TARGET_NATURE;
        fragGetNature();

    }



    /*/////////////////////////////////////////////////////////////////////////////////////////////
    )
    )       StatInfo Fragment onTouch() functions
    )
    )
    )
     *//////////////////////////////////////////////////////////////////////////////////////////////

    private void getStatInSet()
    {
        // seperate dataTarget enum into modable groups
        // remove offset from other data, mod by number of stats
        // then you have groups you can add selectedStat to
        // to get the correct targetData enum
        final int range = TARGET_STAT_TOTAL_SPEED - TARGET_EVS_HP;
        final int statSet = (range - (dataTarget - TARGET_EVS_HP) - selectedStat) / StatData.NUMBER_STATS;

        System.out.println("MainActivity.getStatSet() stat set " + statSet);

        switch(statSet)
        {
            default: break;
            case 5: dataTarget = TARGET_EVS_HP + selectedStat; break;
            case 4: dataTarget = TARGET_ADDED_EVS_HP + selectedStat; break;
            case 3: dataTarget = TARGET_IVS_HP + selectedStat; break;
            case 2: dataTarget = TARGET_IVS_MIN_HP + selectedStat; break;
            case 1: dataTarget = TARGET_IVS_MAX_HP + selectedStat; break;
            case 0: dataTarget = TARGET_STAT_TOTAL_HP + selectedStat; break;
        }
    }


    public void onSetHp(View view)
    {
        selectedStat = StatData.STAT_HP;
        getStatInSet();

        updateViews();

    }
    public void onSetAtk(View view)
    {
        selectedStat = StatData.STAT_ATK;
        getStatInSet();

        updateViews();
    }
    public void onSetDef(View view)
    {
        selectedStat = StatData.STAT_DEF;
        getStatInSet();

        updateViews();
    }
    public void onSetSpAtk(View view)
    {
        selectedStat = StatData.STAT_SPATK;
        getStatInSet();

        updateViews();
    }
    public void onSetSpDef(View view)
    {
        selectedStat = StatData.STAT_SPDEF;
        getStatInSet();

        updateViews();
    }
    public void onSetSpeed(View view)
    {
        selectedStat = StatData.STAT_SPEED;
        getStatInSet();

        updateViews();
    }


    /*/////////////////////////////////////////////////////////////////////////////////////////////
    )
    )       StatInput Fragment onTouch() functions
    )
    )
    )
     *//////////////////////////////////////////////////////////////////////////////////////////////

    private void getFragForEvInput()
    {
        fragGetStatInfo();
        fragStatInfo.setInputMode(fragStatInfo.INPUT_MODE_EV);

        int currentValue = POKEMON.EVs[selectedStat];

        fragGetNumberPad(currentValue, MAX_EV_VALUE, false, true);
    }

    private void getFragForStatInput()
    {
        fragGetStatInfo();
        fragStatInfo.setInputMode(fragStatInfo.INPUT_MODE_STAT);

        //System.out.println("MainActivity.onSetStatTotalHp() selectedStat =  " + selectedStat);
        int currentValue = POKEMON.stats[selectedStat];

        if(currentValue < 0)
            currentValue = 0;

        fragGetNumberPad(currentValue, MAX_STAT_VALUE, false, true);
    }

    public void onGoToEffortInput(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        fragGetEffortInput();
    }

    // open number activity and get a number
    public void onSetIvEvHpDisplay(View view)
    {
        if(!initialCalculation)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        selectedStat = StatData.STAT_HP;
        dataTarget = TARGET_EVS_HP;

        getFragForEvInput();
    }

    // open number activity and get a number
    public void onSetIvEvAtkDisplay(View view)
    {
        if(!initialCalculation)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        selectedStat = StatData.STAT_ATK;
        dataTarget = TARGET_EVS_ATK;

        getFragForEvInput();
    }
    // open number activity and get a number
    public void onSetIvEvDefDisplay(View view)
    {
        if(!initialCalculation)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        selectedStat = StatData.STAT_DEF;
        dataTarget = TARGET_EVS_DEF;

        getFragForEvInput();
    }
    // open number activity and get a number
    public void onSetIvEvSpAtkDisplay(View view)
    {
        if(!initialCalculation)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        selectedStat = StatData.STAT_SPATK;
        dataTarget = TARGET_EVS_SPATK;

        getFragForEvInput();
    }
    // open number activity and get a number
    public void onSetIvEvSpDefDisplay(View view)
    {
        if(!initialCalculation)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        selectedStat = StatData.STAT_SPDEF;
        dataTarget = TARGET_EVS_SPDEF;

        getFragForEvInput();
    }
    // open number activity and get a number
    public void onSetIvEvSpeedDisplay(View view)
    {
        if(!initialCalculation)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        selectedStat = StatData.STAT_SPEED;
        dataTarget = TARGET_EVS_SPEED;

        getFragForEvInput();
    }
    // open number activity and get a number
    public void onSetStatTotalHp(View view)
    {
        if(!allowChangeStats)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);


        selectedStat = StatData.STAT_HP;
        dataTarget = TARGET_STAT_TOTAL_HP;

        getFragForStatInput();
    }
    // open number activity and get a number
    public void onSetStatTotalAtk(View view)
    {
        if(!allowChangeStats)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        selectedStat = StatData.STAT_ATK;
        dataTarget = TARGET_STAT_TOTAL_ATK;

        getFragForStatInput();
    }
    // open number activity and get a number
    public void onSetStatTotalDef(View view)
    {
        if(!allowChangeStats)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        selectedStat = StatData.STAT_DEF;
        dataTarget = TARGET_STAT_TOTAL_DEF;

        getFragForStatInput();
    }
    // open number activity and get a number
    public void onSetStatTotalSpAtk(View view)
    {
        if(!allowChangeStats)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        selectedStat = StatData.STAT_SPATK;
        dataTarget = TARGET_STAT_TOTAL_SPATK;

        getFragForStatInput();
    }
    // open number activity and get a number
    public void onSetStatTotalSpDef(View view)
    {
        if(!allowChangeStats)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        selectedStat = StatData.STAT_SPDEF;
        dataTarget = TARGET_STAT_TOTAL_SPDEF;

        getFragForStatInput();
    }
    // open number activity and get a number
    public void onSetStatTotalSpeed(View view)
    {
        if(!allowChangeStats)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        selectedStat = StatData.STAT_SPEED;
        dataTarget = TARGET_STAT_TOTAL_SPEED;

        getFragForStatInput();
    }

    public void updateEv(TextView input,TextView output, int statPosition)
    {

        int currentEv;

        if(POKEMON.EVs[statPosition] < 0)
            currentEv = 0;
        else
            currentEv = POKEMON.EVs[statPosition];

        int total = ADDED_EVS[statPosition] + currentEv;

        if(total > MAX_EV_VALUE)
            total = MAX_EV_VALUE;
        else if(total < 0)
            total = 0;

        String inValue = String.valueOf(ADDED_EVS[statPosition]);
        String outValue = String.valueOf(currentEv);

        if(pokemonGeneration > 4)
        {
            ADDED_EVS[statPosition] = -1;
            POKEMON.EVs[statPosition] = total;

            inValue = "0";
            outValue = String.valueOf(total);

            input.setText(inValue);
            output.setText(outValue);

            //updateAll();
        }
        else
        {
            input.setText(inValue);
            output.setText(outValue);
        }
    }

    public void onPlusStatHp(View view)
    {
        final int pos = saveManager.getCurrentPosition() + 1;
        if(allowChangeStats && pos == saveManager.getNumEntries())
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        ADDED_STATS[StatData.STAT_HP]++;
        fragStatInput.updateAddedStatDisplay(ADDED_STATS[StatData.STAT_HP], StatData.STAT_HP);
    }
    public void onMinusStatHp(View view)
    {
        final int pos = saveManager.getCurrentPosition() + 1;
        if(allowChangeStats && pos == saveManager.getNumEntries())
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        if(ADDED_STATS[StatData.STAT_HP] > 0)
            ADDED_STATS[StatData.STAT_HP]--;
        fragStatInput.updateAddedStatDisplay(ADDED_STATS[StatData.STAT_HP], StatData.STAT_HP);
    }
    public void onPlusStatAtk(View view)
    {
        final int pos = saveManager.getCurrentPosition() + 1;
        if(allowChangeStats && pos == saveManager.getNumEntries())
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        ADDED_STATS[StatData.STAT_ATK]++;
        fragStatInput.updateAddedStatDisplay(ADDED_STATS[StatData.STAT_ATK], StatData.STAT_ATK);
    }
    public void onMinusStatAtk(View view)
    {
        final int pos = saveManager.getCurrentPosition() + 1;
        if(allowChangeStats && pos == saveManager.getNumEntries())
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        if(ADDED_STATS[StatData.STAT_ATK] > 0)
            ADDED_STATS[StatData.STAT_ATK]--;
        fragStatInput.updateAddedStatDisplay(ADDED_STATS[StatData.STAT_ATK], StatData.STAT_ATK);
    }
    public void onPlusStatDef(View view)
    {
        final int pos = saveManager.getCurrentPosition() + 1;
        if(allowChangeStats && pos == saveManager.getNumEntries())
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        ADDED_STATS[StatData.STAT_DEF]++;
        fragStatInput.updateAddedStatDisplay(ADDED_STATS[StatData.STAT_DEF], StatData.STAT_DEF);
    }
    public void onMinusStatDef(View view)
    {
        final int pos = saveManager.getCurrentPosition() + 1;
        if(allowChangeStats && pos == saveManager.getNumEntries())
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        if(ADDED_STATS[StatData.STAT_DEF] > 0)
            ADDED_STATS[StatData.STAT_DEF]--;
        fragStatInput.updateAddedStatDisplay(ADDED_STATS[StatData.STAT_DEF], StatData.STAT_DEF);
    }
    public void onPlusStatSpAtk(View view)
    {
        final int pos = saveManager.getCurrentPosition() + 1;
        if(allowChangeStats && pos == saveManager.getNumEntries())
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        ADDED_STATS[StatData.STAT_SPATK]++;
        fragStatInput.updateAddedStatDisplay(ADDED_STATS[StatData.STAT_SPATK], StatData.STAT_SPATK);
    }
    public void onMinusStatSpAtk(View view)
    {
        final int pos = saveManager.getCurrentPosition() + 1;
        if(allowChangeStats && pos == saveManager.getNumEntries())
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        if(ADDED_STATS[StatData.STAT_SPATK] > 0)
            ADDED_STATS[StatData.STAT_SPATK]--;
        fragStatInput.updateAddedStatDisplay(ADDED_STATS[StatData.STAT_SPATK], StatData.STAT_SPATK);
    }
    public void onPlusStatSpDef(View view)
    {
        final int pos = saveManager.getCurrentPosition() + 1;
        if(allowChangeStats && pos == saveManager.getNumEntries())
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        ADDED_STATS[StatData.STAT_SPDEF]++;
        fragStatInput.updateAddedStatDisplay(ADDED_STATS[StatData.STAT_SPDEF], StatData.STAT_SPDEF);
    }
    public void onMinusStatSpDef(View view)
    {
        final int pos = saveManager.getCurrentPosition() + 1;
        if(allowChangeStats && pos == saveManager.getNumEntries())
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        if(ADDED_STATS[StatData.STAT_SPDEF] > 0)
            ADDED_STATS[StatData.STAT_SPDEF]--;
        fragStatInput.updateAddedStatDisplay(ADDED_STATS[StatData.STAT_SPDEF], StatData.STAT_SPDEF);
    }
    public void onPlusStatSpeed(View view)
    {
        final int pos = saveManager.getCurrentPosition() + 1;
        if(allowChangeStats && pos == saveManager.getNumEntries())
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        ADDED_STATS[StatData.STAT_SPEED]++;
        fragStatInput.updateAddedStatDisplay(ADDED_STATS[StatData.STAT_SPEED], StatData.STAT_SPEED);
    }
    public void onMinusStatSpeed(View view)
    {
        final int pos = saveManager.getCurrentPosition() + 1;
        if(allowChangeStats && pos == saveManager.getNumEntries())
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        if(ADDED_STATS[StatData.STAT_SPEED] > 0)
            ADDED_STATS[StatData.STAT_SPEED]--;
        fragStatInput.updateAddedStatDisplay(ADDED_STATS[StatData.STAT_SPEED], StatData.STAT_SPEED);
    }


    /*/////////////////////////////////////////////////////////////////////////////////////////////
    )
    )       Nature Fragment onTouch() functions
    )
    )
    )
     *//////////////////////////////////////////////////////////////////////////////////////////////

    public void onNatureConfirm(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        fragPokeInfo.setViewsEnabled(true);
        fragGetStatInput();

    }
    /*/////////////////////////////////////////////////////////////////////////////////////////////
    )
    )       Evolution Fragment onTouch() functions
    )
    )
    )
     *//////////////////////////////////////////////////////////////////////////////////////////////

    public void onEvolutionConfirm(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        allowChangeStats = true;
        fragPokeInfo.setViewsEnabled(true);
        POKEMON.species = fragEvolution.getOutput();
        fragEvolution.resetOutput();

        fragGetStatInput();
        backStack = 0;

        updateViews();
    }
    public void onEvolutionCancel(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        fragPokeInfo.setViewsEnabled(true);
        fragEvolution.resetOutput();

        fragGetStatInput();
        backStack = 0;

        updateViews();
    }

    public void setTempSpeciesName(int PokeNumber)
    {
        fragPokeInfo.setSpeciesText(PokeData.Name[PokeNumber]);
    }

    /*/////////////////////////////////////////////////////////////////////////////////////////////
    )
    )       EffortInput Fragment onTouch() functions
    )
    )
    )
     *//////////////////////////////////////////////////////////////////////////////////////////////


    public void onGoToStatInput(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        fragGetStatInput();
    }

    public void onTogglePokerus(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        if(POKEMON.pokerus == 0)
            POKEMON.pokerus = 1;
        else
            POKEMON.pokerus = 0;

        fragEffortInput.setPokerus((POKEMON.pokerus == 1));

    }

    public void onSetHeldItem(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        POKEMON.heldItem = (POKEMON.heldItem + 1) % StatData.NUMBER_HELD;

        fragEffortInput.setHeldItem(POKEMON.heldItem);
    }

    private boolean searchAddedEvObjects(int itemEnum)
    {
        boolean found = false;

        int startPosition = POKEMON.evHistoryCount;
        int endPosition = startPosition + ADDED_EV_OBJECT_TOTAL;

        if(POKEMON.evHistoryCount == endPosition)
        {
            Toast.makeText(this, "Cannot remove more items.", Toast.LENGTH_SHORT).show();
            return found;
        }

        // check this levels ev entries for the item being removed
        for(int i = startPosition; i < endPosition; i++)
        {
            // check if the item exists in history
            // if it exists shift data towards startPosition
            // TODO: MinaActivity.searchAddedEvObjects() switch to array copy
            if(EV_HISTORY_DATA[i] == itemEnum || found)
            {
                int nextPos = i + 1;
                // prevent out-of-bounds
                if(nextPos == evHistoryLength)
                    EV_HISTORY_DATA[i] = StatData.OBJECT_NONE;
                else
                    EV_HISTORY_DATA[i] = EV_HISTORY_DATA[nextPos];

                found = true;
            }
        }
        return found;
    }


    public void onMinusSlot1Object(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        if(searchAddedEvObjects(UI_CURRENT_EFFORT_ITEMS[0]))
        {
            POKEMON.evHistoryCount--;
            ADDED_EV_OBJECT_COUNT[0]--;
        }

        updateViews();
    }

    public void onPlusSlot1Object(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        if(POKEMON.evHistoryCount == evHistoryLength)
        {
            Toast.makeText(this, "Cannot add more items", Toast.LENGTH_SHORT).show();
            return;
        }

        EV_HISTORY_DATA[POKEMON.evHistoryCount + 1] = UI_CURRENT_EFFORT_ITEMS[0];
        ADDED_EV_OBJECT_COUNT[0]++;
        POKEMON.evHistoryCount++;

        updateViews();
    }

    public void onSelectSlot1Object(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show();


        updateViews();
    }



    public void onMinusSlot2Object(View view)
    {
        Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show();
    }
    public void onPlusSlot2Object(View view)
    {
        Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show();
    }
    public void onSelectSlot2Object(View view)
    {
        Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show();
    }
    public void onMinusSlot3Object(View view)
    {
        Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show();
    }
    public void onPlusSlot3Object(View view)
    {
        Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show();
    }
    public void onSelectSlot3Object(View view)
    {
        Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show();
    }
    public void onMinusSlot4Object(View view)
    {
        Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show();
    }
    public void onPlusSlot4Object(View view)
    {
        Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show();
    }
    public void onSelectSlot4Object(View view)
    {
        Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show();
    }
    public void onMinusSlot5Object(View view)
    {
        Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show();
    }
    public void onPlusSlot5Object(View view)
    {
        Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show();
    }
    public void onSelectSlot5Object(View view)
    {
        Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show();
    }
    public void onMinusSlot6Object(View view)
    {
        Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show();
    }
    public void onPlusSlot6Object(View view)
    {
        Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show();
    }
    public void onSelectSlot6Object(View view)
    {
        Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show();
    }





}
