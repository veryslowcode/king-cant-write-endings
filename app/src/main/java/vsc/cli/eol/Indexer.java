package vsc.cli.eol;

import vsc.cli.Help;
import vsc.cli.HelpException;
import vsc.cli.eol.Ending;
import vsc.cli.eol.LineChanger;
import vsc.cli.arguments.Parser;

import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class Indexer {
    // Help information
    Map<String, String> possibleArguments;
    Map<String, String> possibleFlags;

    String path;
    Ending ending;
    List<String> ignores;

    // Color output
    boolean color = true;
    String successPrefix = "\033[32m";
    String errorPrefix = "\033[31m";
    String resetPrefix = "\033[0m";

    public Indexer() {
        this.possibleArguments = new HashMap<String, String>();
        this.possibleArguments.put("ending", "EOL style: unix(\\n), dos(\\r\\n), mac(\\r)");
        this.possibleArguments.put("path", "The file or directory to change the files of");
        this.possibleArguments.put("ignore", "Comma separated list of files or directories to ignore");

        this.possibleFlags = new HashMap<String, String>();
        this.possibleFlags.put("h", "Display this menu");
        this.possibleFlags.put("n", "Disable color output");
    }

    public void index(String[] args) throws Exception {
        this.handleFlags(args);
        this.handleArguments(args);
        Indexer.traverse(this.path, this.ending, this.ignores, this.successPrefix, this.resetPrefix);
    }
    
    void handleArguments(String[] args) throws Exception {
        var arguments = Parser.parseArguments(args);
        
        if (arguments.isEmpty()) {
            this.handleError("Invalid or missing arguments");
        }

        this.path = arguments.get("path");

        var endingInput = arguments.get("ending");
        this.ending = Ending.getEnum(endingInput);

        if (this.path == null || this.ending == null) {
            this.handleError("Invalid or missing arguments");
        }

        if (arguments.get("ignore") != null) {
            this.ignores = Arrays.asList(arguments.get("ignore").split(","));
        }
    }

    void handleError(String message) throws Exception {
        System.out.println("An error occurred:");
        System.out.println("\t" + this.errorPrefix + message + this.resetPrefix);
        this.handleHelp();
        throw new Exception();
    }

    void handleFlags(String[] args) throws HelpException {
        var flags = Parser.parseFlags(args);

        if (flags.get("n") != null) {
            this.color = false;
            this.successPrefix = "";
            this.errorPrefix = "";
            this.resetPrefix = "";
        }

        if (flags.get("h") != null) {
            this.handleHelp();
            throw new HelpException();
        }
    }

    void handleHelp() {
        var help = new Help(this.possibleArguments, this.possibleFlags);
        help.color = this.color;
        help.displayHelp();
    }

    public static void traverse(String path, Ending ending, List<String> ignores, String prefix, String suffix) throws Exception {
        File root = new File(path);

        if (ignores.contains(root.getName())) {
            return;
        }

        if (root.isDirectory()) {
            System.out.println("At " + root.getName());
            File[] subPaths = root.listFiles();
            for(File subPath : subPaths) {
                Indexer.traverse(subPath.getPath(), ending, ignores, prefix, suffix);
            }
        } else {
            LineChanger.changeLines(path, ending, prefix, suffix);
        }
    }
}
