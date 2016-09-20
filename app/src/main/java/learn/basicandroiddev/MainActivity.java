package learn.basicandroiddev;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity
{
    public static final int VERSION = VersionManager.v0_0_1;

    // enumerate menu options
    public static final int MENU_NEW = 1;
    public static final int MENU_SETTINGS = 2;

    public static final int MENU_SETTINGS_HANDEDNESS = 3;
    public static final int MENU_SETTINGS_AUTO_EV = 4;

    // enumerate request types
    public static final int REQUEST_NUMBER_LV          = 0;
    public static final int REQUEST_NUMBER_EV_HP       = REQUEST_NUMBER_LV + 1;
    public static final int REQUEST_NUMBER_EV_ATK      = REQUEST_NUMBER_EV_HP + 1;
    public static final int REQUEST_NUMBER_EV_DEF      = REQUEST_NUMBER_EV_ATK + 1;
    public static final int REQUEST_NUMBER_EV_SPATK    = REQUEST_NUMBER_EV_DEF + 1;
    public static final int REQUEST_NUMBER_EV_SPDEF    = REQUEST_NUMBER_EV_SPATK + 1;
    public static final int REQUEST_NUMBER_EV_SPEED    = REQUEST_NUMBER_EV_SPDEF + 1;
    public static final int REQUEST_NUMBER_STAT_HP     = REQUEST_NUMBER_EV_SPEED + 1;
    public static final int REQUEST_NUMBER_STAT_ATK    = REQUEST_NUMBER_STAT_HP + 1;
    public static final int REQUEST_NUMBER_STAT_DEF    = REQUEST_NUMBER_STAT_ATK + 1;
    public static final int REQUEST_NUMBER_STAT_SPATK  = REQUEST_NUMBER_STAT_DEF + 1;
    public static final int REQUEST_NUMBER_STAT_SPDEF  = REQUEST_NUMBER_STAT_SPATK + 1;
    public static final int REQUEST_NUMBER_STAT_SPEED  = REQUEST_NUMBER_STAT_SPDEF + 1;
    public static final int REQUEST_NUMBER_TOTAL       = REQUEST_NUMBER_STAT_SPEED + 1;
    public static final int REQUEST_SPECIES            = REQUEST_NUMBER_TOTAL + 1;
    public static final int REQUEST_NATURE             = REQUEST_SPECIES + 1;

    // constants
    public static final int MAX_STAT_VALUE = 999;
    public static final int MAX_LEVEL_VALUE = 100;
    public static final int MAX_EV_VALUE = 255;

    public static PokeObject POKEMON = new PokeObject();
    public static SaveManager saveManager = new SaveManager(VERSION);

    // input variables
    public int[] ADDED_STATS = {0,0,0,0,0,0};
    public int[] ADDED_EVS = {0,0,0,0,0,0};

    // display variables
    public static boolean autoUpdateEvs = false;
    public static boolean leftHandedMode = true;

    public static boolean initialCalculation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_right);

        // import config from file
        {
            // get boolean values
            int configInput = FileEditor.readIntFromFile("userConfig.op", 0);
            if (configInput != -1)
            {
                autoUpdateEvs = (configInput == 1);

                configInput = FileEditor.readIntFromFile("userConfig.op", 1);
                leftHandedMode = (configInput == 1);

            }
        } // end import config from file

        //DisplayMetrics dm = getResources().getDisplayMetrics();
        //float fwidth = dm.density * dm.widthPixels;
        //float fheight = dm.density * dm.heightPixels;

        if(leftHandedMode)
            setContentView(R.layout.activity_main);

        FileEditor.setBaseDirectory(this);

        TextView nameOutput = (TextView) findViewById(R.id.main_species_text);
        nameOutput.setText(PokeData.Name[0]);
        TextView natureOutput = (TextView) findViewById(R.id.main_nature_text);
        natureOutput.setText(StatData.NATURE_OUTPUT_DEFAULT);

        // auto add pokemon for testing
        //{
        //    POKEMON.level = 72;
        //    POKEMON.species = 384;
        //    POKEMON.nature = StatData.NATURE_ADAMANT;
        //    int[] statsss = {254,266,156,208,153,164};
        //    for(int i = 0; i < StatData.NUMBER_STATS; i++)
        //    {
        //        POKEMON.stats[i] = statsss[i];
        //    }
        //}

        if(saveManager.loadFileToMemory("_autoSave.pkm"))
        {
            if(VERSION != saveManager.getSaveVersion())
            {
                VersionManager.handleVersion(VERSION, saveManager.getSaveVersion());
                initialCalculation = true;
            }
            else
            {
                System.out.println("saveManager.getNumEntries = " + saveManager.getNumEntries());
                saveManager.loadPokeFromMemory(POKEMON, saveManager.getNumEntries());
                initialCalculation = false;
            }
            updateAll();
        }
    }



    // create action menu at top of the screen
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add(0, MENU_NEW, 0, "New");
        SubMenu settingsMenu = menu.addSubMenu(0, MENU_SETTINGS, 1,"Settings");

        String handedText = "Toggle Hand Orientation";
        String autoEvText = "Toggle Auto EV calculation";

        settingsMenu.add(MENU_SETTINGS, MENU_SETTINGS_HANDEDNESS, 0, handedText);
        settingsMenu.add(MENU_SETTINGS, MENU_SETTINGS_AUTO_EV, 1, autoEvText);

        menu.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        menu.getItem(1).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        return true;
    }

    // check for options selected in action menu at the top of the screen
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

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
                int val1 = autoUpdateEvs ? 1 : 0;
                int val2 = leftHandedMode ? 1 : 0;
                FileEditor.deleteFile("userConfig.op");
                FileEditor.writeFile("userConfig.op", val1);
                FileEditor.writeFile("userConfig.op", val2);

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

                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                //updateBasicPokeInfo();
                //updateEvInput();
                //updateStatInput();
                //updateEvDisplay();
                //updateStatTotalDisplay();

                return true;
            }
            case MENU_SETTINGS_AUTO_EV:
            {
                autoUpdateEvs = !autoUpdateEvs;

                // save to config
                // TODO: make config manager
                int val1 = autoUpdateEvs ? 1 : 0;
                int val2 = leftHandedMode ? 1 : 0;
                FileEditor.deleteFile("userConfig.op");
                FileEditor.writeFile("userConfig.op", val1);
                FileEditor.writeFile("userConfig.op", val2);

                String msg = "Automatic EV Calculation ";
                if(autoUpdateEvs)
                    msg = msg + "Enabled";
                else
                    msg = msg + "Disabled";

                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // return from number screen
        if(requestCode < REQUEST_NUMBER_TOTAL)
        {
            if (resultCode == RESULT_CANCELED)
                return;

            switch(requestCode)
            {
                default: break;
                case REQUEST_NUMBER_LV:
                {
                    TextView message = (TextView)
                            findViewById(R.id.main_level_input_text);

                    String LvSentBack = data.getStringExtra("returnNumber");

                    POKEMON.level = Integer.parseInt(LvSentBack);

                    message.setText(LvSentBack);
                } break;
                case REQUEST_NUMBER_EV_HP:
                {
                    TextView inputTV = (TextView)
                            findViewById(R.id.main_hp_ev_input_text);
                    TextView outputTV = (TextView)
                            findViewById(R.id.main_hp_ev_text);

                    String valueSentBack = data.getStringExtra("returnNumber");
                    ADDED_EVS[StatData.STAT_HP] = Integer.parseInt(valueSentBack);

                    updateEv(inputTV, outputTV, StatData.STAT_HP);

                    // continue to next stat if true
                    if(data.getBooleanExtra("chainInput", false) && initialCalculation)
                    {
                        Intent setEvInputIntent = new Intent(this, NumberActivity.class);

                        setEvInputIntent.putExtra("callNumber", MAX_EV_VALUE);
                        setEvInputIntent.putExtra("allowNegative", true);
                        //setEvInputIntent.putExtra("chainInput", true);
                        setEvInputIntent.putExtra("numberPurpose", "Set Attack Effort Value");

                        startActivityForResult(setEvInputIntent, REQUEST_NUMBER_EV_ATK);
                    }
                } break;
                case REQUEST_NUMBER_EV_ATK:
                {
                    TextView inputTV = (TextView)
                            findViewById(R.id.main_atk_ev_input_text);
                    TextView outputTV = (TextView)
                            findViewById(R.id.main_atk_ev_text);

                    String valueSentBack = data.getStringExtra("returnNumber");
                    ADDED_EVS[StatData.STAT_ATK] = Integer.parseInt(valueSentBack);

                    updateEv(inputTV, outputTV, StatData.STAT_ATK);

                    // continue to next stat if true
                    if(data.getBooleanExtra("chainInput", false) && initialCalculation)
                    {
                        Intent setEvInputIntent = new Intent(this, NumberActivity.class);

                        setEvInputIntent.putExtra("callNumber", MAX_EV_VALUE);
                        setEvInputIntent.putExtra("allowNegative", true);
                        //setEvInputIntent.putExtra("chainInput", true);
                        setEvInputIntent.putExtra("numberPurpose", "Set Defense Effort Value");

                        startActivityForResult(setEvInputIntent, REQUEST_NUMBER_EV_DEF);
                    }

                } break;
                case REQUEST_NUMBER_EV_DEF:
                {
                    TextView inputTV = (TextView)
                            findViewById(R.id.main_def_ev_input_text);
                    TextView outputTV = (TextView)
                            findViewById(R.id.main_def_ev_text);

                    String valueSentBack = data.getStringExtra("returnNumber");
                    ADDED_EVS[StatData.STAT_DEF] = Integer.parseInt(valueSentBack);

                    updateEv(inputTV, outputTV, StatData.STAT_DEF);

                    // continue to next stat if true
                    if(data.getBooleanExtra("chainInput", false) && initialCalculation)
                    {
                        Intent setEvInputIntent = new Intent(this, NumberActivity.class);

                        setEvInputIntent.putExtra("callNumber", MAX_EV_VALUE);
                        setEvInputIntent.putExtra("allowNegative", true);
                        //setEvInputIntent.putExtra("chainInput", true);
                        setEvInputIntent.putExtra("numberPurpose", "Set Sp.Attack Effort Value");

                        startActivityForResult(setEvInputIntent, REQUEST_NUMBER_EV_SPATK);
                    }

                } break;
                case REQUEST_NUMBER_EV_SPATK:
                {
                    TextView inputTV = (TextView)
                            findViewById(R.id.main_spa_ev_input_text);
                    TextView outputTV = (TextView)
                            findViewById(R.id.main_spa_ev_text);

                    String valueSentBack = data.getStringExtra("returnNumber");
                    ADDED_EVS[StatData.STAT_SPATK] = Integer.parseInt(valueSentBack);

                    updateEv(inputTV, outputTV, StatData.STAT_SPATK);

                    // continue to next stat if true
                    if(data.getBooleanExtra("chainInput", false) && initialCalculation)
                    {
                        Intent setEvInputIntent = new Intent(this, NumberActivity.class);

                        setEvInputIntent.putExtra("callNumber", MAX_EV_VALUE);
                        setEvInputIntent.putExtra("allowNegative", true);
                        //setEvInputIntent.putExtra("chainInput", true);
                        setEvInputIntent.putExtra("numberPurpose", "Set Sp.Defense Effort Value");

                        startActivityForResult(setEvInputIntent, REQUEST_NUMBER_EV_SPDEF);
                    }

                } break;
                case REQUEST_NUMBER_EV_SPDEF:
                {
                    TextView inputTV = (TextView)
                            findViewById(R.id.main_spd_ev_input_text);
                    TextView outputTV = (TextView)
                            findViewById(R.id.main_spd_ev_text);

                    String valueSentBack = data.getStringExtra("returnNumber");
                    ADDED_EVS[StatData.STAT_SPDEF] = Integer.parseInt(valueSentBack);

                    updateEv(inputTV, outputTV, StatData.STAT_SPDEF);

                    // continue to next stat if true
                    if(data.getBooleanExtra("chainInput", false) && initialCalculation)
                    {
                        Intent setEvInputIntent = new Intent(this, NumberActivity.class);

                        setEvInputIntent.putExtra("callNumber", MAX_EV_VALUE);
                        setEvInputIntent.putExtra("allowNegative", true);
                        //setEvInputIntent.putExtra("chainInput", true);
                        setEvInputIntent.putExtra("numberPurpose", "Set Speed Effort Value");

                        startActivityForResult(setEvInputIntent, REQUEST_NUMBER_EV_SPEED);
                    }

                } break;
                case REQUEST_NUMBER_EV_SPEED:
                {
                    TextView inputTV = (TextView)
                            findViewById(R.id.main_spe_ev_input_text);
                    TextView outputTV = (TextView)
                            findViewById(R.id.main_spe_ev_text);

                    String valueSentBack = data.getStringExtra("returnNumber");
                    ADDED_EVS[StatData.STAT_SPEED] = Integer.parseInt(valueSentBack);

                    updateEv(inputTV, outputTV, StatData.STAT_SPEED);

                } break;
                case REQUEST_NUMBER_STAT_HP:
                {
                    TextView outputTV = (TextView)
                            findViewById(R.id.main_hp_total_text);

                    String valueSentBack = data.getStringExtra("returnNumber");
                    POKEMON.stats[StatData.STAT_HP] = Integer.parseInt(valueSentBack);

                    outputTV.setText(valueSentBack);

                    // continue to next stat if true
                    if(data.getBooleanExtra("chainInput", false))
                    {
                        Intent setStatInputIntent = new Intent(this, NumberActivity.class);

                        setStatInputIntent.putExtra("callNumber", MAX_STAT_VALUE);
                        setStatInputIntent.putExtra("allowNegative", true);
                        setStatInputIntent.putExtra("chainInput", true);
                        setStatInputIntent.putExtra("numberPurpose", "Set Initial Attack Value");

                        startActivityForResult(setStatInputIntent, REQUEST_NUMBER_STAT_ATK);
                    }

                    if(!initialCalculation)
                        outputTV.setClickable(false);

                } break;
                case REQUEST_NUMBER_STAT_ATK:
                {
                    TextView outputTV = (TextView)
                            findViewById(R.id.main_atk_total_text);

                    String valueSentBack = data.getStringExtra("returnNumber");
                    POKEMON.stats[StatData.STAT_ATK] = Integer.parseInt(valueSentBack);

                    outputTV.setText(valueSentBack);

                    // continue to next stat if true
                    if(data.getBooleanExtra("chainInput", false))
                    {
                        Intent setStatInputIntent = new Intent(this, NumberActivity.class);

                        setStatInputIntent.putExtra("callNumber", MAX_STAT_VALUE);
                        setStatInputIntent.putExtra("allowNegative", true);
                        setStatInputIntent.putExtra("chainInput", true);
                        setStatInputIntent.putExtra("numberPurpose", "Set Initial Defense Value");

                        startActivityForResult(setStatInputIntent, REQUEST_NUMBER_STAT_DEF);
                    }

                    if(!initialCalculation)
                        outputTV.setClickable(false);
                } break;
                case REQUEST_NUMBER_STAT_DEF:
                {
                    TextView outputTV = (TextView)
                            findViewById(R.id.main_def_total_text);

                    String valueSentBack = data.getStringExtra("returnNumber");
                    POKEMON.stats[StatData.STAT_DEF] = Integer.parseInt(valueSentBack);

                    outputTV.setText(valueSentBack);

                    // continue to next stat if true
                    if(data.getBooleanExtra("chainInput", false))
                    {
                        Intent setStatInputIntent = new Intent(this, NumberActivity.class);

                        setStatInputIntent.putExtra("callNumber", MAX_STAT_VALUE);
                        setStatInputIntent.putExtra("allowNegative", true);
                        setStatInputIntent.putExtra("chainInput", true);
                        setStatInputIntent.putExtra("numberPurpose", "Set Initial Sp.Attack Value");

                        startActivityForResult(setStatInputIntent, REQUEST_NUMBER_STAT_SPATK);
                    }

                    if(!initialCalculation)
                        outputTV.setClickable(false);
                } break;
                case REQUEST_NUMBER_STAT_SPATK:
                {
                    TextView outputTV = (TextView)
                            findViewById(R.id.main_spa_total_text);

                    String valueSentBack = data.getStringExtra("returnNumber");
                    POKEMON.stats[StatData.STAT_SPATK] = Integer.parseInt(valueSentBack);

                    outputTV.setText(valueSentBack);

                    // continue to next stat if true
                    if(data.getBooleanExtra("chainInput", false))
                    {
                        Intent setStatInputIntent = new Intent(this, NumberActivity.class);

                        setStatInputIntent.putExtra("callNumber", MAX_STAT_VALUE);
                        setStatInputIntent.putExtra("allowNegative", true);
                        setStatInputIntent.putExtra("chainInput", true);
                        setStatInputIntent.putExtra("numberPurpose", "Set Initial Sp.Defense Value");

                        startActivityForResult(setStatInputIntent, REQUEST_NUMBER_STAT_SPDEF);
                    }

                    if(!initialCalculation)
                        outputTV.setClickable(false);
                } break;
                case REQUEST_NUMBER_STAT_SPDEF:
                {
                    TextView outputTV = (TextView)
                            findViewById(R.id.main_spd_total_text);

                    String valueSentBack = data.getStringExtra("returnNumber");
                    POKEMON.stats[StatData.STAT_SPDEF] = Integer.parseInt(valueSentBack);

                    outputTV.setText(valueSentBack);

                    // continue to next stat if true
                    if(data.getBooleanExtra("chainInput", false))
                    {
                        Intent setStatInputIntent = new Intent(this, NumberActivity.class);

                        setStatInputIntent.putExtra("callNumber", MAX_STAT_VALUE);
                        setStatInputIntent.putExtra("allowNegative", true);
                        //setStatInputIntent.putExtra("chainInput", true);
                        setStatInputIntent.putExtra("numberPurpose", "Set Initial Speed Value");

                        startActivityForResult(setStatInputIntent, REQUEST_NUMBER_STAT_SPEED);
                    }

                    if(!initialCalculation)
                        outputTV.setClickable(false);
                } break;
                case REQUEST_NUMBER_STAT_SPEED:
                {
                    TextView outputTV = (TextView)
                            findViewById(R.id.main_spe_total_text);

                    String valueSentBack = data.getStringExtra("returnNumber");
                    POKEMON.stats[StatData.STAT_SPEED] = Integer.parseInt(valueSentBack);

                    outputTV.setText(valueSentBack);

                    if(!initialCalculation)
                        outputTV.setClickable(false);
                } break;
            }

        }
        // return from species screen
        else if(requestCode == REQUEST_SPECIES)
        {
            if(resultCode == RESULT_CANCELED)
                return;

            TextView message = (TextView)
                    findViewById(R.id.main_species_text);

            int speciesSentBack = data.getIntExtra("returnSpecies", 0);

            POKEMON.species = speciesSentBack;

            //Calculator calculator = new Calculator();
            //calculator.calculateAllStats(POKEMON);

           //updateAll();
            message.setText(PokeData.Name[speciesSentBack]);
        }
        // return from nature screen
        else if(requestCode == REQUEST_NATURE)
        {
            if(resultCode == RESULT_CANCELED)
                return;

            TextView message = (TextView)
                    findViewById(R.id.main_nature_text);

            int natureSentBack = data.getIntExtra("returnNature", StatData.NATURE_NONE);

            if(natureSentBack != StatData.NATURE_NONE)
                POKEMON.nature = natureSentBack;

            //Calculator calculator = new Calculator();
            //calculator.calculateAllStats(POKEMON);
            //updateAll();

            if(natureSentBack < 0)
            {
                message.setText(StatData.NATURE_OUTPUT_DEFAULT);
                return;
            }

            message.setText(StatData.NatureName[natureSentBack]);
        }
    }

    // open number activity and get a number
    public void onSetLevel(View view)
    {
        if(!initialCalculation)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        Intent setLevelInput = new Intent(this, NumberActivity.class);
       // TextView message = (TextView)
        //        findViewById(R.id.main_level_input_text);

        //final String extra = message.getText().toString();
        setLevelInput.putExtra("callNumber", MAX_LEVEL_VALUE);
        setLevelInput.putExtra("allowNegative", false);
        setLevelInput.putExtra("numberPurpose", "Set the Pokemon's Level");

        startActivityForResult(setLevelInput, REQUEST_NUMBER_LV);
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

        Intent setNatureInput = new Intent(this, NatureActivity.class);
        startActivityForResult(setNatureInput, REQUEST_NATURE);
    }


    // open number activity and get a number
    public void onSetEvInputHp(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        Intent setEvInputIntent = new Intent(this, NumberActivity.class);

        if(initialCalculation)
            setEvInputIntent.putExtra("chainInput", true);

        setEvInputIntent.putExtra("callNumber", MAX_EV_VALUE);
        setEvInputIntent.putExtra("allowNegative", true);
        setEvInputIntent.putExtra("numberPurpose", "Set HP Effort Value");

        startActivityForResult(setEvInputIntent, REQUEST_NUMBER_EV_HP);
    }
    // open number activity and get a number
    public void onSetEvInputAtk(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        Intent setEvInputIntent = new Intent(this, NumberActivity.class);

        if(initialCalculation)
            setEvInputIntent.putExtra("chainInput", true);

        setEvInputIntent.putExtra("callNumber", MAX_EV_VALUE);
        setEvInputIntent.putExtra("allowNegative", true);
        setEvInputIntent.putExtra("numberPurpose", "Set Attack Effort Value");

        startActivityForResult(setEvInputIntent, REQUEST_NUMBER_EV_ATK);
    }
    // open number activity and get a number
    public void onSetEvInputDef(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        Intent setEvInputIntent = new Intent(this, NumberActivity.class);

        if(initialCalculation)
            setEvInputIntent.putExtra("chainInput", true);

        setEvInputIntent.putExtra("callNumber", MAX_EV_VALUE);
        setEvInputIntent.putExtra("allowNegative", true);
        setEvInputIntent.putExtra("numberPurpose", "Set Defense Effort Value");

        startActivityForResult(setEvInputIntent, REQUEST_NUMBER_EV_DEF);
    }
    // open number activity and get a number
    public void onSetEvInputSpa(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        Intent setEvInputIntent = new Intent(this, NumberActivity.class);

        if(initialCalculation)
            setEvInputIntent.putExtra("chainInput", true);

        setEvInputIntent.putExtra("callNumber", MAX_EV_VALUE);
        setEvInputIntent.putExtra("allowNegative", true);
        setEvInputIntent.putExtra("numberPurpose", "Set Sp.Attack Effort Value");

        startActivityForResult(setEvInputIntent, REQUEST_NUMBER_EV_SPATK);
    }
    // open number activity and get a number
    public void onSetEvInputSpd(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        Intent setEvInputIntent = new Intent(this, NumberActivity.class);

        if(initialCalculation)
            setEvInputIntent.putExtra("chainInput", true);

        setEvInputIntent.putExtra("callNumber", MAX_EV_VALUE);
        setEvInputIntent.putExtra("allowNegative", true);
        setEvInputIntent.putExtra("numberPurpose", "Set Sp.Defense Effort Value");

        startActivityForResult(setEvInputIntent, REQUEST_NUMBER_EV_SPDEF);
    }
    // open number activity and get a number
    public void onSetEvInputSpe(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        Intent setEvInputIntent = new Intent(this, NumberActivity.class);

        //if(initialCalculation)
        //    setEvInputIntent.putExtra("chainInput", true);

        setEvInputIntent.putExtra("callNumber", MAX_EV_VALUE);
        setEvInputIntent.putExtra("allowNegative", true);
        setEvInputIntent.putExtra("numberPurpose", "Set Speed Effort Value");

        startActivityForResult(setEvInputIntent, REQUEST_NUMBER_EV_SPEED);
    }
    // open number activity and get a number
    public void onSetStatTotalHp(View view)
    {
        if(!initialCalculation)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        Intent setStatIntent = new Intent(this, NumberActivity.class);

        setStatIntent.putExtra("callNumber", MAX_STAT_VALUE);
        setStatIntent.putExtra("allowNegative", false);
        setStatIntent.putExtra("chainInput", true);
        setStatIntent.putExtra("numberPurpose", "Set Initial HP Value");

        startActivityForResult(setStatIntent, REQUEST_NUMBER_STAT_HP);
    }
    // open number activity and get a number
    public void onSetStatTotalAtk(View view)
    {
        if(!initialCalculation)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        Intent setStatIntent = new Intent(this, NumberActivity.class);

        setStatIntent.putExtra("callNumber", MAX_STAT_VALUE);
        setStatIntent.putExtra("allowNegative", false);
        setStatIntent.putExtra("chainInput", true);
        setStatIntent.putExtra("numberPurpose", "Set Initial Attack Value");

        startActivityForResult(setStatIntent, REQUEST_NUMBER_STAT_ATK);
    }
    // open number activity and get a number
    public void onSetStatTotalDef(View view)
    {
        if(!initialCalculation)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        Intent setStatIntent = new Intent(this, NumberActivity.class);

        setStatIntent.putExtra("callNumber", MAX_STAT_VALUE);
        setStatIntent.putExtra("allowNegative", false);
        setStatIntent.putExtra("chainInput", true);
        setStatIntent.putExtra("numberPurpose", "Set Initial Defense Value");

        startActivityForResult(setStatIntent, REQUEST_NUMBER_STAT_DEF);
    }
    // open number activity and get a number
    public void onSetStatTotalSpAtk(View view)
    {
        if(!initialCalculation)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        Intent setStatIntent = new Intent(this, NumberActivity.class);

        setStatIntent.putExtra("callNumber", MAX_STAT_VALUE);
        setStatIntent.putExtra("allowNegative", false);
        setStatIntent.putExtra("chainInput", true);
        setStatIntent.putExtra("numberPurpose", "Set Initial Sp.Attack Value");

        startActivityForResult(setStatIntent, REQUEST_NUMBER_STAT_SPATK);
    }
    // open number activity and get a number
    public void onSetStatTotalSpDef(View view)
    {
        if(!initialCalculation)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        Intent setStatIntent = new Intent(this, NumberActivity.class);

        setStatIntent.putExtra("callNumber", MAX_STAT_VALUE);
        setStatIntent.putExtra("allowNegative", false);
        setStatIntent.putExtra("chainInput", true);
        setStatIntent.putExtra("numberPurpose", "Set Initial Sp.Defense Value");

        startActivityForResult(setStatIntent, REQUEST_NUMBER_STAT_SPDEF);
    }
    // open number activity and get a number
    public void onSetStatTotalSpeed(View view)
    {
        if(!initialCalculation)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        Intent setStatIntent = new Intent(this, NumberActivity.class);

        setStatIntent.putExtra("callNumber", MAX_STAT_VALUE);
        setStatIntent.putExtra("allowNegative", false);
        //setStatIntent.putExtra("chainInput", false);
        setStatIntent.putExtra("numberPurpose", "Set Initial Speed Value");

        startActivityForResult(setStatIntent, REQUEST_NUMBER_STAT_SPEED);
    }

    public void updateEv(TextView input,TextView output, int statPosition)
    {
        int total = ADDED_EVS[statPosition] + POKEMON.EVs[statPosition];

        if(total > MAX_EV_VALUE)
            total = MAX_EV_VALUE;
        else if(total < 0)
            total = 0;

        String inValue = String.valueOf(ADDED_EVS[statPosition]);
        String outValue = String.valueOf(POKEMON.EVs[statPosition]);

        if(autoUpdateEvs)
        {
            ADDED_EVS[statPosition] = 0;
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
        if(initialCalculation)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        TextView ivText = (TextView) findViewById(R.id.main_hp_stat_input_text);
        ADDED_STATS[StatData.STAT_HP]++;

        ivText.setTextColor(Color.parseColor("#000000"));
        ivText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        ivText.setText(String.valueOf(ADDED_STATS[StatData.STAT_HP]));
    }
    public void onMinusStatHp(View view)
    {
        if(initialCalculation)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        TextView ivText = (TextView) findViewById(R.id.main_hp_stat_input_text);

        ADDED_STATS[StatData.STAT_HP]--;

        if(ADDED_STATS[StatData.STAT_HP] < 0)
            ADDED_STATS[StatData.STAT_HP] = 0;

        ivText.setTextColor(Color.parseColor("#000000"));
        ivText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        ivText.setText(String.valueOf(ADDED_STATS[StatData.STAT_HP]));
    }
    public void onPlusStatAtk(View view)
    {
        if(initialCalculation)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        TextView ivText = (TextView) findViewById(R.id.main_atk_stat_input_text);
        ADDED_STATS[StatData.STAT_ATK]++;

        ivText.setTextColor(Color.parseColor("#000000"));
        ivText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        ivText.setText(String.valueOf(ADDED_STATS[StatData.STAT_ATK]));
    }
    public void onMinusStatAtk(View view)
    {
        if(initialCalculation)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        TextView ivText = (TextView) findViewById(R.id.main_atk_stat_input_text);

        ADDED_STATS[StatData.STAT_ATK]--;

        if(ADDED_STATS[StatData.STAT_ATK] < 0)
            ADDED_STATS[StatData.STAT_ATK] = 0;

        ivText.setTextColor(Color.parseColor("#000000"));
        ivText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        ivText.setText(String.valueOf(ADDED_STATS[StatData.STAT_ATK]));
    }
    public void onPlusStatDef(View view)
    {
        if(initialCalculation)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        TextView ivText = (TextView) findViewById(R.id.main_def_stat_input_text);
        ADDED_STATS[StatData.STAT_DEF]++;

        ivText.setTextColor(Color.parseColor("#000000"));
        ivText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        ivText.setText(String.valueOf(ADDED_STATS[StatData.STAT_DEF]));
    }
    public void onMinusStatDef(View view)
    {
        if(initialCalculation)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        TextView ivText = (TextView) findViewById(R.id.main_def_stat_input_text);

        ADDED_STATS[StatData.STAT_DEF]--;

        if(ADDED_STATS[StatData.STAT_DEF] < 0)
            ADDED_STATS[StatData.STAT_DEF] = 0;

        ivText.setTextColor(Color.parseColor("#000000"));
        ivText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        ivText.setText(String.valueOf(ADDED_STATS[StatData.STAT_DEF]));
    }
    public void onPlusStatSpAtk(View view)
    {
        if(initialCalculation)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        TextView ivText = (TextView) findViewById(R.id.main_spa_stat_input_text);
        ADDED_STATS[StatData.STAT_SPATK]++;

        ivText.setTextColor(Color.parseColor("#000000"));
        ivText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        ivText.setText(String.valueOf(ADDED_STATS[StatData.STAT_SPATK]));
    }
    public void onMinusStatSpAtk(View view)
    {
        if(initialCalculation)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        TextView ivText = (TextView) findViewById(R.id.main_spa_stat_input_text);

        ADDED_STATS[StatData.STAT_SPATK]--;

        if(ADDED_STATS[StatData.STAT_SPATK] < 0)
            ADDED_STATS[StatData.STAT_SPATK] = 0;

        ivText.setTextColor(Color.parseColor("#000000"));
        ivText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        ivText.setText(String.valueOf(ADDED_STATS[StatData.STAT_SPATK]));
    }
    public void onPlusStatSpDef(View view)
    {
        if(initialCalculation)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        TextView ivText = (TextView) findViewById(R.id.main_spd_stat_input_text);
        ADDED_STATS[StatData.STAT_SPDEF]++;

        ivText.setTextColor(Color.parseColor("#000000"));
        ivText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        ivText.setText(String.valueOf(ADDED_STATS[StatData.STAT_SPDEF]));
    }
    public void onMinusStatSpDef(View view)
    {
        if(initialCalculation)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        TextView ivText = (TextView) findViewById(R.id.main_spd_stat_input_text);

        ADDED_STATS[StatData.STAT_SPDEF]--;

        if(ADDED_STATS[StatData.STAT_SPDEF] < 0)
            ADDED_STATS[StatData.STAT_SPDEF] = 0;

        ivText.setTextColor(Color.parseColor("#000000"));
        ivText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        ivText.setText(String.valueOf(ADDED_STATS[StatData.STAT_SPDEF]));
    }
    public void onPlusStatSpeed(View view)
    {
        if(initialCalculation)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        TextView ivText = (TextView) findViewById(R.id.main_spe_stat_input_text);
        ADDED_STATS[StatData.STAT_SPEED]++;

        ivText.setTextColor(Color.parseColor("#000000"));
        ivText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        ivText.setText(String.valueOf(ADDED_STATS[StatData.STAT_SPEED]));
    }
    public void onMinusStatSpeed(View view)
    {
        if(initialCalculation)
            return;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        TextView ivText = (TextView) findViewById(R.id.main_spe_stat_input_text);

        ADDED_STATS[StatData.STAT_SPEED]--;

        if(ADDED_STATS[StatData.STAT_SPEED] < 0)
            ADDED_STATS[StatData.STAT_SPEED] = 0;

        ivText.setTextColor(Color.parseColor("#000000"));
        ivText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        ivText.setText(String.valueOf(ADDED_STATS[StatData.STAT_SPEED]));
    }

    public void onPushLevel(View view)
    {
        boolean cancelPush = false;

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        if(POKEMON.level < 1)
        {
            Toast.makeText(this, "Pokemon Level is not set", Toast.LENGTH_SHORT).show();
            cancelPush = true;
        }
        else if(POKEMON.species <= 0)
        {
            Toast.makeText(this, "Pokemon Species is not set", Toast.LENGTH_SHORT).show();
            cancelPush = true;
        }
        else if(POKEMON.nature == StatData.NATURE_NONE)
        {
            Toast.makeText(this, "Pokemon Nature is not set", Toast.LENGTH_SHORT).show();
            cancelPush = true;
        }
        else
        {
            for (int i = 0; i < StatData.NUMBER_STATS; i++)
            {
                if (POKEMON.stats[i] == 0)
                {
                    Toast.makeText(this, StatData.StatName[i] + " is not set", Toast.LENGTH_SHORT).show();
                    cancelPush = true;
                    break;
                }
            }
        }

        if(cancelPush)
        {
            //Toast.makeText(this, "Choose Level, Species, Nature, and set Stats.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!initialCalculation)
            if(POKEMON.level < 100)
                POKEMON.level += 1;

        for(int i = StatData.STAT_HP; i < StatData.NUMBER_STATS; i++)
        {
            POKEMON.stats[i] += ADDED_STATS[i];
            if(POKEMON.stats[i] > MAX_STAT_VALUE)
                POKEMON.stats[i] = MAX_STAT_VALUE;
        }

        if(!autoUpdateEvs)
            for(int i = StatData.STAT_HP; i < StatData.NUMBER_STATS; i++)
            {
                POKEMON.EVs[i] += ADDED_EVS[i];
                if(POKEMON.EVs[i] > MAX_EV_VALUE)
                    POKEMON.EVs[i] = MAX_EV_VALUE;
            }

        Calculator calculator = new Calculator();
        calculator.calculateIvs(POKEMON);

        initialCalculation = false;

        // reset and check for errors
        for(int i = 0; i < StatData.NUMBER_STATS; i++)
        {
            ADDED_STATS[i] = 0;
            ADDED_EVS[i] = 0;

            // error check from iv calculations
            if(POKEMON.stats[i] == Calculator.ERROR_STAT_TOOHIGH)
            {
                String text = StatData.StatName[i] + " is above what is possible.\nPlease re-enter information";
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
                initialCalculation = true;
            }
            else if(POKEMON.stats[i] == Calculator.ERROR_STAT_TOOLOW)
            {
                String text = StatData.StatName[i] + " is below what is possible.\nPlease re-enter information.";
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
                initialCalculation = true;
            }
        }
        saveManager.autoSaveFile(POKEMON);
        updateAll();
    }


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
            initialCalculation = true;

        updateAll();
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

        updateAll();
    }
    /*///////////////////////////////////////////////////////////////////////
     *
     *
     *      UPDATE FUNCTIONS
     *
     *
     *
     **//////////////////////////////////////////////////////////////////////

    public void updateBasicPokeInfo()
    {
        TextView levelTV = (TextView) findViewById(R.id.main_level_input_text);
        TextView speciesTV = (TextView) findViewById(R.id.main_species_text);
        TextView natureTV = (TextView) findViewById(R.id.main_nature_text);

        if(saveManager.getCurrentPosition() != 0)
        {
            levelTV.setText(String.valueOf(POKEMON.level));
            speciesTV.setText(PokeData.Name[POKEMON.species]);
            natureTV.setText(StatData.NatureName[POKEMON.nature]);
        }
        else
        {
            levelTV.setText("---");
            speciesTV.setText(PokeData.Name[0]);
            natureTV.setText("Select Nature");
        }
    }

    public void updateEvInput()
    {
        TextView addEvHp = (TextView) findViewById(R.id.main_hp_ev_input_text);
        TextView addEvAtk = (TextView) findViewById(R.id.main_atk_ev_input_text);
        TextView addEvDef = (TextView) findViewById(R.id.main_def_ev_input_text);
        TextView addEvSpAtk = (TextView) findViewById(R.id.main_spa_ev_input_text);
        TextView addEvSpDef = (TextView) findViewById(R.id.main_spd_ev_input_text);
        TextView addEvSpeed = (TextView) findViewById(R.id.main_spe_ev_input_text);

        String ev = "+EV ";

        addEvHp.setText(ev);
        addEvAtk.setText(ev);
        addEvDef.setText(ev);
        addEvSpAtk.setText(ev);
        addEvSpDef.setText(ev);
        addEvSpeed.setText(ev);
    }

    public void updateStatInput()
    {
        TextView[] view = new TextView[6];

        view[0] = (TextView) findViewById(R.id.main_hp_stat_input_text);
        view[1] = (TextView) findViewById(R.id.main_atk_stat_input_text);
        view[2] = (TextView) findViewById(R.id.main_def_stat_input_text);
        view[3] = (TextView) findViewById(R.id.main_spa_stat_input_text);
        view[4] = (TextView) findViewById(R.id.main_spd_stat_input_text);
        view[5] = (TextView) findViewById(R.id.main_spe_stat_input_text);

        String stat = "+stat ";

        for(int i = 0; i < 6; i++)
        {
            view[i].setText(stat);
            view[i].setTextColor(Color.parseColor("#303030"));
            view[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        }
    }

    public void updateEvDisplay()
    {
        TextView displayEvHp = (TextView) findViewById(R.id.main_hp_ev_text);
        TextView displayEvAtk = (TextView) findViewById(R.id.main_atk_ev_text);
        TextView displayEvDef = (TextView) findViewById(R.id.main_def_ev_text);
        TextView displayEvSpAtk = (TextView) findViewById(R.id.main_spa_ev_text);
        TextView displayEvSpDef = (TextView) findViewById(R.id.main_spd_ev_text);
        TextView displayEvSpeed = (TextView) findViewById(R.id.main_spe_ev_text);

        displayEvHp.setText(String.valueOf(POKEMON.EVs[StatData.STAT_HP]));
        displayEvAtk.setText(String.valueOf(POKEMON.EVs[StatData.STAT_ATK]));
        displayEvDef.setText(String.valueOf(POKEMON.EVs[StatData.STAT_DEF]));
        displayEvSpAtk.setText(String.valueOf(POKEMON.EVs[StatData.STAT_SPATK]));
        displayEvSpDef.setText(String.valueOf(POKEMON.EVs[StatData.STAT_SPDEF]));
        displayEvSpeed.setText(String.valueOf(POKEMON.EVs[StatData.STAT_SPEED]));
    }

    public void updateIvDisplay()
    {
        TextView[] view = new TextView[6];

        view[0] = (TextView) findViewById(R.id.main_hp_iv_range_text);
        view[1] = (TextView) findViewById(R.id.main_atk_iv_range_text);
        view[2] = (TextView) findViewById(R.id.main_def_iv_range_text);
        view[3] = (TextView) findViewById(R.id.main_spa_iv_range_text);
        view[4] = (TextView) findViewById(R.id.main_spd_iv_range_text);
        view[5] = (TextView) findViewById(R.id.main_spe_iv_range_text);

        String dash = " - ";

        for(int i = 0; i < 6; i++)
        {
            // check if min/max are set
            if (saveManager.getCurrentPosition() != 0)
            {
                // check if precise iv is set
                if (POKEMON.IVs[i] != -3)
                {
                    view[i].setText(String.valueOf(POKEMON.IVs[i]));
                    view[i].setTextColor(Color.parseColor("#000000"));
                } else
                {
                    String string = String.valueOf(POKEMON.IVsMin[i]) + dash + String.valueOf(POKEMON.IVsMax[i]);
                    view[i].setText(string);

                    view[i].setTextColor(Color.parseColor("#dd0000"));
                }
            }
            // else no IV values are set
            else
            {
                view[i].setText(dash);
                view[i].setTextColor(Color.parseColor("#000000"));
            }
        }
    }

    public void updateStatTotalDisplay()
    {
        TextView displayTotalHp = (TextView) findViewById(R.id.main_hp_total_text);
        TextView displayTotalAtk = (TextView) findViewById(R.id.main_atk_total_text);
        TextView displayTotalDef = (TextView) findViewById(R.id.main_def_total_text);
        TextView displayTotalSpAtk = (TextView) findViewById(R.id.main_spa_total_text);
        TextView displayTotalSpDef = (TextView) findViewById(R.id.main_spd_total_text);
        TextView displayTotalSpeed = (TextView) findViewById(R.id.main_spe_total_text);

        if(saveManager.getCurrentPosition() != 0)
        {
            displayTotalHp.setText(String.valueOf(POKEMON.stats[StatData.STAT_HP]));
            displayTotalAtk.setText(String.valueOf(POKEMON.stats[StatData.STAT_ATK]));
            displayTotalDef.setText(String.valueOf(POKEMON.stats[StatData.STAT_DEF]));
            displayTotalSpAtk.setText(String.valueOf(POKEMON.stats[StatData.STAT_SPATK]));
            displayTotalSpDef.setText(String.valueOf(POKEMON.stats[StatData.STAT_SPDEF]));
            displayTotalSpeed.setText(String.valueOf(POKEMON.stats[StatData.STAT_SPEED]));
        }
        else
        {
            String dash = "---";

            displayTotalHp.setText(dash);
            displayTotalAtk.setText(dash);
            displayTotalDef.setText(dash);
            displayTotalSpAtk.setText(dash);
            displayTotalSpDef.setText(dash);
            displayTotalSpeed.setText(dash);
        }
    }

    // TODO: break MainActivity.updateAll() into smaller modular functions
    public void updateAll()
    {
        updateBasicPokeInfo();
        updateEvInput();
        updateStatInput();
        updateEvDisplay();
        updateIvDisplay();
        updateStatTotalDisplay();
    }
    public void resetAll()
    {
        POKEMON = new PokeObject();
        //for(int i = 0; i < BACKUP_NUMBER; i++)
        //{
        //    BACKUP[i] = new PokeObject();
        //}

        TextView levelTV = (TextView) findViewById(R.id.main_level_input_text);
        TextView speciesTV = (TextView) findViewById(R.id.main_species_text);
        TextView natureTV = (TextView) findViewById(R.id.main_nature_text);

        TextView addEvHp = (TextView) findViewById(R.id.main_hp_ev_input_text);
        TextView addEvAtk = (TextView) findViewById(R.id.main_atk_ev_input_text);
        TextView addEvDef = (TextView) findViewById(R.id.main_def_ev_input_text);
        TextView addEvSpAtk = (TextView) findViewById(R.id.main_spa_ev_input_text);
        TextView addEvSpDef = (TextView) findViewById(R.id.main_spd_ev_input_text);
        TextView addEvSpeed = (TextView) findViewById(R.id.main_spe_ev_input_text);

        TextView addStatHp = (TextView) findViewById(R.id.main_hp_stat_input_text);
        TextView addStatAtk = (TextView) findViewById(R.id.main_atk_stat_input_text);
        TextView addStatDef = (TextView) findViewById(R.id.main_def_stat_input_text);
        TextView addStatSpAtk = (TextView) findViewById(R.id.main_spa_stat_input_text);
        TextView addStatSpDef = (TextView) findViewById(R.id.main_spd_stat_input_text);
        TextView addStatSpeed = (TextView) findViewById(R.id.main_spe_stat_input_text);

        TextView displayEvHp = (TextView) findViewById(R.id.main_hp_ev_text);
        TextView displayEvAtk = (TextView) findViewById(R.id.main_atk_ev_text);
        TextView displayEvDef = (TextView) findViewById(R.id.main_def_ev_text);
        TextView displayEvSpAtk = (TextView) findViewById(R.id.main_spa_ev_text);
        TextView displayEvSpDef = (TextView) findViewById(R.id.main_spd_ev_text);
        TextView displayEvSpeed = (TextView) findViewById(R.id.main_spe_ev_text);

        TextView displayIvHp = (TextView) findViewById(R.id.main_hp_iv_range_text);
        TextView displayIvAtk = (TextView) findViewById(R.id.main_atk_iv_range_text);
        TextView displayIvDef = (TextView) findViewById(R.id.main_def_iv_range_text);
        TextView displayIvSpAtk = (TextView) findViewById(R.id.main_spa_iv_range_text);
        TextView displayIvSpDef = (TextView) findViewById(R.id.main_spd_iv_range_text);
        TextView displayIvSpeed = (TextView) findViewById(R.id.main_spe_iv_range_text);

        TextView displayTotalHp = (TextView) findViewById(R.id.main_hp_total_text);
        TextView displayTotalAtk = (TextView) findViewById(R.id.main_atk_total_text);
        TextView displayTotalDef = (TextView) findViewById(R.id.main_def_total_text);
        TextView displayTotalSpAtk = (TextView) findViewById(R.id.main_spa_total_text);
        TextView displayTotalSpDef = (TextView) findViewById(R.id.main_spd_total_text);
        TextView displayTotalSpeed = (TextView) findViewById(R.id.main_spe_total_text);

        String dash = " - ";
        String dash3 = "---";
        String stat = "+stat ";
        String ev = "+EV ";
        String zero = "0";
        String nature = "Select Nature";

        levelTV.setText(dash3);
        levelTV.setClickable(true);
        speciesTV.setText(PokeData.Name[0]);
        speciesTV.setClickable(true);
        natureTV.setText(nature);
        natureTV.setClickable(true);

        addStatHp.setText(stat);
        addStatHp.setTextColor(Color.parseColor("#303030"));
        addStatHp.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        addStatAtk.setText(stat);
        addStatAtk.setTextColor(Color.parseColor("#303030"));
        addStatAtk.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        addStatDef.setText(stat);
        addStatDef.setTextColor(Color.parseColor("#303030"));
        addStatDef.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        addStatSpAtk.setText(stat);
        addStatSpAtk.setTextColor(Color.parseColor("#303030"));
        addStatSpAtk.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        addStatSpDef.setText(stat);
        addStatSpDef.setTextColor(Color.parseColor("#303030"));
        addStatSpDef.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        addStatSpeed.setText(stat);
        addStatSpeed.setTextColor(Color.parseColor("#303030"));
        addStatSpeed.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        addEvHp.setText(ev);
        addEvAtk.setText(ev);
        addEvDef.setText(ev);
        addEvSpAtk.setText(ev);
        addEvSpDef.setText(ev);
        addEvSpeed.setText(ev);

        displayEvHp.setText(zero);
        displayEvAtk.setText(zero);
        displayEvDef.setText(zero);
        displayEvSpAtk.setText(zero);
        displayEvSpDef.setText(zero);
        displayEvSpeed.setText(zero);

        displayIvHp.setText(dash);
        displayIvHp.setTextColor(Color.parseColor("#000000"));
        displayIvAtk.setText(dash);
        displayIvAtk.setTextColor(Color.parseColor("#000000"));
        displayIvDef.setText(dash);
        displayIvDef.setTextColor(Color.parseColor("#000000"));
        displayIvSpAtk.setText(dash);
        displayIvSpAtk.setTextColor(Color.parseColor("#000000"));
        displayIvSpDef.setText(dash);
        displayIvSpDef.setTextColor(Color.parseColor("#000000"));
        displayIvSpeed.setText(dash);
        displayIvSpeed.setTextColor(Color.parseColor("#000000"));

        displayTotalHp.setText(dash3);
        displayTotalHp.setClickable(true);
        displayTotalAtk.setText(dash3);
        displayTotalAtk.setClickable(true);
        displayTotalDef.setText(dash3);
        displayTotalDef.setClickable(true);
        displayTotalSpAtk.setText(dash3);
        displayTotalSpAtk.setClickable(true);
        displayTotalSpDef.setText(dash3);
        displayTotalSpDef.setClickable(true);
        displayTotalSpeed.setText(dash3);
        displayTotalSpeed.setClickable(true);

        initialCalculation = true;
    }


}
