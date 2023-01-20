import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    // новый коммит
    private static File desktop;

    // Получение расширения файла
    public static String getType(String fileName) {
        Pattern pattern = Pattern.compile("\\.[A-Za-z]+$");
        Matcher matcher = pattern.matcher(fileName);
        matcher.find();
        fileName = matcher.group();
        return fileName.substring(1);
    }

    // получение текущей даты
    public static String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_YY");
        Calendar date = Calendar.getInstance();
        return dateFormat.format(date.getTime());
    }

    // перемещение файла в папку соответствии с типом
    public static void pack(File mainDir, File file) {
        String fileType = getType(file.getName());
        try {
            switch (fileType) {
                case "txt" :
                    Files.move(Paths.get(file.getAbsolutePath()), Paths.get(mainDir.getAbsolutePath() + "/Text Files/" + file.getName()));
                    break;
                case "jpg", "png", "gif":
                    Files.move(Paths.get(file.getAbsolutePath()), Paths.get(mainDir.getAbsolutePath() + "/Images/" + file.getName()));
                    break;
                case "docx", "pptx", "xlsx":
                    Files.move(Paths.get(file.getAbsolutePath()), Paths.get(mainDir.getAbsolutePath() + "/Microsoft Office/" + file.getName()));
                    break;
                case "pdf":
                    Files.move(Paths.get(file.getAbsolutePath()), Paths.get(mainDir.getAbsolutePath() + "/Pdf/" + file.getName()));
                    break;
                case "lnk":
                    Files.move(Paths.get(file.getAbsolutePath()), Paths.get(mainDir.getAbsolutePath() + "/Links/" + file.getName()));
                    break;
                default:
                    Files.move(Paths.get(file.getAbsolutePath()), Paths.get(mainDir.getAbsolutePath() + "/Other/" + file.getName()));
                    break;
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void main(String[] args) {
        String user = System.getProperty("user.home");
        System.out.println(user);
        desktop = new File(System.getProperty("user.home") + "/Desktop");
        if(!desktop.exists()) {
            desktop = new File(System.getProperty("user.home") + "/OneDrive/Desktop");
        }

        File dir = new File(desktop.getAbsolutePath() + "/Pannier");
        if(!dir.exists())
            dir.mkdir();
        // создание основной директории
        File mainDir = new File(desktop.getAbsolutePath() + "/Pannier/" + getDate());
        if(mainDir.exists()) {
            for(File x : desktop.listFiles()) {
                if(x.isFile())
                    pack(mainDir, x);
            }
            System.exit(0);
        }
        mainDir.mkdir();

        // создание иерархии
        new File(mainDir.getAbsolutePath() + "/Text Files").mkdir();
        new File(mainDir.getAbsolutePath() + "/Images").mkdir();
        new File(mainDir.getAbsolutePath() + "/Microsoft Office").mkdir();
        new File(mainDir.getAbsolutePath() + "/Pdf").mkdir();
        new File(mainDir.getAbsolutePath() + "/Links").mkdir();
        new File(mainDir.getAbsolutePath() + "/Other").mkdir();

        for(File x : desktop.listFiles()) {
            if(x.isFile())
                pack(mainDir, x);
        }
    }
}