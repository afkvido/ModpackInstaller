import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

public class Main {


    public static final String version = "0.1.3";

    final static String fail = "URLreader failed";

    public static String os;


    public static void main (String[] args) {

        // Make sure that the OS is Windows
        manageOS();

        // Make sure that the latest version of the Installer is being run
        version();
    }

    /** Check the OS. */
    static void manageOS () {

        try {
            os = System.getProperty("os.name");
        } catch (Exception e) {
            System.out.println("[ModpackInstaller] Cannot get the OS.\nThe program might be missing permissions to environment variables.");
        }

        System.out.println("[ModpackInstaller] OS: " + os);
        if (!os.contains("Windows")) {
            System.out.printf("[ModpackInstaller] Your operating system, %s, is not supported.\n", os);
            System.out.println("[ModpackInstaller] ModpackInstaller only works on Windows.");
            Scanner wait = new Scanner(System.in);
            wait.nextLine();
            System.exit(0);
        } else {
            System.out.println("[ModpackInstaller] OS is supported: " + os);
        }
    }

    /** Print the results of the command line process. */
    static void printResults (Process process) {

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (Exception ignored) {}
    }

    /** Read a URL. */
    static String check (String url) {
        String r;
        try {
            URL oracle = new URL(url);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()));

            StringBuilder e = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                e.append(inputLine).append("\n");

            in.close();
            r = e.toString();
        } catch (Exception ignored) {
            r = fail;
        }
        return r;
    }

    /** Enforce the version of the installer. */
    static void version () {

        Scanner wait = new Scanner(System.in);

        String latestVersion = check("https://raw.githubusercontent.com/afkvido/MinecraftDungeonsModpacks/main/InstallerAPI.version").replace("\n", "");

        System.out.println("[ModpackInstaller] Current version: " + version);
        System.out.println("[ModpackInstaller] Latest version: " + latestVersion);

        if (latestVersion.equals(fail)) {
            System.out.println("[ModpackInstaller] Cannot connect to the Modpack Installer API.\nMake sure you have actual internet.\nOr, you might just have an old version. Download a new version here: https://github.com/NecroClient/Installer/releases");
            wait.nextLine();
            System.exit(0);
        }
        if (!latestVersion.equals(version)) {
            System.out.println("[ModpackInstaller] You're on an old version of Modpack Installer.\nDownload a new version here: https://github.com/NecroClient/Installer/releases");
            wait.nextLine();
            System.exit(0);
        } else {
            System.out.println("[ModpackInstaller] Version check: Latest!");
        }

    }

    static void install () {

        String localappdata;

        try {
            localappdata = System.getenv("LOCALAPPDATA");
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.out.println("[ModpackInstaller] Cannot get the LOCALAPPDATA environment variable.\nThe program might be missing permissions to environment variables.");
        }

    }

    /** Utility class <b>Main</b> cannot be initialized. */
    private Main () {

    }




}
