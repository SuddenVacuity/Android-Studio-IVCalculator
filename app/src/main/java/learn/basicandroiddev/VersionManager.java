package learn.basicandroiddev;

/**
 * Created by Jerry on 9/19/2016.
 */
public class VersionManager
{
    final static int v0_1_0 = 2; // switch to fragments, add poke gen support, increase save header and entry size
    final static int v0_0_1 = 1; // change save handling
    final static int v0_0_0 = 0; // initial commit

    public static void handleVersion(int currentVersion, int compareVersion)
    {
        if(currentVersion == compareVersion)
            return;

        switch(currentVersion)
        {
            default: break;
            case 0: v0_0_1(compareVersion); break;
            case 1: v0_1_0(compareVersion); break;
            case 2: /* CURRENT VERSION */ break;
        }
    }

    private static void v0_0_1(int version)
    {
        // changed save layout
        FileEditor.deleteDirectory("saves");
    }
    private static void v0_1_0(int version)
    {
        // first value was changed from ev calc toggle to poke generation
        FileEditor.deleteFile("userConfig.op");
        // changed save layout
        FileEditor.deleteDirectory("saves");
    }

}
