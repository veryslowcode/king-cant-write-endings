package vsc.cli.eol;

import vsc.cli.HelpException;
import vsc.cli.eol.Indexer;

class Runner {
    public static void main(String[] args) {
        try {
            var indexer = new Indexer();
            indexer.index(args);
        } catch (HelpException e) {
            System.exit(0);
        } catch (Exception e) {
            var message = e.getMessage();
            if (message != null && message.trim() != "") {
                System.out.println(message);
            }
            System.exit(1);
        }
    }
}
