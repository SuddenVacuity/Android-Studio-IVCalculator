package learn.basicandroiddev;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by Jerry on 9/13/2016.
 */
public class FileEditor
{
    private static File path;

    // sets (path) to app base directory
    public static void setBaseDirectory(Context context)
    {
        path = context.getFilesDir();
    }

    // returns length in bytes of (fileName)
    public static long getFileLength(String fileName)
    {
        File file = new File(path, fileName);
        return file.length();
    }

    // returns length in bytes of (directory) + (fileName)
    public static long getFileLength(String directory, String fileName)
    {
        // make sure fileName starts with '/'
        String fName = fileName;
        if(fileName.charAt(0) != '/')
            fName = "/" + fName;

        // make sure path and directory have '/' between them
        String fPath = path.getPath();
        if(directory.charAt(0) != '/')
            fPath = fPath + "/" + directory;
        else
            fPath = fPath + directory;

        // get the length
        File file = new File(fPath ,fName);
        return file.length();
    }

    // creates a new file (fileName)
    // returns false if failed
    private static boolean createNewFile(String fileName)
    {
        // make sure fileName starts with '/'
        String fName = fileName;
        if(fileName.charAt(0) != '/')
            fName = "/" + fName;

        // get file at path and delete if it exists
        File file = new File(path, fName);
        file.delete();

        // create new file
        try {
            file.createNewFile();
        } catch(IOException e) {
            System.out.println("createNewFile().General I/O exception: " + e.getMessage());
            return false;
        }

        return true;
    }

    // deletes file (fileName)
    // returns false if file didn't exist
    public static boolean deleteFile(String fileName)
    {
        // make sure fileName starts with '/'
        String fName = fileName;
        if(fileName.charAt(0) != '/')
            fName = "/" + fName;

        // get the file
        File file = new File(path, fName);

        // delete it and return result
        return file.delete();
    }

    // deletes file (directory) + (fileName)
    // returns false if file didn't exist
    public static boolean deleteFile(String directory, String fileName)
    {
        // make sure fileName starts with '/'
        String fName = fileName;
        if(fileName.charAt(0) != '/')
            fName = "/" + fName;

        // make sure path and directory have '/' between them
        String fPath = path.getPath();
        if(directory.charAt(0) != '/')
            fPath = fPath + "/" + directory;
        else
            fPath = fPath + directory;

        // get the file
        File file = new File(fPath ,fName);

        // delete it and return result
        return file.delete();
    }

    // deletes directory (directory)
    // returns false if directory didn't exist
    public static boolean deleteDirectory(String directory)
    {
        // make sure directory starts with '/'
        String fPath = path.getPath();
        if(directory.charAt(0) != '/')
            fPath = fPath + "/" + directory + "/";
        else
            fPath = fPath + directory;

        // get the directory
        File file = new File(fPath);

        // delete it and return result
        return file.delete();
    }

    // UNTESTED
    // writes string (data) to file (fileName)
    // returns false if failed
    public static boolean writeFile(String fileName, String data)
    {
        String fName = fileName;

        if(fileName.charAt(0) != '/')
            fName = "/" + fName;

        File file = new File(path, fName);

        FileOutputStream ostream;
        try {
            // write to file
            ostream = new FileOutputStream(file, true);
            ObjectOutputStream oos = new ObjectOutputStream(ostream);
            oos.writeUTF(data);

            oos.close();
            ostream.close();
        } catch (FileNotFoundException e) {
            System.out.println("writeFile(..., String data).FileNotFoundException: " + e.getMessage());
            createNewFile(fileName);
            return false;
        } catch(IOException e) {
            System.out.println("writeFile(..., String data).General I/O exception: " + e.getMessage());
            return false;
        }

        return true;
    }

    // writes int (data) to file (fileName)
    // returns false if failed
    public static boolean writeFile(String fileName, int data)
    {
        // make sure fileName starts with '/'
        String fName = fileName;
        if(fileName.charAt(0) != '/')
            fName = "/" + fName;

        // get file
        File file = new File(path, fName);

        try {
            // create output streams
            if(!file.exists())
            {
                file.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(file, true);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            // make container to hold data
            final int sizeOfInt = 4;
            byte bytes[] = new byte[sizeOfInt];

            // copy data to container
            bytes[0] = (byte) (data >> 24);
            bytes[1] = (byte) (data >> 16);
            bytes[2] = (byte) (data >> 8);
            bytes[3] = (byte) (data /*>> 0*/);


            // write data to file
            bos.write(bytes);

            // flush and close streams
            bos.flush();
            bos.close();
            fos.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            System.out.println("writeFile(..., int data).FileNotFoundException: " + e.getMessage());
            createNewFile(fileName);
            return false;
        } catch(IOException e) {
            System.out.println("writeFile(..., int data).General I/O exception: " + e.getMessage());
            return false;
        }

        return true;
    }

    // writes int (data) to file (directory) + (fileName)
    // returns false if failed
    public static boolean writeFile(String directory, String fileName, int data)
    {
        // make sure fileName starts with '/'
        String fName = fileName;
        if(fileName.charAt(0) != '/')
            fName = "/" + fName;

        // make sure path and directory have '/' between them
        String fPath = path.getPath();
        if(directory.charAt(0) != '/')
            fPath = fPath + "/" + directory;
        else
            fPath = fPath + directory;

        // get directory and create directory if needed
        //File file = new File(fPath);
        //file.mkdirs();

        // get file
        File file = new File(fPath, fName);

        try {
            if(!file.exists())
            {
                File pFile = new File(fPath);
                pFile.mkdirs();
                file.createNewFile();
            }

            // create output streams
            FileOutputStream fos = new FileOutputStream(file, true);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            // create container to hold data
            final int sizeOfInt = 4;
            byte bytes[] = new byte[sizeOfInt];

            // copy data to container
            bytes[0] = (byte) (data >> 24);
            bytes[1] = (byte) (data >> 16);
            bytes[2] = (byte) (data >> 8);
            bytes[3] = (byte) (data /*>> 0*/);

            // write data to file
            bos.write(bytes);

            // flush and close streams
            bos.flush();
            bos.close();
            fos.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            System.out.println("writeFile(..., int data).FileNotFoundException: " + e.getMessage());
            createNewFile(fileName);
            return false;
        } catch(IOException e) {
            System.out.println("writeFile(..., int data).General I/O exception: " + e.getMessage());
            return false;
        }

        return true;
    }

    // UNTESTED
    // returns string from file(FileName)
    private static String readStringFromFile(String fileName)
    {
        String output;
        String fName = fileName;

        if(fileName.charAt(0) != '/')
            fName = "/" + fName;

        File file = new File(path, fName);

        FileInputStream istream;
        try {
            istream = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(istream);

            output = ois.readUTF();

            ois.close();
            istream.close();
        } catch (FileNotFoundException e){
            System.out.println("readStringFromFile().FileNotFoundException: " + e.getMessage());
            return null;
        } catch(IOException e) {
            System.out.println("readStringFromFile().General I/O exception: " + e.getMessage());
            return null;
        }

        return output;
    }

    // returns int at (position) from (fileName)
    public static int readIntFromFile(String fileName, int position)
    {
        int output = -1;

        // make sure fileName starts with '/'
        String fName = fileName;
        if(fileName.charAt(0) != '/')
            fName = "/" + fName;

        // create file
        File file = new File(path, fName);

        if(!file.exists())
            return output;

        try {
            // create input streams
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);

            // create container for data
            final int sizeOfInt = 4;
            byte bytes[] = new byte[sizeOfInt];

            // read from file
            // bis.read(bytes, offset, sizeOfInt); // this wont work - any offset > 0 is out of bounds
            int offset = position * sizeOfInt;
            int totalToCheck = (1 + position) * sizeOfInt;
            for(int i = 0; i < totalToCheck ; i++)
            {
                if(i < offset)
                    bis.read();
                else
                {
                    bis.read(bytes);
                    break;
                }
            }

            // copy data to output
            ByteBuffer bb = ByteBuffer.allocate(sizeOfInt);
            bb.order(ByteOrder.BIG_ENDIAN);
            bb.put(bytes[0]);
            bb.put(bytes[1]);
            bb.put(bytes[2]);
            bb.put(bytes[3]);
            output = bb.getInt(0);

            System.out.println("readIntFromFile().output " + output);

            // close streams
            bis.close();
            fis.close();
        } catch (FileNotFoundException e){
            System.out.println("readIntFromFile().FileNotFoundException: " + e.getMessage());
        } catch(IOException e) {
            System.out.println("readIntFromFile().General I/O exception: " + e.getMessage());
        }

        return output;
    }

    // returns int at (position) from (directory) + (fileName)
    public static int readIntFromFile(String directory, String fileName, int position)
    {
        int output = -1;

        // make sure fileName starts with '/'
        String fName = fileName;
        if(fileName.charAt(0) != '/')
            fName = "/" + fName;

        // make sure path and directory have '/' between them
        String fPath = path.getPath();
        if(directory.charAt(0) != '/')
            fPath = fPath + "/" + directory;
        else
            fPath = fPath + directory;


        //File fileDir = new File(fPath);
        //fileDir.mkdirs();

        // get file
        File file = new File(fPath, fName);

        if(!file.exists())
            return output;

        try {
            // create input streams
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);

            // create container for data
            final int sizeOfInt = 4;
            byte bytes[] = new byte[sizeOfInt];

            // read form file
            // bis.read(bytes, offset, sizeOfInt); // this wont work - any offset > 0 is out of bounds
            int offset = position * sizeOfInt;
            int totalToCheck = (1 + position) * sizeOfInt;
            for(int i = 0; i < totalToCheck ; i++)
            {
                if(i < offset)
                    bis.read();
                else
                {
                    bis.read(bytes);
                    break;
                }
            }

            // copy data to output
            ByteBuffer bb = ByteBuffer.allocate(sizeOfInt);
            bb.order(ByteOrder.BIG_ENDIAN);
            bb.put(bytes[0]);
            bb.put(bytes[1]);
            bb.put(bytes[2]);
            bb.put(bytes[3]);
            output = bb.getInt(0);

            System.out.println("readIntFromFile().output " + output);

            // close streams
            bis.close();
            fis.close();

        } catch (FileNotFoundException e){
            System.out.println("readIntFromFile().FileNotFoundException: " + e.getMessage());
        } catch(IOException e) {
            System.out.println("readIntFromFile().General I/O exception: " + e.getMessage());
        }

        return output;
    }

    // UNTESTED
    // returns file name at (position) in (directory)
    public static String getFileName(String directory, int position)
    {
        String filePath = path + "/" + directory;

        File f = new File(filePath);
        File fileList[] = f.listFiles();

        int limiter = fileList.length;

        if(position < limiter)
            return  fileList[position].getName();
        else
            return null;
    }

    // UNTESTED
    // returns position of (fileName) in (directory)
    // returns -1 if failed
    public static int getFilePositionByName(String directory, String fileName)
    {
        int position = -1;

        // make sure fileName starts with '/'
        String fName = fileName;
        if(fileName.charAt(0) != '/')
            fName = "/" + fName;

        // make sure path and directory have '/' between them
        String fPath = path.getPath();
        if(directory.charAt(0) != '/')
            fPath = fPath + "/" + directory;

        // get directory
        File p = new File(fPath);

        // get file
        File f = new File(fPath, fName);

        // check if it exists
        if(f.exists())
        {
            // create file list
            File fileList[] = p.listFiles();

            // check all files for file
            int limiter = fileList.length;
            for (int i = 0; i < limiter; i++)
            {
                if (fileList[position].equals(f))
                {
                    position = i;
                    break;
                }
            }
        }

        return position;
    }

    // returns number of file in (directory)
    public static int getNumberOfFiles(String directory)
    {
        String filePath = path + "/" + directory;

        File f = new File(filePath);
        File fileList[] = f.listFiles();

        return fileList.length;
    }

    // UNTESTED
    // creates a copy of file (toCopyFileName) in (directory) named (newFileName)
    public static boolean copyFile(String directory, String toCopyFileName, String newFileName)
    {
        String fName = toCopyFileName;

        if(toCopyFileName.charAt(0) != '/')
            fName = "/" + fName;

        String cName = newFileName;

        if(newFileName.charAt(0) != '/')
            cName = "/" + cName;

        String fPath = path.getPath();

        if(directory.charAt(0) != '/')
            fPath = fPath + "/" + directory;
        else
            fPath = fPath + directory;

        File file = new File(fPath);
        file.mkdirs();

        file = new File(fPath, fName);
        File fileCopy = new File(fPath, cName);

        if(fileCopy.exists())
            file.delete();

        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileCopy));

            final int sizeOfInt = 4;
            byte bytes[] = new byte[sizeOfInt];

            while(bis.read(bytes) != -1)
            {
                bos.write(bytes);
            }


            bos.flush();
            bos.close();
            bis.close();

        } catch (FileNotFoundException e) {
            System.out.println("writeFile(..., int data).FileNotFoundException: " + e.getMessage());
            createNewFile(newFileName);
            return false;
        } catch(IOException e) {
            System.out.println("writeFile(..., int data).General I/O exception: " + e.getMessage());
            return false;
        }

        return true;
    }
}
