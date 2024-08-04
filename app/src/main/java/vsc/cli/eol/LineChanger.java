package vsc.cli.eol;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileOutputStream;

public class LineChanger {
    public static void changeLines(String path, Ending ending, String prefix, String suffix) throws Exception {
        System.out.println(prefix + "EOL change for " + path + suffix);

        FileReader fileReader = new FileReader(path);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String currentLine;
        StringBuffer stringBuffer = new StringBuffer();
        while((currentLine = bufferedReader.readLine()) != null) {
            stringBuffer.append(currentLine + ending.toString()); 
        }

        bufferedReader.close();

        FileOutputStream outputStream = new FileOutputStream(path);
        outputStream.write(stringBuffer.toString().getBytes());
        outputStream.close();
    }
}
