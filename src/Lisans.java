import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;


public class Lisans {

    private static File logFile;
    private static String path;

    public static void main(String[] args) throws IOException {
        File f = new File(System.getProperty("java.class.path"));
        File dir = f.getAbsoluteFile().getParentFile();
        path = dir.toString();
        Date date = new Date();
        logFile = new File(path + "/logs", date.toString().replaceAll(":", "-") + ".log");
        if (!logFile.exists()) {
            logFile.getParentFile().mkdirs();
            logFile.createNewFile();
        }
        System.out.printf("<----- \\ Megalow Teknoloji // ----->\n")
                .printf("           Oyuncu Bilgisi\n")
                .printf("Sunucu basariyla aktif edildi!");
        ServerSocket serverSocket;
        while (true) {
            try {
                serverSocket = new ServerSocket(1453);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String alan;
                if ((alan = in.readLine()) != null) {
                    addLog("[" + date.toGMTString() + "] " + alan.split("_")[0] + " numarali ip adresi " + alan
                            .split("_")[1] + " eklenti icin lisans kontrol etti!\n");
                    out.println(lisansOku(alan));
                }
                out.close();
                in.close();
                clientSocket.close();
                serverSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.printf("\n\nSistemsel bir hata olustu!\n");
                System.out.printf("Eklenti sahibi Yusuf Serhat Ozgen ile Discord; Yusuf#7761!\n");
                System.out.printf("adresi uzerinden iletisime geciniz!\n");
            }
        }

    }

    private static void addLog(String alan) {
        try {
            Date date = new Date();
            BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true));
            writer.newLine();
            writer.write(alan);
            writer.close();
            System.out.println(alan + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean lisansOku(String alinan) {
        String[] bol = alinan.split("_");
        Date date = new Date();
        File lisansDosyalari = new File(path + "/lisanslar");
        if (lisansDosyalari.isDirectory()) {
            for (File f : lisansDosyalari.listFiles()) {
                if (f.getName().split("_")[0].equals(bol[0])
                        && f.getName().split("_")[1].equals(bol[1])) {
                    addLog("[" + date.toGMTString() + "] " + alinan.split("_")[0]
                            + " numaralı ip " + alinan.split("_")[1] + " adlı eklenti için lisans onayladı ve aktif edildi!\n");
                    return true;
                }
            }
        }
        addLog("[" + date.toGMTString() + "] " + alinan.split("_")[0]
                + " numaralı ip " + alinan.split("_")[1] + " adlı eklenti için lisans onaylanmadi!\n");
        return false;
    }

    /*private void lisansEkle(String id, String ip, long alimTarihi) {
        File lisansDosyasi = new File(path + "/lisanslar", (ip + "_" + id + "_.lisans"));
        try {
            lisansDosyasi.getParentFile().mkdirs();
            lisansDosyasi.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Properties ayar = new Properties();
        ayar.put("Alim-Tarihi", alimTarihi);
        ayar.put("IP", ip);
        ayar.put("ID", id);

        PrintWriter writer;
        try {
            writer = new PrintWriter(lisansDosyasi);
            ayar.store(writer, "Licence");
            writer.close();
            Date date = new Date();
            addLog("[" + date.toGMTString() + "] " + ip
                    + " numaralı ip " + id + " adlı eklenti için lisans ekledi!\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}


